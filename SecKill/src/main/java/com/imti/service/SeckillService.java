package com.imti.service;

public interface SeckillService {
/**
 *普通秒杀
 * @comment 
 * @version 1.0
 */
 public void seckillGoods();
 /**
  * 乐观锁秒杀
  * @comment 
  * @version 1.0
  */
  public void seckillGoodsVersion();
  
  /**
   * 悲观锁秒杀
   * @comment 
   * @version 1.0
   */
   public void seckillGoodsForUpdate();
   
   /**
    * 同步锁秒杀
    * @comment 
    * @version 1.0
    */
    public void seckillGoodsSyn();
    /**
     * 可重入锁Lock秒杀
     * @comment 
     * @version 1.0
     */
     public void seckillGoodsLock();
     
     /**
      * redis秒杀
      * @comment 
      * @version 1.0
      */
      public void seckillGoodsRedis();
      
}
