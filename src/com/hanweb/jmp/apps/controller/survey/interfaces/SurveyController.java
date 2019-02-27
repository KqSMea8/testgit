package com.hanweb.jmp.apps.controller.survey.interfaces;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.apps.entity.survey.Answer;
import com.hanweb.jmp.apps.entity.survey.Question;
import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.entity.survey.SurveyAnswer;
import com.hanweb.jmp.apps.entity.survey.SurveyResult;
import com.hanweb.jmp.apps.entity.survey.UserVoteCount;
import com.hanweb.jmp.apps.service.survey.AnswerService;
import com.hanweb.jmp.apps.service.survey.QuestionService;
import com.hanweb.jmp.apps.service.survey.SurveyAnswerService;
import com.hanweb.jmp.apps.service.survey.SurveyResultService;
import com.hanweb.jmp.apps.service.survey.SurveyService;
import com.hanweb.jmp.apps.service.survey.UserVoteCountService;
import com.hanweb.jmp.constant.Configs;


@Controller
@RequestMapping("interfaces/survey")
public class SurveyController {
	
	/**
	 * colService
	 */
	@Autowired
	QuestionService questionService;
	
	@Autowired
	SurveyService surveyService;
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	SurveyResultService surveyResultService;
	
	@Autowired
	SurveyAnswerService surveyAnswerService;
	
	@Autowired
	UserVoteCountService userVoteCountService;
	
