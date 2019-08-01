package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author ：seanyang
 * @date ：Created in 2019/7/27
 * @description ：
 * @version: 1.0
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private MemberDao memberDao;

	@Override
	public List<Map<String, Object>> findSetmealCount() {
		return orderDao.findSetmealCount();
	}

	@Override
	public Map<String, Object> findBusinessData() {

		// 今日，本周，本月
		Date date = DateUtils.getToday();
		Date weekDay = DateUtils.getThisWeekMonday();
		Date monthFirstDay =DateUtils.getFirstDay4ThisMonth();
		String strDate = "";
		String strWeekDay = "";
		String strMonthFirstDay = "";
		try{
			strDate = DateUtils.parseDate2String(date);
			strWeekDay =  DateUtils.parseDate2String(weekDay);
			strMonthFirstDay = DateUtils.parseDate2String(monthFirstDay);
			log.debug("t:{},m:{},f:{}",strDate,strWeekDay,strMonthFirstDay);
		}catch(Exception e){
		    e.printStackTrace();
		}
		Map<String, Object> reportData = new HashMap<>();
		reportData.put("reportDate",strDate);
		// 会员相关
		reportData.put("todayNewMember",memberDao.totalMemberCountByDate(strDate));
		reportData.put("totalMember",memberDao.totalMemberCount());
		reportData.put("thisWeekNewMember",memberDao.totalMemberCountAfterDate(strWeekDay));
		reportData.put("thisMonthNewMember",memberDao.totalMemberCountAfterDate(strMonthFirstDay));
		// 预约相关
		reportData.put("todayOrderNumber",orderDao.totalOrderByDate(strDate));
		reportData.put("thisWeekOrderNumber",orderDao.totalOrderByAfterDate(strWeekDay));
		reportData.put("thisMonthOrderNumber",orderDao.totalOrderByAfterDate(strMonthFirstDay));
		// 到诊相关
		reportData.put("todayVisitsNumber",orderDao.totalVisitByDate(strDate));
		reportData.put("thisWeekVisitsNumber",orderDao.totalVisitByAfterDate(strWeekDay));
		reportData.put("thisMonthVisitsNumber",orderDao.totalVisitByAfterDate(strMonthFirstDay));
		reportData.put("hotSetmeal",orderDao.getHotSetmeal());
		return reportData;
	}
}
