package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/11
 * @description ：预约设置
 * @version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

	@Reference
	private OrderSettingService orderSettingService;
	/**
	 * 提交预约设置的excel文件
	 * @param multipartFile
	 */
	@RequestMapping("/upload")
	public Result upload(@RequestParam("excelFile") MultipartFile multipartFile){
		System.out.println("excelFile:"+multipartFile.getOriginalFilename());
		try{
			// 从上传组件中读取文件，利用POI工具解析内容
			List<String[]> list = POIUtils.readExcel(multipartFile);
			if (list != null && list.size() > 0){
				List<OrderSetting> orderSettingList = new ArrayList<>();
				// 从excel遍历数据，封装为设置对象，添加到集合
				for (String[] rowData:list){
					if(rowData[0] == null){
						continue;
					}
					OrderSetting orderSetting = new OrderSetting(new Date(rowData[0]),Integer.parseInt((rowData[1])));
					orderSettingList.add(orderSetting);
				}
				// 调用业务对象批量保存数据
				orderSettingService.add(orderSettingList);
			}
			return new Result(true, MessageConst.IMPORT_ORDERSETTING_SUCCESS);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false, MessageConst.IMPORT_ORDERSETTING_FAIL);
		}
	}

	/**
	 * 获取某月预约数据
	 * @param date 月份 yyyy-mm
	 * @return
	 */
	@RequestMapping("/getOrderSettingByMonth")
	public Result getOrderSettingByMonth(String date){
		try{
			// 获取数据
		    List<Map> list = orderSettingService.getOrderSettingByMonth(date);
		    // 返回数据
		    return new Result(true,MessageConst.GET_ORDERSETTING_SUCCESS,list);
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.GET_ORDERSETTING_FAIL);
		}
	}

	/**
	 * 基于日期编辑预约设置数据
	 * @param orderSetting 预约设置数据
	 * @return
	 */
	@RequestMapping("/editNumberByDate")
	public Result editNumberByDate(@RequestBody  OrderSetting orderSetting){
		try{
			// 编辑预约设置
		    orderSettingService.editNumberByDate(orderSetting);
		    // 返回设置成功
		    return new Result(true,MessageConst.ORDERSETTING_SUCCESS);
		}catch(Exception e){
		    e.printStackTrace();
		    // 返回设置失败
		    return new Result(false,MessageConst.ORDERSETTING_FAIL);
		}
	}
}
