package byit.osdp.base.security;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import byit.osdp.base.common.BaseConstants;
import byit.osdp.base.security.subject.UserPrincipal;

public class UserHolder {

	/**
	 * 获得当前用户ID
	 * @return 用户ID
	 */
	public static String getId() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? null : principal.getId();
	}

	/**
	 * 获得当前用户姓名
	 * @return 用户姓名
	 */
	public static String getName() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? null : principal.getName();
	}

	/**
	 * 获得当前用户账号
	 * @return 用户账号
	 */
	public static String getUsername() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? null : principal.getName();
	}

	/**
	 * 获得当前用户机构ID
	 * @return 机构ID
	 */
	public static String getOrgId() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? null : principal.getOrgId();
	}

	/**
	 * 获得当前用户角色
	 * @return 角色ID列表
	 */
	public static String[] getRoleIds() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? ArrayUtils.EMPTY_STRING_ARRAY : principal.getRoleIds();
	}

	/**
	 * 获得当前用户权限
	 * @return 功能ID列表
	 */
	public static String[] getPermissionIds() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? ArrayUtils.EMPTY_STRING_ARRAY : principal.getPermissionIds();
	}

	/**
	 * 获得当前用户权限
	 * @return 功能编码列表
	 */
	public static String[] getPermissionCodes() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? ArrayUtils.EMPTY_STRING_ARRAY : principal.getPermissionCodes();
	}

	/**
	 * 判断当前用户是否管理员
	 * @return 如果用户是管理员则返回true，否则返回false
	 */
	public static Boolean isAdmin() {
		UserPrincipal principal = getPrincipal();
		if (principal == null) {
			return false;
		}
		if (BaseConstants.ADMIN_USER_ID.equals(principal.getId())) {
			return true;
		}
		return ArrayUtils.contains(principal.getRoleIds(), BaseConstants.ADMIN_ROLE_ID);
	}

	/**
	 * 获得用户属性
	 * @param name 属性名
	 * @return 属性值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(String name) {
		UserPrincipal principal = getPrincipal();
		return (T) (principal == null ? null : principal.getAttribute(name));
	}

	/**
	 * 获得当前登录用户信息
	 * @return 用户信息
	 */
	public static UserPrincipal getPrincipal() {
		Subject subject = getSubject();
		Object principal = subject.getPrincipal();
		if (principal instanceof UserPrincipal) {
			return (UserPrincipal) principal;
		}
		return null;
	}

	/**
	 * 验证当前用户是否有权限
	 * @param code 权限 编码
	 * @return 如果有该权限返回true，否则返回false.
	 */
	public static boolean hasPermCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return true;
		}
		String[] permissions = getPermissionCodes();
		if (permissions == null) {
			return false;
		}
		if (isAdmin()) {
			return true;
		}
		for (String permission : permissions) {
			if (permission.startsWith(code)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证当前用户是否认证通过(是否登录)
	 * @return 如果认证通过返回true，否则返回false.
	 */
	public static boolean isAuthc() {
		return getSubject().isAuthenticated();
	}

	/**
	 * 获得当前登录用户权限主体
	 * @return 权限主体
	 */
	private static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获得当前登录用户的sessionid
	 * @return 权限主体
	 */
	public static String getsessionId() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? null : principal.getSessionId();
	}
	
	/**
	 * 获得当前登录用户用于单点登录的票据
	 * @return 权限主体
	 */
	public static String getTicket() {
		UserPrincipal principal = getPrincipal();
		return principal == null ? null : principal.getTicket();
	}
}
