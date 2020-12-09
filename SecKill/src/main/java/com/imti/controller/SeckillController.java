package com.imti.controller;

import org.apache.tomcat.jni.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imti.bean.Seckill;
import com.imti.mapper.SeckillMapper;
import com.imti.service.SeckillService;

@Controller
public class SeckillController {
	
 //注入service
 @Autowired
 SeckillService seckillService;
 //注入RedisTemplate模板
 @Autowired
 StringRedisTemplate stringRedisTemplate;
 @Autowired
 SeckillMapper seckillMapper;
 
 @RequestMapping("/redis")
 @ResponseBody
 public  String seckillRedis() throws InterruptedException {
	 int goodsId=1;
	 Seckill seck=seckillMapper.getSeckill(goodsId);
	 Integer goodsSum=0;
	 if(seck!=null && seck.getGoodsNum()!=null){
		  goodsSum=seck.getGoodsNum();
	 }
    //把商品秒杀的库存存放redis中
	 stringRedisTemplate.opsForValue().set("goodsSum",goodsSum+"");
     System.out.println("总库存量："+goodsSum);

	 for (int i = 0; i < 200; i++) {
		Thread t=new Thread(new Runnable() {//创建一个线程
		public void run() {
			//.redis单线程特性，increment方法来实现
			seckillService.seckillGoodsRedis();
		  }
	   });
	  t.start();//启动一个线程
	 }
	 return "success";
 }
 

 @RequestMapping("/seckill")
 @ResponseBody
 public  String seckillSqlLock() throws InterruptedException {
	 for (int i = 0; i < 200; i++) {
		Thread t=new Thread(new Runnable() {//创建一个线程
		public void run() {
			//普通秒杀实现，提示全部抢购成功，但是还有商品库存，没有一个失败，程序出问题
			//seckillService.seckillGoods();
			//乐观锁实现秒杀version，有成功有失败，程序没问题，但是因并发多个线程都修改成功，
			//发出抢购请求较早的用户可能抢不到，导致后来的请求抢到了
			//seckillService.seckillGoodsVersion();
			//悲观锁实现秒杀forupdate，注意必须使用事务，否则锁无法释放，程序没问题
			//seckillService.seckillGoodsForUpdate();
			//线程同步锁实现秒杀,ok
			//seckillService.seckillGoodsSyn();
			//线程lock锁实现秒杀,ok
			seckillService.seckillGoodsLock();
		  }
	   });
	  t.start();//启动一个线程
	 }
	 return "success";
 }
 
}
