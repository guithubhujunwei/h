package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
public interface OrderSettingService {
	/**
	 * 添加预约内容
	 * @param list 预约设置列表
	 */
	public void add(List<OrderSetting> list);

	/**
	 * 获取某个月份的设置列表
	 * @param date 月份
	 * @return 列表包含Map,预约及
	 */
	public List<Map> getOrderSettingByMonth(String date);

	/**
	 * 基于日期编辑数据
	 * @param orderSetting 预约数据
	 */
	public void editNumberByDate(OrderSetting orderSetting);
}
