package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/8
 * @description ：检查项控制器类
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkitem")
@Slf4j
public class CheckItemController {

	@Reference
	private CheckItemService checkItemService;

	@PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@RequestBody CheckItem checkItem){
		try{
			checkItemService.add(checkItem);
			return  new Result(true, MessageConst.ADD_CHECKITEM_SUCCESS);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
		}
	}

	/**
	 * 分页查询
	 * @param queryPageBean
	 * @return
	 */
	@PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
	@RequestMapping("/findPage")
	@ResponseBody
	public PageResult findPage(@RequestBody  QueryPageBean queryPageBean){
		PageResult pageResult = null;
		try{
			pageResult = checkItemService.pageQuery(queryPageBean.getCurrentPage(),
				queryPageBean.getPageSize(),queryPageBean.getQueryString());
			return pageResult;
		}catch(Exception e){
		    e.printStackTrace();
		    return new PageResult(0L,null);
		}
	}

	/**
	 * 基于ID，删除检查项
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(Integer id){
		try{
		    checkItemService.deleteById(id);
		    return new Result(true,MessageConst.DELETE_CHECKITEM_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			return new Result(false,e.getMessage());
		}
	}

	@PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
	@RequestMapping("/findById")
	@ResponseBody
	public Result findById(Integer id){
		try{
			CheckItem checkItem  = checkItemService.findById(id);
			return new Result(true,MessageConst.ACTION_SUCCESS,checkItem);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.ACTION_FAIL);
		}
	}

	@PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(@RequestBody CheckItem checkItem){
		try{
		    checkItemService.edit(checkItem);
		    return new Result(true,MessageConst.ACTION_SUCCESS);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.ACTION_FAIL);
		}
	}

	@PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
	@RequestMapping("/findAll")
	@ResponseBody
	public Result findAll(){
		try{
		    List<CheckItem> checkItemList = checkItemService.findAll();
		    return new Result(true,MessageConst.QUERY_CHECKITEM_SUCCESS,checkItemList);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
		}
	}
}
