package byit.osdp.tableau;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class TableauConfig {

	public static final String TABLEAU_TRUSTED_HOST;
	public static final String TABLEUA_POSTGRESQL_IP;
	public static final String TABLEUA_POSTGRESQL_URL;
	public static final String TABLEUA_POSTGRESQL_USERNAME;
	public static final String TABLEUA_POSTGRESQL_PASSWORD;
	public static final String TABLEUA_EMBED_URL;
	public static final String TABLEAU_SERVER_NAME;
	public static final String TABLEAU_SERVER_PASSWORD;
	public static final String TABLEAU_TASK_REFRESHTIMES;
	public static final String TABLEAU_TASK_REFRESHINTERVAL;
	public static final String TABLEAU_VERSION;
	public static final String SCHEDULER_ROOTPATH;
	
	static {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			properties.load(input = TableauConfig.class.getResourceAsStream("/tableau.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
		}
		TABLEAU_TRUSTED_HOST = properties.getProperty("tableau.trusted.host");
		TABLEUA_POSTGRESQL_IP = properties.getProperty("tableau.postgresql.ip");
		TABLEUA_POSTGRESQL_URL = properties.getProperty("tableau.postgresql.url");
		TABLEUA_POSTGRESQL_USERNAME = properties.getProperty("tableau.postgresql.username");
		TABLEUA_POSTGRESQL_PASSWORD = properties.getProperty("tableau.postgresql.password");
		TABLEUA_EMBED_URL = properties.getProperty("tableau.embed.url");
		TABLEAU_SERVER_NAME = properties.getProperty("tableau.server.admin.username");
		TABLEAU_SERVER_PASSWORD = properties.getProperty("tableau.server.admin.password");
		TABLEAU_TASK_REFRESHTIMES = properties.getProperty("tableau.task.refreshtimes");
		TABLEAU_TASK_REFRESHINTERVAL = properties.getProperty("tableau.task.refreshinterval");
		TABLEAU_VERSION = properties.getProperty("tableau.version");
		SCHEDULER_ROOTPATH = properties.getProperty("scheduler.rootPath");
	}

	//public static final String getEmbedMappingUrl() {}
}
