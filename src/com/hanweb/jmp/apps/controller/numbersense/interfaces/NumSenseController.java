package com.hanweb.jmp.apps.controller.numbersense.interfaces;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;

import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.entity.numbersense.NumSensePhone;
import com.hanweb.jmp.apps.service.numbersense.NumSenseColService;
import com.hanweb.jmp.apps.service.numbersense.NumSensePhoneService;

@Controller
@RequestMapping("interfaces")
public class NumSenseController {
	
	/**
	 * numSenseColService
	 */
	@Autowired
	private NumSenseColService numSenseColService;
	
	/**
	 * numSensePhoneService
	 */
	@Autowired
	private NumSensePhoneService numSensePhoneService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 通讯录分类接口
	 * @param siteid 网站ID
	 * @param clienttype 客户端类型
	 * @param uuid uuid
	 * @param version 版本号
	 * @param cateid 分类id
	 * @return json
	 */
	@RequestMapping("numsensecates")
	@ResponseBody
	@InterfaceCache
	public String numSenseCol(Integer siteid, Integer clienttype, String uuid, String version, Integer cateid){
		if(NumberUtil.getInt(siteid) <= 0){
			return InterfaceLogConfig.interfaceResult(false, 
					InterfaceLogConfig.MOD_NUMSENSE, InterfaceLogConfig.ERROR_07); 
		}
		List<NumSenseCol> numSenseColList = numSenseColService.findByPid( NumberUtil.getInt(cateid), NumberUtil.getInt(siteid));
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
		if(numSenseColList.size() == 0){
			return JsonUtil.objectToString(resource);
		}
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		
		for(NumSenseCol col : numSenseColList){
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("classid", StringUtil.getString(col.getIid()));
			hm.put("classname", StringUtil.getString(col.getName()));
			hm.put("classpic", jmpUrl + StringUtil.getString(col.getIconPath()));
			if(NumberUtil.getInt(col.getType()) == 2){
				hm.put("haschild", "0");
			} else{
				hm.put("haschild", StringUtil.getString(col.getType()));
			}
			resource.add(hm);
		}
		return JsonUtil.objectToString(resource);
	}
	
	
	/**
	 * 号码列表接口
	 * @param siteid 网站ID
	 * @param clienttype 客户端类型
	 * @param uuid uuid
	 * @param version 版本号
	 * @param cateid 分类ID
	 * @param pagenum 页数
	 * @param pagesize 页码
	 * @return json
	 */
	@RequestMapping("numsenselist")
	@ResponseBody
	@InterfaceCache
	public String numSenseList(Integer siteid, Integer clienttype, String uuid, String version,
				               String cateid, Integer pagenum, Integer pagesize){
		pagesize = NumberUtil.getInt(pagesize, 10);
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
		if(NumberUtil.getInt(siteid) <= 0 || NumberUtil.getInt(cateid) <= 0){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_NUMSENSE, InterfaceLogConfig.ERROR_07); 
		}
		String picPath = "";
		List<NumSensePhone> phoneList = numSensePhoneService.findByColIdLimit(siteid, NumberUtil.getInt(cateid), pagesize, NumberUtil.getInt(pagenum, 1));
		NumSenseCol col = numSenseColService.findById(NumberUtil.getInt(cateid));
		if(col == null) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_NUMSENSE, InterfaceLogConfig.ERROR_13); 
		}
		picPath = col.getIconPath();
		if(phoneList.size()==0){
			return JsonUtil.objectToString(resource);
		}
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		for(NumSensePhone phoneEn : phoneList) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("classid", StringUtil.getString(phoneEn.getIid()));
			hm.put("classname", StringUtil.getString(phoneEn.getName()));
			if(StringUtil.isNotEmpty(phoneEn.getIconPath())){
				hm.put("classpic", jmpUrl + StringUtil.getString(phoneEn.getIconPath()));
			}else{
				hm.put("classpic",jmpUrl + StringUtil.getString(picPath));
			}
			hm.put("fixedphone", StringUtil.getString(phoneEn.getPhone()));
			hm.put("mobilephone", StringUtil.getString(phoneEn.getTel()));
			hm.put("location", StringUtil.getString(phoneEn.getAddress()));
			resource.add(hm);
		}
		return JsonUtil.objectToString(resource);
	}
	
	/**
	 * 号码详情接口
	 * @param siteid 网站ID
	 * @param clienttype 客户端类型
	 * @param uuid uuid
	 * @param version 版本号
	 * @param cateid 分类ID
	 * @return json
	 */
	@RequestMapping("numsensedetail")
	@ResponseBody
	@InterfaceCache
	public String numsenseDeatil(Integer siteid, Integer clienttype, String uuid,
						         String version, String cateid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if(NumberUtil.getInt(siteid) <= 0 || NumberUtil.getInt(cateid) <= 0){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_NUMSENSE, InterfaceLogConfig.ERROR_07); 
		}
		NumSensePhone phone = numSensePhoneService.findById(NumberUtil.getInt(cateid));
		if(phone == null) {
			return InterfaceLogConfig.interfaceResult(false, 
					InterfaceLogConfig.MOD_NUMSENSE, InterfaceLogConfig.ERROR_13); 
		}
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz() != LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		
		NumSenseCol col = numSenseColService.findById(NumberUtil.getInt(phone.getColId()));
		ret.put("classid", StringUtil.getString(phone.getIid()));
		ret.put("classname", StringUtil.getString(phone.getName()));
		if(StringUtil.isNotEmpty(phone.getIconPath())) {
			ret.put("classpic", jmpUrl + StringUtil.getString(phone.getIconPath()));
		}else{
			ret.put("classpic", jmpUrl +StringUtil.getString(col.getIconPath()));
		}
		if(StringUtil.isEmpty(phone.getBgColor())){
			ret.put("bgcolor", "008fd5");
		} else {
			ret.put("bgcolor", StringUtil.getString(phone.getBgColor()));
		}
		ret.put("fixedphone", StringUtil.getString(phone.getPhone()));
		ret.put("mobilephone", StringUtil.getString(phone.getTel()));
		ret.put("email", StringUtil.getString(phone.getEmail()));
		ret.put("location", StringUtil.getString(phone.getAddress()));
		ret.put("url", StringUtil.getString(phone.getUrl()));
		ret.put("detail", StringUtil.getString(phone.getSpec()));
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 检索接口
	 * @param clienttype 客户端类型
	 * @param siteid 网站ID 
	 * @param uuid uuid
	 * @param pagesize 页码
	 * @param pagenum 页数
	 * @param keyword 关键字
	 * @return json
	 */
	@RequestMapping("childsearch")
	@ResponseBody
	@InterfaceCache
	public String searchNumsense(Integer clienttype, Integer siteid, String uuid, 
				                 Integer pagesize, Integer pagenum, String keyword){
		pagesize = NumberUtil.getInt(pagesize, 10);
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
		if(NumberUtil.getInt(siteid) <= 0 || NumberUtil.getInt(clienttype) <= 0){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_NUMSENSE, InterfaceLogConfig.ERROR_07); 
		}
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz() != LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		List<NumSensePhone> phoneList = numSensePhoneService.findByKeyWord(siteid, pagesize, NumberUtil.getInt(pagenum, 1), keyword);
		if(CollectionUtils.isEmpty(phoneList)){
			return JsonUtil.objectToString(resource);
		}
		for(NumSensePhone phoneEn : phoneList) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("classid", StringUtil.getString(phoneEn.getIid()));
			hm.put("classname", StringUtil.getString(phoneEn.getName()));
			if(StringUtil.isNotEmpty(phoneEn.getIconPath())){
				hm.put("classpic", jmpUrl + StringUtil.getString(phoneEn.getIconPath()));
			}else{
				NumSenseCol col = numSenseColService.findById(NumberUtil.getInt(phoneEn.getColId()));
				hm.put("classpic", jmpUrl + StringUtil.getString(col.getIconPath()));
			}
			hm.put("fixedphone", StringUtil.getString(phoneEn.getPhone()));
			hm.put("mobilephone", StringUtil.getString(phoneEn.getTel()));
			hm.put("location", StringUtil.getString(phoneEn.getAddress()));
			resource.add(hm);
		}
		return JsonUtil.objectToString(resource);
	}
	
}