package byit.aladdin.workBook.util;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class JdbcConfiguration {

	//@Autowired
	//private Environment environment;

	@Value("${portal.jdbc.url}")
	private String portalJdbcUrl;
	@Value("${portal.jdbc.username}")
	private String portalJdbcUsername;
	@Value("${portal.jdbc.password}")
	private String portalJdbcPassword;

	@Value("${tableau.postgresql.url}")
	private String tableauPostgresqlUrl;
	@Value("${tableau.postgresql.username}")
	private String tableauPostgresqlUsername;
	@Value("${tableau.postgresql.password}")
	private String tableauPostgresqlPassword;

	/**
	 * 数据源配置, 使用C3P0数据库连接池
	 */
	@Primary
	@Bean(destroyMethod = "close")
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl(portalJdbcUrl);
		dataSource.setUser(portalJdbcUsername);
		dataSource.setPassword(portalJdbcPassword);
		dataSource.setAcquireIncrement(1);
		dataSource.setInitialPoolSize(2);
		dataSource.setMinPoolSize(2);
		dataSource.setMaxPoolSize(20);
		dataSource.setMaxIdleTime(60);
		dataSource.setIdleConnectionTestPeriod(20);
		dataSource.setPreferredTestQuery("SELECT 1");
		dataSource.setMaxStatements(100);
		dataSource.setNumHelperThreads(10);
		return dataSource;
	}

	/** 数据源配置, 使用C3P0数据库连接池 */
	@Bean(destroyMethod = "close")
	public DataSource tableauDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("org.postgresql.Driver");
		dataSource.setJdbcUrl(tableauPostgresqlUrl);
		dataSource.setUser(tableauPostgresqlUsername);
		dataSource.setPassword(tableauPostgresqlPassword);
		dataSource.setAcquireIncrement(1);
		dataSource.setInitialPoolSize(2);
		dataSource.setMinPoolSize(2);
		dataSource.setMaxPoolSize(20);
		dataSource.setMaxIdleTime(60);
		dataSource.setIdleConnectionTestPeriod(20);
		dataSource.setPreferredTestQuery("select count(now())");
		dataSource.setMaxStatements(100);
		dataSource.setNumHelperThreads(10);
		return dataSource;
	}

	@Primary
	@Bean
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public JdbcTemplate tableauJdbcTemplate() throws PropertyVetoException {
		return new JdbcTemplate(tableauDataSource());
	}

}
