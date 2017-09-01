package byit.tableausubscribe.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import byit.tableausubscribe.tab.bean.ExcelSubscribe;
import byit.tableausubscribe.tab.bean.ReportSubscribe;
import byit.utils.Tools;

/**
 * 
	 * 项目名称：hfportal   
	 * 类名称：Tools   
	 * 类描述：  常用工具类
	 * 创建人：lisw
	 * 创建时间：2017年5月19日 下午4:31:20   
	 * 修改人：
	 * 修改时间：2017年5月19日 下午4:31:20   
	 * 修改备注：   
	 * @version
 */
public class TableauTools {
	
	private static final Logger logger = Logger.getLogger(TableauTools.class);
	
	
	public static boolean isSend(Map<String,String>  map) {
		
		boolean isGreater=false;
		List<Boolean> isGreaterList=new ArrayList<Boolean>();
		for (String key : map.keySet()) {
			 logger.debug("key= " + key + " and value= " + map.get(key));
			 if(Tools.isNotEmpty(map.get(key))){
				 String time=map.get(key);
				 Date refreshedDate=Tools.formatDateStr(time);
				 
				 Calendar nowDate = Calendar.getInstance();
				 Date now=nowDate.getTime();
				 //计算当前时间和刷新时间的时间差，如果大于0说明当前时间大于刷新时间
				 long greater=Tools.timeDiff(now, refreshedDate);
				 
				 
				logger.debug(greater);
				//如果在当天并且时间差大于0，说明当前时间大于刷新时间。返回true。说明能发送邮件
				 if((Tools.getCurrFormatTimeDay(now).equals(Tools.getCurrFormatTimeDay(refreshedDate))) && (greater>0)){
					 isGreaterList.add(true);
					 isGreater=true;
				 }else{//时间差小于0，说明有可能没有刷新时间。返回false。不能发送邮件
					 isGreaterList.add(false);
					 return false;
				 }
			 }else{//刷新时间没有值。返回false。不能发送邮件
				 isGreaterList.add(false);
				 return false;
			 }
		 }
		return isGreater;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 如果当天没有数据，需要间隔十分钟扫描一次，在有数据并且邮件发送成功，或者超过当天晚上23点后修改方法内的三个字段，使其不再进行10分钟间隔一次的扫描
		 *@creatTime:2017年6月30日 上午12:51:42 
		 *@return:@param reportSubscribe
		 *@return:@return ReportSubscribe
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public static ReportSubscribe modifyReportSubscribePropCol(ReportSubscribe reportSubscribe){
		reportSubscribe.setIsTodaySend("");
		reportSubscribe.setPtempSendTime(null);
		reportSubscribe.setIsSendProp("");
		reportSubscribe.setUserId(Constants.USERID);
		return reportSubscribe;
	}
	
	
	public static ExcelSubscribe modifyExcelPropCol(ExcelSubscribe tExcelSubscribe){
		tExcelSubscribe.setIsTodaySend("");
		tExcelSubscribe.setPtempSendTime(null);
		tExcelSubscribe.setIsSendProp("");
		tExcelSubscribe.setUserId(Constants.USERID);
		return tExcelSubscribe;
	}
	
	public static void main(String[] args) {
		Map<String,String> refreshedMap=new HashMap<String, String>();
		refreshedMap.put("46","");
		refreshedMap.put("47","2017-06-16 16:24:00");
		refreshedMap.put("48","2017-06-16 16:35:00");
		//isSend(refreshedMap);
		System.out.println(isSend(refreshedMap));
	}
}
