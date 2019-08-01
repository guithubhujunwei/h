package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.apache.ibatis.annotations.Param;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/12
 * @description ：会员Dao
 * @version: 1.0
 */
public interface MemberDao {
	/**
	 * 根据电话查找是否有该会员
	 * @param telephone
	 * @return
	 */
	Member findByTelephone(String telephone);

	/**
	 * 保存会员信息
	 * @param member
	 */
	void add(Member member);

	/**
	 * 获取某一日期之前的注册会员数量
	 * @param date
	 * @return
	 */
	Integer findMemberCountBeforeDate(@Param("date") String date);

	/**
	 * 统计会员总数量
	 * @return
	 */
	Integer totalMemberCount();

	/**
	 * 统计某一日期会员注册数量
	 * @param date
	 * @return
	 */
	Integer totalMemberCountByDate(@Param("date") String date);

	/**
	 * 统计某一日期之后（到今日）会员注册数量
	 * @param date
	 * @return
	 */
	Integer totalMemberCountAfterDate(@Param("date") String date);

}
