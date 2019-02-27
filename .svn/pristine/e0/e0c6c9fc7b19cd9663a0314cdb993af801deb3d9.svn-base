package com.hanweb.jmp.cms.controller.infos.info;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.dao.role.RoleColDAO;
import com.hanweb.jmp.sys.entity.role.RoleCol;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/info")
public class ListInfoController implements GridViewDelegate {

	/**
	 * GridViewService.
	 */
	@Autowired
	GridViewService gridViewService = null;
	
	/**
	 * 栏目操作类.
	 */
	@Autowired
	private ColService colService; 
	
	/**
	 * roleColDAO
	 */
	@Autowired
	private RoleColDAO roleColDAO;
	
	/**
	 * siteService.
	 */
	@Autowired
	private SiteService siteService; 
	
	/**
	 * userService
	 */
	@Autowired
	private UserService userService; 
	
	private int flag=0;
	
	
	/**
	 * list:列表页
	 * @param gridView gridView
	 * @param colId 栏目id
	 * @param colName 栏目名称
	 * @param infoTitle 信息标题
	 * @param infoState 信息状态
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param fromutl 原路径
	 * @return    设定参数 .
	*/
	@SuppressWarnings("deprecation")
    @RequestMapping("list")
	public GridView list(GridView gridView, String colId, String colName, String infoTitle, String infoState, 
		String starttime, String endtime, String fromutl, Integer type) {
		Col colEn = null; 
		flag = NumberUtil.getInt(colId);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		//默认跳转到第一个栏目
		if (StringUtil.isEmpty(colName)) {
			//信息维护栏目权限
			List<RoleCol> colList = roleColDAO.findByUserId(currentUser.getIid(), currentUser.getGroupId());
			if(colList != null && colList.size()>0){
				boolean bl = false;
				for(RoleCol r : colList){
					if(NumberUtil.getInt(colId) == r.getColId()){
						bl = true;
						continue;
					}
				}
				if(!bl){
					colId = StringUtil.getString(colList.get(0).getColId());
				}
			}
		}  
		if(StringUtil.isNotEmpty(colId) && !"0".equals(colId)){
			colEn = colService.findByIid(NumberUtil.getInt(colId));
			colName = colEn.getName(); 
		}
		gridView.setDelegate(this); 
		gridView.setViewName("jmp/cms/infos/info_list");
		createButton(gridView, colId, fromutl);
		createHead(gridView);
		createBody(gridView, NumberUtil.getInt(colId), infoTitle, infoState, starttime, endtime, fromutl);
		if (StringUtil.isEmpty(colName)) {
			gridView.addQueryParam("colName", "全部信息");
		} else {
			gridView.addQueryParam("colName", colName);
		}
		gridView.addQueryParam("colId", StringUtil.getString(colId)); 
		gridView.setPosition("信息管理");
		gridView.setSearchPlaceholder("请输入信息标题");
		gridView.setShowAdvSearch(true);
		gridView.addObject("infoTitle", infoTitle);
		gridView.addObject("infoState", infoState);
		gridView.addObject("starttime", starttime);
		gridView.addObject("fromutl", fromutl);
		gridView.addObject("endtime", endtime);
		gridView.addObject("colId", NumberUtil.getInt(colId));
		gridView.addObject("weixinUrl", Configs.getConfigs().getWeixinUrl());
		gridView.addObject("siteId", currentUser.getSiteId());
		String sessionId = SpringUtil.getRequest().getSession().getId();
		gridView.addObject("sessionId", sessionId);
		gridView.addObject("wxSupport", Configs.getConfigs().getWxSupport());
		String loginName = currentUser.getLoginName();
		String key = Configs.getConfigs().getKey();
		String serviceCode = Configs.getConfigs().getServiceCode();
		loginName = Md5Util.md5encode(loginName, key);
		serviceCode = Md5Util.md5encode(serviceCode, key);
		gridView.addObject("loginName", loginName);
		gridView.addObject("serviceCode", serviceCode);
		return gridView;
	}
	 
