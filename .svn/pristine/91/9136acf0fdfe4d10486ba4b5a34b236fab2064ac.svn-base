package com.hanweb.jmp.apps.controller.manage;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.BaseInfo;
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
import com.hanweb.common.service.GridViewService;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Tables;

/**
 * 应用栏目所绑定的应用展示（需求变动应用栏目支持多选，且在栏目表中展示出来）
 * @author hzz
 *
 */
@Controller
@RequestMapping(value = "manager/lightappforcol")
public class ListLightAppForColController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	@Autowired
	private ColService colService;
	
	/**
	 * 列表页
	 * @param gridView
	 * @param request
	 * @param lightAppIds
	 * @return
	 */
	@RequestMapping(value = "list")
	public GridView list(GridView gridView, HttpServletRequest request, String treeNodeId, String colId) {
		String iid = "";
		if(colId != null){
			iid = colId;
		}
		if(treeNodeId != null){
			iid = treeNodeId;
		}
		Col colEn = colService.findByIid(NumberUtil.getInt(iid));
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/apps/manage/lightappforcol_list");
		createHead(gridView, request);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		createBody(gridView, currentUser.getSiteId(), colEn.getNewLightAppId());
		createButton(gridView);
		gridView.setSearchPlaceholder("请输入应用名称");
		gridView.addQueryParam("lightAppIds", colEn.getNewLightAppId());
		gridView.addObject("lightAppIds", colEn.getNewLightAppId());
		gridView.addObject("colId", iid);
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 * @param request  request
	 */
	public void createHead(GridView gridView, HttpServletRequest request) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(20));
		gridView.addHead(Head.getInstance().setField("name").setTitle("应用名称").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("lighttype").setTitle("应用分类").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("groupname").setTitle("所属机构").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("apptype").setTitle("应用类型").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("isOpen").setTitle("是否启用").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(80));
	}
	
	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param name  name
	 * @param colId  colId
	 * @param siteId  siteId
	 * @param colText  colText
	 * @param colState  colState
	 * @param colType  colType
	 */
	private void createBody(GridView gridView, Integer siteId, String lightAppIds) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("createtime")
		           .addSelectField("isOpen").addSelectField("isdefault").addSelectField("apptype")
		           .addSelectField("url").addSelectField("defaulttype").addSelectField("lighttype")
		           .addSelectField("lighttypename").addSelectField("groupname").setTable(Tables.LIGHTAPP);
		String where = "";
		
		int dbType = BaseInfo.getDbType();
		String name = gridView.getSearchText();
		if (StringUtil.isNotEmpty(name)) {
			where += " AND name like :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}
		
		//主要是按照in的顺序展现数据，不同数据库方法不一样，目前只测试oracle和mysql
		if(dbType == 1){//oracle数据库
			if(StringUtil.isNotEmpty(lightAppIds)){
				List<Integer> idList = StringUtil.toIntegerList(lightAppIds, ",");
				String[] Ids = lightAppIds.split(",");
				StringBuffer sb = new StringBuffer();
				for(int i=1;i<=Ids.length;i++){
					sb.append(Ids[i-1]);
					sb.append(",");
					sb.append(i+",");
				}
				String ss = sb.substring(0, sb.length()-1);
				List<Integer> idsList = StringUtil.toIntegerList(ss, ",");
				where += " iid IN (:idList) ORDER  BY DECODE(iid,:idsList)";
				gridViewSql.addParam("idList", idList);
				gridViewSql.addParam("idsList", idsList);
			}
		}else{//FIELD目前只是在mysql中测试过，如后期增加适配数据库，需自行添加方法
			if(StringUtil.isNotEmpty(lightAppIds)){
				List<Integer> idList = StringUtil.toIntegerList(lightAppIds, ",");
				where += " iid IN (:idList) ORDER  BY FIELD(iid,:idList)";
				gridViewSql.addParam("idList", idList);
			}
		}
		
		
		gridViewSql.setWhere(where);
//		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 * @param colId  colId
	 */
	public void createButton(GridView gridView) {
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
        gridView.addButton(sort);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name")); 
		int isOpen = NumberUtil.getInt(rowData.get("isopen"));
		int appType = NumberUtil.getInt(rowData.get("apptype"));
		String url = StringUtil.getString(rowData.get("url"));
		int isdefault = NumberUtil.getInt(rowData.get("isdefault")); 
		String lighttypename = StringUtil.getString(rowData.get("lighttypename")); 
		String groupname = StringUtil.getString(rowData.get("groupname"));
		String isEnable = "否";
		if(isOpen == 1){
			isEnable = "是";
		}
		gridRow.addCell("isOpen", isEnable);
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		if(isdefault == 1){
			gridRow.addCell("iid", iid).setDisabled(true);
		} else {
			gridRow.addCell("iid", iid);
		}
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("lighttype", lighttypename.length()>0?lighttypename:"默认分组");
		gridRow.addCell("url", url).setTip(url);
		gridRow.addCell("groupname", groupname);
		if(appType == 1){
			gridRow.addCell("apptype", "H5");
		}else if(appType == 2){
			gridRow.addCell("apptype", "Native");
		}
		gridRow.addCell("createTime", createTime);
	}
}