package com.hanweb.jmp.sys.service.version;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.util.StrUtil;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.pack.dao.ClientDAO;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.entity.Client;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.sys.dao.version.VersionDAO;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.version.Version;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.sys.controller.version.VersionFormBean;
import com.jcraft.jsch.ConfigRepository.Config;

/**
 * 客户端升级Service
 * @author WangFei
 */
public class VersionService {
	
	/**
	 * versionDAO
	 */
	@Autowired
	private VersionDAO versionDAO;
	
	/**
	 * logService
	 */
	@Autowired
	private LogService logService;
	
	/**
	 * logService
	 */
	@Autowired
	private AppService appService;
	
	/**
	 * logService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * clientDao
	 */
	@Autowired
	private ClientDAO clientDao;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 查询客户端最新版本信息
	 * @param siteId logService
	 * @param clientType clientType
	 * @return Version
	 */
	public Version findNewVersionClient(Integer siteId, Integer clientType) {
		List<Version> list = versionDAO.findNewVersionClient(siteId, clientType);
		if(list == null || list.size() == 0){
			return null;
		}
		Version update = new Version();
		for(Version u : list){ 
			if(isMaxVersion(StringUtil.getString(u.getVersion()), 
					StringUtil.getString(update.getVersion()))){
				update = u; 
			}
		}
		return update;
	}

	/**
	 * 比较两个版本号的大小
	 * @param version1 version1
	 * @param version2 version2
	 * @return    设定参数 .
	 */
	public static boolean isMaxVersion(String version1, String version2){
		if(StringUtil.isEmpty(version2)){
			return true;
		} 
		if(version1.split("\\.").length >= version2.split("\\.").length){
			for(int i=0 ; i<version2.split("\\.").length; i++){ 
				if(NumberUtil.getInt(version1.split("\\.")[i]) < NumberUtil.getInt(version2.split("\\.")[i])){
					return false;
				}else if(NumberUtil.getInt(version1.split("\\.")[i]) == NumberUtil.getInt(version2.split("\\.")[i])){
					continue;
				}else{
					return true;
				}
			}
			return true;
		}else{
			for(int i=0 ; i<version1.split("\\.").length; i++){
				if(NumberUtil.getInt(version1.split("\\.")[i]) > NumberUtil.getInt(version2.split("\\.")[i])){
					return true;
				}else if(NumberUtil.getInt(version1.split("\\.")[i]) == NumberUtil.getInt(version2.split("\\.")[i])){
					continue;
				}else{
					return false;
				}
			}
			return false;
		}
	}
	
	/**
	 * 版本号比较
	 * @param version1 Version
	 * @param version2 version2
	 * @return Integer
	 */
	public Integer contrastVersions(String version1, String version2) {
		int flag = 0;
		StringBuffer sb1 = new StringBuffer(version1);
		StringBuffer sb2 = new StringBuffer(version2);
		String[] versionarr1 = version1.split("\\.");
		String[] versionarr2 = version2.split("\\.");
		for (int i = 0; i < Math.abs(versionarr1.length - versionarr2.length); i = i + 2) {
			if (versionarr1.length > versionarr2.length) {
				sb2.append(".0");
			} else if (versionarr1.length < versionarr2.length) {
				sb1.append(".0");
			}
		}
		versionarr1 = sb1.toString().split("\\.");
		versionarr2 = sb2.toString().split("\\.");
		for (int k = 0; k < versionarr1.length; k++) {
			if (NumberUtil.getInt(versionarr1[k]) > NumberUtil.getInt(versionarr2[k])) {
				flag = 1;
				break;
			} else if (NumberUtil.getInt(versionarr1[k]) < NumberUtil.getInt(versionarr2[k])) {
				flag = 2;
				break;
			}
		}
		return flag;
	}

	/**
	 * 删除
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 抛出异常
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> intIds = StringUtil.toIntegerList(ids);
		boolean isSuccess = versionDAO.deleteByIds(intIds);
		if (!isSuccess) {
			throw new OperationException(SpringUtil.getMessage("remove.fail"));
		}
		logService.add(LogConfig.modupdate, LogConfig.oprremove, "");
		return isSuccess;
	}
	
	/**
	 * 新增
	 * @param update update
	 * @return boolean
	 * @throws OperationException 抛出异常
	 */
	public boolean add(VersionFormBean update) throws OperationException {
		int num = this.findNumOfSameName(0, update.getClientType(), update.getVersion(), 
				update.getSiteId());
		if (num > 0) {
			throw new OperationException("版本号已存在,请重新设置！");
		}
		if(Configs.getConfigs().getCreateAppType() == 0) {
			Integer id = versionDAO.insert(update);
			return id > 0;
		} else {
			//文件类型检查
			checkFileType(update);
			
			int siteid=update.getSiteId();
			App appEn=appService.findBySiteid(siteid);
			if(appEn != null){
				int appid=appEn.getIid();
				update.setAppId(appid);
			}
			
//			if(NumberUtil.getInt(update.getApptype())==1){ 
//				update=saveFile(update);
//			} 
			Integer id = versionDAO.insert(update);
			return id > 0;
		}
	}
	
