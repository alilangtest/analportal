package byit.aladdin.instantQuery.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.aladdin.dataAnalysis.entity.AuthRole;
import byit.aladdin.dataAnalysis.entity.Employee;
import byit.aladdin.dataAnalysis.entity.LabelInfo;
import byit.aladdin.dataAnalysis.entity.Org;
import byit.aladdin.dataAnalysis.entity.Report;
import byit.aladdin.dataAnalysis.entity.TypeTop;
import byit.aladdin.instantQuery.dao.InstantQueryDao;
import byit.aladdin.instantQuery.entity.TableauUsersEntity;
import byit.aladdin.instantQuery.service.InstantQueryService;
import byit.osdp.tableau.TableauConfig;
import cn.byitgroup.utils.tableau.TableauUtil;

@Transactional
@Service("instantQueryService")
public class InstantQueryServiceImpl implements InstantQueryService{
	private final Logger logger = Logger.getLogger(InstantQueryServiceImpl.class);
	@Autowired
	private InstantQueryDao instantQueryDao;
	
	/**
	 * 获取当前登录人的所属角色
	 * operationUser 当前登录用户id
	 */
	@Override
	public List<AuthRole> queryRoleById(String operationUser) {
		return instantQueryDao.queryRoleById(operationUser);
	}

	/**
	 * 获取父标签
	 */
	@Override
	public List<LabelInfo> queryDataLabel(List<AuthRole> authRoles) {
		return instantQueryDao.queryDataLabel(authRoles);
	}

	/**
	 * 条件查询报表
	 */
	@Override
	public List<TypeTop> queryAnalysis(String operationUser) {
		Map<String, Object> param=new HashMap<String,Object>();
		param.put("operationUser", operationUser);
		return instantQueryDao.queryAnalysis(param);
	}
	@Override
	public List<TypeTop> queryAnalysis3(String menuId, String operationUser) {
		return instantQueryDao.queryAnalysis3(menuId,operationUser);
	}


	/**
	 * 获取报表信息（表pm_report_query）
	 */
	@Override
	public Report getReportByIdAndType(String reportid) {
		return instantQueryDao.getReportByIdAndType(reportid);
	}

	/**
	 * 获取所有机构
	 */
	@Override
	public Map<String, Object> getOrgInfo(String userId) {
		
		Map<String,Object> info=new HashMap<String,Object>();
		//根据用户查询机构id
		Employee emp = instantQueryDao.queryEmpById(userId);
		if (emp!=null) {
			//根据机构id查询机构信息
			Org org=instantQueryDao.queryOrgById(emp.getOrgId());
			if(org==null){
				return null;
			}
			info.put("orgids",  org.getId());
			info.put("orglev", org.getLev());
		} else {
			return null;
		}
		return info;
	}

	/**
	 * 报表与角色关联关系
	 */
	@Override
	public Report getRoleReport(String reportid, String userId) {
		Map<String, Object> param=new HashMap<String,Object>();
		param.put("reportid", reportid);
		param.put("userId", userId);
		return instantQueryDao.getRoleReport(param);
	}

	/**
	 * 判断该用户是否有权限编辑报表
	 */
	@Override
	public List<TableauUsersEntity> getTableauUser(String username) {
		String sql = "select name from system_users where name=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TableauUsersEntity> reports = new ArrayList<TableauUsersEntity>();
		try {
			String tableuaPostgresqlIp = TableauConfig.TABLEUA_POSTGRESQL_IP;
			String tableuaPostgresqlUsername = TableauConfig.TABLEUA_POSTGRESQL_USERNAME;
			String tableuaPostgresqlPassword = TableauConfig.TABLEUA_POSTGRESQL_PASSWORD;
			
			conn = TableauUtil.getTableauConn(tableuaPostgresqlIp,tableuaPostgresqlUsername, tableuaPostgresqlPassword);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			logger.debug(sql);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				TableauUsersEntity entity = new TableauUsersEntity();
				entity.setName(rs.getString("name"));
				
				reports.add(entity);
			}
			TableauUtil.closeTableauConn(conn, rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return reports;
	}

	/**
	 * 数据分析筛选子标签
	 */
	@Override
	public List<LabelInfo> querylabelInfoList(String pmennuid, List<AuthRole> authRoles) {
		return instantQueryDao.querylabelInfoList(pmennuid,authRoles);
	}

	
	//获取站点UrlNamespace
		@Override
		public String getUrlNamespace(String siteid) {
			String sql = "select s.url_namespace from sites s where id = " + siteid ;
			String  url_namespace = "";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				String tableuaPostgresqlIp = TableauConfig.TABLEUA_POSTGRESQL_IP;
				String tableuaPostgresqlUsername = TableauConfig.TABLEUA_POSTGRESQL_USERNAME;
				String tableuaPostgresqlPassword = TableauConfig.TABLEUA_POSTGRESQL_PASSWORD;
				
				conn = TableauUtil.getTableauConn(tableuaPostgresqlIp,tableuaPostgresqlUsername, tableuaPostgresqlPassword);
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					url_namespace=rs.getString("url_namespace");
				}
				TableauUtil.closeTableauConn(conn, rs);
				
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			return url_namespace;
		}

}
