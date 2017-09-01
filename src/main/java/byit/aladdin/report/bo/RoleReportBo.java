package byit.aladdin.report.bo;

import java.util.Date;

public class RoleReportBo {

	private String eventid;
	private String reportid;
	private String roleid;
	private String reporttype;
	private String optype;
	private Date audtate;
	private Long limitday;
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public Date getAudtate() {
		return audtate;
	}
	public void setAudtate(Date audtate) {
		this.audtate = audtate;
	}
	public Long getLimitday() {
		return limitday;
	}
	public void setLimitday(Long limitday) {
		this.limitday = limitday;
	}
	public RoleReportBo(String eventid, String reportid, String roleid, String reporttype, String optype, Date audtate,
			Long limitday) {
		super();
		this.eventid = eventid;
		this.reportid = reportid;
		this.roleid = roleid;
		this.reporttype = reporttype;
		this.optype = optype;
		this.audtate = audtate;
		this.limitday = limitday;
	}
	public RoleReportBo() {
		super();
	}
	
	
	
}
