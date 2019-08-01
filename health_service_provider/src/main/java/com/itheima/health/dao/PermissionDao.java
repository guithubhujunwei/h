package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/1
 * @description ：权限Dao
 * @version: 1.0
 */
public interface PermissionDao {
	/**
	 * 根据角色ID，获取权限列表
	 * @param id
	 * @return
	 */
	Set<Permission> findByRoleId(@Param("id") Integer id);
}
