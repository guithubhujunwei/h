package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.common.RedisConst;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/10
 * @description ：套餐控制器类
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
	@Autowired
	private JedisPool jedisPool;

	@Reference
	private SetmealService setmealService;


	/**
	 * 图片上传
	 * @param imgFile 图片上传组件
	 * @return
	 */
	@RequestMapping("/upload")
	public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
		try{
			// 获取原图片文件名
		    String fileName = imgFile.getOriginalFilename();
		    // 文件名的基础上，在前面加入UUID，防止文件重名
			String saveUploadName = UUID.randomUUID().toString().replace("-","")+"_"+fileName;
			// 使用七牛工具类，完成图片上传
			QiniuUtils.upload2Qiniu(imgFile.getBytes(),saveUploadName);
			// 把访问路径返回给客户端
			String file_url = QiniuUtils.qiniu_img_url_pre+saveUploadName;

			// 把文件名存入redis,基于Redis的Set集合
			jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES,saveUploadName);
			// 把全路径返回给客户端
			return new Result(true, MessageConst.PIC_UPLOAD_SUCCESS,file_url);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.PIC_UPLOAD_FAIL);
		}
	}

	/**
	 * 新增套餐
	 * @param setmeal 套餐基本信息
	 * @param checkgroupIds 选择的检查组列表
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
		try{
			// 去掉图片路径中，七牛访问域的前缀部分
			setmeal.setImg(setmeal.getImg().replace(QiniuUtils.qiniu_img_url_pre,""));
			setmealService.add(setmeal,checkgroupIds);
			return new Result(true, MessageConst.ADD_SETMEAL_SUCCESS);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.ADD_CHECKGROUP_FAIL);
		}
	}

	/**
	 * 分页获取套餐数据
	 * @param queryPageBean 查询参数
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(@RequestBody  QueryPageBean queryPageBean){
		PageResult pageResult = setmealService.pageQuery(
			queryPageBean.getCurrentPage(),
			queryPageBean.getPageSize(),
			queryPageBean.getQueryString());
		return pageResult;
	}
}