	/**
	 * 查找实体
	 * @param iid boolean
	 * @return boolean
	 */
	public Version findByIid(Integer iid) {
		return versionDAO.queryForEntityById(iid);
	}
	
	/**
	 * 修改
	 * @param update update
	 * @return boolean
	 * @throws OperationException 抛出异常
	 */
	public boolean modify(VersionFormBean version) throws OperationException { 
		int num = this.findNumOfSameName(version.getIid(), version.getClientType(), 
				  version.getVersion(), version.getSiteId());
		if (num > 0) {
			throw new OperationException("版本号已存在,请重新设置！");
		}
		//文件类型检查
		checkFileType(version);
		UpdateSql updateSql = new UpdateSql(Tables.UPDATE);
		updateSql.addString("version", version.getVersion());
		updateSql.addInt("clienttype", version.getClientType());
		updateSql.addInt("updatetype", version.getUpdateType());
		updateSql.addString("msg", version.getMsg());
		updateSql.setWhere("iid=:iid");
		updateSql.addWhereParamInt("iid", version.getIid());
		if(version!=null && Configs.getConfigs()!=null){
			if(Configs.getConfigs().getCreateAppType() == 1) {
				version = saveFile(version);
				updateSql.addString("downurl", version.getDownUrl());
				updateSql.addString("logopath", version.getLogoPath());
			} else {
				updateSql.addString("downurl", version.getDownUrl());
				updateSql.addString("logopath", version.getLogoPath());
			}
		}
		return versionDAO.update(updateSql);
	}

	/**
	 * 查找是否有重复记录
	 * @param iid iid
	 * @param clientType clientType
	 * @param version version
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public int findNumOfSameName(Integer iid, Integer clientType, String version, Integer siteId) {
		if (StringUtil.isEmpty(version) || NumberUtil.getInt(clientType)==0 
				|| NumberUtil.getInt(siteId)==0) {
			return 0;
		}
		int num = versionDAO.findNumOfSameName(iid, clientType, version, siteId);
		return num;
	}
	  
	/**
	 * 新建实体
	 * @param client client
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean createVersion(Client client)throws OperationException{
		int clientType = client.getClientType();
		VersionFormBean update = new VersionFormBean();
		update.setClientType(clientType);
		update.setSiteId(client.getSiteId());
		update.setUpdateType(1);
		update.setVersion(client.getVersion());
		if(clientType==3){
			update.setDownUrl(client.getAndroidUrl());
		}else if(clientType==2){
			update.setDownUrl(client.getPlist());
		}
		update.setCreateTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(),
				DateUtil.YYYY_MM_DD_HH_MM_SS));
		update.setMsg("系统更新");
		return this.add(update);
	}
	
	/**
	 * 删除更新实体
	 * @param client client
	 * @return boolean
	 */
	public boolean deleteVersion(Client client){
		int clienttype = client.getClientType();
		String version = client.getVersion();
		int siteId = client.getSiteId();
		Version version1 = findByClienttypeAndVersion(siteId, clienttype, version);
		return versionDAO.deleteById(version1.getIid());
	}
	
	/**
	 * 根据客户端类型和版本号查找更新实体
	 * @param siteId siteId 
	 * @param clienttype clienttype
	 * @param version version
	 * @return Version
	 */
	private Version findByClienttypeAndVersion(int siteId, int clienttype, String version){
		return versionDAO.findByClientAndVersion(siteId, clienttype, version);
	}
	
