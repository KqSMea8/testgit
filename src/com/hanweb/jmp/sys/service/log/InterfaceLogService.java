package com.hanweb.jmp.sys.service.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.dao.log.InterfaceLogDAO;
import com.hanweb.jmp.sys.entity.log.InterfaceLog;

public class InterfaceLogService {
	
	/**
	 * interfaceLogDAO
	 */
	@Autowired
	InterfaceLogDAO interfaceLogDAO;
	
	/**
	 * 新增接口日志记录
	 * @param interfaceLog interfaceLog
	 * @return boolean
	 */
	public boolean add(InterfaceLog interfaceLog) {
		if(interfaceLog == null){
			return false;
		}
		int iid = interfaceLogDAO.insert(interfaceLog, this.getTableName(interfaceLog.getSiteId()));
		return iid > 0;
	}
	
	/**
	 * 根据id删除
	 * @param ids ids
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException  设定参数 .
	 */
	public boolean removeByIds(String ids, Integer siteId) throws OperationException{
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		boolean isSuccess = interfaceLogDAO.deleteByIds(idList);
		if (!isSuccess) {
			throw new OperationException("删除失败！");
		}
		return true;
	}
	
	/**
	 * 删除接口日志
	 * @param interfaceLogList interfaceLogList
	 * @param isThread isThread
	 * @return boolean
	 * @throws OperationException 设定参数 .
	 */ 
	public boolean removeByList(List<InterfaceLog> interfaceLogList, int isThread) throws OperationException {
		boolean isSuccess = false;
		if (interfaceLogList==null || interfaceLogList.size()<=0) {
			return false;
		}
		StringBuffer idssb = new StringBuffer(100);
		int i = 0;
		String ids = "";
		for(InterfaceLog interfaceLog : interfaceLogList){
			i=i+1;
			if (interfaceLog == null || interfaceLog.getIid() <= 0) {
				continue;
			} 
			idssb.append(",").append(interfaceLog.getIid()); 
			if(i%100 == 0 || i == interfaceLogList.size()){
				if (StringUtil.isNotEmpty(idssb + "") && idssb.length() > 0) {
					ids = idssb.substring(1);
					List<Integer> idList = StringUtil.toIntegerList(ids, ",");
					if (CollectionUtils.isEmpty(idList)) {
						return false;
					}
					isSuccess = interfaceLogDAO.deleteByIds(idList, this.getTableName(interfaceLog.getSiteId()));
					if (!isSuccess) {
						throw new OperationException("清理接口日志线程清理失败！");
					}
				}
			}
		}
		return isSuccess;
	}
	
	/**
	 * 根据接口获取ArrayList
	 * @param interfaceAddress interfaceAddress
	 * @return    设定参数 .
	*/
	public ArrayList<String> getArrByInterface(String interfaceAddress) {
		String [][] s = InterfaceLogConfig.MOD_ARRAY;
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			if (interfaceAddress.equals(s[i][2])) {
				al.add(s[i][0]);
				al.add(s[i][1]);
				al.add(s[i][2]);
				return al;
			}
		}
		return null;
	}
	
	/**
	 * 查询接口记录条数
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public List<InterfaceLog> findClientType(int iid){
		if(iid == 0){ 
			return null;
		}else{
			return interfaceLogDAO.findClientTypeCount(iid);
		}
	}
	
	/**
	 * 根据网站id查找接口记录条数
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Map<Integer, Integer> findInterfaceCountsBySiteId(int iid){
		if(iid == 0){ 
			return null;
		}else{
			return interfaceLogDAO.findInterfaceCountsBySiteId(iid);
		}	
	}
	
	/**
	 * 根据iid获取接口名称
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public List<InterfaceLog> findCountsOfInterface(int iid){
		if(iid == 0){ 
			return null;
		}else{
			return interfaceLogDAO.findCountsOfInterface(iid);
		}	
	}
	
	/**
	 * 增加接口日志表
	 * @param tableName 表名
	 * @return    设定参数 .
	 */
	public boolean addTable(String tableName){
		return interfaceLogDAO.addTable(tableName);
	}
	
	/**
	 * 删除接口日志表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean deleteTable(String tableName){
		return interfaceLogDAO.deleteTable(tableName);
	}
	
	/**
	 * 获得不同网站的接口日志表名
	 * @param iid 网站Id
	 * @return tableName
	 */
	public String getTableName(Integer iid){
		String tableName = "jmp_interface_log" + iid;
		return tableName;
	}
	
	/**
	 * 获取某一时间点前的接口日志
	 * @param endTime  某一时间点
	 * @param siteId   网站id
	 * @return List<InterfaceLog>
	 */
	public List<InterfaceLog> findByEndTime(String endTime, Integer siteId){
		return interfaceLogDAO.findByEndTime(endTime, siteId);
	}
	
}