package byit.tableausubscribe.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Tableau 资源获取器
 */
public class TableauInceptHelper {

	// ==============================Fields===========================================
	private static final int CONNECT_TIMEOUT = 15 * 1000;
	private static final int READ_TIMEOUT = 60 * 1000;

	// ==============================Methods==========================================
	public static byte[] readResource(String url) throws Exception {

		Thread.sleep(500);

		HttpURLConnection conn1 = null;
		HttpURLConnection conn2 = null;
		InputStream input = null;
		try {
			URL oUrl = new URL(url);
			String host = oUrl.getHost() + ":" + oUrl.getPort();// #$

			conn1 = getConnection(url);
			conn1.setRequestProperty("Host", host);
			conn1.connect();
			if (conn1.getResponseCode() != 302) {
				throw new RuntimeException("Tableau Ticket Authentication Failed!");
			}

			Map<String, List<String>> headers = conn1.getHeaderFields();

			String redirect = headers.get("Location").get(0);

			List<String> cookieBuilder = new ArrayList<String>();
			for (String cookieItem : conn1.getHeaderFields().get("Set-Cookie")) {
				int part = cookieItem.indexOf(";");
				cookieBuilder.add(cookieItem.substring(0, part));
			}
			String cookie = StringUtils.join(cookieBuilder, "; ");

			// System.out.println(cookie);

			conn2 = getConnection(redirect);

			List<String> contentTypes = headers.get("Content-Type");
			if (contentTypes != null && contentTypes.size() >= 1) {
				conn2.setRequestProperty("Content-Type", contentTypes.get(0));
			}
			conn2.setRequestProperty("Host", host);
			conn2.setRequestProperty("Cookie", cookie);
			conn2.connect();

			if (conn2.getResponseCode() != 200) {
				throw new RuntimeException("Tableau Resource[" + redirect + "] read error !");
			}

			return IOUtils.toByteArray(input = conn2.getInputStream());
		} finally {
			IOUtils.closeQuietly(input);
			closeQuietly(conn1);
			closeQuietly(conn2);
		}
	}

	// 仿照IE11的HTTP_HEADER
	private static HttpURLConnection getConnection(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		conn.setRequestProperty("Connection", "close");
		conn.setRequestProperty("Cache-Control", "no-cache");
		conn.setRequestProperty("Accept-Language", "zh-CN");
		conn.setInstanceFollowRedirects(false);
		conn.setDoInput(true);
		return conn;
	}

	private static void closeQuietly(HttpURLConnection conn) {
		try {
			//IOUtils..close(conn);
			conn.disconnect();
		} catch (Exception e) {

		}
	}
}
