package byit.osdp.base.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import byit.osdp.base.common.BaseConstants;
import byit.osdp.base.model.AuthOrgVo;
import byit.osdp.base.service.AuthOrgService;

/**
 * 组织机构
 */
@Controller
@RequestMapping(value = "/auth_org")
public class AuthOrgController {
	// ==============================Fields===========================================
	@Autowired
	private AuthOrgService authOrgService;

	// ==============================Methods==========================================
	/** 部门主页 */
	//~/auth_org/auth_org_main.html
	@RequestMapping(value = "/auth_org_main.html")
	public String main() {
		return "auth_org/auth_org_main";
	}

	/** 部门编辑 */
	//~/auth_org/auth_org_edit.html
	@RequestMapping(value = "/auth_org_edit.html")
	public String edit() {
		return "auth_org/auth_org_edit";
	}

	/** 机构选择 */
	@RequestMapping(value = "/auth_org_select.html")
	public String select() {
		return "auth_org/auth_org_select";
	}

	/** 上级机构选择 */
	@RequestMapping(value = "/auth_org_parent_select.html")
	public String parentSelect() {
		return "auth_org/auth_org_parent_select";
	}

	/** 加载机构树(异步加载) */
	//~/auth_org/asyncTree.do
	@RequestMapping(value = "/asyncTree.do")
	@ResponseBody
	public Object asyncTree(String id) {
		String parentId = StringUtils.defaultString(id, BaseConstants.ORG_ROOT_ID);
		return authOrgService.findChildrenNode(parentId);
	}

	/** 加载机构树 */
	//~/auth_org/loadTree.do
	@RequestMapping(value = "/loadTree.do")
	@ResponseBody
	public Object loadTree() {
		return authOrgService.getOrgTree();
	}

	/** 获得机构数据 */
	// ~/auth_org/load.do
	@RequestMapping(value = "/load.do")
	@ResponseBody
	public Object load(String id) {
		return authOrgService.getById(id);
	}

	/** 保存(更新)机构数据 */
	//~/auth_org/save.do
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public synchronized Object save(AuthOrgVo vo) {
		if (StringUtils.isEmpty(vo.getId())) {
			authOrgService.save(vo);
		} else {
			authOrgService.update(vo);
		}
		return Boolean.TRUE;
	}

	/** 删除机构数据 */
	//~/auth_org/remove.do
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	public Object remove(String id) {
		authOrgService.removeById(id);
		return Boolean.TRUE;
	}

	/** 强制刷新组织机构树索引(级别和ID路径) */
	//~/auth_org/forceRefreshIndexes.do
	@RequestMapping(value = "/forceRefreshIndexes.do")
	@ResponseBody
	public Object forceRefreshIndexes() {
		authOrgService.forceRefreshIndexes();
		return Boolean.TRUE;
	}
}
