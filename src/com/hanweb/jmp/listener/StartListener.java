package com.hanweb.jmp.listener;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.oschina.j2cache.CacheChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.task.TaskManager;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.Properties;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.task.AppSynchronizeTask;
import com.hanweb.jmp.task.AuditInfoTask;
import com.hanweb.jmp.task.ClearInfoTask;
import com.hanweb.jmp.task.ClearInterfaceCacheTask;
import com.hanweb.jmp.task.ClearInterfaceLogTask;
import com.hanweb.jmp.task.JSearchTask;
import com.hanweb.jmp.task.OfflineTask;
import com.hanweb.jmp.task.PushTask;
import com.hanweb.jmp.task.SynchInfoTask;
import com.hanweb.jmp.task.TopInfoTask;
import com.hanweb.jmp.util.SynInfoUtil;

public class StartListener extends ContextLoaderListener implements ServletContextListener {
	
	/**
	 * logger
	 */
	protected final Log logger = LogFactory.getLog(StartListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 判断是否已经注册并初始化过
		if (BaseInfo.isPrepared()) {
			initApplication();
		}
	}

	/**
	 * initApplication:(具体应用需要初始化的内容在此实现).
	 */
	public void initApplication() {
		//初始化缓存
		initCache();
		// jmportal.properties配置文件参数读取
		readProperties();
		//初始化任务启动
		initTask();
	}
	
	/**
	 * initTask:(这里用一句话描述这个方法的作用).
	 *    设定参数 .
	 */
	private void initTask(){
		// 启动同步线程,延迟2分钟加载
		new java.util.Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				// 启用信息同步的任务
				startSynTask();		
				// 启动离线下载线程
				TaskManager.addTask(new OfflineTask());
			}
		}, 120000);
	
		// 启动信息置顶线程
		TaskManager.addTask(new TopInfoTask());

		// 启动信息审核线程
		TaskManager.addTask(new AuditInfoTask());

		// 启动信息清除线程
		TaskManager.addTask(new ClearInfoTask());
		
		// 启动全文检索同步线程
		TaskManager.addTask(new JSearchTask());
		
		// 启动信息推送线程
		TaskManager.addTask(new PushTask());
		
		//启动接口日志清理线程
		TaskManager.addTask(new ClearInterfaceLogTask());
		
		// 启动接口缓存清理线程
		TaskManager.addTask(new ClearInterfaceCacheTask());
		
		// 组件任务启动
		//ModuleService.initTask();
		
		// 启动应用同步线程
		TaskManager.addTask(new AppSynchronizeTask());
				 
	}

	/**
	 * 启用信息同步的任务
	 */
	private void startSynTask() {
		SynInfoUtil synDao = new SynInfoUtil();
		
		// 是否能够连接jget接口
		boolean isconnect = false;
		
		// 查找已经启用的栏目
		ColService colService = SpringUtil.getBean("jmp_ColService", ColService.class);
		List<Col> cols = colService.findColByEnable(1);
		if (cols != null && cols.size() > 0) {
			SynchInfoTask synTask = null;
			for (Col col : cols) {
				if ((col == null 
						|| NumberUtil.getInt(col.getIid()) <= 0 
						|| NumberUtil.getInt(col.getTaskId()) <= 0
						|| NumberUtil.getInt(col.getSynPeriod()) <= 0) 
						|| col.getSynPeriod() == null) {
					continue;
				}
				isconnect = synDao.isConnection(col.getSourceurl());
				if (!isconnect) {
					logger.error("id为"+col.getIid()+"的栏目的"+"信息同步接口无法连接");
					continue;
				}
				
				synTask = new SynchInfoTask();
				synTask.addParam("col", col);
				
				// 任务周期
				synTask.setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(col.getSynPeriod()));
				
				// 任务id
				synTask.setTaskId("synInfo_" + col.getIid());
				// 任务名称
				synTask.setTaskName(col.getSiteId()+"-"+col.getName());
				// 任务可以暂停
				synTask.setCanPause(true);
				// 任务可以删除
				synTask.setCanRemove(true);
				// 信息同步线程启用
				TaskManager.addTask(synTask);
			}
		}
	}

	/**
	 * 读取配置文件参数
	 */
	private void readProperties() {
		Properties properties = new Properties(
				BaseInfo.getRealPath() + "/WEB-INF/config/jmportal.properties");
		if (!properties.isEmpty()) {
			StaticValues.setFormats(StringUtil.split(Configs.getConfigs().getTimeFormat(), ","));
		}
		Properties template = new Properties(
				BaseInfo.getRealPath() + "/WEB-INF/config/htmltemplate.properties");
		if (!template.isEmpty()) {
			StaticValues.setWindows(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("windowsHtml")));
			StaticValues.setAndroid(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("androidHtml")));
			StaticValues.setIpad(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("ipadHtml")));
			StaticValues.setIphone(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("iphoneHtml")));
			StaticValues.setDefhtml(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("defHtml")));
			
			//加载模版
			StaticValues.setAndroidTemp(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("androidTemp")));
			StaticValues.setNormalAndroidTemp(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("normalAndroidTemp")));
			StaticValues.setAndrTempWithWidth(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("andrTempWithWidth")));
			StaticValues.setIpadPicAndTextTemp(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("ipadPicAndTextTemp")));
			StaticValues.setIpadTemp(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("ipadTemp")));
			StaticValues.setIphoneTemp(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("iphoneTemp")));
			StaticValues.setSceneAbsTmpUrl(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("sceneAbsTmp")));
			StaticValues.setSceneVedioTmpUrl(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("sceneVedioTmp")));
			StaticValues.setSceneAudioTmpUrl(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("sceneAudioTmp")));
			StaticValues.setAndrVedioTmpUrl(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("andrVedioTmp")));
			StaticValues.setArtVedioTmpUrl(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("artVedioTmp")));
			StaticValues.setArtAbsTmpUrl(BaseInfo.getRealPath() 
					+ StringUtil.getString(template.getString("artAbsTmp")));
		}
	}

	/**
	 * 初始化缓存
	 */
	private void initCache() {
		
		// 初始化jget树缓存,延迟2分钟
//		new java.util.Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				SpringUtil.getBean("jmp_TaskService", TaskService.class)
//				.initCache();  
//			}
//		}, 120000);
		
		//初始化缓存
		@SuppressWarnings("unused")
		CacheChannel jcache = CacheChannel.getInstance();
	}
	
}