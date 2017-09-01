package byit.aladdin.workBook.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import byit.aladdin.dataAnalysis.service.ReportDataAnalysisService;
import byit.aladdin.workBook.entity.AuthOrgInfo;
import byit.aladdin.workBook.entity.UserOrgInfo;
import byit.aladdin.workBook.entity.Workbooks;
import byit.aladdin.workBook.entity.XmlTablesMain;
import byit.aladdin.workBook.service.WorkBookService;
import byit.osdp.base.service.AuthOrgService;
import byit.osdp.base.service.AuthUserService;
import net.sf.json.JSONObject;

/**
 * 对外提供接口辅助类
 */
@Controller
@RequestMapping("/vizportal")
public class VizportalController {
	@Autowired
	private WorkBookService workBookService;
	@Autowired
	private AuthUserService authUserService;
	@Autowired
	private AuthOrgService authOrgService;
	@Autowired
	private ReportDataAnalysisService reportDataAnalysisService;
	private final Logger logger = Logger.getLogger(VizportalController.class);
	/**
	* @Description: 工作簿刷新回调方法
	* @author wangxingfei   
	* @param @param workbookId
	* @param @return
	* @date 2017年6月23日 下午2:36:54 
	* @version V1.0
	 */
	//curl -d "workbookId=${1}" http://locahost/tableau/vizportal/api/refreshextracts-workbook
	@RequestMapping("/api/refreshextracts-workbook")
	@ResponseBody
	public Object refreshextractsWorkbook(String workbookIds) {
		try {
			//Long id = ConvertUtil.toLong(workbookId, 0L);
			List<Workbooks> list = workBookService.findRefreshTime(workbookIds);
			//当查询结果不一致时返回哭错误信息
			if (list == null || list.isEmpty() || (list.size() != workbookIds.split(",").length)) {
				return "404";
			}
			/*	
			int timesByToday = xmlTablesMainService.getAndIncrementCountsByWorkbookId(workbookId);
			if (timesByToday == -1) {
				return "404";
			}
			if (timesByToday > 5) {
				return "409";
			}*/
			for (Workbooks workbooks2 : list) {
				//修改任务状态  任务更新时间改为最新
				XmlTablesMain main=new XmlTablesMain();
				main.setWorkbookId(workbooks2.getId().toString());
				main.setTaskState("1");
				main.setTaskRefTime(new Date().toString());
				workBookService.updateXmlTablesMainTaskStateById(main);
			}
			return "200";
		} catch (Exception e) {
			logger.error("!", e);
			return "500";
		}
	}
	
	@RequestMapping("/api/getusers")
	@ResponseBody
	public Object getusers() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserOrgInfo> UserOrgInfos = authUserService.findUserByUserName(null);
			if(UserOrgInfos != null && UserOrgInfos.size() > 0){
				map.put("state", "200");
				map.put("result", UserOrgInfos);
			}
		} catch (Exception e) {
			map.put("state", "500");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/api/getOrgs")
	@ResponseBody
	public Object getOrgs() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AuthOrgInfo> list = authOrgService.getAuthOrgAll();
			//List<UserOrgInfo> UserOrgInfos = authUserService.findUserByUserName(null);
			if(list != null && list.size() > 0){
				map.put("state", "200");
				map.put("result", list);
			}
		} catch (Exception e) {
			map.put("state", "500");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/api/getuserId")
	@ResponseBody
	public Object getuserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserOrgInfo> UserOrgInfos = authUserService.findUserByUserName(userId);
			if(UserOrgInfos != null && UserOrgInfos.size() > 0){
				map.put("state", "200");
				map.put("result", UserOrgInfos.get(0));
			}
		} catch (Exception e) {
			map.put("state", "500");
			e.printStackTrace();
		}
		return map;
	}
	public static void main(String[] args) throws Exception {
		HttpClient httpClient = new HttpClient();
		PostMethod getMethod = new PostMethod("http://localhost:8080/analportal/vizportal/api/getusers");
		httpClient.executeMethod(getMethod);
		String responseMsg = getMethod.getResponseBodyAsString();
		if (getMethod.getStatusCode() != HttpStatus.SC_OK) {
			System.out.println("Method failed: " + getMethod.getStatusLine());
		} else {
			System.out.println("success");
		}
		System.out.println(responseMsg);
		JSONObject s = JSONObject.fromObject(responseMsg);
		System.out.println(s.get("state"));
	}
}
