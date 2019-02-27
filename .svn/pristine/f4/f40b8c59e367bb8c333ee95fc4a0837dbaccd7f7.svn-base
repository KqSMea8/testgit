package com.hanweb.jmp.pack.backstage.controller.appuser;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head; 
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

/**
 * 外网用户列表控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@RequestMapping("manager/appuser")
public class ListAppUserController implements GridViewDelegate {

	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 
	 * list:(这里用一句话描述这个方法的作用).
	 *
	 * @param gridView gridView
	 * @param name name
	 * @param loginName loginName
	 * @return    设定参数 .
	 */
	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, String name, String loginName) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/pack/backstage/appuser/appuser_list");
		createButton(gridView);
		createHead(gridView);
		gridView.setShowAdvSearch(false);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		createBody(gridView, name, loginName, siteId);
		gridView.setPosition("注册用户管理");
		gridView.setSearchPlaceholder("请输入用户姓名或登录名");

		gridView.addObject("name", name);
		gridView.addObject("loginName", loginName);
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
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("username").setTitle("用户名").setAlign("left")
				.setWidth(100).setResizable(true));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间")
				.setAlign("center").setWidth(100)); 
	}
 
	/**
	 * 
	 * createBody:(这里用一句话描述这个方法的作用).
	 *
	 * @param gridView gridView
	 * @param name name
	 * @param loginName loginName
	 * @param siteId    设定参数 .
	 */
	private void createBody(GridView gridView, String name, String loginName, Integer siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("username").addSelectField("createtime")
				.setTable(com.hanweb.jmp.constant.Tables.APPUSER);
		String where = " siteid=:siteid";
		gridViewSql.addParam("siteid", siteId);
		String userName = gridView.getSearchText();
		if (StringUtil.isNotEmpty(userName)) {
			where += " AND (username LIKE :userName)";
			gridViewSql.addParam("userName", userName, LikeType.LR);
		} else {
			if (StringUtil.isNotEmpty(name)) {
				where += " AND username LIKE :name";
				gridViewSql.addParam("name", name, LikeType.LR);
			}
			if (StringUtil.isNotEmpty(loginName)) {
				where += " AND username LIKE :loginName";
				gridViewSql.addParam("loginName", loginName, LikeType.LR);
			}
		}

		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", "DESC");
		gridViewService.find(gridViewSql);
	}
 
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String username = StringUtil.getString(rowData.get("username"));
		Date createtime = (Date)rowData.get("createtime");  
	 
		
		gridRow.addCell("iid", iid);
		gridRow.addCell("username", username, Script.createScript("edit", iid, username));
		gridRow.addCell("createtime", DateUtil.dateToString(createtime, 
				DateUtil.YYYY_MM_DD_HH_MM_SS));
		 

	}

}
