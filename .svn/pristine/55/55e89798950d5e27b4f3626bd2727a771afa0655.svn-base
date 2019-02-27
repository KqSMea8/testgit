package com.hanweb.jmp.pack.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.jmp.apps.dao.read.ReadDAO;
import com.hanweb.jmp.apps.entity.read.Read;

public class ReadService {

	/**
	 * readDAO
	 */
	@Autowired
	private ReadDAO readDAO;
	
	/**
	 * 频道新增 ：单栏目类
	 * @param siteid 
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean addEbook(int siteid){
		String spec = "大汉网络是国家信息部首批认定的国家软件企业、南京市高新技术企业和骨干软件企业，" 
				    + "总部位于南京，在北京、杭州、宁波、济南等地设有分支机构和办事处。" 
				    + "同时，大汉网络以卓越的技术实力，被评为中国电子政务IT100强企业之一，" 
				    + "成为国家政府采购中心指定的电子政务供应商。";
		Read readEn=new Read();
		readEn.setAuthor("朱冬健"); 
		readEn.setChangeTime(new Date());
		readEn.setCreateTime(new Date()); 
		readEn.setFlag(0);
		readEn.setIsparent(false);
		readEn.setName("汉风飞扬");
		readEn.setOrderId(-1); 
		readEn.setPid(0);
		readEn.setPname("");
		readEn.setPubTime(new Date());
		readEn.setSiteId(siteid);
		readEn.setPicsize("23.3");
		readEn.setSpec(spec);
		readEn.setType(1); 
		int iid = readDAO.insert(readEn);
		boolean bl = false;
		if(iid>0){
			readEn.setIid(iid);
			//拷贝文件
			String sourcePath = "/resources/app/images/ebook/hanweb.pdf";
			String distinctPath = "/web/site" + siteid + "/ebook/" + iid + "/book_source.pdf";
			FileUtil.copyFile(BaseInfo.getRealPath() + sourcePath, BaseInfo.getRealPath() + distinctPath);
			readEn.setFilePath(distinctPath);
			
			//拷贝封面图
			sourcePath = "/resources/app/images/ebook/firstpic.png";
			distinctPath = "/web/site" + siteid + "/ebook/" + iid + "/smallpic_source.png";
			FileUtil.copyFile(BaseInfo.getRealPath() + sourcePath, BaseInfo.getRealPath() + distinctPath);
			readEn.setPicPath(distinctPath);
			
			//拷贝海报图
			sourcePath = "/resources/app/images/ebook/bigpic.png";
			distinctPath = "/web/site" + siteid + "/ebook/" + iid + "/bigpic_source.png";
			FileUtil.copyFile(BaseInfo.getRealPath() + sourcePath, BaseInfo.getRealPath() + distinctPath);
			readEn.setBigPath(distinctPath);
			bl = readDAO.update(readEn);
		}
		return bl;
	}
	
}