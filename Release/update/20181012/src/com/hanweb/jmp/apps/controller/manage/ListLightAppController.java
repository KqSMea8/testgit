package com.hanweb.jmp.apps.controller.manage;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.common.service.GridViewService;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.constant.Tables;

/**
 * 应用列表页
 * @author lgq
 *
 */
@Controller
@RequestMapping(value = "manager/lightapp")
public class ListLightAppController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 列表页
	 * @param gridView
	 * @param request
	 * @param lightTypeId
	 * @return
	 */
	@RequestMapping(value = "list")
	public GridView list(GridView gridView, HttpServletRequest request, String lightTypeId) {
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/apps/manage/lightapp_list");
		createHead(gridView, request);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		createBody(gridView, currentUser.getSiteId(), lightTypeId);
		createButton(gridView, lightTypeId);
		gridView.setSearchPlaceholder("请输入应用名称");
		gridView.addQueryParam("lightTypeId", lightTypeId);
		gridView.addObject("lightTypeId", lightTypeId);
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 * @param request  request
	 */
	public void createHead(GridView gridView, HttpServletRequest request) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(20));
		gridView.addHead(Head.getInstance().setField("name").setTitle("应用名称").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("lighttype").setTitle("应用分类").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("groupname").setTitle("所属机构").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("apptype").setTitle("应用类型").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("isOpen").setTitle("是否启用").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("url").setTitle("应用地址").setTip(true).setAlign("center").setWidth(120));
		gridView.addHead(Head.getInstance().setField("action").setTitle("操作").setAlign("center").setWidth(100)); 
	}
	
	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param name  name
	 * @param colId  colId
	 * @param siteId  siteId
	 * @param colText  colText
	 * @param colState  colState
	 * @param colType  colType
	 */
	private void createBody(GridView gridView, Integer siteId, String lightTypeId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("createtime")
		           .addSelectField("isOpen").addSelectField("isdefault").addSelectField("apptype")
		           .addSelectField("url").addSelectField("defaulttype").addSelectField("lighttype")
		           .addSelectField("lighttypename").addSelectField("groupname").setTable(Tables.LIGHTAPP);
		String where = "";
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin()){
			if (NumberUtil.getInt(siteId) != 0) {
				where += " siteid =:siteId";
				gridViewSql.addParam("siteId", siteId);
			}
			String name = gridView.getSearchText();
			if (StringUtil.isNotEmpty(name)) {
				where += " AND name like :name";
				gridViewSql.addParam("name", "%" + name + "%");
			}
		} else { 
			if (NumberUtil.getInt(siteId) != 0) {
				where += " siteid =:siteId";
				gridViewSql.addParam("siteId", siteId);
			}
			String sql = "";
			if(haveRight(currentUser, "broke")){
				sql += "OR defaulttype = 2 ";	
			}
			if(haveRight(currentUser, "numsense")){
				sql += "OR defaulttype = 5 " ;
			}
			if(haveRight(currentUser, "read")){
				sql += "OR defaulttype = 6 ";
			}
			if(haveRight(currentUser, "broke")||haveRight(currentUser, "numsense")||haveRight(currentUser, "read")){
				where += " AND (";
				sql = sql.substring(2);
				where += sql+")";
			}
		}
		if(NumberUtil.getInt(lightTypeId)>=0){
			where += " AND lighttype =:lighttype";
			gridViewSql.addParam("lighttype", NumberUtil.getInt(lightTypeId));
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}
	
	private boolean haveRight(CurrentUser currentUser, String url){
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin() 
				|| (currentUser.getPermissions() != null && currentUser.getPermissions().contains(url))){
			return true;
		}
		return false;
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 * @param colId  colId
	 */
	public void createButton(GridView gridView, String lightTypeId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin() 
				|| haveRight(currentUser, "lightapp") && !haveRight(currentUser, "broke") 
				&& !haveRight(currentUser, "numsense") && !haveRight(currentUser, "read")){
		    if(NumberUtil.getInt(lightTypeId)!=-1){
		        gridView.addButton(Button.getAdd());
	            gridView.addButton(Button.getRemove());
	            Button sort = Button.getInstance("&#xf5036;", "order", "排序");
	            gridView.addButton(sort);
		    }else{
				Button syn = Button.getInstance("&#xf5022;", "syn", "应用同步");
			    gridView.addButton(syn);
			}
		}
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name")); 
		int isOpen = NumberUtil.getInt(rowData.get("isopen"));
		int appType = NumberUtil.getInt(rowData.get("apptype"));
		String url = StringUtil.getString(rowData.get("url"));
		int isdefault = NumberUtil.getInt(rowData.get("isdefault")); 
		int defaulttype = NumberUtil.getInt(rowData.get("defaulttype")); 
		String lighttypename = StringUtil.getString(rowData.get("lighttypename")); 
		String groupname = StringUtil.getString(rowData.get("groupname"));
		String isEnable = "<font color=\"red\">否</font>";
		if(isOpen == 1){
			isEnable = "<font color=\"blue\">是</font>";
		}
		gridRow.addCell("isOpen", isEnable, Script.createScript("modifyEnable", iid, isOpen),  false);
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		if(isdefault == 1){
			gridRow.addCell("iid", iid).setDisabled(true);
		} else {
			gridRow.addCell("iid", iid);
		}
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("lighttype", lighttypename.length()>0?lighttypename:"默认分组");
		//gridRow.addCell("url", url).setTip(url);
		gridRow.addCell("groupname", groupname);
		if(appType == 1){
			gridRow.addCell("apptype", "H5");
		}else if(appType == 2){
			gridRow.addCell("apptype", "Native");
		}
		gridRow.addCell("createTime", createTime);
		String buttonStr="";
		if(isOpen == 1){
			buttonStr += "<input type='button' class='btn btn-success btn-small' style='color:white;' " +
			"onclick='modifyEnable(\"" + iid + "\",\"" + isOpen+ "\")' value='已启用'></input>&nbsp;";
		}else{
			buttonStr += "<input type='button' class='btn btn-success btn-small' style='color:gray;' " +
			"onclick='modifyEnable(\"" + iid + "\",\"" + isOpen+ "\")' value='未启用'></input>&nbsp;";
		}
		if(defaulttype == 2 || defaulttype == 5 || defaulttype == 6){
			   buttonStr+="<input type='button' class='btn btn-success btn-small' " +
			   	"onclick='managerInfo(\"" + 
					iid + "\", \"" + defaulttype + "\")'" +
					" value='管理'></input>&nbsp;";
		}
		if(defaulttype == 6) {
			
		} else {
			buttonStr += "<input type='button' class='btn btn-success btn-small' " +
					"onclick='previewModel(\"" + url + "\","+iid+")'" +
					" value='二维码'></input>&nbsp;";
		}
		
		String button1 = "<input type='button' class='btn btn-success btn-small' "
				+ "onclick='copyUrl(\"" + url + "\","+ iid+")' value='应用地址' /> &nbsp;"
				+"<input id='"+iid+"' style='border: hidden;width: 0.1px;height: 0.1px;' value='"+url+"'/>";
		
		
		gridRow.addCell("url",button1, false);
		gridRow.addCell("action", buttonStr, false);
	}
}