package byit.aladdin.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import byit.aladdin.report.bo.IdaPmReportMenuBo;
import byit.aladdin.report.bo.ReportMenuReportBo;
import byit.aladdin.report.bo.RoleReportMenuBo;
import byit.aladdin.report.entity.IdaPmReportMenu;
import byit.core.plug.mybatis.Mapper;
import byit.core.plug.mybatis.PageBounds;

@Mapper
public interface IdaPmReportMenuDao {

	//列表及查询
	List<IdaPmReportMenu> queryReportMenuList(IdaPmReportMenuBo idaPmReportMenuBo);
	
	//新增
	boolean saveReportMenu(IdaPmReportMenuBo idaPmReportMenuBo);

	//修改
	boolean updateReportMenu(IdaPmReportMenuBo idaPmReportMenuBo);

	//删除
	int deleteReportMenu(IdaPmReportMenuBo idaPmReportMenuBo);

	//验证id,名称唯一
	IdaPmReportMenuBo queryReportMenuBy(IdaPmReportMenuBo idaPmReportMenuBo);

	// 获取下级菜单
	List<IdaPmReportMenu> queryMenuList(IdaPmReportMenuBo reportMenuBo);

	//删除报表菜单与角色关系
	int deleteRoleRelateReportMenu(IdaPmReportMenuBo idaPmReportMenuBo);
	
	void deleteRoleReportMenuRelate(Map<String, String> map);
	
	//授权
	boolean RoleRelateReportMenu(Map<String, String> map);

	//查询所有可分配的角色
	List<Map<String, Object>> queryRoleList(Map<String, Object> params, PageBounds bounds);

	//查询已分配的角色
	List<Map<String, Object>> queryReportMenuRoleList(Map<String, Object> params, PageBounds bounds);

	//菜单树
	List<IdaPmReportMenu> queryMenuListTree(@Param("parentid")String parentid);

	// 回显角色与菜单的关系
	List<RoleReportMenuBo> queryRoleReportMenuList(RoleReportMenuBo roleReportMenuBo);

	//查询角色id
	List<String> queryReportMenuRoleListBymenuid(@Param("menuid")String menuid);

	//根据报表菜单查询报表
	List<ReportMenuReportBo> getReportByMenuid(String menuid);

	//删除报表与角色关系
	void deleteRoleReportRelate(Map<String, String> map1);

	//删除收藏报表
	void deleteCollect(String reportid);

	//删除首页报表
	void deleteHomepage(String reportid);

	


}
