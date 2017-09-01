package byit.aladdin.queryReport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

import byit.aladdin.queryReport.dao.QueryReportMenuDao;
import byit.aladdin.queryReport.bo.IdaPmReportMenuBo;
import byit.aladdin.queryReport.bo.RoleReportMenuBo;
import byit.aladdin.queryReport.entity.IdaPmReportMenu;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.security.UserHolder;






@Service("queryReportMenuServiceImp")
public class QueryReportMenuServiceImp {
	
	@Autowired
	private QueryReportMenuDao queryReportMenuDao;
	
	//列表及查询
	public List<IdaPmReportMenuBo> queryReportMenuList(IdaPmReportMenuBo idaPmReportMenuBo) {
		List<IdaPmReportMenuBo> bolist=new ArrayList<IdaPmReportMenuBo>();
		List<IdaPmReportMenu> reportMenulist=this.queryReportMenuDao.queryReportMenuList(idaPmReportMenuBo);
		for (IdaPmReportMenu idaPmReportMenu : reportMenulist) {
			bolist.add(this.convert(idaPmReportMenu));
		}
		return bolist;
	}
	
	//新增
	public boolean saveReportMenu(IdaPmReportMenuBo idaPmReportMenuBo) {
		boolean result = this.queryReportMenuDao.saveReportMenu(idaPmReportMenuBo);
		return result;
	}
	
	//修改
	public boolean updateReportMenu(IdaPmReportMenuBo idaPmReportMenuBo) {
		boolean result = this.queryReportMenuDao.updateReportMenu(idaPmReportMenuBo);
		return result;
	}
	
	//删除
	public boolean deleteReportMenu(IdaPmReportMenuBo idaPmReportMenuBo) {
		int i = this.queryReportMenuDao.deleteReportMenu(idaPmReportMenuBo);
		if(i>0){
			return true;
		}else{
			return true;
		}
	}
	
    //删除报表菜单角色	
	public boolean deleteRoleReportmenu(IdaPmReportMenuBo idaPmReportMenuBo) {
		int i  = this.queryReportMenuDao.deleteRoleRelateReportMenu(idaPmReportMenuBo);
		if(i>0){
			return true;
		}else{
			return true;
		}
	}
	
	//验证id,名称唯一
	public boolean queryReportMenuBy(IdaPmReportMenuBo idaPmReportMenuBo) {
		//String sql="select MENUID,MENUPARENT,MENUNAME,MENUDESP,REM,URL,ISUSED from PM_REPORTMENU where MENUID=? or MENUNAME=?";
		IdaPmReportMenuBo idaPmReportMenu=this.queryReportMenuDao.queryReportMenuBy(idaPmReportMenuBo);
		if(idaPmReportMenu== null){
			return false;
		} else {
			return true;
		}
	}
	
	// 获取下级菜单
	public List<IdaPmReportMenu> queryMenuList(IdaPmReportMenuBo reportMenuBo) {
		//List<IdaPmReportMenuBo> listIdaPmReportMenuBo=new ArrayList<IdaPmReportMenuBo>();
		List<IdaPmReportMenu> listIdaPmReportMenu=this.queryReportMenuDao.queryMenuList(reportMenuBo);
		/*for (IdaPmReportMenu idaPmMenu : listIdaPmReportMenu) {
			listIdaPmReportMenuBo.add(this.convert(idaPmMenu));
		}*/
		return listIdaPmReportMenu;
	}
	
