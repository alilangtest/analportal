package byit.aladdin.workBook.entity;

import java.util.Date;

public class WorkbookPlanTaskVo {
	// ==============================Fields========================================
	private String id;//主键
	private String name;//工作薄名称
	private String workbookUrl;//工作薄路径
	private Date createdAt;//创建时间
	private Date updatedAt;//修改时间
	private String ownerId;//所属人
	private String ownerName;//所属人名称
	private String projectId;//项目
	private String projectName;//项目名
	private String siteId;//站点ID
	//~
	private String releaseId;//发布表ID
	private String siteName;
	private String releaseProjectId;//发布场所	varchar(255)	项目
	private String releaseProjectName;//发布场所	varchar(255)	项目
	private Integer releaseState;//发布状态	int	1:审核中, -1驳回, 2已通过 | 找不到关联，0未申请
	private String auditReason;
	private String auditorName;//审核人名称
	
	private Integer times;
	private String createdTime;
	private String updatedTime;
	private String dispatchState;  //调度状态
	private String taskState;		//任务状态
	private String refreshState;	//刷新状态
	private String creator;			//创建者
	private String updator;			//更新者
	private String taskRefTime;		//任务刷新时间
	private String workbookRefTime;	//工作薄刷新时间
	private String refreshTime;		//刷新时间
	private Integer refreshFreq;		//刷新频率
	private String tablesIds;		//tables表的多个id
	private String dbAndTables;		//多个 数据库.表名,数据库.表名
	
	// ==============================Methods==========================================

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkbookUrl() {
		return workbookUrl;
	}

	public void setWorkbookUrl(String workbookUrl) {
		this.workbookUrl = workbookUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public Integer getReleaseState() {
		return releaseState;
	}

	public void setReleaseState(Integer releaseState) {
		this.releaseState = releaseState;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getDispatchState() {
		return dispatchState;
	}

	public void setDispatchState(String dispatchState) {
		this.dispatchState = dispatchState;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getRefreshState() {
		return refreshState;
	}

	public void setRefreshState(String refreshState) {
		this.refreshState = refreshState;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getTaskRefTime() {
		return taskRefTime;
	}

	public void setTaskRefTime(String taskRefTime) {
		this.taskRefTime = taskRefTime;
	}

	public String getWorkbookRefTime() {
		return workbookRefTime;
	}

	public void setWorkbookRefTime(String workbookRefTime) {
		this.workbookRefTime = workbookRefTime;
	}

	public String getReleaseProjectId() {
		return releaseProjectId;
	}

	public void setReleaseProjectId(String releaseProjectId) {
		this.releaseProjectId = releaseProjectId;
	}

	public String getReleaseProjectName() {
		return releaseProjectName;
	}

	public void setReleaseProjectName(String releaseProjectName) {
		this.releaseProjectName = releaseProjectName;
	}

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Integer getRefreshFreq() {
		return refreshFreq;
	}

	public void setRefreshFreq(Integer refreshFreq) {
		this.refreshFreq = refreshFreq;
	}

	public String getTablesIds() {
		return tablesIds;
	}

	public void setTablesIds(String tablesIds) {
		this.tablesIds = tablesIds;
	}

	public String getDbAndTables() {
		return dbAndTables;
	}

	public void setDbAndTables(String dbAndTables) {
		this.dbAndTables = dbAndTables;
	}
	
	
}
