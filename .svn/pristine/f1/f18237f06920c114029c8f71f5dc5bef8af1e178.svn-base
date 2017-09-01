package byit.osdp.portal.sso.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byit.utils.Tools;

/**
 * 
	 * 项目名称：hfportal 
	 * 类名称：CookiesUtil   
	 * 类描述：cookies操作工具类
	 * 创建人：lisw
	 * 创建时间：2017年5月30日 下午6:27:15   
	 * 修改人：
	 * 修改时间：2017年5月30日 下午6:27:15   
	 * 修改备注：   
	 * @version
 */
public class CookiesUtil {
	static Logger logger = LoggerFactory.getLogger(CookiesUtil.class);
	/**
	 * 
		 *@author lisw
		 *@Description: 给cookies赋值
		 *@creatTime:2017年5月30日 下午6:28:00 
		 *@return: void
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static void setCookies(HttpServletRequest request,HttpServletResponse response,String tokenKey,String tokenValue){
		logger.debug(Tools.getCurrFormatTimeGen()+"：进入cookies操作类，将ticket、session放到cookies中");		
		// 将要写入的cookie项，调用者通过参数传递
		String cookieName = tokenKey;
		String cookieVal = tokenValue;
	
		// 生成cookie 
		Cookie cookie = new Cookie(cookieName, cookieVal);
		cookie.setPath("/"); //www.a.com
		// 一般可以将domain设置到顶级域
		// cookie.setDomain("sub.a.com"); 
		response.addCookie(cookie);
		logger.debug(Tools.getCurrFormatTimeGen()+"：ticket、session放到浏览器cookies成功");	
	}
	
	public  static void removeOneCookies(HttpServletRequest request,HttpServletResponse response,String tokenKey){
		Cookie cookie = new Cookie(tokenKey, null);
		cookie.setMaxAge(0);//设置cookie的过期时间
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public  static void removeAllCookies(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies=request.getCookies();
		for(Cookie cookie:cookies){
			removeOneCookies(request,response,cookie.getName());
		}
	}

}
