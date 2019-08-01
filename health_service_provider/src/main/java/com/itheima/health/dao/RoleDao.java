package com.itheima.health.dao;

import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/1
 * @description ：角色Dao
 * @version: 1.0
 */
public interface RoleDao {
	Set<Role> findByUserId(@Param("id") Integer id);
}
