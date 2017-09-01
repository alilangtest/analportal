package byit.aladdin.instantQuery.service;

import java.util.List;
import java.util.Map;

import byit.aladdin.dataAnalysis.entity.AuthRole;
import byit.aladdin.dataAnalysis.entity.LabelInfo;
import byit.aladdin.dataAnalysis.entity.Report;
import byit.aladdin.dataAnalysis.entity.TypeTop;
import byit.aladdin.instantQuery.entity.TableauUsersEntity;

public interface InstantQueryService {

	// 获取当前登录人的所属角色
	List<AuthRole> queryRoleById(String operationUser);
	
	//获取父标签
	List<LabelInfo> queryDataLabel(List<AuthRole> authRoles);

	//获取子标签
	List<LabelInfo> querylabelInfoList(String pmennuid, List<AuthRole> authRoles);
	
	//条件查询报表
	List<TypeTop> queryAnalysis(String operationUser);
	
	//条件查询报表
	List<TypeTop> queryAnalysis3(String menuId, String operationUser);
	
	//判断该用户是否有权限编辑报表
	List<TableauUsersEntity> getTableauUser(String username);
	
	//获取报表信息（表pm_report_query）
	Report getReportByIdAndType(String reportid);
	
	//获取所有机构
	Map<String, Object> getOrgInfo(String userId);
	
	//报表与角色关联关系
	Report getRoleReport(String reportid, String userId);

	//获取站点UrlNamespace
	String getUrlNamespace(String siteid);
	
}
