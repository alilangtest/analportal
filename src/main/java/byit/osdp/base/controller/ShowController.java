package byit.osdp.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import byit.osdp.base.entity.ShowEntity;
import byit.osdp.base.model.PasswordUpdateDto;
import byit.osdp.base.security.UserHolder;
import byit.osdp.base.service.ShowService;

/**
 * 前台下拉框显示（不同角色显示不同菜单）
 * @author Mr.tang
 */
@Controller
@RequestMapping(value = "/show")
public class ShowController {
	
	@Autowired
	private ShowService showService;
	
	/** 获得功能数据 */
	// ~/auth_permission/load.do
	@RequestMapping(value = "/load.do")
	@ResponseBody
	public Object load() {
		//获取当前登录用户
		String loginid=UserHolder.getId();
		List<ShowEntity> list = showService.queryUserById(loginid);
		return list;
	}
	
	/**
	 * 验证是否为初始密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkPassword.do")
	public Object checkPassword(){
		Boolean flag = showService.checkPassword();
		if(flag){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@RequestMapping(value = "/checkPasswordHtml.html")
	public Object checkPasswordHtml(){
		return "auth_user/checkPasswordHtml";
	}
	
	/** 修改当前用户密码 */
	//~/auth_user/updatePassword.do
	@RequestMapping(value = "/updatePassword.do")
	@ResponseBody
	public Object updatePassword(String newPassword) {
		PasswordUpdateDto dto = new PasswordUpdateDto();
		String id = UserHolder.getId();
		dto.setUserId(id);
//		dto.setOldPassword(oldPassword);
		dto.setNewPassword(newPassword);
		showService.updatePassword(dto);
		return Boolean.TRUE;
	}
}
