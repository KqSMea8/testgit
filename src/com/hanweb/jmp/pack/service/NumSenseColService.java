package com.hanweb.jmp.pack.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.jmp.apps.dao.numbersense.NumSenseColDAO;
import com.hanweb.jmp.apps.dao.numbersense.NumSensePhoneDAO;
import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.entity.numbersense.NumSensePhone;

public class NumSenseColService {

	/**
	 * numSenseColDAO
	 */
	@Autowired
	private NumSenseColDAO numSenseColDAO;
	
	/**
	 * numSensePhoneDAO
	 */
	@Autowired
	private NumSensePhoneDAO numSensePhoneDAO;
	
	/**
	 * 号码组件
	 * @param siteid 网站ID
	 * @return true/false
	 */
	public boolean addColData(int siteid){
		NumSenseCol numsenCol=new NumSenseCol();
		numsenCol.setCreateTime(new Date());
		numsenCol.setIconPath("/resources/app/images/phone/phonecol.png");
		numsenCol.setIssearch(0);
		numsenCol.setName("便民服务");
		numsenCol.setOrderId(-1);
		numsenCol.setPid(0);
		numsenCol.setSiteId(siteid);
		numsenCol.setType(2);
		int colid = numSenseColDAO.insert(numsenCol); 
		int phoneid = 0;
		if(colid>0){
			String sourcePath = "/resources/app/images/phone/phonecol.png";
			String newPath =  "/web/site" + siteid + "/numsense/col/" + siteid + "/col_source.png";
			FileUtil.copyFile(BaseInfo.getRealPath()+sourcePath, BaseInfo.getRealPath()+ newPath);
			numsenCol.setIid(colid);
			numsenCol.setIconPath(newPath);
			numSenseColDAO.update(numsenCol);
			NumSensePhone numPhone=new NumSensePhone();
			numPhone.setColId(colid);
			numPhone.setCreateTime(new Date());
			numPhone.setName("火警电话");
			numPhone.setOrderId(-1);
			numPhone.setSiteId(siteid);
			numPhone.setPhone("119");
			phoneid=numSensePhoneDAO.insert(numPhone);
		} 
		return (colid>0 && phoneid>0);
	}
	
}