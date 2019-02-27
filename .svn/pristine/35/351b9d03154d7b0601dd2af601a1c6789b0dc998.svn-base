package com.hanweb.jmp.sys.controller.feedback;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.basedao.LikeType;
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
//@Permission(module = "feedback", allowedAdmin = Allowed.YES )
@RequestMapping("manager/feedback")
public class ListFeedBackController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 反馈列表
	 * @param request   request
	 * @param gridView  gridView
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/feedback/feedback_list");
		gridView.setSearchPlaceholder("请输入反馈信息");
		String searchName = gridView.getSearchText();
		gridView.setShowAdvSearch(false);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, searchName);
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getRemove());
	}

	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("content").setTitle("反馈信息").setAlign("left").setTip(true));
		gridView.addHead(Head.getInstance().setField("loginname").setTitle("用户名").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(60));
		gridView.addHead(Head.getInstance().setField("isread").setTitle("状态").setAlign("center").setWidth(30));
	}

	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param searchName searchName
	 */
	private void createBody(GridView gridView, String searchName) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("content")
			       .addSelectField("loginname").addSelectField("createtime")
			       .addSelectField("isread").setTable(Tables.FEEDBACK);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		String where = "";
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		if (NumberUtil.getInt(siteId) != 0) {
			where += " siteid =:siteId";
			gridViewSql.addParam("siteId", siteId);
		}
		if(StringUtil.isNotEmpty(searchName)){ 
			where += " AND content LIKE :searchName";
			gridViewSql.addParam("searchName", searchName, LikeType.LR);
		} 
		gridViewSql.setWhere(where);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		String content = StringUtil.getString(rowData.get("content"));
		String loginname = StringUtil.getString(rowData.get("loginname"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		Integer isread = NumberUtil.getInt(rowData.get("isread"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("content", content, Script.createScript("edit", iid));
		gridRow.addCell("loginname", loginname);
		gridRow.addCell("createtime", createtime);
		String isRead = "<font style=\"color: red;\">未读</font>";
		if(isread == 1){
			isRead = "<font style=\"color: blue;\">已读</font>";
		}
		gridRow.addCell("isread", isRead, false);
	}
	
}