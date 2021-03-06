package byit.aladdin.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;

//import byit.aladdin.question.controller.DqQusnfoController;
import byit.aladdin.report.bo.IdaPmReportBo;
import byit.aladdin.report.bo.IdaPmReportMenuBo;
import byit.aladdin.report.bo.ReportMenuReportBo;
import byit.aladdin.report.entity.IdaPmReportMenu;
import byit.aladdin.report.service.IdaPmReportMenuServiceImp;
import byit.aladdin.report.service.IdaPmReportServiceImpl;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.common.easyui.DataGridAdapter;




@Controller
@RequestMapping("/reportMenu")
public class ReportMenuController {
	@Autowired
	private IdaPmReportMenuServiceImp idaPmReportMenuServiceImp;
	@Autowired
	private IdaPmReportServiceImpl idaPmReportServiceImpl;
	@Autowired
	private DataGridAdapter dataGridAdapter;
	
	//报表菜单页面
	@RequestMapping(value = "/reportMenu.html")
	public String toreportMenu(){
		return "/report/reportMenu/reportMenu";
	}
	
	@RequestMapping(value = "/updateReportMenu.html")
	public String toupdateReportMenu(String menuid,HttpServletRequest request){
		// 根据id查询菜单
		IdaPmReportMenuBo idaPmReportMenuBo = this.idaPmReportMenuServiceImp.queryReportMenuById(menuid);
		request.setAttribute("menu",idaPmReportMenuBo);
		return "/report/reportMenu/updateReportMenu";
	}
	
	@RequestMapping(value = "/viewReportMenu.html")
	public String toviewReportMenu(){
		return "/report/reportMenu/viewReportMenu";
	}
	
