package com.hanweb.complat.controller.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.entity.Right;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.RightService;
import com.hanweb.complat.service.RoleRightService;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.sys.entity.role.RoleCol;
import com.hanweb.jmp.sys.service.role.RoleColService;
import com.hanweb.support.controller.CurrentUser;

/**
 * 角色权限关系控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@Permission(module = "role_rights")
@RequestMapping("manager/role/rights")
public class OprRoleRightsController {

	@Autowired
	private RightService rightService;

	@Autowired
	private RoleRightService roleRightService;
	
	@Autowired
    private RoleColService roleColService;
	
	@Autowired
	private ColService colService;

	/**
	 * 显示角色权限关系界面
	 * 
	 * @param iid
	 *            角色ID
	 * @return
	 */
	@Permission(function = "modify_show")
	@RequestMapping(value = "modify_show")
	public ModelAndView showModifyRights(String iid) {
		ModelAndView modelAndView = new ModelAndView("complat/role/rights_opr");
		Integer roleId = NumberUtil.getInt(iid);

		List<Right> rightList = rightService.findByRoleId(roleId);
		List<Right> allRightList = rightService.findAllRights();
		List<Right> appRightList = rightService.findAppRights();
		
		StringBuilder selectedRightIds = new StringBuilder();

		for (Right right : rightList) {
			if (right == null || right.getIid() <= 0) {
				continue;
			}
			selectedRightIds.append("," + right.getIid()) ;
		}
		for (Right right : allRightList) { 
			right.setCustom(","+right.getIid()+",");
		}

		for (Right right1 : appRightList) { 
			right1.setCustom(","+right1.getIid()+",");
		}

		modelAndView.addObject("selectedRightIds", selectedRightIds.toString()+ ",");
		modelAndView.addObject("allRightList", allRightList);
		modelAndView.addObject("appRightList", appRightList);
		modelAndView.addObject("iid", iid);
		modelAndView.addObject("url", "modify_submit.do");
		this.addOtherObject(modelAndView, roleId); 
		return modelAndView;
	}

	/**
	 * 提交角色权限关系
	 * 
	 * @param iid
	 *            角色ID
	 * @param rights
	 *            权限ID集 如:1,2,3,4
	 * @return
	 */
	@Permission(function = "modify_submit")
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModifyMembers(String iid, String rights, String colManages,
			String colIds, String colAuditIds) {
		JsonResult jsonResult = JsonResult.getInstance();
		List<Integer> rightIdList = StringUtil.toIntegerList(rights, ",");		
		List<Integer> colList = StringUtil.toIntegerList(colIds, ",");
		List<Integer> colAuditList = StringUtil.toIntegerList(colAuditIds, ",");
		List<Integer> colManageList = StringUtil.toIntegerList(colManages, ",");
		Map<Integer, Integer> colMap=new HashMap<Integer, Integer>();
		Map<Integer, Integer> colAuditMap=new HashMap<Integer, Integer>();
		Map<Integer, Integer> colManageMap=new HashMap<Integer, Integer>();
		Map<Integer, RoleCol> roleColMap=new HashMap<Integer, RoleCol>();
		if(!CollectionUtils.isEmpty(colList)){
			for(Integer colid : colList){
				if(NumberUtil.getInt(colid)>0){
					colMap.put(colid, colid);
				}
			}
		}
		if(!CollectionUtils.isEmpty(colAuditList)){
			for(Integer colid : colAuditList){
				if(NumberUtil.getInt(colid)>0){
					colAuditMap.put(colid, colid);
				}
			}
		}
		if(!CollectionUtils.isEmpty(colManageList)){
            for(Integer colid : colManageList){
                if(NumberUtil.getInt(colid)>0){
                    colManageMap.put(colid, colid);
                }
            }
        }
		RoleCol roleCol = null;
		if(colMap!=null && colMap.size()>0){
			for (Integer colid : colMap.keySet()) {
				roleCol = new RoleCol();
				roleCol.setRoleId(NumberUtil.getInt(iid));
				roleCol.setColId(colid);
				roleCol.setIsedit(1);
				roleColMap.put(colid, roleCol);
		    }

		}
		
		if(colAuditMap!=null && colAuditMap.size()>0){
			for (Integer colid : colAuditMap.keySet()) {
				if(roleColMap.containsKey(colid)){
					roleCol=roleColMap.get(colid);
					roleCol.setIsaudit(1);
					roleColMap.put(colid, roleCol);
				}else{
					roleCol = new RoleCol();
					roleCol.setRoleId(NumberUtil.getInt(iid));
					roleCol.setColId(colid);
					roleCol.setIsaudit(1);
					roleColMap.put(colid, roleCol);
				}
		    }
		}
		
		if(colManageMap != null && colManageMap.size()>0){
            for (Integer colid : colManageMap.keySet()) {
                if(roleColMap.containsKey(colid)){
                    roleCol = roleColMap.get(colid);
                    roleCol.setIsColManage(1);
                    roleColMap.put(colid, roleCol);
                } else {
                    roleCol = new RoleCol();
                    roleCol.setRoleId(NumberUtil.getInt(iid));
                    roleCol.setColId(colid);
                    roleCol.setIsColManage(1);
                    roleColMap.put(colid, roleCol);
                }
            }
        }
		
		
		boolean isSuccess = false;
		try {
			isSuccess = roleRightService.modifyRoleRight(NumberUtil.getInt(iid), rightIdList, 0);
			/* 以下是栏目、推送分类权限修改 */
			if(isSuccess){
				isSuccess = roleColService.modifyRoleCol(NumberUtil.getInt(iid), roleColMap);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		if (isSuccess) {
			jsonResult.set(ResultState.MODIFY_SUCCESS);
		} else {
			jsonResult.set(ResultState.MODIFY_FAIL);
		}
		return jsonResult;
	}
	
	/**
	 * 栏目编辑,审核权限设置
	 * @param modelAndView
	 * @param roleId
	 */
	private void addOtherObject(ModelAndView modelAndView, Integer roleId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		Tree colTree = Tree.getInstance("colId", "colName");
		Tree colTreeAudit = Tree.getInstance("colId", "colName");
		Tree colManageTree = Tree.getInstance("colId", "colName");
		colTree.addNode(TreeNode.getInstance("0", "", "栏目分类"));
		colTreeAudit.addNode(TreeNode.getInstance("0", "", "栏目分类"));
		colManageTree.addNode(TreeNode.getInstance("0", "", "栏目分类"));
		List<RoleCol> roleColList = roleColService.findByRoleId(roleId);
		String colIds = "";
		String colAuditIds = "";
		String colManages = "";
		StringBuffer colidStr = new StringBuffer(100);
		StringBuffer colidAuditStr = new StringBuffer(100);
		StringBuffer colManageStr = new StringBuffer(100);
		String pushIds = "";
		//组织栏目数 
		List<Col> colList = colService.findBySiteIds(siteId+"");  
		for (Col col : colList) {
			if(roleColList!= null && roleColList.size() > 0){
				boolean isCheck = false;
				boolean isAuditCheck = false;
				boolean iscolManageCheck = false;
				for(RoleCol roleCol : roleColList){
					if(NumberUtil.getInt(col.getIid()) == NumberUtil.getInt(roleCol.getColId())){
						// 信息编辑权限
						if(NumberUtil.getInt(roleCol.getIsedit())==1){
							colidStr.append(roleCol.getColId()+","); 
							isCheck = true;
						}
						
						// 信息审核权限
						if(NumberUtil.getInt(roleCol.getIsaudit())==1){
							colidAuditStr.append(roleCol.getColId()+","); 
							isAuditCheck = true;
						}
						
						// 栏目管理权限
						if(NumberUtil.getInt(roleCol.getIsColManage()) == 1){
						    colManageStr.append(roleCol.getColId()+","); 
						    iscolManageCheck = true;
                        }
						
					}
				}
				colTree.addNode(TreeNode.getInstance(col.getIid() + "",
						NumberUtil.getInt(col.getPid())+"", col.getName(), false)
						.setChecked(isCheck));	
				colTreeAudit.addNode(TreeNode.getInstance(col.getIid() + "",
						NumberUtil.getInt(col.getPid())+ "", col.getName(), false)
						.setChecked(isAuditCheck));
				colManageTree.addNode(TreeNode.getInstance(col.getIid() + "", 
				        NumberUtil.getInt(col.getPid())+ "", col.getName(), false)
                        .setChecked(iscolManageCheck));
			}else{
				colTree.addNode(TreeNode.getInstance(col.getIid() + "",
						NumberUtil.getInt(col.getPid())+ "", col.getName(), false));	
				colTreeAudit.addNode(TreeNode.getInstance(col.getIid() + "",
						NumberUtil.getInt(col.getPid())+ "", col.getName(), false));	
				colManageTree.addNode(TreeNode.getInstance(col.getIid() + "", 
                        NumberUtil.getInt(col.getPid())+ "", col.getName(), false));
			}	
		}
		StringBuffer pushidsstr=new StringBuffer(100);
		if(colidStr!=null){
			colIds = colidStr.toString();
		}
		if(StringUtil.isNotEmpty(colIds)){
			colIds = colIds.substring(0, colIds.length()-1);
		}
		if(colidAuditStr!=null && colidAuditStr.length()>0){
			colAuditIds=colidAuditStr.substring(0, colidAuditStr.length()-1);
		}
		if(colManageStr != null && colManageStr.length()>0){
		    colManages = colManageStr.substring(0, colManageStr.length() - 1);
		}
		if(pushidsstr!=null){
			pushIds=pushidsstr.toString();
		}
		if(StringUtil.isNotEmpty(pushIds)){
			pushIds = pushIds.substring(0, pushIds.length()-1);
		}
		modelAndView.addObject("colIds", colIds);
		modelAndView.addObject("colAuditIds", colAuditIds);
		modelAndView.addObject("colManages", colManages);
		modelAndView.addObject("pushIds", pushIds);
		modelAndView.addObject("colTree", colTree.parse());
		modelAndView.addObject("colTreeAudit", colTreeAudit.parse());
		modelAndView.addObject("colManageTree", colManageTree.parse());
	}
	
}