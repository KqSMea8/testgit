package com.hanweb.jmp.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.quartz.JobDataMap;

import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.global.entity.PushThread;
import com.hanweb.jmp.global.service.PushThreadService;
import com.hanweb.jmp.global.service.XgPushService;
import com.hanweb.jmp.newspush.peoplelist.entity.PeoplelistRelation;
import com.hanweb.jmp.newspush.peoplelist.service.PeoplelistRelationService;
import com.hanweb.jmp.newspush.userdevice.entity.UserDevice;
import com.hanweb.jmp.newspush.userdevice.service.UserDeviceService;
import com.hanweb.jmp.sys.service.log.PushInfoLogService;

public class PushTask extends BaseTask{
	
	/**
	 * infoService
	 */
	private InfoService infoService = SpringUtil.getBean(InfoService.class);
	
	/**
	 * pushThreadService
	 */
	private PushThreadService pushThreadService = SpringUtil.getBean(PushThreadService.class);
	
	/**
	 * xgPushService
	 */
	private XgPushService xgPushService = SpringUtil.getBean(XgPushService.class);
	
	/**
	 * pushInfoLogService
	 */
	private PushInfoLogService pushInfoLogService =  SpringUtil.getBean(PushInfoLogService.class);
	
	private UserService userService =  SpringUtil.getBean(UserService.class);
	
	private UserDeviceService userDeviceService =  SpringUtil.getBean(UserDeviceService.class);
	
	private PeoplelistRelationService peoplelistRelationService =  SpringUtil.getBean(PeoplelistRelationService.class);
	
	
    /**
     * 开关
     */
    public static int state;
    
	/**
	 * state
	 */
	public static void state(){
		state = 1;
	} 
	
	@Override
	protected void config() {
		setTaskId("push_task");
		setTaskName("推送信息");
		//每1分扫描执行
		setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(1));
	}

	@Override
	protected void doWork(JobDataMap dataMap) {
		if(state == 0){
			return;
		} 
		List<PushThread> pts = pushThreadService.findAllByTime(new Date()); 
		int androidstatus = 0;
		int iosstatus = 0;
		int ipadstatus = 0;
		boolean androidIsSuccess = false;
		if(pts != null && pts.size()>0){			
			List<Integer> pushed = new ArrayList<Integer>();
			List<Integer> pids = new ArrayList<Integer>();
			for(PushThread pt : pts){
				Info info = infoService.findByIid(pt.getInfoId(), pt.getSiteId());
				if(info==null){
					pids.add(pt.getIid());
					continue;
				}
				if(pt.getIspublic() == 1){
					androidIsSuccess = xgPushService.androidAllPush(info);
					if(androidIsSuccess){
						androidstatus = 2;
					}else{
						androidstatus = 3;
					}
					Map<String, Boolean> map = xgPushService.iosAllPush(info);
					//iPhone推送状态
					if(map.get("iphone")){
						iosstatus = 2;
					}else{
						iosstatus = 3;
					}
					//iPad推送状态
					if(map.get("ipad")){
						ipadstatus = 2;
					}else{
						ipadstatus = 3;
					}
				}else{
					Integer sendtype = pt.getSendType();
					//广播（全部用户）
					if(sendtype == 0){
						List<User> user = userService.findAllUser(pt.getSiteId());
						for(User u:user){
							List<UserDevice> userDevice = userDeviceService.findByUid(u.getIid());
							for(UserDevice ud:userDevice){
								if(NumberUtil.getInt(ud.getClientType()) == 1){
									androidIsSuccess = xgPushService.androidPushTokenMessage(info, ud);
									if(androidIsSuccess){
										androidstatus = 2;
									}else{
										androidstatus = 3;
									}
								}else{
									Map<String, Boolean> map = xgPushService.iosPushTokenMessage(info, ud);
									if(map.get("iphone")){
										iosstatus = 2;
									}else{
										iosstatus = 3;
									}
									//iPad推送状态
									if(map.get("ipad")){
										ipadstatus = 2;
									}else{
										ipadstatus = 3;
									}
								}
							}
						}
					}else if(sendtype == 1){//指定机构
						List<User> user = userService.findByGroupid(pt.getSendtypeid());
						for(User u:user){
							List<UserDevice> userDevice = userDeviceService.findByUid(u.getIid());
							for(UserDevice ud:userDevice){
								if(NumberUtil.getInt(ud.getClientType()) == 1){
									androidIsSuccess = xgPushService.androidPushTokenMessage(info, ud);
									if(androidIsSuccess){
										androidstatus = 2;
									}else{
										androidstatus = 3;
									}
								}else{
									Map<String, Boolean> map = xgPushService.iosPushTokenMessage(info, ud);
									if(map.get("iphone")){
										iosstatus = 2;
									}else{
										iosstatus = 3;
									}
									//iPad推送状态
									if(map.get("ipad")){
										ipadstatus = 2;
									}else{
										ipadstatus = 3;
									}
								}
							}
						}
					}else if(sendtype == 2){//指定群组
						List<PeoplelistRelation> list = peoplelistRelationService.findBypeoplelistid(pt.getSendtypeid());
						for(PeoplelistRelation p:list){
							List<UserDevice> userDevice = userDeviceService.findByUid(p.getUserId());
							for(UserDevice ud:userDevice){
								if(NumberUtil.getInt(ud.getClientType()) == 1){
									androidIsSuccess = xgPushService.androidPushTokenMessage(info, ud);
									if(androidIsSuccess){
										androidstatus = 2;
									}else{
										androidstatus = 3;
									}
								}else{
									Map<String, Boolean> map = xgPushService.iosPushTokenMessage(info, ud);
									if(map.get("iphone")){
										iosstatus = 2;
									}else{
										iosstatus = 3;
									}
									//iPad推送状态
									if(map.get("ipad")){
										ipadstatus = 2;
									}else{
										ipadstatus = 3;
									}
								}
							}
						}
					}else if(sendtype == 3){//指定用户
						List<Integer> list = StringUtil.toIntegerList(pt.getUserIds());
						for(Integer i:list){
							List<UserDevice> userDevice = userDeviceService.findByUid(i);
							for(UserDevice ud:userDevice){
								if(NumberUtil.getInt(ud.getClientType()) == 1){
									androidIsSuccess = xgPushService.androidPushTokenMessage(info, ud);
									if(androidIsSuccess){
										androidstatus = 2;
									}else{
										androidstatus = 3;
									}
								}else{
									Map<String, Boolean> map = xgPushService.iosPushTokenMessage(info, ud);
									if(map.get("iphone")){
										iosstatus = 2;
									}else{
										iosstatus = 3;
									}
									//iPad推送状态
									if(map.get("ipad")){
										ipadstatus = 2;
									}else{
										ipadstatus = 3;
									}
								}
							}
						}
					}
				}
				
				pushInfoLogService.modifyPushInfoLog(androidstatus, iosstatus, ipadstatus, info.getIid(), info.getSiteId());
				pushed.add(info.getIid()); 
				pids.add(pt.getIid());
				infoService.modifyPushState(pushed, pt.getSiteId());
				pushed = new ArrayList<Integer>();
			}	
		    pushThreadService.removeByIids(pids);
		}
		int num1 = pushThreadService.findAllPushTask();
		if(num1 > 0){
			state = 1;
			return;
		}else{
			state = 0;
		}
	}
	
}