	/**
	 * 创建按钮
	 * @param gridView gridView
	 * @param colId 栏目id
	 * @param fromutl    设定参数 .
	 */
	private void createButton(GridView gridView, String colId, String fromutl) {
		int colid = NumberUtil.getInt(colId);
		int subnum = colService.findSubCol(colid);
		Col col = colService.findByIid(colid);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		Set<String> colRightids = currentUser.getColids(); 
		if(colid > 0 && subnum <= 0 && col.getType() == 2){
			if(currentUser.getIsWebSiteAdmin() || colRightids.contains("edit-"+colid) || colRightids.contains("manage-"+colid)){
				gridView.addButton(Button.getAdd()); 
				gridView.addButton(Button.getRemove());
			}
		}
	    if(colid > 0 && subnum <= 0 && col.getType() == 2){
			if(UserSessionInfo.getCurrentUser().getIsWebSiteAdmin()){
				gridView.addButton(Button.getClean());
			}
			if(NumberUtil.getInt(col.getSortType()) == 0){
				Button sort = Button.getInstance("&#xf5036;", "order", "排序");
			    gridView.addButton(sort);
			}
			 Button recycle = Button.getInstance("&#xf5079;", "recycle", "回收站");
			    gridView.addButton(recycle);
		}
	    if(StringUtil.isNotEmpty(fromutl)){
	    	Button back = Button.getInstance("&#xf0009;", "back", "返回");
		    gridView.addButton(back);
	    } 
	    if(colid > 0 && subnum <= 0 && col.getType() == 2){
            Button syninfo = Button.getInstance("&#xf5022;", "syninfo", "信息同步");
            gridView.addButton(syninfo);
           
            if(currentUser.getIsWebSiteAdmin()){
                Button quote = Button.getInstance("&#xf0037;", "quote", "引用");
                gridView.addButton(quote);
                
                Button more = Button.getInstance(null, null, "更多");
                more.addSubButton(Button.getCopy()); 
                Button transfer = Button.getInstance("&#xf0036;", "transfer", "转移");
                more.addSubButton(transfer); 
                
                if(NumberUtil.getInt(Configs.getConfigs().getWxSupport())==1){
                    more.addSubButton(Button.getInstance("&#xf5085;", "weixin", "微信分享"));
                    more.addSubButton(Button.getInstance("&#xf5088;", "weibo", "微博分享"));
                }
                gridView.addButton(more); 
            }
            
	    	
	    	
		}
	    
	}
	 