	/**
	 * 检查附件类型
	 * @param col
	 *            栏目实体
	 * @return
	 * @throws OperationException
	 *             界面异常
	 */
	private void checkFileType(VersionFormBean update) throws OperationException {
		if(update==null){
			return;
		} 
		//首图
		if (update.getLogoFile()!=null && !update.getLogoFile().isEmpty()) {
			MultipartFileInfo logofile = MultipartFileInfo.getInstance(update.getLogoFile()); 
			if("jpg,png".indexOf(logofile.getFileType().toLowerCase()) == -1){ 
				throw new OperationException("应用图标类型不正确，请重新上传！");
			} 
		}
		
		//PLIST文件
		if (update.getPlistFile()!=null && !update.getPlistFile().isEmpty()) {
			MultipartFileInfo plistfile = MultipartFileInfo.getInstance(update.getPlistFile());  
			if( !plistfile.getFileType().toLowerCase().equals("plist")){
				throw new OperationException("PLIST文件类型不正确，请重新上传！");
			} 
		}
		
		//IPA文件
		if (update.getIpaFile()!=null && !update.getIpaFile().isEmpty()) {
			MultipartFileInfo ipafile = MultipartFileInfo.getInstance(update.getIpaFile());  
			if( !ipafile.getFileType().toLowerCase().equals("ipa")){
				throw new OperationException("IPA文件类型不正确，请重新上传！");
			} 
		}
	}
	
	/**
	 * 保存文件
	 * @param update
	 * @return
	 */
	private VersionFormBean saveFile(VersionFormBean update){
		String clientName="";
		if (NumberUtil.getInt(update.getClientType()) == 4) {
			clientName = "ipad";
		} else if(NumberUtil.getInt(update.getClientType()) ==2){
			clientName = "iphone";
		}else{
			clientName = "android";
		}
		int siteid=update.getSiteId(); 
		int appid=update.getAppId(); 
		int clientType=update.getClientType();
		String version=update.getVersion();
		Site siteEn=siteService.findByIid(siteid);
		//文件路径
		String path = "/web/" + "site" + siteid + "/app/" + appid + "/"+version+"/" + clientName + "/";
		FileUtil.createDir(BaseInfo.getRealPath()+ path); 
		File desFile = null;
		Client clientEn=null;
		List<Client> clientList=null; 
		//iphone
		if(clientType == 2 && update.getIpaFile()!=null){
			desFile = new File(BaseInfo.getRealPath()+path + "iphone.ipa");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getIpaFile());
			
			desFile = new File(BaseInfo.getRealPath()+path + "iphone.plist");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getPlistFile());
			
