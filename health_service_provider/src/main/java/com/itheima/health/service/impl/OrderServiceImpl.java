package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.common.MessageConst;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/12
 * @description ：预约订单实现类
 * @version: 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderSettingDao orderSettingDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private OrderDao orderDao;

	/**
	 * 1、检查预约日期是否做预约设置
	 * 2、检查预约日期是否已经约满
	 * 3、检查用户是否为会员，
	 *          是，检查用户是否重复预约
	 *          不，是会员则自动完成注册并进行预约
	 * 4、更新预约设置当日的已预约人数
	 * 5、保存预约订单，预约成功
	 * @param map 存储表单数据
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result addOrder(Map<String,String> map) {
		try{
			// 检查日期是否做过预约设置
			log.debug("检查日期是否做过预约设置");
			Date orderDate = DateUtils.parseString2Date(map.get("orderDate"));
			OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
			if (orderSetting == null){
				return new Result(false, MessageConst.SELECTED_DATE_CANNOT_ORDER);
			}
			// 检查预约日期是否已经约满
			log.debug("检查预约日期是否已经约满");
			int number = orderSetting.getNumber();
			int reservations = orderSetting.getReservations();
			if (reservations >= number){
				return new Result(false,MessageConst.ORDER_FULL);
			}
			// 检查用户是否为会员
			log.debug("检查用户是否为会员");
			String telephone = map.get("telephone");
			Member member = memberDao.findByTelephone(telephone);
			int setmealId = Integer.parseInt(map.get("setmealId"));	// 套餐ID;
			if (member != null){
				// 是会员，检查是否已经预约
				int memberId = member.getId();	// 会员ID
				Order order = new Order(memberId,orderDate,null,null,setmealId);
				List<Order> orderList = orderDao.findByCondition(order);
				log.debug("------findByCondition orderLits:{}",orderList);
				if (orderList !=null || orderList.size() > 0 ){
					return new Result(false,MessageConst.HAS_ORDERED);
				}
			}else{
				// 非会员,保存会员信息
				member = new Member();
				member.setName(map.get("name"));
				member.setPhoneNumber(telephone);
				member.setIdCard(map.get("idCard"));
				member.setRegTime(new Date());
				member.setSex(map.get("sex"));
				memberDao.add(member);
				log.debug("保存会员...");
			}
			// 更新预约设置当日的已预约人数
			orderSetting.setReservations(orderSetting.getReservations()+1);
			orderSettingDao.editReservationsByOrderDate(orderSetting);
			// 保存预约订单
			Order order = new Order(member.getId(),
									orderDate, map.get("orderType"),Order.ORDERSTATUS_NO,setmealId);
			orderDao.add(order);
			log.debug("保存预约订单...{}",order.toString());
			return  new Result(true,MessageConst.ORDER_SUCCESS,order);
		}catch(Exception e){
		    e.printStackTrace();
		}
		return new Result(true,MessageConst.ACTION_FAIL);
	}

	@Override
	public Result findById4Detail(Integer id) {
		try{
		    Map map = orderDao.findById4Detail(id);
		    map.put("orderDate",DateUtils.parseDate2String((Date) map.get("orderDate")));
		    return new Result(true,MessageConst.QUERY_ORDER_SUCCESS,map);
		}catch(Exception e){
		    e.printStackTrace();
			return new Result(false,MessageConst.QUERY_ORDER_FAIL);
		}
	}
}
