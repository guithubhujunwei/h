package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/29
 * @description ：登录控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/mobile/member")
public class MemberController {

	@Autowired
	private JedisPool jedisPool;

	@Reference
	private MemberService memberService;

	/**
	 * 1. 获取客户端手机号及验证码
	 * 2. 根据手机号获取redis验证码
	 * 3. 判断用户验证码与redis验证码是否匹配
	 *    1. 如果匹配失败，返回验证码错误
	 *    2.如果成功，根据手机号获取会员信息
	 *        1. 如果有会员，登录成功
	 *        2. 如果不是会员，封装Member对象，调用Service保存
	 * @param map
	 * @return
	 */
	@RequestMapping("/smsLogin")
	public Result smsLogin(@RequestBody Map<String,String> map){
		try{
			// 获取客户端手机号及验证码
			String telephone = map.get("telephone");
			String validateCode = map.get("validateCode");
			// 读取Redis验证码
			String codeInRedis = jedisPool.getResource().get(telephone+ RedisConst.SENDTYPE_LOGIN);
			if(codeInRedis == null || codeInRedis.length()==0 ){
				return new Result(false, "验证码失效");
			}
			if ( !codeInRedis.equals(validateCode) ){
				return new Result(false, "验证码错误");
			}
			memberService.smsLogin(telephone);
			return new Result(true,MessageConst.LOGIN_SUCCESS);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.ACTION_FAIL);
		}
	}
}
