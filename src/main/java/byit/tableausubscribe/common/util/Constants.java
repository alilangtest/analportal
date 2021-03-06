package byit.tableausubscribe.common.util;



public class Constants {
	// tabIp
	//public static final String TABIP = "10.17.2.12:80";//生产
	//public static final String TABIP = "25.0.89.91:80";//测试
	// tabUser
	//public static final String TABUSER = "admin";
	//间隔符
	public static final String DELIMITER=",";
	
	//提前发送报表的分钟间隔
	public static final long ADVANCEMIN=3L*60l*1000L;
	
	//十分钟的间隔（10L表示十分钟），通过Date的getTime()来相加减
	public static final long TENMIN=10L*60l*1000L;
	//提前2分钟发送报表
	//public static final long TWOMIN=2L*60l*1000L;
	
	
	//5分钟
	//public static final long FIVEMIN=5l*60L*60l*1000L;
	//6分钟
	public static final long SIXMIN=6l*60l*1000L;
	
	//如果当天没有数据，会间隔10分钟扫描一次数据，但是一超过23:00就不会再扫描
	public static final String STOPSCANTASKBEGIN="23:00:00";
	
	//当天更改发送状态的时间
	public static final String MODIFYSENDSTATETIME="06:00:00";
	
	//当天不进行更改发送状态的时间
	public static final String DONOTMODIFYSENDSTATETIME="06:10:00";
	
	//系统操作员
	public static final String USERID="sys";
	
	public static final String NO_DATA_SEND_INFO="无数据请忽略";
	
	public static final String NO_MOREDATA_SEND_INFO="数据过大";
	
	public static final String ERROR_DATE_SEND_INFO="错误";
	
	public static final String SENDED="1";//发送状态为已发送
	public static final String SENDDNS="0";//发送状态为未发送
	
	//excel的筛选条件
	public static final String EXCEL_SCR_SEC_KEY="2";
	public static final String EXCEL_SCR_SEC_VAL="存在前一日数据";
	
	public static final String EXCEL_SCR_THR_KEY="3";
	public static final String EXCEL_SCR_THR_VAL="bi_chk_account_end";
	
	public static final String EXCEL_SCR_FOR_KEY="4";
	public static final String EXCEL_SCR_FOR_VAL="bi_end_check";
	
	public static final String EXCEL_SCR_FIV_KEY="5";
	public static final String EXCEL_SCR_FIV_VAL="bi_cl_end_check";
	
	public static final String EXCEL_SCR_SIX_KEY="6";
	public static final String EXCEL_SCR_SIX_VAL="bi_on_end_check";
	
	public static final String EXCEL_SCR_SEV_KEY="7";
	public static final String EXCEL_SCR_SEV_VAL="bi_cuishou_end_check";
	
	public static final String EXCEL_SCR_EGT_KEY="8";
	public static final String EXCEL_SCR_EGT_VAL="bi_ear_end_check";
	
	
}
