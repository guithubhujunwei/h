package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */
public interface OrderDao {
	/**
	 * 保存订单
	 * @param order 订单数据
	 */
	void add(Order order);

	/**
	 *  基于条件查找订单数据
	 * @param order
	 * @return
	 */
	List<Order> findByCondition(Order order);

	/**
	 * 基于ID获取预约详情
	 * @param id
	 * @return
	 */
	Map<String,Object> findById4Detail(Integer id);

	/**
	 * 获取套餐占比
	 * @return
	 */
	List<Map<String,Object>> findSetmealCount();


	/**
	 * 统计某一日期预约人数
	 * @param date
	 * @return
	 */
	Integer totalOrderByDate(@Param("date") String date);

	/**
	 * 统计某一日期之后预约人数
	 * @param date
	 * @return
	 */
	Integer totalOrderByAfterDate(@Param("date") String date);

	/**
	 * 统计某一日期到诊人数
	 * @param date
	 * @return
	 */
	Integer totalVisitByDate(@Param("date") String date);

	/**
	 * 统计某一日期之后到诊人数
	 * @param date
	 * @return
	 */
	Integer totalVisitByAfterDate(@Param("date") String date);

	/**
	 * 获取热门套餐列表
	 * @return
	 */
	List<Map<String,Object>> getHotSetmeal();

}
