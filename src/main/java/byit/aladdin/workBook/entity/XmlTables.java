package byit.aladdin.workBook.entity;

public class XmlTables {
	// ==============================Fields========================================
	private Long id;
	private String dbName;
	private String tableName;
	private String workbookId;

	// ==============================Methods==========================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(String workbookId) {
		this.workbookId = workbookId;
	}
}
