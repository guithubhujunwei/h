package com.itheima.health.controller;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/25
 * @description ：
 * @version: 1.0
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.common.MessageConst;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计报表
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

	@Reference
	private MemberService memberService;

	@Reference
	private SetmealService setmealService;

	@Reference
	private ReportService reportService;

	/**
	 * 获取前12个月，每月会员累计增长总数量
	 * @return
	 */
	@RequestMapping("/getMemberReport")
	public Result getMemberReport(){

		Calendar calendar = Calendar.getInstance();
		// 返回到12个月之前的日期
		calendar.add(Calendar.MONTH,-12);
		try{
			// 获取月份列表
			List<String> monthList = new ArrayList();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
			for (int i= 0 ;i!=12;i++){
				calendar.add(Calendar.MONTH,1);
				monthList.add(sdf.format(calendar.getTime()));
			}
			// 获取月份列表中月份的成员人数
			List<Integer> memberCount = memberService.findMemberCountByMonth(monthList);

			// 封装到返回数据
			Map<String,Object> maps	 = new HashMap<>();
			maps.put("months",monthList);
			maps.put("memberCount",memberCount);
			return new Result(true, MessageConst.ACTION_SUCCESS,maps);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new Result(false,MessageConst.ACTION_FAIL);
	}

	@RequestMapping("/getSetmealReport")
	public Result getSetmealReport(){
		try{
			// 通过service,获取套餐占比
			List<Map<String,Object>> mapList = reportService.findSetmealCount();

			// 封装套餐名称列表
			// 从集合中遍历获取套餐名称
			List<String> setmealNames = new ArrayList<>();
			for (Map<String,Object> mapOne:mapList){
				setmealNames.add(mapOne.get("name").toString());
			}
			// 返回数据,把数据封装到Map
			Map<String,Object> result = new HashMap<>();
			result.put("setmealNames",setmealNames);
			result.put("setmealCount",mapList);
			return new Result(true,MessageConst.ACTION_SUCCESS,result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false,MessageConst.ACTION_FAIL);
		}
	}

	@RequestMapping("/getBusinessReportData")
	public Result getBusinessReportData(){
		try{
		   Map<String,Object> map = reportService.findBusinessData();
		   return new Result(true,MessageConst.ACTION_SUCCESS,map);
		}catch(Exception e){
		    e.printStackTrace();
		    return  new Result(false,MessageConst.ACTION_FAIL);
		}
	}

	@RequestMapping("/exportBusinessReport")
	public Result exportBusinessData(HttpServletResponse response){
		try{
		    // 通过service获取源数据
			Map<String,Object> reportData = reportService.findBusinessData();
			log.debug(">>>>>> reportData:{}",reportData);
			// 把数据写入excel
			// 先读取Excel
			InputStream inputStream = this.getClass().getResourceAsStream("/report_template.xlsx");
			// 构建excel对象
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
			// 读取sheet
			Sheet sheet = xssfWorkbook.getSheetAt(0);
			// 读取Row
			Row row  = sheet.getRow(2);
			// 读取cell
			// 设置报表日期
			row.getCell(5).setCellValue(reportData.get("reportDate").toString());

			// 设置会员相关数据
			row = sheet.getRow(4);
			row.getCell(5).setCellValue((Integer)reportData.get("todayNewMember"));
			row.getCell(7).setCellValue((Integer)reportData.get("totalMember"));
			row = sheet.getRow(5);
			row.getCell(5).setCellValue((Integer)reportData.get("thisWeekNewMember"));
			row.getCell(7).setCellValue((Integer)reportData.get("thisMonthNewMember"));
			// 设置预约及已到诊数据
			// 设置今日预约及到诊
			row = sheet.getRow(7);
			row.getCell(5).setCellValue((Integer)reportData.get("todayOrderNumber"));
			row.getCell(7).setCellValue((Integer)reportData.get("todayVisitsNumber"));
			// 设置本周预约及到诊
			row = sheet.getRow(8);
			row.getCell(5).setCellValue((Integer)reportData.get("thisWeekOrderNumber"));
			row.getCell(7).setCellValue((Integer)reportData.get("thisWeekVisitsNumber"));
			// 设置本月预约及到诊
			row = sheet.getRow(9);
			row.getCell(5).setCellValue((Integer)reportData.get("thisMonthOrderNumber"));
			row.getCell(7).setCellValue((Integer)reportData.get("thisMonthVisitsNumber"));

			// 设置热门套餐
			int rowNum = 12;
			List<Map<String,Object>> hotSetmealList = (List<Map<String,Object>>)reportData.get("hotSetmeal");
			for(Map<String,Object> oneMap:hotSetmealList){
				String name = (String)oneMap.get("name");
				Long setmealCount = (Long)oneMap.get("setmeal_count");
				BigDecimal proportion = (BigDecimal)oneMap.get("proportion");
				row = sheet.getRow(rowNum);
				row.getCell(4).setCellValue(name);
				row.getCell(5).setCellValue(setmealCount);
				row.getCell(6).setCellValue(proportion.doubleValue());
				rowNum++;
			}
			// 通过流下载excel
			// 获取到客户端流
			ServletOutputStream servletOutputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-Disposition","attachment;filename="+reportData.get("reportDate").toString()+"_report.xlsx");
			xssfWorkbook.write(servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();
			xssfWorkbook.close();
			return null;
		}catch(Exception e){
		    e.printStackTrace();
		    return new Result(false,MessageConst.ACTION_FAIL);
		}
	}
}
