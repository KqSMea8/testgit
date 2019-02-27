package com.hanweb.jmp.sys.controller.sites;

import java.util.Date;
import java.util.Map;
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
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
@Permission
@RequestMapping("manager/site")
public class ListSiteController implements GridViewDelegate{

	/**
	 * gridViewService
	 */
	@Autowired
	GridViewService gridViewService;
	
	/**
	 * 列表页
	 * @param gridView gridView
	 * @param name name
	 * @return    设定参数 .
	*/
	@RequestMapping("list")
	public GridView list(GridView gridView, String name){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();  
		if(!currentUser.isSysAdmin()){
			return null;
		}	
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/sites/site_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView);
		gridView.setPosition("站点管理");
		gridView.setSearchPlaceholder("请输入站点名称");
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getImport());
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid").setHidden(true));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15));
		gridView.addHead(Head.getInstance().setField("name").setTitle("站点名称 ").setAlign("left").setResizable(true));
		int isAutoCreateApp = Configs.getConfigs().getIsAutoCreateApp();
		if(isAutoCreateApp == 1){
			gridView.addHead(Head.getInstance().setField("type").setTitle("站点类型").setAlign("center").setWidth(80));
		}
		gridView.addHead(Head.getInstance().setField("operation").setTitle("操作").setAlign("center").setWidth(230));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("url").setHidden(true));
	}

	/**
	 * 创建列表
	 * @param gridView gridView
	 */
	private void createBody(GridView gridView) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
				.addSelectField("iid")
				.addSelectField("name")
				.addSelectField("createtime")
				.addSelectField("url")
				.addSelectField("appfrom")
				.setTable(Tables.SITE);
		String where = ""; 
		String name = gridView.getSearchText();
		if (StringUtil.isNotEmpty(name)) {
			where = "name like :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(StringUtil.getString(rowData.get("iid")));
		String name = StringUtil.getString(rowData.get("name"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		Integer appfrom = NumberUtil.getInt(StringUtil.getString(rowData.get("appfrom")));
		gridRow.addCell("operation",
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='adminLogin(\"" + iid + "\")' value='维护'></input>&nbsp;" +
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='showField("+iid+")' value='信息字段'></input>&nbsp;"+
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='showColField("+iid+")' value='展现类型扩展'></input>&nbsp;"+
				"<input type='button' class='btn btn-success btn-small'" +
				"onclick='exportSite(\"" + 
					iid + "\", \"" + name + "\")' value='导出'></input>&nbsp;" +
				"<input type='button' class='btn btn-success btn-small' " +
					"onclick='deleteSite(\"" + iid + "\", \"" + name + "\")' value='删除'></input>" 
				, false);
		gridRow.addCell("createtime", createtime);
		String siteType = "";
		if(Configs.getConfigs().getIsAutoCreateApp() == 1 && appfrom == 1){
			siteType = "自助打包创建";
			gridRow.addCell("type", siteType);
		}else if(Configs.getConfigs().getIsAutoCreateApp() == 1 && appfrom == 0){
			siteType = "后台新建";
			gridRow.addCell("type", siteType);
		}
	}

}