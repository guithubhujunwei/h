package com.itheima.health.mobile.controller;

import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.mobile.utils.SMSUtils;
import com.itheima.health.mobile.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/mobile/validateCode")
public class ValidateCodeController {

	@Autowired
	private JedisPool jedisPool;

	@RequestMapping("/send4Order")
	public Result send4Order(String telephone){
		// 生成短信验证码
		Integer code = ValidateCodeUtils.generateValidateCode(4);
		try{
			// 调用三方发送短信
			//SMSUtils.sendShortMessage(telephone,code.toString());
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
		}
		System.out.println("发送的手机验证码为："+code);
		// 把验证码缓存到redis
		jedisPool.getResource().setex(telephone+ RedisConst.SENDTYPE_ORDER,5*60,code.toString());
		return new Result(true,MessageConst.SEND_VALIDATECODE_SUCCESS);
	}

	@RequestMapping("/send4Login")
	public Result send4Login(String telephone){
		// 生成登录验证码
		Integer code = ValidateCodeUtils.generateValidateCode(6);
		try{
			// 调用三方发送短信
			//SMSUtils.sendShortMessage(telephone,code.toString());
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
		}
		System.out.println("发送的手机验证码为："+code);
		// 把验证码缓存到redis
		jedisPool.getResource().setex(telephone+ RedisConst.SENDTYPE_LOGIN,5*60,code.toString());
		return new Result(true,MessageConst.SEND_VALIDATECODE_SUCCESS);
	}
}
