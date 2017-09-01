package byit.tableausubscribe.mybatis.dialect;

public class PostgresDialect extends Dialect{
	
	protected static final String SQL_END_DELIMITER = ";";
	
	public String getLimitString(String sql, boolean hasOffset, String querySelect) {
		sql =  getLineSql(sql);
		return sql.replaceAll("[^\\s,]+\\.", "") +" limit -1  offset -1 ";
	}

	public String getLimitString(String sql, int offset, int limit) {
		sql = getLineSql(sql);
		 sql =  sql.replaceAll("[^\\s,]+\\.", "") +" limit "+ offset +" offset "+ limit;
		return sql;
	}

	public boolean supportsLimit() {  
		return true;
	}
	private  String getLineSql(String sql) {
		return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
	}
}
