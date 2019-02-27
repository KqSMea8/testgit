package com.hanweb.jmp.sys.service.feedback;

import java.util.ArrayList; 

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.global.entity.normalentity.Error;
import com.hanweb.jmp.sys.dao.feedback.FeedBackDAO;
import com.hanweb.jmp.sys.entity.feedback.FeedBack;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.global.entity.jsonentity.FeedBackInfo;
import com.hanweb.jmp.global.entity.jsonentity.FeedBackJSON;

public class FeedBackService {
	
	/**
	 * feedBackDao
	 */
	@Autowired
	private FeedBackDAO feedBackDao;
	
	/**
	 * logService
	 */
	@Autowired
	private LogService logService;
 
	/**
	 * 反馈信息提交
	 * @param feedBack 反馈信息实体
	 * @return    设定参数 .
	*/
	public Integer addFeedBack(FeedBack feedBack) {  
		// 插入数据库  
		return feedBackDao.insert(feedBack); 
	}

	/**
	 * 获取反馈信息
	 * @param deviceCode deviceCode
	 * @param userLoginName userLoginName
	 * @param sinceTime sinceTime
	 * @param nextTime nextTime
	 * @param linages linages
	 * @return String
	 */
	public String getFeedBackJSON(String deviceCode, String userLoginName, String sinceTime, 
			                      String nextTime, String linages){
		Error error = new Error();
		if (StringUtil.isEmpty(deviceCode)) {
			error.setErrorCode("2");
			return JsonUtil.objectToString(error);
		}
		Long since = NumberUtil.getLong(sinceTime);
		Long next = NumberUtil.getLong(nextTime);
		if (since != 0L && next != 0L) {
			error.setErrorCode("2");
			return JsonUtil.objectToString(error);
		}
		int linage = NumberUtil.getInt(linages, 15);
		if (linage > 100) {
			linage = 100;
		}
		List<FeedBack> list = feedBackDao.findFeedBackInfo(deviceCode, userLoginName, since, next, linage);
		int len = list.size();
		FeedBackJSON feedBackJson = new FeedBackJSON();
		if (next != 0L || (next == 0L && since == 0L)) {
			if (len == (linage + 1)) {
				feedBackJson.setHaveMore("1");
				len = len - 1;
			} else {
				feedBackJson.setHaveMore("0");
			}
		} else {
			if (len >= (linage + 1)) {
				feedBackJson.setHaveMore("1");
			} else {
				feedBackJson.setHaveMore("0");
			}
		}
		ArrayList<FeedBack> reAl = new ArrayList<FeedBack>();
		for (int i = 0; i < len; i++) {
			reAl.add(list.get(i));
		}
		feedBackJson.setFeedBack(reAl);
		return JsonUtil.objectToString(feedBackJson);
	}

	/**
	 * 获取反馈信息
	 * @param deviceCode deviceCode
	 * @param userLoginName userLoginName
	 * @return String
	 */
	public String getFeedBackJSON(String deviceCode, String userLoginName) {
		Error error = new Error();
		if (StringUtil.isEmpty(deviceCode)) {
			error.setErrorCode("2");
			return JsonUtil.objectToString(error);
		}
		List<FeedBack> list = feedBackDao.findFeedBackInfo(deviceCode, userLoginName);
		List<FeedBack> infolist = new ArrayList<FeedBack>();
		for (FeedBack info : list) {
			infolist.add(info);
		}
		return JsonUtil.objectToString(infolist);
	}

	/**
	 * 删除
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean removeByIds(String ids) throws OperationException {
		boolean isSuccess = feedBackDao.deleteByIds(StringUtil.toIntegerList(ids, ","));
		if (!isSuccess) {
			throw new OperationException("删除分类失败!");
		}
		logService.add(LogConfig.modfeedback, LogConfig.oprremove, "");
		return isSuccess;
	}

	/**
	 * 根据iid获取实体
	 * @param iid iid
	 * @return FeedBack
	 */
	public FeedBack findByIid(Integer iid) {
		return feedBackDao.queryForEntityById(iid);
	}

	/**
	 * 修改
	 * @param iid iid
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean modify(Integer iid) throws OperationException {
		boolean isSuccess = feedBackDao.update(iid);
		if (!isSuccess) {
			throw new OperationException("修改失败!");
		}
		logService.add(LogConfig.modfeedback, LogConfig.oprmodify, "");
		return isSuccess;
	}
	
	/**
	 * 查找反馈信息
	 * @param siteid siteid
	 * @param pagesize pagesize
	 * @param time time
	 * @param type type
	 * @param deviceCode deviceCode
	 * @param loginname loginname
	 * @return    设定参数 .
	 */
	public List<FeedBackInfo> findInfoList(Integer siteid, Integer pagesize, Date time, 
			   Integer type, String deviceCode, String loginname){
		if(NumberUtil.getInt(pagesize) <= 0){
			pagesize = 15;
		}
		List<FeedBack> list = feedBackDao.findInfoList(siteid, pagesize, time, type, deviceCode, loginname);
		return convertList(list);
	}
	
	/**
	 * 
	 * @param list list
	 * @return    设定参数 .
	 */
	private List<FeedBackInfo> convertList(List<FeedBack> list){
		List<FeedBackInfo> covertlist =new ArrayList<FeedBackInfo>();
		if(CollectionUtils.isEmpty(list)){
			return covertlist;
		}
		FeedBackInfo feedbackEntity=null;
		String createtime="";
		for (FeedBack info : list) {
			feedbackEntity = new FeedBackInfo();
			feedbackEntity.setIid(StringUtil.getString(info.getIid()));
			feedbackEntity.setSiteid(StringUtil.getString(info.getSiteId()));
			feedbackEntity.setContent(StringUtil.getString(info.getContent()));
			feedbackEntity.setContact(StringUtil.getString(info.getContact())); 
			if(info.getCreateTime() == null){
				createtime = "";
			}else{
				createtime = StringUtil.getString(info.getCreateTime().getTime());
			}
			feedbackEntity.setCreatetime(createtime); 
			feedbackEntity.setLoginname(StringUtil.getString(info.getLoginName()));
			covertlist.add(feedbackEntity);
		}
		return covertlist;
	}
	
}