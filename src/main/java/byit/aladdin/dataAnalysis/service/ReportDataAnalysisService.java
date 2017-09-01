package byit.aladdin.dataAnalysis.service;

import java.util.List;
import java.util.Map;

import byit.aladdin.TableauConfigure.bo.CollectBo;
import byit.aladdin.dataAnalysis.entity.AuthRole;
import byit.aladdin.dataAnalysis.entity.Dic;
import byit.aladdin.dataAnalysis.entity.LabelInfo;
import byit.aladdin.dataAnalysis.entity.PmReportCollect;
import byit.aladdin.dataAnalysis.entity.PmTableauUser;
import byit.aladdin.dataAnalysis.entity.RepAttributeInfo;
import byit.aladdin.dataAnalysis.entity.Report;
import byit.aladdin.dataAnalysis.entity.ReportHomepage;
import byit.aladdin.dataAnalysis.entity.TypeTop;
//import byit.aladdin.personCenter.entity.PersonEntity;

public interface ReportDataAnalysisService {


	List<Dic> queryDic(Dic dic);

	List<LabelInfo> querylabelInfoList(List<AuthRole> authRoles);

	List<TypeTop> queryAnalysis(String repLab,String operationUser);

	List<LabelInfo> queryDataLabel(List<AuthRole> authRoles);

	List<TypeTop> queryReportInfo(List<AuthRole> authRoles);

	int addReportInfo(TypeTop typeTop);

	List<TypeTop> queryReportBy(TypeTop typeTop);

	List<TypeTop> isReportDo(TypeTop typeTop);

	int deleteReport(TypeTop typeTop);

	Report getReportByIdAndType(String reportid);

	Map<String, Object> getOrgInfo(String usrId);

	Report getRoleReport(String reportid, String userId);

	PmTableauUser getTableauUser(String optype);

	void addCollectInfo(PmReportCollect collect);

	void deleteCollectInfo(PmReportCollect collect);

	List<AuthRole> queryRoleById(String operationUser);



	List<RepAttributeInfo> queryrepAttributeInfo(String operationUser);

	List<TypeTop> queryReportByUser(String operationUser);

	List<TypeTop> getSCSort();

	List<TypeTop> getSCBSort(String operationUser);

	List<TypeTop> getFXBSort(String operationUser);

	List<TypeTop> queryAnalysis2(String operationUser);
	
	//收藏的报表列表
	List<CollectBo> countCollections(String loginid);

	//置顶
	boolean collectToTop(CollectBo collectBo);

	//取消收藏
	int collectDelete(CollectBo collectBo);

	//添加首页
	int addHomepage(String reportid);

	//查询首页
	ReportHomepage getHomepage(String userid);

	//取消首页
	int deleteHomepage();

	//报表菜单树
	List<LabelInfo> queryReportMenuList(List<AuthRole> authRoles);

	//获取站点
	String getUrlNamespace(String siteid);

}
