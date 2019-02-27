package com.hanweb.jmp.cms.controller.sign;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.entity.sign.SignRel;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.sign.SignRelService;
import com.hanweb.jmp.cms.service.sign.SignService;
import com.hanweb.jmp.constant.SignConfig;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/sign")
public class OprSignController {
	
	/**
	 *  signService
	 */
	@Autowired
	SignService signService;
	
	/**
	 *  signRelService
	 */
	@Autowired
	SignRelService signRelService;
	
	/**
	 *  colService
	 */
	@Autowired
	ColService colService;
	
	/**
	 *  infoService
	 */
	@Autowired
	InfoService infoService;
	
	/**
	 *  siteService
	 */
	@Autowired
	SiteService siteService;
	
	/**
	 * 新增页
	 * @param mid   mid
	 * @param colId colId
	 * @return  ModelAndView
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd(Integer mid, Integer colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/sign/sign_opr");
		if(NumberUtil.getInt(mid)==3){
			modelAndView = new ModelAndView("jmp/cms/sign/tag_opr");
		}
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		Sign sign = new Sign();
		sign.setSiteId(siteId);
		sign.setMid(mid);
		sign.setColId(NumberUtil.getInt(colId));
		sign.setMname(SignConfig.listType.get(NumberUtil.getInt(mid)));
		String createTime = DateUtil.dateToString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
		modelAndView.addObject("createTime", createTime);
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("sign", sign);
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param sign   sign
	 * @return  JsonResult
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public JsonResult submitAdd(Sign sign) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			sign.setCreateTime(new Date());
			isSuccess = signService.add(sign);
			if(sign.getMid() == 2){
				isSuccess = isSuccess && siteService.modifySubscribeColSignFlag(currentUser.getSiteId());
			}else if(sign.getMid() == 1){
				isSuccess = isSuccess && siteService.modifyCardSignFlag(currentUser.getSiteId());
			}
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh", "subscribecol");
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 修改页
	 * @param iid  iid
	 * @return  showModify
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/sign/sign_opr");
		Sign sign = signService.findByIid(NumberUtil.getInt(iid));
		if(NumberUtil.getInt(sign.getMid())==3){
			modelAndView = new ModelAndView("jmp/cms/sign/tag_opr");
		}
		modelAndView.addObject("url", "modify_submit.do"); 
		modelAndView.addObject("sign", sign);
		if(StringUtil.isNotEmpty(sign.getColor())) {
			modelAndView.addObject("bgColor", sign.getColor());
		}
		return modelAndView;
	}
	
	/**
	 * 修改提交
	 * @param sign  sign
	 * @return  JsonResult
	 */
	@RequestMapping("modify_submit")
	@ResponseBody
	public JsonResult modifySubmit(Sign sign) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = signService.modify(sign);
			if(sign.getMid() == 2){
				isSuccess = isSuccess && siteService.modifySubscribeColSignFlag(currentUser.getSiteId());
			}else if(sign.getMid() == 1){
				isSuccess = isSuccess && siteService.modifyCardSignFlag(currentUser.getSiteId());
			}
			if(isSuccess){
				if(NumberUtil.getInt(sign.getMid()) == 1){
					isSuccess = colService.modifyFlag(StringUtil.getString(sign.getColId()));
				}else{  
					isSuccess = siteService.modifySubscribeColFlag(currentUser.getSiteId()); 
					isSuccess = isSuccess && siteService.modifySubscribeColSignFlag(currentUser.getSiteId());
				}
			}
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 删除角标
	 * @param ids 
	 *          栏目id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids, Integer mid) {
		JsonResult jsonResult = JsonResult.getInstance();
		int siteId = UserSessionInfo.getCurrentUser().getSiteId();
		boolean isSuccess = false;
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = signService.removeByIds(ids, siteId, mid);
			isSuccess = isSuccess && siteService.modifySubscribeColSignFlag(currentUser.getSiteId());
			isSuccess = isSuccess && siteService.modifyCardSignFlag(currentUser.getSiteId());
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
				jsonResult.addParam("remove", ids);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 排序页
	 * @param mid   mid
	 * @param colId colId
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(int mid, Integer colId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/sign/sign_sort");
		List<Sign> signList = signService.findByMid(mid, siteId, colId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("signList", signList);
		modelAndView.addObject("mid", mid);
		return modelAndView;
	}

	/**
	 * 排序提交
	 * @param ids       ids
	 * @param orderids  orderids
	 * @param mid  mid
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids, Integer mid) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = signService.modifyOrderIdById(ids, orderids);
			if(NumberUtil.getInt(mid) == 2){
				isSuccess = isSuccess && siteService.modifySubscribeColSignFlag(currentUser.getSiteId());
			}else if(NumberUtil.getInt(mid) == 1){
				isSuccess = isSuccess && siteService.modifyCardSignFlag(currentUser.getSiteId());
			}
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS); 
				jsonResult.addParam("refresh", "subscribecol");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}
	
	/**
	 * 显示卡片角标树
	 * @param infoId  infoId
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "cardsignmenu_show")
	public ModelAndView showCardSignMenu(Integer infoId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/sign/cardsign_opr");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Info info = infoService.findByIid(NumberUtil.getInt(infoId), currentUser.getSiteId());
		SignRel signRel = signRelService.findRelByInfoId(NumberUtil.getInt(infoId), 1, currentUser.getSiteId());
		Tree tree = Tree.getInstance("dimensionid", "signName");
		tree.addNode(TreeNode.getInstance("0", "", "信息分组"));
		List<Sign> l1 = signService.findByMid(1, currentUser.getSiteId(), info.getColId());
		for(Sign d : l1){
			TreeNode t = TreeNode.getInstance(d.getIid() + "", "0", d.getDname(), false, false);
			if(signRel != null && NumberUtil.getInt(d.getIid()) == NumberUtil.getInt(signRel.getDimensionid())){
				t.setChecked(true);
				modelAndView.addObject("iid", signRel.getDimensionid());
			}else{
				t.setChecked(false);
			}
			tree.addNode(t);
		}
		modelAndView.addObject("tree", tree.parse());
		modelAndView.addObject("infoId", infoId);
		modelAndView.addObject("url", "cardsignmenu_submit.do");
		return modelAndView;
	}
	
	/**
	 * 卡片式角标提交
	 * @param infoId   infoId
	 * @param iid  iid
	 * @return  JsonResult
	 */
	@RequestMapping(value = "cardsignmenu_submit")
	@ResponseBody
	public JsonResult submitCardSignMenu(Integer infoId, Integer iid) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			SignRel signRel = new SignRel();
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			SignRel signRel1 = signRelService.findRelByInfoId(infoId, 1, currentUser.getSiteId());
			Info info = infoService.findByIid(infoId, currentUser.getSiteId());
			colService.modifyFlag(info.getColId() + "");
			if(signRel1 != null && signRel1.getIid() > 0){
				isSuccess = signRelService.remove(signRel1);
			}
			if(NumberUtil.getInt(iid) > 0){
				signRel.setSiteId(currentUser.getSiteId());
				signRel.setDimensionid(iid);
				signRel.setModuleid(1);
				signRel.setAttrid(infoId);
				signRel.setOrderid(NumberUtil.getInt(signRelService
					   .findMinOrderIdBySignIdAndModuleId(NumberUtil.getInt(iid), 1, currentUser.getSiteId())) - 1);
				isSuccess = signRelService.add(signRel);
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
	 * 删除卡片信息关系
	 * @param ids 
	 *          卡片信息id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "removeinfo")
	@ResponseBody
	public JsonResult removeInfo(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			List<SignRel> list = signRelService.findByIid(StringUtil.toIntegerList(ids, ","));
			isSuccess = signRelService.removeByIds(ids);
			for(SignRel dr : list){
				colService.modifyFlag(infoService.findByIid(dr.getAttrid(), dr.getSiteId()).getColId() + "");
			}
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
				jsonResult.addParam("remove", ids);
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
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sortcard_show")
	@ResponseBody
	public ModelAndView orderCard(int dimensionid) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/sign/cardcol_sort");
		List<SignRel> signRelList = signRelService.findRelBySignIdAndModuleId(NumberUtil.getInt(dimensionid), 1, NumberUtil.getInt(siteId));
		modelAndView.addObject("url", "sortcard_submit.do");
		modelAndView.addObject("signRelList", signRelList);
		modelAndView.addObject("dimensionid", dimensionid);
		return modelAndView;
	}

	/**
	 * 排序提交
	 * @param ids   ids
	 * @param orderids  orderids
	 * @return   JsonResult
	 */
	@RequestMapping(value = "sortcard_submit")
	@ResponseBody
	public JsonResult submitSortCard(String ids, String orderids) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = signRelService.modifyOrderIdById(ids, orderids);
			isSuccess = isSuccess && siteService.modifyCardSignFlag(currentUser.getSiteId());
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