	//entity转bo
		private IdaPmReportMenuBo convert(IdaPmReportMenu idaPmReportMenu){
			IdaPmReportMenuBo idaPmReportMenuBo= new IdaPmReportMenuBo();
			idaPmReportMenuBo.setMenuid(idaPmReportMenu.getMenuId());
			idaPmReportMenuBo.setMenuname(idaPmReportMenu.getMenuname());
			idaPmReportMenuBo.setMenuparent(idaPmReportMenu.getMenuparent());
			idaPmReportMenuBo.setMenudesp(idaPmReportMenu.getMenudesp());
			idaPmReportMenuBo.setRem(idaPmReportMenu.getRem());
			idaPmReportMenuBo.setUrl(idaPmReportMenu.getUrl());
			idaPmReportMenuBo.set_parentId(idaPmReportMenu.getMenuparent());
			idaPmReportMenuBo.setOrdinal(idaPmReportMenu.getOrdinal());
			return idaPmReportMenuBo;
		}
	
	
 //报表菜单关联角色
	public boolean RoleRelateReportMenu(String leftRoleIds,String rightRoleIds,String menuid) {
		String[] rightRoleId = rightRoleIds.split(",");
		String[] leftRoleId = leftRoleIds.split(",");
		boolean result=true;
		try {
			//删除报表菜单与角色关系
			for (String roleid : leftRoleId) {
				Map<String, String> map = new HashMap<String,String>();
				map.put("roleid", roleid);
				map.put("menuid", menuid);
				this.queryReportMenuDao.deleteRoleReportMenuRelate(map);
				map.clear();
			}
			//授权
			for (String str : rightRoleId) {
				if(str != null && !"".equals(str)){
					Map<String, String> map = new HashMap<String,String>();
					map.put("eventid",UUID.randomUUID().toString());
					map.put("roleid", str);
					map.put("menuid", menuid);
					result =this.queryReportMenuDao.RoleRelateReportMenu(map);
					map.clear();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
	
	//报表菜单父节点关联角色
	public boolean parentstaffRelateRole(String rightRoleIds, String menuid) {
		// TODO Auto-generated method stub
		String[] rightRoleId = rightRoleIds.split(",");
		
		try {
			List<String> list = this.queryReportMenuDao.queryReportMenuRoleListBymenuid(menuid);
			
			for (String str : rightRoleId) {
				if(str != null && !"".equals(str)){
					if(!list.contains(str)){
						Map<String, String> map = new HashMap<String,String>();
						map.put("eventid",UUID.randomUUID().toString());
						map.put("roleid", str);
						map.put("menuid", menuid);
						this.queryReportMenuDao.RoleRelateReportMenu(map);
						map.clear();
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//查询所有可分配的角色
	public Object queryRoleList(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		String userid=UserHolder.getId();
		params.put("userid", userid);
		//角色名称
		String roleName = pagination.getFilters().getString("roleName");
		if(!StringUtils.isEmpty(roleName)){
			params.put("roleName", roleName);
		}
		//获得分页
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());
		Page<Map<String, Object>> page = bounds.wrap(this.queryReportMenuDao.queryRoleList(params, bounds));
		return page;
	}
	
	// 查询已分配的角色
	public Object queryReportMenuRoleList(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		String menuid=pagination.getFilters().get("menuid").toString();
		String userid=UserHolder.getId();
		params.put("userid", userid);
		params.put("menuid", menuid);
		//获得分页
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());
		Page<Map<String, Object>> page = bounds.wrap(this.queryReportMenuDao.queryReportMenuRoleList(params, bounds));
		return page;
	}

	//修改回显
	public IdaPmReportMenuBo queryReportMenuById(String menuid) {
		IdaPmReportMenuBo idaPmReportMenuBo = new IdaPmReportMenuBo();
		idaPmReportMenuBo.setMenuid(menuid);
		IdaPmReportMenuBo idaPmReportMenu=this.queryReportMenuDao.queryReportMenuBy(idaPmReportMenuBo);
		return idaPmReportMenu;
	}
	
	//菜单树
	public List<IdaPmReportMenuBo> queryMenuListTree(String parentid) {
		List<IdaPmReportMenuBo> menubo=new ArrayList<IdaPmReportMenuBo>();
		List<IdaPmReportMenu> menu=this.queryReportMenuDao.queryMenuListTree(parentid);
		for (IdaPmReportMenu idaPmMenu : menu) {
			menubo.add(this.convert(idaPmMenu));
		}
		return menubo;
	}
	
	// 回显角色与菜单的关系
	public List<RoleReportMenuBo> queryRoleReportMenuList(String menuid) {
		RoleReportMenuBo roleReportMenuBo = new RoleReportMenuBo();
		roleReportMenuBo.setMenuid(menuid);
		return  this.queryReportMenuDao.queryRoleReportMenuList(roleReportMenuBo);
	}

	

	
	

		
}
