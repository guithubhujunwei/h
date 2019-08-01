package com.itheima.health.controller;

import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/1
 * @description ：
 * @version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/getUsername")
	@ResponseBody
	public String getUser(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			return  "fail";
		}
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication  authentication = securityContext.getAuthentication();
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication.getPrincipal().equals("anonymousUser") ||
			!authentication.isAuthenticated()){
			return "auth fail";
		}
		User user = (User)authentication.getPrincipal();
		System.out.println(user.getUsername()+" "+user.getPassword());
		System.out.println("author:"+user.getAuthorities());
		return user.getUsername();
	}
}
