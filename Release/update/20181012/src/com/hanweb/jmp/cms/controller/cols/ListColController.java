package com.hanweb.jmp.cms.controller.cols;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
//@Permission(module = "/col", allowedAdmin = Allowed.YES)
@RequestMapping(value = "manager/col")
public class ListColController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * colService
	 */
	@Autowired
	ColService colService;

	/**
	 * 列表
	 * @param gridView  gridView
	 * @param request  request
	 * @param colId  colId
	 * @param colName  colName
	 * @param name  name
	 * @param type  type
	 * @param colText  colText
	 * @param colState   colState
	 * @param colType  colType
	 * @return  GridView
	 */
	@RequestMapping(value = "list")
	public GridView list(GridView gridView, HttpServletRequest request, String colId, String colName,
		String name, String type, String colText, Integer colState, Integer colType) {
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/cms/cols/col_list");
		createHead(gridView, request);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		String searchName = gridView.getSearchText();
		if (StringUtil.isEmpty(searchName)) {
			searchName = name;
		}
		Set<String> colRightids = currentUser.getColids();
		createBody(gridView, searchName, NumberUtil.getInt(colId), siteId, colText, colState, colType, colRightids);
		createButton(gridView, NumberUtil.getInt(colId), colRightids);
		if (StringUtil.isEmpty(colName)) {
			gridView.addQueryParam("colName", "栏目分类");
		} else {
			gridView.addQueryParam("colName", colName);
		}
		gridView.addQueryParam("type", type);
		gridView.addQueryParam("siteId", siteId);
		gridView.addQueryParam("colId", colId); 
		gridView.addObject("colText", colText);
		gridView.addObject("colState", colState);
		gridView.addObject("colType", colType);  
		gridView.setSearchPlaceholder("请输入栏目名称");
		gridView.setShowAdvSearch(true);
		return gridView;
	}
	
	/**
	 * 创建表头
	 * 
	 * @param gridView  gridView
	 * @param request  request
	 */
	public void createHead(GridView gridView, HttpServletRequest request) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15));
		gridView.addHead(Head.getInstance().setField("name").setTitle("栏目名称").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("type").setTitle("类型").setAlign("center").setWidth(40));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("operate").setTitle("操作").setAlign("center").setWidth(100));
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
	 * @param colRightids 
	 */
	private void createBody(GridView gridView, String name, Integer colId, Integer siteId,
		String colText, Integer colState, Integer colType, Set<String> colRightids) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("pid")
		           .addSelectField("type").addSelectField("enable").addSelectField("createtime")
		           .addSelectField("newlightappid")
		           .addSelectField("coltype").setTable(Tables.COL);
		String where = " pid IS NULL ";
		if (NumberUtil.getInt(colId) != 0) {
			where = " pid =:pId";
			gridViewSql.addParam("pId", colId);
		}
		if (NumberUtil.getInt(siteId) != 0) {
			where += " AND siteid =:siteId";
			gridViewSql.addParam("siteId", siteId);
		}
		if (StringUtil.isNotEmpty(name)) {
			where += " AND name LIKE :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}else{
			if(StringUtil.isNotEmpty(colText)){
				where += " AND name LIKE :name";
				gridViewSql.addParam("name", "%" + colText + "%");
			}
			if(colState != null && NumberUtil.getInt(colState) > -1){
				where += " AND enable =:enable";
				gridViewSql.addParam("enable", colState);
			} 
			if(colType != null && NumberUtil.getInt(colType) > -1){
				where += " AND type =:type";
				gridViewSql.addParam("type", colType);
			}
		}
		List<Integer> colList = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(colRightids)){
		    for(String str : colRightids){
		        if(str.contains("manage")){
		            colList.add(NumberUtil.getInt(str.substring(7)));
		        }
		    }
		}
		if(colList.size() > 1){
		    where += " AND iid IN (:colList)";
            gridViewSql.addParam("colList", colList);
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 * @param colId  colId
	 * @param colRightids 
	 */
	public void createButton(GridView gridView , int colId, Set<String> colRightids) {
        Col col = colService.findByIid(colId);
		if(colId == 0 || col.getType() == 1){
		    if(CollectionUtils.isNotEmpty(colRightids)){
                gridView.addButton(Button.getAdd());
                gridView.addButton(Button.getRemove());
                Button sort = Button.getInstance("&#xf5036;", "order", "排序");
                gridView.addButton(sort);
		    } else {
    			gridView.addButton(Button.getAdd());
    			gridView.addButton(Button.getRemove());
    			gridView.addButton(Button.getImport());
    			gridView.addButton(Button.getExport());
    			Button sort = Button.getInstance("&#xf5036;", "order", "排序");
    			gridView.addButton(sort);
    			gridView.addButton(Button.getCopy());
    			Button quote = Button.getInstance("&#xf0037;", "quote", "引用");
    			gridView.addButton(quote);
		    }
		}
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String pid = StringUtil.getString(rowData.get("pid"));
		String coltype = StringUtil.getString(rowData.get("coltype"));
		int enable = NumberUtil.getInt(rowData.get("enable"));
		int type = NumberUtil.getInt(rowData.get("type"));
		String newlightappid = StringUtil.getString(rowData.get("newlightappid"));
		int subnum = 0;
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		Col col = colService.findByIid(NumberUtil.getInt(iid));
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		if("C".equals(coltype)){
		    gridRow.addCell("name", "<font color='red'>[复]"+"</font>" + name, Script.createScript("edit", iid), false);
		} else if("Q".equals(coltype)){
		    gridRow.addCell("name", "<font color='red'>[引]"+"</font>" + name, Script.createScript("edit", iid), false);
		} else {
		    gridRow.addCell("name", name, Script.createScript("edit", iid));
		}
		gridRow.addCell("pid", pid);
		subnum = colService.findSubCol(NumberUtil.getInt(iid));
		String operateStr = "" ;
		operateStr += "<input type='button' class='btn btn-success btn-small' " +
		"onclick='edit(\"" + iid + "\")' value='编辑'></input>&nbsp;";
		if(enable == 1){
			operateStr += "<input type='button' class='btn btn-success btn-small' style='color:white;' " +
			"onclick='modifyEnable(\"" + iid + "\",\"" + enable+ "\")' value='已启用'></input>&nbsp;";
		}else{
			operateStr += "<input type='button' class='btn btn-success btn-small' style='color:gray;' " +
			"onclick='modifyEnable(\"" + iid + "\",\"" + enable+ "\")' value='未启用'></input>&nbsp;";
		}
		if(subnum <= 0 && col.getType() == 2){
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			boolean isWebSiteAdmin=currentUser.getIsWebSiteAdmin();
			//网站管理员
			if(isWebSiteAdmin){
				operateStr += "<input type='button' class='btn btn-success btn-small' " +
                        "onclick='filterRule(\"" + iid + "\")' value='过滤规则'></input>&nbsp;";
		    }
			operateStr += "<input type='button' class='btn btn-success btn-small' " +
                    "onclick='goInfo(\"" + iid + "\",\"" + name+ "\")' value='信息维护'></input>&nbsp;";
		} 
		if(type == 3 && newlightappid != null){
			operateStr += "<input type='button' class='btn btn-success btn-small' " +
            "onclick='sort(\"" + iid + "\", \"" + newlightappid + "\")' value='排序'></input>&nbsp;";
		}
		gridRow.addCell("operate", operateStr, false);
		String typename = "";
		if(type == 1){
			typename = "类目";
		}else if(type == 2){
			typename = "信息列表";
		}else{
			typename = "应用";
		}
		gridRow.addCell("type", typename);
		gridRow.addCell("createTime", createTime);
	}
	
}