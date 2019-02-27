package com.hanweb.jmp.cms.controller.matters;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.cms.entity.matters.doc.DocCol;
import com.hanweb.jmp.cms.entity.matters.picture.PictureCol;
import com.hanweb.jmp.cms.entity.matters.video.VideoCol;
import com.hanweb.jmp.cms.service.matters.doc.DocColService;
import com.hanweb.jmp.cms.service.matters.picture.PictureColService;
import com.hanweb.jmp.cms.service.matters.video.VideoColService;
import com.hanweb.jmp.util.EncodeUtil;

@Controller
@RequestMapping("manager/menu")
public class MatterMenuController {

	/**
	 * pictureTypeService
	 */
	@Autowired
	private PictureColService pictureColService;
	/**
	 * docColService
	 */
	@Autowired
	private VideoColService videoColService;
	/**
	 * docColService
	 */
	@Autowired
	private DocColService docColService;
	
	@RequestMapping("mattermenu_show")
	public ModelAndView showMatterMenu(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/jmp/cms/matters/col_menu");
		// 组织树
		Tree tree = Tree.getInstance("colId","colName");
		tree.addNode(TreeNode.getInstance("matter", null, "素材管理","").setOpen(true));
		tree.addNode(TreeNode.getInstance("picture", "matter", "图片", "/manager/matter/list.do"));
		tree.addNode(TreeNode.getInstance("video", "matter", "音视频", "/manager/matter/videocol/list.do"));
		tree.addNode(TreeNode.getInstance("doc", "matter", "附件", "/manager/matter/doccol/list.do"));
		TreeNode treeNode1 = null;
		TreeNode treeNode2 = null;
		TreeNode treeNode3 = null;
		//图片树
		List<PictureCol> pictureColList = pictureColService.findBySiteId(UserSessionInfo.getCurrentUser().getSiteId());
		for (PictureCol pictureCol : pictureColList) {
			treeNode1 = TreeNode.getInstance(pictureCol.getIid() + "", "picture", pictureCol.getName(),
					    "/manager/matter/picture/list.do?name=" 
					  + new EncodeUtil().encodeStr(pictureCol.getName(), "UTF-8")
					  + "&classId=" + pictureCol.getIid() + "");
			tree.addNode(treeNode1);
		}
		//音视频树
		List<VideoCol> videoColList = videoColService.findBySiteId(UserSessionInfo.getCurrentUser().getSiteId());
		for (VideoCol videoCol : videoColList) {
			treeNode2 = TreeNode.getInstance(videoCol.getIid() + "", "video", videoCol.getName(),
					  "/manager/matter/video/list.do?name=" 
					  + new EncodeUtil().encodeStr(videoCol.getName(), "UTF-8")
					  + "&classId=" + videoCol.getIid() + "");
			tree.addNode(treeNode2);
		}
		//附件树
		List<DocCol> docColList = docColService.findBySiteId(UserSessionInfo.getCurrentUser().getSiteId());
		for (DocCol docCol : docColList) {
			treeNode3 = TreeNode.getInstance(docCol.getIid() + "", "doc", docCol.getName(),
					  "/manager/matter/doc/list.do?name=" 
					  + new EncodeUtil().encodeStr(docCol.getName(), "UTF-8")
					  + "&classId=" + docCol.getIid() + "");
			tree.addNode(treeNode3);
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	/**
	 * 素材树
	 * @return    设定参数 .
	 */
	@RequestMapping("menucol_search")
	@ResponseBody
	public String searchMenuCol(String colId) {
		Tree tree = Tree.getInstance();
		if(colId.equals("picture")){
			//图片树
			List<PictureCol> pictureColList = pictureColService.findBySiteId(
					UserSessionInfo.getCurrentUser().getSiteId());
			for (PictureCol pictureCol : pictureColList) {
				tree.addNode(TreeNode.getInstance(pictureCol.getIid() + "", "picture", pictureCol.getName(),
						     " /manager/matter/picture/list.do?name=" 
						     + pictureCol.getName() + "&classId=" + pictureCol.getIid() + ""));
			}
		}else if(colId.equals("video")){
			//音视频树
			List<VideoCol> videoColList = videoColService.findBySiteId(UserSessionInfo.getCurrentUser().getSiteId());
			for (VideoCol videoCol : videoColList) {
				tree.addNode(TreeNode.getInstance(videoCol.getIid() + "", "video", videoCol.getName(),
						     " /manager/matter/video/list.do?name=" 
						     + videoCol.getName() + "&classId=" + videoCol.getIid() + ""));
			}
		}else if(colId.equals("doc")){
			//附件树
			List<DocCol> docColList = docColService.findBySiteId(
					UserSessionInfo.getCurrentUser().getSiteId());
			for (DocCol docCol : docColList) {
				tree.addNode(TreeNode.getInstance(docCol.getIid() + "", "doc", docCol.getName(),
						     " /manager/matter/doc/list.do?name=" 
						     + docCol.getName() + "&classId=" + docCol.getIid() + ""));
			}
		}
		return tree.parse();
	}
	
}