package com.hanweb.jmp.cms.controller.subscribe;
 
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
 
import com.hanweb.common.annotation.Permission;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.util.NumberUtil; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode; 
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo; 

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.sign.SignRel;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.sign.SignRelService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/sign/subscribecol")
public class OprSubscribeColController {

	/**
	 * signRelService
	 */
	@Autowired
	private SignRelService signRelService;
	
	/**
	 * colService
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * 显示卡片角标树
	 * @param
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "subscribesignmenu_show")
	public ModelAndView showCardSignMenu(Integer dimensionid) {
		dimensionid = NumberUtil.getInt(dimensionid);
		ModelAndView modelAndView = new ModelAndView("jmp/cms/subscribe/subscribesign_opr");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		//该角标下，已经被订阅的栏目
		List<SignRel> list = signRelService.findRelBySignId(dimensionid, 2, currentUser.getSiteId());
		StringBuilder sb = new StringBuilder(",");
		if(list != null && list.size() > 0){
			for(SignRel d : list){
				sb.append(d.getAttrid() + ",");
			}
		}
		//订阅模块所有被订阅的栏目
		List<SignRel> list1 = signRelService.findRelByModuleId(2, currentUser.getSiteId());
		StringBuilder sb1 = new StringBuilder(",");
		if(list1 != null && list1.size() > 0){
			for(SignRel d1 : list1){
				sb1.append(d1.getAttrid() + ",");
			}
		}
		//该网站下所有的栏目
		List<Col> list2 = colService.findBySiteIds(currentUser.getSiteId() + "");
		Tree tree = Tree.getInstance("colId", "colName");
		tree.addNode(TreeNode.getInstance("0", "", "订阅分类").setChkDisabled(true));
		if(list2 != null && list2.size() > 0){
			for(Col col : list2){
				TreeNode t = TreeNode.getInstance(col.getIid() + "", NumberUtil.getInt(col.getPid()) + "", col.getName());
				if(sb.toString().indexOf("," + col.getIid() + ",") != -1){
					t.setChecked(true);
				}else if(sb1.toString().indexOf("," + col.getIid() + ",") != -1){
					t.setChkDisabled(true);
				}
				tree.addNode(t);
			}
		}
		modelAndView.addObject("tree", tree.parse());
		modelAndView.addObject("dimensionid", dimensionid);
		modelAndView.addObject("colId", sb.toString());
		modelAndView.addObject("url", "subscribesignmenu_submit.do");
		return modelAndView;
	}
	
	/**
	 * 订阅角标菜单提交
	 * @param colId       colId
	 * @param 
	 * @return  JsonResult
	 */
	@RequestMapping(value = "subscribesignmenu_submit")
	@ResponseBody
	public JsonResult submitSubscribeSignMenu(String colId, Integer dimensionid) { 
		JsonResult jsonResult = JsonResult.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		boolean isSuccess = false;
		try {
			if(StringUtil.isNotEmpty(colId) && colId.startsWith(",")){
				colId=colId.substring(1);
			} 
			isSuccess = signRelService.removeBySignIdAndModuleId(dimensionid, 2, currentUser.getSiteId());
			List<Integer> colIds = StringUtil.toIntegerList(colId); 
			if(colIds != null && colIds.size() > 0){ 
				for(int ci : colIds){ 
					SignRel signRel = new SignRel();
					signRel.setSiteId(currentUser.getSiteId());
					signRel.setDimensionid(NumberUtil.getInt(dimensionid));
					signRel.setAttrid(ci);
					signRel.setModuleid(2);
					signRel.setOrderid(NumberUtil.getInt(signRelService
						   .findMinOrderIdBySignIdAndModuleId(NumberUtil.getInt(dimensionid)
						   , 2, currentUser.getSiteId())) - 1);
					isSuccess = isSuccess && signRelService.add(signRel);
					isSuccess = isSuccess && siteService.modifySubscribeColFlag(currentUser.getSiteId());
				}
			}
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 删除
	 * @param ids  ids
	 * @return  JsonResult
	 */
	@RequestMapping(value = "removesubscribe")
	@ResponseBody
	public JsonResult removeSubscribe(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = signRelService.removeByIds(ids);
			isSuccess = isSuccess && siteService.modifySubscribeColFlag(currentUser.getSiteId());
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 排序
	 * @param 
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(int dimensionid) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/subscribe/subscribecol_sort");
		List<SignRel> signRelList = signRelService.findRelBySignIdAndModuleId(NumberUtil.getInt(dimensionid), 2, NumberUtil.getInt(siteId));
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("signRelList", signRelList);
		modelAndView.addObject("dimensionid", dimensionid);
		return modelAndView;
	}

	/**
	 * 排序提交
	 * @param ids      ids
	 * @param orderids orderids
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = signRelService.modifyOrderIdById(ids, orderids);
			isSuccess = isSuccess && siteService.modifySubscribeColFlag(currentUser.getSiteId());
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS); 
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}
	
}