package byit.osdp.base.common;

import byit.osdp.base.common.config.Configs;

/**
 * 常量类
 */
public class BaseConstants {
	/** NULL标记 */
	public static final String NULL_SYMBOL = "__NULL";
	/** 未定义ID标记 */
	public static final String UNDEFINED_ID = "__UNDEFINED";
	/** 根节点ID标记 */
	public static final String ROOT_ID = "0";
	/** 根机构节点ID */
	public static final String ORG_ROOT_ID = "0";
	/** 系统管理员的帐号ID */
	public static final String ADMIN_USER_ID = "__ADMIN_USER";
	/** 系统管理员的角色ID */
	public static final String ADMIN_ROLE_ID = "__ADMIN_ROLE";
	/** 密码占位符 */
	public static final String PASSWORD_PLACEHOLDER = "●●●●●●"; // 密码占位符号
	/** 默认密码 (111111) */
	public static final String DEFAULT_PASSWORD = Configs.getString("security.default.password", "111111");
	/** 获取默认密码 */
	public static final String GET_PASSWORD = Configs.getString("security.default.password");
}