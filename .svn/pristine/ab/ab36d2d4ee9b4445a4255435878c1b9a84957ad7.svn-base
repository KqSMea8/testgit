package com.hanweb.jmp.sys.controller.log;

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
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.support.controller.CurrentUser;

@Controller
//@Permission
@RequestMapping("manager/log")
public class ListLogController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * logService
	 */
	@Autowired
	private LogService logService;

	/**
	 * 操作日志列表.
	 * @param request request
	 * @param gridView gridView
	 * @param siteName siteName
	 * @param userName userName
	 * @param groupName groupName
	 * @param module module
	 * @param oprtype oprtype
	 * @param starttime starttime
	 * @param endtime endtime
	 * @return    设定参数 .
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView, String siteName, String userName, String groupName,
			             String module, String oprtype, String starttime, String endtime) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/log/log_list");
		gridView.addObject("mod_array", LogConfig.MOD_ARRAY);
		gridView.addObject("opr_array", LogConfig.OPR_ARRAY);
		gridView.addObject("siteName", siteName);
		gridView.addObject("userName", userName);
		gridView.addObject("groupName", groupName);
		gridView.addObject("module", module);
		gridView.addObject("oprtype", oprtype);
		gridView.addObject("starttime", starttime);
		gridView.addObject("endtime", endtime);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, siteName, userName, groupName, module, oprtype, starttime, endtime);
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser.isSysAdmin()) {
			gridView.addButton(Button.getInstance("&#xf5016;", "reset", "设置"));
			gridView.addButton(Button.getRemove());
			gridView.addButton(Button.getClean());
		}
		Button chart = Button.getInstance("&#xf5043;", "chart", "统计图");
		gridView.addButton(chart);
		
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("content").setTitle("操作描述").setAlign("left").setTip(true));
		gridView.addHead(Head.getInstance().setField("oprtime").setTitle("操作时间").setAlign("center").setWidth(65));
		gridView.addHead(Head.getInstance().setField("oprname").setTitle("操作人").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("groupName").setTitle("操作人所属机构").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("modulename").setTitle("功能模块").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("funcname").setTitle("操作类型").setAlign("center").setWidth(35));
		gridView.addHead(Head.getInstance().setField("sitename").setTitle("所属站点").setAlign("center").setWidth(60));
		gridView.addHead(Head.getInstance().setField("ipaddr").setTitle("操作IP").setAlign("center").setWidth(65));

	}

	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param siteName siteName
	 * @param module module
	 * @param oprtype oprtype
	 * @param starttime starttime
	 * @param endtime    设定参数 .
	 */
	private void createBody(GridView gridView, String siteName, String userName, String groupName, String module, String oprtype, 
			                String starttime, String endtime) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.content")
				   .addSelectField("a.username").addSelectField("a.oprtime")
				   .addSelectField("a.moduleid").addSelectField("a.funcid")
				   .addSelectField("a.ip").addSelectField("a.groupname").addSelectField("b.name").setTable(Tables.LOG + " a, " + Tables.SITE + " b");
		String where = "a.siteid=b.iid";
		if (UserSessionInfo.getCurrentUser().getIsWebSiteAdmin()) {
			where += " AND a.siteid LIKE :siteid";
			gridViewSql.addParam("siteid", UserSessionInfo.getCurrentUser().getSiteId());
		}
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where += " AND a.content LIKE :content";
			gridViewSql.addParam("content", "%" + gridView.getSearchText() + "%");
		}
		if (StringUtil.isNotEmpty(siteName)) {
			where += " AND b.name LIKE :sitename";
			gridViewSql.addParam("sitename", "%" + siteName + "%");
		}
		if (StringUtil.isNotEmpty(userName)) {
            where += " AND a.username LIKE :userName";
            gridViewSql.addParam("userName", "%" + userName + "%");
        }
		if (StringUtil.isNotEmpty(groupName)) {
            where += " AND a.groupname LIKE :groupName";
            gridViewSql.addParam("groupName", "%" + groupName + "%");
        }
		if (StringUtil.isNotEmpty(module) && !"0".equals(module)) {
			where += " AND a.moduleid = :modid";
			gridViewSql.addParam("modid", NumberUtil.getInt(module));
		}
		if (StringUtil.isNotEmpty(oprtype) && !"0".equals(oprtype)) {
			where += " AND a.funcid = :funcid";
			gridViewSql.addParam("funcid", NumberUtil.getInt(oprtype));
		}
		if (StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)) {
			where += " AND a.oprtime BETWEEN :starttime AND :endtime";
			gridViewSql.addParam("starttime", starttime);
			gridViewSql.addParam("endtime", endtime);
		} else if (StringUtil.isNotEmpty(starttime)) {
			where += " AND a.oprtime >= :starttime";
			gridViewSql.addParam("starttime", starttime);
		} else if (StringUtil.isNotEmpty(endtime)) {
			where += " AND a.oprtime <= :endtime";
			gridViewSql.addParam("endtime", endtime);
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("a.iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt((rowData.get("iid")));
		String content = StringUtil.getString(rowData.get("content"));
		String oprtime = DateUtil.dateToString((Date) rowData.get("oprtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		String username = StringUtil.getString(rowData.get("username"));
		String groupName = StringUtil.getString(rowData.get("groupName"));
		Integer moduleid = NumberUtil.getInt((rowData.get("moduleid")));
		Integer funcid = NumberUtil.getInt((rowData.get("funcid")));
		String ip = StringUtil.getString(rowData.get("ip"));
		String sitename = StringUtil.getString(rowData.get("name"));
		if(ip.equals("0:0:0:0:0:0:0:1")){
			ip="127.0.0.1";
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("content", content);
		gridRow.addCell("oprtime", oprtime);
		gridRow.addCell("oprname", username);
		gridRow.addCell("groupName", groupName);
		gridRow.addCell("modulename", logService.getArrName(LogConfig.MOD_ARRAY, moduleid));
		gridRow.addCell("funcname", logService.getArrName(LogConfig.OPR_ARRAY, funcid));
		gridRow.addCell("ipaddr", ip);
		gridRow.addCell("sitename", sitename);
	}

}