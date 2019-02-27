package com.hanweb.jmp.pack.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.controller.infos.info.InfoFormBean;
import com.hanweb.jmp.cms.dao.infos.InfoCountDAO;
import com.hanweb.jmp.cms.dao.infos.InfoDAO;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.GeneratorABS;
import com.hanweb.jmp.util.ImageUtil;
import com.hanweb.jmp.util.XmlUtil;

public class InfoService {
	/**
	 * infoDAO
	 */
	@Autowired
	private InfoDAO infoDAO;
	
	/**
	 * infoCountDAO
	 */
	@Autowired
	private InfoCountDAO infoCountDAO;
	
	/**
	 * colService
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * colService
	 */
	@Autowired
	private PicService picService; 
	
	/**
	 * infoOperService
	 */
	@Autowired
	private InfoOperService infoOperService;
	
	/**
	 * 新增信息
	 * 
	 * @param info
	 *            信息实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public Integer addInfo(InfoFormBean info) throws OperationException {
		boolean isSuccess = false;
		if (info == null) {
			return null;
		}
		String title = info.getTitle();
		title = title.replace("\n", "").replace("\r", "");
		info.setTitle(title);
		// 排序id
		int orderid = infoDAO.findMinOrderIdByCateID(
				info.getColId(), this.getTableName(info.getSiteId()));
		orderid = orderid - 1;
		info.setOrderid(orderid);
		// 保存新增记录
		int iid = infoDAO.insert(info, this.getTableName(info.getSiteId()));
		if (iid > 0) {
			info = excuteFile(info);
			String content = info.getContent();
			// 摘要
			String abs = GeneratorABS.generatorAbs(content);
			info.setAbs(abs);
			info.setContent(content);
			// 信息存放相对路径
			String relativePath = "/web/site" + info.getSiteId() + "/info/" + info.getColId() + "/"
					+ DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
			String xmlName = "1.xml";
			XmlUtil.createDirectory(BaseInfo.getRealPath()+relativePath);
			info.setPath(relativePath + xmlName);
			info.setIid(iid);
			
			
			// 获取在高级编辑器中手动上传的图片
			ArrayList<String> alFile = null;
			Col col = colService.findByIid(info.getColId());
			if(col == null){
				return null;
			}
			if(NumberUtil.getInt(info.getInfoListType()) == 0){
				info.setInfoListType(col.getInfoListType());
			}
			if(NumberUtil.getInt(info.getInfoContentType()) == 0){
				info.setInfoContentType(col.getInfoContentType());
			}
			// 修改信息
			isSuccess = infoDAO.updateInfo(info);
			if (isSuccess) {
				// 信息保存至xml
				writeXml(info, alFile); 
				//酷图
				if(NumberUtil.getInt(info.getInfoContentType())==4){
					picService.addPics(info, info.getPicname(), info.getPicdesc());
				} 
			}
			//新增信息阅读记录
			InfoCount infoCount = new InfoCount();
			infoCount.setTitleId(iid);
			infoCount.setType(1);
			infoCountDAO.insert(infoCount, "jmp_info_count" + info.getSiteId());
			
		} 
		//jsearch
		Col col = colService.findByIid(info.getColId());
		if(NumberUtil.getInt(col.getIsJsearch()) == 1){
			infoOperService.add(info.getSiteId(), info.getColId(), info.getIid(), 1
					, info.getTitle(), info.getUrl());
		}
		return iid;
	}
    
    /**
     * writeXml:(这里用一句话描述这个方法的作用).
     *
     * @param info info
     * @param alFile alFile
     * @return    设定参数 .
    */
    public boolean writeXml(Info info, ArrayList<String> alFile){
		int filesize=0;
		if(alFile!=null){
			filesize=alFile.size();
		}

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("infoid", info.getIid() + "");
		hm.put("filesize", filesize+"");
		String content=info.getContent();
		String str = "";
		//把内容中的图片替换成{file1}这种格式
		if (alFile != null && alFile.size() > 0) {
			for (int i = 0; i < alFile.size(); i++) {
				str = (String) alFile.get(i);
				content = content.replace(str, "{file"
				+ (i + 1) + "}");
				hm.put("file" + (i + 1), str.replace(Configs.getConfigs().getJmpUrl(), ""));
			}
		}
		hm.put("content", content);
		hm.put("path", info.getPath());
	    return writeInfoXML(hm, info.getSiteId()); 
	} 
     
    /**
     * writeInfoXML:(这里用一句话描述这个方法的作用).
     *
     * @param hm hm
     * @param siteId siteId
     * @return    设定参数 .
    */
    public boolean writeInfoXML(Map<String, String> hm, int siteId){
        try{
            if(hm==null){ 
                return false;
            }
            XmlUtil xmlutil = new XmlUtil("");
            /** 建立document对象 */
            Document document = DocumentHelper.createDocument();
            /** 建立XML文档的根节点 */
            Element root = document.addElement("root");
            xmlutil.setChildNode(hm, root, "infoid", false); //设置信息ID
            xmlutil.setChildNode(hm, root, "content", true); //设置内容
            int fileSize = NumberUtil.getInt(hm.get("filesize")+""); //设置图片附件
            Element files = root.addElement("files");      
            files.addAttribute("size",  fileSize+"");
            String pathValue;
            for(int i=0; i<fileSize; i++){
                pathValue = (String) hm.get("file"+(i+1));       //文件路径
                xmlutil.setAttrNode(files, "file"+(i+1), "", "path", pathValue, true);
            }
            String filePath=StringUtil.getString(hm.get("path"));
            if(!"".equals(filePath)){
            	filePath=BaseInfo.getRealPath()
            		+filePath.substring(0, filePath.lastIndexOf("/"))+"/";
            	File file = new File(filePath);
        	    if (!file.exists()){
        	    	FileUtil.deleteFile(file); 
                } 
            } 
           
            xmlutil.createXmlFile(document, BaseInfo.getRealPath()+"/"+hm.get("path")); 
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /**
	 * 获得不同网站的信息表名
	 * 
	 * @param iid 网站Id
	 * @return String
	 */
	public String getTableName(Integer iid){
		String tableName = "jmp_info" + iid;
		return tableName;
	}
	 
	/**
	 * excuteFile:对上传的附件进行处理.
	 *
	 * @param info 信息实体
	 * @return 处理后的信息
	 * @throws OperationException    设定参数 .
	*/
	private InfoFormBean excuteFile(InfoFormBean info) throws OperationException {
		if (info == null) {
			return null;
		}
		int iid = info.getIid();
		if (iid <= 0) {
			return null;
		}
		int siteId = info.getSiteId(); 
		// 信息存放相对路径
		String relativePath = "/web/site" + siteId + "/info/" + info.getColId() + "/"
				+ DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
		String orignalPicpath = "";
		File firstPicFile = new File(BaseInfo.getRealPath()+info.getFirstPicPath());
		orignalPicpath = relativePath + "info_source.png";
		File desFile = new File(BaseInfo.getRealPath() + orignalPicpath);
		// 由于文件目前属于暂存状态，我们需要把文件拷贝到我们需要的位置
		FileUtil.copyFile(firstPicFile, desFile);
		String distinctPath=BaseInfo.getRealPath() + orignalPicpath;
		
		ImageUtil.scaleFirstMiniature(siteId, distinctPath, distinctPath); 
		if(!StringUtil.isEmpty(orignalPicpath)){
			info.setFirstPicPath(orignalPicpath);
		}
		return info;
	}
	
	

}
