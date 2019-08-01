package com.itheima.health.service;

import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/27
 * @description ：
 * @version: 1.0
 */
public interface ReportService {
	/**
	 * 获取套餐占比
	 * @return
	 */
	public List<Map<String,Object>> findSetmealCount();

	/**
	 * 获取运营数据
	 * @return
	 */
	public Map<String,Object> findBusinessData();
}
