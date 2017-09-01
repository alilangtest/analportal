package byit.tableausubscribe.tab.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import byit.tableausubscribe.common.util.Constants;
import byit.tableausubscribe.tab.bean.ExcelColumnSubscribe;
import byit.tableausubscribe.tab.bean.ExcelSubscribe;
import byit.tableausubscribe.tab.dao.PushExcelColumnDao;
import byit.tableausubscribe.tab.db.DB;
import byit.tableausubscribe.tab.db.DbSql;

public class SendExcelOper {
	private static final Logger logger = Logger.getLogger(SendExcelOper.class);
	
	public static int sendExcel(ExcelSubscribe excelSubscribe) throws Exception{
		Connection conn = null;
		if (conn == null) {
			try {
				conn = DB.getDmConn(); //DBConnection.getConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PushExcelColumnDao excelColumnDao=new PushExcelColumnDao();
		int sendExcel = 0;
		String sendExcelString = "";
		List<Map<String, String>> excelList = new ArrayList<Map<String, String>>();//excel表
		Map<String, String> excelMap = new HashMap<String, String>();//excel表
		String screening = excelSubscribe.getScreening();
		String tableId = excelSubscribe.getTableId();
		String[] tableIdList = tableId.split(",");
		String isCheckedString = "";
		List<ExcelColumnSubscribe> excelColSubscribeList=excelColumnDao.getExcelSubscribeById(tableId);
		String isChecked = "";
		for (int i = 0; i < excelColSubscribeList.size(); i++) {
			isChecked=excelColSubscribeList.get(i).getIsChecked();
			isCheckedString=isCheckedString+"~"+isChecked;
		}
		isCheckedString = isCheckedString.substring(1,isCheckedString.length());
		String[] isCheckedArray =  isCheckedString.split("~");
		
		//如果选择的是“bi_chk_account_end”、“bi_end_check”、“bi_cl_end_check”、“bi_on_end_check”、“bi_cuishou_end_check”、“bi_ear_end_check”等
		if(  Constants.EXCEL_SCR_THR_KEY.equals(screening) || Constants.EXCEL_SCR_FOR_KEY.equals(screening) 
		  || Constants.EXCEL_SCR_FIV_KEY.equals(screening) || Constants.EXCEL_SCR_SIX_KEY.equals(screening)  
		  || Constants.EXCEL_SCR_SEV_KEY.equals(screening) || Constants.EXCEL_SCR_EGT_KEY.equals(screening) ){
			logger.debug(excelSubscribe.getTableId()+"选择的screening为"+screening);
			String screeningExcel = "";
			if(Constants.EXCEL_SCR_THR_KEY.equals(screening)){
				screeningExcel = Constants.EXCEL_SCR_THR_VAL;
			}else if(Constants.EXCEL_SCR_FOR_KEY.equals(screening)){
				screeningExcel = Constants.EXCEL_SCR_FOR_VAL;
			}else if(Constants.EXCEL_SCR_FIV_KEY.equals(screening)){
				screeningExcel = Constants.EXCEL_SCR_FIV_VAL;
			}else if(Constants.EXCEL_SCR_SIX_KEY.equals(screening)){
				screeningExcel = Constants.EXCEL_SCR_SIX_VAL;
			}else if(Constants.EXCEL_SCR_SEV_KEY.equals(screening)){
				screeningExcel = Constants.EXCEL_SCR_SEV_VAL;
			}else if(Constants.EXCEL_SCR_EGT_KEY.equals(screening)){
				screeningExcel = Constants.EXCEL_SCR_EGT_VAL;
			}
			String excelSql="select count(*) as count from comm.comm_rpt_end_check where node = '"+screeningExcel+"' and to_char(currdt,'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd')";
			logger.info("查询前一日数据的sql为："+excelSql);
			//TODO。上线后需要使用上面的sql语句，bi_cuishou_end_check，bi_ear_end_check测试库中无数据
			//String excelSql="select count(*) as count from comm.comm_rpt_end_check where node = '"+screeningExcel+"' and to_char(currdt,'yyyy-mm-dd') = to_char(sysdate-177,'yyyy-mm-dd')";
			//System.out.println("-----------------excelSql-----------------"+excelSql);
			excelList = DbSql.findNativeSQL(conn, excelSql, null);
			logger.debug(excelSubscribe.getTableId()+"的excelList："+excelList);
			for(int i=0;i<excelList.size();i++){
				excelMap = (Map<String, String>) excelList.get(i);
				sendExcelString = String.valueOf(excelMap.get("count"));
			}
			sendExcel = Integer.parseInt(sendExcelString);
			logger.debug(excelSubscribe.getTableId()+"的sendExcel为："+sendExcel);
		}else if(Constants.EXCEL_SCR_SEC_KEY.equals(screening)) {//如果选择的是“存在前一日数据”
			logger.debug(excelSubscribe.getTableId()+"选择的screening为："+screening);
			for(int t=0;t<tableIdList.length;t++){
				String[] isCheckedColumn = isCheckedArray[t].split(",");
				
				//存在前一日数据
				String excelSql="select count(*) as count from "+tableIdList[t]+" where to_char("+isCheckedColumn[0]+",'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd')";
				logger.info("查询前一日数据的sql为："+excelSql);
				//System.out.println("-----------------excelSql-----------------"+excelSql);
				excelList = DbSql.findNativeSQL(conn, excelSql, null);
				logger.debug("excelList："+excelList);
				for(int i=0;i<excelList.size();i++){
					excelMap = (Map<String, String>) excelList.get(i);
					sendExcelString = String.valueOf(excelMap.get("count"));
				}
				sendExcel = Integer.parseInt(sendExcelString);
				logger.debug(excelSubscribe.getTableId()+"的sendExcel为："+sendExcel);
				if(sendExcel == 0){
					//只要有一张表的数据显示前一天的数据未到 即跳出循环
					break;
				}
			}
		}
		
		//System.out.println("----------sendExcelString:"+sendExcelString+"-------------sendExcel:"+sendExcel);
		return sendExcel;
	}
//	public static void main(String[] args) throws Exception {		
//		int sendExcel = 0;
//		Connection conn = null;
//		if (conn == null) {
//			try {				
//				conn = DBConnection.getConnection();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		sendExcel = sendExcel(conn);
//		System.out.println("sendExcel："+sendExcel);
//		if(sendExcel>0){
//			//当天跑批有数据
//			System.out.println("有数据sendExcel"+sendExcel);
//		}else{
//			System.out.println("当天数据未到，不能发送该条信息");
//		}
//	}
}
