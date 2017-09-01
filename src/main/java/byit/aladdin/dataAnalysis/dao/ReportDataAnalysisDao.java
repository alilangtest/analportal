package byit.aladdin.dataAnalysis.dao;

import java.util.List;
import java.util.Map;


import byit.aladdin.TableauConfigure.bo.CollectBo;
import byit.aladdin.dataAnalysis.entity.AuthRole;
import byit.aladdin.dataAnalysis.entity.Dic;
import byit.aladdin.dataAnalysis.entity.Employee;
import byit.aladdin.dataAnalysis.entity.LabelInfo;
import byit.aladdin.dataAnalysis.entity.Org;
import byit.aladdin.dataAnalysis.entity.PmReportCollect;
import byit.aladdin.dataAnalysis.entity.PmTableauUser;
import byit.aladdin.dataAnalysis.entity.RepAttributeInfo;
import byit.aladdin.dataAnalysis.entity.Report;
import byit.aladdin.dataAnalysis.entity.ReportHomepage;
import byit.aladdin.dataAnalysis.entity.TypeTop;
import byit.core.plug.mybatis.Mapper;

@Mapper
public interface ReportDataAnalysisDao {


	List<Dic> queryDic(Dic dic);


	List<LabelInfo> querylabelInfoList(List<AuthRole> authRoles);

	List<LabelInfo> queryDataLabel(List<AuthRole> authRoles);

	List<TypeTop> queryReportInfo(List<AuthRole> authRoles);

	int addReportInfo(TypeTop typeTop);

	List<TypeTop> queryReportBy(TypeTop typeTop);

	List<TypeTop> isReportDo(TypeTop typeTop);

	int deleteReport(TypeTop typeTop);

	Report getReportByIdAndType(String reportid);

	Employee queryEmpById(String userId);

	Org queryOrgById(String orgId);
	
	Org queryOrgParentById(String orgId);

	Report getRoleReport(String reportid, String userId);

	Report getRoleReport(Map<String, Object> param);

	PmTableauUser getTableauUser(String optype);

	void addCollectInfo(PmReportCollect collect);

	void deleteCollectInfo(PmReportCollect collect);

	List<AuthRole> queryRoleById(String operationUser);


	List<TypeTop> queryAnalysis(Map<String, Object> param);

	List<RepAttributeInfo> queryrepAttributeInfo(String operationUser);

	List<TypeTop> queryReportByUser(String operationUser);

	List<TypeTop> getSCSort();

	List<TypeTop> getSCBSort(String operationUser);

	List<TypeTop> getFXBSort(String operationUser);

	List<TypeTop> queryAnalysis2(String operationUser);
	
	//查询收藏列表
	public List<CollectBo> countCollections(String loginid);
	//取消收藏报表
	public int collectDelete(CollectBo collectBo);
	public void collectDeleteRetOperation(CollectBo collectBo);
	//报表收藏置顶
	public boolean collectToTop(CollectBo collectBo);

	//删除首页
	int deleteHomepage(String userid);

	//添加首页
	int addHomepage(ReportHomepage reportHomepage);

	//查询首页
	ReportHomepage getHomepage(String userid);

	//报表菜单树
	List<LabelInfo> queryReportMenuList(List<AuthRole> authRoles);

}
