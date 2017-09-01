package byit.aladdin.queryReport.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byit.aladdin.TableauConfigure.entity.PmTableauserverEntity;
import byit.aladdin.queryReport.bo.IdaPmReportBo;
import byit.aladdin.queryReport.bo.IdaPmReportMenuBo;
import byit.aladdin.queryReport.bo.IdaPmRoleBo;
import byit.aladdin.queryReport.bo.RepAttributeBo;
import byit.aladdin.queryReport.bo.RepAttributeRelaBo;
import byit.aladdin.queryReport.bo.ReportMenuReportBo;
import byit.aladdin.queryReport.bo.RoleReportBo;
import byit.aladdin.queryReport.entity.IdaPmReport;
import byit.core.plug.mybatis.Mapper;
import byit.core.plug.mybatis.PageBounds;


@Mapper
public interface QueryReportDao {

	
	//根据ID查询
	IdaPmReportBo getReportById(IdaPmReportBo reportBo);

	//挂载报表
	boolean reportSetMenuid(ReportMenuReportBo menuReportBo);

	//查询角色树   模糊查询角色
	List<IdaPmRoleBo> queryLikeRoleTreeList(HashMap<String, String> hashMap);

	//查询报表权限
	List<RoleReportBo> getRoleReportByReportid(String reportid);

	// 卸载报表
	boolean deleteMenuReportByReportId(ReportMenuReportBo menuReportBo);

	//删除报表
	boolean deleteReport(String reportid);

	//删除报表角色关系
	void deleteRoleReportByReportid(String reportid);

	//判断该报表是否挂载
	int findIsReportMenu(String reportid);

	//报表菜单id获取报表
	List<ReportMenuReportBo> queryReportList(IdaPmReportMenuBo reportMenuBo);

	//列表
	List<Map<String, Object>> queryReprotPage(Map<String, Object> params, PageBounds bounds);

	List<IdaPmReportBo> queryReportListTree(String menuid);

	//解除角色与菜单关系
	void deleteByReportid(String reportid);

	List<RoleReportBo> queryReportRoleLists(RoleReportBo roleReportBo);

	RoleReportBo queryRoleReportOptype(RoleReportBo roleReportBo1);

	//保存报表角色关系
	boolean saveOrUpdate(RoleReportBo org);

	
	//获取Tableau连接数据
	List<PmTableauserverEntity> queryTableauList();

	//获取本地报表
	List<IdaPmReport> getAllReports();

	//更新报表
	boolean updateReportFromTableau(IdaPmReport report);

	//添加报表
	boolean saveReport(IdaPmReport report);
	
	//回显角色与报表的关系
	List<RoleReportBo> queryMenuRoleList(RoleReportBo roleReportBo);

	//编辑报表
	boolean updateReport(IdaPmReportBo reportBo);

	//查询报表属性
	List<RepAttributeRelaBo> queryAttribute();

	//添加属性
	int saveAttribute(RepAttributeBo repAttributeBo);

	//添加属性验证
	List<RepAttributeRelaBo> queryAttributeByName(String name);

	//添加报表属性关系
	void saveAttributeRela(RepAttributeRelaBo repAttributeRelaBo);

	//删除报表属性关系
	void deleteAttributeRelaByReportid(String reportid);

	//删除报表角色关系
	int deleteRoleReport(RoleReportBo roleReportBo);

	//查询报表已有属性
	List<RepAttributeRelaBo> queryAttributecheckedlist(String reportid);

}
