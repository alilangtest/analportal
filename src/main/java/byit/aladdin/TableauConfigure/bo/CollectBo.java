package byit.aladdin.TableauConfigure.bo;

/**
 * Title:Collect 
 * Description:我的收藏 
 */
public class CollectBo {

	private String collectid;//收藏id
	private String reportid;//报表id
	private String employeeid;//用户编号
	private String employeename;//用户编号
	private String seqdate;//按日期排序用于置顶功能
	private String reportname;//报表名称
	
//	private String staffname;//用户姓名

	public CollectBo() {
	}

	public CollectBo(String collectid, String reportid, String employeename, String seqdate, String reportname) {
		super();
		this.collectid = collectid;
		this.reportid = reportid;
		this.employeename = employeename;
		this.seqdate = seqdate;
		this.reportname = reportname;
	}

	public String getCollectid() {
		return collectid;
	}

	public void setCollectid(String collectid) {
		this.collectid = collectid;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getSeqdate() {
		return seqdate;
	}

	public void setSeqdate(String seqdate) {
		this.seqdate = seqdate;
	}

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
}
