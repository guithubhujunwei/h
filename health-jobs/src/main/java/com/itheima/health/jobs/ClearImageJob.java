package com.itheima.health.jobs;

import com.itheima.health.common.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;


/**
 * @author ：seanyang
 * @date ：Created in 2019/6/10
 * @description ：任务调度类
 * @version: 1.0
 */
@Service
public class ClearImageJob {

	@Autowired
	private JedisPool jedisPool;
	/**
	 * 定义清理图片的任务
	 */
	public void clearImageJob(){
			try{
				System.out.println("clearImageJob......jedisPool:"+jedisPool!=null +" getResource:"+jedisPool.getResource());
				// 获取两个数据差集
			Set<String> set = jedisPool.getResource().sdiff(RedisConst.SETMEAL_PIC_DB_RESOURCES,
				RedisConst.SETMEAL_PIC_RESOURCES);
			if (set == null){
				System.out.println("clearImageJob......set is null");
				return;
			}
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()){
				String picName = iterator.next();
				QiniuUtils.deleteFileFromQiniu(picName);
				jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES,picName);
				System.out.println("clearImageJob......del:"+picName);
			}
		}catch(Exception e){
		    e.printStackTrace();
		}

	}
}
