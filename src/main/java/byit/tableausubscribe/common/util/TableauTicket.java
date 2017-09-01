package byit.tableausubscribe.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;

public class TableauTicket {

	public static String getTrustedTicket(String wgserver, String user,
			String remoteAddr) throws ServletException {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		final int MAZ_STR_LEN = 10000;
		try {
			// Encode the parameters
			StringBuffer data = new StringBuffer();
			data.append(URLEncoder.encode("username", "UTF-8"));
			data.append("=");
			data.append(URLEncoder.encode(user, "UTF-8"));
			data.append("&");
			data.append(URLEncoder.encode("client_ip", "UTF-8"));
			data.append("=");
			data.append(URLEncoder.encode(remoteAddr, "UTF-8"));

			// send the request
			URL url = new URL("http://" + wgserver + "/trusted");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(data.toString());
			out.flush();

			// Read the response
			StringBuffer rsp = new StringBuffer();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (rsp.length() > MAZ_STR_LEN) {
					throw new Exception("input too long!");
				}
				rsp.append(line);
			}
			//System.out.println("******************"+rsp.toString()+"************"+conn.getHeaderField("Set-Cookie"));
			return rsp.toString();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
}
