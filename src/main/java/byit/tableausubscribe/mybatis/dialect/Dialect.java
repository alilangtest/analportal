package byit.tableausubscribe.mybatis.dialect;

public abstract class Dialect {

	public static enum Type{
		MYSQL,
		ORACLE,
		POSTGRESQL
	}
	
	public abstract String getLimitString(String sql, int skipResults, int maxResults);
	
}
 