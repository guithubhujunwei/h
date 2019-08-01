package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/1
 * @description ：用户Dao接口
 * @version: 1.0
 */
public interface UserDao {
	/**
	 * 根据用户ID，获取用户信息
	 * @param id
	 * @return
	 */
	User findById(@Param("id") Integer id);

	/**
	 * 根据用户名，获取用户信息
	 * @param username
	 * @return
	 */
	User findByUsername(@Param("username") String username);
}
