package byit.aladdin.report.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

//import byit.aladdin.orderAbtain.utils.PropertiesUtil;
import byit.aladdin.report.bo.IdaPmReportBo;
import byit.aladdin.report.bo.IdaPmReportMenuBo;
import byit.aladdin.report.bo.IdaPmRoleBo;
import byit.aladdin.report.bo.RepAttributeBo;
import byit.aladdin.report.bo.RepAttributeRelaBo;
import byit.aladdin.report.bo.ReportMenuReportBo;
import byit.aladdin.report.bo.RoleReportBo;
import byit.aladdin.report.bo.TableauProjectBo;
import byit.aladdin.report.dao.IdaPmReportDao;
import byit.aladdin.report.entity.IdaPmReport;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.security.UserHolder;
import byit.osdp.tableau.TableauConfig;
import cn.byitgroup.utils.tableau.TableauUtil;




@Service("idaPmReportServiceImpl")
public class IdaPmReportServiceImpl {
	
	private static final Logger logger = Logger.getLogger(IdaPmReportServiceImpl.class);
	@Autowired
	private IdaPmReportDao idaPmReportDao;
	
	//报表列表
	public Object queryReprotPage(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		//获得参数
		String reportname=pagination.getFilters().get("reportname").toString();
		String menuid=pagination.getFilters().get("menuid").toString();
		String flag=pagination.getFilters().get("flag").toString();
		String mountstate = pagination.getFilters().getString("mountstate");
		String userid=UserHolder.getId();
		params.put("reportname", reportname);
		params.put("menuid", menuid);
		params.put("flag", flag);
		params.put("userid", userid);
		params.put("mountstate", mountstate);
		
		//获得分页
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());
		Page<IdaPmReport> page = bounds.wrap(idaPmReportDao.queryReprotPage(params, bounds));
		return page;
	}
	
	
	//查询
