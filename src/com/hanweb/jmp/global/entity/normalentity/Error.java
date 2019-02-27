package com.hanweb.jmp.global.entity.normalentity;

/**
 * 接口错误返回值定义
 * @author hq
 */
public class Error {
	
	/**
	 * 错误描述： 
	 * 1 版本不支持 
	 * 2 参数错误 
	 * 3官方微博未授权 
	 * 4 网络连接错误 
	 * 5 服务器异常
	 */
	private String errorCode;

	/**
	 * operate
	 */
	private String operate;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
	
}