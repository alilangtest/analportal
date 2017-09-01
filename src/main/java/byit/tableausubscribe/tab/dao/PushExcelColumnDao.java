package byit.tableausubscribe.tab.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import byit.osdp.base.security.UserHolder;
import byit.tableausubscribe.tab.bean.ExcelColumnSubscribe;
import byit.tableausubscribe.tab.db.DB;
import byit.utils.Tools;




/** 
* Comments:操作XML文件---ok
*/
@Component
public class PushExcelColumnDao {
	private static final Logger logger = Logger.getLogger(PushExcelColumnDao.class);
	
	//增
	public boolean addExcelColumnSubscribe(List<ExcelColumnSubscribe> list)throws Exception {
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		try {
			for (int i = 0; i < list.size(); i++) {
				StringBuffer sql=new StringBuffer();
				sql.append("insert into  subscribe_excelcol_report ");
				sql.append(" (id,table_id,sel_cond,is_checked,add_date,creator_id,create_time,updater_id,update_time,status) ");
				sql.append(" values ");
				sql.append(" (?,?,?,?,?,?,?,?,?,?) ");
				
				String tableId=list.get(i).getTableId();
				String selCond=	list.get(i).getSelCond();
				String isChecked=list.get(i).getIsChecked();
				String addDate=list.get(i).getAddDate();
				pstmt=conn.prepareStatement(sql.toString());
				pstmt.setString(1, UUID.randomUUID().toString());
				pstmt.setString(2,tableId);
				pstmt.setString(3,selCond);
				pstmt.setString(4,isChecked);
				pstmt.setString(5,addDate);
				pstmt.setString(6,list.get(i).getUserId());
				pstmt.setString(7,Tools.getCurrFormatTimeGen());
				pstmt.setString(8,list.get(i).getUserId());
				pstmt.setString(9,Tools.getCurrFormatTimeGen());
				pstmt.setString(10,"1");
				pstmt.execute();
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		return flag;
	}
	
	public boolean addExcelColumnSubscribe(ExcelColumnSubscribe ColumnSubscribe)throws Exception {
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		try {
				StringBuffer sql=new StringBuffer();
				sql.append("insert into  subscribe_excelcol_report ");
				sql.append(" (id,table_id,sel_cond,is_checked,add_date,creator_id,create_time,updater_id,update_time,status) ");
				sql.append(" values ");
				sql.append(" (?,?,?,?,?,?,?,?,?,?) ");
				
				String tableId=ColumnSubscribe.getTableId();
				String selCond=	ColumnSubscribe.getSelCond();
				String isChecked=ColumnSubscribe.getIsChecked();
				String addDate=ColumnSubscribe.getAddDate();
				pstmt=conn.prepareStatement(sql.toString());
				pstmt.setString(1, UUID.randomUUID().toString());
				pstmt.setString(2,tableId);
				pstmt.setString(3,selCond);
				pstmt.setString(4,isChecked);
				pstmt.setString(5,addDate);
				pstmt.setString(6,ColumnSubscribe.getUserId());
				pstmt.setString(7,Tools.getCurrFormatTimeGen());
				pstmt.setString(8,ColumnSubscribe.getUserId());
				pstmt.setString(9,Tools.getCurrFormatTimeGen());
				pstmt.setString(10,"1");
				pstmt.execute();
				flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		return flag;
	}
		
	//删
	public boolean deleteExcelColumnSubscribe(String tableId)  throws Exception  {
		boolean result=false;
		 
		 StringBuffer sql=new StringBuffer();
		 sql.append("update  subscribe_excelcol_report set ");
		 sql.append(" status=?,updater_id=?,update_time=?");
		 sql.append(" where table_id =?");
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
		 *@Description: 依据tableId查询subscribe_excel_result详细信息
		 *@creatTime:2017年6月3日 下午9:12:18 
		 *@return:@param tableId
		 *@return:@return ExcelSubscribe
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public List<ExcelColumnSubscribe> getExcelSubscribeById(String tableId){
		List<ExcelColumnSubscribe> excelColSubscribeList=new ArrayList<ExcelColumnSubscribe>();
		
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select id,table_id,sel_cond,is_checked,add_date, ");
		sql.append(" creator_id,create_time,updater_id,update_time,status ");
		sql.append(" from subscribe_excelcol_report where 1=1 and status=1 and table_id=? ");
		try {
			logger.debug("getExcelSubscribeById的sql为"+sql);
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,tableId);
			logger.debug("getExcelSubscribeById的sql为"+sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ExcelColumnSubscribe excelColSubscribe=new ExcelColumnSubscribe();
				excelColSubscribe.setTableId(rs.getString("table_id"));
				excelColSubscribe.setSelCond((rs.getString("sel_cond")));
				excelColSubscribe.setIsChecked(rs.getString("is_checked"));
				excelColSubscribe.setAddDate(rs.getString("add_date"));
				excelColSubscribeList.add(excelColSubscribe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt, rs);
		}
		return excelColSubscribeList;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 更新报表订阅.不要删除注释代码，
		 *@creatTime:2017年6月15日 下午6:55:34 
		 *@return:@param excel
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public boolean updateExcelCol(ExcelColumnSubscribe excelCol){
		boolean flag=false;
		Connection conn=DB.getPortalConn();
		PreparedStatement pstmt=null;
		//获取当前登录人id
		 
		//TODO，改处注释代码勿删除。boolean isCheckedColumnId=Tools.isNotEmpty(excelCol.getCheckedColumnId());
		boolean isIsChecked=Tools.isNotEmpty(excelCol.getIsChecked());
		StringBuffer sql=new StringBuffer();
		sql.append("update  subscribe_excelcol_report set ");
		sql.append(" updater_id=?,update_time=? ");
		if(isIsChecked){
			sql.append(" ,is_checked=? ");
		}
		sql.append(" where table_id=? ");
		
		try{
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,excelCol.getUserId());
			pstmt.setString(2,Tools.getCurrFormatTimeGen());
			//isIsChecked不为空
			if(isIsChecked){
				pstmt.setString(3,excelCol.getIsChecked());
				pstmt.setString(4,excelCol.getTableId());
			}else{//isIsChecked为空
				pstmt.setString(3,excelCol.getTableId());
			}
			
			pstmt.execute();
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}
		
		/*TODO 改处注释代码勿删除
		 * if(isCheckedColumnId){
			sql.append(" checked_column_id=?, ");
		}else  
		if(isIsChecked){
			sql.append(" is_checked=?, ");
		}
		
		sql.append(" where table_id=? ");
		
		try{
			pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1,excelCol.getUserId());
			pstmt.setString(2,Tools.getCurrFormatTimeGen());
			//两个都不为空
			if(isCheckedColumnId && isIsChecked){
				pstmt.setString(3,excelCol.getCheckedColumnId());
				pstmt.setString(4,excelCol.getIsChecked());
				pstmt.setString(5,excelCol.getTableId());
			}else if((!isCheckedColumnId) && isIsChecked){//第一个为空
				pstmt.setString(3,excelCol.getIsChecked());
				pstmt.setString(4,excelCol.getTableId());
			}else if(isCheckedColumnId && (!isIsChecked)){//第二个为空
				pstmt.setString(3,excelCol.getCheckedColumnId());
				pstmt.setString(4,excelCol.getTableId());
			}else if((!isCheckedColumnId) && (!isIsChecked)){//两个都为空
				pstmt.setString(3,excelCol.getTableId());
			}
			
			pstmt.execute();
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}finally{
			DB.commit(conn);
			DB.close(conn, pstmt);
		}*/
		return flag;
		
	}
}
