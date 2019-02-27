package com.hanweb.complat.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.complat.dao.AccessLogDAO;
import com.hanweb.complat.entity.AccessLog;

public class AccessLogService {
	@Autowired
	private AccessLogDAO accessLogDAO;
	
	private int keepNumber = 3; //保留最后几次的日志记录
	
	/**
	 * 记录日志
	 * @param log
	 * @return
	 */
	public boolean record(AccessLog log) {
		boolean isSuccess = false;
		this.deleteExpiredLogs(log.getLoginName());
		int iid = accessLogDAO.insert(log);
		if (iid > 0) {
			isSuccess = true;
		}
		return isSuccess;
	}
	
	/**
	 * 删除过期日志
	 * @param loginName
	 */
	private void deleteExpiredLogs(String loginName) {
		int count = accessLogDAO.findCount(loginName);
		if (count >= keepNumber) {
			accessLogDAO.deleteByMinId(loginName);
		}
	}
}
