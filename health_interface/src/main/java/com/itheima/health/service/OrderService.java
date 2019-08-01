package com.itheima.health.service;

import com.itheima.health.entity.Result;

import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/12
 * @description ：预约订单接口
 * @version: 1.0
 */
public interface OrderService {
	/**
	 * 保存预约订单
	 * @param map 存储表单数据
	 * @return
	 */
	public Result addOrder(Map<String,String> map);

	/**
	 * 根据id查询预约信息，包括体检人信息、套餐信息
	 * @param id
	 * @return
	 */
	public Result findById4Detail(Integer id);
}
