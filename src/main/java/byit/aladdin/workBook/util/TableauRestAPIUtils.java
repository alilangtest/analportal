package byit.aladdin.workBook.util;

import org.dom4j.Document;
import org.dom4j.Element;

import byit.osdp.tableau.TableauConfig;

/**
 * 调用rest api接口
 * @author lvyt
 *
 */
public class TableauRestAPIUtils {
	
	public static String username = TableauConfig.TABLEAU_SERVER_NAME;
	public static String password = TableauConfig.TABLEAU_SERVER_PASSWORD;
	/**
	 * 根据rest api登录获取管理员token
	 * @return
	 */
	public static String doGetUserToken(String urlNamespace){
		String accessToken = null;
		String result = null;
		String authUrl = HttpClientUtils.doGetUrl("/api/api-version/auth/signin");
		String xmlData = "<tsRequest>"
				 		+"<credentials name=\""+username+"\" password=\""+password+"\" >"
				 		+"<site contentUrl=\""+urlNamespace+"\" />"
				 		+"</credentials></tsRequest>";
		try {
			//rest api sign in
			result = HttpClientUtils.sendXMLDataByPost(authUrl,xmlData,null);
			Document document =DomXMLUtils.doGetDocument(result);
			//获取根节点
			Element root =  document.getRootElement();
			//获取节点credentials
			Element element = root.element("credentials");
			//获取属性token
			accessToken = element.attributeValue("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
}
