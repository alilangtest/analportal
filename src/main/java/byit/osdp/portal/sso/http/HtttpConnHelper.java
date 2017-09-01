package byit.osdp.portal.sso.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import byit.osdp.base.entity.AuthUserEntity;
import byit.utils.JsonUtil;
import byit.utils.Tools;


public class HtttpConnHelper {
	private static final Logger logger = Logger.getLogger(HtttpConnHelper.class);
	/**
	 * 
		 *@author lisw
		 *@Description: 登录校验（包含校验用户名密码、校验票据）
		 *@creatTime:2017年5月21日 下午8:59:14 
		 *@return:@param user
		 *@return:@return
		 *@return:@throws ServletException
		 *@return:@throws IOException Map<String,String>
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> verifyLogin(AuthUserEntity user,String ticket/*,
			HttpServletRequest request, HttpServletResponse response*/)
			throws ServletException, IOException {
		Map<String,String> map = new HashMap<String,String>();
		try{
			
			map.put("username", Tools.isNotEmpty(user.getUsername())?user.getUsername():"");
			map.put("password", Tools.isNotEmpty(user.getPassword())?user.getPassword():"");
		    map.put("ticket", Tools.isNotEmpty(user.getTicket())? user.getTicket():"");
		    map.put("sessionId", Tools.isNotEmpty(user.getSessionId())? user.getSessionId():"");
		    //map.put("ip",user.getClientIp());

			String params = JsonUtil.formatMap(map);
			String loginVerifyStr=HttpPostConn.send(HttpUrl.LOGIN_VERIFY_URL, params);
	        //String loginVerifyStr = HttpClientPost.requestPost(HttpsUrl.LOGIN_VERIFY_URL, params,"utf-8"); 
	        logger.debug("loginVerifyStr="+loginVerifyStr);
	        List<String> list= JsonUtil.loginVerifySucc(loginVerifyStr);
	        String userStr=null;
	        //长度为5说明校验成功(登录、票据、ip校验均成功)
	        if(list.size()==5){
	        	userStr=list.get(4);
	        }
	        
	        ObjectMapper mapper = new ObjectMapper();
	        map=mapper.readValue(loginVerifyStr, Map.class);
	        
	        for (String key : map.keySet()) {
				if(key.equals("user")){
					map.put("user",userStr);
				}
				//log.debug("key= " + key + " and value= " + map.get(key));
			}
	    
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return map;
	}
	
	/*public static Map<String, String> verifyTicketSession(String sessionId,String ticket)
			throws ServletException, IOException {
		Map<String,String> map = new HashMap<String,String>();
		try{
			
		    map.put("sessionId",sessionId);
		    map.put("ticket",ticket);
			String params = JsonUtil.formatMap(map);
	        String loginVerifyStr = HttpsClientPost.requestPost(HttpsUrl.TICKETSESSION_VERIFY_URL, params,"utf-8"); 
	        System.out.println("loginVerifyStr="+loginVerifyStr);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return map;
	}*/
	
	/**
	 * @throws Exception 
	 * 
		 *@author lisw
		 *@Description: 退出，销毁session
		 *@creatTime:2017年5月21日 下午9:00:42 
		 *@return: void
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> destorySession(String sessionId) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		
		Map<String,String> tmap = new HashMap<String,String>();
		tmap.put("sessionId", sessionId);
		String params = JsonUtil.formatMap(tmap);
		
		String destorySessionStr =HttpPostConn.send(HttpUrl.LOGINOUT_URL, params); 
		ObjectMapper mapper = new ObjectMapper();
        map=mapper.readValue(destorySessionStr, Map.class);
        return map;
	}
	
	/**
	 * @throws Exception 
	 * 
		 *@author lisw
		 *@Description: 判断session是否存活
		 *@creatTime:2017年5月21日 下午9:17:56 
		 *@return:@return Map<String,String>
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> verifySessionAlive(String sessionId) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		
		Map<String,String> tmap = new HashMap<String,String>();
		tmap.put("sessionId", sessionId);
		String params = JsonUtil.formatMap(tmap);
		
		String isSessionAliveStr = HttpPostConn.send(HttpUrl.SESSION_VERIFY_URL, params);
		ObjectMapper mapper = new ObjectMapper();
        map=mapper.readValue(isSessionAliveStr, Map.class);
        return map;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String>  isLoginverify(String sessionId,String ticket) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> tmap = new HashMap<String,String>();
		tmap.put("sessionId", sessionId);
		tmap.put("ticket", ticket);
		String params = JsonUtil.formatMap(tmap);
		
		String isSessionAliveStr = HttpPostConn.send(HttpUrl.LOGIN_VERIFY_URL, params);
		ObjectMapper mapper = new ObjectMapper();
        map=mapper.readValue(isSessionAliveStr, Map.class);
        return map;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 获取sso客户端
		 *@creatTime:2017年8月11日 下午6:36:32 
		 *@return:@param userId,当前登录的用户Id
		 *@return:@return
		 *@return:@throws Exception Map<String,Object>
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object>  getSSOClient(String userId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> tmap = new HashMap<String,String>();
		tmap.put("userId", userId);
		String params = JsonUtil.formatMap(tmap);
		
		String clientStr = HttpPostConn.send(HttpUrl.OBTAIN_ALLCLIENT_URL, params);
		ObjectMapper mapper = new ObjectMapper();
        map=mapper.readValue(clientStr, Map.class);
        return map;
	}
}
