package byit.osdp.base.security.subject;

/**
 * 用户认证对象，携带登录用户的信息.
 */
public interface UserPrincipal {

	/**
	 * 获得用户ID
	 * @return 用户ID
	 */
	public String getId();
	
	/**
	 * 获得用户姓名
	 * @return 用户姓名
	 */
	public String getName();

	/**
	 * 获得用户所属机构ID
	 * @return 机构ID
	 */
	public String getOrgId();

	/**
	 * 获得用户所属角色ID列表
	 * @return 角色ID列表
	 */
	public String[] getRoleIds();

	/**
	 * 获得用户拥有权限ID列表
	 * @return 权限ID列表
	 */
	public String[] getPermissionIds();

	/**
	 * 获得用户拥有权限编码列表
	 * @return 权限编码列表
	 */
	public String[] getPermissionCodes();

	/**
	 * 获得用户属性
	 * @param name 属性名
	 * @return 权限ID列表
	 */
	public <T> T getAttribute(String name);

	/**
	 * 设置用户属性
	 * @param name 属性名
	 * @param value 属性值
	 */
	public <T> void setAttribute(String name, T value);
	
	/**
	 * 获得用户sessionID
	 * @return sessionID
	 */
	public String getSessionId();
	
	
	/**
	 * 获得用户单点登录的票据
	 * @return sessionID
	 */
	public String getTicket();

}
