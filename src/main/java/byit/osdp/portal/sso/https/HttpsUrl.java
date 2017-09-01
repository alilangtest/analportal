package byit.osdp.portal.sso.https;

public class HttpsUrl {
	/**sso服务端url地址*/
	//验证session是否存在
	public static String SESSION_VERIFY_URL="https://localhost:8444/dcsso/sessionVerify";
	
	//登出
	public static String LOGINOUT_URL="https://localhost:8444/dcsso/loginOutVerify";
	
	//登录校验用户名密码是否正确
	public static String LOGIN_VERIFY_URL="https://localhost:8444/dcsso/loginVerify";
	
	//跳转到登录页面
	//public static String LOGIN__URL="https://localhost:8444/sso_server/setCookie";
	
	public static final String status ="status";

}
