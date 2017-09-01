package byit.aladdin.dataIndex.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class ByitConfig {

	public static final String FILE_UPLOAD_PATH;
	public static final String FILE_ERROR_PATH;
	public static final int EXP_EXCEL_MAXSIZE;
	static {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			properties.load(input = ByitConfig.class.getResourceAsStream("/byit.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}
		FILE_UPLOAD_PATH = properties.getProperty("file.upload.path");
		FILE_ERROR_PATH = properties.getProperty("file.error.path");
		EXP_EXCEL_MAXSIZE =Integer.parseInt(properties.getProperty("tableauscbscribe.excel.maxsize"));
	}

	//public static final String getEmbedMappingUrl() {}
}
