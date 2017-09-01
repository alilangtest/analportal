package byit.osdp.base.controller;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.common.easyui.DataGridAdapter;
import byit.osdp.base.entity.SystemDoMainEntity;
import byit.osdp.base.model.AuthRoleUserVo;
import byit.osdp.base.model.AuthRoleVo;
import byit.osdp.base.service.AuthRoleService;
import byit.osdp.base.service.SystemDoMainService;
import byit.osdp.base.util.AuthRealTypeUtil;

/**
 * 角色管理
 */
@Controller
@RequestMapping(value = "/auth_role")
public class AuthRoleController {

	// ==============================Fields===========================================
	@Autowired
	private AuthRoleService authRoleService;

	@Autowired
	private DataGridAdapter dataGridAdapter;
	
	@Autowired
	private SystemDoMainService systemDoMainService;

	// ==============================Methods==========================================
	/** 主页 */
	//~/auth_role/auth_role_main.html
	@RequestMapping(value = "/auth_role_main.html")
	public String main() {
		return "auth_role/auth_role_main";
	}

	/** 编辑页 */
	//~/auth_role/auth_role_edit.html
	@RequestMapping(value = "/auth_role_edit.html")
	public ModelAndView edit() {
		List<SystemDoMainEntity> list = systemDoMainService.getByDoMainIdList("type");
		ModelAndView map = new ModelAndView();
		map.addObject("list", list);
		map.setViewName("auth_role/auth_role_edit");
		return map;
	}
	
	/** 添加页 */
	//~/auth_role/auth_role_add.html
	@RequestMapping(value = "/auth_role_add.html")
	public ModelAndView add() {
		List<SystemDoMainEntity> list = systemDoMainService.getByDoMainIdList("type");
		ModelAndView map = new ModelAndView();
		map.addObject("list", list);
		map.setViewName("auth_role/auth_role_add");
		return map;
	}

	/** 授权页 */
	//~/auth_role/auth_role_permission_choose.html
	@RequestMapping(value = "/auth_role_permission_choose.html")
	public String permissionChoose() {
		return "auth_role/auth_role_permission_choose";
	}
	/** 关联用户页 */
	//~/auth_role/auth_role_permission_choose.html
	@RequestMapping(value = "/auth_role_user_choose.html")
	public String userChoose() {
		return "auth_role/auth_role_user_choose";
	}
	/** 查询数据列表 */
	//~/auth_role/pageQuery.do
	@RequestMapping(value = "/pagedQuery.do")
	@ResponseBody
	private Object pagedQuery() {
		AuthRealTypeUtil.map = systemDoMainService.getByDoMainId("type");
		Pagination pagination = dataGridAdapter.getPagination();
		Page<AuthRoleVo> page = authRoleService.pagedQuery(pagination);
		return dataGridAdapter.wrap(page);
	}

	/** 查看数据 */
	//~/auth_role/load.do
	@RequestMapping(value = "/load.do")
	@ResponseBody
	private Object load(String id) {
		AuthRoleVo vo = authRoleService.getById(id);
		return vo;
	}

	/** 保存数据 */
	//添加
	//~/auth_role/save.do
	@RequestMapping(value = "/save.do")
	@ResponseBody
	private Object save(AuthRoleVo vo) {
//		if (StringUtils.isEmpty(vo.getId())) {
//			authRoleService.save(vo);
//		} else {
//			authRoleService.update(vo);
//		}
		authRoleService.save(vo);
		return Boolean.TRUE;
	}
	
	/** 保存数据 */
	//修改
	//~/auth_role/save.do
	@RequestMapping(value = "/update.do")
	@ResponseBody
	private Object update(AuthRoleVo vo) {
//		if (StringUtils.isEmpty(vo.getId())) {
//			authRoleService.save(vo);
//		} else {
//			authRoleService.update(vo);
//		}
		authRoleService.update(vo);
		return Boolean.TRUE;
	}
	
	/** 验证id */
	@RequestMapping(value = "/getById.do")
	@ResponseBody
	private int getById(String id) {
		AuthRoleVo vo = authRoleService.getById(id);
		if(null!=vo){
			return 1;
		}else{
			return 0;
		}
	}

	/** 删除数据 */
	//~/auth_role/remove.do
	@RequestMapping(value = "/remove.do")
	@ResponseBody
	private Object remove(String id) {
		authRoleService.removeById(id);
		return Boolean.TRUE;
	}

	/** 查询数据 */
	//~/auth_role/findAll.do
	@RequestMapping(value = "/findAll.do")
	@ResponseBody
	private Object findAll(String type) {
		List<AuthRoleVo> records = authRoleService.findAll(type);
		return records;
	}

	/** 获得角色权限 */
	//~/auth_role/findPermissionId.do
	@RequestMapping(value = "/findPermissionId.do")
	@ResponseBody
	public Object findPermissionIdByRoleId(String id) {
		return authRoleService.findPermissionIdByRoleId(id);
	}

	/** 保存角色权限 */
	//~/auth_role/updateRolePermissions.do
	@RequestMapping(value = "/updateRolePermissions.do")
	@ResponseBody
	public Object updateRolePermissions(String roleId, String[] permissionIds) {
		authRoleService.updateRolePermissions(roleId, permissionIds == null ? ArrayUtils.EMPTY_STRING_ARRAY : permissionIds);
		return Boolean.TRUE;
	}
	
	@RequestMapping(value = "/findRoleType.do")
	@ResponseBody
	public Object findRoleType() {
		List<SystemDoMainEntity> list = systemDoMainService.getByDoMainIdList("type");
		return list;
	}
	/**
	* @Description: 根据角色id查询该角色已关联的所有用户信息 
	* @author wangxingfei   
	* @param @param roleId
	* @param @return
	* @date 2017年8月23日 下午3:08:23 
	* @version V1.0
	 */
	@RequestMapping(value = "/findRoleUserByRoleId.do")
	@ResponseBody
	private Object findRoleUserByRoleId(String roleId) {
		List<AuthRoleUserVo> records = authRoleService.findRoleUserByRoleId(roleId);
		return records;
	}
}
