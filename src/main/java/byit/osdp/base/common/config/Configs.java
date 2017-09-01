package byit.osdp.base.common.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Throwables;

/**
 * 读取配置文件工具
 */
public class Configs {

	private static final Properties properties = new Properties();
	static {
		InputStream input = null;
		try {
			// Configs.class.getClassLoader().getResourceAsStream("application.properties")
			properties.load(input = Configs.class.getResourceAsStream("/application.properties"));
		} catch (Exception e) {
			throw Throwables.propagate(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	/**
	 * 返回配置文件中对应的字符串
	 * @param name 属性名
	 * @return 属性值
	 */
	public static String getString(String name) {
		return properties.getProperty(name);
	}

	/**
	 * 返回配置文件中对应的字符串
	 * @param name 属性名
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public static String getString(String name, String defaultValue) {
		return StringUtils.defaultString(properties.getProperty(name), defaultValue);
	}

	/**
	 * 返回配置文件中对应的值
	 * @param name 属性名
	 * @return 属性值
	 */
	public static Boolean getBoolean(String name) {
		try {
			return Boolean.valueOf(getString(name));
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}
}
