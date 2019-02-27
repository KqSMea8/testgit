package com.hanweb.jmp.constant;

import java.io.Serializable;

public class InfoConfig implements Serializable {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）.
	 *
	 * @since zx Ver 1.0 .
	 */
	
	private static final long serialVersionUID = -9081197047547029488L;
	/**
	 * 小图宽度
	 */
	private int minWidth = 176;
	/**
	 * 小图高度
	 */
	private int minHeight = 132;
	/**
	 * 中图宽度
	 */
	private int middleWidth = 290;
	/**
	 * 中图高度
	 */
	private int middleHeight = 218;
	
	/**
	 * 大图宽度
	 */
	private int bigWidth = 600;
	/**
	 * 大图高度
	 */
	private int bigHeight = 338;
	
	public int getMinWidth() {
		return minWidth;
	}
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}
	public int getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}
	public int getMiddleWidth() {
		return middleWidth;
	}
	public void setMiddleWidth(int middleWidth) {
		this.middleWidth = middleWidth;
	}
	public int getMiddleHeight() {
		return middleHeight;
	}
	public void setMiddleHeight(int middleHeight) {
		this.middleHeight = middleHeight;
	}
	public int getBigWidth() {
		return bigWidth;
	}
	public void setBigWidth(int bigWidth) {
		this.bigWidth = bigWidth;
	}
	public int getBigHeight() {
		return bigHeight;
	}
	public void setBigHeight(int bigHeight) {
		this.bigHeight = bigHeight;
	} 
}
