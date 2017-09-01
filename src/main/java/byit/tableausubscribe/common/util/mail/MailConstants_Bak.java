package byit.tableausubscribe.common.util.mail;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import byit.aladdin.dataIndex.util.ByitConfig;

public class MailConstants_Bak {
	// 邮箱登陆账号
	//public static final String USERNAME = "yidongjinrongbu@hfbank.com.cn";
	public static final String USERNAME;
	
	// 邮箱登陆密码
	//public static final String PASSWORD = "yidong2016";//2016-11-15
	public static final String PASSWORD;//2016-11-15
	
	// 邮箱地址
	//public static final String MAILADDRESS = "yidongjinrongbu@hfbank.com.cn";
	public static final String MAILADDRESS;
	
	// 邮箱服务器发送地址
	//public static final String MAILHOST = "mail.hfbank.com.cn";
	public static final String MAILHOST;
	// 邮箱服务器端口
	public static final String MAILPORT;
	// 邮箱服务器状态
	public static final boolean VALIDATE;
	
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
		USERNAME = properties.getProperty("email.username");
		PASSWORD = properties.getProperty("email.password");
		MAILADDRESS = properties.getProperty("email.mailaddress");
		MAILHOST = properties.getProperty("email.mailhost");
		MAILPORT = properties.getProperty("email.mailport");
		VALIDATE = Boolean.parseBoolean(properties.getProperty("email.validate"));
	}
}
