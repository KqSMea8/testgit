package com.hanweb.interceptor;


import com.hanweb.common.permission.SimplePermissionAdapter;
import com.hanweb.common.util.SpringUtil; 
import com.hanweb.jmp.annotation.Service;  

/**
 * 系统权限验证业务类
 * @author 高成锋
 *
 */
@Service
public class PermissionCheckAdapter extends SimplePermissionAdapter { 
	
	public static PermissionCheckAdapter getInstance(){
		return SpringUtil.getBean(PermissionCheckAdapter.class);
	} 
}
