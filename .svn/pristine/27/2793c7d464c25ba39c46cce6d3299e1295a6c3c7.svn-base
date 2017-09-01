package byit.osdp.portal.sso.https;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;


public class HttpsClientPost {
	static Logger log=Logger.getLogger(HttpsClientPost.class);
	
	//定义SSL
	static String SSL="SSL";
	
	//定义请求方式
	static String requestType="POST";
	
	//定义头部信息
	static String contentType="application/json";
	
	/**
	 * @throws Exception 
	 * 
		 *@author lisw
		 *@Description: post方式向服务器请求数据，返回json字符串
		 *@creatTime:2016年9月10日 下午5:16:00 
		 *@param url，请求服务器的路径
		 *@param content，请求的json字符串内容，元素必须用双引号包含
		 *@param charset，编码，一般为UTF-8
		 *@return
		 *@throws NoSuchAlgorithmException
		 *@throws KeyManagementException
		 *@throws IOException String
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static String requestPost(String url, String content, String charset)
            throws Exception {
		//log.info("into HttpClientPost's requestPost method，current time is: "+Tools.getCurrFormatTime());
		String returnStr=null;
        SSLContext sc = SSLContext.getInstance(SSL);
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                new java.security.SecureRandom());
 
        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod(requestType);
        //conn.setRequestProperty("Content-Type ","application/x-www-form-urlencoded ");
        conn.setRequestProperty("Content-Type",contentType);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        log.info("begin request data with https");
        InputStream is = conn.getInputStream();
        if(is!=null){
        	//InputStreamReader是从字节到字符的桥梁，它使用指定的 charset 读取字节并将其解码为字符，所以需要进行转码
    		InputStreamReader inStreamReader=new InputStreamReader(is,charset);

    		//Reader读取的是字符流。BufferedReader是reader的子类
    		BufferedReader responseReader = new BufferedReader(inStreamReader);
    		
    		StringBuffer sb = new StringBuffer();
    		String readLine = new String();
    		//BufferedReader.readLinr():读取一个文本行，返回String。读取的事返回后的数据
    		while ((readLine = responseReader.readLine()) != null) {
    			sb.append(readLine).append("\n");
    		}
    		
    		responseReader.close();
    		returnStr = sb.toString();
        }
        log.info("request data with https is end，current time is: "+new Date());
        return returnStr;
    }
}
