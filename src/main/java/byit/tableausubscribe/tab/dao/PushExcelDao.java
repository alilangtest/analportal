package byit.tableausubscribe.tab.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.osdp.base.security.UserHolder;
import byit.tableausubscribe.common.util.Constants;
import byit.tableausubscribe.tab.bean.ExcelColumnSubscribe;
import byit.tableausubscribe.tab.bean.ExcelSubscribe;
import byit.tableausubscribe.tab.bean.SendExcelResult;
import byit.tableausubscribe.tab.db.DB;
import byit.tableausubscribe.tab.bean.SubscribeExcelType;
import byit.utils.JsonUtil;
import byit.utils.Tools;


/** 
* Comments:操作XML文件---ok
*/
@Service
@Transactional
public class PushExcelDao {
	private static final Logger logger = Logger.getLogger(PushExcelDao.class);
	/**
	 * 
		 *@author lisw
		 *@Description: 查询列表
		 *@creatTime:2017年6月3日 下午7:20:05 
		 *@return:@param searchValue
		 *@return:@return List<ExcelSubscribe>
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public List<ExcelSubscribe> getExcelSubscribeList(String searchValue){
		List<ExcelSubscribe> list=new ArrayList<ExcelSubscribe>();
		StringBuffer sql=new StringBuffer();
		sql.append("select row_number() over(ORDER BY create_time) seqnum,id,mail_title_excel,table_id,table_name,screening,send_type_excel,send_time_excel, ");
		sql.append(" send_state_excel,excel_mail,creator_id,create_time,updater_id,update_time,status,temp_send_time,is_today_send,is_send_prop ");
		sql.append(" from subscribe_excel_report where 1=1 and status=1 ");
		
		if(Tools.isNotEmpty(searchValue)){
			sql.append(" and (excel_mail like '%'||?||'%'  or send_state_excel like '%'||?||'%'  or send_time_excel like '%'||?||'%' ");
			sql.append("   or table_id like '%'||?||'%'  or table_name like '%'||?||'%' )  ");
		}
		sql.append(" ORDER BY create_time ");
		logger.info("getExcelSubscribeList的sql:"+sql);
		Connection conn=DB.getPortalConn();
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try {
			pstmt=conn.prepareStatement(sql.toString());
			if(Tools.isNotEmpty(searchValue)){
				searchValue=Tools.formatWeekToNo(searchValue);
				pstmt.setString(1, searchValue);
				pstmt.setString(2, searchValue);
				pstmt.setString(3, searchValue);
				pstmt.setString(4, searchValue);
				pstmt.setString(5, searchValue);
			}
			rs=	pstmt.executeQuery();
			while( rs.next()){
				ExcelSubscribe excelSubscribe=new ExcelSubscribe();
				excelSubscribe.setId(rs.getString("id"));
				excelSubscribe.setSeqNum(rs.getString("seqnum"));
				excelSubscribe.setMailTitleExcel(rs.getString("mail_title_excel"));
				excelSubscribe.setTableId(rs.getString("table_id"));
				excelSubscribe.setTableName(rs.getString("table_name"));
				excelSubscribe.setScreening(rs.getString("screening"));
				String sendTypeExcel=rs.getString("send_type_excel");
				String sendTypeNameExcel=SubscribeExcelType.getName(sendTypeExcel);
				excelSubscribe.setSendTypeExcel(sendTypeExcel);
				excelSubscribe.setSendTypeNameExcel(sendTypeNameExcel);
				String DateStr=rs.getString("temp_send_time");
				if(Tools.isNotEmpty(DateStr) ){
					excelSubscribe.setTempSendTime(Tools.formatDateStrMin(DateStr));
				}
				excelSubscribe.setIsTodaySend(rs.getString("is_today_send"));
				excelSubscribe.setIsSendProp(rs.getString("is_send_prop"));
				excelSubscribe.setSendTimeExcel(rs.getString("send_time_excel"));
				excelSubscribe.setSendStateExcel(rs.getString("send_state_excel"));
				excelSubscribe.setExcelMails(rs.getString("excel_mail"));
				list.add(excelSubscribe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(conn, pstmt, rs);
		}
		return list;
	}
	
	
	/**
	 * 
		 *@author lisw
		 *@Description: 查询未发送的报表
		 *@creatTime:2017年6月3日 下午7:20:05 
		 *@return:@param searchValue
		 *@return:@return List<ExcelSubscribe>
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public List<ExcelSubscribe> getDNSExcelSubscribeList(){
		List<ExcelSubscribe> list=new ArrayList<ExcelSubscribe>();
		StringBuffer sql=new StringBuffer();
		sql.append("select row_number() over(ORDER BY create_time) seqnum,id,mail_title_excel,table_id,table_name,screening,send_type_excel,send_time_excel, ");
		sql.append(" send_state_excel,excel_mail,creator_id,create_time,updater_id,update_time,status,temp_send_time,is_today_send,is_send_prop ");
		sql.append(" from subscribe_excel_report where 1=1 and status=1 and send_state_excel=? ");
		
		sql.append(" ORDER BY create_time ");
		logger.info("getDNSExcelSubscribeList的sql:"+sql);
		Connection conn=DB.getPortalConn();
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try {
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,Constants.SENDDNS);
			rs=	pstmt.executeQuery();
			while( rs.next()){
				ExcelSubscribe excelSubscribe=new ExcelSubscribe();
				excelSubscribe.setSeqNum(rs.getString("seqnum"));
				excelSubscribe.setMailTitleExcel(rs.getString("mail_title_excel"));
				excelSubscribe.setTableId(rs.getString("table_id"));
				excelSubscribe.setTableName(rs.getString("table_name"));
				excelSubscribe.setScreening(rs.getString("screening"));
				String sendTypeExcel=rs.getString("send_type_excel");
				String sendTypeNameExcel=SubscribeExcelType.getName(sendTypeExcel);
				excelSubscribe.setSendTypeExcel(sendTypeExcel);
				excelSubscribe.setSendTypeNameExcel(sendTypeNameExcel);
				String DateStr=rs.getString("temp_send_time");
				if(Tools.isNotEmpty(DateStr) ){
					excelSubscribe.setTempSendTime(Tools.formatDateStrMin(DateStr));
				}
				excelSubscribe.setIsTodaySend(rs.getString("is_today_send"));
				excelSubscribe.setIsSendProp(rs.getString("is_send_prop"));
				excelSubscribe.setSendTimeExcel(rs.getString("send_time_excel"));
				excelSubscribe.setSendStateExcel(rs.getString("send_state_excel"));
				excelSubscribe.setExcelMails(rs.getString("excel_mail"));
				list.add(excelSubscribe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(conn, pstmt, rs);
		}
		return list;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 依据tableId查询ExcelSubscribe详细信息
		 *@creatTime:2017年6月3日 下午9:12:18 
		 *@return:@param tableId
		 *@return:@return ExcelSubscribe
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public ExcelSubscribe getExcelSubscribeById(String id){
		ExcelSubscribe excelSubscribe=new ExcelSubscribe();
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select ser.id,ser.mail_title_excel,ser.table_id,ser.table_name,ser.screening,ser.send_type_excel,ser.send_time_excel, ");
		sql.append(" ser.send_state_excel,ser.excel_mail,ser.creator_id,ser.create_time,ser.updater_id,ser.update_time,ser.status,escr.sel_cond, ");
		sql.append(" escr.is_checked,escr.add_date,escr.sub_table_id,escr.sub_table_name ");
		sql.append("  from subscribe_excel_report ser LEFT JOIN subscribe_excelcol_report escr on ser.table_id=escr.table_id ");
		//sql.append(" where 1=1 and ser.status = 1 and escr.status=1 and ser.table_id =? ");
		sql.append(" where 1=1 and ser.status = 1 and escr.status=1 and ser.id =? ");
		logger.info("getExcelSubscribeById的sql:"+sql);
		try {
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			String tTables="";
			while(rs.next()){
				String tTableName="";
				String tIsChecked="";
				tTableName=rs.getString("sub_table_id")+"-"+rs.getString("sub_table_name");
				tIsChecked=rs.getString("is_checked");
				String tables="{\"tableName\":\""+tTableName+"\",\"isChecked\":\""+tIsChecked+"\"},";
				tTables=tTables+tables;
				excelSubscribe.setId(rs.getString("id"));
				excelSubscribe.setMailTitleExcel(rs.getString("mail_title_excel"));
				excelSubscribe.setTableId(rs.getString("table_id"));
				excelSubscribe.setTableName(rs.getString("table_name"));
				excelSubscribe.setScreening(rs.getString("screening"));
				String sendTypeExcel=rs.getString("send_type_excel");
				String sendTypeNameExcel=SubscribeExcelType.getName(sendTypeExcel);
				excelSubscribe.setSendTypeExcel(sendTypeExcel);
				excelSubscribe.setSendTypeNameExcel(sendTypeNameExcel);
				excelSubscribe.setSendTimeExcel(rs.getString("send_time_excel"));
				excelSubscribe.setSendStateExcel(rs.getString("send_state_excel"));
				excelSubscribe.setExcelMails(rs.getString("excel_mail"));
				excelSubscribe.set_selCond(rs.getString("sel_cond"));
				excelSubscribe.set_addDate(rs.getString("add_date"));
				excelSubscribe.setIsChecked(rs.getString("is_checked"));
			}
			if(Tools.isNotEmpty(tTables)){
				tTables=tTables.substring(0, tTables.length()-1);
			}
			excelSubscribe.setTables(tTables);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return excelSubscribe;
	}
	
	public boolean addExcelSubscribe(ExcelSubscribe excel){
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		//获取当前登录人id
		 //String uid = UserHolder.getId();
		 
		 List<String> mailListExcel = excel.getMailListExcel();
		 String mail=""; 
		 for (int i = 0; i < mailListExcel.size(); i++) {
			if(i==0){
					mail=mailListExcel.get(i);
			}else{
				mail+=Constants.DELIMITER+mailListExcel.get(i);
			}
			 
		 }
		
		try{
			StringBuffer sql=new StringBuffer();
			sql.append("insert into  subscribe_excel_report ");
			sql.append(" (id,mail_title_excel,table_id,table_name,screening,send_type_excel,send_time_excel, ");
			sql.append(" send_state_excel,excel_mail,creator_id,create_time,updater_id,update_time,status) ");
			sql.append(" values ");
			sql.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			logger.info("addExcelSubscribe的sql:"+sql);
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2,excel.getMailTitleExcel());
			pstmt.setString(3,excel.getTableId());
			pstmt.setString(4,excel.getTableName());
			pstmt.setString(5,excel.getScreening());
			pstmt.setString(6,excel.getSendTypeExcel());
			pstmt.setString(7,excel.getSendTimeExcel());
			pstmt.setString(8,excel.getSendStateExcel());
			pstmt.setString(9,mail);
			pstmt.setString(10,excel.getUserId());
			pstmt.setString(11,Tools.getCurrFormatTimeGen());
			pstmt.setString(12,excel.getUserId());
			pstmt.setString(13,Tools.getCurrFormatTimeGen());
			pstmt.setString(14,"1");
			pstmt.execute();
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		return flag;
		
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 同时添加excel主表和字段表
		 *@creatTime:2017年6月16日 下午1:41:16 
		 *@return:@param excel
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public boolean addExcelAndCol(ExcelSubscribe excel,ExcelColumnSubscribe column){
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		PreparedStatement colPstmt=null;
		 
		 List<String> mailListExcel = excel.getMailListExcel();
		 String mail=""; 
		 for (int i = 0; i < mailListExcel.size(); i++) {
			if(i==0){
					mail=mailListExcel.get(i);
			}else{
				mail+=Constants.DELIMITER+mailListExcel.get(i);
			}
			 
		 }
		 
		//将excel中table的中名称、已选字段由json字符串转为map结构
		List<Map<String, String>> tList=JsonUtil.formatJsonToList(excel.getTables());
		
		//循环map，分别获取table英文名字符串、中文名字符串（都用，分割）
		String tableIds="";
		String tTableNames="";
		for (int i = 0; i < tList.size(); i++) {
			String tTablenames="";
			for (String key : tList.get(i).keySet()) {
				if("tableName".equals(key)){
					tTablenames=tList.get(i).get(key);
				}
			}
			String[] tTableIdAndName=tTablenames.split("-");
			tableIds=tableIds+tTableIdAndName[0]+",";
			tTableNames=tTableNames+tTableIdAndName[1]+",";
			
		}
		tableIds=tableIds.substring(0, tableIds.length()-1);
		tTableNames=tTableNames.substring(0, tTableNames.length()-1);
		try{
			String excelTable_id=UUID.randomUUID().toString();
			//保存excel主表
			StringBuffer sql=new StringBuffer();
			sql.append("insert into  subscribe_excel_report ");
			sql.append(" (id,mail_title_excel,table_id,table_name,screening,send_type_excel,send_time_excel, ");
			sql.append(" send_state_excel,excel_mail,creator_id,create_time,updater_id,update_time,status) ");
			sql.append(" values ");
			sql.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			logger.info("addExcelAndCol中insert到subscribe_excel_report的sql:"+sql);
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,excelTable_id);
			pstmt.setString(2,excel.getMailTitleExcel());
			pstmt.setString(3,tableIds);
			pstmt.setString(4,tTableNames);
			pstmt.setString(5,excel.getScreening());
			pstmt.setString(6,excel.getSendTypeExcel());
			pstmt.setString(7,excel.getSendTimeExcel());
			pstmt.setString(8,excel.getSendStateExcel());
			pstmt.setString(9,mail);
			pstmt.setString(10,excel.getUserId());
			pstmt.setString(11,Tools.getCurrFormatTimeGen());
			pstmt.setString(12,excel.getUserId());
			pstmt.setString(13,Tools.getCurrFormatTimeGen());
			pstmt.setString(14,"1");
			pstmt.execute();
			
			//循环table。分别将table的信息保存在字段表中
			for (int i = 0; i < tList.size(); i++) {
				String tableNames="";
				String tableId="";
				String tableName="";
				String isChecked="";
				for (String key : tList.get(i).keySet()) {
					if("tableName".equals(key)){
						tableNames=tList.get(i).get(key);
					}
					if("isChecked".equals(key)){
						isChecked=tList.get(i).get(key);
					}
				}
				
				String[] tableIdAndName=tableNames.split("-");
				tableId=tableIdAndName[0];
				tableName=tableIdAndName[1];
				
				//保存excel字段表
				StringBuffer colSql=new StringBuffer();
				colSql.append("insert into  subscribe_excelcol_report ");
				colSql.append(" (id,table_id,sel_cond,is_checked,add_date,creator_id,create_time,updater_id,"
								+ "update_time,status,sub_table_id,sub_table_name,excel_table_id) ");
				colSql.append(" values ");
				colSql.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				logger.info("addExcelAndCol中insert到subscribe_excelcol_report的sql:"+colSql);
				colPstmt=conn.prepareStatement(colSql.toString());
				colPstmt.setString(1, UUID.randomUUID().toString());
				colPstmt.setString(2,tableIds);
				colPstmt.setString(3,column.getSelCond());
				colPstmt.setString(4,isChecked);
				colPstmt.setString(5,column.getAddDate());
				colPstmt.setString(6,column.getUserId());
				colPstmt.setString(7,Tools.getCurrFormatTimeGen());
				colPstmt.setString(8,column.getUserId());
				colPstmt.setString(9,Tools.getCurrFormatTimeGen());
				colPstmt.setString(10,"1");
				colPstmt.setString(11,tableId);
				colPstmt.setString(12,tableName);
				colPstmt.setString(13,excelTable_id);
				colPstmt.execute();
			}
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
			DB.close(colPstmt);
		}
		return flag;
		
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 修改excel主表
		 *@creatTime:2017年6月16日 下午2:06:22 
		 *@return:@param excel
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public boolean updateExcelSubscribe(ExcelSubscribe excel){
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		//获取当前登录人id
		 
		 List<String> mailListExcel = excel.getMailListExcel();
		 String mail=""; 
		 for (int i = 0; i < mailListExcel.size(); i++) {
			if(i==0){
					mail=mailListExcel.get(i);
			}else{
				mail+=Constants.DELIMITER+mailListExcel.get(i);
			}
			 
		 }
		StringBuffer sql=new StringBuffer();
		sql.append("update  subscribe_excel_report ");
		sql.append(" set mail_title_excel=?,screening=?,send_type_excel=?,send_time_excel=?, ");
		sql.append(" send_state_excel=?,excel_mail=?,updater_id=?,update_time=?,temp_send_time=?,is_today_send=? ");
		sql.append(" where table_id=? ");
		logger.info("updateExcelSubscribe的sql:"+sql);
		try{
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,excel.getMailTitleExcel());
			pstmt.setString(2,excel.getScreening());
			pstmt.setString(3,excel.getSendTypeExcel());
			pstmt.setString(4,excel.getSendTimeExcel());
			pstmt.setString(5,excel.getSendStateExcel());
			pstmt.setString(6,mail);
			pstmt.setString(7,excel.getUserId());
			pstmt.setString(8,Tools.getCurrFormatTimeGen());
			pstmt.setString(9,excel.getPtempSendTime());
			pstmt.setString(10,excel.getIsTodaySend());
			pstmt.setString(11,excel.getTableId());
			pstmt.execute();
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		return flag;
		
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 修改excel主表和字段表
		 *@creatTime:2017年6月16日 下午2:06:43 
		 *@return:@param excel
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@SuppressWarnings("unused")
	public boolean updateExcelAndColSubscribe(ExcelSubscribe excel,ExcelColumnSubscribe column){
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		PreparedStatement colPstmt=null;
		
		String excelTableId=excel.getId();
		//获取当前登录人id
		 List<String> mailListExcel = excel.getMailListExcel();
		 String mail=""; 
		 for (int i = 0; i < mailListExcel.size(); i++) {
			if(i==0){
					mail=mailListExcel.get(i);
			}else{
				mail+=Constants.DELIMITER+mailListExcel.get(i);
			}
			 
		 }
		 
		//将excel中table的中名称、已选字段由json字符串转为map结构
		List<Map<String, String>> tList=JsonUtil.formatJsonToList(excel.getTables());
			
		//循环map，分别获取table英文名字符串、中文名字符串（都用，分割）
		String tableIds="";
		String tTableNames="";
		for (int i = 0; i < tList.size(); i++) {
			String tTablenames="";
			for (String key : tList.get(i).keySet()) {
				if("tableName".equals(key)){
					tTablenames=tList.get(i).get(key);
				}
			}
			String[] tTableIdAndName=tTablenames.split("-");
			tableIds=tableIds+tTableIdAndName[0]+",";
			tTableNames=tTableNames+tTableIdAndName[1]+",";
			
		}
		tableIds=tableIds.substring(0, tableIds.length()-1);
		tTableNames=tTableNames.substring(0, tTableNames.length()-1);
		
		try{
			
			StringBuffer sql=new StringBuffer();
			sql.append("update  subscribe_excel_report ");
			sql.append(" set mail_title_excel=?,screening=?,send_type_excel=?,send_time_excel=?, ");
			sql.append(" send_state_excel=?,excel_mail=?,updater_id=?,update_time=? ");
			//sql.append(" where table_id=? ");
			sql.append(" where id=? ");
			logger.info("updateExcelAndColSubscribe中update的subscribe_excel_report的sql:"+sql);
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,excel.getMailTitleExcel());
			pstmt.setString(2,excel.getScreening());
			pstmt.setString(3,excel.getSendTypeExcel());
			pstmt.setString(4,excel.getSendTimeExcel());
			pstmt.setString(5,excel.getSendStateExcel());
			pstmt.setString(6,mail);
			pstmt.setString(7,excel.getUserId());
			pstmt.setString(8,Tools.getCurrFormatTimeGen());
			//pstmt.setString(9,tableIds);
			pstmt.setString(9,excelTableId);
			pstmt.execute();
			
			//TODO，改处注释代码勿删除。boolean isCheckedColumnId=Tools.isNotEmpty(excelCol.getCheckedColumnId());
			
			//循环修改table的字段表
			for (int i = 0; i < tList.size(); i++) {
				String tableNames="";
				String tableId="";
				String isChecked="";
				for (String key : tList.get(i).keySet()) {
					if("tableName".equals(key)){
						tableNames=tList.get(i).get(key);
					}
					if("isChecked".equals(key)){
						isChecked=tList.get(i).get(key);
					}
				}
				
				String[] tableIdAndName=tableNames.split("-");
				tableId=tableIdAndName[0];
			
				boolean isIsChecked=Tools.isNotEmpty(column.getIsChecked());
				StringBuffer colSql=new StringBuffer();
				colSql.append("update  subscribe_excelcol_report set ");
				colSql.append(" updater_id=?,update_time=?,add_date=?,sel_cond=? ");
				if(isIsChecked){
					colSql.append(" ,is_checked=? ");
				}
				//colSql.append(" where sub_table_id=? ");
				colSql.append(" where excel_table_id=? and status=1");
				logger.info("updateExcelAndColSubscribe中update的subscribe_excelcol_report的sql:"+colSql);
				colPstmt=conn.prepareStatement(colSql.toString());
				colPstmt.setString(1,column.getUserId());
				colPstmt.setString(2,Tools.getCurrFormatTimeGen());
				colPstmt.setString(3,column.getAddDate());
				colPstmt.setString(4,column.getSelCond());
				//isIsChecked不为空
				if(isIsChecked){
					colPstmt.setString(5,isChecked);
					colPstmt.setString(6,excelTableId);
				}else{//isIsChecked为空
					colPstmt.setString(5,excelTableId);
				}
				
				colPstmt.execute();
			}
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		return flag;
		
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 更新当天发送情况字段
		 *@creatTime:2017年6月11日 下午3:49:52 
		 *@return:@param report
		 *@return:@return String
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public  String updateSendTimeExcelSubscribe(ExcelSubscribe excelReport){
		String result=null;

		StringBuffer sql=new StringBuffer();
		 sql.append("update  subscribe_excel_report set ");
		 sql.append(" is_today_send =?,temp_send_time=?,is_send_prop=? ");
		 sql.append(" where table_id =? and status=1");
		 logger.info("updateSendTimeExcelSubscribe的sql:"+sql);
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 try {
			conn=DB.getPortalConn();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setString(1,excelReport.getIsTodaySend());
			 pstmt.setString(2,excelReport.getPtempSendTime());
			 pstmt.setString(3,excelReport.getIsSendProp());
			 pstmt.setString(4,excelReport.getTableId());
			 pstmt.execute();
			 result="修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			result="修改失败";
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		 return result;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 更新当天发送状态
		 *@creatTime:2017年6月11日 下午3:49:52 
		 *@return:@param report
		 *@return:@return String
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public  String updateSendStateExcelSubscribe(){
		String result=null;

		StringBuffer sql=new StringBuffer();
		 sql.append("update  subscribe_excel_report set ");
		 sql.append(" send_state_excel =? ");
		 sql.append(" where  status=1 and send_state_excel=? ");
		 logger.info("updateSendStateExcelSubscribe的sql:"+sql);
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 try {
			conn=DB.getPortalConn();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setString(1,Constants.SENDDNS);
			 pstmt.setString(2,Constants.SENDED);
			 pstmt.execute();
			 result="修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			result="修改失败";
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		 return result;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 删除，逻辑删除，修改状态即可
		 *@creatTime:2017年6月6日 上午11:52:48 
		 *@return:@param tableId
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public  boolean  deleteExcelConfig(String tableId)  {
		boolean result=false;
		 
		 StringBuffer sql=new StringBuffer();
		 sql.append("update subscribe_excel_report set ");
		 sql.append(" status=?,updater_id=?,update_time=?");
		 sql.append(" where table_id =?");
		 logger.info("deleteExcelConfig的sql:"+sql);
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 try {
			conn=DB.getPortalConn();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setString(1,"0");
			 pstmt.setString(2,UserHolder.getId());
			 pstmt.setString(3,Tools.getCurrFormatTimeGen());//update_time
			 pstmt.setString(4,tableId);//update_time
			 pstmt.execute();
			 result=true;
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		 return result;
	}
	
	
	/**
	 * 
		 *@author lisw
		 *@Description: 删除excel主表、字段表、结果表
		 *@creatTime:2017年6月16日 下午4:20:12 
		 *@return:@param tableId
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public  boolean  deleteExcelAndCol(String id)  {
		boolean result=false;
		 
		 Connection conn=DB.getPortalConn();;
		 PreparedStatement pstmt=null;
		 PreparedStatement colPstmt=null;
		 PreparedStatement resPstmt=null;
		 try {
			 
			 //删除主表
			 StringBuffer sql=new StringBuffer();
			 sql.append("update subscribe_excel_report set status=?,updater_id=?,update_time=?  where id =? and status=1");
			 logger.info("deleteExcelAndCol中del的subscribe_excel_report的sql:"+sql);
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setString(1,"0");
			 pstmt.setString(2,UserHolder.getId());
			 pstmt.setString(3,Tools.getCurrFormatTimeGen());//update_time
			 pstmt.setString(4,id);//update_time
			 pstmt.execute();
			 
			 //删除字段表
			 StringBuffer colSql=new StringBuffer();
			 colSql.append("update subscribe_excelcol_report set status=?,updater_id=?,update_time=?  where excel_table_id =? and status=1");
			 logger.info("deleteExcelAndCol中del的subscribe_excelcol_report的sql:"+sql);
			 colPstmt=conn.prepareStatement(colSql.toString());
			 colPstmt.setString(1,"0");
			 colPstmt.setString(2,UserHolder.getId());
			 colPstmt.setString(3,Tools.getCurrFormatTimeGen());//update_time
			 colPstmt.setString(4,id);//update_time
			 colPstmt.execute();
			 
			 //删除结果表
			 StringBuffer resSql=new StringBuffer();
			 resSql.append("update subscribe_excel_result set status=?,updater_id=?,update_time=?  where excel_table_id =? and status=1");
			 logger.info("deleteExcelAndCol中del的subscribe_excel_result的sql:"+sql);
			 resPstmt=conn.prepareStatement(resSql.toString());
			 resPstmt.setString(1,"0");
			 resPstmt.setString(2,UserHolder.getId());
			 resPstmt.setString(3,Tools.getCurrFormatTimeGen());//update_time
			 resPstmt.setString(4,id);//update_time
			 resPstmt.execute();
			 
			 result=true;
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
			DB.close(resPstmt);
			DB.close(colPstmt);
		}
		 return result;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description:删除，物理删除，直接从数据库删除数据
		 *@creatTime:2017年6月6日 上午11:53:08 
		 *@return:@param tableId
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public  boolean dropExcelConfig(String tableId)  {
		boolean result=false;
		 
		 StringBuffer sql=new StringBuffer();
		 sql.append("delete from subscribe_excel_report where table_id =?");
		 logger.info("dropExcelConfig的sql:"+sql);
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 try {
			conn=DB.getPortalConn();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setString(1,tableId);//update_time
			 pstmt.execute();
			 result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		 return result;
	}
	
	
	
	/**
	 * 
		 *@author lisw
		 *@Description: 删除excel发送结果
		 *@creatTime:2017年6月6日 上午11:57:40 
		 *@return:@param tableId
		 *@return:@return
		 *@return:@throws Exception boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public boolean dropSendExcelResult(String tableId)  throws Exception  {
		boolean result=false;
		 
		 StringBuffer sql=new StringBuffer();
		 sql.append("delete from subscribe_excel_result where table_id =?");
		 logger.info("dropSendExcelResult的sql:"+sql);
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 try {
			conn=DB.getPortalConn();
			 pstmt=conn.prepareStatement(sql.toString());
			 pstmt.setString(1,tableId);//update_time
			 pstmt.execute();
			 result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		 return result;
	}
	
	public boolean addExcelSubscribeResult(Map<String, List<SendExcelResult>> sendExcelResultMap){
		boolean flag=false;
		String tableId=null;
		String mails=null;
		String sendTime=null;
		String sendState=null;
		Connection conn=null;
		
		PreparedStatement pstmt=null;
		
		StringBuffer sql=new StringBuffer();
		sql.append("insert into  subscribe_excel_result ");
		sql.append(" (id,table_id,mails,send_time,send_state,creator_id,create_time,status,excel_table_id) ");
		sql.append(" values ");
		sql.append(" (?,?,?,?,?,?,?,?,?) ");
		 logger.info("addExcelSubscribeResult的sql:"+sql);
		//获取当前登录人id
		 String uid = "sys";
		for (String key : sendExcelResultMap.keySet()) {
			for (SendExcelResult ser : sendExcelResultMap.get(key)) {
				try {
					if (Tools.isNotEmpty(ser.getTableId())) {
						tableId = ser.getTableId();
					}
	
					if (Tools.isNotEmpty(ser.getEmailId())) {
						mails = ser.getEmailId();
						if (Tools.isNotEmpty(ser.getSendTimeExcel())) {
							sendTime = ser.getSendTimeExcel();
						}
						if (Tools.isNotEmpty(ser.getSendStateExcel())) {
							sendState = ser.getSendStateExcel();
						}
					}
					conn=DB.getPortalConn();
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1,UUID.randomUUID().toString());
			 	   pstmt.setString(2,tableId);
				   pstmt.setString(3,mails);
				   pstmt.setString(4,sendTime);
				   pstmt.setString(5,sendState);
				   pstmt.setString(6,uid);//creator_id
				   pstmt.setString(7,Tools.getCurrFormatTimeGen());//create_time
				   pstmt.setString(8,"1");
				   pstmt.setString(9,ser.getExcelTableId());
				   pstmt.execute();
				   flag=true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					DB.commit(conn);
					DB.close(conn, pstmt);
				}
			}
		}
		 
		return flag;
	}
	
	public List<SendExcelResult>  getExcelSendResultList(String id){
		List<SendExcelResult> list=new ArrayList<SendExcelResult>();
		
		StringBuffer sql=new StringBuffer();
		sql.append(" select id,table_id,mails,send_time,send_state from subscribe_excel_result  ");
		sql.append(" where excel_table_id=? and status=1  order by send_time desc ");
		 logger.info("getExcelSendResultList的sql:"+sql);
		 Connection conn=DB.getPortalConn();
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 try {
			
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,id);
			rs=	pstmt.executeQuery();
			while( rs.next()){
				SendExcelResult sendExcelResult=new SendExcelResult();
				sendExcelResult.setTableId(rs.getString("table_id"));
				sendExcelResult.setSendTimeExcel(rs.getString("send_time"));
				sendExcelResult.setSendStateExcel(rs.getString("send_state"));
				sendExcelResult.setEmailId(rs.getString("mails"));
				list.add(sendExcelResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(conn, pstmt, rs);
		}
		 return list;
	}
}
