package byit.tableausubscribe.tab.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbSql {
	/*
	 * public static List findNativeSQL(Connection conn, String sql, Object[]
	 * obj) throws Exception { List resultList = new ArrayList(); ResultSet rs =
	 * null; ResultSetMetaData rsm = null; PreparedStatement st = null; Map map
	 * = null; String dateStr = ""; try { st = conn.prepareStatement(sql); if
	 * (obj != null) { for (int i = 0; i < obj.length; i++) { st.setObject(i +
	 * 1, obj[i]); } } rs = st.executeQuery(); rsm = rs.getMetaData(); while
	 * (rs.next()) { map = new HashMap(); for (int col = 0; col <
	 * rsm.getColumnCount(); col++) { if
	 * ("date".equalsIgnoreCase(rsm.getColumnTypeName(col + 1))) { dateStr =
	 * rs.getString(col + 1); if ((dateStr != null) && (dateStr.contains(".0")))
	 * { dateStr = dateStr .substring(0, dateStr.length() - 2); }
	 * map.put(rsm.getColumnName(col + 1).toLowerCase(), dateStr); } else {
	 * map.put(rsm.getColumnName(col + 1).toLowerCase(), rs.getString(col + 1));
	 * } } resultList.add(map); map = null; } List localList1 = resultList;
	 * return localList1; } catch (Exception ex) { throw new
	 * Exception(ex.getMessage()); } finally { if (rs != null) { try {
	 * rs.close(); } catch (SQLException e) { throw new
	 * Exception("��ѯʱ���رս���쳣:" + e.getMessage()); } } if (st != null) { try {
	 * st.close(); } catch (SQLException e) { throw new
	 * Exception("��ѯʱ���ر�statement�쳣:" + e.getMessage()); } } rsm = null; st =
	 * null; rs = null; map = null; dateStr = null; resultList = null; } }
	 */

	public static List<Map<String, String>> findNativeSQL(Connection conn,
			String sql, Object[] obj) throws Exception {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		ResultSet rs = null;
		ResultSetMetaData rsm = null;
		PreparedStatement st = null;
		Map<String, String> map = null;
		String dateStr = "";
		try {
			st = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					st.setObject(i + 1, obj[i]);
				}
			}
			rs = st.executeQuery();
			rsm = rs.getMetaData();
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int col = 0; col < rsm.getColumnCount(); col++) {
					if ("date".equalsIgnoreCase(rsm.getColumnTypeName(col + 1))) {
						dateStr = rs.getString(col + 1);
						if ((dateStr != null) && (dateStr.contains(".0"))) {
							dateStr = dateStr
									.substring(0, dateStr.length() - 2);
						}
						map.put(rsm.getColumnName(col + 1).toLowerCase(),
								dateStr);
					} else {
						map.put(rsm.getColumnName(col + 1).toLowerCase(),
								rs.getString(col + 1));
					}
				}
				resultList.add(map);
				map = null;
			}
			List<Map<String, String>> localList1 = resultList;
			return localList1;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new Exception("错误信息:" + e.getMessage());
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new Exception("错误信息:" + e.getMessage());
				}
			}
			rsm = null;
			st = null;
			rs = null;
			map = null;
			dateStr = null;
			resultList = null;
		}
	}
	public static int findNativeSQLRows(Connection conn,
			String sql) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;
		int rowCount = 0;
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				rowCount = rs.getInt(1);
			}
			return rowCount;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new Exception("错误信息:" + e.getMessage());
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new Exception("错误信息:" + e.getMessage());
				}
			}
			st = null;
			rs = null;
		}
	}
	/*public static List findNativeSQL(String sql, Object[] obj) {
		Connection conn = null;
		if (conn == null) {
			try {
				conn = DBConnection.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List list = null;
		try {
			list = findNativeSQL(conn, sql, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}*/

	/*public static List<Map<String, String>> findNativeSQL(String sql, Object[] obj) {
		Connection conn = null;
		if (conn == null) {
			try {
				conn = DBConnection.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Map<String, String>> list = null;
		try {
			list = findNativeSQL(conn, sql, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}*/
	
	public static void executeSQL(Connection conn, String sql, Object[] obj)
			throws Exception {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					st.setObject(i + 1, obj[i]);
				}
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					throw new Exception("错误信息:" + e.getMessage());
				}
			}
			st = null;
		}
	}
}