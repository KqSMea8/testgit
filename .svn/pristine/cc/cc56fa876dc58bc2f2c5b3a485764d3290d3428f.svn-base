package com.hanweb.jmp.cms.controller.comment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
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

@Controller
//@Permission(module = "/comment", allowedAdmin = Allowed.YES)
@RequestMapping("manager/comment")
public class ListCommentController implements GridViewDelegate {

	/** 
	 *   gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 列表页
	 * @param gridView  gridView
	 * @param infoId    infoId
	 * @param type      type
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer infoId, Integer type) {
		gridView.setShowPageList(false);
		if(NumberUtil.getInt(infoId) > 0){
			gridView.setPageList("10");
			gridView.setPageSize(10);
			gridView.setPagination(true);
		}  
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/comment/comment_list");
		createButton(gridView);
		createHead(gridView);
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		createBody(gridView, searchText, siteId, infoId, type);
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("siteId", siteId);
		gridView.addQueryParam("type", type);
		gridView.addQueryParam("infoId", infoId);
		gridView.setSearchPlaceholder("请输入评论内容");
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getRemove());
		Button audit = Button.getInstance("&#xf3004;", "audit", "审核");
	    gridView.addButton(audit);
	    Button unaudit = Button.getInstance("&#xf3003;", "unaudit", "撤审");
	    gridView.addButton(unaudit);
	}

	/**
	 * 创建表头
	 * @param gridView   gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("content").setTitle("评论内容").setAlign("left").setWidth(200).setResizable(true));
		gridView.addHead(Head.getInstance().setField("type").setTitle("评论类型").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("评论时间").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("ip").setTitle("IP地址").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("state").setTitle("状态").setAlign("center").setWidth(50));
	}

	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param searchText  searchText
	 * @param siteId  siteId
	 * @param infoId  infoId
	 * @param type  type
	 */
	private void createBody(GridView gridView, String searchText, int siteId, Integer infoId, Integer type) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("content").addSelectField("type")
		           .addSelectField("createtime").addSelectField("ip").addSelectField("state")
		           .setTable(Tables.COMMENT);
		String where = "";
		if(NumberUtil.getInt(siteId) > 0){
		    where = "siteid=:siteid";
		    gridViewSql.addParam("siteid", siteId);
		}
		if(NumberUtil.getInt(infoId) > 0){
			where += " AND infoid=:infoid";
			gridViewSql.addParam("infoid", infoId);
		}
		if(NumberUtil.getInt(type) > 0){
			where += " AND type=:type";
			gridViewSql.addParam("type", type);
		}
		if (StringUtil.isNotEmpty(searchText)) {
			where += " AND content LIKE :content";
			gridViewSql.addParam("content", searchText, LikeType.LR);
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String content = StringUtil.getString(rowData.get("content"));
		String createtime = StringUtil.getString(rowData.get("createtime"));
		String ip = StringUtil.getString(rowData.get("ip"));
		int state = NumberUtil.getInt(rowData.get("state"));
		int type = NumberUtil.getInt(rowData.get("type"));
		String stateName = "";
		switch (state) {
		case 0:
			stateName = "<font color='red'>未审核</font>";
			break;
		case 1:
			stateName = "<font color='green'>已审核</font>";
			break;
		case 2:
			stateName = "<font color='blue'>已撤审</font>";
			break;
		default:
			break;
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("content", content, Script.createScript("edit", iid));
		String commentType = "报料";
		if(type == 1){
			commentType = "信息";
		}
		gridRow.addCell("type", commentType);
		gridRow.addCell("createtime", createtime);
		gridRow.addCell("ip", ip);
		gridRow.addCell("state", stateName, false);
	}
	
}