			desFile = new File(BaseInfo.getRealPath() 
					+ "/web/" + "site"+siteid + "/app/" + appid + "/iphone.png");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getLogoFile());
			File desFile1 = new File(BaseInfo.getRealPath() + "/web/" 
					      + "site"+siteid + "/app/" + appid + "/images/logo.png");
			FileUtil.copyFile(desFile, desFile1);
			HadoopUtil.fileUpload(desFile, "web/" + "site"+siteid + "/app/" + appid + "/iphone.png");
			HadoopUtil.fileUpload(desFile1, "web/" + "site"+siteid + "/app/" + appid + "/images/logo.png");
			String des = BaseInfo.getRealPath() + "/web/" + "site"+siteid + "/app/" + appid + "/iphone.ipa";
			String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteid + "/app/" + appid + "/iphone.plist";
			File desfile = new File(des);
			File desfile1 = new File(des1);
			File sourcefile = new File(BaseInfo.getRealPath()+ path+"iphone.ipa");
			File sourcefile1 = new File(BaseInfo.getRealPath()+ path+"iphone.plist");
			FileUtil.copyFile(sourcefile, desfile);
			FileUtil.copyFile(sourcefile1, desfile1); 
			update.setLogoPath("/web/" + "site"+siteid + "/app/" + appid + "/iphone.png");
			update.setDownUrl("/web/" + "site"+siteid + "/app/" + appid + "/iphone.plist");
			
			//替换plist
			File file = new File(BaseInfo.getRealPath()+ "/web/" + "site"+siteid + "/app/" + appid + "/iphone.plist");
			String str = FileUtil.readFileToString(file);
			str = str.replace("{plisturl}", Configs.getConfigs().getJmpUrl()+"/web/" 
				+ "site"+siteid + "/app/" + appid + "/iphone.ipa");
			str = str.replace("{siteid}", StringUtil.getString(siteid));
			str = str.replace("{version}", version);
			str = str.replace("{appname}", siteEn.getName());
			FileUtil.writeStringToFile(file, str);
			
			// 文件转移
			if(fileUtil.getImplClazz() != LocalFileUtil.class) {
				StrUtil.moveFile(BaseInfo.getRealPath()+"/web/" + "site"+siteid + "/app/" + appid+"/");
			}
			clientList=clientDao.findBySiteidAndClientType(2, update.getSiteId());
			clientEn=clientList.get(0);
			clientEn.setIphoneUrl("/web/" + "site"+siteid + "/app/" + appid + "/iphone.ipa");
			clientEn.setStatus(2);
			clientDao.update(clientEn);
		//android
		}else if(clientType == 3 && update.getApkFile()!=null){
			desFile = new File(BaseInfo.getRealPath()+path + "android.apk");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getApkFile());
			desFile = new File(BaseInfo.getRealPath() 
					+ "/web/" + "site"+siteid + "/app/" + appid + "/android.png");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getLogoFile());
			File desFile1 = new File(BaseInfo.getRealPath() + "/web/" 
					      + "site"+siteid + "/app/" + appid + "/images/logo.png");
			FileUtil.copyFile(desFile, desFile1);
			String des = BaseInfo.getRealPath() 
				       + "/web/" + "site"+siteid + "/app/" + appid + "/android.apk";
			File desfile = new File(des);
			File sourcefile = new File(BaseInfo.getRealPath()+path+"android.apk");
			FileUtil.copyFile(sourcefile, desfile);  
			update.setLogoPath("/web/" + "site"+siteid + "/app/" + appid + "/android.png");
			update.setDownUrl("/web/" + "site"+siteid + "/app/" + appid + "/android.apk");
			
			// 文件转移
			if(fileUtil.getImplClazz() != LocalFileUtil.class) {
				StrUtil.moveFile(BaseInfo.getRealPath()+"/web/" + "site"+siteid + "/app/" + appid+"/");
			}
			
			clientList = clientDao.findBySiteidAndClientType(3, update.getSiteId());
			clientEn = clientList.get(0); 
			clientEn.setAndroidUrl( "/web/" + "site"+siteid + "/app/" + appid + "/android.apk");
			clientEn.setStatus(2);
			clientDao.update(clientEn);
		//ipad
		} else if(clientType == 4 && update.getIpaFile()!=null){
			desFile = new File(BaseInfo.getRealPath()+path + "ipad.ipa");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getIpaFile());
			desFile = new File(BaseInfo.getRealPath()+path + "ipad.plist");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getPlistFile());
			
			desFile = new File(BaseInfo.getRealPath() 
					+ "/web/" + "site"+siteid + "/app/" + appid + "/ipad.png");
			ControllerUtil.writeMultipartFileToFile(desFile, update.getLogoFile());
			
			File desFile1 = new File(BaseInfo.getRealPath() + "/web/" 
					      + "site"+siteid + "/app/" + appid + "/images/logo.png");
			FileUtil.copyFile(desFile, desFile1);
			
			String des = BaseInfo.getRealPath() 
				       + "/web/" + "site"+siteid + "/app/" + appid + "/ipad.ipa";
			String des1 = BaseInfo.getRealPath() 
				        + "/web/" + "site"+siteid + "/app/" + appid + "/ipad.plist";
			File desfile = new File(des);
			File desfile1 = new File(des1);
			File sourcefile = new File(BaseInfo.getRealPath()+ path+"ipad.ipa");
			File sourcefile1 = new File(BaseInfo.getRealPath()+ path+"ipad.plist");
			FileUtil.copyFile(sourcefile, desfile);
			FileUtil.copyFile(sourcefile1, desfile1);  
			update.setLogoPath("/web/" + "site"+siteid + "/app/" + appid + "/ipad.png");
			update.setDownUrl("/web/" + "site"+siteid + "/app/" + appid + "/ipad.plist");
			
			//替换plist
			File file = new File(BaseInfo.getRealPath()+ "/web/" 
					  + "site"+siteid + "/app/" + appid + "/ipad.plist");
			String str = FileUtil.readFileToString(file);
			str = str.replace("{plisturl}", Configs.getConfigs().getJmpUrl()+"/web/" 
				+ "site"+siteid + "/app/" + appid + "/ipad.ipa");
			str = str.replace("{siteid}", StringUtil.getString(siteid));
			str = str.replace("{version}", version);
			str = str.replace("{appname}", siteEn.getName());
			FileUtil.writeStringToFile(file, str);
			
			// 文件转移
			if(fileUtil.getImplClazz() != LocalFileUtil.class) {
				StrUtil.moveFile(BaseInfo.getRealPath()+"/web/" + "site"+siteid + "/app/" + appid+"/");
			}
			clientList=clientDao.findBySiteidAndClientType(4, update.getSiteId());
			clientEn=clientList.get(0);  
			clientEn.setIpadUrl("/web/" + "site"+siteid + "/app/" + appid + "/ipad.ipa");
			clientEn.setStatus(2);
			clientDao.update(clientEn);
		}  
		return update;
	}
	
}