package com.hanweb.setup.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HttpClientUtil;
import com.hanweb.jmp.util.XmlUtil;
import com.hanweb.setup.entity.Update;
import com.hanweb.setup.entity.UpdateItem;

@Controller
public class UpdateSetupController {
	/**
	 * logger
	 */
	protected final Log logger = LogFactory.getLog(getClass());


	/**
	 * modify
	 * @param request  request
	 * @return ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("setup/main/db/updatesetup")
	public ModelAndView modify(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("setup/updatesetup");
		String strXml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				+ "/WEB-INF/pages/setup/update/update.xml"));
		XmlUtil xml = null;
		try {
			xml = new XmlUtil(strXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Element> nodeList = new ArrayList<Element>();
		if(xml != null){
		    nodeList = xml.getNodeFromName("/update"); 
		}
		List<Update> updateList = new ArrayList<Update>();
		Update update = null;
		UpdateItem updateItem = null;
		Element el = null;
		int index = 0;
		if (nodeList != null && nodeList.size() > 0) {
			for (Iterator<Element> it = nodeList.iterator(); it.hasNext(); ){
				el = (Element) it.next();
				update = new Update();
				update.setIndex(index);
				update.setVersion(el.attributeValue("version"));
				for (Iterator<Element> item = el.elementIterator(); item.hasNext(); ){
					el = (Element) item.next();
					updateItem = new UpdateItem();
					updateItem.setUrl(el.attributeValue("url"));
					updateItem.setValue(el.getTextTrim());
					update.getUpdateItemList().add(updateItem);
				}
				updateList.add(update);
				index++;
			}
		}
		request.getSession().setAttribute("updateList", updateList);
		modelAndView.addObject("startVersion", "0");
		modelAndView.addObject("endVersion", "0");
		modelAndView.addObject("updateList", updateList);
		modelAndView.addObject("url", "show.do");
		return modelAndView;
	}

	/**
	 * show
	 * @param request  request
	 * @param startVersion  startVersion
	 * @param endVersion  endVersion
	 * @return   ModelAndView
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("setup/main/db/show")
	public ModelAndView show(HttpServletRequest request, int startVersion, int endVersion) {
		ModelAndView modelAndView = new ModelAndView("setup/updatesetup");
		JsonResult jsonResult = JsonResult.getInstance();
		if(startVersion >= endVersion){
			jsonResult.set(ResultState.OPR_FAIL);
			jsonResult.setMessage("历史版本要小于升级版本");
		}
		List<Update> updateList = (List<Update>) request.getSession().getAttribute("updateList");
		List<Update> selectUpdateList = new ArrayList<Update>();
		if(updateList != null){
			for (Update update : updateList) {
				if(update.getIndex() > startVersion && update.getIndex() <= endVersion){
					selectUpdateList.add(update);
				}
			}
		}
		modelAndView.addObject("startVersion", startVersion);
		modelAndView.addObject("endVersion", endVersion);
		modelAndView.addObject("selectUpdateList", selectUpdateList);
		modelAndView.addObject("url", "show.do");
		return modelAndView;
	}

	/**
	 * update
	 * @param url  url
	 * @return   JsonResult
	 */
	@RequestMapping("setup/main/db/update")
	@ResponseBody
	public JsonResult update(String url) {
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			String result = HttpClientUtil.getInfo(Configs.getConfigs().getJmpUrl() + 
					"/updatejsp.do?url="+url, "utf-8");
			if("OK".equals(result)){
				jsonResult.set(ResultState.OPR_SUCCESS);
			}else{
				jsonResult.set(ResultState.OPR_FAIL);
				jsonResult.setMessage(result);
			}
		} catch (Exception e) {
			logger.error("initDB error", e);
		}
		return jsonResult;
	}
	
	/**
	 * udpatejsp
	 * @param url  url
	 * @return  ModelAndView
	 */
	@RequestMapping("updatejsp")
	public ModelAndView udpatejsp(String url) {
		ModelAndView modelAndView = new ModelAndView("setup/update/"+url);
		return modelAndView;
	}

}
