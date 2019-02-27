package com.hanweb.jmp.cms.controller.sign;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
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
@RequestMapping(value = "manager/sign")
public class ListCardSignInfoController implements GridViewDelegate  {
	
	/**
	 *  gridViewService
	 */
	@Autowired
	GridViewService gridViewService;

	/**
	 * 列表
	 * @param gridView    gridView
	 * @param did         did
	 * @return  GridView
	 */
	@RequestMapping("infolist")
	public GridView list(GridView gridView, Integer did){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/sign/cardsigninfo_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, 1, siteId, did);
		gridView.setPosition("信息管理");
		gridView.setSearchPlaceholder("请输入信息标题");
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("did", did);
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getRemove());
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
		gridView.addButton(sort);
	}

	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15).setTip(true));
		gridView.addHead(Head.getInstance().setField("nname").setTitle("信息标题 ").setAlign("left").setResizable(true).setTip(true));
	}

	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param mid  mid
	 * @param siteId  siteId
	 * @param did  did
	 */
	private void createBody(GridView gridView, int mid, int siteId, int did) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("b.title nname")
				   .setTable(Tables.DIMENSIONREL + " a," + Tables.INFO +siteId + " b");
		String where = " a.siteid=:siteid AND b.iid = a.attrid";
		if(mid > 0){
			where += " AND a.moduleid=:mid AND a.dimensionid=:did ";
		}
		String nname = gridView.getSearchText();
		if (StringUtil.isNotEmpty(nname)) {
			where += " AND b.title like :nname";
			gridViewSql.addParam("nname", "%" + nname + "%");
		}
		gridViewSql.addParam("mid", mid);
		gridViewSql.addParam("did", did);
		gridViewSql.addParam("siteid", siteId);
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("a.orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(StringUtil.getString(rowData.get("iid")));
		String nname = StringUtil.getString(rowData.get("nname"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		gridRow.addCell("nname", nname);
	}

}