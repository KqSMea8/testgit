package com.hanweb.jmp.cms.controller.matters.doc;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
//@Permission(module = "info", allowedAdmin = Allowed.YES)
@RequestMapping("manager/matter/doc")
public class ListDocShowController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 列表页
	 * @param request
	 * @param gridView
	 * @param classId
	 * @param name
	 * @return
	 */
	@RequestMapping("showlist")
	public GridView list(HttpServletRequest request, GridView gridView, Integer classId,String name) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/matters/doc/docshow_list");
		gridView.setSearchPlaceholder("请输入附件名称");
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("classId", classId);
		gridView.addQueryParam("name", name);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView,NumberUtil.getInt(classId), gridView.getSearchText());
		return gridView;
	}
	
	/**
	 * 创建按钮  
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
		Button recycle = Button.getInstance("&#xf5079;", "upload", "确定");
		gridView.addButton(Button.getAdd());
		gridView.addButton(recycle); 
	}
	
	/**
	 * 创建表头 
	 * @param gridView   gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("url").setHidden(true));
		gridView.addHead(Head.getInstance().setField("name").setTitle("附件名称").setAlign("left").setWidth(100));
		gridView.addHead(Head.getInstance().setField("pname").setTitle("所属分类").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(100));
	}
	
	/**
	 * 创建列表
	 * @param gridView   gridView
	 * @param searchName searchName
	 */
	private void createBody(GridView gridView, Integer classId, String searchName) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("createtime")
				   .addSelectField("pname").addSelectField("docpath").setTable(Tables.DOC);
		String where = " siteid=:siteid AND isremove=0";
		if(classId > 0){
			where += " AND classid=:classId ";
			gridViewSql.addParam("classId", classId);
		}
		if (StringUtil.isNotEmpty(searchName)) {
			where += " AND name LIKE :name";
			gridViewSql.addParam("name", "%" + searchName + "%");
		}
		where += " ORDER BY iid DESC";
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		gridViewSql.addParam("siteid", currentUser.getSiteId());
		gridViewSql.setWhere(where);
		gridViewService.find(gridViewSql);
	}
	
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String pname = StringUtil.getString(rowData.get("pname"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		String docpath = StringUtil.getString(rowData.get("docpath"));
		String url=Configs.getConfigs().getJmpUrl()+docpath;
		gridRow.addCell("iid", iid);
		gridRow.addCell("url", url);
		gridRow.addCell("name", name);
		gridRow.addCell("pname", pname);
		gridRow.addCell("createtime", createtime);
	}
}