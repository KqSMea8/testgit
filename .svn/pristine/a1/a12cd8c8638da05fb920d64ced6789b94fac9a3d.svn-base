package com.hanweb.jmp.global.service;

import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.sys.entity.ditch.Ditch;
import com.hanweb.jmp.sys.service.ditch.DitchService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode; 
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.support.controller.CurrentUser;

public class TaskService {

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	 
	/**
	 * ditchService
	 */
	@Autowired
	private DitchService ditchService;
	
	public String findNodeTaskTree() {
        // 创建一个树实例
        Tree tree = Tree.getInstance();
        String ret = "";
        tree.addNode(TreeNode.getInstance("root", null, 
                /*"<a style='color:red'>选择分类</a>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<a style='color:#3498DB' onclick='refleshJgetTree();'>刷新</a>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<a style='color:#3498DB' onclick='clearValue();'>清空</a>"*/"选择分类", null, true)
                .setOpen(true).setIsDisabled(true));
        
        Service service = new Service();
        Document document = null;
        Call call;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL("http://192.168.89.220:8081/taizhou2732u6/services/WSReceive?wsdl"));
            call.setTimeout(30000);
            // 指定方法名称
            call.setOperationName("wsGetXxgkJd");
            // 获取xml格式字符串
            ret = (String) call.invoke(new Object[] {});
            document = DocumentHelper.parseText(ret);
            Element root = document.getRootElement();
            getNodeXml(root, "root", tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tree.parse();
    }
	
