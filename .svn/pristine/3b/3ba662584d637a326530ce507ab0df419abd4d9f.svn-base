package com.hanweb.jmp.cms.controller.infos.pic;

import java.util.Date;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission; 
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

/**
 * 文件类型列表页控制器
 * 
 * @author WangFei
 * 
 */
@Controller 
@RequestMapping("manager/pic")
public class ListPicController implements GridViewDelegate {

	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * infoname
	 */
	private String infoname="";

	/**
	 * list:(这里用一句话描述这个方法的作用).
	 *
	 * @param request request
	 * @param gridView gridView
	 * @param infoId infoId
	 * @param infoName infoName
	 * @param fromurl fromurl
	 * @param siteId siteId
	 * @param colId colId
	 * @return    设定参数 .
	*/
	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView, 
			Integer infoId, String infoName, String fromurl, Integer siteId, Integer colId) {
		infoname=infoName;
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/cms/infos/pic/pic_list"); 
		gridView.setShowAdvSearch(false);
		gridView.setShowSimpleSearch(false);
		gridView.setPosition("组图管理-"+infoName);
		gridView.setSearchPlaceholder("请输入名称");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, infoId);
		gridView.addQueryParam("infoId", infoId + ""); 
		gridView.addQueryParam("infoName", infoName + "");  
		gridView.addQueryParam("fromutl", fromurl);
		gridView.addQueryParam("siteId", siteId);
		gridView.addQueryParam("colId", colId);
		
		return gridView;
	}

	/**
	 * 创建按钮
	 * 
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
	    gridView.addButton(sort);
		Button back = Button.getInstance("&#xf0009;", "back", "返回");
	    gridView.addButton(back);
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView){
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("iid1").setTitle("ID")
				.setAlign("center"));
		gridView.addHead(Head.getInstance().setField("infoname").setTitle("信息标题")
				.setAlign("center"));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间")
				.setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("syntime").setTitle("发文时间")
				.setAlign("center").setWidth(100));  
		gridView.addHead(Head.getInstance().setField("picpath").setTitle("图标预览")
				.setAlign("center"));
	}
 
	/**
	 * createBody:创建列表.
	 *
	 * @param gridView gridView
	 * @param infoId    设定参数 .
	*/
	private void createBody(GridView gridView, Integer infoId) {
		int siteId =  UserSessionInfo.getCurrentUser().getSiteId();
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("createtime").addSelectField("syntime")
				.addSelectField("picpath").setTable(Tables.PIC);
		String where = " siteid=:siteId";
		gridViewSql.addParam("siteId", siteId);
		if (NumberUtil.getInt(infoId) > 0) {
			where += " AND infoId = :infoId";
			gridViewSql.addParam("infoId", infoId);
		} 
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", "ASC");
		gridViewService.find(gridViewSql);
	}
 
	/**
	 * createRow:创建行.
	 *
	 * @param gridRow gridRow
	 * @param rowData    设定参数
	 * @param index    index .
	*/
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String picpath = StringUtil.getString(rowData.get("picpath"));
	    gridRow.addCell("iid", iid); 
	    gridRow.addCell("iid1", iid);
	    gridRow.addCell("infoname",  infoname, Script.createScript("edit", iid));
	    gridRow.addCell("createtime", DateUtil.dateToString(
				(Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS)); 
		gridRow.addCell("syntime", DateUtil.dateToString(
				(Date) rowData.get("syntime"), DateUtil.YYYY_MM_DD_HH_MM_SS));  
	    gridRow.addCell("picpath",
				"<input type='button' id='button_"+iid+"' class='btn btn-success btn-small' " +
				" value='预览'></input><input type='hidden' " +
				" id='filetype_"+iid+"' class='btn btn-success btn-small' " +
				" value='"+picpath+"'></input>" 
				, false); 
	}
}