package com.hanweb.jmp.pack.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.controller.cols.ColFormBean;
import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.cms.dao.cols.ColRelationDAO;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.cols.ColRelation;
import com.hanweb.jmp.constant.Tables;


public class ColService {
	
	/**
	 * colDAO
	 */
	@Autowired
	private ColDAO colDAO;
	
	/**
	 * colreDAO
	 */
	@Autowired 
	private ColRelationDAO colreDAO;

	/**
	 * 新增栏目
	 * @param col
	 *            栏目实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public Integer addCol(ColFormBean col) throws OperationException {
		if (col == null) {
			return 0;
		}
		//栏目层级
		int collevel=1; 
		col.setCollevel(collevel);
		col.setEnable(1);
		col.setCreateTime(new Date());
		String orderId = colDAO.getStrMaxKey(Tables.COL, "orderid");
		col.setOrderId(NumberUtil.getInt(orderId));
		col.setBookorderId(NumberUtil.getInt(orderId));
		
		//栏目不为互动栏目，将互动类型重置
		if(col.getType() != 3){
			col.setHdType(0);
		}
		//栏目类型不为虚拟栏目，将栏目平铺方式重置
		if(col.getType() != 1){
			col.setColListType(0);
		}
		int iid = colDAO.insert(col);
		
		//上传背景图以及栏目图标及首图
		col = excuteDefaultPic(col);
		colDAO.update(col);
//		siteService.modifyColFlag(col.getSiteId());
		return iid;
	}
	
	/**
	 * 根据Iid获取栏目详细信息
	 * @param iid
	 *            主键ID
	 * @return 栏目实体
	 */
	public Col findByIid(int iid) {
		if (NumberUtil.getInt(iid) == 0) {
			return null;
		}
		Col col = (Col) CacheUtil.getValue("col", ""+iid);
		if(col == null){
			col = colDAO.findByIid(iid);
			ColRelation colreEn = colreDAO.findByColid(iid);
			if (colreEn != null) {
				col.setColRelationIid(NumberUtil.getInt(colreEn.getIid()));
				col.setTaskId(NumberUtil.getInt(colreEn.getTaskId()));
				col.setTaskName(colreEn.getTaskName());
			}
			CacheUtil.setValue("col", "" + iid, col);
		}
		return col;
	}
	 
	/**
	 * 处理默认图片.
	 * @param col 栏目
	 * @return 栏目
	 * @throws OperationException    设定参数 .
	*/
	private ColFormBean excuteDefaultPic(ColFormBean col) throws OperationException {
		if(col==null){
			return col;
		} 
		return col;
	}

}