package byit.osdp.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import byit.core.plug.expection.PromptException;
import byit.osdp.base.security.Securitys;
import byit.osdp.base.security.password.PasswordEncoder;

/** 登录登出 */
@Controller
@RequestMapping(value = "/_")
public class SecurityContextController {
	// ==============================Fields===========================================
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ==============================Methods==========================================
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 */
	// ~ /base/login.do
	@RequestMapping(value = "/login.do")
	@ResponseBody
	private Object doLogin(@RequestParam(value = "username", defaultValue = StringUtils.EMPTY) String username,
			@RequestParam(value = "password", defaultValue = StringUtils.EMPTY) String password, //
			@RequestParam(value = "encryt", defaultValue = "true") String encryt, //
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if ("true".equals(encryt)) {
				password = passwordEncoder.encode(password);//MD5
			}

			if (StringUtils.isEmpty(username)) {
				return new PromptException("用户名不能为空！");
			}
			if (StringUtils.isEmpty(password)) {
				return new PromptException("密码不能为空！");
			}

			AuthenticationToken token = new UsernamePasswordToken(username, password, false);

			Securitys.login(token);

		} catch (LockedAccountException e) {
			throw new PromptException("帐号被锁定");
		} catch (DisabledAccountException e) {
			throw new PromptException("帐号被禁用");
		} catch (ExcessiveAttemptsException e) {
			throw new PromptException("登录失败次数过多");
		} catch (ExpiredCredentialsException e) {
			throw new PromptException("凭证过期");
		} catch (AuthenticationException e) {
			throw new PromptException("用户名或密码错误");
		}
		return Boolean.TRUE;
	}

	/**
	 * 用户登出
	 */
	@RequestMapping(value = "/logout.do")
	@ResponseBody
	//~/base/logout.do
	private Object doLogout(HttpServletRequest request, HttpServletResponse response) {
		Securitys.logout();
		return Boolean.TRUE;
	}
}
