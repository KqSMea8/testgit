package com.hanweb.jmp.newspush.news.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.constant.Tables;

/**
 * 消息详情列表控制器
 * 
 * @author Wangjw
 * 
 */
@Controller
@RequestMapping("manager/infodetail")
public class ListNewsDetailController implements GridViewDelegate {

	@Autowired
	private GridViewService gridViewService;
	
	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer infoId, Integer ustate, Integer state) {
		gridView.setShowPageList(false);
		gridView.setPageSize(10);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/newspush/news/newsdetail_list");
		createButton(gridView);
		createHead(gridView);
		
		createBody(gridView, infoId, ustate, state);
		gridView.setShowAdvSearch(false);
		gridView.setPosition("详情管理");
		gridView.setSearchPlaceholder("请输入用户真实姓名"); 
		
		gridView.addQueryParam("infoId", infoId);
		gridView.addObject("ustate", ustate);
		gridView.addObject("state", state);
		
		return gridView;
	}

	/**
	 * 创建按钮
	 * 
	 * @param gridView
	 */
	private void createButton(GridView gridView) {
//		gridView.addButton(Button.REMOVE);
//		gridView.addButton(Button.CLEAN);
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("真实姓名").setAlign("left")
				.setResizable(true));
		gridView.addHead(Head.getInstance().setField("loginname").setTitle("登录名")
				.setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("istate").setTitle("消息状态")
				.setAlign("center").setWidth(80));
		/*gridView.addHead(Head.getInstance().setField("online").setTitle("用户状态")
				.setAlign("center").setWidth(80));*/
		gridView.addHead(Head.getInstance().setField("sendtime").setTitle("发送时间")
				.setAlign("center").setWidth(125));
		gridView.addHead(Head.getInstance().setField("receipttime").setTitle("查看时间")
				.setAlign("center").setWidth(125));
//		gridView.addHead(Head.getInstance().setField("detail").setTitle("查看回执")
//				.setAlign("center").setWidth(110));
	}

	/**
	 * 创建列表
	 * 
	 * @param gridView
	 */
	private void createBody(GridView gridView, Integer infoId, Integer ustate, Integer state) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
			.addSelectField("u.iid t")
			.addSelectField("u.name")
			.addSelectField("u.loginname")
			.addSelectField("r.state")
			.addSelectField("u.islogin")
			.addSelectField("r.sendtime")
			.addSelectField("r.receipttime")
			//.addSelectField("u.hearttime")
			.addSelectField("r.iid t1")
			.setTable(
				Tables.USER + " u , " + com.hanweb.jmp.constant.Tables.INFODETAIL
						+ " r ");
		StringBuilder where = new StringBuilder();
		where.append(" u.iid = r.usid ");
		if(NumberUtil.getInt(infoId) != 0){
			where.append(" AND r.infoid=:infoid");
			gridViewSql.addParam("infoid", infoId);
		}
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where.append(" AND (u.name LIKE :name)");
			gridViewSql.addParam("name", gridView.getSearchText(), LikeType.LR);
		}
		/*if (NumberUtil.getInt(state) > -1) {
			Date d = new Date(new Date().getTime() - 1800000);
			if(NumberUtil.getInt(state) == 0){
				where.append(" AND (u.hearttime<:hearttime OR u.hearttime IS NULL)");
			}else{
				where.append(" AND u.hearttime>:hearttime");
			}
			gridViewSql.addParam("hearttime", d);
		}*/
		if (NumberUtil.getInt(ustate) > -1) {
			where.append(" AND r.state=:ustate");
			gridViewSql.addParam("ustate", ustate);
		}
		gridViewSql.setWhere(where.toString());
		
		gridViewSql.addOrderBy("r.iid", "DESC");
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String usid = StringUtil.getString(rowData.get("t"));
		String name = StringUtil.getString(rowData.get("name"));
		String loginname = StringUtil.getString(rowData.get("loginname"));
		int istate = NumberUtil.getInt(rowData.get("state"));
		String sendtime = DateUtil.dateToString(
				(Date) rowData.get("sendtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		String receipttime = DateUtil.dateToString(
				(Date) rowData.get("receipttime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		int infodetailId = NumberUtil.getInt(rowData.get("t1"));
		Date heartTime = (Date) rowData.get("hearttime");
		boolean online = false;
		if(heartTime != null){
			online = new Date().getTime() - heartTime.getTime() <= 1800000 ;
		}
	
		gridRow.addCell("iid", infodetailId);
		gridRow.addCell("name", "<span class=\"user\" style='cursor:pointer' onclick='show(" + 
				usid + ")'><i class=\"icon-user-3\"></i>" + name + "</span>", false);
		gridRow.addCell("loginname", loginname);
		gridRow.addCell("istate", istate == 0?"<font color='red'>未阅读</font>":
			(istate == 1?"<font color='red'>未阅读</font>":"<font color='blue'>已阅读</font>"),false);
		gridRow.addCell("online", online ? "<font color='blue'>在线</font>"
				: "<font color='red'>离线</font>",false);
		gridRow.addCell("sendtime", sendtime);
		gridRow.addCell("receipttime", receipttime);
	}
}
