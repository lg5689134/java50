package com.imti.bean;

import java.io.Serializable;
public class Seckill implements Serializable{
	private static final long serialVersionUID = -235695333399115974L;
	/*
	 * 秒杀id
	 */
	Integer seckId;
	/*
	 * 会员id
	 */
	private Integer membId;
	/*
	 * 商品名称
	 */
	private String goodsName;
	/*
	 * 商品库存
	 */
	private Integer goodsNum;
	/*
	 * 版本号，用于乐观锁
	 */
	private Integer version;
	/*
	 * 创建时间
	 */
	private String createTime;
	public Integer getSeckId() {
		return seckId;
	}
	public void setSeckId(Integer seckId) {
		this.seckId = seckId;
	}
	public Integer getMembId() {
		return membId;
	}
	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
