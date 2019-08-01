package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/8
 * @description ：检查项接口
 * @version: 1.0
 */
public interface CheckItemService {
	/**
	 * 新增检查项
	 * @param checkItem
	 */
	public void add(CheckItem checkItem);

	/**
	 * 分页查询
	 * @param currentPage 当前页
	 * @param pageSize 默认条数
	 * @param queryString 查询条件
	 * @return
	 */
	public PageResult pageQuery(Integer currentPage,Integer pageSize,String queryString);

	/**
	 * 基于ID，删除检查项
	 * @param id
	 */
	void deleteById(Integer id);

	/**
	 * 基于ID获取检查项数据
	 * @param id
	 * @return
	 */
	public CheckItem findById(Integer id);

	/**
	 * 编辑数据
	 * @param checkItem
	 */
	public void edit(CheckItem checkItem);

	/**
	 * 获取所有检查项
	 * @return
	 */
	public List<CheckItem> findAll();


}
