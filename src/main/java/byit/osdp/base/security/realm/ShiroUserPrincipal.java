package byit.osdp.base.security.realm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import byit.osdp.base.security.subject.UserPrincipal;

public class ShiroUserPrincipal implements UserPrincipal {
	// ==============================Fields===========================================
	private String id;//用户名
	private String name;//员工编号
	private String orgId; //所属机构
	private String sessionId;//sessionid
	private String ticket;//单点登录用户票据
	private String[] roleIds = new String[0]; //所属角色
	private String[] permissionIds = new String[0];// 拥有权限(ID)
	private String[] permissionCodes = new String[0];// 拥有权限(编码)
	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

	// ==============================Constructors=====================================
	public ShiroUserPrincipal() {
	}

	// ==============================Methods==========================================
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	public String[] getPermissionCodes() {
		return permissionCodes;
	}

	public void setPermissionCodes(String[] permissionCodes) {
		this.permissionCodes = permissionCodes;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String name) {
		return (T) attributes.get(name);
	}

	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
