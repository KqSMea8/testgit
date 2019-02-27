package com.hanweb.jmp.sys.controller.log;

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
@RequestMapping("manager/offlineziplog")
public class ListOfflineZipController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 操作日志列表
	 * @param request request
	 * @param gridView gridView
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/log/offlineziplog_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, searchText);
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser.isSysAdmin()) {
			gridView.addButton(Button.getRemove());
			gridView.addButton(Button.getClean());
		}
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("离线下载栏目").setAlign("left").setTip(true));
		gridView.addHead(Head.getInstance().setField("offlineNum").setTitle("打包条数").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("ziptime").setTitle("打包时间").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("iszip").setTitle("打包状态").setAlign("center").setWidth(50));
	}
	 
	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param searchText    设定参数 .
	*/
	private void createBody(GridView gridView, String searchText) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("b.name").addSelectField("b.offlinenum")
			       .addSelectField("a.ziptime").addSelectField("a.iszip").setTable(Tables.OFFLINEZIP + " a, " + Tables.COL+" b");
		String where = " a.colid = b.iid";
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where += " AND b.name LIKE :name";
			gridViewSql.addParam("name", searchText, LikeType.LR);	
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt((rowData.get("iid")));
		String name = StringUtil.getString(rowData.get("name"));
		Integer offlineNum = NumberUtil.getInt((rowData.get("offlineNum")));
		String ziptime = DateUtil.dateToString((Date) rowData.get("ziptime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		Integer iszip = NumberUtil.getInt((rowData.get("iszip")));
		String zipStr="失败";
		if(NumberUtil.getInt(iszip)==1){
			zipStr="成功";
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("offlineNum", offlineNum);
		gridRow.addCell("name", name);
		gridRow.addCell("ziptime", ziptime);
		gridRow.addCell("iszip", zipStr);
	}
	
}