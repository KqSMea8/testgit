package com.hanweb.complat.controller.user;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.annotation.Permission;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.task.TaskManager;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.entity.Group;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.EmailService;
import com.hanweb.complat.service.GroupManagerService;
import com.hanweb.complat.service.GroupService;
import com.hanweb.complat.service.RoleService;
import com.hanweb.complat.service.UserService;
import com.hanweb.complat.task.RestDynamicCodeTask;
import com.hanweb.support.controller.CurrentUser;

/**
 * 用户操作页控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@Permission(module = "user", allowedGroup = Allowed.YES)
@RequestMapping("manager/user")
public class OprUserController {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupManagerService groupManagerService;
	
	@Autowired
	private EmailService emailService;

	/**
	 * 显示新增用户页面
	 * 
	 * @return
	 */
	@Permission(function = "add_show")
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(Integer groupId) {

		ModelAndView modelAndView = new ModelAndView("complat/user/user_opr");
		UserFormBean userFormBean = new UserFormBean();
		
		userFormBean.getUser().setGroupId(groupId);
		
		String groupName = groupService.findNameByIid(groupId);
		userFormBean.setGroupName(groupName);

		// 机构树(所属机构和管理机构选择)
		String groupTreeJson = this.findGroupTreeJson();
		
		// 可选角色，即所有选项
		LinkedHashMap<Integer, String> allRoleOptions = this.findAllRoleOptions();
		
		// 新增用户初始角色，包括默认角色和机构拥有的角色
		List<Role> groupRoleList = roleService.findGroupRoles(groupId);
		List<Role> defaultRoleList = roleService.findDefaultRoles();
		
		LinkedHashSet<Integer> selectedRole = new LinkedHashSet<Integer>();
		HashSet<Integer> noRemoveRole = new HashSet<Integer>();
		for (Role role : groupRoleList) {
			selectedRole.add(role.getIid());
			noRemoveRole.add(role.getIid());
		}
		for (Role role : defaultRoleList) {
			selectedRole.add(role.getIid());
		}

		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("formBean", userFormBean);
		modelAndView.addObject("groupMenu", groupTreeJson);
		modelAndView.addObject("allRoleOptions", JsonUtil.objectToString(allRoleOptions));
		modelAndView.addObject("selectedRoleIds", JsonUtil.objectToString(selectedRole));
		modelAndView.addObject("noremoveRoleIds", JsonUtil.objectToString(noRemoveRole));
		return modelAndView;
	}
	
	/**
	 * 机构树(所属机构和管理机构选择)
	 * @return 输的JSON字符串
	 */
	private String findGroupTreeJson() {
		Integer adminRangeId = -1;
		String adminRangeName;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		
		Tree tree = Tree.getInstance();
		if (currentUser.isSysAdmin()) {
			List<Group> groupList = groupService.findChildGroupByIid(0, currentUser.getSiteId());
			for (Group group : groupList) {
				tree.addNode(TreeNode.getInstance(group.getIid() + "", 0 + "",
						group.getName()).setIsParent(group.getIsParent()));
			}
		} else {
			adminRangeId = currentUser.getRangeId();
			if (adminRangeId == null) {
				adminRangeId = currentUser.getGroupId(); /* 缺省 管理当前机构 */
			}
			adminRangeName = groupService.findNameByIid(adminRangeId);
			tree.addNode(TreeNode.getInstance(adminRangeId + "", "menu", adminRangeName, true));
		}
		
		return tree.parse();
	}
	
	/**
	 * 获得可选角色，即所有选项
	 * @return
	 */
	private LinkedHashMap<Integer, String> findAllRoleOptions() {
		List<Role> roleList = null;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser.isSysAdmin()) {
			roleList = roleService.findAllRoles(currentUser.getSiteId());
		} else {
			roleList = roleService.findUserRoles(currentUser.getIid());
		}
		LinkedHashMap<Integer, String> allRoleOptions = new LinkedHashMap<Integer, String>();
		for (Role role : roleList) {
			allRoleOptions.put(role.getIid(), role.getName());
		}
		return allRoleOptions;
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 *            用户实体
	 * @return
	 */
	@Permission(function = "add_submit")
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult saveAdd(UserFormBean userFormBean) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = userService.add(userFormBean);
			if (isSuccess) {
				// 判断动态码
				if (Settings.getSettings().isDynamicCodeLogin()) {
					emailService.modifyDynamicCodeAndSendEmail(userFormBean.getUser().getIid());
				}
				jsonResult.set(ResultState.ADD_SUCCESS);
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 显示编辑用户页面
	 * 
	 * @param iid
	 *            用户ID
	 * @return
	 */
	@Permission(function = "modify_show")
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("complat/user/user_opr");
		
		UserFormBean userFormBean = new UserFormBean();
		
		User user = userService.findByIid(iid);
		
		userFormBean.setUser(user);
		
		Integer groupId = user.getGroupId();
		String groupName = groupService.findNameByIid(user.getGroupId());
		userFormBean.setGroupName(groupName);
		
		// 机构树(所属机构和管理机构选择)
		String groupTreeJson = this.findGroupTreeJson();

		// 可选角色，即所有选项
		LinkedHashMap<Integer, String> allRoleOptions = this.findAllRoleOptions();
		
		// 可管理机构的ID
		Integer rangeId = groupManagerService.findRangeIdByUserId(user.getIid());// 获得目标用户 管理的机构ID
		String rangeName = groupService.findNameByIid(rangeId);
		userFormBean.setRangeId(rangeId);
		userFormBean.setRangeName(rangeName);

		// 不可移除角色
		HashSet<Integer> noRemoveRole = new HashSet<Integer>();
		List<Role> groupRoleList = roleService.findGroupRoles(groupId);// 机构拥有的角色
		for (Role role : groupRoleList) {
			noRemoveRole.add(role.getIid());
		}
		
		// 已选角色
		List<Role> selectedRole = roleService.findUserRoles(user.getIid());
		int[] selectedRoleIds = new int[selectedRole.size()];
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		for (int i=0;i<selectedRole.size();i++) {
			Role role = selectedRole.get(i);
			int roleId = role.getIid();
			selectedRoleIds[i] = roleId;

			if (currentUser.isGroupAdmin() && !currentUser.isSysAdmin() && roleId == 1) {//机构管理员不可移除系统管理员角色
				noRemoveRole.add(1);
			}
		}
		
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("formBean", userFormBean);
		modelAndView.addObject("groupMenu", groupTreeJson);
		modelAndView.addObject("rangeMenu", groupTreeJson);
		modelAndView.addObject("allRoleOptions", JsonUtil.objectToString(allRoleOptions));
		modelAndView.addObject("selectedRoleIds", JsonUtil.objectToString(selectedRoleIds));
		modelAndView.addObject("noremoveRoleIds", JsonUtil.objectToString(noRemoveRole));
		
		return modelAndView;
	}

	/**
	 * 编辑用户
	 * 
	 * @param user
	 *            用户实体
	 * @return
	 */
	@Permission(function = "modify_submit")
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(UserFormBean userFormBean) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = userService.modify(userFormBean);
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 *            用户ID串 如:1,2,3,4
	 * @return
	 */
	@Permission(function = "remove")
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = userService.removeByIds(ids);
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 切换用户状态
	 * 
	 * @param iid
	 *            用户ID
	 * @param enable
	 *            是否有效<br>
	 *            1 有效<br>
	 *            0 失效
	 * @return
	 */
	@Permission(function = "enable_modify")
	@RequestMapping(value = "enable_modify")
	@ResponseBody
	public JsonResult modifyEnable(Integer iid, Integer enable) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = userService.modifyEnable(iid, enable);
		jsonResult.setSuccess(isSuccess);
		if (isSuccess) {
			jsonResult.setMessage("opr.success");
		} else {
			jsonResult.setMessage("opr.fail");
		}
		
		return jsonResult;
	}

	/**
	 * 用户导出
	 * 
	 * @param ids
	 *            用户ID串 如:1,2,3,4
	 * @param groupId
	 *            机构ID
	 * @return
	 */
	@Permission(function = "export")
	@RequestMapping(value = "export")
	public FileResource export(String ids, Integer groupId) {
		File file = null;
		FileResource fileResouce = null;
		try {
			String filePath = userService.exportUser(ids, groupId);
			file = new File(filePath);
			fileResouce = ControllerUtil.getFileResource(file, "用户列表.xls");
		} catch (Exception e) {
			logger.error("export group Error ", e);
			return null;
		} finally {
			if (file != null && file.exists()) {
				file.delete();
			}
		}
		return fileResouce;
	}

	/**
	 * 显示用户导入页面
	 * 
	 * @return
	 */
	@Permission(function = "import_show")
	@RequestMapping(value = "import_show")
	@ResponseBody
	public ModelAndView showImport() {
		ModelAndView modelAndView = new ModelAndView("complat/user/user_import");

		modelAndView.addObject("exporturl", "user.xls");
		modelAndView.addObject("url", "import_submit.do");
		return modelAndView;
	}

	/**
	 * 用户导入
	 * 
	 * @param file
	 *            上传的文件
	 * @return
	 */
	@Permission(function = "import_submit")
	@RequestMapping(value = "import_submit")
	@ResponseBody
	public String submitImport(MultipartFile file) {
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		if (MultipartFileInfo.isEmpty(file)) {
			message = SpringUtil.getMessage("import.nofile");
		} else {
			try {
				MultipartFileInfo info = MultipartFileInfo.getInstance(file);
				if (ArrayUtils.contains(FileUtil.EXCEL_FILE, info.getFileType())) {
					File filePath = new File(Settings.getSettings().getFileTmp()
							+ StringUtil.getUUIDString() + "." + info.getFileType());
					ControllerUtil.writeMultipartFileToFile(filePath, file);
					message = userService.importUser(filePath);
				} else {
					throw new OperationException("文件类型不正确");
				}
			} catch (OperationException e) {
				message = e.getMessage();
			}
		}
		if (StringUtil.isEmpty(message)) {
			script.refreshNode("0");
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
//			script.addAlert(message);
			script.addAlert(message);
			script.refreshNode("0");
			script.addScript("parent.refreshParentWindow();");
		}
		return script.getScript();
	}

	/**
	 * 用户xls文件下载
	 * 
	 * @return
	 */
	@RequestMapping(value = "downloadfile")
	@ResponseBody
	public FileResource downloadFile() {
		File file = new File(BaseInfo.getRealPath() + "/WEB-INF/pages/complat/user/user.xls");
		FileResource fileResource = ControllerUtil.getFileResource(file, "user.xls");

		return fileResource;
	}

	/**
	 * 组织用户操作页面其它参数
	 * 
	 * @param modelAndView
	 * @param user
	 *            用户实体
	 * @param groupId
	 *            机构ID
	 */
