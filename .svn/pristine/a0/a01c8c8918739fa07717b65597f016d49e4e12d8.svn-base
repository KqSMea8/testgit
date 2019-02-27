package com.hanweb.jmp.pack.service;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.pack.dao.ApplicationDAO;
import com.hanweb.jmp.pack.dao.ClientDAO;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.entity.Client;
import com.hanweb.jmp.pack.entity.CreateReturnEntity;
import com.hanweb.jmp.sys.service.version.VersionService;
import com.hanweb.jmp.util.HttpClientUtil;

public class ClientService {
	
	/**
	 * clientDAO
	 */
	@Autowired
	private ClientDAO clientDAO;
	
	/**
	 * appDAO
	 */
	@Autowired
	private ApplicationDAO appDAO;
	
	/**
	 * versionService
	 */
	@Autowired
	private VersionService versionService;
	
	/**
	 * appService
	 */
	@Autowired
	private AppService appService;

	/**
	 * 新增版本实体
	 * @param client client
	 * @return Integer
	 */
	public Integer addClient(Client client){
		int appId = clientDAO.insert(client);
		return appId;
	}
	
	/**
	 * 根据应用id和版本类型查询版本实体
	 * @param appId appId
	 * @param clientType clientType
	 * @return List<Client>
	 */
	public List<Client> findByAppIdAndClientType(Integer appId, Integer clientType){
		List<Client> clientList = clientDAO.findByAppIdAndClientType(appId, clientType);
		return clientList;
	}
	
	/**
	 * 根据iid查找实体
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Client findByIid(Integer iid){
		return clientDAO.findByIid(iid);
	}
	
	/**
	 * 得到客户端打包返回，并且下载安装包
	 * @param clientJSON clientJSON
	 * @return boolean
	 */
	public boolean modifyClientReturn(String clientJSON){  
		boolean bl = false;  
		CreateReturnEntity createReturnEntity =  JsonUtil.StringToObject(clientJSON, CreateReturnEntity.class);
		int siteId = NumberUtil.getInt(createReturnEntity.getSiteid());
		int clientType = NumberUtil.getInt(createReturnEntity.getClienttype());
		String version = StringUtil.getString(createReturnEntity.getVersion()); 
		//下载包
		if("success".equals(createReturnEntity.getResult())){ 
			String downUrl = createReturnEntity.getUrl();
			String plisturl = createReturnEntity.getPlisturl();
			Client clientEntity = clientDAO.findByVersionAndClientType(version, clientType, siteId);
			clientEntity.setCallbackJson(clientJSON);
			App appEn = appDAO.findByIid(clientEntity.getAppId());
			String clientName = "";
			if (NumberUtil.getInt(clientEntity.getClientType()) == 4) {
				clientName = "ipad";
			} else if(NumberUtil.getInt(clientEntity.getClientType()) ==2){
				clientName = "iphone";
			} else {
				clientName = "android";
			}
			//文件路径
			String path ="/web/" + "site"+appEn.getSiteId() + "/app/" + appEn.getIid() + "/"+version+"/"+clientName+"/";
			FileUtil.createDir(BaseInfo.getRealPath()+ path); 
			if(clientType == 2){
				bl = HttpClientUtil.downloadFile(downUrl, BaseInfo.getRealPath()+ path+"iphone.ipa");
				bl = HttpClientUtil.downloadFile(plisturl, BaseInfo.getRealPath()+ path+"iphone.plist");
				File file = new File(BaseInfo.getRealPath()+ path+"iphone.plist");
				String str = FileUtil.readFileToString(file);
				str = str.replace("{plisturl}", Configs.getConfigs().getJmpUrl()+"/web/" 
				    + "site"+siteId + "/app/" + appEn.getIid() + "/iphone.ipa");
				str = str.replace("{siteid}", StringUtil.getString(siteId));
				str = str.replace("{version}", version);
				FileUtil.writeStringToFile(file, str);
				String des = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appEn.getIid() + "/iphone.ipa";
				String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appEn.getIid() + "/iphone.plist";
				File desfile = new File(des);
				File desfile1 = new File(des1);
				File sourcefile = new File(BaseInfo.getRealPath()+ path+"iphone.ipa");
				File sourcefile1 = new File(BaseInfo.getRealPath()+ path+"iphone.plist");
				FileUtil.copyFile(sourcefile, desfile);
				FileUtil.copyFile(sourcefile1, desfile1);
				clientEntity.setIphoneUrl(Configs.getConfigs().getJmpUrl()+"/web/" + "site"+siteId + "/app/" 
						                  + appEn.getIid() + "/iphone.ipa");
				String url = Configs.getConfigs().getJmpUrl().replace("http", "https");
				clientEntity.setPlist(url +"/web/" + "site"+siteId + "/app/" + appEn.getIid() + "/iphone.plist");
			} else if (clientType == 3){
				bl = HttpClientUtil.downloadFile(downUrl, BaseInfo.getRealPath()+path+"android.apk");
				String des = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appEn.getIid() + "/android.apk";
				File desfile = new File(des);
				File sourcefile = new File(BaseInfo.getRealPath()+ path+"android.apk");
				FileUtil.copyFile(sourcefile, desfile);
				clientEntity.setAndroidUrl(Configs.getConfigs().getJmpUrl() +"/web/" + "site"+siteId + "/app/" 
						                   + appEn.getIid() + "/android.apk");
			} else if (clientType == 4){
				bl = HttpClientUtil.downloadFile(downUrl, BaseInfo.getRealPath()+ path+"ipad.ipa"); 
			}
			if(bl){
				clientEntity.setStatus(2);
			} else {
				clientEntity.setStatus(3);
			}
			bl = bl && clientDAO.update(clientEntity); 
			appService.generateTpl(appEn.getIid(), siteId);
		} 
		return bl;
	}
	
	/**
	 * 更新实体
	 * @param client client
	 * @return boolean
	 */
	public boolean update(Client client){
		return clientDAO.update(client);
	}
	 
	/**
	 * 审核信息
	 * @param ids ids
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyAudit(String ids, Integer siteId) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		} 
		boolean isSuccess = clientDAO.updateStateByIds(idList, 1);
		if (isSuccess) {
			for(int iid : idList){
				Client client = this.findByIid(iid);
				boolean success = versionService.createVersion(client);
				if(!success){
					throw new OperationException("新建更新记录失败！");
				}
			}
		} else {
			throw new OperationException("审核信息失败！");
		}
		return isSuccess;
	}

	/**
	 * 撤审信息
	 * @param ids ids
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyUnAudit(String ids, Integer siteId) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		} 
		boolean isSuccess = clientDAO.updateStateByIds(idList, 2);
		if (isSuccess) {
			for(int iid : idList){
				Client client = this.findByIid(iid);
				boolean success = versionService.deleteVersion(client);
				if(!success){
					throw new OperationException("删除更新记录失败！");
				}
			}
		} else {
			throw new OperationException("审核信息失败！");
		}
		return isSuccess;
	} 
	
}