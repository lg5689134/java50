package com.imti.service.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imti.bean.Seckill;
import com.imti.mapper.SeckillMapper;
import com.imti.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService{
   //注入秒杀mapper接口
	@Autowired
	SeckillMapper seckillMapper;
	 //注入RedisTemplate模板
	 @Autowired
	 StringRedisTemplate stringRedisTemplate;
    Lock lock=new ReentrantLock();//可重入锁对象
	/**
	 *普通秒杀
	 * @comment 
	 * @version 1.0
	 */
	public void seckillGoods() {
		int countSuc=0; 
		int goodsId=1;
		//1、拿到商品库存
		Seckill seck=seckillMapper.getSeckill(goodsId);
		//2、抢购成功，修改库存
		int goodsNum=seck.getGoodsNum();//商品库存
		if(goodsNum>0){
			seck.setGoodsNum(goodsNum-1);
			 countSuc=seckillMapper.updateSeckill(seck);
			if(countSuc>0){
				System.out.println("抢到iphone12,成功！");
			}else{
				System.out.println("抢购失败！");
			}
		}else{
			System.out.println("该商品已抢完。。");
		}
		
	}
	/**
	  * 乐观锁秒杀
	  * @comment 
	  * @version 1.0
	  */
	public void seckillGoodsVersion() {
		int countSuc=0; 
		int goodsId=1;
		//1、拿到商品库存
		Seckill seck=seckillMapper.getSeckill(goodsId);
		//2、抢购成功，修改库存
		int goodsNum=seck.getGoodsNum();//商品库存
		if(goodsNum>0){
			seck.setGoodsNum(goodsNum-1);
			//调用乐观锁修改库存
			 countSuc=seckillMapper.updateSeckillVersion(seck);
			if(countSuc>0){
				System.out.println("抢到iphone12,成功！");
			}else{
				System.out.println("抢购失败！");
			}
		}else{
			System.out.println("该商品已抢完。。");
		}
		
	}
	
 /**
   * 悲观锁秒杀
   * @comment 
   * @version 1.0
   */
	@Override
    @Transactional
	public void seckillGoodsForUpdate() {
		int countSuc=0; 
		int goodsId=1;
		//1、悲观锁查询商品库存
		Seckill seck=seckillMapper.getSeckillForUpdate(goodsId);
		//2、抢购成功，修改库存
		int goodsNum=seck.getGoodsNum();//商品库存
		if(goodsNum>0){
			seck.setGoodsNum(goodsNum-1);
			 countSuc=seckillMapper.updateSeckill(seck);
			if(countSuc>0){
				System.out.println("抢到iphone12,成功！");
			}else{
				System.out.println("抢购失败！");
			}
		}else{
			System.out.println("该商品已抢完。。");
		}
		
	}
   /**
    * 同步锁秒杀
    * @comment 
    * @version 1.0
    */
	 public  void seckillGoodsSyn(){		 
		 synchronized (this) {//同步锁
			int countSuc=0; 
			int goodsId=1;
			//1、拿到商品库存
			Seckill seck=seckillMapper.getSeckill(goodsId);
			//2、抢购成功，修改库存
			int goodsNum=seck.getGoodsNum();//商品库存
			if(goodsNum>0){
				seck.setGoodsNum(goodsNum-1);
				 countSuc=seckillMapper.updateSeckill(seck);
				if(countSuc>0){
					System.out.println("抢到iphone12,成功！");
				}else{
					System.out.println("抢购失败！");
				}
			}else{
				System.out.println("该商品已抢完。。");
			}
		 }
	 }
   /**
     * 可重入锁Lock秒杀
     * @comment 
     * @version 1.0
     */
	public void seckillGoodsLock() {
		lock.lock();//加锁
		try {
			int countSuc=0; 
			int goodsId=1;
			//1、拿到商品库存
			Seckill seck=seckillMapper.getSeckill(goodsId);
			//2、抢购成功，修改库存
			int goodsNum=seck.getGoodsNum();//商品库存
			if(goodsNum>0){
				seck.setGoodsNum(goodsNum-1);
				 countSuc=seckillMapper.updateSeckill(seck);
				if(countSuc>0){
					System.out.println("抢到iphoneX,成功！");
				}else{
					System.out.println("抢购失败！");
				}
			}else{
				System.out.println("该商品已抢完。。");
			}
			
		} catch (Exception e) {
			e.getMessage();
		}finally{
			lock.unlock();//解锁
		}
		
	}
	/**
	 * redis秒杀
	 */
	public void seckillGoodsRedis() {
		
		//利用redis的单线程特性，增量计算剩余库存
        double goodsSurplusSum=stringRedisTemplate.opsForValue().increment("goodsSum",-1);
        if(goodsSurplusSum>=0){
            System.out.println("抢到iphone12,成功！还剩下："+goodsSurplusSum);
           //自己业务处理……
           // userService.saveList()
            //redis 存放list
          //  stringRedisTemplate.opsForList().rightPush("users", "s"+goodsSurplusSum);
        }else {
            System.out.println("抢到iphoneX,失败！");
        }

	}
	
}