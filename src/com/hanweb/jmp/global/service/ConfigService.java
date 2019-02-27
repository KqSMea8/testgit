package com.hanweb.jmp.global.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.hanweb.common.BaseInfo;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.jmp.constant.CateConfig;
import com.hanweb.jmp.constant.ChannelConfig;
import com.hanweb.jmp.constant.ExtentionConfig;
import com.hanweb.jmp.constant.InfoConfig;

public class ConfigService {
	
	/**
	 * CONFIG_PATH
	 */
	private static final String CONFIG_PATH = BaseInfo.getRealPath() + "/WEB-INF/config/";
	
	/**
	 * CONFIG_CACHENAME
	 */
	private static final String CONFIG_CACHENAME = "config_cache";
	
	/**
	 * 保存配置文件
	 * @param json json
	 * @param fileName fileName
	 */
	public static void saveConfig(String json, String fileName){
		String path = CONFIG_PATH + fileName + ".properties";
		FileUtil.writeByteArrayToFile(new File(path), json.getBytes());
	}
	
	/**
	 * 读取配置文件
	 * @param fileName fileName
	 * @return String
	 */
	public static String readConfig(String fileName){
		String path = CONFIG_PATH + fileName + ".properties";
		File file = new File(path);
		if(file.exists()){
			return FileUtil.readFileToString(new File(path));
		}
		return null;
	}
	
	/**
	 * FINDNAME
	 * @param webId webId
	 * @param type type
	 * @return String
	 */
	private static String findName(int webId, int type){
		String fileName = null;
		switch (type) {
		case 1://频道
			fileName = "channel_";
			break;
		case 2://栏目
			fileName = "cate_";
			break;
		case 3://信息
			fileName = "info_";
			break;
		case 4://扩展模块
			fileName = "exten_";
			break;
		default:
			return "";
		}
		return fileName + webId;
	}
	
	/**
	 * 查找信息配置
	 * @param webId webId
	 * @return    设定参数 .
	 */
	public static InfoConfig findInfoConfig(int webId){
		String key = findName(webId, 3);
		InfoConfig infoConfig = (InfoConfig) CacheUtil.getValue(CONFIG_CACHENAME, key);
		if(infoConfig != null){
			return infoConfig;
		}
		String json = readConfig(key);
		if(json != null && json.trim().length() > 0){
			infoConfig = (InfoConfig) JsonUtil.StringToObject(json, InfoConfig.class);
			
		}
		if(infoConfig == null){
			infoConfig = new InfoConfig();
		}
		CacheUtil.setValue(CONFIG_CACHENAME, key, infoConfig);
		return infoConfig;
	}
	
	/**
	 * 查找cate配置
	 * @param webid webid
	 * @param catetype catetype
	 * @return    设定参数 .
	 */
	public static CateConfig findCateConfig(int webid, int catetype){
		String filename = findName(webid, 2);
		String key = filename+"_"+catetype;
		CateConfig cateConfig = (CateConfig) CacheUtil.getValue(CONFIG_CACHENAME, key);
		if(cateConfig != null){
			return cateConfig;
		}
		String json = readConfig(filename);
		if(json != null && json.trim().length() > 0){
			List<Object> cateConfigs = JsonUtil.StringToList(json, CateConfig.class);
			if(cateConfigs==null){
				return new CateConfig();
			}  
			CateConfig cataConfigEn=null;
			for(Object objectEn : cateConfigs){
				
				if(objectEn==null){
					continue;
				}
				cataConfigEn=(CateConfig) objectEn;
				if(cataConfigEn.getCateType()==catetype){
					cateConfig=cataConfigEn;
					CacheUtil.setValue(CONFIG_CACHENAME, key, cataConfigEn); 
					break;
				}
				
			}   
		} 
		if(cateConfig==null){
			cateConfig=new CateConfig();
		}
		return cateConfig;
	}
	
