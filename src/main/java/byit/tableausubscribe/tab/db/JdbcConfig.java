package byit.tableausubscribe.tab.db;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class JdbcConfig {

	public static final String ADM_DRIVER;
	public static final String ADM_URL;
	public static final String ADM_USERNAME;
	public static final String ADM_PASSWORD;
	
	public static final String DM_DRIVER;
	public static final String DM_URL;
	public static final String DM_USERNAME;
	public static final String DM_PASSWORD;
	
	public static final String PORTAL_DRIVER;
	public static final String PORTAL_URL;
	public static final String PORTAL_USERNAME;
	public static final String PORTAL_PASSWORD;
	
	static {
		//Properties properties = new Properties();
		Properties portalProperties = new Properties();
		InputStream input = null;
		try {
			//properties.load(input = JdbcConfig.class.getResourceAsStream("/tableausubscribeConf/tableauSubscribeJdbc.properties"));
			portalProperties.load(input = JdbcConfig.class.getResourceAsStream("/application.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}
		ADM_DRIVER = portalProperties.getProperty("adm.driver");
		ADM_URL = portalProperties.getProperty("adm.url");
		ADM_USERNAME = portalProperties.getProperty("adm.username");
		ADM_PASSWORD = portalProperties.getProperty("adm.password");
		
		DM_DRIVER = portalProperties.getProperty("dm.driver");
		DM_URL = portalProperties.getProperty("dm.url");
		DM_USERNAME = portalProperties.getProperty("dm.username");
		DM_PASSWORD = portalProperties.getProperty("dm.password");
		
		PORTAL_DRIVER = portalProperties.getProperty("jdbc.driver");
		PORTAL_URL = portalProperties.getProperty("jdbc.url");
		PORTAL_USERNAME = portalProperties.getProperty("jdbc.username");
		PORTAL_PASSWORD = portalProperties.getProperty("jdbc.password");
	}

	//public static final String getEmbedMappingUrl() {}
}
