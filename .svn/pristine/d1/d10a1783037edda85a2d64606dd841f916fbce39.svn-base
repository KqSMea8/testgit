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
@RequestMapping("manager/site/colfield")
public class ListColFieldController implements GridViewDelegate {
	
	/**
	 * 列表
	 */
	@Autowired
	GridViewService gridViewService = null;
	
	/**
	 * 列表页
	 * @param gridView gridView
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer siteId) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/field/colfield_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, siteId);
		gridView.setPosition("展现类型扩展");
		gridView.setSearchPlaceholder("请输入展现名称");
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("siteId", siteId);
		return gridView;
	}
	 
	/**
	 * 创建按钮
	 * @param gridView    设定参数 .
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd()); 
		gridView.addButton(Button.getRemove()); 
	}
	 
	/**
	 * 创建表头.
	 * @param gridView    设定参数 .
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("fieldname").setTitle("展现名称 ").setAlign("left").setWidth(50).setResizable(true));
		gridView.addHead(Head.getInstance().setField("fieldkey").setTitle("键值").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("fieldtype").setTitle("展现类型").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("showlist").setTitle("列表显示").setAlign("center").setWidth(50));	 
	}
	 
	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param siteId    设定参数 .
	*/
	private void createBody(GridView gridView, Integer siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
				.addSelectField("iid")
				.addSelectField("fieldname")
				.addSelectField("fieldkey")
				.addSelectField("fieldtype")
				.addSelectField("showlist")
				.setTable(Tables.COLFIELD);
		String where = ""; 
		String name = gridView.getSearchText(); 
		where = "siteid = :siteId";
		gridViewSql.addParam("siteId", siteId); 
		if (StringUtil.isNotEmpty(name)) {
			where += " AND fieldname like :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("fieldtype", GridViewSql.SORT_ASC);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}
	
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(StringUtil.getString(rowData.get("iid")));	
		String fieldname = StringUtil.getString(rowData.get("fieldname"));
		int fieldtype = NumberUtil.getInt(rowData.get("fieldtype"));
		int fieldkey = NumberUtil.getInt(rowData.get("fieldkey"));
		int showlist = NumberUtil.getInt(rowData.get("showlist"));	
		String strShow="否";
		String fieldtypename ="";
		if(fieldtype==1){
			gridRow.addCell("iid", iid);
			fieldtypename = "子栏目展现方式（虚拟栏目）";
		}else if(fieldtype==2){
			gridRow.addCell("iid", iid);
			fieldtypename = "信息布局（普通栏目）";
		}else if(fieldtype==3){
			gridRow.addCell("iid", iid);
			fieldtypename = "信息列表展现方式（普通栏目）";
		}else if(fieldtype==4){
			gridRow.addCell("iid", iid);
			fieldtypename = "信息内容展现方式（普通栏目）";
		}else if(fieldtype==5){
			gridRow.addCell("iid", iid);
			fieldtypename = "互动类型（互动栏目）";
		}else{
			gridRow.addCell("iid", iid).setDisabled(true);
		}
		gridRow.addCell("fieldname", fieldname, Script.createScript("edit", iid, fieldname));
		gridRow.addCell("fieldtype", fieldtypename);
		gridRow.addCell("fieldkey", fieldkey);
		if(showlist==1){
			strShow="是";
		}
		gridRow.addCell("showlist", strShow);
	}
	
}