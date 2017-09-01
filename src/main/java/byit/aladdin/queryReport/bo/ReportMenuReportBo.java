package byit.aladdin.queryReport.bo;

public class ReportMenuReportBo {
	
	private String eventid;
	private String menuid;
	private String reportid;
	
	public ReportMenuReportBo() {
		super();
	}
	public ReportMenuReportBo(String eventid, String menuid, String reportid) {
		super();
		this.eventid = eventid;
		this.menuid = menuid;
		this.reportid = reportid;
	}
	
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	
	

}