//	private UserFormBean buildFormBean(Integer groupId, adminRangeIdInteger userId) {
//		UserFormBean userFormBean = new UserFormBean();
//		User user;
//		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
//		Integer rangeId = 0;// 目标用户管理的机构ID
//		String rangeName = "";// 目标用户管理的机构名称
//		Integer adminRangeId = 0;// 管理员 管理的机构ID
//		String adminRangeName = "";// 管理员 管理的机构名称
//		String groupName = ""; // 目标用户所在的机构名称
//		String groupRoleIds = ""; // 目标用户所在机构对应的角色ID集 不可删除
//		String roleIds = "";// 目标用户对应的角色ID集
//		String roleNames = "";// 目标用户对应的角色名称集
//		String allRoleIds = "";// 管理员对应的角色ID集
//		String allRoleNames = "";// 管理员对应的角色名称集
//		List<Role> groupRoles = null;
//		List<Role> userRoles = null; // 目标用户对应的角色集
//		
//		if (userId == 0) {//新增
//			user = new User();
//			groupName = groupService.findNameByIid(groupId);
//			userRoles = roleService.findDefaultUserRoles(groupId);// 目标用户可获得的默认角色集
//			user.setGroupId(groupId);
//		} else {//修改
//			user = userService.findByIid(userId);
//			rangeId = groupManagerService.findRangeIdByUserId(user.getIid());// 获得目标用户 管理的机构ID
//			rangeName = groupService.findNameByIid(rangeId);
//			userRoles = roleService.findUserRoles(user.getIid());
//		}
//		
//
//		/* 获得管理员 管理的机构范围 */
//		if (currentUser.getIsSysAdmin()) {
//			adminRangeId = 0;
//			adminRangeName = "机构选择";
//		} else {
//			adminRangeId = currentUser.getRangeId();
//			if (NumberUtil.getInt(adminRangeId) == 0) {
//				adminRangeId = currentUser.getGroupId(); /* 缺省 管理当前机构 */
//			}
//			adminRangeName = groupService.findNameByIid(adminRangeId);
//		}
//
//		groupRoles = roleService.findGroupRoles(user.getGroupId()); // 目标用户所在机构的角色权限
//
//		List<Role> roleList = null;// 管理员的角色集
//		if (currentUser.getIsSysAdmin())
//			roleList = roleService.findAllRoles();
//		else {
//			roleList = roleService.findUserRoles(currentUser.getId());
//		}
//
//		List<Integer> tmpRoleIds = new ArrayList<Integer>();
//		List<String> tmpRoleNames = new ArrayList<String>();
//
//		for (Role role : roleList) {
//			tmpRoleIds.add(role.getIid());
//			tmpRoleNames.add(role.getName());
//		}
//
//		allRoleIds = StringUtil.join(tmpRoleIds, ",");
//		allRoleNames = StringUtil.join(tmpRoleNames, ",");
//
//		tmpRoleIds.clear();
//		tmpRoleNames.clear();
//
//		for (Role role : userRoles) {
//			if (NumberUtil.getInt(user.getIid()) == 0 && !currentUser.getIsSysAdmin()
//					&& role.getIid() == 1) // 机构管理员 不能删除目标用户为系统管理员的角色
//				continue;
//			tmpRoleIds.add(role.getIid());
//			tmpRoleNames.add(role.getName());
//		}
//
//		roleIds = StringUtil.join(tmpRoleIds, ",");
//		roleNames = StringUtil.join(tmpRoleNames, ",");
//
//		tmpRoleIds.clear();
//		tmpRoleNames.clear();
//
//		for (Role role : groupRoles) {
//			tmpRoleIds.add(role.getIid());
//		}
//
//		groupRoleIds = StringUtil.join(tmpRoleIds, ",");
//
//		if (NumberUtil.getInt(rangeId) == 0) {
//			rangeId = user.getGroupId();
//		}
//
//
//		// 组织树
//		Tree tree = Tree.getInstance();
//
//		tree.addNode(TreeNode.getInstance(adminRangeId + "", "menu", adminRangeName, true));
//
//		modelAndView.addObject("groupMenu", tree.parse());
//		modelAndView.addObject("rangeMenu", tree.parse());
//		modelAndView.addObject("currentUser", currentUser);
//		modelAndView.addObject("adminRangeId", adminRangeId);
//		modelAndView.addObject("groupRoleIds", groupRoleIds);
//		modelAndView.addObject("roleIds", roleIds);
//		modelAndView.addObject("roleNames", roleNames);
//		modelAndView.addObject("allRoleIds", allRoleIds);
//		modelAndView.addObject("allRoleNames", allRoleNames);
//	}
	
	/**
	 * 重置动态码密钥
	 * 
	 * @param iid
	 *            用户ID
	 * @param enable
	 *            是否有效<br>
	 *            1 有效<br>
	 *            0 失效
	 * @return
	 */
	@Permission(function = "restDynamicCode")
	@RequestMapping(value = "rest_dynamic_code")
	@ResponseBody
	public JsonResult restDynamicCode(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		if (TaskManager.isExist(RestDynamicCodeTask.TASKID)) {
			jsonResult.setSuccess(false);
			jsonResult.setMessage("线程正在运行请稍后再试");
		} else {
			RestDynamicCodeTask restDynamicCodeTask = new RestDynamicCodeTask();
			restDynamicCodeTask.addParam("ids", ids);
			TaskManager.addTask(restDynamicCodeTask);
			jsonResult.setSuccess(true);
			jsonResult.setMessage("操作已经加入后台线程");
		}
		return jsonResult;
	}
	
	@RequestMapping("show")
	public ModelAndView show(Integer iid){
		ModelAndView modelAndView = new ModelAndView("complat/user/user_show");
		User user = userService.findByIid(iid);
		modelAndView.addObject("user", user);
		return modelAndView;
	}
}
