package com.hanweb.interceptor;

import java.util.ArrayList; 
import java.util.List; 
 
import com.hanweb.common.permission.PermissionStructure;
import com.hanweb.common.permission.SimplePermissionAdapter; 
import com.hanweb.common.util.StringUtil; 
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

/**
 * 越权访问验证
 * @author 高成锋
 *
 */
public class UnauthorizedCheckAdapter extends SimplePermissionAdapter{

	@Override
	public boolean checkControllerWithURL(PermissionStructure perminssion, String url) { 
		String menuright = url.replaceFirst(perminssion.getRootPath(), ""); 
		menuright = menuright.substring(0, menuright.lastIndexOf("/"));
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();  
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin() || 
				(currentUser.getPermissions()!=null 
						&& currentUser.getPermissions().contains(menuright))){
			return true;
		}
		if(("/manager/user/modify_password_show.do".equals(url) 
				|| "/manager/user/modify_password_submit.do".equals(url)
				|| "/manager/user/modify_self_show.do".equals(url)
				|| "/manager/user/modify_self_submit.do".equals(url)) && currentUser!=null){
			return true;
		}
		
		//机构权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("group")){
			if(menuright.indexOf("group")>-1){
				return true;
			}
		}
		
		//阅读权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("read")){
			if(menuright.indexOf("read")>-1){
				return true;
			}
		}
		
		//通讯录权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("numsense")){
			if(menuright.indexOf("numsense")>-1){
				return true;
			}
		}
		
		//报料权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("broke")){
			if(menuright.indexOf("broke")>-1){
				return true;
			}
		}
		
		//应用权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("read")
				|| currentUser.getPermissions().contains("lightapp")
				|| currentUser.getPermissions().contains("numsense")
				|| currentUser.getPermissions().contains("broke")){
			if(menuright.indexOf("read")>-1 || menuright.indexOf("numsense")>-1 || menuright.indexOf("broke")>-1 || menuright.indexOf("lightapp")>-1){
				return true;
			}
		}
		
		//素材权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("/matter")
				|| currentUser.getPermissions().contains("/matter/doccol")
				|| currentUser.getPermissions().contains("/matter/videocol")){
			if(menuright.indexOf("picture")>-1 || menuright.indexOf("doc")>-1 || menuright.indexOf("video")>-1){
				return true;
			}
		}
		//商城权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("/shop")){
			if(menuright.indexOf("shop")>-1){
				return true;
			}
		}
		//商城权限
		if(currentUser.getPermissions()!=null 
				&& currentUser.getPermissions().contains("/role")){
			if(menuright.indexOf("role")>-1){
				return true;
			}
		}
		//酷图权限
		if(currentUser.getPermissions()!=null
				&& (currentUser.getPermissions().contains("/info"))){
			if(menuright.indexOf("pic")>-1){
				return true;
			}
		}
		//待审核信息权限
		if(currentUser.getPermissions()!=null 
				&& (currentUser.getPermissions().contains("/todo")
						|| currentUser.getPermissions().contains("/recycle"))){
			if(menuright.indexOf("info")>-1){
				return true;
			}
		}
		return false;  
	}
	
	 
	/**
	 * 根据菜单默认权限过滤出访问路径中可能的合法菜单标识
	 * @param tagList tagList
	 * @param menuList menuList
	 * @return List<String>
	 */
	public List<String> obtainValidMenu(List<String> tagList, List<String> menuList){ 
		List<String> validMenuTag = new ArrayList<String>();
		String menutag = "";
		int index = 0;
		do{
			if(StringUtil.equals(tagList.get(index), "")){
				index++;
				continue;
			}
			if(index == 0 || StringUtil.equals(menutag, "")){
				menutag = tagList.get(index);
			}else{
				menutag += "_" + tagList.get(index);
			}
			if(menuList.contains(menutag)){
				validMenuTag.add(menutag);
			}
			index++;
		}while(index < tagList.size());
		
		return validMenuTag;
	}
	
	/**
	 * 获取集合中最长的字符串
	 * @param validMenuTag validMenuTag
	 * @return String
	 */
	public String obtainLongestTag(List<String > validMenuTag){ 
		String longestTag = "";
		if(validMenuTag != null && validMenuTag.size() > 0){
			for(String menuTag : validMenuTag){
				if(longestTag.length() < menuTag.length()){
					longestTag = menuTag;
				}
			}
		}
		return longestTag;
	} 
}
