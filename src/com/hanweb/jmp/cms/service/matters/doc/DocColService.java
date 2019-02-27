package com.hanweb.jmp.cms.service.matters.doc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.dao.matters.doc.DocColDAO;
import com.hanweb.jmp.cms.entity.matters.doc.DocCol;

public class DocColService {
	
	/**
	 * docColDAO
	 */
	@Autowired
	private DocColDAO docColDAO;
	
	/**
	 * 新增分类
	 * @param docCol
	 * @return
	 * @throws OperationException
	 */
	public boolean add(DocCol docCol) throws OperationException{
		if(docCol == null){
			return false;
		}
		int num = this.findNumOfSameName(0, docCol.getName(), docCol.getSiteId());
		if (num > 0) {
			throw new OperationException("分类名称已存在,请重新设置！");
		}
		Integer iid = docColDAO.insert(docCol);
		return NumberUtil.getInt(iid) > 0;
	}
	
	/**
	 * 根据iid查找信息
	 * @param iid
	 * @return
	 */
	public DocCol findByIid(Integer iid) {
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return docColDAO.queryForEntityById(iid);
	}
	
	/**
	 * 根据siteId查找
	 * @param siteid
	 * @return
	 */
	public List<DocCol> findBySiteId(Integer siteId) {
		List<DocCol> list = null;
		if(NumberUtil.getInt(siteId) > 0){
			list = docColDAO.findDocCol(siteId);
		}
		if(list == null){
			list = new ArrayList<DocCol>();
		}
		return list;
	}
	
	/**
	 * 修改分类
	 * @param docCol
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(DocCol docCol) throws OperationException{
		if(docCol == null){
			return false;
		}
		return docColDAO.update(docCol);
	}
	
	/**
	 * 删除分类
	 * @param ids
	 * @param siteId
	 * @param isremove
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids,Integer siteId,String  isremove) throws OperationException{
		boolean hasSubInfo = this.checkHaveInfo(ids);
		if (hasSubInfo) {
			throw new OperationException("所选分类或者回收站存在附件,请先删除附件!");
		}
		boolean hasRecycleInfo = this.checkRecycleInfo(isremove, siteId);
		if (hasRecycleInfo) {
			throw new OperationException("所选分类回收站存在附件,请先删除附件!");
		}
		return docColDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 检查回收站是否有信息.
	 * @param ids 栏目ID串 如:1,2,3
	 * @param siteId 网站id
	 * @return    设定参数 .
	*/
	public boolean checkRecycleInfo(String isremove, Integer siteId) {
		List<Integer> idsList = StringUtil.toIntegerList(isremove, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = docColDAO.findCountRecycleInfo(idsList, siteId);
		return num > 0;
	}
	
	/**
	 * 排序
	 * @return
	 */
	public int findMaxOrderId() {
		return NumberUtil.getInt(docColDAO.findMaxOrderId());
	}
	
	/**
	 * 查找分类
	 * @param siteId
	 * @return
	 */
	public List<DocCol> finddocColBySiteId(Integer siteId) {
		if(NumberUtil.getInt(siteId) <= 0){
			return null;
		}
		return docColDAO.findDocCol(siteId);
	}
	
	/**
	 * 修改排序id
	 * @param ids ids
	 * @param orderids orderids
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if(StringUtil.isEmpty(ids) || StringUtil.isEmpty(orderids)){
			return false;
		}
		List<Integer> idList = StringUtil.toIntegerList(ids);
		List<Integer> orderList = StringUtil.toIntegerList(orderids);
		if(idList.size() != orderList.size()){
			return false;
		}
		for(int i=0; i< idList.size(); i++){
			int id = NumberUtil.getInt(idList.get(i));
			int order = NumberUtil.getInt(orderList.get(i));
			docColDAO.updateOrder(id, order);
		}
		return true;
	}
	
	/**
	 * 判重方法
	 * @param id id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @return num
	 */
	public int findNumOfSameName(Integer id, String name, Integer siteId) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = docColDAO.findNumOfSameName(id, name, siteId);
		return num;
	}
	
	/**
	 * 判断分类下是否还有附件实体
	 * @param ids
	 * @return
	 */
	public boolean checkHaveInfo(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = docColDAO.findCountSubInfo(idsList);
		return num > 0;
	}

	/**
	 * 系统自动生成一条附件分类
	 * @param siteId
	 * @return 
	 * @throws ParseException 
	 */
    public Integer addFieldsBySiteId(Integer siteId) throws ParseException {
        String name = "个人分类";
        String spec = "新建站点时初始化个人分类";
        DocCol docCol = new DocCol();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        docCol.setPid(2);
        docCol.setOrderId(1);
        docCol.setName(name);
        docCol.setSpec(spec);
        docCol.setSiteId(siteId);
        docCol.setCreateTime(sdf.parse(sdf.format(new Date())));
        return docColDAO.insert(docCol);
    }
	
}