package byit.tableausubscribe.tab.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {
	 /* public static Connection getConnection()
	    throws Exception
	  {
	    Connection conn = null;
	    try {
	      conn = DBConnection.getConnection(null, null);
	    } catch (Exception e) {
	      throw new Exception("数据库链接错误");
	    }
	    return conn;
	  }*/

	  public static void close(Connection conn)
	    throws Exception
	  {
	    if (conn != null)
	      try {
	        conn.close();
	      } catch (SQLException e) {
	        throw new Exception("数据库链接错误", e);
	      }
	  }
	}