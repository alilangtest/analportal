package byit.osdp.tableau.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import byit.core.plug.expection.WarnException;
import byit.core.util.codec.EncodeUtil;

/**
 * Tableau服务工具类
 */
public class TableauUtil {

	// ==============================Fields===========================================
	private static final Logger LOGGER = LoggerFactory.getLogger(TableauUtil.class);

	private static final int MAX_TRUSTED_LIMIT = 10000;

	// ==============================Methods==========================================
	/**
	 * 获取 Tableau Server 票证
	 * @param wgserver Tableau Server 的访问地址
	 * @param username Tableau Server 的用户名
	 * @param clientIp 客户端IP
	 * @return 票证
	 */
	public static String getTrustedTicket(String wgserver, String username, String clientIp,String url_namespace) {
		OutputStream output = null;
		BufferedReader input = null;
		
		try {
			URLConnection conn = new URL("http://" + wgserver + "/trusted").openConnection();
			conn.setDoOutput(true);
			output = conn.getOutputStream();
			output.write(("username=" + EncodeUtil.urlEncode(username) + "&client_ip=" + EncodeUtil.urlEncode(clientIp) + "&target_site=" + EncodeUtil.urlEncode(url_namespace)).getBytes());
			output.flush();
			input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder writer = new StringBuilder();
			for (String line = null; (line = input.readLine()) != null;) {
				if (writer.length() > MAX_TRUSTED_LIMIT) {
					throw new WarnException("Input too lang!");
				}
				writer.append(line);
			}
			return writer.toString();
		} catch (Exception e) {
			LOGGER.warn("!", e);
			return "";
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * 连接数据库
	 * @param url 数据库地址 (jdbc:postgresql://{localhost:8060}/workgroup)
	 * @param username 用户名 (tableau|readonly)
	 * @param password 密码
	 */
	public static Connection getPostgreSqlConnection(String url, String username, String password) {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			LOGGER.error("!", e);
			return null;
		}
	}
}