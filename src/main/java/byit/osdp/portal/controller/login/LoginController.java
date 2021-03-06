package byit.osdp.portal.controller.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import byit.aladdin.dataAnalysis.utils.Constant;
import byit.core.plug.expection.PromptException;
import byit.osdp.base.security.UserHolder;
import byit.osdp.base.security.exception.UserDisabledAuthcException;
import byit.osdp.base.security.exception.UserNoRoleAuthcException;
import byit.osdp.base.security.exception.UserValidAuthcException;
import byit.osdp.base.security.exception.UserVerifyException;
import byit.osdp.base.security.password.PasswordEncoder;
import byit.osdp.base.service.UseSSOService;
import byit.osdp.portal.sso.Contants;
import byit.osdp.portal.sso.Utils;
import byit.osdp.portal.sso.cookies.CookiesUtil;
import byit.osdp.portal.sso.http.HttpUrl;
import byit.osdp.portal.sso.http.HtttpConnHelper;
import byit.utils.Tools;

/**
 * 用户登录/注销 视图类
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

	// ==============================Fields===========================================
	Logger logger=Logger.getLogger(LoginController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UseSSOService useSSOService;

	// ==============================Methods==========================================
	/**
	 * 
		 *@author lisw
		 *@Description: sso服务端转向该方法，获取票据和sessionId，并将票据、sessionId写入浏览器
		 *@creatTime:2017年8月10日 下午7:28:05 
		 *@return:@param request
		 *@return:@return ModelAndView
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@RequestMapping(value = "/sso.html")
	private ModelAndView sso(HttpServletRequest request,HttpServletResponse response){
		logger.info(this.getClass()+":进入sso.html");
		ModelAndView mv=new ModelAndView();
		String ticket=request.getParameter("ticket");
		String sessionId=request.getParameter("sessionId");
		if((Tools.isNotEmpty(ticket))&&(Tools.isNotEmpty(sessionId))){
			mv.addObject(Contants.TICKET_KEY,ticket);
			mv.addObject(Contants.SESSIONID_KEY,sessionId);
			mv.setViewName("login/ssologin");
		}else{//如果没有sessionid、ticket。跳转到登录页面
			mv.addObject("ssoUrl",HttpUrl.SSO_URL);
			mv.setViewName("login/tosso");
		}
		return mv;
	}
	
	/** 登录页面 
	 * @throws Exception 
	 * @throws IOException 
	 * @throws ServletException */
	// ~ /login.html
	@RequestMapping(value = "/index.html")
	private ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info(Tools.getCurrFormatTimeGen()+":进入index.html");
		//查询数据库是否启用单点登录
		String isEnable=useSSOService.findSystemDomain(Contants.DOMAINID, Contants.CODEID);
		ModelAndView mv=new ModelAndView();
		if(Contants.ISENABLE.equals(isEnable)){
			logger.info(Tools.getCurrFormatTimeGen()+":启用单点登录");
			//启用单点登录系统
			//String loginid=UserHolder.getId();//获取登录用户的Id
			Map<String, Object> map =new HashMap<String, Object>();
			List<Map<String,String>> clientList=Utils.getClients(map,"");
			List<String> urlList=Utils.getClientUrl(clientList);
			logger.info(Tools.getCurrFormatTimeGen()+":查询出sso客户端为"+urlList.size()+"个");
			if(urlList.size()>0){
				mv.addObject("urlList", urlList);
				mv.setViewName("login/ssoindex");//TODO 生产环境使用
				//mv.setViewName("forward:/cookies/read");
				logger.info(Tools.getCurrFormatTimeGen()+":进入login/ssoindex页面");
			}else{
				logger.info(Tools.getCurrFormatTimeGen()+":无sso客户端，不使用sso登录，跳转到login/index页面");
				mv.setViewName("login/index");
			}
			
			return mv;
		}else{
			//不启用单点登录系统，直接从当前数据库查询数据根据用户名称(登录名)，查询用户信息
			logger.info(Tools.getCurrFormatTimeGen()+":进入login/index页面");
			mv.setViewName("login/index");
			return mv;
		}
	}
	
	//TODO SSO使用的代码。
	/*@RequestMapping(value = "/index.html")
	private ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=new ModelAndView();
		Map<String, Object> map = HtttpConnHelper.getSSOClient();
		
		List<Map<String,String>> clientList=Utils.getClients(map);
		List<String> urlList=Utils.getClientUrl(clientList);
		mv.addObject("urlList", urlList);
		mv.setViewName("login/ssoindex");//TODO 生产环境使用
		return mv;
	}*/

	/**跳转到登录页*/
	@RequestMapping(value = "/toIndex.html")
	private ModelAndView loginToIndex() {
		ModelAndView mv=new ModelAndView();
		logger.info(Tools.getCurrFormatTimeGen()+":进入toIndex.html！，页面再次跳转到login/index.jsp");
		mv.addObject("ssoUrl",HttpUrl.SSO_URL);
		mv.setViewName("login/tosso");
		return mv;
	}
	
	
	
	/** 登录页面 */
	// ~ /login.html
	@RequestMapping(value = "/login.html")
	private ModelAndView login(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		logger.info(Tools.getCurrFormatTimeGen()+":进入login.html，登录核心，这里判断进入登录页面还是进入首页！");
		//查询数据库是否启用单点登录
		String isEnable=useSSOService.findSystemDomain(Contants.DOMAINID, Contants.CODEID);
		if(Contants.ISENABLE.equals(isEnable)){
			logger.info(Tools.getCurrFormatTimeGen()+":启用单点登录");
			//启用单点登录
			String loginid=UserHolder.getId();//获取登录用户的Id
			String loginAfterPage="admin/index_upAdown2";//登录后进入的页面(首页)
			String loginFailPage="login/tosso";//未登录跳转的页面（登录页面）
			String retPage=loginFailPage;//默认进入登录页面
			if(loginid != null && !loginid.equals("")){
				
				//获取请求的url全路径
				String url = request.getRequestURL().toString();
				Map<String, Object> tmap =new HashMap<String, Object>();
				
				//获取该用户拥有访问权限的系统url
				List<Map<String,String>> clientList=Utils.getClients(tmap,loginid);
				List<String> urlList=Utils.getClientUrl(clientList);
				
				boolean hasSSOwebAuth=false;//是否拥有访问系统的权限
				
				/*
				 *循环该用户有权限的url，如果某个系统有权限，将hasSSOwebAuth设为true，同时停止循环，否则一直循环到结束
				 *如果到结束没有找到该用户请求的url全路径和拥有的权限域名匹配，页面跳转到sso登录页面。
				 */
				for (int i = 0; i < urlList.size(); i++) {
					hasSSOwebAuth=urlList.get(i).contains(Utils.getDomainUrl(url));
					if(hasSSOwebAuth){
						break;
					}
				}
				if(hasSSOwebAuth){//如果有访问某个系统权限
					logger.info(Tools.getCurrFormatTimeGen()+":有当前登录的用户id，用户id为"+loginid);
					String _sessionId=UserHolder.getsessionId();//如果有用户的id，获取用户的sessionId
					Map<String,String> map =new HashMap<String, String>();
					/*
					 * 如果有sessionId，
					 * 进入sso服务端，查看服务端是否有sessionId，
					 * 如果服务端有该session说明还在登录状态(is_session_exist=true)，进入登录后的页面，只有在这一种情况下才会进入登录后的页面
					 */
					if(Tools.isNotEmpty(_sessionId)){
						logger.info(Tools.getCurrFormatTimeGen()+":有当前用户登录的sessionId:"+_sessionId);
						try {
							logger.info(Tools.getCurrFormatTimeGen()+":判断sso服务端是否有session");
							map =HtttpConnHelper.verifySessionAlive(_sessionId);
							for (String key : map.keySet()) {
								logger.info(Tools.getCurrFormatTimeGen()+":key="+key+",value="+map.get(key));
								//判断是否is_session_exist=true
								if((Contants.IS_SESSION_EXIST_KEY.equals(key)) && (Contants.TRUE).equals(map.get(key))){
									retPage=loginAfterPage;
									mv.setViewName(retPage);
									logger.info(Tools.getCurrFormatTimeGen()+":sso服务端有session,将会进入"+retPage+"页面");
									break;
								}else if((Contants.IS_SESSION_EXIST_KEY.equals(key)) && (Contants.FALSE).equals(map.get(key))){
									//retPage=loginAfterPage;
									mv.setViewName(retPage);
									logger.info(Tools.getCurrFormatTimeGen()+":sso服务端无session,将会进入"+retPage+"页面");
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else{
					mv.setViewName(loginFailPage);
				}
				
			}else{
				mv.setViewName(retPage);
			}
		
			mv.addObject("ssoUrl",HttpUrl.SSO_URL);
			logger.info(Tools.getCurrFormatTimeGen()+":页面跳转到"+retPage);
			return mv;
		}else{
			logger.info(Tools.getCurrFormatTimeGen()+":不启用单点登录");
			//不启用单点登录
			String loginid=UserHolder.getId();
			if(loginid != null && !loginid.equals("")){
				logger.info(Tools.getCurrFormatTimeGen()+":无用户id，进入admin/index_upAdown2页面");
				mv.setViewName("admin/index_upAdown2");
				return mv;
			}
			mv.setViewName("login/index");
			return mv;
		}
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 依据登录用户id查询sso服务端是否有该用户的session信息，如果有进入登录后的首页，如果没有进入登录页面
		 *@creatTime:2017年6月17日 下午11:50:05 
		 *@return:@return String
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	/*private String login() {
		String loginid=UserHolder.getId();//获取登录用户的Id
		String loginAfterPage="admin/index_upAdown2";//登录后进入的页面(首页)
		String loginFailPage="login/index";//未登录跳转的页面（登录页面）
		String retPage=loginFailPage;//默认进入登录页面
		if(loginid != null && !loginid.equals("")){
			String _sessionId=UserHolder.getsessionId();//如果有用户的id，获取用户的sessionId
			Map<String,String> map =new HashMap<String, String>();
			
			 * 如果有sessionId，
			 * 进入sso服务端，查看服务端是否有sessionId，
			 * 如果服务端有该session说明还在登录状态(is_session_exist=true)，进入登录后的页面，只有在这一种情况下才会进入登录后的页面
			 
			if(Tools.isNotEmpty(_sessionId)){
				try {
					map =HtttpConnHelper.verifySessionAlive(_sessionId);
					for (String key : map.keySet()) {
						//判断是否is_session_exist=true
						if((Contants.IS_SESSION_EXIST_KEY.equals(key)) && (Contants.TRUE).equals(map.get(key))){
							retPage=loginAfterPage;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return retPage;
	}*/

	/** 用户登出 
	 * @throws Exception */
	// ~ /logout.html
	@RequestMapping(value = "/logout.html")
	private String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询数据库是否启用单点登录
		String isEnable=useSSOService.findSystemDomain(Contants.DOMAINID, Contants.CODEID);
		logger.info(Tools.getCurrFormatTimeGen()+":启用单点登录");
		if(Contants.ISENABLE.equals(isEnable)){
			logger.info(Tools.getCurrFormatTimeGen()+":启用单点登录");
			//启用单点登录
			String sessionid = UserHolder.getsessionId();
			//调用sso服务端，判断服务端session是否清空，如果服务端session清空，则清空本系统session。
			Map<String,String> map =HtttpConnHelper.destorySession(sessionid);
			String destoryResult=null;
			for (String key : map.keySet()) {
				if("removeSession".equals(key)){
					destoryResult=map.get(key);
					logger.info(Tools.getCurrFormatTimeGen()+":服务端session是否清空："+destoryResult);
				}
			}
	        //销毁客户端session
			if("true".equals(destoryResult)){
				logger.info(Tools.getCurrFormatTimeGen()+":服务端session已清空，执行退出方法");
				//清除本地session
				SecurityUtils.getSubject().logout();
			}
			//情况浏览器中homePage的cookie
			CookiesUtil.removeOneCookies(null, response, Constant.HOMEPAGECOOKIE);//清除homePage的cookies
			CookiesUtil.removeAllCookies(request, response);
			logger.info(Tools.getCurrFormatTimeGen()+":清空浏览器中的cookies!，页面跳转到：login/logoutSSO.jsp");
			logger.info(Tools.getCurrFormatTimeGen()+":logoutSSO.jsp页面会直接跳转到toIndex.html");
			return "login/logoutSSO";
		}else{
			//不启用单点登录
			//置空首页cookie
			CookiesUtil.removeOneCookies(null, response, Constant.HOMEPAGECOOKIE);//清除homePage的cookies
			SecurityUtils.getSubject().logout();
			return "login/logout";
		}
	}
	//TODO
	//SSO使用如下代码
	/*private String Logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String sessionid = UserHolder.getsessionId();
		//调用sso服务端，判断服务端session是否清空，如果服务端session清空，则清空本系统session。
		Map<String,String> map =HtttpConnHelper.destorySession(sessionid);
		String destoryResult=null;
		for (String key : map.keySet()) {
			if("removeSession".equals(key)){
				destoryResult=map.get(key);
			}
		}

        //销毁客户端session
		if("true".equals(destoryResult)){
			//清除本地session
			SecurityUtils.getSubject().logout();
		}
		//情况浏览器中homePage的cookie
		CookiesUtil.removeOneCookies(null, response, Constant.HOMEPAGECOOKIE);//清除homePage的cookies
		CookiesUtil.removeAllCookies(request, response);
		return "login/logoutSSO";
	}*/

	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 */
	// ~ /login.do
	@RequestMapping(value = "/login.do")
	@ResponseBody
	private Object doLogin(@RequestParam(value = "username", defaultValue = StringUtils.EMPTY) String username,
			@RequestParam(value = "password", defaultValue = StringUtils.EMPTY) String password, //
			@RequestParam(value = "sessionId", defaultValue = StringUtils.EMPTY) String sessionId,
			@RequestParam(value = "ticket", defaultValue = StringUtils.EMPTY) String ticket,
			@RequestParam(value = "encryt", defaultValue = "true") String encryt, //
			HttpServletRequest request, HttpServletResponse response) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try {
			logger.info(Tools.getCurrFormatTimeGen()+"：进入login.do");
			String host=null;
			if((!Tools.isNotEmpty(ticket))&&(!Tools.isNotEmpty(sessionId))){
				if ("true".equals(encryt)) {
					password = passwordEncoder.encode(password);//MD5
				}
				if (StringUtils.isEmpty(username)) {
					return new PromptException("用户名不能为空！");
				}
				if (StringUtils.isEmpty(password)) {
					return new PromptException("密码不能为空！");
				}
			}else{
				host=ticket+Contants.DELIMITER+sessionId;
			}
			
			
			//AuthenticationToken token = new UsernamePasswordToken(username, password, false);
			//host按照api要求传递的应该是本机地址(host)，在这里将票据(ticket)和sso服务端的sessionId拼接赋值
			AuthenticationToken token = new UsernamePasswordToken(username, password, false,host);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);

			
			
			if (!currentUser.isAuthenticated()) {
				map.put("success", false);
				map.put("msg", "用户名或密码错误");
			}
			map.put("success", true);
			//向浏览器的cookie赋值，cookie分别是ticket和sessionId
			logger.info(Tools.getCurrFormatTimeGen()+"：用户查询成功，准备将ticket、session放到cookies中");
			CookiesUtil.setCookies(request, response, Contants.TICKET_KEY,UserHolder.getTicket());
			CookiesUtil.setCookies(request, response, Contants.SESSIONID_KEY,UserHolder.getsessionId());
		} catch(UserValidAuthcException e){
			map.put("success", false);
			map.put("msg", "用户已过有效期，请联系管理员");
		} catch(UserNoRoleAuthcException e){
			map.put("success", false);
			map.put("msg", "该用户还未分配角色");
		} catch (UserDisabledAuthcException e) {
			map.put("success", false);
			map.put("msg", "该用户已经被禁用");
		} catch (UserVerifyException e1){//该用户从sso服务端获取到的user为空，但是有ticket和sessionId等，说明浏览器的票据等是错误的
			map.put("success", false);
			map.put(Contants.ERR_TICKET_KEY, true);
		}catch (AuthenticationException e) {
			map.put("success", false);
			map.put("msg", "用户名或密码错误");
		}
		
		return map;
	}

	/**
	 * 用户登出
	 * @throws Exception 
	 */
	@RequestMapping(value = "/logout.do")
	@ResponseBody
	private Object doLogout(HttpServletRequest request, HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		return Boolean.TRUE;
	}

}
