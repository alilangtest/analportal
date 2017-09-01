package byit.osdp.base.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证是否在有效期内
 * @author Mr.tang
 */
@SuppressWarnings("serial")
public class UserValidAuthcException extends AuthenticationException {
	public UserValidAuthcException() {
	}

	public UserValidAuthcException(String message) {
		super(message);
	}

	public UserValidAuthcException(Throwable cause) {
		super(cause);
	}

	public UserValidAuthcException(String message, Throwable cause) {
		super(message, cause);
	}
}