	@SuppressWarnings("unchecked")
    private void getNodeXml(Element element, String id, Tree tree) {
	    boolean hasChild = false;
        for (Iterator i = element.elementIterator("Jd"); i.hasNext();) {
            Element e = (Element) i.next();
            if(e.nodeCount()>2){
                hasChild = true;
            }
            tree.addNode(TreeNode.getInstance(e.elementText("JdArea"), "root",e.elementText("JdName"), hasChild, false));
            if(hasChild){
                getChildNodeXml(e, e.elementText("JdArea"), tree);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void getChildNodeXml(Element element, String id, Tree tree) {
        boolean hasChild = false;
        for (Iterator i = element.elementIterator("SubJd"); i.hasNext();) {
            Element e = (Element) i.next();
            if(e.nodeCount()>2){
                hasChild = true;
            }
            tree.addNode(TreeNode.getInstance(e.elementText("Jdarea"), id, e.elementText("JdName"), hasChild, false));
            if(hasChild){
                getChildNodeXml(e, e.elementText("Jdarea"), tree);
            }
        }
    }

    /**
	 * 新增栏目时组织树
	 * @return    设定参数 .
	 */
	public String findColTaskTree(){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		if(NumberUtil.getInt(siteId)==0){
			return "";
		}
		try{
			// 创建一个树实例
			Tree tree = Tree.getInstance();
			tree.addNode(TreeNode.getInstance("root", null, 
					/*"<a style='color:red'>选择分类</a>" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
					"<a style='color:#3498DB' onclick='refleshJgetTree();'>刷新</a>" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
					"<a style='color:#3498DB' onclick='clearValue();'>清空</a>"*/"选择分类", 
					null, true)
					.setOpen(true).setIsDisabled(true)); 
			String treeJson = (String) CacheUtil.getValue(StaticValues.CACHE_SITE, "treeJson_" 
						   + NumberUtil.getInt(siteId));
			if(treeJson!=null){
			    return treeJson;
			}
		    Document document = null;
            List<Ditch> ditchList = ditchService.findBySiteId(siteId);
            if(CollectionUtils.isNotEmpty(ditchList)){
                int type;    //用于判断同步类型
                int i = 0;   //循环递增，给不同的地址替换"C"节点
                String urlpoint = "";   //同步地址
                String logid = "";      //用户名
                String pwd = "";        //密码
                String key = "";
                String ret = "";        //xml格式字符串
                int ditchId;
                for(Ditch ditch : ditchList){
                    i++;
                    urlpoint = ditch.getUrl();
                    logid = ditch.getName();
                    pwd = ditch.getPwd();
                    type = ditch.getSyntype();
                    ditchId = ditch.getIid();
                    if(urlpoint==null || "".equals(urlpoint)|| logid==null || "".equals(logid) || pwd==null || "".equals(logid)){
                        System.out.println("渠道"+ditch.getIid()+"配置有误！");
                        return "";
                    }
                    Service service = new Service();
                    Call call;
                    call = (Call) service.createCall();
                    call.setTargetEndpointAddress(new java.net.URL(urlpoint));  
                    call.setTimeout(30000);
                    //指定方法名称
                    call.setOperationName("getParaData");
                    //指定参数
                    call.addParameter("strLoginId", XMLType.XSD_STRING, ParameterMode.IN);
                    call.addParameter("strPwd", XMLType.XSD_STRING, ParameterMode.IN);
                    call.addParameter("strKey", XMLType.XSD_STRING, ParameterMode.IN);
                    call.addParameter("strParameter", XMLType.XSD_STRING, ParameterMode.IN);
                    call.setReturnType(XMLType.XSD_STRING);
                    //获取xml格式字符串
                    ret = (String) call.invoke(new Object[] {logid, pwd, key, "itaskid"});
                    if("".equals(ret)|| ret.indexOf("<errorcode>A01</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A02</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A03</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A04</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A05</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A06</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A07</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A00</errorcode>") > -1
                            ){
                        System.out.println("渠道"+ditch.getIid()+"获取XML失败！返回结果为："+ret);
                        return tree.parse();
                    }
                    //存入缓存
                    CacheUtil.setValue(StaticValues.CACHE_SITE, "ret_" + NumberUtil.getInt(siteId), ret);
                    //每次节点都是C头,会导致ztree错乱，在此用其它字母替换
                    char c = (char)('C'+(i-1));
                    //char转string
                    String treeN = String.valueOf(c);
                    //解析xml
                    document = DocumentHelper.parseText(ret.replaceAll("C", treeN));
                    Element root = document.getRootElement();
                    if(type==0){    //从jget抓取
                        getJgetXml((Element) root.node(2), "root", tree, urlpoint, logid, pwd, NumberUtil.getInt(type), ditchId);
                    } else {        //从jcms抓取
                        getJcmsColTaskXml(root, "root", tree, urlpoint, logid, pwd, NumberUtil.getInt(type), treeN, ditchId);
                    }
                }
                CacheUtil.setValue(StaticValues.CACHE_SITE, "treeJson_" + NumberUtil.getInt(siteId), tree.parse());
                return tree.parse();
            } else{
                return tree.parse();
            }
		}catch (Exception e) {
			logger.error("findColTaskTree error", e);
		}
	    return "";
	} 
	
	/**
	 * 遍历jget的xml
	 * @param element
	 * @param id
	 * @param tree
	 * @param url
	 * @param name
	 * @param pwd
	 * @param type
	 */
    @SuppressWarnings("unchecked")
    private void getJgetXml(Element element, String id, Tree tree, String url, String name, String pwd, int type, int ditchId){
        boolean hasChild = false;
        if(element!=null){
            for (Iterator<Element> iter = element.elementIterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();
                if("0".equals(e.attributeValue("allowed"))){
                    hasChild = true;
                }
                tree.addNode(TreeNode.getInstance(e.attributeValue("id"), 
                        id, e.attributeValue("text"), hasChild, false)
                        .addAttr("sourceurl", url).addAttr("sourcename", name)
                        .addAttr("sourcepwd", pwd).addAttr("sourcetype", type)
                        .addAttr("ditchId", ditchId));
                if(hasChild){
                    getJgetXml(e, e.attributeValue("id"), tree, url, name, pwd, type, ditchId);
                }
            }
        }
    }
    
    /**
     * 遍历jcms的节点(有孩子)
     * @param element
     * @param parid
     * @param tree
     * @param url
     * @param name
     * @param pwd
     * @param type
     * @param treeN
     */
    @SuppressWarnings("unchecked")
    private void getJcmsColTaskXml(Element element, String parid, Tree tree, String url,
            String name, String pwd, int type, String treeN, int ditchId){
        boolean hasChild = false; 
        String taskid = "";
        String nodename = "";
        String nodeText = "";
        for (Iterator<Element> iter = element.elementIterator(); iter.hasNext(); ) {
            Element e = (Element) iter.next();
            taskid=e.attributeValue("id");
            taskid = taskid + ditchId;
            nodename=e.getName();
            if(!StringUtil.equals(nodename, "nodes") && !StringUtil.equals(nodename, "node")){
                continue;
            }
//            if(taskid.startsWith(treeN)){
//                taskid=taskid.substring(1);
//            }
            if("0".equals(e.attributeValue("allowed"))){
                hasChild = true;
            }
            if("nodes".equals(nodename)){
                nodeText = e.attributeValue("name");
            } else {
                nodeText = e.attributeValue("text");
            }
            if("nodes".equals(nodename)){
                tree.addNode(TreeNode.getInstance(taskid, parid, nodeText, hasChild, false).setIsDisabled(true)
                        .addAttr("sourceurl", url).addAttr("sourcename", name)
                        .addAttr("sourcepwd", pwd).addAttr("sourcetype", type)
                        .addAttr("ditchId", ditchId));
            } else {
                tree.addNode(TreeNode.getInstance(taskid, parid, nodeText, hasChild, false)
                        .addAttr("sourceurl", url).addAttr("sourcename", name)
                        .addAttr("sourcepwd", pwd).addAttr("sourcetype", type)
                        .addAttr("ditchId", ditchId));
            } 
            getJcmsColXml(e, taskid, tree, url, name, pwd, type, treeN, ditchId);
        }
    }
    
   /**
    * 遍历jcms的节点(无孩子)
    * @param element
    * @param id
    * @param tree
    * @param url
    * @param name
    * @param pwd
    * @param type
    * @param treeN
    */
    @SuppressWarnings("unchecked")
    private void getJcmsColXml(Element element, String id, Tree tree, String url,
            String name, String pwd, int type, String treeN, int ditchId){
        boolean hasChild = false; 
        String taskid = "";
        for (Iterator<Element> iter = element.elementIterator(); iter.hasNext(); ) {
            Element e = (Element) iter.next();
            taskid = e.attributeValue("id");
            if(taskid.startsWith(treeN)){
               if(e.nodeCount()==0){
                   taskid = taskid.substring(1);
               }
            }
            tree.addNode(TreeNode.getInstance(taskid, id, e.attributeValue("text"), hasChild, false)
                    .addAttr("sourceurl", url).addAttr("sourcename", name)
                    .addAttr("sourcepwd", pwd).addAttr("sourcetype", type)
                    .addAttr("ditchId", ditchId));  
            getJcmsColXml(e, taskid, tree, url, name, pwd, type, treeN, ditchId); 
        }
    }
    
    /**
     * 刷新--栏目选择任务树时，点击刷新，重新获取jget树
     * @return String
     */
    public String refleshColJgetTree(){
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        if(NumberUtil.getInt(siteId)==0){
            return "";
        }
        try{
            // 创建一个树实例
            Tree tree = Tree.getInstance();
            tree.addNode(TreeNode.getInstance("root", null, 
                    /*"<a style='color:red'>选择分类</a>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='color:#3498DB' onclick='refleshJgetTree();'>刷新</a>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a style='color:#3498DB' onclick='clearValue();'>清空</a>"*/"选择分类", null, false)
                    .setOpen(true).setIsDisabled(true));
            Document document = null;
            List<Ditch> ditchList = ditchService.findBySiteId(siteId);
            if(CollectionUtils.isNotEmpty(ditchList)){
                String urlpoint = "";
                String logid = "";
                String pwd = ""; 
                String key = "";
                String ret = "";
                int ditchId;
                int type;
                int i = 0;
                for(Ditch ditch : ditchList){
                    i++;
                    urlpoint = ditch.getUrl();
                    logid = ditch.getName();
                    pwd = ditch.getPwd();
                    type = ditch.getSyntype();
                    ditchId = ditch.getIid();
                    if(urlpoint==null || "".equals(urlpoint)|| logid==null || "".equals(logid) || pwd==null || "".equals(logid)){
                        logger.warn("接口"+ditch.getIid()+"配置有误！");
                        return "";
                    }
                    Service service = new Service();
                    Call call;
                    call = (Call) service.createCall();
                    call.setTargetEndpointAddress(new java.net.URL(urlpoint));  
                    call.setTimeout(30000);
                    //Receive_ThirdToLM 方法测试
                    //指定方法名称
                    call.setOperationName("getParaData");
                    //指定参数
                    call.addParameter("strLoginId", XMLType.XSD_STRING, ParameterMode.IN);
                    call.addParameter("strPwd", XMLType.XSD_STRING, ParameterMode.IN);
                    call.addParameter("strKey", XMLType.XSD_STRING, ParameterMode.IN);
                    call.addParameter("strParameter", XMLType.XSD_STRING, ParameterMode.IN);
                    call.setReturnType(XMLType.XSD_STRING);
                    //获取xml格式字符串
                    ret = (String) call.invoke(new Object[] {logid, pwd, key, "itaskid"});
                    if("".equals(ret)|| ret.indexOf("<errorcode>A01</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A02</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A03</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A04</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A05</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A06</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A07</errorcode>") > -1
                            ||ret.indexOf("<errorcode>A00</errorcode>") > -1
                            ){
                        System.out.println("渠道"+ditch.getIid()+"获取XML失败！返回结果为："+ret);
                        return tree.parse();
                    }
                    //每次节点都是C头,会导致ztree错乱，在此用其它字母替换
                    char c = (char)('C'+(i-1));
                    //char转string
                    String treeN = String.valueOf(c);
                    //解析xml
                    document = DocumentHelper.parseText(ret.replaceAll("C", treeN));
                    Element root = document.getRootElement();
                    if(type==0){    //从jget抓取
                        getJgetXml((Element) root.node(2), "root", tree, urlpoint, logid, pwd, NumberUtil.getInt(type), ditchId);
                    } else {        //从jcms抓取
                        getJcmsColTaskXml(root, "root", tree, urlpoint, logid, pwd, NumberUtil.getInt(type), treeN, ditchId);
                    }
                }
                CacheUtil.setValue(StaticValues.CACHE_SITE, "treeJson_" + NumberUtil.getInt(siteId), tree.parse());
                return tree.parse();
            } else{
                return tree.parse();
            }
        }catch (Exception e) {
            logger.error("refleshtTree error", e);
        }
        return "";
    }

  
    
}