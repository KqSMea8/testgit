package com.hanweb.jmp.sys.service.ditch;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.sys.dao.ditch.DitchDAO;
import com.hanweb.jmp.sys.entity.ditch.Ditch;

import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.setup.dao.DataInitDAO;

public class DitchService {
	
	/**
	 * DitchDAO
	 */
	@Autowired
	private DitchDAO ditchDAO;
	
	/**
     * SynFieldService
     */
    @Autowired
    private SynFieldService synFieldService;
	
	/**
     * ColDAO
     */
    @Autowired
    private ColDAO colDAO;
    
    /**
     * ColService
     */
    @Autowired
    private ColService colService;
    
    /**
     * DataInitDAO
     */
    @Autowired
    private DataInitDAO dataInitDAO;

	/**
	 * 新增
	 * @param ditch
	 * @return
	 */
	public boolean add(Ditch ditch) throws OperationException{
		if(ditch == null){
			return false;
		}else{
		  // 去重查询先屏蔽
//	        int num = ditchDAO.findNumByUrl(NumberUtil.getInt(ditch.getIid()), 
//	                ditch.getUrl(), NumberUtil.getInt(ditch.getSiteId()));
//	        if(num > 0){
//	            throw new OperationException("接口地址已存在,请重新设置！");
//	        }
	        //数据入库
	        int iid = ditchDAO.insert(ditch);
	        //创建接口地址的同时生成同步字段表
	        String tableName = "jmp_synfield" + "_" +ditch.getSiteId()+ "_" + ditch.getIid();
            boolean isSuccess = synFieldService.createSynFieldTable(tableName);
            if(isSuccess){
                List<String> sqls = FileUtil.readLines(new File(BaseInfo.getRealPath()
                        + "/WEB-INF/sql/init/synfield.sql"), "utf-8");
                if (sqls != null) {
                    for (String sql : sqls) {
                        if (StringUtils.isBlank(sql)) {
                            continue;
                        }
                        if (sql.endsWith(";")) {
                            sql = sql.substring(0, sql.length() - 1);
                        }
                        if(ditch.getSyntype() == 0){
                            sql = sql.replace("jmp_synfield", tableName);
                            sql = sql.replace("{title}", "'标题'");
                            sql = sql.replace("{content}", "'内容'");
                            sql = sql.replace("{siteid}", ""+ditch.getSiteId());
                            sql = sql.replace("{ditchid}", ""+ditch.getIid());
                        } else {
                            sql = sql.replace("jmp_synfield", tableName);
                            sql = sql.replace("{title}", "'vc_title'");
                            sql = sql.replace("'网页URL'", "'vc_href'");
                            sql = sql.replace("'发布时间'", "'c_deploytime'");
                            sql = sql.replace("'来源'", "'vc_source'");
                            sql = sql.replace("'作者'", "'vc_author'");
                            sql = sql.replace("'视频'", "'vc_media'");
                            sql = sql.replace("'摘要'", "'vc_describe'");
                            sql = sql.replace("'副标题'", "'vc_sectitle'");
                            sql = sql.replace("{content}", "'vc_content'");
                            sql = sql.replace("{siteid}", ""+ditch.getSiteId());
                            sql = sql.replace("{ditchid}", ""+ditch.getIid());
                        }
                        Query query = dataInitDAO.createQuery(sql);
                        dataInitDAO.execute(query);
                    }
                }
                return true;
            }
	        return iid > 0 && isSuccess; 
		}
	}

	/**
	 * 通过id查找实体
	 * @param iid
	 * @return
	 */
	public Ditch findByIid(Integer iid) {
		if(iid <= 0){
			return null;
		}
		return ditchDAO.findByIid(iid);
	}

	/**
	 * 修改渠道
	 * @param ditch
	 * @return
	 */
	public boolean modify(Ditch ditch) {
		if(ditch == null){
			return false;
		}
//		int num = ditchDAO.findNumByUrl(NumberUtil.getInt(ditch.getIid()), 
//				ditch.getUrl(), NumberUtil.getInt(ditch.getSiteId()));
//		if(num > 0){
//			throw new OperationException("接口地址已存在，请重新设置！");
//		}
		//查找原实体信息
		Ditch ditch1 = ditchDAO.findByIid(ditch.getIid());
		
		//判断原接口地址是否存在被栏目调用的情况
		int colNum = colDAO.findCountByUrl(ditch1.getUrl(), ditch.getSiteId());
		
//		//若存在被栏目调用的情况，则不允许修改
//		if ((colNum>0 && !ditch1.getUrl().equals(ditch.getUrl())) || (colNum>0 && !ditch1.getName().equals(ditch.getName()))
//                || (colNum>0 && !ditch1.getPwd().equals(ditch.getPwd()))) {
//            throw new OperationException("该渠道已被栏目调用,请先修改或删除相关栏目!");
//        }
		
		boolean isSuccess = ditchDAO.update(ditch);
		if(isSuccess){
			try {
			    // 修改渠道后需修改绑定了该渠道的栏目信息
		        if(colNum > 0){
		            List<Col> colList = colService.findBySourceUrl(ditch1.getUrl(), ditch1.getSiteId());
		            if(CollectionUtils.isNotEmpty(colList)){
		                for(Col col : colList){
		                    col.setSourceurl(ditch.getUrl());
		                    col.setSourcename(ditch.getName());
		                    col.setSourcepwd(ditch.getPwd());
		                    colDAO.update(col);
		                }
		            }
		        }
				CacheUtil.removeAll(StaticValues.CACHE_COL);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return isSuccess;
	}
	
	/**
	 * 删除渠道
	 * @param ids
	 * @return
	 */
	public boolean removeByIds(String ids) {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		for(Integer iid : idList){
		    Ditch ditch = ditchDAO.findByIid(iid);
		    if(ditch!=null){
		        int colNum = colDAO.findCountByUrl(ditch.getUrl(), ditch.getSiteId());
		        if (colNum>0) {
		            throw new OperationException("存在被栏目调用的渠道,请先删除相关栏目!");
		        }
		        //删除渠道钱先删除相关的同步字段表
		        String tableName = "jmp_synfield" + "_" + ditch.getSiteId()+ "_" + ditch.getIid();
		        synFieldService.dropTableByTableName(tableName);
		    }
		}
		return ditchDAO.deleteByIds(idList);
	}

	/**
	 * 查找当前站点下的所有渠道
	 * @param siteId
	 * @return
	 */
	public List<Ditch> findBySiteId(int siteId) {
		if(siteId == 0){
			return null;
		}
		return ditchDAO.findBySiteId(siteId);
	}

	/**
	 * 根据url查找ditchid
	 * @param sourceurl
	 * @return
	 */
    public Ditch findByUrl(String sourceurl, Integer siteId) {
        return ditchDAO.findByUrl(sourceurl, siteId);
    }

    /**
     * 修改启用状态
     * @param iid
     * @param enable
     * @return
     */
    public boolean modifyEnable(Integer iid, Integer enable) {
        if(iid.intValue()<1){
            return false;
        }
        return ditchDAO.modifyEnable(iid, enable);
    }
    
}