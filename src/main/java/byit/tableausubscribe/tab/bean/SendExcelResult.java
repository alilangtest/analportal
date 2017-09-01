package byit.tableausubscribe.tab.bean;



public class SendExcelResult{
	@Override
	public String toString() {
		return "SendExcelResult [tableId=" + tableId + ", emailId=" + emailId
				+ ", sendTimeExcel=" + sendTimeExcel + ", sendStateExcel="
				+ sendStateExcel + "]";
	}
	private String tableId;//报表
	private String emailId;//邮件
	private String sendTimeExcel;//发送时间
	private String sendStateExcel;//发送状态
	private String excelTableId;//subscribe_excel_report表id
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getSendTimeExcel() {
		return sendTimeExcel;
	}
	public void setSendTimeExcel(String sendTimeExcel) {
		this.sendTimeExcel = sendTimeExcel;
	}
	public String getSendStateExcel() {
		return sendStateExcel;
	}
	public void setSendStateExcel(String sendStateExcel) {
		this.sendStateExcel = sendStateExcel;
	}
	public String getExcelTableId() {
		return excelTableId;
	}
	public void setExcelTableId(String excelTableId) {
		this.excelTableId = excelTableId;
	}
}
