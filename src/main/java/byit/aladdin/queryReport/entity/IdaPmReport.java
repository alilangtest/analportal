package byit.aladdin.queryReport.entity;


import java.util.Date;

public class IdaPmReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String reportid;
	private String reportname;
	private String projectname;
	private String reportzhname;
	private String reporttype;
	private String url;
	private String desp;
	private Short sortindex;
	private String reportstate;
	private String reportpic;
	private String publisper;
	private Date publishdate;
	private Date updatedate;
	private String reportlev;
	private String reportcateg;
	private String orgfield;
	private String panelwidth;
	private String panelheight;
	private String reportdescription;
	private String dateScreen;
	private String  workbookid;
	private String workbookname;
	private String siteid;        //站点id
	private Date gettime;
	private String mountstate;
	
	public IdaPmReport() {
	}

	public IdaPmReport(String reportid, String reportname, String projectname, String reportzhname, String reporttype,
			String url, String desp, Short sortindex, String reportstate, String reportpic, String publisper,
			Date publishdate, Date updatedate, String reportlev, String reportcateg, String orgfield, String panelwidth,
			String panelheight, String reportdescription, String dateScreen) {
		super();
		this.reportid = reportid;
		this.reportname = reportname;
		this.projectname = projectname;
		this.reportzhname = reportzhname;
		this.reporttype = reporttype;
		this.url = url;
		this.desp = desp;
		this.sortindex = sortindex;
		this.reportstate = reportstate;
		this.reportpic = reportpic;
		this.publisper = publisper;
		this.publishdate = publishdate;
		this.updatedate = updatedate;
		this.reportlev = reportlev;
		this.reportcateg = reportcateg;
		this.orgfield = orgfield;
		this.panelwidth = panelwidth;
		this.panelheight = panelheight;
		this.reportdescription = reportdescription;
		this.dateScreen = dateScreen;
	}
	
	public Date getGettime() {
		return gettime;
	}

	public void setGettime(Date gettime) {
		this.gettime = gettime;
	}

	public String getMountstate() {
		return mountstate;
	}

	public void setMountstate(String mountstate) {
		this.mountstate = mountstate;
	}

	public String getReportId() {
		return reportid;
	}

	public void setReportId(String reportid) {
		this.reportid = reportid;
	}

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getReportzhname() {
		return reportzhname;
	}

	public void setReportzhname(String reportzhname) {
		this.reportzhname = reportzhname;
	}

	public String getReporttype() {
		return reporttype;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public Short getSortindex() {
		return sortindex;
	}

	public void setSortindex(Short sortindex) {
		this.sortindex = sortindex;
	}

	public String getReportstate() {
		return reportstate;
	}

	public void setReportstate(String reportstate) {
		this.reportstate = reportstate;
	}

	public String getReportpic() {
		return reportpic;
	}

	public void setReportpic(String reportpic) {
		this.reportpic = reportpic;
	}

	public String getPublisper() {
		return publisper;
	}

	public void setPublisper(String publisper) {
		this.publisper = publisper;
	}

	public Date getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getReportlev() {
		return reportlev;
	}

	public void setReportlev(String reportlev) {
		this.reportlev = reportlev;
	}

	public String getReportcateg() {
		return reportcateg;
	}

	public void setReportcateg(String reportcateg) {
		this.reportcateg = reportcateg;
	}

	public String getOrgfield() {
		return orgfield;
	}

	public void setOrgfield(String orgfield) {
		this.orgfield = orgfield;
	}

	public String getPanelwidth() {
		return panelwidth;
	}

	public void setPanelwidth(String panelwidth) {
		this.panelwidth = panelwidth;
	}

	public String getPanelheight() {
		return panelheight;
	}

	public void setPanelheight(String panelheight) {
		this.panelheight = panelheight;
	}

	public String getReportdescription() {
		return reportdescription;
	}

	public void setReportdescription(String reportdescription) {
		this.reportdescription = reportdescription;
	}

	public String getDateScreen() {
		return dateScreen;
	}

	public void setDateScreen(String dateScreen) {
		this.dateScreen = dateScreen;
	}

	
	public String getWorkbookid() {
		return workbookid;
	}

	public void setWorkbookid(String workbookid) {
		this.workbookid = workbookid;
	}

	public String getWorkbookname() {
		return workbookname;
	}

	public void setWorkbookname(String workbookname) {
		this.workbookname = workbookname;
	}

	
	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	@Override
	public String toString() {
		return "IdaPmReport [reportname=" + reportname + ", projectname=" + projectname + ", reportzhname="
				+ reportzhname + ", reporttype=" + reporttype + ", url=" + url + ", desp=" + desp + ", sortindex="
				+ sortindex + ", reportstate=" + reportstate + ", reportpic=" + reportpic + ", publisper=" + publisper
				+ ", publishdate=" + publishdate + ", updatedate=" + updatedate + ", reportlev=" + reportlev
				+ ", reportcateg=" + reportcateg + ", orgfield=" + orgfield + ", panelwidth=" + panelwidth
				+ ", panelheight=" + panelheight + ", reportdescription=" + reportdescription + ", dateScreen="
				+ dateScreen + "]";
	}

}