	/**
	 * 创建头部
	 * @param gridView    设定参数 .
	*/
	private void createHead(GridView gridView) { 
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(30).setTip(true));
		gridView.addHead(Head.getInstance().setField("title").setTitle("信息标题").setAlign("left").setResizable(true).setTip(true)); 
		gridView.addHead(Head.getInstance().setField("colname").setTitle("所属栏目").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("syntime").setTitle("发布时间").setAlign("center").setWidth(80)); 
		gridView.addHead(Head.getInstance().setField("visitcount").setTitle("阅读数").setAlign("center").setWidth(30));  
		gridView.addHead(Head.getInstance().setField("operate").setTitle("操作").setAlign("left").setWidth(200));   
		//gridView.addHead(Head.getInstance().setField("details").setTitle("回执").setAlign("center").setWidth(80));
	}
	 
	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param colId colId
	 * @param infoTitle  infoTitle
	 * @param infoState infoState
	 * @param starttime  starttime
	 * @param endtime    endtime .
	*/
	private void createBody(GridView gridView, Integer colId, String infoTitle, String infoState, String starttime, String endtime, String loginName) { 
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		loginName = currentUser.getLoginName();
		User user = userService.findByLoginName1(loginName);
		int siteId = user.getSiteId();
		gridViewSql.addSelectField("i.iid").addSelectField("i.title").addSelectField("i.pushstate")
		           .addSelectField("(SELECT b.name FROM " + Tables.COL + " b  WHERE b.iid = i.colid) colname")
		           .addSelectField("i.createtime").addSelectField("i.syntime").addSelectField("i.topid")
		           .addSelectField("i.colid").addSelectField("i.status").addSelectField("i.infocontenttype").addSelectField("i.infotype")
				   .addSelectField("(SELECT MAX(v.visitcount) FROM " + Tables.INFO_COUNT + siteId + " v WHERE v.titleid = i.iid AND v.type=1) visitcount")
				   .setTable("jmp_info"+siteId + " i");
		String where = "i.siteid=:siteId AND i.isremove=0"; 
		gridViewSql.addParam("siteId", currentUser.getSiteId());
		if (NumberUtil.getInt(colId) > 0) {
			where += " AND  i.colid = :colId";
			gridViewSql.addParam("colId", colId);
		} 
		//检索
		String title = gridView.getSearchText();  
		if (StringUtil.isNotEmpty(title)) {
			if(StringUtil.isEmpty(where)){
				where+=" i.title LIKE :title";
			} else {
				where+=" AND i.title LIKE :title";
			} 
			gridViewSql.addParam("title", "%" + title + "%");
		} else {
			if (StringUtil.isNotEmpty(infoTitle)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.title LIKE :title";
				} else {
					where+=" AND i.title LIKE :title";
				}  
				gridViewSql.addParam("title", "%" + infoTitle + "%");
			} 
			if (StringUtil.isNotEmpty(infoState) && !"-1".equals(infoState)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.status = :status";
				} else {
					where+=" AND i.status = :status";
				}   
				gridViewSql.addParam("status", infoState);
			} 
			if (StringUtil.isNotEmpty(starttime)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.createtime >= :starttime";
				} else {
					where+=" AND i.createtime >= :starttime";
				}    
				gridViewSql.addParam("starttime", starttime+" 00:00:00'");
			} 
			if (StringUtil.isNotEmpty(endtime)) {
				if(StringUtil.isEmpty(where)){
					where+=" i.createtime <= :endtime";
				} else {
					where+=" AND i.createtime <= :endtime";
				}    
				gridViewSql.addParam("endtime", endtime+" 23:59:59'");
			}  
		} 
		gridViewSql.setWhere(where);
		// 栏目下信息的排序方式
		Col col = colService.findByIid(colId);
		if(col != null){
			int sortType = NumberUtil.getInt(col.getSortType());
			if(sortType == 1){
				gridViewSql.addOrderBy("i.syntime", GridViewSql.SORT_DESC); 
			} else {
				gridViewSql.addOrderBy("i.topid", GridViewSql.SORT_DESC);  
				gridViewSql.addOrderBy("i.orderid", GridViewSql.SORT_ASC);
			}
		} else {
			gridViewSql.addOrderBy("i.syntime", GridViewSql.SORT_DESC);
		}
		gridViewService.find(gridViewSql);
	}
	
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String title = StringUtil.getString(rowData.get("title")); 
		String colname = StringUtil.getString(rowData.get("colname")); 
		int topid = NumberUtil.getInt(rowData.get("topid"));
		int colid=NumberUtil.getInt(rowData.get("colid"));
		int pushstate = NumberUtil.getInt(rowData.get("pushstate"));
		String status = StringUtil.getString(rowData.get("status"));
		String infoType = StringUtil.getString(rowData.get("infotype"));
		int visitCount =  NumberUtil.getInt(rowData.get("visitcount"));
		int infocontenttype=NumberUtil.getInt(rowData.get("infocontenttype"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		Set<String> colRightids=currentUser.getColids();
		
		if(currentUser.getIsWebSiteAdmin() || colRightids.contains("edit-"+colid)){
    		if(topid > 0){
    		    if("C".equals(infoType)){
    		        gridRow.addCell("title",  "<font color='red'>[复]" + "</font>" + "<font color='red'> [顶]" + "</font>" + title, Script.createScript("edit", iid), false);
    	        } else if("Q".equals(infoType)){
    	            gridRow.addCell("title",  "<font color='red'>[引]" + "</font>" + "<font color='red'> [只读]" + "</font>" + "</font>" + "<font color='red'> [顶]" + "</font>" + title, Script.createScript("edit", iid, "1"), false);
    	        } else {
    	            gridRow.addCell("title",  "<font color='red'>[顶]" + "</font>" + title, Script.createScript("edit", iid), false);
    	        }
    		} else {
    		    if("C".equals(infoType)){
    		        gridRow.addCell("title",  "<font color='red'>[复]" + "</font>" + title, Script.createScript("edit", iid), false);
    		    } else if ("Q".equals(infoType)){
    		        gridRow.addCell("title",  "<font color='red'>[引]" + "</font>" + "<font color='red'> [只读]" + "</font>" + title, Script.createScript("edit", iid, "1"), false);
    		    } else {
    		        gridRow.addCell("title",  title, Script.createScript("edit", iid), false);
    		    }
    		}
		} else {
			if(topid > 0){
			    if("C".equals(infoType)){
			        gridRow.addCell("title",  "<font color='red'>[复]" + "</font>" + "<font color='red'> [顶]" + "</font>" + title, false);
	            } else if("Q".equals(infoType)){
	                gridRow.addCell("title",  "<font color='red'>[引]" + "</font>" + "<font color='red'> [只读]" + "</font>" + "<font color='red'> [顶]" + "</font>" + title, false);
	            } else {
	                gridRow.addCell("title",  "<font color='red'>[顶]" + "</font>" + title, false);
	            }
			} else {
			    if("C".equals(infoType)){
			        gridRow.addCell("title", "<font color='red'>[复]" + "</font>" + title, false);
			    } else if("Q".equals(infoType)){
			        gridRow.addCell("title", "<font color='red'>[引]" + "</font>" + "<font color='red'> [只读]" + "</font>" + title, false);
			    } else {
			        gridRow.addCell("title",  title, false);
			    }
			}
		}
		gridRow.addCell("colname", colname);
		gridRow.addCell("syntime", DateUtil.dateToString((Date) rowData.get("syntime"), DateUtil.YYYY_MM_DD_HH_MM_SS));  
		Integer siteId = currentUser.getSiteId();
		Site site = siteService.findByIid(siteId); 
		gridRow.addCell("visitcount", visitCount, Script.createScript("visitcount", iid));
		
		// 查询信息布局类型
//		@SuppressWarnings("unused")
//		int commonType = NumberUtil.getInt(colService.findByIid(colid).getCommonType());
		String operateStr = "";
		//推送管理 ：推送分类、推送信息 
		if(currentUser.getIsWebSiteAdmin() || colRightids.contains("edit-"+colid) || colRightids.contains("manage-"+colid)){
		    if("Q".equals(infoType)){
		        operateStr +="<input type='button' class='btn btn-success btn-small' " +
                "onclick='edit(\"" + iid + "\", \"" + 1 + "\")' value='编辑'></input>&nbsp;";
		    } else {
		        operateStr +="<input type='button' class='btn btn-success btn-small' " +
	            "onclick='edit(\"" + iid + "\")' value='编辑'></input>&nbsp;";
		    } 
		}
		
		if(currentUser.getIsWebSiteAdmin() || colRightids.contains("info")){
		    if(site != null && NumberUtil.getInt(site.getParameter().getIsPush())== 1){
	            String strPush="推送";
	            String pushClass="btn btn-success btn-small";
	            if(pushstate==1){
	                strPush="推送中";
	                pushClass="btn btn-primary btn-small";
	            } else if (pushstate==2){
	                strPush="已推送";
	                pushClass="btn btn-primary btn-small";
	            }
	            operateStr += " <input type='button' class='"+pushClass+"' " 
	                       + " onclick='pushToTask("
	                       + iid + ", "+colid+")' value='"+strPush+"'></input>&nbsp;";
	        } 
		}
		
		if(currentUser.getIsWebSiteAdmin()){
			operateStr += " <input type='button' class='btn btn-success btn-small' " 
				       + " onclick='comment(\"" + iid + "\")' value='评论'></input>&nbsp;";
		}
		int subnum = colService.findSubCol(colid);
		Col col = colService.findByIid(colid);
		if(colid > 0 && (subnum <= 0 && col.getType() == 2)){
			if(currentUser.getIsWebSiteAdmin() || colRightids.contains("audit-"+colid)){
				if("1".equals(status)){
					operateStr += "<input type='button' class='btn btn-success btn-small' style='color:white;' " +
					"onclick='modifyStatus(\"" + iid + "\",\"" + status+ "\")' value='已审核' ></input>&nbsp;";
				}else{
					operateStr += "<input type='button' class='btn btn-success btn-small' style='color:gray;'" +
					"onclick='modifyStatus(\"" + iid + "\",\"" + status+ "\")' value='未审核'></input>&nbsp;";
				}
			}
		}
		if(flag >0 && colid > 0 && subnum <= 0 && col.getType() == 2){
			if(topid == 0){
				operateStr += " <input type='button' class='btn btn-success btn-small' style='color:gray;'" 
					       + " onclick='modifyTop(\"" + iid + "\",\"" + topid+ "\")' value='未置顶'></input>&nbsp;";
			} else {
				operateStr += " <input type='button' class='btn btn-success btn-small' style='color:white;'" 
					       + " onclick='modifyTop(\"" + iid + "\",\"" + topid+ "\")' value='已置顶'></input>&nbsp;";
			}
		}
		//组图
		if(!currentUser.getIsWebSiteAdmin()){
			if(colRightids.contains("edit-"+colid)){
				if(infocontenttype==4){
					operateStr += "<input type='button' class='btn btn-success btn-small' "
						       + "onclick='goPic(\"" + iid + "\", \"" + title+ "\", \"" + siteId+ "\", \""
						       + colid+"\")' value='组图'></input>"; 
				}
			}
		} else {
			if(infocontenttype==4){
				operateStr += " <input type='button' class='btn btn-success btn-small' " 
					       + "onclick='goPic(\"" + iid + "\", \"" + title+ "\", \"" + siteId+ "\", \""
					       + colid+"\")' value='组图'></input>"; 
			}
		}
		operateStr += " <input type='button' class='btn btn-success btn-small' " 
			       + " onclick='preview(\"" + iid + "\")' value='预览'></input>&nbsp;";
		/*operateStr += " <input type='button' class='btn btn-success btn-small' " 
			       + "onclick='lookDetails(" + iid + ")' value='查看详情'></input>"; */
		gridRow.addCell("operate", operateStr, false);
		
		/*gridRow.addCell("details", "<a class=\"btn btn-success btn-small\">" 
				+ "<i class=\"icon-users-2\"></i>查看详情</a>",
				Script.createScript("lookDetails", iid), false);*/
	}

}