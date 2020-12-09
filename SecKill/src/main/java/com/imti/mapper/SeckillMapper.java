package com.imti.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imti.bean.Seckill;

public interface SeckillMapper {
  /**
   * @comment 查询商品信息
   * @param id
   * @return
   * @version 1.0
   */
  @Select("select * from seckill where seck_id=#{id}")
  @Results(id="SeckillResult",value={
  		@Result(id=true,column="seck_id",property="seckId"),
  		@Result(column="goods_name",property="goodsName"),
  		@Result(column="goods_num",property="goodsNum"),
  		@Result(column="create_time",property="createTime")
  })
  public Seckill getSeckill(Integer id);
 
  /**
   * @comment 悲观锁查询商品信息
   * @param id
   * @return
   * @version 1.0
   */
  @Select("select * from seckill where seck_id=#{id} FOR UPDATE ")
  @Results(value={
  		@Result(id=true,column="seck_id",property="seckId"),
  		@Result(column="goods_name",property="goodsName"),
  		@Result(column="goods_num",property="goodsNum"),
  		@Result(column="create_time",property="createTime")
  })
  public Seckill getSeckillForUpdate(Integer id);
  
  /**
   * @comment  修改商品库存
   * @param seckill
   * @return
   * @version 1.0
   */
  @Update("UPDATE `seckill` SET `goods_num`=#{goodsNum} WHERE `seck_id`=#{seckId}")  
  public int updateSeckill(Seckill seckill);
  
  
  /**
   * @comment  乐观锁修改库存
   * @param seckill
   * @return
   * @version 1.0
   */
  @Update("UPDATE seckill SET goods_num = #{goodsNum},VERSION=#{version}+1  WHERE seck_id =#{seckId} AND VERSION =#{version}")  
  public int updateSeckillVersion(Seckill seckill);
}
