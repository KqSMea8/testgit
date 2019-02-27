package com.hanweb.jmp.sys.controller.log;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/interfacelog")
public class ListInterfaceLogController implements GridViewDelegate {

	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;

	/**
	 * 接口访问日志列表
	 * @param request request
	 * @param gridView gridView
	 * @param uuid uuid
	 * @param clientType clientType
	 * @param version version
	 * @param name name
	 * @param starttime starttime
	 * @param endtime endtime
	 * @param iid iid
	 * @return    设定参数 .
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView, String uuid, 
			             String clientType, String version, String name, String starttime, 
			             String endtime, Integer iid) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/log/interfacelog_list");
		gridView.addObject("name", name);
		gridView.addObject("uuid", uuid);
		gridView.addObject("mod_array", InterfaceLogConfig.MOD_ARRAY);
		gridView.addObject("starttime", starttime);
		gridView.addObject("endtime", endtime);
		gridView.addObject("iid", iid);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, uuid, clientType, version, name, starttime, endtime, iid);
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
		}
		Button chart = Button.getInstance("&#xf5043;", "chart", "统计图");
		gridView.addButton(chart);
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView){
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("uuid").setTitle("客户端唯一码").setAlign("left"));
		gridView.addHead(Head.getInstance().setField("clientType").setTitle("客户端类型").setAlign("center").setWidth(65));
		gridView.addHead(Head.getInstance().setField("version").setTitle("版本号").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("name").setTitle("接口名").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("siteName").setTitle("所属站点").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("oprTime").setTitle("操作时间").setAlign("center").setWidth(100));
	}
 
	 
	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param uuid uuid
	 * @param clientType clientType
	 * @param version version
	 * @param name name
	 * @param starttime starttime
	 * @param endtime endtime
	 * @param iid    设定参数 .
	*/
	private void createBody(GridView gridView, String uuid, String clientType, String version, 
			                String name, String starttime, String endtime, Integer iid) {
		int siteId = 0;
		if(NumberUtil.getInt(iid) == 0){
		    siteId = UserSessionInfo.getCurrentUser().getSiteId();
		}else{
			siteId = iid;
		}
		if(NumberUtil.getInt(siteId)==0){
			List<Site> siteList = siteService.findAll();
			if(!CollectionUtils.isEmpty(siteList)){
				Site siteEn=siteList.get(0);
				siteId=siteEn.getIid();
			}
		}
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("siteid").addSelectField("uuid")
			       .addSelectField("clienttype").addSelectField("version").addSelectField("name")
			       .addSelectField("oprtime").setTable("jmp_interface_log" + siteId);
		String where = "";
		if (UserSessionInfo.getCurrentUser().getIsWebSiteAdmin()) {
			if(StringUtil.isEmpty(where)){
				where += " siteid=:siteid";
			}else{
				where += " AND siteid=:siteid";
			}	
			gridViewSql.addParam("siteid", siteId);
		}
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where += " AND uuid LIKE :uuid";
			gridViewSql.addParam("uuid", "%" + gridView.getSearchText() + "%");
		}
		if (StringUtil.isNotEmpty(uuid)) {
			if(StringUtil.isEmpty(where)){
				where += " uuid=:uuid";
			}else{
				where += " AND uuid=:uuid";
			}
			gridViewSql.addParam("uuid", uuid);
		}
		if (StringUtil.isNotEmpty(clientType)) {
			if(StringUtil.isEmpty(where)){
				where += " clienttype=:clienttype";
			}else{
				where += " AND clienttype=:clienttype";
			}
			gridViewSql.addParam("clienttype", clientType);
		}
		if (StringUtil.isNotEmpty(version)) {
			if(StringUtil.isEmpty(where)){
				where += " version=:version";
			}else{
				where += " AND version=:version";
			}
			gridViewSql.addParam("version", version);
		}
		if (StringUtil.isNotEmpty(name)) {
			if(StringUtil.isEmpty(where)){
				where += " name=:name";
			}else{
				where += " AND name=:name";
			}
			gridViewSql.addParam("name", name);
		} 
		if (StringUtil.isNotEmpty(starttime)) {
			if(StringUtil.isEmpty(where)){
				where += " oprtime >= :starttime";
			}else{
				where += " AND oprtime >= :starttime";
			}
			gridViewSql.addParam("starttime", starttime);
		} 
		if (StringUtil.isNotEmpty(endtime)) {
			if(StringUtil.isEmpty(where)){
				where += " oprtime <= :endtime";
			}else{
				where += " AND oprtime <= :endtime";
			}
			gridViewSql.addParam("endtime", endtime);
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt((rowData.get("iid")));
		String clientType = StringUtil.getString(rowData.get("clienttype"));
		String oprTime = DateUtil.dateToString((Date) rowData.get("oprtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		String name = StringUtil.getString(rowData.get("name"));
		int siteId = NumberUtil.getInt((rowData.get("siteid")));
		String uuid = StringUtil.getString(rowData.get("uuid"));
		String version = StringUtil.getString(rowData.get("version"));
		if("1".equals(clientType)){
			clientType = "UC";
		}else if("2".equals(clientType)){
			clientType = "iPhone";
		}else if("3".equals(clientType)){
			clientType = "Android";
		}else if("4".equals(clientType)){
			clientType = "iPad";
		}
		Site site = siteService.findByIid(siteId);
		String siteName = "";
		if(site != null){
			siteName = site.getName();
		}
		gridRow.addCell("clientType", clientType);
		gridRow.addCell("oprTime", oprTime);
		gridRow.addCell("name", name);
		gridRow.addCell("uuid", uuid);
		gridRow.addCell("version", version);
		gridRow.addCell("iid", iid);
		gridRow.addCell("siteName", siteName);
	}
	
}