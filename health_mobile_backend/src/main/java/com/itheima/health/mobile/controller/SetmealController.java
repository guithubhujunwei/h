package com.itheima.health.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/12
 * @description ：套餐Controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/mobile/setmeal")
public class SetmealController {

	@Reference
	private SetmealService setmealService;

	/**
	 * 获取套餐列表数据
	 * @return
	 */
	@RequestMapping("/getSetmeal")
	public Result getSetmeal(){
		try{
			List<Setmeal> setmealList = setmealService.findAll();
			return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS,setmealList);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.QUERY_SETMEAL_FAIL);
		}
	}

	@RequestMapping("/findById")
	public Result findById(Integer id){
		try{
		    Setmeal setmeal = setmealService.findById(id);
		    return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS,setmeal);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.QUERY_SETMEAL_FAIL);
		}
	}


}
