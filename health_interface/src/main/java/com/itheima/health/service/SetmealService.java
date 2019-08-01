package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/10
 * @description ：体检套餐服务接口
 * @version: 1.0
 */
public interface SetmealService {
	/**
	 * 添加体验套餐
	 * @param setmeal 体检套餐基本信息
	 * @param checkgroupIds 检查组选定列表
	 */
	public void add(Setmeal setmeal, Integer[] checkgroupIds);

	/**
	 * 分页获取套餐数据
	 * @param currentPage 当前页码
	 * @param pageSize 默认条数
	 * @param queryString 查询条件
	 * @return
	 */
	public PageResult pageQuery(Integer currentPage,Integer pageSize,String queryString);

	/**
	 * 获取所有套餐的列表
	 * @return
	 */
	public List<Setmeal> findAll();

	/**
	 * 基于ID，获取套餐详情
	 * @param id
	 * @return
	 */
	public Setmeal findById(Integer id);

	/**
	 * 获取套餐占比
	 * @return
	 */
	public List<Map<String,Object>> findSetmealCount();
}
