package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/9
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class CheckGroupServiceImpl implements CheckGroupService {
	@Autowired
	private CheckGroupDao checkGroupDao;
	@Override
	public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
		log.debug("添加检查组......");
		checkGroupDao.add(checkGroup);
		for(Integer checkItemId:checkItemIds){
			Map map = new HashMap<>();
			map.put("checkgroup_id",checkGroup.getId());
			map.put("checkitem_id",checkItemId);
			checkGroupDao.addCheckGroupAndCheckItem(map);
			log.debug("添加检查项......{}:{}",checkGroup.getId(),checkItemId);
		}
	}

	public CheckGroupServiceImpl() {
		super();
	}

	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		PageHelper.startPage(currentPage,pageSize);
		Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
		PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
		return pageResult;
	}

	@Override
	public CheckGroup findById(Integer id) {
		return checkGroupDao.findById(id);
	}

	@Override
	public List<Integer> findCheckItemsByCheckGroupId(Integer id) {
		return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
	}

	@Transactional
	@Override
	public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
		// 保存检查组信息
		checkGroupDao.edit(checkGroup);
		// 删除检查组之前关联关系
		checkGroupDao.deleteCheckItemsListByIds(checkGroup.getId());
		// 保存新的关系
		for (Integer checkItemId :checkitemIds) {
			Map maps = new HashMap();
			maps.put("checkgroup_id",checkGroup.getId());
			maps.put("checkitem_id",checkItemId);
			checkGroupDao.addCheckGroupAndCheckItem(maps);
		}
	}

	@Override
	public List<CheckGroup> findAll() {
		return checkGroupDao.findAll();
	}
}