//	public IdaPmReportBo getReportById(IdaPmReportBo ReportBo) {
//		IdaPmReportBo idaPmReport = this.idaPmReportDao.getReportById(ReportBo);
//		return idaPmReport;
//	}

	//挂载报表
	public Map<String, Object> reportSetMenuid(HttpServletRequest request,ReportMenuReportBo menuReportBo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String ids = request.getParameter("reportids");
	//	ReportMenuReport menuReport = new ReportMenuReport();
	//	menuReport.setMenuid(menuReportBo.getMenuid());
		try{
			String[] reportids = null;
			if(ids!=null && !"".equals(ids)){
				reportids = request.getParameter("reportids").split(",");
				boolean result=false;
				boolean update = false;
				for(String reportid : reportids){
					menuReportBo.setReportid(reportid);
					menuReportBo.setEventid(UUID.randomUUID().toString());
					//
					IdaPmReport report = new IdaPmReport();
					report.setReportId(reportid);
					report.setMountstate("1");
					//更改报表挂载状态
					update = this.idaPmReportDao.updateReportFromTableau(report);
					result = this.idaPmReportDao.reportSetMenuid(menuReportBo);
				}
				if (update &&  result) {
					map.put("result", true);
					map.put("msg", "挂载报表成功！");
				} else{
					map.put("result", false);
					map.put("msg", "挂载报表失败！");
				}
			} else{
				map.put("result", false);
				map.put("msg", "请选择报表！");
			}
		} catch(Exception e){
			map.put("result", false);
			map.put("msg", "添加报表出现异常！");
			e.printStackTrace();
		}
		return map;
	}
	
	
	//获取报表
	public boolean getReportFromTableau(String id) {
		logger.info("~~~~~~~~~~~~从tableau获取报表开始~~~~~~~~~~~~");
		boolean result = false;
		//从tableau端获取的报表
		List<IdaPmReport> reports = this.getReportTableau(id);
		//本地所有报表
		List<IdaPmReport> reportsOra = this.idaPmReportDao.getAllReports();
		
		List<IdaPmReport> rs = new ArrayList<IdaPmReport>();
		List<IdaPmReport> rsOra = new ArrayList<IdaPmReport>();
		//本地已有的报表执行更新，没有的报表执行插入
		if(reportsOra!=null && reportsOra.size()>0){
			for(IdaPmReport reportOra : reportsOra){
				if(reports!=null && reports.size()>0){
					for(IdaPmReport report : reports){
						if(report.getReportzhname().indexOf("\n") > -1){
							report.setReportzhname(report.getReportzhname().replace("\n", " "));
						}
						if(report.getDesp().indexOf("\n") > -1){
							report.setDesp(report.getDesp().replace("\n", " "));
						}
						if(report.getReportId().equals(reportOra.getReportId())){
							//获取时间未当前系统时间
							report.setGetTime(new Date());
							result = this.idaPmReportDao.updateReportFromTableau(report);
							//result = true;
							rs.add(report);
							rsOra.add(reportOra);
						}
					}
				}else{
					break;
				}
			}

			// 已经更新过的从集合中删除,其他的执行添加
			if(reports!=null && reports.size()>0){
				for(IdaPmReport report : rs){
					reports.remove(report);
				}
			}
			
			//添加
			if(reports!=null && reports.size()>0){
				for(IdaPmReport report : reports){
					if(report.getReportzhname().indexOf("\n") > -1){
						report.setReportzhname(report.getReportzhname().replace("\n", " "));
					}
					if(report.getDesp().indexOf("\n") > -1){
						report.setDesp(report.getDesp().replace("\n", " "));
					}
					if("null".equals(report.getReportstate()) || report.getReportstate()==null){
						report.setReportstate("");
					}
					report.setGetTime(new Date());
					report.setMountstate("0");
					boolean s = this.idaPmReportDao.saveReport(report);
					if(s){
						result = true;
					}
				}
			}
		}else{
			if(reports!=null && reports.size()>0){
				for(IdaPmReport report : reports){
					if(report.getReportzhname().indexOf("\n") > -1){
						report.setReportzhname(report.getReportzhname().replace("\n", " "));
					}
					if(report.getDesp().indexOf("\n") > -1){
						report.setDesp(report.getDesp().replace("\n", " "));
					}
					report.setGetTime(new Date());
					report.setMountstate("0");
					boolean s = this.idaPmReportDao.saveReport(report);
					if(s){
						result = true;
					}
				}
			}
		}
		logger.info("~~~~~~~~~~~~从tableau获取报表结束~~~~~~~~~~~~");
		return result;
	}

	
	//获取Tableau 
	public List<IdaPmReport> getReportTableau(String id) {

		String sql = "select w.id as workbookid,w.name as workbookname,"
				+ "w.owner_id as ownerid,w.owner_name as ownername,"
				+ "w.project_id as projetid,w.project_name as projectname,"
				+ "w.site_id as siteid,w.system_user_id as sysuserid,"
				+ "w.updated_at as workupdatetime,w.workbook_url as workbookurl,"
				+ "w.created_at as workcreatetime,v.id as viewid,v.name as viewname,"
				+ "v.caption as caption,v.created_at as viewcreatetime,"
				+ "v.title as title,v.view_url as viewurl "
				+ "from _workbooks w "
				+ "left join _views v on v.workbook_id=w.id "
				+ "where w.project_id = "+id;
				
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<IdaPmReport> reports = new ArrayList<IdaPmReport>();
		try {
			String tableuaPostgresqlIp = TableauConfig.TABLEUA_POSTGRESQL_IP;
			String tableuaPostgresqlUsername = TableauConfig.TABLEUA_POSTGRESQL_USERNAME;
			String tableuaPostgresqlPassword = TableauConfig.TABLEUA_POSTGRESQL_PASSWORD;
			
//			conn = TableauUtil.getTableauConn(config.getTableauserverip(),config.getPostgreuname(), config.getPostgrepwd());
			conn = TableauUtil.getTableauConn(tableuaPostgresqlIp,tableuaPostgresqlUsername, tableuaPostgresqlPassword);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				IdaPmReport report = new IdaPmReport();
				
				report.setReportId(rs.getString("viewid"));
				report.setReportname(rs.getString("viewname"));
				report.setProjectname(rs.getString("projectname"));
				report.setReportzhname(rs.getString("title"));
				report.setUrl(rs.getString("viewurl"));
				report.setDesp(rs.getString("caption"));
				report.setPublishdate(rs.getDate("viewcreatetime"));
				report.setUpdatedate(rs.getDate("workupdatetime"));
				report.setPublisper(rs.getString("ownerid"));
				report.setReporttype("1");
				report.setWorkbookid(rs.getString("workbookid"));
				report.setWorkbookname(rs.getString("workbookname"));
				report.setSiteid(rs.getString("siteid"));
				reports.add(report);
			}
			TableauUtil.closeTableauConn(conn, rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return reports;
	}
	
	//tableau站点列表
	public List<TableauProjectBo> tableauSitePage() {
		String sql = "select s.id,s.name from _sites s ";
				
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TableauProjectBo> list = new ArrayList<TableauProjectBo>();
		try {
			String tableuaPostgresqlIp = TableauConfig.TABLEUA_POSTGRESQL_IP;
			String tableuaPostgresqlUsername = TableauConfig.TABLEUA_POSTGRESQL_USERNAME;
			String tableuaPostgresqlPassword = TableauConfig.TABLEUA_POSTGRESQL_PASSWORD;
			
			conn = TableauUtil.getTableauConn(tableuaPostgresqlIp,tableuaPostgresqlUsername, tableuaPostgresqlPassword);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TableauProjectBo tableauProjec = new TableauProjectBo();
				
				tableauProjec.setSite_id(rs.getString("id"));
				tableauProjec.setSite_name(rs.getString("name"));
				
				list.add(tableauProjec);
			}
			TableauUtil.closeTableauConn(conn, rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	//tableau项目列表
	public List<TableauProjectBo> tableauProjectPage(String site_id) {
		String sql = "select p.id,p.name ,s.id site_id,s.name site_name "
				+"from _projects p ,_sites s "
				+"where p.site_id = s.id ";
		
		if(null!=site_id&&""!=site_id){
			sql += " and p.site_id="+site_id;
		}
		
		sql += "ORDER BY s.id";
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TableauProjectBo> list = new ArrayList<TableauProjectBo>();
		try {
			String tableuaPostgresqlIp = TableauConfig.TABLEUA_POSTGRESQL_IP;
			String tableuaPostgresqlUsername = TableauConfig.TABLEUA_POSTGRESQL_USERNAME;
			String tableuaPostgresqlPassword = TableauConfig.TABLEUA_POSTGRESQL_PASSWORD;
			
			conn = TableauUtil.getTableauConn(tableuaPostgresqlIp,tableuaPostgresqlUsername, tableuaPostgresqlPassword);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TableauProjectBo tableauProjec = new TableauProjectBo();
				
				tableauProjec.setId(rs.getString("id"));
				tableauProjec.setName(rs.getString("name"));
				tableauProjec.setSite_id(rs.getString("site_id"));
				tableauProjec.setSite_name(rs.getString("site_name"));
				
				list.add(tableauProjec);
			}
			TableauUtil.closeTableauConn(conn, rs);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	
	// 卸载报表
	public boolean deleteMenuReportByReportId(ReportMenuReportBo menuReportBo) {
		IdaPmReport report = new IdaPmReport();
		report.setReportId(menuReportBo.getReportid());
		report.setMountstate("0");
		boolean update = this.idaPmReportDao.updateReportFromTableau(report);
		boolean delete = this.idaPmReportDao.deleteMenuReportByReportId(menuReportBo);
		return update == delete;
	}

	
	//查询报表权限
	public List<RoleReportBo> getRoleReportByReportid(String string) {
		return idaPmReportDao.getRoleReportByReportid(string);
	}

	//判断该报表是否挂载
	public int findIsReportMenu(String reportid) {
		return idaPmReportDao.findIsReportMenu(reportid);
	}
	//得到该报表对应的报表对象
	public IdaPmReportBo getReportById(String string) {
		IdaPmReportBo reportBo = new IdaPmReportBo();
		reportBo.setReportId(string);
		return this.idaPmReportDao.getReportById(reportBo);
	}
	//删除报表
	public boolean deleteReportById(String reportid) {
		//删除报表与角色关系
		this.idaPmReportDao.deleteRoleReportByReportid(reportid);
		//删除报表
		boolean result = this.idaPmReportDao.deleteReport(reportid);
		//删除收藏报表
		 this.idaPmReportDao.deletecollect(reportid);
		//删除首页报表
		this.idaPmReportDao.deleteHomepage(reportid);
		return result;
	}
	
	//已挂载报表删除
	public boolean deleteReport(String reportid, String menuid) {
		ReportMenuReportBo menuReportBo = new ReportMenuReportBo("",menuid,reportid);
		boolean result=false;
		try {
			//删除报表与角色关系
			this.idaPmReportDao.deleteRoleReportByReportid(reportid);
			//卸载报表
			this.idaPmReportDao.deleteMenuReportByReportId(menuReportBo);
			//删除报表
			result = this.idaPmReportDao.deleteReport(reportid);
			//删除收藏报表
			 this.idaPmReportDao.deletecollect(reportid);
			//删除首页报表
			this.idaPmReportDao.deleteHomepage(reportid);
		} catch (Exception e) {
			return result;
		}
		return result;
	}

	//查询角色树   模糊查询角色
	public List<IdaPmRoleBo> queryLikeRoleTreeList(HashMap<String, String> hashMap) {
		List<IdaPmRoleBo> IdaPmRole = idaPmReportDao.queryLikeRoleTreeList(hashMap);
		return IdaPmRole;
	}

	
	//报表菜单id获取报表
	public List<ReportMenuReportBo> queryReportListTrees(IdaPmReportMenuBo reportMenuBo) {
		return this.idaPmReportDao.queryReportList(reportMenuBo);
	}

	

	
	public List<IdaPmReportBo> queryReportListTree(String menuid) {
		//String sql="select m.reportid,m.reportname from REPORTMENU_REPORT_RELA s inner join PM_REPORT m on m.reportid=s.reportid where s.MENUID='"+menuid+"'";
		//List<IdaPmReportBo> reportbo = new ArrayList<IdaPmReportBo>();
		List<IdaPmReportBo> reportbo = this.idaPmReportDao.queryReportListTree(menuid);
		/*for (IdaPmReport idaPmReport : report) {
			reportbo.add(this.convertToBo(idaPmReport));
		}*/
		return reportbo;
	}

//解除角色与菜单关系
	public boolean removeRoleReport(String reportids,String roleids) {
		logger.info("~~~~~~~~~~~~解除角色与菜单关系开始~~~~~~~~~~~~");
		String optype = reportids.substring(reportids.length() - 1,reportids.length());
		String reportid = reportids.substring(0, reportids.length() - 2);
		
		boolean flag = false;
		try {
			if(null!=roleids&&""!=roleids){
				String[] roleid = roleids.split(",");
				for(String rolestr : roleid){
					RoleReportBo roleReportBo = new RoleReportBo();
					roleReportBo.setReportid(reportid);
					roleReportBo.setRoleid(rolestr);
					idaPmReportDao.deleteRoleReport(roleReportBo);
				}	
			}/*else{
				RoleReportBo roleReportBo = new RoleReportBo();
				roleReportBo.setReportid(reportid);
				roleReportBo.setOptype(optype);
				idaPmReportDao.deleteRoleReport(roleReportBo);
			}*/
			RoleReportBo roleReportBo = new RoleReportBo();
			roleReportBo.setReportid(reportid);
			roleReportBo.setOptype(optype);
			idaPmReportDao.deleteRoleReport(roleReportBo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		logger.info("~~~~~~~~~~~~解除角色与菜单关系结束~~~~~~~~~~~~");
		return flag;
	}
	
	//保存报表与角色
	public boolean saveReportRole(RoleReportBo orgReportbo,String menuid) {
		logger.info("~~~~~~~~~~~~保存报表与角色的关系开始~~~~~~~~~~~~");
		boolean flag = true;
		String reportstr = orgReportbo.getReportid();
		String optype = reportstr.substring(reportstr.length() - 1,reportstr.length());
		String reportid = reportstr.substring(0, reportstr.length() - 2);
		String[] roleid = orgReportbo.getRoleid().split(",");
		/*for(String rolestr : roleid){
			
			RoleReportBo roleReportBo = new RoleReportBo();
			roleReportBo.setReportid(reportid);
			roleReportBo.setRoleid(rolestr);
			
			List<RoleReportBo> rReportBo = idaPmReportDao.queryReportRoleLists(roleReportBo);
			for (RoleReportBo roleReport : rReportBo) {
				//if(roleReport.getOptype()==optype||optype.equals(roleReport.getOptype()))
				idaPmReportDao.deleteByReportid(roleReport.getReportid());
			}
		}*/
		
		// 切割选中机构的id
		Date date = new Date();
		try {
			for (String rolestr : roleid) {
				RoleReportBo roleReportBo1 = new RoleReportBo();
				roleReportBo1.setOptype(optype);
				roleReportBo1.setReportid(reportid);
				roleReportBo1.setRoleid(rolestr);
				// 查询数据库中是否已经保存该关系
				RoleReportBo exitorg = this.idaPmReportDao.queryRoleReportOptype(roleReportBo1);
				if (exitorg != null) {

				} else {
					RoleReportBo org = new RoleReportBo();
					org.setEventid(UUID.randomUUID().toString());
					org.setAudtate(date);
					org.setLimitday(Long.valueOf(0));
					org.setOptype(optype);
					org.setReporttype("1");
					org.setReportid(reportid);
					org.setRoleid(rolestr);
					flag = this.idaPmReportDao.saveOrUpdate(org);
					
					flag=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		logger.info("~~~~~~~~~~~~保存报表与角色的关系结束~~~~~~~~~~~~");
		return flag;
	}

	//回显角色与报表操作类型的关系
	public List<RoleReportBo> queryMenuRoleList(String reportid, String optype) {
		RoleReportBo roleReportBo = new RoleReportBo();
		roleReportBo.setOptype(optype);
		roleReportBo.setReportid(reportid);
		List<RoleReportBo> roleReportList = idaPmReportDao.queryMenuRoleList(roleReportBo);
		return roleReportList;
	}
	
	//回显角色与报表的关系
	public List<RoleReportBo> queryRoleReportMenuList(String reportid) {
		RoleReportBo roleReportBo = new RoleReportBo();
		roleReportBo.setReportid(reportid);
		List<RoleReportBo> roleReportList = idaPmReportDao.queryMenuRoleList(roleReportBo);
		return roleReportList;
	}


	//编辑报表
	public boolean updateReport(IdaPmReportBo reportBo,String names) {
		if(names!=null&&names!=""){
			idaPmReportDao.deleteAttributeRelaByReportid(reportBo.getReportId());
			String[] split = names.split(",");
			for (String name : split) {
				RepAttributeRelaBo repAttributeRelaBo = new RepAttributeRelaBo();
				repAttributeRelaBo.setId(UUID.randomUUID().toString());
				repAttributeRelaBo.setName(name);
				repAttributeRelaBo.setCreatedate(new Date());
				repAttributeRelaBo.setCreator(UserHolder.getId());
				repAttributeRelaBo.setReportid(reportBo.getReportId());
				idaPmReportDao.saveAttributeRela(repAttributeRelaBo);
			}
		}
		if(""!=reportBo.getDatescreen()&&null!=reportBo.getDatescreen()){
			return idaPmReportDao.updateReport(reportBo);
		}else{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			int currenYear = calendar.get(Calendar.YEAR);
			calendar.set(Calendar.YEAR, currenYear + 2);
			Date date = new Date(calendar.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startTime = sdf.format(date);
			reportBo.setDatescreen(startTime);
			return idaPmReportDao.updateReport(reportBo);
		}
	}


	//查询报表属性
	public List<RepAttributeRelaBo> queryAttribute() {
		List<RepAttributeRelaBo> repAttributeRelaList = this.idaPmReportDao.queryAttribute();
		return repAttributeRelaList;
	}

	//查询报表已有属性
	public List<RepAttributeRelaBo> queryAttributecheckedlist(String reportid) {
		List<RepAttributeRelaBo> repAttributeRelaList = this.idaPmReportDao.queryAttributecheckedlist(reportid);
		return repAttributeRelaList;
	}

	//添加属性
	public Boolean saveAttribute(String name) {
		
		List<RepAttributeRelaBo> repAttributeRelaList = this.idaPmReportDao.queryAttributeByName(name);
		
		if(repAttributeRelaList!=null&&repAttributeRelaList.size()>0){
			return false;
		}else{
			RepAttributeBo repAttributeBo = new RepAttributeBo();
			repAttributeBo.setName(name);
			repAttributeBo.setCreator(UserHolder.getId());
			repAttributeBo.setCreate_date(new Date());
			repAttributeBo.setId(UUID.randomUUID().toString());
			int i= this.idaPmReportDao.saveAttribute(repAttributeBo);
			if(i>0){
				return true;
			}else{
				return false;
			}
		}
		
	}







	


	

	
	
	
	
}
