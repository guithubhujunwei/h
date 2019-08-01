package com.itheima.health.service;

import com.itheima.health.pojo.User;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/7
 * @description ：
 * @version: 1.0
 */
public interface UserService {
	public boolean login(String username,String password);

	/**
	 * 根据用户名获取用户信息(基本信息、角色信息、权限信息)
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
}
