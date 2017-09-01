package byit.tableausubscribe.tab.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import byit.tableausubscribe.tab.service.BankService;
import byit.tableausubscribe.tab.service.TableauService;

/**
 * 
 * @Description: 获取视图、认证
 *
 * @author liyanqiu
 */
@Controller
@RequestMapping("/tab/subscribe")
public class TableauController  {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TableauController.class);
	@Autowired
	private TableauService tabService ;
	@Autowired
	private BankService bankService ;
	
	/**
	 * 
		 *@author lisw
		 *@Description: 跳转到报表订阅首页
		 *@creatTime:2017年5月31日 下午4:29:27 
		 *@return:@return ModelAndView
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	@RequestMapping(value="/index.html")
	public ModelAndView turnIndex(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("tableausubscribe/index");
		return mv;
	}
	/**
	 * 获取工作簿
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/allWorkbooks")
	@ResponseBody
	public Object queryAllWorkbooks(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		////logger.info("------------开始-----------");
		try{
			result.put("rows", tabService.getAllWorkbooks());
			result.put("success",1);
		}catch(Exception e){
			result.put("error", -1);
			result.put("msg", "加载数据失败");
			e.printStackTrace();
		}
		////logger.info("------------结束-----------");
		result.put("success",1);
		return result;
	}
	/**
	 * 获得仪表板
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getViewsByWorkBook")
	@ResponseBody
	public Object getViewsByWorkBook(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		////logger.info("------------开始-----------");
		String workBookId = request.getParameter("workBookId");
		try{
			result.put("rows", tabService.getViewsByWorkBook(workBookId));
			result.put("success",1);
		}catch(Exception e){
			result.put("error", -1);
			result.put("msg", "加载数据失败");
			e.printStackTrace();
		}
		////logger.info("------------结束-----------");
		result.put("success",1);
		return result;
	}
	/**
	 * 获取数据源
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDataResource")
	@ResponseBody
	public Object getDataResource(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		////logger.info("------------开始-----------");
		try{
			result.put("rows", bankService.getDataSource());
//			注释Oracle的访问
//			List<IdaPmReport> list = new ArrayList<>();
//			IdaPmReport report = new IdaPmReport();
//			report.setDatasourceId("NO");
//			report.setDatasourceName("不可用");
//			list.add(report);
//			result.put("rows", list);
			result.put("total", 10);
			result.put("success",1);
		}catch(Exception e){
			result.put("error", -1);
			result.put("msg", "加载数据失败");
			e.printStackTrace();
		}
		////logger.info("------------结束-----------");
		result.put("success",1);
		return result;
	}
}
