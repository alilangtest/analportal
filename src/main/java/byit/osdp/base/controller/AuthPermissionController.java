package byit.osdp.base.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import byit.osdp.base.entity.AuthPermissionEntity;
import byit.osdp.base.model.AuthPermissionNodeVo;
import byit.osdp.base.model.AuthPermissionVo;
import byit.osdp.base.security.UserHolder;
import byit.osdp.base.service.AuthPermissionService;

/**
 * 菜单功能
 */
@Controller
@RequestMapping(value = "/auth_permission")
public class AuthPermissionController {
	// ==============================Fields===========================================
	@Autowired
	private AuthPermissionService authPermissionService;

	// ==============================Methods==========================================

	/** 菜单功能主页 */
	//~/auth_permission/auth_permission_main.html
	@RequestMapping(value = "/auth_permission_main.html")
	public String main() {
		return "auth_permission/auth_permission_main";
	}

	/** 菜单功能编辑 */
	//~/auth_permission/auth_permission_edit.html
	@RequestMapping(value = "/auth_permission_edit.html")
	public String edit() {
		return "auth_permission/auth_permission_edit";
	}

	/** 获得功能树 */
	// ~/auth_permission/loadTree.do
	@RequestMapping(value = "/loadTree.do")
	@ResponseBody
	public Object loadTree() {
		return authPermissionService.getTree();
	}

	/** 后台菜单树 */
	//~/auth_permission/loadMenuTreeByPermCode.do
	@RequestMapping(value = "/loadMenuTreeByPermCode.do")
	@ResponseBody
	public Object loadMenuTreeByPermCode(String code) {

		String rootId = authPermissionService.getIdByCode(code);

		if (StringUtils.isEmpty(rootId)) {
			return Lists.newArrayList();
		}

		if (UserHolder.isAdmin()) {
			List<AuthPermissionNodeVo> nodes = authPermissionService.getTreeMenuByRootId(rootId);
			return nodes;
		} else {
			String[] permissionIds = UserHolder.getPermissionIds();
			List<AuthPermissionNodeVo> nodes = authPermissionService.getTreeMenuByRootId(code, permissionIds);
			return nodes;
		}
	}

	/** 获得功能数据 */
	// ~/auth_permission/load.do
	@RequestMapping(value = "/load.do")
	@ResponseBody
	public Object load(String id) {
		return authPermissionService.getById(id);
	}

	/** 保存(更新)功能数据 */
	//~/auth_permission/save.do
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public synchronized Object save(AuthPermissionVo vo) {
		if (StringUtils.isEmpty(vo.getId())) {
			authPermissionService.save(vo);
		} else {
			authPermissionService.update(vo);
		}
		return Boolean.TRUE;
	}

	/** 删除功能数据 */
	//~/auth_permission/remove.do
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(String id) {
		authPermissionService.removeById(id);
		return Boolean.TRUE;
	}

	/** 强制刷新索引(级别和ID路径) */
	//~/auth_permission/forceRefreshIndexes.do
	@RequestMapping(value = "/forceRefreshIndexes.do")
	@ResponseBody
	public Object forceRefreshIndexes() {
		List<AuthPermissionEntity> list = authPermissionService.forceRefreshIndexes();
		authPermissionService.updateAuthRolePermissionByIdPath(list);
		return Boolean.TRUE;
	}
}
