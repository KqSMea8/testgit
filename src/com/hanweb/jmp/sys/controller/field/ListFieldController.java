package com.hanweb.jmp.sys.controller.field;
 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.hanweb.jmp.constant.Tables;

@Controller
@RequestMapping("manager/site/field")
public class ListFieldController implements GridViewDelegate {

	/**
	 * 列表
	 */
	@Autowired
	GridViewService gridViewService = null;

	/**
	 * 获取信息列表页展现
	 * @param gridView gridView
	 * @param siteId siteId  
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer siteId) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/field/field_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, siteId);
		gridView.setPosition("字段管理");
		gridView.setSearchPlaceholder("请输入显示名称");
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("siteId", siteId + "");
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd()); 
		gridView.addButton(Button.getRemove()); 
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("显示名称 ").setAlign("left").setWidth(50).setResizable(true));
		gridView.addHead(Head.getInstance().setField("fieldname").setTitle("数据库字段 ").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("fieldlength").setTitle("字段长度 ").setAlign("center").setWidth(50));
	}

	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param siteId siteId
	 */
	private void createBody(GridView gridView, Integer siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
				.addSelectField("iid")
				.addSelectField("name")
				.addSelectField("fieldname")
				.addSelectField("fieldtype")
				.addSelectField("fieldlength")
				.setTable(Tables.FIELD);
		String where = ""; 
		String name = gridView.getSearchText(); 
		where = "(siteid = :siteId OR siteid = 0)";
		gridViewSql.addParam("siteId", siteId); 
		if (StringUtil.isNotEmpty(name)) {
			where += " AND name like :name";
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
		String fieldname = StringUtil.getString(rowData.get("fieldname"));
		int fieldtype = NumberUtil.getInt(rowData.get("fieldtype"));
		int fieldLength = NumberUtil.getInt(rowData.get("fieldlength"));
		if(fieldtype==2){
			gridRow.addCell("iid", iid).setDisabled(true);
		}else{
			gridRow.addCell("iid", iid);
		}
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		gridRow.addCell("fieldname", fieldname);
		gridRow.addCell("fieldlength", fieldLength);
	}
	
}