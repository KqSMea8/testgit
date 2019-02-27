package com.hanweb.jmp.sys.service.sites;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.sys.dao.sites.SiteSplashDAO;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;

public class SiteSplashService {
	
	/**
	 * siteSplashDAO
	 */
	@Autowired
	SiteSplashDAO siteSplashDAO;
	
	/**
	 * 新增 
	 * @param siteSplash 
	 * @return 站点引导页id
	 */
	public Integer add(SiteSplash siteSplash){
		return siteSplashDAO.insert(siteSplash);
	}
	
	/**
	 * 根据引导页id删除记录
	 * @param iid 引导页id
	 * @return true 成功<b/>
	 * 		   false 失败
	 */
	public boolean removeById(Integer iid){
		return siteSplashDAO.deleteById(iid);
	}
	
	/**
	 * 修改引导页
	 * @param siteSplash 
	 * @return true 成功<b/>
	 * 		   false 失败
	 */
	public boolean modify(SiteSplash siteSplash){
		SiteSplash curSiteSplash = this.findBySiteId(siteSplash.getSiteId());
		if(curSiteSplash != null){
			siteSplash.setIid(curSiteSplash.getIid());
			return siteSplashDAO.update(siteSplash);
		}else{
			return this.add(siteSplash) > 0;
		}	
	}

	/**
	 * 根据站点id查找引导页
	 * @param siteId 
	 * @return SiteSplash 
	 */
	public SiteSplash findBySiteId(Integer siteId){
		return siteSplashDAO.findBySiteId(siteId);
	}
	
	public List<SiteSplash> findSiteSplashs() {
		return siteSplashDAO.findSiteSplashs();
	}
	
}