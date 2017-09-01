package byit.osdp.portal.sso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.fasterxml.jackson.databind.ObjectMapper;

import byit.osdp.base.entity.AuthUserEntity;
import byit.osdp.portal.sso.http.HtttpConnHelper;
import byit.utils.Tools;

/**
 * 
 * 项目名称：hfportal_05 类名称：Utils 类描述： 单点登录工具类 创建人：lisw 创建时间：2017年5月30日 下午8:20:11
 * 修改人： 修改时间：2017年5月30日 下午8:20:11 修改备注：
 * 
 * @version
 */
public class Utils {
	private static final Logger logger = Logger.getLogger(Utils.class);
	/**
	 * 
	 * @author lisw
	 * @Description:校验登录
	 * @creatTime:2017年5月30日 下午8:20:40
	 * @return:@return AuthUserEntity
	 * @modifier:
	 * @modifTime:
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static AuthUserEntity verifyLogin(UsernamePasswordToken token) {
		// TODO sso使用，切换到生产需使用
		AuthUserEntity _user=new AuthUserEntity();
		AuthUserEntity user=null;
		try {

			_user.setUsername(token.getUsername());
			_user.setPassword(String.valueOf(token.getPassword()));
			String host=token.getHost();
			if(host!=null){
				String tTicket=host.substring(0, host.indexOf(Contants.DELIMITER));
				String tSessionId=host.substring(host.indexOf(Contants.DELIMITER)+3);
				_user.setTicket(tTicket);
				_user.setSessionId(tSessionId);
			}
			
			//HttpPostUrl.send(InitServlet.companylistUrl, params); 
			
			Map<String, String> map = HtttpConnHelper.verifyLogin(_user, "");
			String userStr = null;
			String _ticket = null;
			for (String key : map.keySet()) {
				if (Contants.USER.equals(key)) {
					userStr = map.get(key);
					ObjectMapper mapper = new ObjectMapper();
					map = mapper.readValue(userStr, Map.class);
					user = Tools.convertMapToUser(map);
					user.setTicket(_ticket);
				}
				if (Contants.TICKET_KEY.equals(key)) {
					_ticket = map.get(key);
				}
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/*public static Object getValue(String param) {
        Map map = new HashMap();
        String str = "";
        String key = "";
        Object value = "";
        char[] charList = param.toCharArray();
        boolean valueBegin = false;
        for (int i = 0; i < charList.length; i++) {
            char c = charList[i];
            if (c == '{') {
                if (valueBegin == true) {
                    value = getValue(param.substring(i, param.length()));
                    i = param.indexOf('}', i) + 1;
                    map.put(key, value);
                }
            } else if (c == '=') {
                valueBegin = true;
                key = str;
                str = "";
            } else if (c == ',') {
                valueBegin = false;
                value = str;
                str = "";
                map.put(key, value);
            } else if (c == '}') {
                if (str != "") {
                    value = str;
                }
                map.put(key, value);
                return map;
            } else if (c != ' ') {
                str += c;
            }
        }
        return map;
    }*/
	
