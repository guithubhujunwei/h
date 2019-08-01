package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
@Service
@Transactional
@Slf4j
public class OrderSettingServiceImpl implements OrderSettingService {
	@Autowired
	private OrderSettingDao orderSettingDao;
	@Override
	public void add(List<OrderSetting> list) {
		if(list == null || list.size() == 0){
			log.debug("list is null");
			return;
		}
		//批量添加
		for (OrderSetting orderSetting:list){
			//检查此数据（日期）是否存在
			long count = orderSettingDao.countByOrderDate(orderSetting.getOrderDate());
			if (count > 0){
				//已经存在，执行更新操作
				orderSettingDao.updateOrderSettingByOrderDate(orderSetting);
			}else{
				//不存在，执行添加操作
				orderSettingDao.add(orderSetting);
			}
		}
	}

	@Override
	public List<Map> getOrderSettingByMonth(String date) {
		String dateBegin = date+"-1";	// yyyy-mm-1
		String dateEnd = date+"-31" ;	//yyyy-mm-31
		Map map = new HashMap();
		map.put("dateBegin",dateBegin);
		map.put("dateEnd",dateEnd);
		List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
		List<Map> data = new ArrayList<>();
		for (OrderSetting orderSetting:list){
			Map<String,Object> orderSettingMap = new HashMap();
			// 预约日期（几号）
			orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
			// 可预约人数
			orderSettingMap.put("number",orderSetting.getNumber());
			// 已预约人数
			orderSettingMap.put("reservations",orderSetting.getReservations());
			// 添加到集合
			data.add(orderSettingMap);
		}
		return data;
	}

	@Override
	public void editNumberByDate(OrderSetting orderSetting) {
		//检查此数据（日期）是否存在
		long count = orderSettingDao.countByOrderDate(orderSetting.getOrderDate());
		if (count > 0){
			//已经存在，执行更新操作
			orderSettingDao.updateOrderSettingByOrderDate(orderSetting);
		}else{
			//不存在，执行添加操作
			orderSettingDao.add(orderSetting);
		}
	}
}
