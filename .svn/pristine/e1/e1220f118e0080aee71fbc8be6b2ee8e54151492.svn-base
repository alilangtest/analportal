package byit.aladdin.instantQuery.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import byit.aladdin.dataAnalysis.entity.AuthRole;
import byit.aladdin.dataAnalysis.entity.Employee;
import byit.aladdin.dataAnalysis.entity.LabelInfo;
import byit.aladdin.dataAnalysis.entity.Org;
import byit.aladdin.dataAnalysis.entity.Report;
import byit.aladdin.dataAnalysis.entity.TypeTop;
import byit.core.plug.mybatis.Mapper;

/**
 * 报表统计分析dao
 * @author Mr.tang
 */
@Mapper
public interface InstantQueryDao {
	
	/**
	 * 获取当前登录人的所属角色
	 * operationUser 当前登录用户id
	 */
	List<AuthRole> queryRoleById(String operationUser);
	
	/**
	 *  获取父标签
	 * @param authRoles
	 * @return
	 */
	List<LabelInfo> queryDataLabel(List<AuthRole> authRoles);
	
	/**
	 * 条件查询报表
	 * @param param 参数
	 * @return
	 */
	List<TypeTop> queryAnalysis(Map<String, Object> param);
	
	/**
	 * 获取报表信息（表pm_report_query）
	 * @param reportid
	 * @return
	 */
	Report getReportByIdAndType(String reportid);
	
	/**
	 * 根据用户查询机构id
	 * @param userId
	 * @return
	 */
	Employee queryEmpById(String userId);
	
	/**
	 * 根据机构id查询机构信息
	 * @param orgId
	 * @return
	 */
	Org queryOrgById(String orgId);
	
	/**
	 * 报表与角色关联关系
	 * @param param
	 * @return
	 */
	Report getRoleReport(Map<String, Object> param);
	
	/**
	 * 数据分析筛选子标签
	 * @param pmennuid
	 * @param authRoles
	 * @return
	 */
	List<LabelInfo> querylabelInfoList(@Param("pmennuid")String pmennuid, @Param("list")List<AuthRole> authRoles);
	
	/**
	 * 
	 * @param menuId
	 * @param operationUser
	 * @return
	 */
	List<TypeTop> queryAnalysis3(@Param("menuId")String menuId, @Param("operationUser")String operationUser);

}
