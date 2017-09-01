package byit.tableausubscribe.tab.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DB {
	static Logger log=Logger.getLogger(DB.class);
	
	/**
	 * 
		 *@author lisw
		 *@Description: 获取adm数据库的链接
		 *@creatTime:2017年6月1日 下午4:34:26 
		 *@return:@return Connection
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static Connection getAdmConn() {
		return getConn(JdbcConfig.ADM_DRIVER,JdbcConfig.ADM_URL,JdbcConfig.ADM_USERNAME,JdbcConfig.ADM_PASSWORD);
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 获取dm数据库的链接
		 *@creatTime:2017年6月1日 下午4:34:38 
		 *@return:@param url
		 *@return:@param username
		 *@return:@param password
		 *@return:@return Connection
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static Connection getDmConn() {
		return getConn(JdbcConfig.DM_DRIVER,JdbcConfig.DM_URL,JdbcConfig.DM_USERNAME,JdbcConfig.DM_PASSWORD);
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 获取portal平台数据库的链接，该平台数据库在其他功能点通过mybatis与数据库交互，在这里为了方便使用JDBC与数据库交互
		 *@creatTime:2017年6月1日 下午4:35:47 
		 *@return:@param url
		 *@return:@param username
		 *@return:@param password
		 *@return:@return Connection
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static Connection getPortalConn(){
		return getConn(JdbcConfig.PORTAL_DRIVER,JdbcConfig.PORTAL_URL,JdbcConfig.PORTAL_USERNAME,JdbcConfig.PORTAL_PASSWORD);
	}
	/**
	 * 
		 *@author lisw
		 *@Description: 获取数据库链接
		 *@creatTime:2017年6月1日 下午4:34:52 
		 *@return:@param driver
		 *@return:@param url
		 *@return:@param username
		 *@return:@param password
		 *@return:@return Connection
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static Connection getConn(String driver,String url,String username,String password) {
		
		//1、加载数据库驱动
		try {
			Class.forName(driver); // 在某个静态代码块中调用DriverManager.registerDriver
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 2.创建数据库连接
		Connection conn = null;
		try {
			// 意味着DriverManager.registerDriver在此之前已经被调用了
			conn = DriverManager.getConnection(url,username,password);
			// 表明数据库连接OK
			conn.setAutoCommit(false);
			log.debug(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	//数据库提交
	public static void commit(Connection conn) {

		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//数据库回滚
	public static void rollback(Connection conn) {

		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//关闭Statement（含preparedStatement）
	public static void close(Statement stmt) {

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//关闭数据库链接
	public static void close(Connection conn) {

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	//关闭resultSet
	public static void close(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 关闭connect、statement、resultset
		 *@creatTime:2017年6月1日 上午10:19:17 
		 *@return:@param conn
		 *@return:@param stmt
		 *@return:@param rs void
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static void close(Connection conn,Statement stmt,ResultSet rs){
		close(conn);
		close(stmt);
		close(rs);
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 关闭connect、statement
		 *@creatTime:2017年6月1日 上午10:19:17 
		 *@return:@param conn
		 *@return:@param stmt
		 *@return:@param rs void
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static void close(Connection conn,Statement stmt){
		close(conn);
		close(stmt);
	}
}
