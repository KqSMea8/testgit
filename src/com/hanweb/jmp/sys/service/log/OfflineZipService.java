package com.hanweb.jmp.sys.service.log;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.sys.dao.log.OfflineZipDAO;
import com.hanweb.jmp.sys.entity.log.OfflineZip;

public class OfflineZipService {
	
	/**
	 * offlineZipDAO
	 */
	@Autowired
	private OfflineZipDAO offlineZipDAO;
	
	/**
	 * 新增记录
	 * @param offlinezip  offlinezip
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean add(OfflineZip offlinezip) throws OperationException {
		if (offlinezip == null) {
			return false;
		} 
		int iid = offlineZipDAO.insert(offlinezip);
		return iid > 0;
	}

	/**
	 * 修改记录
	 * @param offlinezip  offlinezip
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean modify(OfflineZip offlinezip) throws OperationException {
		if (offlinezip == null || NumberUtil.getInt(offlinezip.getIid()) == 0) {
			return false;
		}
		boolean isSuccess = false;
		isSuccess = offlineZipDAO.update(offlinezip);
		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		} 
		return isSuccess;
	} 
	
	/**
	 * 根据栏目id和网站id获取离线线程扫描记录
	 * @param colId colId
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public OfflineZip findByColId(int colId, int siteId){
		return offlineZipDAO.findOfflineZipByColid(colId, siteId);
		
	}
	
}