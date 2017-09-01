package byit.tableausubscribe.tab.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import byit.tableausubscribe.tab.bean.ReportSubscribe;
import byit.tableausubscribe.tab.bean.SendResult;
import byit.tableausubscribe.tab.service.PushReportService;

/**
 * 报表订阅
 * 
 * @author liyanqiu
 */
@Controller
@RequestMapping("/tab/pushReport")
public class PushReportController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(PushReportController.class);
	
	@Autowired
	private PushReportService pushService;
	

	/**
	 * 报表订阅列表--首页列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querySubscribeList")
	@ResponseBody
	public List<ReportSubscribe> querySubscribeList(HttpServletRequest request, HttpServletResponse response) {
		////logger.info("------------------------------进入querySubscribeList-----------------------------------------");
		String searchValue = request.getParameter("searchValue");
		List<ReportSubscribe> resultList = new ArrayList<>();
		try {
			resultList = pushService.getReportSubscribeList(searchValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Constants="+Constants.CLASSPATH);
		//System.out.println("Constants="+Constants.tempPath);
		return resultList;
	}

	/**
	 * 查看推送结果--查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querySendResult")
	@ResponseBody
	public List<SendResult> querySendResult(HttpServletRequest request, HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		List<SendResult> resultList = new ArrayList<>();
		try {
			resultList = pushService.querySendResult(reportId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 查看订阅配置--查看
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querySubscribe")
	@ResponseBody
	public ReportSubscribe querySubscribe(HttpServletRequest request, HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		ReportSubscribe result = new ReportSubscribe();
		try {
			result = pushService.querySubscribe(reportId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteSubscribe")
	@ResponseBody
	public boolean deleteSubscribe(HttpServletRequest request, HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		boolean result = false;
		try {
			result = pushService.deleteReportSubscribe(reportId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addSubscribe")
	@ResponseBody
	public String addSubscribe(HttpServletRequest request, HttpServletResponse response, ReportSubscribe report) {
		String str ="添加失败";
		try {
			//logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+report.getReportId());
			str =  pushService.addReportSubscribe(report);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateSubscribe")
	@ResponseBody
	public String updateSubscribe(HttpServletRequest request, HttpServletResponse response, ReportSubscribe report) {
		String str ="修改失败！";
		try {
			////logger.info("---------------修改开始---------------"+report.getReportId());
			str = pushService.updateReportSubscribe(report);
			////logger.info("---------------try-catch 返回结果---------------"+str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		////logger.info("---------------返回结果---------------"+str);
		return str;
	}

	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @param response
	 * @author wangchunquan
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public String sendEmail(HttpServletRequest request, HttpServletResponse response, ReportSubscribe report) {
		////logger.info("-----------手动发送--------------------------------");
		String str ="发送失败";
		//获取选中的仪表板
		String reportId = request.getParameter("reportId");
		////logger.info("-----------手动发送-----------reportId-----------------"+reportId);
		str = pushService.sendEmail(reportId);
		////logger.info("-----------手动发送-----------str--------------------"+str);
		return str;
	}

}
