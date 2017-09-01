package byit.osdp.base.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 校验用户是否票据是否通过
 */
@SuppressWarnings("serial")
public class UserVerifyException extends AuthenticationException {
	public UserVerifyException() {
	}

	public UserVerifyException(String message) {
		super(message);
	}

	public UserVerifyException(Throwable cause) {
		super(cause);
	}

	public UserVerifyException(String message, Throwable cause) {
		super(message, cause);
	}
}
