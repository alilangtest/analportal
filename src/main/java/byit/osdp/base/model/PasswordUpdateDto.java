package byit.osdp.base.model;

import java.io.Serializable;

/**
 * 密码修改
 */
@SuppressWarnings("serial")
public class PasswordUpdateDto implements Serializable {
	// ==============================Fields========================================
	/** 用户ID */
	private String userId;
	/** 当前登录密码 */
	private String oldPassword;
	/** 新的登录密码 */
	private String newPassword;

	// ==============================PropertyAccessors================================
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