	/**
	 * 主页显示
	 * @param siteId
	 * @param surveyId
	 * @param loginname
	 * @param uuid
	 * @return
	 */
	@RequestMapping("index")
	@ResponseBody
	public String showMainPage(Integer siteId, Integer surveyId ,String loginname, String uuid){
	siteId = NumberUtil.getInt(siteId);
	surveyId = NumberUtil.getInt(surveyId);
	loginname = StringUtil.getString(loginname);
	uuid = StringUtil.getString(uuid);
	String domain = Configs.getConfigs().getJmpUrl();
	String 	mainPage = "";
	Survey surver = surveyService.findByIid(NumberUtil.getInt(surveyId));
	//是否公开    1公开,2不公开
	int isOpen = NumberUtil.getInt(surver.getIsOpen());
	//展现类型
	int showtype = NumberUtil.getInt(surver.getShowType());
	//调查标题
	String title=  StringUtil.getString(surver.getName());
	//调查简介
	String abs =  StringUtil.getString(surver.getAbs());
	//调查首图
	String bgPath =  domain+surver.getFirstPicPath();
	//调查是否结束
	int isend = 0;
	long endtime = surver.getEndTime().getTime();
	long nowtime = new Date().getTime();
	long minus = nowtime-endtime;
	if(minus>0){
		isend = 1;
	}else{
		isend = 0;
	}
	//读取九宫格模板
	if(showtype==1){
		mainPage = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				 + "/resources/jmp/survey/html/sudoku.html"), "utf-8");
	}else{
	//读取列表模板	
		mainPage = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				 + "/resources/jmp/survey/html/list.html"), "utf-8");
	}
	List<Question> questions = questionService.findBySurveyId(siteId, surver.getIid());
	Question question  = null;
	String replaceDivAll = "";
	if(questions!=null&&questions.size()>0){
	  //调查进行中	
	  if(isend!=1){
		  String quesDiv = "";
		  if(showtype==1){
			quesDiv = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
					+ "/resources/jmp/survey/txt/quesdiv.txt"), "utf-8");
		  }else{
			quesDiv = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
					+ "/resources/jmp/survey/txt/quesdiv2.txt"), "utf-8");  
		  }
		for(int i=0;i<questions.size();i++){
			String quesDiv2 = quesDiv;
			question =  questions.get(i);
			SurveyResult surveyResult = surveyResultService.findByQuestionId(siteId, surveyId, question.getIid());
			//题目类型
			int quesType = question.getType();
			//总头票数
			int sum = 0;
			if(surveyResult!=null){
				sum = NumberUtil.getInt(surveyResult.getSum());
			}
			//是否必填
			int iswrite = NumberUtil.getInt(question.getIsWrite());
			//是否展示
			int isshow = NumberUtil.getInt(question.getIsShow());
			//题目标题
			String quesTitle = question.getName();
			//获取选项集合
			List<Answer> answerList = answerService.findByQuestionId(siteId, question.getIid());
			int size = 0;
			if(answerList!=null&&answerList.size()>0){
				size = answerList.size();
			}else if(quesType!=3){
				//如果该问题下没有选项(非简答)，直接跳过
				continue;
			}
			if(isshow==1){
				//如果该问题不显示，直接跳过
				continue;
			}else{	
					//九宫格页面{body}替换
					if(showtype==1){
						//简答题
						if(quesType==3){
							String textHtml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
							                + "/resources/jmp/survey/txt/textarea.txt"), "utf-8");
							textHtml = textHtml.replace("{quesTitle}", quesTitle);
							textHtml = textHtml.replace("{show2}", "none");
							textHtml = textHtml.replace("{index}", i+1+"");
							quesDiv2 = quesDiv2.replace("{body}", textHtml);
							quesDiv2 = quesDiv2.replace("{value1}", question.getType()+"");
							quesDiv2 = quesDiv2.replace("{value2}", question.getIid()+"");
							quesDiv2 = quesDiv2.replace("{value3}", question.getIsWrite()+"");
							quesDiv2 = quesDiv2.replace("{quesTitle}", quesTitle);
					
						}else{
							//获取九宫格模板html
							String sodokuHtmlContent = answerService.organizeSudokuHtml(size, answerList, domain, quesType, isend, i+1);
							quesDiv2 = quesDiv2.replace("{body}", sodokuHtmlContent);
							quesDiv2 = quesDiv2.replace("{value1}", question.getType()+"");
							quesDiv2 = quesDiv2.replace("{value2}", question.getIid()+"");
							quesDiv2 = quesDiv2.replace("{value3}", question.getIsWrite()+"");
							quesDiv2 = quesDiv2.replace("{answernum}", answerList.size()+"");
							quesDiv2 = quesDiv2.replace("{quesTitle}", quesTitle);
							if(quesType==1||(quesType==2&&isend==1)){
								mainPage = mainPage.replace("{show}", "none");
							}
						}
						String quesTypeName = "";
						if(quesType==1){
							quesTypeName = "单选题";
						}else if(quesType==2){
							quesTypeName = "多选题";
						}else{
							quesTypeName = "简答题";
						}
						quesDiv2 = quesDiv2.replace("{quesType}", quesTypeName);
					}else if(showtype==2){
						//列表版页面{body}替换
						if(quesType==3){
							//简答题
							String textHtml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
									        + "/resources/jmp/survey/txt/textarea.txt"), "utf-8");
							textHtml = textHtml.replace("{quesTitle}", quesTitle);
							textHtml = textHtml.replace("{index}", i+1+"");
							textHtml = textHtml.replace("{show2}", "none");
							quesDiv2 = quesDiv2.replace("{body}", textHtml);
							quesDiv2 = quesDiv2.replace("{value1}", question.getType()+"");
							quesDiv2 = quesDiv2.replace("{value2}", question.getIid()+"");
							quesDiv2 = quesDiv2.replace("{value3}", question.getIsWrite()+"");
							quesDiv2 = quesDiv2.replace("{quesTitle}", quesTitle);
						}else{
							//获得列表版html内容
							String listHtmlContent = answerService.organizeListHtml(size, answerList, domain, quesType, isend, i+1);
							quesDiv2 = quesDiv2.replace("{body}", listHtmlContent);
							quesDiv2 = quesDiv2.replace("{value1}", question.getType()+"");
							quesDiv2 = quesDiv2.replace("{value2}", question.getIid()+"");
							quesDiv2 = quesDiv2.replace("{value3}", question.getIsWrite()+"");
							quesDiv2 = quesDiv2.replace("{answernum}", answerList.size()+"");
							quesDiv2 = quesDiv2.replace("{quesTitle}", quesTitle);
							if(quesType==1||(quesType==2&&isend==1)){
								mainPage = mainPage.replace("{show}", "none");
							}
						}
						String quesTypeName = "";
						if(quesType==1){
							quesTypeName = "单选题";
						}else if(quesType==2){
							quesTypeName = "多选题";
						}else{
							quesTypeName = "简答题";
						}
						quesDiv2 = quesDiv2.replace("{quesType}", quesTypeName);
					}
			}
			quesDiv2 = quesDiv2.replace("{index}" , (i+1)+"");
			replaceDivAll += quesDiv2;
		}
		
	  }else{
		  //调查结束了
		  for(int i=0;i<questions.size();i++){
			    question = questions.get(i);
			    //题目类型
				int quesType = question.getType();
				 //题目标题
				String quesTitle = question.getName();
				//是否展示
				int isshow = NumberUtil.getInt(question.getIsShow());
				//简答题没结果
				if(quesType==3){
					continue;
				}
			    SurveyResult surveyResult = surveyResultService.findByQuestionId(siteId, surveyId, question.getIid());
			    //总头票数
				int sum = 0;
				if(surveyResult!=null){
					sum = NumberUtil.getInt(surveyResult.getSum());
				}
				//获取选项集合
				List<Answer> answerList = answerService.findByQuestionId(siteId, question.getIid());
				if(isshow==1){
					continue;
				}
				if((answerList==null||answerList.size()<=0)&&quesType!=3){
					continue;
				}
				String resultHtml = answerService.organizeResultHtml(answerList, domain, sum);
				resultHtml = resultHtml.replace("{title}", quesTitle);
				replaceDivAll+= resultHtml;
		  }
	  }	
	}
	 String uniquecode = new Date().getTime()+"";
	 String tokenuuid = uniquecode+"318qwe"+uuid;
	 tokenuuid=md5(tokenuuid);
	 mainPage = mainPage.replace("{uniquecode}", uniquecode);
	 mainPage = mainPage.replace("{tokenuuid}", tokenuuid);
	 mainPage = mainPage.replace("{body}", replaceDivAll);
	 mainPage = mainPage.replace("{sysPath}", domain);
	 mainPage = mainPage.replace("{bgPath}", bgPath);
	 mainPage = mainPage.replace("{isend}", isend+"");
	 mainPage = mainPage.replace("{nums}", questions.size()+"");
	 mainPage = mainPage.replace("{title}", title).replace("{abs}", abs);
	 mainPage = mainPage.replace("{siteId}", siteId+"");
	 mainPage = mainPage.replace("{surveyId}", surveyId+"");
	 mainPage = mainPage.replace("{loginname}", loginname);
	 mainPage = mainPage.replace("{uuid}", uuid);
	 return mainPage;
	}
	
	/**
	 * 调查结果确定
	 * @param siteid
	 * @param surveyid
	 * @param submitResult
	 * @param loginname
	 * @param uuid
	 * @return
	 */
	@RequestMapping("submitresult")
	@ResponseBody
	public String submitResult(Integer siteid, Integer surveyid, String submitResult, String loginname, String uuid){
		String returnMessage = "";
		boolean isCanVote = userVoteCountService.checkUserVoteCount(siteid, surveyid, uuid, loginname);
	    if(isCanVote){
		HashMap<String,Object> ret = new HashMap<String,Object>();
		List<HashMap<String,String>> countList=  new ArrayList<HashMap<String,String>>();
		boolean isSuccess = true;
		try{
			String[] results = StringUtil.split(submitResult, "#");
			for(String re : results){
				String[] eachQuesInfo = StringUtil.split(re, "_");
				int quesid = 0;
				int quesType = 0;
				List<Integer> idsList = null;
				String textContent = "";
				for(int i=0;i<eachQuesInfo.length;i++){
					quesid = NumberUtil.getInt(eachQuesInfo[0]);
					quesType = NumberUtil.getInt(eachQuesInfo[1]);
					}
				if(quesType!=3){
					idsList = StringUtil.toIntegerList(eachQuesInfo[2]);
				}else{
					textContent = StringUtil.getString(eachQuesInfo[2]);
				}
				
				SurveyResult surveyResult = surveyResultService.findByQuestionId(siteid, surveyid, quesid);
				if(quesType==1){
					isSuccess = isSuccess&&answerService.modifyAnswerCount(idsList);
					if(surveyResult==null){
						surveyResult = new SurveyResult();
						surveyResult.setQuestionId(quesid);
						surveyResult.setSiteId(siteid);
						surveyResult.setSurveyId(surveyid);
						surveyResult.setSum(1);
						isSuccess = isSuccess&&surveyResultService.addSurveyResult(surveyResult);
					
					}else{
						surveyResult.setSum(NumberUtil.getInt(surveyResult.getSum())+1);
						isSuccess = isSuccess&&surveyResultService.updateSurveyResult(surveyResult);
					}	
					if(isSuccess){
						Answer an = null;
						for(int answerId :idsList){
							HashMap<String,String> map =new HashMap<String,String>();
						
							an = answerService.findByIid(answerId);
							map.put("answerid", answerId+"");
							map.put("count", an.getCount()+"");
							countList.add(map);
						}
					}
				}else if(quesType==2){
				    isSuccess = isSuccess&&answerService.modifyAnswerCount(idsList);
				    if(surveyResult==null){
						surveyResult = new SurveyResult();
						surveyResult.setQuestionId(quesid);
						surveyResult.setSiteId(siteid);
						surveyResult.setSurveyId(surveyid);
						surveyResult.setSum(idsList.size());
						isSuccess = isSuccess&&surveyResultService.addSurveyResult(surveyResult);
					}else{
						surveyResult.setSum(surveyResult.getSum()+idsList.size());
						isSuccess = isSuccess&&surveyResultService.updateSurveyResult(surveyResult);
					}
				    if(isSuccess){
				    	Answer an = null;
						for(int answerId :idsList){
							HashMap<String,String> map =new HashMap<String,String>();
							an = answerService.findByIid(answerId);
							map.put("answerid", answerId+"");
							map.put("count", an.getCount()+"");
							countList.add(map);
						}
					}
					}else{
						//插入关系表
						if(StringUtil.isNotEmpty(textContent)){
							if(surveyResult==null){
								surveyResult = new SurveyResult();
								surveyResult.setQuestionId(quesid);
								surveyResult.setSiteId(siteid);
								surveyResult.setSurveyId(surveyid);
								surveyResultService.addSurveyResult(surveyResult);
							}			
							SurveyAnswer surveyAnswer = new SurveyAnswer();
							surveyAnswer.setContent(textContent);
							surveyAnswer.setQuestionId(quesid);
							surveyAnswer.setSiteId(siteid);
							surveyAnswer.setSurveyId(surveyid);
							surveyAnswerService.add(surveyAnswer);
						}
					}
				}
			    //投票成功，次数+1
			    UserVoteCount userVoteCount = userVoteCountService.findBySiteIdAndSurveyId(siteid, surveyid, uuid);
			    userVoteCount.setCount(NumberUtil.getInt(userVoteCount.getCount())+1);
			    userVoteCountService.update(userVoteCount);
			    ret.put("result", "success");
			    ret.put("countlist", countList);
			}catch(Exception e){
				e.printStackTrace();
				returnMessage = "{\"result\":\"fail\"}";
			}
			returnMessage =JsonUtil.objectToString(ret);
			return returnMessage;
		}else{
			returnMessage = "{\"result\":\"nomore\"}";
			return returnMessage;
		}
	}
	
	/**
	 * MD5 32位散列加密
	 * @param uuid uuid
	 * @return String
	 */
	public String md5(String uuid) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(uuid.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	} 
	
}