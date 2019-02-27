package com.hanweb.jmp.sys.controller.ditch;

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
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/synfield")
public class ListSynFieldController implements GridViewDelegate{
    
    /**
     * gridViewService
     */
    @Autowired
    GridViewService gridViewService;
    
    /**
     * 版本更新列表 
     * @param gridView gridView
     * @return GridView
     */
    @RequestMapping("list")
    public GridView list(GridView gridView, int ditchId) {
        gridView.setDelegate(this);
        gridView.setViewName("jmp/sys/ditch/synfield_list");
        createButton(gridView);
        createHead(gridView);
        createBody(gridView, ditchId);
        gridView.addQueryParam("ditchId", ditchId + "");
        gridView.addObject("ditchId", ditchId + "");
        gridView.setSearchPlaceholder("请输入字段名称");
        gridView.setShowAdvSearch(false);
        return gridView;
    }

    /**
     * 列表数据
     * @param gridView
     * @param ditchId
     */
    private void createBody(GridView gridView, int ditchId) {
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        int siteId = currentUser.getSiteId();
        GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
        gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("orderid")
                   .addSelectField("fieldname").addSelectField("fieldtype")
                   .setTable(Tables.SYNFIELD.replace("synfield", "synfield"+"_"+siteId+"_"+ditchId)); 
        String where = " siteid=:siteId ";
        //检索
        String name = gridView.getSearchText(); 
        if(StringUtil.isNotEmpty(name)){
            where += " AND name LIKE :name";
        }
        gridViewSql.addParam("name", "%" + name + "%");
        gridViewSql.addParam("siteId", siteId);
        gridViewSql.setWhere(where);
        gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
        gridViewService.find(gridViewSql);
    }

    /**
     * 表头
     * @param gridView
     */
    private void createHead(GridView gridView) {
        gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
        gridView.addHead(Head.getInstance().setField("name").setTitle("同步字段名称").setAlign("center").setWidth(50));
        gridView.addHead(Head.getInstance().setField("fieldname").setTitle("数据库字段名称").setAlign("center").setWidth(50));
    }

    /**
     * 按钮
     * @param gridView
     */
    private void createButton(GridView gridView) {
        gridView.addButton(Button.getAdd());
        gridView.addButton(Button.getRemove());
        Button sort = Button.getInstance("&#xf5036;", "order", "排序");
        gridView.addButton(sort);
    }

    /**
     * 创建列表页每一行的数据
     */
    @Override
    public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
        int iid = NumberUtil.getInt(rowData.get("iid"));
        String name = StringUtil.getString(rowData.get("name"));
        String fieldName = StringUtil.getString(rowData.get("fieldname"));
        int fieldType = NumberUtil.getInt(rowData.get("fieldtype"));
        if(fieldType==0){
            gridRow.addCell("iid", iid).setDisabled(true);
        }else{
            gridRow.addCell("iid", iid);
        }
        gridRow.addCell("name", name, Script.createScript("edit", iid));
        gridRow.addCell("fieldname", fieldName);
    }

}