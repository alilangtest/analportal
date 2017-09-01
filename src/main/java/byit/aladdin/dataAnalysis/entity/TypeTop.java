package byit.aladdin.dataAnalysis.entity;

import java.io.Serializable;

/**
 * 报表统计分析实体
 * @author yangfan
 *
 */
public class TypeTop implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;//TOP_ID uuid
	private String reportId;//REPORT_ID 报表id
	private String reportName;//REPORT_NAME 报表名称
	private int topType;//TOP_TYPE 类型
	private String operationTime;//OPERATION_TIME 操作时间
	private String operationUser;//OPERATION_USER 操作人 
	private int ranking;//排名数量
	
	private String ck;//访问
	private String sc;//收藏
	private String pl;//评论
	private String dz;//点赞
	private String fx;//分享
	
	private String publisper;

	
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getTopType() {
		return topType;
	}
	public void setTopType(int topType) {
		this.topType = topType;
	}
	
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	public String getCk() {
		return ck;
	}
	public void setCk(String ck) {
		this.ck = ck;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getDz() {
		return dz;
	}
	public void setDz(String dz) {
		this.dz = dz;
	}
	public String getFx() {
		return fx;
	}
	public void setFx(String fx) {
		this.fx = fx;
	}
	public String getPublisper() {
		return publisper;
	}
	public void setPublisper(String publisper) {
		this.publisper = publisper;
	}
}
