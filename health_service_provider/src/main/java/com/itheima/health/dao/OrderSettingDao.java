package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/11
 * @description ：订单设置Dao
 * @version: 1.0
 */
public interface OrderSettingDao {
	/**
	 * 添加预约设置
	 * @param orderSetting 预约设置数据
	 */
	void add(OrderSetting orderSetting);

	/**
	 * 基于预约日期更新预约设置
	 * @param orderSetting 预约设置数据
	 */
	void updateOrderSettingByOrderDate(OrderSetting orderSetting);

	/**
	 * 统计某一日期下的数据
	 * @param date
	 * @return
	 */
	Long countByOrderDate(Date date);

	/**
	 * 获取某月数据
	 * @param date 提供月初与月末时间段
	 */
	List<OrderSetting> getOrderSettingByMonth(Map date);

	/**
	 * 基于预约日期获取预约设置
	 * @param orderDate
	 * @return
	 */
	OrderSetting findByOrderDate(Date orderDate);

	/**
	 * 更新预约设置天数
	 * @param orderSetting
	 */
	void editReservationsByOrderDate(OrderSetting orderSetting);

}
