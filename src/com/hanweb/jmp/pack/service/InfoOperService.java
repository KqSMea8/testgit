package com.hanweb.jmp.pack.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.cms.dao.infos.InfoOperDAO;
import com.hanweb.jmp.cms.entity.infos.InfoOperate;

@Service
public class InfoOperService {
	
	/**
	 * infoOperDAO
	 */
	@Autowired
	private InfoOperDAO infoOperDAO;
	
	/**
	 * add:信息操作数据插入.
	 *
	 * @param siteid 网站id
	 * @param colid 栏目id
	 * @param infoid 信息id
	 * @param oprType 操作类型   1新增    2删除  3修改  4审核 5 撤审
	 * @param title title
	 * @param url url
	 * @return    设定参数 .
	*/
	public Integer add(int siteid, int colid, int infoid, 
			int oprType, String title, String url) {
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
	 * add:(这里用一句话描述这个方法的作用).
	 *
	 * @param infoOper infoOper
	 * @return    设定参数 .
	*/
	public int add(InfoOperate infoOper){
		if(infoOper == null){
			return 0;
		}
		return NumberUtil.getInt(infoOperDAO.insert(infoOper));
	}

}
