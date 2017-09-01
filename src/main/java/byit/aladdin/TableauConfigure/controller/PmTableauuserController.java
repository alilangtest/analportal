package byit.aladdin.TableauConfigure.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import byit.aladdin.TableauConfigure.entity.PmTableauuserEntity;
import byit.aladdin.TableauConfigure.service.PmTableauuserService;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.common.easyui.DataGridAdapter;

/**
 *Tableau服务器配置>Tableau权限设置controller层
 * @author tanghuabo
 */
@Controller
@RequestMapping(value = "/tableauuser")
public class PmTableauuserController {
	// ==============================Fields===========================================
	@Autowired
	private PmTableauuserService tableauuserService;
	@Autowired
	private DataGridAdapter dataGridAdapter;
	// ==============================Method===========================================
	/**
	 * 跳到列表页面
	 */
	@RequestMapping(value = "/tableauUser.html")
	private String main(HttpServletRequest request, ModelMap mode) {
		return "tableau/tableauUser";
	}
	@RequestMapping(value = "/tableauUserAdd.html")
	private String add(HttpServletRequest request, ModelMap mode) {
		return "tableau/tableauUserAdd";
	}
	@RequestMapping(value = "/tableauUserUpdate.html")
	private String update(HttpServletRequest request, ModelMap mode) {
		return "tableau/tableauUserUpdate";
	}
	@RequestMapping(value = "/tableauUserView.html")
	private String view(HttpServletRequest request, ModelMap mode) {
		return "tableau/tableauUserView";
	}
	
	
	/**
	 * Tableau权限设置列表及模糊查询
	 * @return
	 */
	@RequestMapping(value = "/pageQuery.do")
	@ResponseBody
	private Object getUserList() {
		Pagination pagination = dataGridAdapter.getPagination();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page<Map<String, Object>> page = (Page<Map<String, Object>>) this.tableauuserService.getPagedQuery(pagination);
		    result.put("rows", page.getRecords());
    		result.put("total", page.getTotal());
    		result.put("success",1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  result;
	
	}
	
	/**
	 * 新增或修改table权限设置
	 * @param map 前台传入的要保存数据
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate.do")
	@ResponseBody
	private Object saveUserList(HttpServletRequest request,PmTableauuserEntity tableauUser) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			tableauuserService.saveOrUpdate(tableauUser);
    		result.put("flag",true);
		} catch (Exception e) {
			result.put("flag",false);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除操作
	 * @param idArray 前台传入id数组 接口信息表ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tableauUserRemove")
	private Object removeOpenApiInfo(String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(ids !=null && !ids.equals("")){
			try {
				tableauuserService.removeAll(ids);
	    		result.put("flag",true);
			} catch (Exception e) {
				result.put("flag",false);
				e.printStackTrace();
			}
		}else{
			result.put("flag",false);
		}
		return result;
	}
	
	// 值域类型名称
	@RequestMapping(value = "/querySystemDomainByDomainid")
	@ResponseBody
	public Object querySystemDomainByDomainid() {
		return this.tableauuserService.querySystemDomainByDomainid();
	}
	
}
