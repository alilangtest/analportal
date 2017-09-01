package byit.tableausubscribe.tab.bean;


/**
 * 字段选择
 * <!-- 	<column>
		<tableId>DM_D_YGNEW_CUST,DM_F_PUBLISH_APP</tableId>
		<checkedColumnID id="DM_D_YGNEW_CUST">
			<isChecked>DATA_DATE,REG_CUST_NUM</isChecked>
		</checkedColumnID>
		<checkedColumnID id="DM_D_YGNEW_CUST">
			<isChecked>DATA_DT</isChecked>
		</checkedColumnID>
	</column> -->
 */
public class ExcelColumnSubscribe{
	
	private String tableId;
	private String checkedColumnId;
	private String isChecked;
	private String addDate;
	private String userId;//操作员
	private String excelTableId;//subscribe_excel_report表id
	private String selCond;//筛选条件（输入的文本域）
	
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getCheckedColumnId() {
		return checkedColumnId;
	}
	public void setCheckedColumnId(String checkedColumnId) {
		this.checkedColumnId = checkedColumnId;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "ExcelColumnSubscribe [tableId=" + tableId
				+ ", checkedColumnId=" + checkedColumnId + ", isChecked="
				+ isChecked + ", addDate=" + addDate + "]";
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSelCond() {
		return selCond;
	}
	public void setSelCond(String selCond) {
		this.selCond = selCond;
	}
	public String getExcelTableId() {
		return excelTableId;
	}
	public void setExcelTableId(String excelTableId) {
		this.excelTableId = excelTableId;
	}
	
}
