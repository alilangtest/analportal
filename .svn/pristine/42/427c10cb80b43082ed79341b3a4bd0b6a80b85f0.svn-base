package byit.tableausubscribe.tab.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection 
{
	 /* public static Connection getConnection(String contributionName, String dsName)
	    throws Exception
	  {
	    Connection oracle_conn = null;
	    try {
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      oracle_conn = DriverManager.getConnection("jdbc:oracle:thin:@25.0.88.36:1521:datacent", "dm", "oracle");//测试
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    return oracle_conn;
	  }*/

	  /*public static Connection getConnection()
	    throws Exception
	  {
	    return getConnection(null, null);
	  }*/

	  public static void closeConnection(Connection conn, Statement stmt, ResultSet rs)
	  {
	    if (rs != null)
	    {
	      try
	      {
	        rs.close();
	      }
	      catch (Exception localException1)
	      {
	      }
	    }
	    if (stmt != null)
	    {
	      try
	      {
	        stmt.close();
	      }
	      catch (Exception localException2)
	      {
	      }
	    }
	    if (conn != null)
	    {
	      try
	      {
	        conn.close();
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }

	  public static void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs)
	  {
	    if (rs != null)
	    {
	      try
	      {
	        rs.close();
	      }
	      catch (Exception localException1)
	      {
	      }
	    }
	    if (pstmt != null)
	    {
	      try
	      {
	        pstmt.close();
	      }
	      catch (Exception localException2)
	      {
	      }
	    }
	    if (conn != null)
	    {
	      try
	      {
	        conn.close();
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }

	  public static void closeConnection(Connection conn, CallableStatement cs, ResultSet rs)
	  {
	    if (rs != null)
	    {
	      try
	      {
	        rs.close();
	      }
	      catch (Exception localException1)
	      {
	      }
	    }
	    if (cs != null)
	    {
	      try
	      {
	        cs.close();
	      }
	      catch (Exception localException2)
	      {
	      }
	    }
	    if (conn != null)
	    {
	      try
	      {
	        conn.close();
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }

	  /*public static void main(String[] args) {
	    System.out.println("=================test start!=================");
	    Connection conn;
	    try {
	      conn = getConnection();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }

	    System.out.println("=================test end!=================");
	  }*/
	}
