package com.hanweb.jmp.apps.service.survey;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.entity.OutsideUser;
import com.hanweb.complat.service.OutsideUserService;
import com.hanweb.jmp.apps.dao.survey.UserVoteCountDAO;
import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.entity.survey.UserVoteCount;

public class UserVoteCountService {
	
	/**
	 * userVoteCountDAO
	 */
	@Autowired
	private UserVoteCountDAO userVoteCountDAO;
	
	/**
	 * outsideUserService
	 */
	@Autowired
	private OutsideUserService outsideUserService;
	
	/**
	 * surveyService
	 */
	@Autowired
	private SurveyService surveyService;
	
	/**
	 * 根据网站id和调查id获取实体
	 * @param siteId
	 * @param surveyId
	 * @param uuid
	 * @return
	 */
	public UserVoteCount findBySiteIdAndSurveyId(int siteId, int surveyId, String uuid){
		return userVoteCountDAO.findBySiteIdAndSurveyId(siteId, surveyId, uuid);
	}
	
	/**
	 * 检查用户是否可以投票
	 * @param uuid
	 * @param loginname
	 * @return
	 */
	public boolean checkUserVoteCount(Integer siteId, Integer surveyId, String uuid, String loginName){
		boolean isCanVote = false;
		UserVoteCount userVoteCount = findBySiteIdAndSurveyId(siteId, surveyId, uuid);
		Survey Survey = surveyService.findByIid(surveyId);
		int userVoteLimit = Survey.getLimitCount();
		OutsideUser outsideUser = null;
		if(StringUtil.isNotEmpty(loginName)){
			outsideUser = outsideUserService.findByLoginName(loginName, siteId);
		}
		int userId = 0;
		if(outsideUser!=null){
			userId = outsideUser.getIid();
		}
		if(userVoteCount==null){
			userVoteCount = new UserVoteCount();
			userVoteCount.setUuid(uuid);
			userVoteCount.setSiteId(siteId);
			userVoteCount.setSurveyId(surveyId);
			userVoteCount.setCount(0);
			userVoteCount.setUserId(userId);
			add(userVoteCount);
			isCanVote = true;
		}else{
		    int usserHasVoteNum = userVoteCount.getCount();
		    if(usserHasVoteNum>=userVoteLimit){
		    	isCanVote =  false;
		    }else{
		    	isCanVote =  true;
		    }
		}
		return isCanVote;
	}
	
	/**
	 * 更新实体
	 * @param userVoteCount
	 * @return
	 */
    public boolean update(UserVoteCount userVoteCount){
		return userVoteCountDAO.update(userVoteCount);
	}
    
    /**
     * 新增实体
     * @param userVoteCount
     * @return
     */
    public boolean add(UserVoteCount userVoteCount){
		return userVoteCountDAO.insert(userVoteCount)>0;
	}
    
}