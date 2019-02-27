package com.hanweb.jmp.newspush.userextra.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.constant.Tables;
import com.hanweb.complat.service.UserService;

/**
 * 用户成员列表控制器
 * 
 * @author Wangjw
 * 
 */
@Controller
@RequestMapping("manager/userextra/users")
public class ListUsersController implements GridViewDelegate {

	@Autowired
	private GridViewService gridViewService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer infoId, String userIds, String removeUserIds
			, String usids) {
		
		int iid = NumberUtil.getInt(infoId);		
		
		gridView.setShowPageList(false);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/newspush/userextra/users_list");
		createButton(gridView);
		createHead(gridView);
		
		//获得列表页展现需要的id
		List<Integer> sUserIds = getUids(usids, userIds);
		List<Integer> sRemoveUserIds = StringUtil.toIntegerList(removeUserIds);
		sUserIds.removeAll(sRemoveUserIds);
		String sUserIds1 = StringUtil.join(sUserIds, ",");
		
		createBody(gridView, sUserIds1);
		gridView.setShowAdvSearch(false);
		gridView.setPosition("用户管理");
		gridView.setSearchPlaceholder("请输入用户真实姓名"); 
		gridView.addObject("orgType", "s");
		gridView.addQueryParam("infoId", iid);
		gridView.addQueryParam("userIds", sUserIds1);
		gridView.addQueryParam("userNames", userService.getUserNameById(sUserIds1));
		gridView.addQueryParam("usids", userIds);
		
		return gridView;
	}

	/**
	 * 创建按钮
	 * 
	 * @param gridView 
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		Button ok = Button.getInstance("&#xf3004;", "ok", "确定");
		gridView.addButton(ok);
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
	}

	/**
	 * 创建列表
	 * 
	 * @param gridView 
	 * @param userIds 用户ids
	 */
	private void createBody(GridView gridView, String userIds) {
		if("".equals(userIds)){
			userIds = "0";
		}
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
			.addSelectField("u.iid")
			.addSelectField("u.name")
			.setTable(Tables.USER + " u");
			
		StringBuilder where = new StringBuilder();
		where.append("u.iid IN (:userIds)");
		gridViewSql.addParam("userIds", StringUtil.toIntegerList(userIds));
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where.append(" AND (u.name LIKE :name)");
			gridViewSql.addParam("name", gridView.getSearchText(), LikeType.LR);
		}
		gridViewSql.setWhere(where.toString());
		
		gridViewSql.addOrderBy("u.iid", "DESC");
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
	
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", "<span class=\"user\"><i class=\"icon-user-3\"></i>" + name
				+ "</span>", false);
	}
	
	private List<Integer> getUids(String oldIds, String newIds){
		if(StringUtil.isEmpty(oldIds) || "undefined".equals(oldIds)){
			oldIds = "";
		}else{
			oldIds = oldIds + ",";
		}
		List<Integer> l1 = StringUtil.toIntegerList(oldIds + newIds);
		HashSet<Integer> h = new HashSet<Integer>(l1);
	    l1.clear();
	    l1.addAll(h);
	    return l1;
	}
	
}
