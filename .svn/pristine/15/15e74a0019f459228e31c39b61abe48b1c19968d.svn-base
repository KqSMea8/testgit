package com.hanweb.jmp.cms.controller.infos.info;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
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
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/todo")
public class ListtodoInfoController implements GridViewDelegate{
	
	/**
	 * GridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 列表页展现
	 * @param gridView GridView
	 * @param colId String
	 * @param infoTitle String
	 * @param starttime String
	 * @param endtime String
	 * @param colName String
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, String colId, String infoTitle, String starttime, String endtime, String colName){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/infos/todo_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, NumberUtil.getInt(colId), infoTitle, starttime, endtime);
		gridView.addQueryParam("colId", StringUtil.getString(colId)); 
		gridView.addObject("siteId", currentUser.getSiteId());
		gridView.addObject("colName", colName);
		gridView.addObject("starttime", starttime);
		gridView.addObject("endtime", endtime);
		gridView.addObject("infoTitle", infoTitle);
		gridView.addObject("colId", colId);
		return gridView;
	}
	
	/**
	 * 表头功能按钮
	 * @param gridView GridView
	 */
	public void createButton(GridView gridView){
		gridView.addButton(Button.getInstance(null, "audit", "审核"));
		
	}
	
	/**
	 * 表头
	 * @param gridView GridView
	 */
	public void createHead(GridView gridView){
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15).setTip(true));
		gridView.addHead(Head.getInstance().setField("title").setTitle("信息标题").setAlign("left").setResizable(true).setTip(true)); 
		gridView.addHead(Head.getInstance().setField("colname").setTitle("所属栏目").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("syntime").setTitle("发布时间").setAlign("center").setWidth(100)); 
		gridView.addHead(Head.getInstance().setField("status").setTitle("状态").setAlign("center").setWidth(40)); 
		gridView.addHead(Head.getInstance().setField("goodcount").setTitle("点赞数").setAlign("center").setWidth(30)); 
		gridView.addHead(Head.getInstance().setField("visitcount").setTitle("阅读数").setAlign("center").setWidth(30));  
	}
	
	/**
	 * 从数据库查询数据
	 * @param gridView GridView
	 * @param colId Integer
	 * @param infoTitle String
	 * @param starttime String
	 * @param endtime String
	 */
	public void createBody(GridView gridView, Integer colId, String infoTitle, String starttime, String endtime){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("i.iid")
				   .addSelectField("i.title")
				   .addSelectField("(SELECT b.name FROM "+Tables.COL+
					" b WHERE b.iid=i.colid) colname")
				   .addSelectField("i.colid")
				   .addSelectField("i.status").addSelectField("i.syntime")
				   .addSelectField("i.topid")
				   .addSelectField("(SELECT MAX(c.goodcount) FROM " 
				   + Tables.INFO_COUNT  + currentUser.getSiteId() 
				   + " c WHERE c.titleid = i.iid AND c.type=1) goodcount")
				   .addSelectField("(SELECT MAX(v.visitcount) FROM " 
				   + Tables.INFO_COUNT  + currentUser.getSiteId()  + " v " 
				   + " WHERE v.titleid = i.iid AND v.type=1) visitcount")
		           .setTable(Tables.INFO+currentUser.getSiteId() + " i");
		String where = " i.status is null OR i.status!=1 AND i.siteid=:siteId AND i.isremove=0";
		gridViewSql.addParam("siteId", currentUser.getSiteId());
		
		if (NumberUtil.getInt(colId) > 0) {
			where += " AND  i.colid = :colId";
			gridViewSql.addParam("colId", colId);
		} 
		String title = gridView.getSearchText();  
		if (StringUtil.isNotEmpty(title)) {
			if(StringUtil.isEmpty(where)){
				where+=" i.title LIKE :title";
			} else {
				where+=" AND i.title LIKE :title";
			} 
			gridViewSql.addParam("title", "%" + title + "%");
		} else {
			if (StringUtil.isNotEmpty(infoTitle)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.title LIKE :title";
				} else {
					where+=" AND i.title LIKE :title";
				}  
				gridViewSql.addParam("title", "%" + infoTitle + "%");
			} 
			 
			if (StringUtil.isNotEmpty(starttime)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.createtime >= :starttime";
				} else {
					where+=" AND i.createtime >= :starttime";
				}    
				gridViewSql.addParam("starttime", starttime+" 00:00:00'");
			} 
			
			if (StringUtil.isNotEmpty(endtime)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.createtime <= :endtime";
				} else {
					where+=" AND i.createtime <= :endtime";
				}    
				gridViewSql.addParam("endtime", endtime+" 23:59:59'");
			}  
		} 
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("i.iid", GridViewSql.SORT_DESC);    
		gridViewService.find(gridViewSql);
	}
	
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String title = StringUtil.getString(rowData.get("title")); 
		String colname = StringUtil.getString(rowData.get("colname"));   
		String status = StringUtil.getString(rowData.get("status"));  
		int goodCount =  NumberUtil.getInt(rowData.get("goodcount"));
		int topid = NumberUtil.getInt(rowData.get("topid"));
		int visitCount =  NumberUtil.getInt(rowData.get("visitcount"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		String editStr=Script.createScript("look", iid, title);
		if(topid > 0){
			gridRow.addCell("title",  "<font color='red'>[顶]"+"</font>"+title, editStr, false);
		}else{
			gridRow.addCell("title",  title, editStr);
		}
		gridRow.addCell("colname", colname);
		gridRow.addCell("syntime", DateUtil.dateToString((Date) rowData.get("syntime"), DateUtil.YYYY_MM_DD_HH_MM_SS));  
	    if("1".equals(status)){
			gridRow.addCell("status", "已审核"); 
		}else if("2".equals(status)){
			gridRow.addCell("status", "已撤审"); 
		}else{
			gridRow.addCell("status", "未审核"); 
		} 
		gridRow.addCell("goodcount", goodCount);
		gridRow.addCell("visitcount", visitCount);		
	}

}