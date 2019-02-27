package com.hanweb.jmp.cms.service.infos;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.cms.dao.infos.InfoOperDAO;
import com.hanweb.jmp.cms.entity.infos.InfoOperate;

public class InfoOperService {
	
	/**
	 * infoOperDAO
	 */
	@Autowired
	private InfoOperDAO infoOperDAO;
	
	/**
	 * 增加信息操作
	 * @param infoOper infoOper
	 * @return    设定参数 .
	 */
	public int add(InfoOperate infoOper){
		if(infoOper == null){
			return 0;
		}
		return NumberUtil.getInt(infoOperDAO.insert(infoOper));
	} 
	
	/**
	 * 信息操作数据插入
	 * @param siteid 网站id
	 * @param colid 栏目id
	 * @param infoid 信息id
	 * @param oprType 操作类型   1新增    2删除  3修改  4审核 5 撤审
	 * @param title title
	 * @param url url
	 * @return    设定参数 .
	 */
	public Integer add(int siteid, int colid, int infoid, int oprType, String title, String url) {
		 if(siteid <= 0 || colid <= 0){
			 return 0;
		 }
		 InfoOperate infoOperEn=new InfoOperate();
		 infoOperEn.setSiteId(siteid);
		 infoOperEn.setColId(colid);
		 infoOperEn.setInfoId(infoid);
		 infoOperEn.setOprType(oprType);
		 infoOperEn.setIsSearch(0);
		 infoOperEn.setIsOffline(0);
		 infoOperEn.setUrl(url);
		 infoOperEn.setTitle(title);
		 infoOperEn.setCreateTime(new Date());
		 return add(infoOperEn);
	}
	
	/**
	 * 修改信息操作
	 * @param infoOper infoOper
	 * @return    设定参数 .
	 */
	public boolean modify(InfoOperate infoOper){
		if(infoOper == null){
			return false;
		}
		return infoOperDAO.update(infoOper);
	}
	
	/**
	 * 删除信息操作
	 * @param ids ids
	 * @return    设定参数 .
	 */
	public boolean remove(String ids){
		if(StringUtil.isEmpty(ids)){
			return false;
		}
		return infoOperDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 查询离线信息
	 * @param siteId siteId
	 * @param cateId cateId
	 * @return    设定参数 .
	 */
	public List<InfoOperate> findOfflineByCateId(int siteId, int cateId) {
		if(siteId == 0 || cateId == 0){
			return null;
		}
		return infoOperDAO.findOfflineByCateId(siteId, cateId);
	}
	
	/**
	 * 查询索引库信息数量
	 * @param siteId siteId
	 * @param cateId cateId
	 * @return    设定参数 .
	 */
	public int findCountJsearchByCateId(int siteId, int cateId){
		if(siteId == 0 || cateId == 0){
			return 0;
		}
		return infoOperDAO.findCountJsearchByCateId(siteId, cateId);
	}
	
	/**
	 * 查询索引库信息
	 * @param siteId siteId
	 * @param cateId cateId
	 * @return    设定参数 .
	 */
	public List<InfoOperate> findJsearchByCateId(int siteId, int cateId) {
		if(siteId == 0 || cateId == 0){
			return null;
		}
		return infoOperDAO.findJsearchByCateId(siteId, cateId);
	}
	
	/**
	 * 通过最大id修改离线信息
	 * @param siteId siteId
	 * @param cateId cateId
	 * @param maxId maxId
	 * @return    设定参数 .
	 */
	public boolean modifyIsOfflineByMaxId(int siteId, int cateId, int maxId){
		if(siteId == 0 || cateId == 0){
			return false;
		}
		return infoOperDAO.updateIsOfflineByMaxId(siteId, cateId, maxId);
	}
	
	/**
	 * 通过最大id修改索引库
	 * @param siteId siteId
	 * @param colId colId
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public boolean modifyIsJsearchByMaxId(int siteId, int colId, String iid) {
		if(siteId == 0 || colId == 0){
			return false;
		}
		return infoOperDAO.updateIsJsearchByMaxId(siteId, colId, StringUtil.toIntegerList(iid));
	}
	
	/**
	 * 根据信息ID删除记录
	 * @param ids ids
	 * @return boolean
	 */
	public boolean removeByInfoIds(List<Integer> ids){
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return infoOperDAO.deleteByInfoIds(ids);
	}
	
}