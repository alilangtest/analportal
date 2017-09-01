package byit.aladdin.workBook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import byit.aladdin.workBook.entity.WorkbookPlanTaskVo;
import byit.aladdin.workBook.service.WorkBookService;
import byit.aladdin.workBook.util.Pagination;

/**
* @Description: TODO(用一句话描述该文件做什么) 
* @author wangxingfei   
* @date 2017年6月16日 下午3:58:03 
* @version V1.0
 */
@Controller
@RequestMapping(value = "/work-book")
public class WorkBookController {
	@Autowired
	private WorkBookService workBookService;
	@RequestMapping(value = "/workBookList.html")
	private ModelAndView workBookList(HttpServletRequest request, ModelMap mode) {
		ModelAndView model = new ModelAndView();
		model.setViewName("work-book/workbook-task-list");
		return model;
	}
	@RequestMapping(value = "/workbooktaskPostPage.html")
	private ModelAndView workbooktaskPostPage(HttpServletRequest request, ModelMap mode) {
		ModelAndView model = new ModelAndView();
		model.setViewName("work-book/workbook-task-post");
		return model;
	}
	/**
	* @Description: 根据条件查询分页总条数 
	* @author wangxingfei   
	* @param @param request
	* @param @param response
	* @param @return
	* @date 2017年6月20日 上午11:16:41 
	* @version V1.0
	 */
	@RequestMapping("/pagedQueryCount.do")
	@ResponseBody
	public Object pagedQueryCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pagination pagination = new Pagination();
		pagination.getFilters().put("siteId", request.getParameter("siteId"));
		pagination.getFilters().put("projectId", request.getParameter("projectId"));
		pagination.getFilters().put("reportName", request.getParameter("reportName"));
		//查询分页总条数
		int count = workBookService.pagedQueryCount(pagination);
		map.put("count", count);
		pagination.setStart(Integer.valueOf(request.getParameter("start")));
		pagination.setLimit(Integer.valueOf(request.getParameter("limit")));
		//查询第一页数据
		List<WorkbookPlanTaskVo> lists = workBookService.pagedQuery(pagination);
		map.put("lists", lists);
		return map;
	}
	@RequestMapping("/pagedQuery.do")
	@ResponseBody
	public Object pagedQuery(HttpServletRequest request, HttpServletResponse response) {
		Pagination pagination = new Pagination(Integer.valueOf(request.getParameter("start")), Integer.valueOf(request.getParameter("limit")));
		pagination.getFilters().put("siteId", request.getParameter("siteId"));
		pagination.getFilters().put("projectId", request.getParameter("projectId"));
		pagination.getFilters().put("reportName", request.getParameter("reportName"));
		List<WorkbookPlanTaskVo> lists = workBookService.pagedQuery(pagination);
		return lists;
	}
	@RequestMapping("/findBySiteId.do")
	@ResponseBody
	public Object findBySiteId(Long siteId){
		return workBookService.findBySiteId(siteId);
	}
	@RequestMapping("/findAll.do")
	@ResponseBody
	public Object findAll(){
		return workBookService.findAll();
	}
	@RequestMapping("/confirm.do")
	@ResponseBody
	public Object confirm(WorkbookPlanTaskVo vo) {
		//xmlTablesService
		return workBookService.Updateconfirm(vo);
	}
	@RequestMapping("/queryByWorkbookId.do")
	@ResponseBody
	public Object queryByWorkbookId(String workbookId) {
		return workBookService.queryXmlTablesByWorkBookId(workbookId);
	}
}
