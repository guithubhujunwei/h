package com.itheima.health.common;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/10
 * @description ：Redis常量类
 * @version: 1.0
 */
public class RedisConst {
	//套餐图片所有图片名称
	public static final String SETMEAL_PIC_RESOURCES = "setmealPicResources";
	//套餐图片保存在数据库中的图片名称
	public static final String SETMEAL_PIC_DB_RESOURCES = "setmealPicDbResources";

	//用于缓存体检预约时发送的验证码
	public static final String SENDTYPE_ORDER = "001";
	//用于缓存手机号快速登录时发送的验证码
	public static final String SENDTYPE_LOGIN = "002";
	//用于缓存找回密码时发送的验证码
	public static final String SENDTYPE_GETPWD = "003";
}
