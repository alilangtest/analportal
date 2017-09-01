package byit.utils;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
* @Description: mail用户验证类
* @author wangxingfei   
* @date 2017年5月24日 上午11:32:55 
* @version V1.0
 */
public class MyAuthenticator extends Authenticator {
	String userName=null;  
	String password=null;  
	   
	public MyAuthenticator(){  
	}  
	public MyAuthenticator(String username, String password) {   
	    this.userName = username;   
	    this.password = password;   
	}   
	protected PasswordAuthentication getPasswordAuthentication(){  
	    return new PasswordAuthentication(userName, password);  
	}  
}