	@RequestMapping(value = "/addReportMenu.html")
	public String toaddReportMenu(HttpServletRequest request){
		return "/report/reportMenu/addReportMenu";
	}
	@RequestMapping(value = "/relateRole.html")
	public String torelateRole(HttpServletRequest request,String menuid,String parentid){
		return "/report/reportMenu/relateRole";
	}
	//列表
	@RequestMapping(value = "/listReportMenu.do")
	@ResponseBody
	public Object listReportMenu(HttpServletRequest request,IdaPmReportMenuBo idaPmReportMenuBo){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<IdaPmReportMenuBo> listReportMenuBo=this.idaPmReportMenuServiceImp.queryReportMenuList(idaPmReportMenuBo);
			result.put("rows", listReportMenuBo);
			result.put("success", 1);
			
		} catch (Exception e) {
			result.put("error", -1);
			result.put("msg", "加载数据失败");
			e.printStackTrace();
		}
		return result;
	}
	
	//新增
	@RequestMapping(value = "/saveReportMenu")
    @ResponseBody
	public Map<String, Object> saveReportMenu(HttpServletRequest request,IdaPmReportMenuBo idaPmReportMenuBo) {
		boolean result;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			result = this.idaPmReportMenuServiceImp.saveReportMenu(idaPmReportMenuBo);
			if (result) {
				map.put("success", true);
	
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	//修改
	@RequestMapping(value = "/updateReportMenu")
	@ResponseBody
	public Map<String, Object> updateReportMenu(HttpServletRequest request,IdaPmReportMenuBo idaPmReportMenuBo) {
		boolean result;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			result = this.idaPmReportMenuServiceImp.updateReportMenu(idaPmReportMenuBo);
			if (result) {
				map.put("success", true);
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//验证id,名称唯一
	@RequestMapping(value = "/checkReportMenu")
    @ResponseBody
	public String checkReportMenu(HttpServletRequest request,IdaPmReportMenuBo idaPmReportMenuBo) {
		boolean result = this.idaPmReportMenuServiceImp.queryReportMenuBy(idaPmReportMenuBo);
		if(result){
			return "1";
		}else{
			return "0";
		}
	}
	
	//删除
	@RequestMapping(value = "/deleteReportMenu.do")
	@ResponseBody
	public Map<String, Object> deleteReportMenu(HttpServletRequest request,IdaPmReportMenuBo idaPmReportMenuBo) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		String ids = request.getParameter("ids");
		String[] split = ids.split(",");
		for (String id : split) {
			idaPmReportMenuBo.setMenuid(id);
			boolean deleteReportMenuresult = this.idaPmReportMenuServiceImp.deleteReportMenu(idaPmReportMenuBo);
			if(deleteReportMenuresult){
				//  删除系统菜单角色	
					result=this.idaPmReportMenuServiceImp.deleteRoleReportmenu(idaPmReportMenuBo);
			}
		}
		
		String msg = "删除菜单失败！";
		if (result) {
			msg = "删除菜单成功！";
		}
		map.put("success", result);
		map.put("msg", msg);
		return map;
	}
	
	
	//删除验证
	@RequestMapping(value = "/getReportMenu.do")
	@ResponseBody
	public Map<String, Object> getReportMenu(HttpServletRequest request,IdaPmReportMenuBo idaPmReportMenuBo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String ids = request.getParameter("ids");
		String[] reportMenuIds = ids.split(",");
		boolean result = true;
		String msg = "";
		for (int i = 0; i < reportMenuIds.length; i++) {
			IdaPmReportMenuBo reportMenuBo = new IdaPmReportMenuBo();
			reportMenuBo.setMenuid(reportMenuIds[i]);
			// 获取下级菜单
			List<IdaPmReportMenu> listidaPmReportMenu = this.idaPmReportMenuServiceImp.queryMenuList(reportMenuBo);
			// 如果存在下一级
			if (listidaPmReportMenu != null && listidaPmReportMenu.size() > 0) {
				msg = "删除菜单失败！选择的菜单中含有子菜单！";
				result = false;
				map.put("success", result);
				map.put("msg", msg);
				return map;
			}
			//获取报表
			List<ReportMenuReportBo> listidaPmReportBo = this.idaPmReportServiceImpl.queryReportListTrees(reportMenuBo);
			//如果有报表
			if (listidaPmReportBo != null && listidaPmReportBo.size() > 0) {
				msg = "删除菜单失败！该菜单下有报表！";
				result = false;
			}
		}
		map.put("success", result);
		map.put("msg", msg);
		return map;
	}
	
	
	
	//  查询所有可分配的角色
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/relateNotRoleList.do")
    @ResponseBody
    public Object relateNotRoleList(HttpServletRequest request,String menuid){
		Pagination pagination = dataGridAdapter.getPagination();
		Map<String, Object> result = new HashMap<String, Object>();
		//List<Object> roleList = this.idaPmReportMenuServiceImp.queryRoleList();
	
		try {
			Page<Map<String, Object>> page = (Page<Map<String, Object>>) this.idaPmReportMenuServiceImp.queryRoleList(pagination);
			//Page<IdaPmRole> treebo=this.idaPmReportMenuService.queryrelateNotRoleList(pageRequest,menuid);
			/*result.put("rows", treebo.getResult());
    		result.put("total", treebo.getTotalLength());*/
    		result.put("rows", page.getRecords());
    		result.put("total", page.getTotal());
    		result.put("success",1);

		} catch (Exception e) {
			result.put("error", -1);
            result.put("msg", "加载数据失败");
            e.printStackTrace();
		}
    	return result;
	}
	
	
	// 查询已分配的角色
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/relateInRoleList.do")
    @ResponseBody
    public Object relateInRoleList(HttpServletRequest request,String menuid){
		Pagination pagination = dataGridAdapter.getPagination();
		Map<String, Object> result = new HashMap<String, Object>();
		//List<Object> queryReportMenuRoleList = this.idaPmReportMenuServiceImp.queryReportMenuRoleList(menuid);
		//PageRequest pageRequest = getPage(request);
		try {
			Page<Map<String, Object>> page = (Page<Map<String, Object>>) this.idaPmReportMenuServiceImp.queryReportMenuRoleList(pagination);
			//Page<IdaPmRole> treebo=this.idaPmReportMenuService.queryrelateInRoleList(pageRequest,menuid);
			result.put("rows", page.getRecords());
    		result.put("total", page.getTotal());
    		result.put("success",1);
		} catch (Exception e) {
			result.put("error", -1);
            result.put("msg", "加载数据失败");
            e.printStackTrace();
		}
    	return result;
	}
	
	
	//  给菜单分配角色
	@RequestMapping(value = "/staffRelateRole.do")
    @ResponseBody
    public Map<String, Object> staffRelateRole(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		String leftRoleIds = request.getParameter("leftRoleIds");
		String rightRoleIds = request.getParameter("rightRoleIds");
		String menuid = request.getParameter("menuid");
		boolean result = false;
	//	result = this.idaPmReportMenuService.staffRelateRole(leftRoleIds,rightRoleIds,menuid);
		result = this.idaPmReportMenuServiceImp.RoleRelateReportMenu(leftRoleIds,rightRoleIds, menuid);
		String msg = "分配角色失败！";
		if(result){
			msg = "分配角色成功！";
		}
		map.put("success", result);
		map.put("msg", msg);
		return map;
	}
	
//  给菜单父节点分配角色
	@RequestMapping(value = "/parentstaffRelateRole.do")
    @ResponseBody
    public Map<String, Object> parentstaffRelateRole(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
	//  String leftRoleIds = request.getParameter("leftRoleIds");
		String rightRoleIds = request.getParameter("rightRoleIds");
		String menuid = request.getParameter("menuid");
		boolean result = false;
	//	result = this.idaPmReportMenuService.staffRelateRole(leftRoleIds,rightRoleIds,menuid);
		result = this.idaPmReportMenuServiceImp.parentstaffRelateRole(rightRoleIds, menuid);
		String msg = "分配角色失败！";
		if(result){
			msg = "分配角色成功！";
		}
		map.put("success", result);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping(value = "/queryMenuTree")
	@ResponseBody
	public Object queryMenuTree(HttpServletRequest request, String menuid) {
		// 角色id
		String orgid = request.getParameter("orgid");
		//setLoginUser(request, idaPmReportMenuService);
		//setLoginUser(request, idaPmReportService);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		// 获取根目录机构
		List<IdaPmReportMenuBo> treebo = this.idaPmReportMenuServiceImp.queryMenuListTree(null);
		// 循环根目录取下级机构
		for (IdaPmReportMenuBo menuBo : treebo) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", menuBo.getMenuid());
			item.put("text", menuBo.getMenuname());
			// 获取菜单下的报表
			List<IdaPmReportBo> reportlist = this.idaPmReportServiceImpl.queryReportListTree(menuBo.getMenuid());
			List<Map<String, Object>> reportitem = new ArrayList<Map<String, Object>>();
			if (reportlist != null && reportlist.size() > 0) {
				for (IdaPmReportBo idaPmReportBo : reportlist) {
					//RoleReportBo bo = this.idaPmReportService.queryRoleReport(orgid,idaPmReportBo.getReportId());
					// 报表权限
					Map<String, Object> reportmenu = new HashMap<String, Object>();
					reportmenu.put("id", idaPmReportBo.getReportId());
					reportmenu.put("text", idaPmReportBo.getReportname());

					List<Map<String, Object>> tableau = new ArrayList<Map<String, Object>>();
					// 查看权限
					Map<String, Object> item1 = new HashMap<String, Object>();
					item1.put("id", idaPmReportBo.getReportId() + "_1");
					item1.put("text", "查看");
					/*if (bo != null && "1".equals(bo.getOptype())) {
						item1.put("checked", true);
					}*/
					tableau.add(item1);
					// 下载权限
					// 编辑权限
					Map<String, Object> item3 = new HashMap<String, Object>();
					item3.put("id", idaPmReportBo.getReportId() + "_2");
					item3.put("text", "下载");
					/*if (bo != null && "2".equals(bo.getOptype())) {
						item3.put("checked", true);
					}*/
					tableau.add(item3);
					Map<String, Object> item2 = new HashMap<String, Object>();
					item2.put("id", idaPmReportBo.getReportId() + "_3");
					item2.put("text", "编辑");
					/*if (bo != null && "3".equals(bo.getOptype())) {
						item2.put("checked", true);
					}*/
					tableau.add(item2);
					reportmenu.put("state", "closed");
					reportmenu.put("children", tableau);
					// 如果attributes为1 则为报表
					// 在菜单下添加tableau用户权限选项
					reportitem.add(reportmenu);
				}
				// 获取次级机构
				List<IdaPmReportMenuBo> nexttreebo = this.idaPmReportMenuServiceImp.queryMenuListTree(menuBo.getMenuid());
				// 如果存在次级机构
				if (nexttreebo != null && nexttreebo.size() > 0) {
					List<Map<String, Object>> nextjson=toJsons(nexttreebo, orgid);
					for (Map<String, Object> map : nextjson) {
						reportitem.add(map);
					}
				}
				item.put("state", "open");
				item.put("children", reportitem);
			}else{
				// 获取次级机构
				List<IdaPmReportMenuBo> nexttreebo = this.idaPmReportMenuServiceImp.queryMenuListTree(menuBo.getMenuid());
				// 如果存在次级机构
				if (nexttreebo != null && nexttreebo.size() > 0) {
					item.put("state", "open");
					item.put("children", toJsons(nexttreebo, orgid));
				} 
			}
			items.add(item);
		}
		
		return items;
	}
	public List<Map<String, Object>> toJsons(List<IdaPmReportMenuBo> treelist,String orgid) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		// 遍历
		for (IdaPmReportMenuBo menuBo : treelist) {
			// 获取下级机构
//			List<IdaPmReportMenuBo> nexttreebo = this.idaPmReportMenuService.queryMenuListTree(menuBo.getMenuid());
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", menuBo.getMenuid());
			item.put("text", menuBo.getMenuname());
			// 获取菜单下的报表
			List<IdaPmReportBo> reportlist = this.idaPmReportServiceImpl.queryReportListTree(menuBo.getMenuid());
			List<Map<String, Object>> reportitem = new ArrayList<Map<String, Object>>();
			if (reportlist != null && reportlist.size() > 0) {
				for (IdaPmReportBo idaPmReportBo : reportlist) {
					//RoleReportBo bo = this.idaPmReportService.queryRoleReport(orgid,idaPmReportBo.getReportId());
					// 报表权限
					Map<String, Object> reportmenu = new HashMap<String, Object>();
					reportmenu.put("id", idaPmReportBo.getReportId());
					reportmenu.put("text", idaPmReportBo.getReportname());

					List<Map<String, Object>> tableau = new ArrayList<Map<String, Object>>();
					// 查看权限
					Map<String, Object> item1 = new HashMap<String, Object>();
					item1.put("id", idaPmReportBo.getReportId() + "_1");
					item1.put("text", "查看");
					/*if (bo != null && "1".equals(bo.getOptype())) {
						item1.put("checked", true);
					}*/
					tableau.add(item1);
					// 下载权限
					Map<String, Object> item3 = new HashMap<String, Object>();
					item3.put("id", idaPmReportBo.getReportId() + "_2");
					item3.put("text", "下载");
					/*if (bo != null && "2".equals(bo.getOptype())) {
						item2.put("checked", true);
					}*/
					tableau.add(item3);
					// 编辑权限
					Map<String, Object> item2 = new HashMap<String, Object>();
					item2.put("id", idaPmReportBo.getReportId() + "_3");
					item2.put("text", "编辑");
					/*if (bo != null && "3".equals(bo.getOptype())) {
						item3.put("checked", true);
					}*/
					tableau.add(item2);
					// 发布权限
					reportmenu.put("state", "closed");
					reportmenu.put("children", tableau);
					// 如果attributes为1 则为报表
					// 在菜单下添加tableau用户权限选项
					reportitem.add(reportmenu);
				}
				// 获取次级机构
				List<IdaPmReportMenuBo> nexttreebo1 = this.idaPmReportMenuServiceImp.queryMenuListTree(menuBo.getMenuid());
				// 如果存在次级机构
				if (nexttreebo1 != null && nexttreebo1.size() > 0) {
					List<Map<String, Object>> nextjson=toJsons(nexttreebo1, orgid);
					for (Map<String, Object> map : nextjson) {
						reportitem.add(map);
					}
				}
				item.put("state", "open");
				item.put("children", reportitem);
			}else{
				// 获取次级机构
				List<IdaPmReportMenuBo> nexttreebo1 = this.idaPmReportMenuServiceImp.queryMenuListTree(menuBo.getMenuid());
				// 如果存在次级机构
				if (nexttreebo1 != null && nexttreebo1.size() > 0) {
					item.put("state", "open");
					item.put("children", toJsons(nexttreebo1, orgid));
				} 
			}
			items.add(item);
		}
		return items;
	}
	
}
