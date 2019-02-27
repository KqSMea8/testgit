package com.hanweb.jmp.pack.entity;

public class ErrorEntity {
	
	/**
      	错误描述：
        1	版本不支持
		2	参数错误
		3	官方微博未授权
		4	网络连接错误
		5	服务器异常
     */
    private String errorCode;
    
    /**
     * operate 操作错误
     */
    private String operate;
    
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getOperate() {
		return this.operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}
    
}