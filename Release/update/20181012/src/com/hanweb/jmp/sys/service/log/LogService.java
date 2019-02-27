package com.hanweb.jmp.sys.service.log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil; 
import com.hanweb.complat.dao.GroupDAO;
import com.hanweb.complat.entity.Group;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.sys.controller.log.LogConfigFormBean;
import com.hanweb.jmp.sys.dao.log.LogConfigDAO;
import com.hanweb.jmp.sys.dao.log.LogDAO;
import com.hanweb.jmp.sys.entity.log.Log;
import com.hanweb.support.controller.CurrentUser;

public class LogService {
	
	/**
	 * logDAO
	 */
	@Autowired
	private LogDAO logDAO;

	/**
	 * logConfigDAO
	 */
	@Autowired
	private LogConfigDAO logConfigDAO; 
	
	@Autowired
	private GroupDAO groupDAO;
	
	/**
	 * siteid
	 */
	private int siteid=0;

	/**
	 * 新增日志
	 * @param moduleId moduleId
	 * @param funcId funcId
	 * @param content content
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
    public boolean add(Integer moduleId, Integer funcId, String content) {
		try {
			Integer islog = logConfigDAO.checkIsLog(moduleId, funcId);
			if (islog == null) {
				return false;
			}
			if (islog == 0) {// 不需要写入日志,则直接返回true
				return true;
			}
			CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
			Log log = new Log();
			log.setIp(ControllerUtil.getIp());
			log.setContent(getOprContent(moduleId, funcId, content));
			log.setFuncId(funcId);
			log.setModuleId(moduleId);
			if(currentUser!=null){
				log.setSiteId(currentUser.getSiteId());
				log.setUserId(currentUser.getIid());
				log.setUserName(currentUser.getName());
				Group group = groupDAO.findByIid(NumberUtil.getInt(currentUser.getGroupId()));
				if(group != null){
				    log.setGroupName(group.getName());
				} else {
				    log.setGroupName("未知机构");
				}
			}else if(siteid>0){
				log.setSiteId(siteid);
			}
			log.setOprTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
			return NumberUtil.getInt(logDAO.insert(log)) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 组织日志内容
	 * @param moduleId moduleId
	 * @param funcId funcId
	 * @param content content
	 * @return String
	 */
	private String getOprContent(Integer moduleId, Integer funcId, String content) {
		String oprContent = "";
		String funcname = getArrName(LogConfig.OPR_ARRAY, funcId);
		String modulename = getArrName(LogConfig.MOD_ARRAY, moduleId);
		if (StringUtil.isNotEmpty(funcname) && StringUtil.isNotEmpty(modulename)) {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			String name ="";
			if(currentUser!=null){
				name = currentUser.getName();
			} 
			if (StringUtil.isEmpty(content)) {
				oprContent = name + funcname + modulename;
			} else {
				oprContent = name + funcname + modulename + "【" + content + "】";
			}
		}
		return oprContent;
	}

	/**
	 * getArrName
	 * @param str
	 * @param id
	 * @return
	 */
	public String getArrName(String[][] str, Integer id) {
		for (int i = 0; i < str.length; i++) {
			if (id == NumberUtil.getInt(str[i][0])) {
				return str[i][1];
			}
		}
		return "";
	}

	/**
	 * 删除操作日志
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 设定参数 .
	 */ 
	public boolean removeByIds(String ids) throws OperationException {
		boolean isSuccess = logDAO.deleteByIds(StringUtil.toIntegerList(ids, ","));
		if (!isSuccess) {
			throw new OperationException("删除操作日志失败!");
		}
		return isSuccess;
	}

	/**
	 * 删除某站点下日志
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException 设定参数 .
	 */
	public boolean clean(Integer siteId) throws OperationException {
		boolean isSuccess = NumberUtil.getInt(logDAO.clean(siteId)) > 0;
		if (!isSuccess) {
			throw new OperationException("删除失败！");
		}
		return isSuccess;
	}

	/**
	 * 清空所有日志
	 * @return boolean
	 * @throws OperationException 设定参数 .
	 */
	public boolean clean() throws OperationException {
		logDAO.clean();
		return true;
	}

	/**
	 * 日志设置集合
	 * @return List<LogConfigFormBean>
	 */
	public List<LogConfigFormBean> findLogConfig() {
		List<com.hanweb.jmp.sys.entity.log.LogConfig> moduleList = logConfigDAO.findModuleName();
		List<LogConfigFormBean> formBeanList = new ArrayList<LogConfigFormBean>();
		for (com.hanweb.jmp.sys.entity.log.LogConfig config : moduleList) {
			LogConfigFormBean formBean = new LogConfigFormBean();
			formBean.setModuleName(config.getModuleName());
			formBean.setModuleId(config.getModuleId());
			List<com.hanweb.jmp.sys.entity.log.LogConfig> funcNameList = logConfigDAO.findFuncName(config.getModuleId());
			formBean.setLogConfigList(funcNameList);
			formBeanList.add(formBean);
		}
		return formBeanList;
	}

	/**
	 * 修改日志权限
	 * @param modAndFuncId modAndFuncId 
	 * @return boolean
	 * @throws OperationException 设定参数 .
	 */
	public boolean modifyIsLog(String modAndFuncId) throws OperationException {
		String[] modIds = modAndFuncId.split(",");
		boolean isSuccess = false;
		for (String modFunc : modIds) {
			String[] mf = modFunc.split("-");
			isSuccess = logConfigDAO.updateIsLog(NumberUtil.getInt(mf[0]), NumberUtil.getInt(mf[1]));
			if (!isSuccess) {
				throw new OperationException("修改日志权限失败!");
			}
		}
		return isSuccess;
	}

	/**
	 * 重置日志权限
	 * @return boolean
	 */
	public boolean modifyIsLog() {
		return logConfigDAO.updateIsLog();
	}
	
	/**
	 * 查找每个功能模块总的操作次数
	 * @param moduleId 功能ID
	 * @param siteId   网站ID
	 * @return int
	 */
	public int findCountByModuleId(int moduleId, int siteId){
		return logDAO.findCountByModuleId(moduleId, siteId);
	}
	
	/**
	 * 查找每种功能总的操作次数
	 * @param funcId   功能ID
	 * @param siteId   网站ID
	 * @return int
	 */
	public int findCountByFuncId(int funcId, int siteId){
		return logDAO.findCountByFuncID(funcId, siteId);
	}

	public int getSiteid() {
		return siteid;
	}

	public void setSiteid(int siteid) {
		this.siteid = siteid;
	} 
	
}