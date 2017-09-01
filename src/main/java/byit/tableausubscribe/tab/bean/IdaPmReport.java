package byit.tableausubscribe.tab.bean;

/**
 * tableau实例对象：工作簿、视图、数据源
 */
public class IdaPmReport{

	private String workbooksId;//报表
	private String workbooksName;//仪表板名称
	private String reportId;//报表
	private String reportName;//仪表板名称
	private String datasourceId;//一个数据源，多个表
	private String datasourceName;//数据源名称
	private String url;
	
	
	public String getWorkbooksId() {
		return workbooksId;
	}
	public void setWorkbooksId(String workbooksId) {
		this.workbooksId = workbooksId;
	}
	public String getWorkbooksName() {
		return workbooksName;
	}
	public void setWorkbooksName(String workbooksName) {
		this.workbooksName = workbooksName;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getDatasourceId() {
		return datasourceId;
	}
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}





}
