package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/8
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
@Slf4j
public class CheckItemServiceImpl implements CheckItemService {

	@Autowired
	private CheckItemDao checkItemDao;

	@Override
	public void add(CheckItem checkItem) {
		checkItemDao.add(checkItem);
	}

	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		// 使用分页插件PageHelper
		PageHelper.startPage(currentPage,pageSize);
		// 获取分页数据
		Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
		// 封装分页返回对象
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public void deleteById(Integer id) {
		long count = checkItemDao.countCheckItemsById(id);
		if( count > 0){
			throw new RuntimeException("当前检查项有数据，不能删除");
		}
		checkItemDao.deleteById(id);
	}

	@Override
	public CheckItem findById(Integer id) {
		return checkItemDao.findById(id);
	}

	@Override
	public void edit(CheckItem checkItem) {
		checkItemDao.edit(checkItem);
	}

	@Override
	public List<CheckItem> findAll() {
		return checkItemDao.findAll();
	}
}