	/**
	 * findExtenConfig
	 * @param webid webid
	 * @param extentype extentype
	 * @return    设定参数 .
	 */
	public static ExtentionConfig findExtenConfig(int webid, int extentype){
		String filename = findName(webid, 2);
		String key = filename+"_"+extentype;
		ExtentionConfig extenConfig = (ExtentionConfig) CacheUtil.getValue(CONFIG_CACHENAME, key);
		if(extenConfig != null){
			return extenConfig;
		}
		String json = readConfig(filename);
		if(json != null && json.trim().length() > 0){
			List<Object> extenConfigs=JsonUtil.StringToList(json, ExtentionConfig.class);
			if(extenConfigs==null){
				return new ExtentionConfig();
			}  
			ExtentionConfig extenConfigEn=null;
			for(Object objectEn : extenConfigs){
				
				if(objectEn==null){
					continue;
				}
				extenConfigEn=(ExtentionConfig) objectEn;
				if(extenConfigEn.getType()==extentype){
					extenConfig=extenConfigEn;
					CacheUtil.setValue(CONFIG_CACHENAME, key, extenConfigEn); 
					break;
				}
				
			}   
		} 
		if(extenConfig==null){
			extenConfig=new ExtentionConfig();
		}
		return extenConfig;
	}
	
	/**
	 * findCateList
	 * @param webid webid
	 * @return    设定参数 .
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<CateConfig> findCateList(int webid){
		String key = findName(webid, 2);   
		ArrayList<CateConfig> cateList = (ArrayList<CateConfig>)
		CacheUtil.getValue(CONFIG_CACHENAME, key);
		if(cateList != null){
			return cateList;
		}
		String json = readConfig(key); 
		List<Object> cateobjectList=null;
		ArrayList<CateConfig> cateConList =new ArrayList<CateConfig>();
		
		if(json != null && json.trim().length() > 0){ 
			cateobjectList=JsonUtil.StringToList(json, CateConfig.class); 
			CateConfig cateconfig=null;
			if(cateobjectList!=null){
				for(Object objectEn:cateobjectList){
					if(objectEn==null){
						continue;
					}
					cateconfig=(CateConfig) objectEn;
					if(cateconfig.getCateType()<=7){
						continue;
					}
					cateConList.add(cateconfig);
					 
			    } 
				CacheUtil.setValue(CONFIG_CACHENAME, key, cateConList); 
			}   		 
		} 
		return cateConList;
	}
	
	/**
	 * findChannelConfig
	 * @param webid webid
	 * @param channeltype channeltype
	 * @return    设定参数 .
	*/
	public static ChannelConfig findChannelConfig(int webid, int channeltype){
		String filename = findName(webid, 1);
		String key = filename + "_" + channeltype;
		ChannelConfig channelConfig = (ChannelConfig) CacheUtil.getValue(CONFIG_CACHENAME, key); 
		if(channelConfig != null){
			return channelConfig;
		}
		String json = readConfig(filename);
		if(json != null && json.trim().length() > 0){
			List<Object> channelConfigs = JsonUtil.StringToList(json, ChannelConfig.class);
			if(channelConfigs == null){
				return new ChannelConfig();
			}  
			ChannelConfig chanConfigEn = null;
			for(Object objectEn : channelConfigs){
				if(objectEn == null){
					continue;
				}
				chanConfigEn = (ChannelConfig) objectEn;
				if(chanConfigEn.getChannelType() == channeltype){
					channelConfig = chanConfigEn;
					CacheUtil.setValue(CONFIG_CACHENAME, key, channelConfig); 
					break;
				}
			}   
		} 
		if(channelConfig == null){
			channelConfig = new ChannelConfig();
		}
		return channelConfig;
	}
	
	/**
	 * findChannelList
	 * @param webid webid
	 * @return    设定参数 .
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<ChannelConfig> findChannelList(int webid){
		String key = findName(webid, 1);  
		ArrayList<ChannelConfig> channelList = (ArrayList<ChannelConfig>)
		CacheUtil.getValue(CONFIG_CACHENAME, key);
		if(channelList != null){
			return channelList;
		}
		String json = readConfig(key);
		List<Object> objectList =null;
		ArrayList<ChannelConfig> chanConList =new ArrayList<ChannelConfig>();
		if(json != null && json.trim().length() > 0){
			objectList=JsonUtil.StringToList(json, ChannelConfig.class);
			if(objectList!=null){
				ChannelConfig channelconfig=null;
				for(Object objectEn:objectList){
					if(objectEn==null){
						continue;
					}
					channelconfig=(ChannelConfig) objectEn;
					if(channelconfig.getChannelType()==2 || 
							channelconfig.getChannelType()==6){
						continue;
					}
					chanConList.add(channelconfig);
					 
			    } 
				CacheUtil.setValue(CONFIG_CACHENAME, key, chanConList); 
			}   		 
		} 
		return chanConList;
	}
	
}