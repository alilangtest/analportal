package byit.osdp.portal.sso.http;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import byit.aladdin.dataIndex.util.ByitConfig;

public class HttpUrl {
	/**sso服务端url地址*/
	//sso服务端地址
	public static String SSO_URL;
	
	//验证session是否存在
	public static String SESSION_VERIFY_URL;
	
	//登出
	public static String LOGINOUT_URL;
	
	//登录校验用户名密码是否正确
	public static String LOGIN_VERIFY_URL;
	
	//获取其他需要单点登录客户端
	public static String OBTAIN_ALLCLIENT_URL;
	
	//跳转到登录页面
	//public static String LOGIN__URL="https://localhost:8080/sso_server/setCookie";
	
	public static final String status = "status";
	static {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			properties.load(input = ByitConfig.class.getResourceAsStream("/byit.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}
		SSO_URL=properties.getProperty("sso.url");
		SESSION_VERIFY_URL = properties.getProperty("sso.session.verify.url");
		LOGINOUT_URL = properties.getProperty("sso.loginout.url");
		LOGIN_VERIFY_URL = properties.getProperty("sso.login.verify.url");
		OBTAIN_ALLCLIENT_URL = properties.getProperty("sso.obtaim.allclient.url");
	}
}
