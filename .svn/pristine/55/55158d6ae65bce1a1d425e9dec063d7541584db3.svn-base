package com.hanweb.jmp.global.service;

import java.util.ArrayList;
import java.util.List;

import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.view.menu.Menu;
import com.hanweb.common.view.menu.MenuItem;

/**
 * 组件化类
 * 实现组件显示和组件任务
 * @author denganming
 */
@SuppressWarnings("unchecked")
public class ModuleService {
	
	/**
	 * 所有组件
	 */
	private static final String[] ALLMODULE = {"weather", "weibo"};
	
	/**
	 * HASAllMoODULE
	 */
	private static List<String> hasModule = new ArrayList<String>();
	
	/**
	 * 需要显示的组件和具体属性
	 */
	private static final String[][] SHOWMODULE = {
											{"weather", "area", "地区管理"}
										};
	/**
	 * CLASSNAME
	 */
	private static final String CLASSNAME = "com.hanweb.1.service.2Service";
	static{
		String clazzName = "";
		String upModule = "";
		Class clazz = null;
		for (String module : ALLMODULE) {
			upModule = module.substring(0, 1).toUpperCase() + module.substring(1);
			clazzName = CLASSNAME.replace("1", module).replace("2", upModule);
			try {
				clazz = Class.forName(clazzName);
				if(clazz != null){
					hasModule.add(module);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 初始化组件任务
	 * 放在每个组件service的里面实现
	 */
	public static void initTask(){
	}
	 
	/**
	 * initShow:初始化组件显示.
	 * @param menu    设定参数 .
	 */
	public static void initShow(Menu menu){
		for (String[] module : SHOWMODULE) {
			if(hasModule.contains(module[0])){
				if("地区管理".equals(module[2])){
					menu.addMenuItem(MenuItem.getInstance(module[1], "plugins", module[2],
							ControllerUtil.getAbsolutePath("/manager/menu/provincemenu_show.do"),
							ControllerUtil.getAbsolutePath("/manager/" + module[1] + "/list.do"))
							.setAllowed(true, false));
				}else{
					menu.addMenuItem(MenuItem.getInstance(module[1], "plugins", module[2], 
							ControllerUtil.getAbsolutePath("/manager/" + module[1] + "/list.do"))
							.setAllowed(true, false));
				} 
			}
		}
	}
	
}