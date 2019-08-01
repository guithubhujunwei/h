package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/8
 * @description ：Dao接口
 * @version: 1.0
 */
public interface CheckItemDao {
	/**
	 * 新增检查项
	 * @param checkItem
	 */
	public void add(CheckItem checkItem);

	/**
	 * 基于分页插件进行分页查询
	 * @param queryString
	 * @return
	 */
	public Page<CheckItem> selectByCondition(@Param("queryString") String queryString);

	/**
	 * 基于检查项ID，查询是否有关联数据
	 * @param checkItemId
	 * @return
	 */
	public Long countCheckItemsById(Integer checkItemId);

	/**
	 * 基于ID删除
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 基于ID，获取数据
	 * @param id
	 * @return
	 */
	public CheckItem findById(Integer id);

	/**
	 * 基于ID，更新当前数据
	 * @param checkItem
	 */
	public void edit(CheckItem checkItem);

	/**
	 * 获取所有检查项列表
	 * @return
	 */
	public List<CheckItem> findAll();
}
