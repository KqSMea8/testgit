package com.hanweb.jmp.sys.controller.ditch;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.sys.entity.ditch.SynField;
import com.hanweb.jmp.sys.entity.field.Field;
import com.hanweb.jmp.sys.service.ditch.SynFieldService;
import com.hanweb.jmp.sys.service.field.FieldService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/synfield")
public class OprSynFieldController {
    
    /**
     * SynFieldService
     */
    @Autowired
    private SynFieldService synFieldService;
    
    /**
     * FieldService
     */
    @Autowired
    private FieldService FieldService;
    
    /**
     * 新增页面
     * @param ditchId
     * @return
     */
    @RequestMapping("add_show")
    public ModelAndView showAdd(String ditchId){
        ModelAndView modelAndView = new ModelAndView("jmp/sys/ditch/synfield_opr");
        SynField synField = new SynField();
        modelAndView.addObject("synField", synField);
        modelAndView.addObject("ditchid", ditchId);
        modelAndView.addObject("url", "add_submit.do");
        this.addOtherObject(modelAndView);
        return modelAndView;
    }
    
    /**
     * 组织树
     * @param modelAndView
     */
    private void addOtherObject(ModelAndView modelAndView) {
        Tree tree = Tree.getInstance();
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        List<Field> fieldList = FieldService.findUnSysBySiteid(siteId);
        if(CollectionUtils.isNotEmpty(fieldList)){
            for(Field field : fieldList){
                tree.addNode(TreeNode.getInstance( "" + field.getIid(), "root",
                        field.getFieldName()).setIsParent(false));
            }
        }
        modelAndView.addObject("FieldMenu", tree.parse());
    }

    /**
     * 新增提交
     * @param synField
     * @param ditchId
     * @return
     */
    @RequestMapping("add_submit")
    @ResponseBody
    public JsonResult submitAdd(SynField synField, String ditchid){
        boolean isSuccess = false;
        JsonResult jsonResult = JsonResult.getInstance();
        try {
            isSuccess = synFieldService.add(synField, NumberUtil.getInt(ditchid));
            if (isSuccess) {
                jsonResult.set(ResultState.ADD_SUCCESS);
            } else {
                jsonResult.set(ResultState.ADD_FAIL);
            }
        } catch (OperationException  e) {
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult; 
    }
    
    /**
     * 删除提交
     * @param ids
     * @return
     */
    @RequestMapping("remove")
    @ResponseBody
    public JsonResult remove(String ids, String ditchId) {
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        JsonResult jsonResult = JsonResult.getInstance();
        boolean isSuccess = false;
        try {
            isSuccess = synFieldService.removeByIds(ids, NumberUtil.getInt(ditchId), siteId);
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
     * 修改页面
     * @param iid
     * @param ditchId
     * @return
     */
    @RequestMapping("modify_show")
    public ModelAndView showModify(int iid, String ditchId){
        ModelAndView modelAndView  = new ModelAndView("jmp/sys/ditch/synfield_opr");
        SynField synField = synFieldService.findByIid(iid, NumberUtil.getInt(ditchId));
        if(synField != null){
            modelAndView.addObject("synField", synField);
        }
        modelAndView.addObject("url", "modify_submit.do");
        this.addOtherObject(modelAndView);
        return modelAndView;
    }
    
    /**
     * 修改提交
     * @param synField
     * @return
     */
    @RequestMapping("modify_submit")
    @ResponseBody
    public JsonResult submitModify(SynField synField){
        boolean isSuccess = false;
        JsonResult jsonResult = JsonResult.getInstance();
        try {
            isSuccess = synFieldService.modify(synField);
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
     * 排序页面
     * @param ditchId
     * @return
     */
    @RequestMapping("sort_show")
    public ModelAndView showSort(String ditchId) {
        ModelAndView modelAndView = new ModelAndView("jmp/sys/ditch/synfield_sort");
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        int siteId = currentUser.getSiteId();
        List<SynField> synFieldList = synFieldService.findSynFieldByOrderId(siteId, NumberUtil.getInt(ditchId));
        if(CollectionUtils.isNotEmpty(synFieldList)){
            modelAndView.addObject("synFieldList", synFieldList);
        }
        modelAndView.addObject("ditchId", ditchId);
        modelAndView.addObject("url", "sort_submit.do");
        return modelAndView;
    }
    
   /**
    * 排序提交
    * @param ids
    * @param orderids
    * @param ditchId
    * @return
    */
    @RequestMapping("sort_submit")
    @ResponseBody
    public JsonResult submitSort(String ids, String orderids, String ditchId) {
        JsonResult jsonResult = JsonResult.getInstance();
        boolean isSuccess = false;
        try {
            isSuccess = synFieldService.modifyOrderId(ids, orderids, NumberUtil.getInt(ditchId));
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