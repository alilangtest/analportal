package byit.tableausubscribe.tab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import byit.osdp.base.entity.SystemDoMainEntity;
import byit.tableausubscribe.tab.db.DB;

public class EmailDao {
	
	public List<SystemDoMainEntity> getEmailInfo(){
		List<SystemDoMainEntity>  list=new ArrayList<SystemDoMainEntity>();
		StringBuffer sql=new StringBuffer();
		sql.append("select eventid,domainname,domainid,codeid,codename from SYSTEMDOMAIN where domainid='email'");
		
		 Connection conn=null;
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 
		 try {
			conn=DB.getPortalConn();
			pstmt=conn.prepareStatement(sql.toString());
			rs=	pstmt.executeQuery();
			while( rs.next()){
				SystemDoMainEntity tSystemDoMainEntity=new SystemDoMainEntity();
				tSystemDoMainEntity.setEventid(rs.getString("eventid"));
				tSystemDoMainEntity.setDomainid(rs.getString("domainid"));
				tSystemDoMainEntity.setDomainname(rs.getString("domainname"));
				tSystemDoMainEntity.setCodeid(rs.getString("codeid"));
				tSystemDoMainEntity.setCodename(rs.getString("codename"));
				list.add(tSystemDoMainEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return list;
	}

	
}