    @SuppressWarnings("unused")
	public static String getList(String param){
    	 String str = "";
    	 String retStr="";
    	char[] charList = param.toCharArray();
    	for (int i = 0; i < charList.length; i++) {
    		 char c = charList[i];
    		 if (c == '[') {
            	 str="";
             }else if (c == ']') {
                 str="";
             }else if (c == '{') {
            	 retStr =retStr+"";
             }else if (c == '}') {
            	 retStr=retStr+"";
             }else if (c != ' '){
            	 retStr= retStr+c;
             }
		}
    	return retStr;
    }
    /**
     * 
    	 *@author lisw
    	 *@Description: 从map中获取clients的值。用于单点登录返回值中获取客户端实体类
    	 *@creatTime:2017年8月11日 下午6:35:00 
    	 *@return:@param map
    	 *@return:@param userId：当前登录的用户Id(如果有的话)
    	 *@return:@return List<Map<String,String>>
    	 *@modifier:
    	 *@modifTime:
    	 *@throws
     */
    @SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public static List<Map<String,String>> getClients(Map<String, Object> map,String userId){
    	Map<String, Object> _map=new HashMap<String, Object>();
    	try {
			 map= HtttpConnHelper.getSSOClient(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isRet=false;
		List<Map<String,String>> clients=new ArrayList<Map<String,String>>();
		for(String key : map.keySet()){
			if((Contants.RET_KEY.equals(key))&&(Contants.RET_SUCC_VALUE.equals(map.get(key)))){//如果ret=0说明从sso服务端获取数据成功
				isRet=true;
			}
			if(isRet && (Contants.CLIENT_KEY.equals(key))){
				clients=(List)map.get(key);
			}
		}
		return clients;
    }
    /**
     * 
    	 *@author lisw
    	 *@Description: 通过clients获取其中的clientUrl值，并将值以list形式返回。client字符串形式：[{clientId=t001, clientName=恒丰portal测试1, clientUrl=http://www.a.com/hfportal/, remark= }, {clientId=t002, clientName=恒丰portal测试2, clientUrl=http://www.b.com/hfportal/, remark=}, {clientId=t003, clientName=恒丰portal测试3, clientUrl=http://www.c.com/hfportal/, remark=}]
    	 *@creatTime:2017年6月11日 上午1:19:04 
    	 *@return:@param clients
    	 *@return:@return List<String>
    	 *@modifier:
    	 *@modifTime:
    	 *@throws
     */
    @SuppressWarnings("unchecked")
	public static List<String> getClientUrl(String clients){
    	String formatStr=getList(clients);
    	logger.debug(formatStr);
		String[] list=formatStr.split(",");
		List<String> clientUrlList=new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			String tstr=list[i];
			String _str=tstr.replace("=", "\":\"");
			String str="{\""+_str+"\"}";
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String,String> map=mapper.readValue(str, Map.class);
				for(String key : map.keySet()){
					if(Contants.CLIENTURL.equals(key)){
						clientUrlList.add(map.get(key));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clientUrlList;
    }
    
    public static List<String> getClientUrl(List<Map<String,String>> clientList){
    	List<String> clientUrlList=new ArrayList<String>();
    	for (int i = 0; i < clientList.size(); i++) {
    		Map<String,String> map=new HashMap<String,String>();
    		map=clientList.get(i);
    		logger.debug(map);
			for(String key : map.keySet()){
				if(Contants.CLIENTURL.equals(key)){
					clientUrlList.add(map.get(key));
				}
			}
		}
    	
		return clientUrlList;
    }
    
    /**
     * 
    	 *@author lisw
    	 *@Description: 从url全路径中获取不带项目名的域名，如http://www.analportal.com/login.html/...等，返回http://www.analportal.com/
    	 *@creatTime:2017年8月11日 下午6:55:38 
    	 *@return:@param url
    	 *@return:@return String
    	 *@modifier:
    	 *@modifTime:
    	 *@throws
     */
    public static String getDomainUrl(String url){
    	String retStr=url;
    	if(Tools.isNotEmpty(url)){
    		//从http://之后开始获取第一个“/”所在位置。然后截取0开始到这个位置所在的字符串，最终返回
    		String http="http://";
    		if(url.length()>http.length()){
    			String tUrl=url.substring(http.length());
        		
        		int position=tUrl.indexOf("/");
        		if(position!=-1){
        			retStr=tUrl.substring(0, position);
            		retStr=http+retStr;
        		}
    		}
    		
    	}
    	return retStr;
    }
    
	public static void main(String[] args) {
		
		System.out.println(getDomainUrl("/login.html..."));
		/*String s="hucihciuhewi|||cnwuhciu";
		String s1=s.substring(0, s.indexOf(Contants.DELIMITER));
		String s2=s.substring(s.indexOf(Contants.DELIMITER)+3);
		System.out.println(s1+";"+s2);*/
		
		//String clients="[{clientId=t001, clientName=恒丰portal测试1, clientUrl=http://www.a.com/hfportal/, remark= }, {clientId=t002, clientName=恒丰portal测试2, clientUrl=http://www.b.com/hfportal/, remark=}, {clientId=t003, clientName=恒丰portal测试3, clientUrl=http://www.c.com/hfportal/, remark=}]";
		
		//System.out.println(list.length);
	}

}
