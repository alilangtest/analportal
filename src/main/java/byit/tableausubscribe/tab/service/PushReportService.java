package byit.tableausubscribe.tab.service;


import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import byit.osdp.base.security.UserHolder;
import byit.tableausubscribe.tab.bean.ReportSubscribe;
import byit.tableausubscribe.tab.bean.SendResult;
import byit.tableausubscribe.tab.dao.PushReportDao;
import byit.utils.Tools;


/** 
* Comments:操作XML文件---ok
*/
@Service
@Transactional
public class PushReportService extends EmailTask{
	private static final Logger logger = Logger.getLogger(PushReportService.class);
	
	@Autowired
	private PushReportDao reportDao;
	
	/*
	 * 获取订阅列表
	 */
	/**
		 *@note:lisw
		 *@modifier:
		 *@modifTime:
		 *@throws
		 *@description:获取首页仪表板列表数据
	 */
	public List<ReportSubscribe> getReportSubscribeList(String searchValue){
		List<ReportSubscribe> reusltSub = new ArrayList<>();
		reusltSub=reportDao.getReportSubscribeList(searchValue);
		 return reusltSub;
		
	}
	/*
	 * 获取订阅列表
	 */
	public ReportSubscribe querySubscribe(String reportId){
		ReportSubscribe reportSubscribe=reportDao.getSubscribe(reportId);
		return reportSubscribe;
	}
	/*
	 * 更新订阅规则
	 * 不能更新仪表板
	 */
	public  String updateReportSubscribe(ReportSubscribe report){
		String result =null;
		report.setUserId(UserHolder.getId());
		result=reportDao.updateReportSubscribe(report);
		return result;
	}
	/*
	 * 添加订阅规则
	 */
	public String addReportSubscribe(ReportSubscribe report) {
		String result = "添加成功！";
		String id = report.getReportId();
		if(id.endsWith(",")){
			report.setReportId(id.substring(0, id.length()-1));
		}
		try {
			//不让填重复，类似表的唯一键，依据仪表板id来判断
			ReportSubscribe reportSubscribe=reportDao.getSubscribe(report.getReportId());
			if(Tools.isNotEmpty(reportSubscribe.getReportId())){
				result="添加失败：该规则已存在！";
				return result;
			}
			report.setNextTime();
			report.setUserId(UserHolder.getId());
			boolean resultFlag= reportDao.addReportConfig(report);
			if(resultFlag){
				result="添加成功！";
			}else{
				result="添加失败！";
			}
			 logger.info("report:"+report+"");
			 //InitReportSubscribeConfig.reportSubscribeConfig.add(report);
		} catch (Exception e) {
			e.printStackTrace();
			result ="添加失败！";
		}
		return result;
	}
	/*
	 * 删除订阅规则
	 * 可以不删除发送结果
	 */
	public  boolean  deleteReportSubscribe( String reportId)  {
		////logger.info("------------ deleteReportSubscribe- -begin-----------");
		boolean result = false;
		result=reportDao.deleteReportSubscribe(reportId);
		return result;
	}
	/*
	 * 查看邮件发送结果
	 */
	public List<SendResult> querySendResult(String reportId){
		return reportDao.getSendResultList(reportId);
		//return InitReportSubscribeConfig.sendResultConfig.get(reportId);
	}
	/*private void deleteSendResult(String reportId) throws Exception{
		//reportOper.deleteSendResult(reportId);
		InitReportSubscribeConfig.sendResultConfig.remove(InitReportSubscribeConfig.sendResultConfig.get(reportId));
	}*/

	/*
	 * 查看邮件发送结果
	 */
	public String sendEmail(String reportId){
		String result = "发送失败！";
		//发送邮件
		////logger.info("-----------仪表板："+reportId+"------------------------");
		try {
			result = this.sendEmailByManual(reportId);
			////logger.info("-----------仪表板返回结果："+result+"------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		////logger.info("-----------这一步的result是什么呢？："+result+"------------------------");
		return result;
	}
	

}
