package com.hanweb.jmp.cms.controller.infos.pic; 
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.servlet.ModelAndView; 
     
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;  
import com.hanweb.complat.exception.OperationException;   
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.infos.Pic;
import com.hanweb.jmp.cms.service.infos.PicService;


@Controller 
@RequestMapping("manager/pic")
public class OprPicController {
	/**
	 * picService 图片service
	 */
	@Autowired
	private PicService picService; 
	  
	/**
	 * showadd:(显示新增页面).
	 *
	 * @param siteId 网站id
	 * @param colId 栏目id
	 * @param infoId 信息id
	 * @return    设定参数 .
	*/
	@RequestMapping("add_show")
	public ModelAndView showadd(Integer siteId, Integer colId, Integer infoId){  
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/pic/pic_opr");
		PicFormBean pic = new PicFormBean();   
		modelAndView.addObject("operatename", "组图管理");
		modelAndView.addObject("operatetype", "新增");  
		pic.setSiteId(siteId);
		pic.setInfoId(infoId);
		pic.setColId(colId);
		modelAndView.addObject("pic", pic);  
		modelAndView.addObject("url", "add_submit.do");
		return modelAndView;
	}
    

	/**
	 * submitadd:(新增图片).
	 *
	 * @param picFormBean 图片实体 
	 * @return    设定参数 .
	*/
	@RequestMapping("add_submit")
	@ResponseBody
	public JsonResult submitadd(PicFormBean picFormBean){ 
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			Info info=new Info();
			info.setIid(picFormBean.getInfoId());
			info.setSiteId(picFormBean.getSiteId());
			info.setColId(picFormBean.getColId());
		    isSuccess = picService.addPics(info, 
		    		picFormBean.getPicname(), picFormBean.getPicdesc());
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
	 * showModify:(这里用一句话描述这个方法的作用).
	 *
	 * @param iid iid
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(String iid) { 
		int picId = NumberUtil.getInt(iid); 
		Pic pic = picService.findByIid(picId); 
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/pic/pic_opr");
	    modelAndView.addObject("synshowtime", DateUtil.dateToString(pic.getSynTime(), 
				DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("pic", pic);  
		modelAndView.addObject("operatename", "组图管理");
		modelAndView.addObject("operatetype", "编辑");
		modelAndView.addObject("url", "modify_submit.do");  
		return modelAndView;
	}

	
	
	/**
	 * submitModify:(这里用一句话描述这个方法的作用).
	 *
	 * @param pic pic
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(PicFormBean pic){ 
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = picService.modify(pic);
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
	 * remove:(这里用一句话描述这个方法的作用).
	 *
	 * @param ids ids
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = picService.removeByIds(ids);
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
	 * 显示排序页面
	 * @param infoId
	 * @param siteId
	 * @param colId
	 * @return
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(Integer infoId, Integer siteId, Integer colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/pic/pic_sort");
		List<Pic> pics = picService.findByInfoid2(NumberUtil.getInt(infoId), NumberUtil.getInt(siteId));
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("pics", pics);
		return modelAndView;
	}
	
	/**
	 * 排序修改提交
	 * @param ids
	 * @param orderids
	 * @return
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			if(null == ids || ids.length() <= 0){
				isSuccess = true;
			}else{
				isSuccess = picService.modifyOrderIdById(ids, orderids);
			}
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
