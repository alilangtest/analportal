package byit.tableausubscribe.tab.service;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import byit.aladdin.dataIndex.util.ByitConfig;
import byit.osdp.base.entity.EmailEntity;
import byit.osdp.base.entity.SystemDoMainEntity;
import byit.osdp.base.service.SystemDoMainService;
import byit.osdp.tableau.TableauConfig;
import byit.tableausubscribe.common.util.Constants;
import byit.tableausubscribe.common.util.TableauInceptHelper;
import byit.tableausubscribe.common.util.TableauTicket;
import byit.tableausubscribe.common.util.TableauTools;
import byit.tableausubscribe.common.util.mail.MailSendPack;
import byit.tableausubscribe.common.util.mail.MailSenderInfo;
import byit.tableausubscribe.common.util.mail.MailUtils;
import byit.tableausubscribe.tab.bean.ExcelSubscribe;
import byit.tableausubscribe.tab.bean.IdaPmReport;
import byit.tableausubscribe.tab.bean.ReportSubscribe;
import byit.tableausubscribe.tab.bean.SendExcelResult;
import byit.tableausubscribe.tab.bean.SendResult;
import byit.tableausubscribe.tab.bean.SubscribeExcelType;
import byit.tableausubscribe.tab.bean.SubscribeType;
import byit.tableausubscribe.tab.dao.ExcelOper;
import byit.tableausubscribe.tab.dao.PushExcelDao;
import byit.tableausubscribe.tab.dao.PushReportDao;
import byit.tableausubscribe.tab.dao.TableauDao;
import byit.utils.FileOperation;
import byit.utils.Tools;

@Service
@Transactional
public class EmailTask {
	private String filePath = ByitConfig.FILE_UPLOAD_PATH;
	@Autowired
	private BankService bankService;
	@Autowired
	private TableauService tableauService;
	@Autowired
	private PushReportDao operDao;
	@Autowired
	private TableauDao tableauDao;
	@Autowired
	private PushExcelDao excelDao;
	@Autowired
	private SystemDoMainService systemDoMainService;
	//private String localIp = "127.0.0.1";//"25.0.88.163";//tableau信任的客户端
	private final SimpleDateFormat dateSdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private ExecutorService executor = Executors.newCachedThreadPool();
	private static final Logger logger = Logger.getLogger(EmailTask.class);

	public void execute(JobExecutionContext jobExecutionContext) {
		try {
			logger.info(Tools.getCurrFormatTimeGen()+"进入发送邮件的定时任务，当前时间:" + dateSdf.format(new Date()) + "");
			sendEmail();
			sendExcelEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 邮件推送 1->订阅规则是否该推送--时间 2>判断数据是否翻盘--判断条件_银行 3>邮件发送--保存到发送记录表
	 * 
	 * @throws Exception
	 * @author wangchunquan
	 */
	@SuppressWarnings("unused")
	public String sendEmailByManual(String reportId) throws Exception {
		logger.info(Tools.getCurrFormatTimeGen()+"-----------EmailTask-------- reportId=" + reportId);
		Calendar nowDate = Calendar.getInstance();
		String resultStr = "发送失败";
		// -----------------------遍历配置信息：是否需要发送邮件(时间类型、规则)------------------------------------------------//
		for (ReportSubscribe rs : operDao.getReportSubscribeList("")/*InitReportSubscribeConfig.reportSubscribeConfig*/) {
			// 判断是否为选中的仪表板
			if (reportId.equals(rs.getReportId())) {
				/*if (rs.getNextTime() == null) {// 时间格式不对，没有成功设置发送时间
					continue;
				}*/
				// 一分钟遍历一次
				if (SubscribeType.once.getIndex().equals(rs.getSendType())
						&& rs.getNextTime() != null) {
					isSendMailReport(rs);
					rs.setNextTime(null);// 代表已执行完毕
					rs.setSendState(Constants.SENDED);
					resultStr = "发送成功";
				} else if (SubscribeType.day.getIndex()
						.equals(rs.getSendType())
						&& nowDate.getTime().before(rs.getNextTime())) {
					isSendMailReport(rs);
					rs.setSendState(Constants.SENDED);
					resultStr = "发送成功";
				} else if (SubscribeType.hour.getIndex().equals(rs.getSendType())){//按小时发送
					logger.info("进入按小时发送,仪表板主题="+rs.getMailTitle()+",发送报表的workbookID="+rs.getWorkbookId()+",reportId="+rs.getReportId());
					isSendMailReport(rs);
				}else if (SubscribeType.week.getIndex().equals(
						rs.getSendType())
						&& nowDate.getTime().before(rs.getNextTime())) {

					logger.info(Tools.getCurrFormatTimeGen()+"每周：" + SubscribeType.week.getIndex()
							+ "；下次发送时间rs.getNextTime()：" + rs.getNextTime()
							+ "获取发送时间 rs.getSendTime()：" + rs.getSendTime());
					String[] str = rs.getSendTime().split("_");

					String[] dayx = str[0].split(",");
					logger.info(Tools.getCurrFormatTimeGen()+"dayx.length长度是：" + dayx.length + "内容是"
							+ dayx[0]);
					// 0->length-1
					// 获取今天是星期几
					Date date = new Date();
					SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
					String da = dateFm.format(date);
					if (da.equals("星期日")) {
						da = "1";
					} else if (da.equals("星期一")) {
						da = "2";
					} else if (da.equals("星期二")) {
						da = "3";
					} else if (da.equals("星期三")) {
						da = "4";
					} else if (da.equals("星期四")) {
						da = "5";
					} else if (da.equals("星期五")) {
						da = "6";
					} else if (da.equals("星期六")) {
						da = "7";
					}

					int countWeek = 0;
					int countWeek0 = 0;
					int countWeek1 = 0;
					int countWeek2 = 0;
					int countWeek3 = 0;
					int countWeek4 = 0;
					int countWeek5 = 0;

					long d = rs.getNextTime().getTime()
							- nowDate.getTime().getTime();
					// if (d <= 2 * 60 * 1000 && d > 0) {
					// 满足发送条件
					isSendMailReport(rs);
//					nowDate.setTime(rs.getNextTime());
					// nowDate.add(Calendar.DATE,7);
					// 判断今天是否等于所选的发送星期
					for (int y = 0; y < dayx.length; y++) {
						// 选定的星期等于今天
						if (dayx[y].equals(da)) {
							// 选择的天数的判断
							switch (dayx.length) {
							case 1:
								// 选1天
//								nowDate.add(Calendar.DATE, 7);
								break;
							case 2:
								// 选2天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek0 + 7 - countWeek1;
//									nowDate.add(Calendar.DATE, countWeek);
								}
								break;
							case 3:
								// 选3天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek0 + 7 - countWeek2;
//									nowDate.add(Calendar.DATE, countWeek);
								}
								break;
							case 4:
								// 选4天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek3 - countWeek2;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 3) {
									// 下标为3
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek0 + 7 - countWeek3;
//									nowDate.add(Calendar.DATE, countWeek);
								}
								break;
							case 5:
								// 选5天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek3 - countWeek2;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 3) {
									// 下标为3
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek = countWeek4 - countWeek3;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 4) {
									// 下标为4
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek = countWeek0 + 7 - countWeek4;
//									nowDate.add(Calendar.DATE, countWeek);
								}
								break;
							case 6:
								// 选6天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek3 - countWeek2;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 3) {
									// 下标为3
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek = countWeek4 - countWeek3;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 4) {
									// 下标为4
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek5 = Integer.parseInt(dayx[5]);
									countWeek = countWeek5 - countWeek4;
//									nowDate.add(Calendar.DATE, countWeek);
								} else if (y == 5) {
									// 下标为5
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek5 = Integer.parseInt(dayx[5]);
									countWeek = countWeek0 + 7 - countWeek5;
//									nowDate.add(Calendar.DATE, countWeek);
								}
								break;
							case 7:
								// 选7天
//								nowDate.add(Calendar.DATE, 1);
								break;
							}
						}
					}

//					rs.setNextTime(nowDate.getTime());
					rs.setSendState(Constants.SENDED);
					resultStr = "发送成功";
					// }

				} else if (SubscribeType.month.getIndex().equals(
						rs.getSendType())
						&& nowDate.getTime().before(rs.getNextTime())) {
					////logger.info(Tools.getCurrFormatTimeGen()+"-----------EmailTask --------------------------------一月遍历一次");
					isSendMailReport(rs);
//					nowDate.setTime(rs.getNextTime());
//					nowDate.add(Calendar.MONTH, 1);
//					rs.setNextTime(nowDate.getTime());
					rs.setSendState(Constants.SENDED);
					resultStr = "发送成功";
				} else if (SubscribeType.year.getIndex().equals(
						rs.getSendType())
						&& nowDate.getTime().before(rs.getNextTime())) {
					////logger.info(Tools.getCurrFormatTimeGen()+"-----------EmailTask --------------------------------一年遍历一次");
					isSendMailReport(rs);
//					nowDate.setTime(rs.getNextTime());
//					nowDate.add(Calendar.YEAR, 1);
//					rs.setNextTime(nowDate.getTime());
					rs.setSendState(Constants.SENDED);
					resultStr = "发送成功";
				}
			}
		}
		return resultStr;
	}

	/**
	 * 邮件推送 1->订阅规则是否该推送--时间 2>判断数据是否翻盘--判断条件_银行 3>邮件发送--保存到发送记录表
	 * 
	 * @throws Exception
	 */
	private void sendEmail() throws Exception {
		
		//首先判断当前时间是否大于指定时间，如果大于指定时间，将数据库中的发送状态字段中已发送的字段都改为未发送。之后当天再查询未发送状态的数据
		Date curr=Tools.formatDateStrMin(Tools.getCurrFormatTimeGen());
		Date modifyTime=Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.MODIFYSENDSTATETIME);
		Date donotModifyTime=Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.DONOTMODIFYSENDSTATETIME);
		
		//如果当前时间大于修改时间，并且小于不修改状态的时间。需要把数据库中的发送状态修改为未发送
		if((Tools.timeDiff(curr,modifyTime)>0)&&(Tools.timeDiff(curr,donotModifyTime)<0)){
			logger.info("当前时间大于当天修改发送状态的时间("+modifyTime+"),修改数据库中的发送状态为未发送");
			operDao.updateSendStatusSubscribe();
		}
		logger.info(Tools.getCurrFormatTimeGen()+"进入定时发送仪表板的任务，当前时间:" + dateSdf.format(new Date()) + "");
		Calendar nowDate = Calendar.getInstance();

		// -----------------------遍历配置信息：是否需要发送邮件(时间类型、规则)------------------------------------------------//
		//查找未发送的报表
		List<ReportSubscribe>  reportSubscribeList=operDao.getDNSReportSubscribeList();
		ReportSubscribe rs=new ReportSubscribe();
		Date sendNextTime=null;
		logger.info(Tools.getCurrFormatTimeGen()+"数据库中一共有【"+reportSubscribeList.size()+"】个报表");
		for (int i = 0; i < reportSubscribeList.size(); i++) {
			rs=reportSubscribeList.get(i);
			logger.info(Tools.getCurrFormatTimeGen()+"循环第【"+i+"】个报表，主题为："+rs.getMailTitle());
			//当天临时发送的时间和当前时间的时间差，当天数据未到位时，会给这个字段赋值，其他情况暂不赋值
			long d1 = 0L;
			if(rs.getTempSendTime()!=null){
				d1 = rs.getTempSendTime().getTime()- nowDate.getTime().getTime();
				logger.info("临时发送的时间和当前时间的时间差："+d1);
			}
			
			sendNextTime=rs.getNextTime();
			logger.info(Tools.getCurrFormatTimeGen()+"下次发送时间："+sendNextTime);
			//按小时发送没有下次发送时间
			if (rs.getNextTime() == null && !rs.getSendType().equals(SubscribeType.hour.getIndex())) {// 时间格式不对，没有成功设置发送时间
				continue;
			}
			// 一分钟遍历一次
			if (SubscribeType.once.getIndex().equals(rs.getSendType())
					&& rs.getNextTime() != null) {
				logger.info(Tools.getCurrFormatTimeGen()+"发送类型：仅发送一次。");
				long d = rs.getNextTime().getTime()
						- nowDate.getTime().getTime();
				// 提前一分钟发邮件2分钟 记录下次时间(必须),以防被遗漏
				if (d < Constants.ADVANCEMIN && d > 0) {
					// 满足发送条件
					isSendMailReport(rs);
					rs.setNextTime(null);// 代表已执行完毕
					rs.setSendState(Constants.SENDED);
				}
			} else if (SubscribeType.day.getIndex().equals(rs.getSendType())){
				logger.info(Tools.getCurrFormatTimeGen()+"每日发送!当前时间："+nowDate.getTime()+",下次发送时间："+rs.getNextTime());
				if(nowDate.getTime().before(rs.getNextTime())){//如果当前之间在下次发送之前
					
					long d = rs.getNextTime().getTime()- nowDate.getTime().getTime();
					//long d3 =Tools.timeDiff(rs.getNextTime(), nowDate.getTime());
					logger.info(Tools.getCurrFormatTimeGen()+"，当前时间在下次发送时间之前！当前时间=" + dateSdf.format(nowDate.getTime())+"，下次发送时间=" + dateSdf.format(rs.getNextTime()));
					if (d < Constants.ADVANCEMIN && d > 0) {
						logger.info("进入定时发送,仪表板主题="+rs.getMailTitle()+",发送报表的workbookID="+rs.getWorkbookId()+",reportId="+rs.getReportId());
						// 满足发送条件
						isSendMailReport(rs);
						nowDate.setTime(rs.getNextTime());
						nowDate.add(Calendar.DATE, 1);
						rs.setNextTime(nowDate.getTime());
						rs.setSendState(Constants.SENDED);
					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(rs.getTempSendTime()))) 
					&&
					("false".equals(rs.getIsTodaySend()))
					&&
					(d1 < Constants.ADVANCEMIN && d1 > 0)
				   ){
						logger.info("由于在指定时间报表数据未到位，再次进入扫描！");
						//判断当前时间和23:00:00的时间差，如果当前时间大于23:00:00。就不再进行下一步，直接修改数据库的3个字段使其不再进行十分钟间隔的扫描，
						if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
							rs=TableauTools.modifyReportSubscribePropCol(rs);
							operDao.updateReportSubscribeAfterSendEmail(rs);
						}else{//如果当前时间小于23点继续进行下一步
							//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
							isSendMailReport(rs);
							rs.setSendState(Constants.SENDED);
						}
					
				}
				
			}else if (SubscribeType.hour.getIndex().equals(rs.getSendType())){//按小时发送
				logger.info(Tools.getCurrFormatTimeGen()+"按小时发送!当前时间："+nowDate.getTime());
				String[] str = rs.getSendTime().split(",");
				List<String> list = java.util.Arrays.asList(str);
				for (int j = 0; j < list.size(); j++) {
					String sHourSnedTime=list.get(j);
					logger.debug("下次发送时间是："+sHourSnedTime);
					Date dHourSnedTime=Tools.FormatDateTimeHourMin(sHourSnedTime);
					if(nowDate.getTime().before(dHourSnedTime)){//如果当前之间在下次发送之前
						long d = dHourSnedTime.getTime()- nowDate.getTime().getTime();
						logger.info(Tools.getCurrFormatTimeGen()+"，当前时间在指定小时发送时间之前！当前时间=" + dateSdf.format(nowDate.getTime())+"，指定发送时间=" + sHourSnedTime);
						if (d < Constants.ADVANCEMIN && d > 0) {
							logger.info("进入定时发送,仪表板主题="+rs.getMailTitle()+",发送报表的workbookID="+rs.getWorkbookId()+",reportId="+rs.getReportId());
							// 满足发送条件
							isSendMailReport(rs);
						}
					}
				}
			}else if(SubscribeType.week.getIndex().equals(rs.getSendType())){
				if(nowDate.getTime().before(rs.getNextTime())){
					logger.info(Tools.getCurrFormatTimeGen()+"发送类型：每周发，下次发送时间rs.getNextTime()：" + rs.getNextTime()+"，每周发送时间："+rs.getSendTime());
					String[] str = rs.getSendTime().split("_");

					String[] dayx = str[0].split(",");
					logger.info(Tools.getCurrFormatTimeGen()+"每周发送的天数是：" + dayx.length + "，每周第一次发送的日期是："+ dayx[0]);
					// 0->length-1
					// 获取今天是星期几
					Date date = new Date();
					SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
					String da = dateFm.format(date);
					logger.info(Tools.getCurrFormatTimeGen()+"今天是："+da);
					if (da.equals("星期日")) {
						da = "1";
					} else if (da.equals("星期一")) {
						da = "2";
					} else if (da.equals("星期二")) {
						da = "3";
					} else if (da.equals("星期三")) {
						da = "4";
					} else if (da.equals("星期四")) {
						da = "5";
					} else if (da.equals("星期五")) {
						da = "6";
					} else if (da.equals("星期六")) {
						da = "7";
					}

					int countWeek = 0;
					int countWeek0 = 0;
					int countWeek1 = 0;
					int countWeek2 = 0;
					int countWeek3 = 0;
					int countWeek4 = 0;
					int countWeek5 = 0;

					long d = rs.getNextTime().getTime()- nowDate.getTime().getTime();
					logger.info(Tools.getCurrFormatTimeGen()+"发送与当前的时间差为："+d);
					//logger.info(Tools.getCurrFormatTimeGen()+"二分钟之内的时间差是："+Constants.ONEMIN);
					if (d < Constants.ADVANCEMIN && d > 0) {
						logger.info(Tools.getCurrFormatTimeGen()+"时间差在2分钟内");
						// 满足发送条件
						isSendMailReport(rs);
						nowDate.setTime(rs.getNextTime());
						// 判断今天是否等于所选的发送星期
						for (int y = 0; y < dayx.length; y++) {
							// 选定的星期等于今天
							if (dayx[y].equals(da)) {
								// 选择的天数的判断
								switch (dayx.length) {
								case 1:
									// 选1天
									nowDate.add(Calendar.DATE, 7);
									break;
								case 2:
									// 选2天
									if (y == 0) {
										// 下标为0
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek = countWeek1 - countWeek0;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 1) {
										// 下标为1
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek = countWeek0 + 7 - countWeek1;
										nowDate.add(Calendar.DATE, countWeek);
									}
									break;
								case 3:
									// 选3天
									if (y == 0) {
										// 下标为0
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek = countWeek1 - countWeek0;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 1) {
										// 下标为1
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek = countWeek2 - countWeek1;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 2) {
										// 下标为2
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek = countWeek0 + 7 - countWeek2;
										nowDate.add(Calendar.DATE, countWeek);
									}
									break;
								case 4:
									// 选4天
									if (y == 0) {
										// 下标为0
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek = countWeek1 - countWeek0;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 1) {
										// 下标为1
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek = countWeek2 - countWeek1;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 2) {
										// 下标为2
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek3 = Integer.parseInt(dayx[3]);
										countWeek = countWeek3 - countWeek2;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 3) {
										// 下标为3
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek3 = Integer.parseInt(dayx[3]);
										countWeek = countWeek0 + 7 - countWeek3;
										nowDate.add(Calendar.DATE, countWeek);
									}
									break;
								case 5:
									// 选5天
									if (y == 0) {
										// 下标为0
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek = countWeek1 - countWeek0;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 1) {
										// 下标为1
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek = countWeek2 - countWeek1;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 2) {
										// 下标为2
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek3 = Integer.parseInt(dayx[3]);
										countWeek = countWeek3 - countWeek2;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 3) {
										// 下标为3
										countWeek3 = Integer.parseInt(dayx[3]);
										countWeek4 = Integer.parseInt(dayx[4]);
										countWeek = countWeek4 - countWeek3;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 4) {
										// 下标为4
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek4 = Integer.parseInt(dayx[4]);
										countWeek = countWeek0 + 7 - countWeek4;
										nowDate.add(Calendar.DATE, countWeek);
									}
									break;
								case 6:
									// 选6天
									if (y == 0) {
										// 下标为0
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek = countWeek1 - countWeek0;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 1) {
										// 下标为1
										countWeek1 = Integer.parseInt(dayx[1]);
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek = countWeek2 - countWeek1;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 2) {
										// 下标为2
										countWeek2 = Integer.parseInt(dayx[2]);
										countWeek3 = Integer.parseInt(dayx[3]);
										countWeek = countWeek3 - countWeek2;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 3) {
										// 下标为3
										countWeek3 = Integer.parseInt(dayx[3]);
										countWeek4 = Integer.parseInt(dayx[4]);
										countWeek = countWeek4 - countWeek3;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 4) {
										// 下标为4
										countWeek4 = Integer.parseInt(dayx[4]);
										countWeek5 = Integer.parseInt(dayx[5]);
										countWeek = countWeek5 - countWeek4;
										nowDate.add(Calendar.DATE, countWeek);
									} else if (y == 5) {
										// 下标为5
										countWeek0 = Integer.parseInt(dayx[0]);
										countWeek5 = Integer.parseInt(dayx[5]);
										countWeek = countWeek0 + 7 - countWeek5;
										nowDate.add(Calendar.DATE, countWeek);
									}
									break;
								case 7:
									// 选7天
									nowDate.add(Calendar.DATE, 1);
									break;
								}
							}
						}

						rs.setNextTime(nowDate.getTime());
						rs.setSendState(Constants.SENDED);
					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(rs.getTempSendTime()))) 
					&&
					("false".equals(rs.getIsTodaySend()))
				 	&&
				 	(d1 < Constants.ADVANCEMIN && d1 > 0)){
						//判断当前时间和23:00:00的时间差，如果当前时间大于23:00:00。就不再进行下一步，直接修改数据库的3个字段使其不再进行十分钟间隔的扫描，
					if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
							rs=TableauTools.modifyReportSubscribePropCol(rs);
							operDao.updateReportSubscribeAfterSendEmail(rs);
						}else{//如果当前时间小于23点继续进行下一步
							//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
							isSendMailReport(rs);
							rs.setSendState(Constants.SENDED);
						}
					
				}
				
			} /*else if (SubscribeType.month.getIndex().equals(rs.getSendType())
					&& nowDate.getTime().before(rs.getNextTime())) {
				long d = rs.getNextTime().getTime()
						- nowDate.getTime().getTime();
				if (d <= 2 * 60 * 1000 && d > 0) {
					// 满足发送条件
					isSendMailReport(rs);
					nowDate.setTime(rs.getNextTime());
					nowDate.add(Calendar.MONTH, 1);
					rs.setNextTime(nowDate.getTime());
					rs.setSendState(Constants.SENDED);
				}
			}*/ 
			else if (SubscribeType.month.getIndex().equals(rs.getSendType())){
				if(nowDate.getTime().before(rs.getNextTime())){
					long d = rs.getNextTime().getTime()
							- nowDate.getTime().getTime();
					if (d < Constants.ADVANCEMIN && d > 0) {
						// 满足发送条件
						isSendMailReport(rs);
						nowDate.setTime(rs.getNextTime());
						nowDate.add(Calendar.MONTH, 1);
						rs.setNextTime(nowDate.getTime());
						rs.setSendState(Constants.SENDED);
					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(rs.getTempSendTime()))) 
						&&
						("false".equals(rs.getIsTodaySend()))
					 	&&
					 	(d1 < Constants.ADVANCEMIN && d1 > 0)){
							//判断当前时间和23:00:00的时间差，如果当前时间大于23:00:00。就不再进行下一步，直接修改数据库的3个字段使其不再进行十分钟间隔的扫描，
					if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
								rs=TableauTools.modifyReportSubscribePropCol(rs);
								operDao.updateReportSubscribeAfterSendEmail(rs);
							}else{//如果当前时间小于23点继续进行下一步
								//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
								isSendMailReport(rs);
								rs.setSendState(Constants.SENDED);
							}
				}
				
			}/*else if (SubscribeType.year.getIndex().equals(rs.getSendType())
					&& nowDate.getTime().before(rs.getNextTime())) {
				long d = rs.getNextTime().getTime()
						- nowDate.getTime().getTime();
				if (d <= 2 * 60 * 1000 && d > 0) {
					// 满足发送条件
					isSendMailReport(rs);
					nowDate.setTime(rs.getNextTime());
					nowDate.add(Calendar.YEAR, 1);
					rs.setNextTime(nowDate.getTime());
					rs.setSendState(Constants.SENDED);

				}
			}*/
			else if (SubscribeType.year.getIndex().equals(rs.getSendType())){
				if(nowDate.getTime().before(rs.getNextTime())){
					long d = rs.getNextTime().getTime()
							- nowDate.getTime().getTime();
					if (d < Constants.ADVANCEMIN && d > 0) {
						// 满足发送条件
						isSendMailReport(rs);
						nowDate.setTime(rs.getNextTime());
						nowDate.add(Calendar.YEAR, 1);
						rs.setNextTime(nowDate.getTime());
						rs.setSendState(Constants.SENDED);

					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(rs.getTempSendTime()))) 
					&&
					("false".equals(rs.getIsTodaySend()))
				 	&&
				 	(d1 < Constants.ADVANCEMIN && d1 > 0)){
						//判断当前时间和23:00:00的时间差，如果当前时间大于23:00:00。就不再进行下一步，直接修改数据库的3个字段使其不再进行十分钟间隔的扫描，
					if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
							rs=TableauTools.modifyReportSubscribePropCol(rs);
							operDao.updateReportSubscribeAfterSendEmail(rs);
						}else{//如果当前时间小于23点继续进行下一步
							//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
							isSendMailReport(rs);
							rs.setSendState(Constants.SENDED);
						}
					
				}
				
			}
		}
	}

	/**
	 * 邮件推送 1->订阅规则是否该推送--时间 2->判断数据是否翻盘--判断条件_银行 3>邮件发送--保存到发送记录表 附件：csv文件
	 * 
	 * @param reportSubscribe
	 *            订阅规则配置
	 * @throws Exception
	 */
	public String isSendMailReport(final ReportSubscribe reportSubscribe)
			throws Exception {
		logger.info(Tools.getCurrFormatTimeGen()+"开始准备发送");
		String result = "发送失败！";
		// 需要发送邮件的报表
		// 获取Tableau仪表板信息
		final List<IdaPmReport> reportList = tableauService.getViewDataSources(reportSubscribe.getReportId());
		logger.info(Tools.getCurrFormatTimeGen()+"，仪表板个数：" + reportList.size());
		if (reportList != null && reportList.size() > 0) {
			// 配置文件中的数据源
			Map<String, String> dataSources = reportSubscribe.getDataSources();
			logger.info(Tools.getCurrFormatTimeGen()+"，操作时选中的判定条件：" + dataSources);
			// 所有的数据源:
			List<IdaPmReport> dataSoruceList = bankService.getDataSource();
			logger.info(Tools.getCurrFormatTimeGen()+"，数据库中查询出来的判定条件：" + dataSoruceList);
			String dataSourcesStr = "";
			int count = 0;
			for (int i = 0; i < dataSoruceList.size(); i++) {
				IdaPmReport ds=new IdaPmReport();
				ds=dataSoruceList.get(i);
				if("Y".equals(dataSources.get(ds.getDatasourceId()))){
					dataSourcesStr += ",'" + ds.getDatasourceId() + "'";
					count++;
				}
			}
			logger.info(Tools.getCurrFormatTimeGen()+"，判定条件：" + dataSourcesStr);
			if (!"".equals(dataSourcesStr)) {
				// 因仪表板和判断条件没有关系，所以只要数据没有更新就都不发送
				int nowTime = bankService.isNewTableTime(dataSourcesStr.substring(1));
				if (nowTime == 0 || nowTime < count) {
					result = "数据没有更新，不发送！";
					ReportSubscribe tReportSubscribe=new ReportSubscribe();
					
					//如果数据不更新将当天发送失败、当天下次发送时间存储到数据库中，下次执行定时任务时依据当天下次发送时间来判断
					String isSendProp=reportSubscribe.getIsSendProp();
					//如果isSendProp这个字段为空或者为false。表示还暂发送提示邮件需要发送。如果为true表示已经发送过邮件了，下次不需要再次发送
					if((!Tools.isNotEmpty(isSendProp)) || ("false".equals(isSendProp))){
						//从数据库获取邮件title
						List<SystemDoMainEntity> title = systemDoMainService.getByDoMainIdList("EmailTitle");
						//从数据库获取邮件content
						List<SystemDoMainEntity> content = systemDoMainService.getByDoMainIdList("EmailContent");
						EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
						for (String email : reportSubscribe.getMailList()) {
							MailSenderInfo mailSenderInfo=new MailSenderInfo();
							mailSenderInfo.setToAddress(email);
							mailSenderInfo.setContent(content.get(0).getCodename().replace("#", reportSubscribe.getMailTitle()));
							mailSenderInfo.setSubject(reportSubscribe.getMailTitle()+title.get(0).getCodename());
							MailSendPack.sendContentEmailPack(mailSenderInfo,emailEntity);
						}
					}
					tReportSubscribe.setIsTodaySend("false");//已经发送过提示邮件了，需要将isTodaySend字段改为true
					tReportSubscribe.setIsSendProp("true");
					Long tnextSendTime=new Date().getTime();
					Long tempSendTime=tnextSendTime+Constants.TENMIN;
					tReportSubscribe.setReportId(reportSubscribe.getReportId());
					tReportSubscribe.setPtempSendTime((Tools.getCurrFormatTimeday(new Date(tempSendTime))));
					operDao.updateSendTimeSubscribe(tReportSubscribe);
					
					logger.info(Tools.getCurrFormatTimeGen()+result);
					
					// 只要有一个数据没更新就不发送邮件
					return result;
				}
			}
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						sendMail(reportList, reportSubscribe);
						// sendZipMail(reportList, reportSubscribe);
					} catch (Exception e) {
						e.printStackTrace();
					}

					logger.info(Tools.getCurrFormatTimeGen()+"~~~~~~~~~~~~~~~send~end~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}

			});
			result = "发送成功！";
		}
		return result;
	}

	/**
	 * 邮件推送 1->订阅规则是否该推送--时间 2->判断数据是否翻盘--判断条件_银行 3->邮件发送--保存到发送记录表
	 * 
	 * @param reportList
	 * @throws Exception
	 */
	private void sendMail(List<IdaPmReport> reportList,
			ReportSubscribe reportSubscribe) throws Exception {
		boolean isSend=false;
		List<String> tFilePathList=new ArrayList<String>();
		logger.info(Tools.getCurrFormatTimeGen()+"查询当天tableau数据是否更新");
		//就算数据有更新，也需要判断tableau中当天数据是否刷新
		//检查tableau数据是否刷新，如未刷新发送提示邮件，一个工作簿下多个仪表板，只要有一个仪表板数据不更新就不发送邮件
		Map<String,String> refreshedMap=tableauDao.getTableauRefreshed(reportSubscribe.getWorkbookId());
		logger.info(Tools.getCurrFormatTimeGen()+"该workbookId与查询出来的刷新时间："+refreshedMap);
		isSend=TableauTools.isSend(refreshedMap);
		logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"当天tableau数据是否更新："+isSend);
		if(isSend){//如果tanleauserver仪表板数据已刷新
			logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"当天tableau数据更新！");
			// 创建一个线程池
			// IdaPmReport有多个数据源，为了去重
			Map<String, IdaPmReport> mapIpr = new HashMap<>();
			Vector<String> fileNameStrings = new Vector<String>();
			String imagesStr = "";
			String reportName = "";
			String fileName = "";
			if (reportList.size() > 0 && reportSubscribe.getMailList().size() > 0) {
				logger.info(Tools.getCurrFormatTimeGen()+"reportList.size()为："+reportList.size()+"，reportSubscribe.getMailList().size()为："+reportSubscribe.getMailList().size());
				// 报表 多个
				for (IdaPmReport report : reportList) {
					if (mapIpr.get(report.getReportId()) == null) {
						mapIpr.put(report.getReportId(), report);
						String ticket = TableauTicket.getTrustedTicket(TableauConfig.TABLEAU_TRUSTED_HOST,TableauConfig.TABLEAU_SERVER_NAME,"");
						String params = ":embed=y&:tabs=no&:toolbar=top";
						logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"的tableau票据："+ticket);
						if (!ticket.equals("-1")) {
							// 取出的报表以图片的形式保存
							fileName = UUID.randomUUID().toString()+ "_"+ report.getUrl().substring(
											report.getUrl().lastIndexOf("/") + 1)+ ".png";
							logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"的fileName：" + fileName);
							
							tFilePathList.add(filePath + fileName);
							logger.info("将"+filePath + fileName+"添加到tFilePathList中，目前list中的图片的数量为"+tFilePathList.size()+"个");
							
							// 保存的图片的链接地址
							String url = "http://" + TableauConfig.TABLEAU_TRUSTED_HOST+ "/trusted/"
									+ ticket + "/views/" + report.getUrl()
									+ ".png?" + params;
							logger.info(Tools.getCurrFormatTimeGen()+"url：" + url);
							logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"~~~~~~~~~~~~~~~~~~~~开始将tableauurl数据转为字节~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							byte[] buffer = TableauInceptHelper.readResource(url);
							logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"~~~~~~~~~~~~~~~~~~~~将tableau数据转为字节结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							
							logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"准备将上一步中转化的字节数据写入流中");
							try {
								FileOutputStream os = new FileOutputStream(filePath+fileName);

								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"创建一个缓冲区，提高写入文件的效率");
								
								os.write(buffer, 0, buffer.length);
								
								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"将字节数据写入流中结束，但未关闭流");
								
								fileNameStrings.add(filePath + fileName);
								
								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"的fileNameStrings :" + fileNameStrings);
								
								imagesStr = imagesStr + "<img src='" + filePath	+ fileName + "'/>";
								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"的imagesStr:" + imagesStr + ";filePath:"+ filePath);
								
								reportName += "," + report.getReportName();
								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"的reportName:" + reportName);
								
								os.close();
								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"关闭流");
							} catch (Exception e) {
								e.printStackTrace();
								logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"流操作错误，错误详情："+e.getMessage());
							}
						}
					}
				}
				logger.info(Tools.getCurrFormatTimeGen()+"imagesStr end:" + imagesStr + ";filePath:" + filePath);
				logger.info(Tools.getCurrFormatTimeGen()+"reportName end:" + reportName);
				logger.info(Tools.getCurrFormatTimeGen()+"fileNameStrings end:" + fileNameStrings);
				if (fileNameStrings.size() > 0) {
					logger.info(Tools.getCurrFormatTimeGen()+"imgs=" + imagesStr);
					logger.info(Tools.getCurrFormatTimeGen()+"mails=" + reportSubscribe.getMailList());
					
					List<SendResult> sendResultList = new ArrayList<>();
					SendResult result;
					// 多个邮箱
					EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
					for (String email : reportSubscribe.getMailList()) {
						result = new SendResult();
						result.setReportId(reportSubscribe.getReportId());
						result.setEmailId(email);
						logger.info(Tools.getCurrFormatTimeGen()+"email：" + email);
						result.setSendTime(dateSdf.format(new Date()));
						result.setSendState("失败");
						try {
							MailUtils sendmail = new MailUtils();
							sendmail.setTo(email);
							sendmail.setSubject(reportSubscribe.getMailTitle());
							if ("1".equals(reportSubscribe.getSendDetails())) {
								// 发送附件
								for (String file : fileNameStrings) {
									sendmail.attachfile(file);
								}
								sendmail.setContent(reportSubscribe.getSendInfo());
								try {
									logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+",发送报表附件");
									boolean sendFlag=MailSendPack.sendWorkBookEmailPack(sendmail, reportSubscribe.getSendDetails(), null, reportName,emailEntity);
									if(sendFlag){
										result.setSendState("成功");
										logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"附件发送成功");
									}else{
										result.setSendState("失败");
										logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"附件发送失败");
									}
									
								} catch (Exception e) {
									result.setSendState("失败");
									logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"附件发送失败，程序出现问题，错误信息为"+e.getMessage());
									e.printStackTrace();
								}
							} else if ("2".equals(reportSubscribe.getSendDetails())) {
								// 发送内容
								try {
									logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+",发送报表内容");
									boolean sendFlag=MailSendPack.sendWorkBookEmailPack(sendmail, reportSubscribe.getSendDetails(), fileNameStrings, reportName,emailEntity);
									
									if(sendFlag){
										result.setSendState("成功");
										logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"内容发送成功");
									}else{
										result.setSendState("失败");
										logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"内容发送失败");
									}
								} catch (Exception e) {
									result.setSendState("失败");
									logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"内容发送失败，程序出现问题，错误信息为："+e.getMessage());
									e.printStackTrace();
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							result.setSendState("失败");
							logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"邮件发送失败，程序出现问题，错误信息为："+e.getMessage());
						}
						sendResultList.add(result);
					}

					//邮件发送后需要删除服务器上的文件
					
					for (int i = 0; i < tFilePathList.size(); i++) {
						FileOperation.dropFile(tFilePathList.get(i));
						logger.info("删除磁盘中的第"+i+"个文件:"+tFilePathList.get(i));
					}
					
					
					// 存储发送结果
					Map<String, List<SendResult>> sendResultMap = new HashMap<>();
					sendResultMap.put(reportSubscribe.getReportId(), sendResultList);
					operDao.addReportResult(sendResultMap);
					logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"将发送结果保存到结果表中！");

					// 修改仪表板主表数据
					reportSubscribe=TableauTools.modifyReportSubscribePropCol(reportSubscribe);
					operDao.updateReportSubscribeAfterSendEmail(reportSubscribe);
					logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"修改主表的数据！");
				}
			}
		}else{//如果tanleauserver仪表板数据未刷新，发送提示邮件
			logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"当天仪表板数据未更新！");
			//如果数据不更新将当天发送失败、当天下次发送时间存储到数据库中，下次执行定时任务时依据当天下次发送时间来判断
			String isSendProp=reportSubscribe.getIsSendProp();
			//如果isSendProp这个字段为空或者为false。表示还暂发送提示邮件需要发送。如果为true表示已经发送过邮件了，下次不需要再次发送
			if((!Tools.isNotEmpty(isSendProp)) || ("false".equals(isSendProp))){
				//从数据库获取邮件title
				List<SystemDoMainEntity> title = systemDoMainService.getByDoMainIdList("EmailTitle");
				//从数据库获取邮件content
				List<SystemDoMainEntity> content = systemDoMainService.getByDoMainIdList("EmailContent");
				EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
				for (String email : reportSubscribe.getMailList()) {
					MailSenderInfo mailSenderInfo=new MailSenderInfo();
					mailSenderInfo.setToAddress(email);
					mailSenderInfo.setContent(content.get(0).getCodename().replace("#", reportSubscribe.getMailTitle()));
					mailSenderInfo.setSubject(reportSubscribe.getMailTitle()+title.get(0).getCodename());
					MailSendPack.sendContentEmailPack(mailSenderInfo,emailEntity);
					logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"给"+email+"发送提示邮件！");
				}
			}else{
				logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"已发送过提示邮件，将不再发送");
			}
			
			ReportSubscribe tReportSubscribe=new ReportSubscribe();
			tReportSubscribe.setIsTodaySend("false");
			tReportSubscribe.setIsSendProp("true");
			Long tnextSendTime=new Date().getTime();
			Long tempSendTime=tnextSendTime+Constants.TENMIN;
			tReportSubscribe.setPtempSendTime((Tools.getCurrFormatTimeday(new Date(tempSendTime))));
			tReportSubscribe.setReportId(reportSubscribe.getReportId());
			operDao.updateSendTimeSubscribe(tReportSubscribe);
			logger.info(Tools.getCurrFormatTimeGen()+","+reportSubscribe.getWorkbookId()+"更新下次邮件发送时间！");
			
			
		}
		
	}

	/**************************************************** excel ************************************************************/

	/**
	 * 
	 * 
	 * @throws Exception
	 * @author
	 */
	public String sendExcelEmailByManual(String tableId) throws Exception {
		Calendar nowDate = Calendar.getInstance();
		String resultStrExcel = "发送失败";
		 List<ExcelSubscribe> ExcelSubscribeList=excelDao.getExcelSubscribeList("");
		 logger.info("数据库中有"+ExcelSubscribeList.size()+"个excel报表");
		 for (int i = 0; i < ExcelSubscribeList.size(); i++) {
			 ExcelSubscribe es =new ExcelSubscribe();
			 es=ExcelSubscribeList.get(i);
			if (tableId.equals(es.getTableId())) {
				logger.info(Tools.getCurrFormatTimeGen()+"-----if----------开始");
				if (es.getNextTime() == null) {// 时间格式不对，没有成功设置发送时间
					continue;
				}
				logger.info(Tools.getCurrFormatTimeGen()+"-----if----------结束");
				// 一分钟遍历一次
				if (SubscribeExcelType.once.getIndex().equals(
						es.getSendTypeExcel())
						&& es.getNextTime() != null) {
					isSendExcelMailReport(es);
					es.setNextTime(null);// 代表已执行完毕
					es.setSendStateExcel(Constants.SENDED);
					resultStrExcel = "发送成功";
				} else if (SubscribeExcelType.day.getIndex().equals(
						es.getSendTypeExcel())
						&& nowDate.getTime().before(es.getNextTime())) {
					isSendExcelMailReport(es);
					nowDate.setTime(es.getNextTime());
					nowDate.add(Calendar.DATE, 1);
					es.setNextTime(nowDate.getTime());
					es.setSendStateExcel(Constants.SENDED);
					resultStrExcel = "发送成功";
				} else if (SubscribeExcelType.week.getIndex().equals(
						es.getSendTypeExcel())
						&& nowDate.getTime().before(es.getNextTime())) {
					logger.info(Tools.getCurrFormatTimeGen()+"每周："
							+ SubscribeExcelType.week.getIndex()
							+ "；下次发送时间es.getNextTime()：" + es.getNextTime()
							+ "获取发送时间 es.getSendTimeExcel()："
							+ es.getSendTimeExcel());
					String[] strExcel = es.getSendTimeExcel().split("_");

					String[] dayxExcel = strExcel[0].split(",");
					logger.info(Tools.getCurrFormatTimeGen()+"dayxExcel.length长度是："
							+ dayxExcel.length + "内容是" + dayxExcel[0]);
					// 0->length-1
					// 获取今天是星期几
					Date dateExcel = new Date();
					SimpleDateFormat dateFmExcel = new SimpleDateFormat("EEEE");
					String daExcel = dateFmExcel.format(dateExcel);
					if (daExcel.equals("星期日")) {
						daExcel = "1";
					} else if (daExcel.equals("星期一")) {
						daExcel = "2";
					} else if (daExcel.equals("星期二")) {
						daExcel = "3";
					} else if (daExcel.equals("星期三")) {
						daExcel = "4";
					} else if (daExcel.equals("星期四")) {
						daExcel = "5";
					} else if (daExcel.equals("星期五")) {
						daExcel = "6";
					} else if (daExcel.equals("星期六")) {
						daExcel = "7";
					}

					int countWeekExcel = 0;
					int countWeekExcel0 = 0;
					int countWeekExcel1 = 0;
					int countWeekExcel2 = 0;
					int countWeekExcel3 = 0;
					int countWeekExcel4 = 0;
					int countWeekExcel5 = 0;

					// 满足发送条件
					isSendExcelMailReport(es);
					nowDate.setTime(es.getNextTime());
					// nowDate.add(Calendar.DATE,7);
					// 判断今天是否等于所选的发送星期
					for (int y = 0; y < dayxExcel.length; y++) {
						// 选定的星期等于今天
						if (dayxExcel[y].equals(daExcel)) {
							// 选择的天数的判断
							switch (dayxExcel.length) {
							case 1:
								// 选1天
								nowDate.add(Calendar.DATE, 7);
								break;
							case 2:
								// 选2天
								if (y == 0) {
									// 下标为0
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel = countWeekExcel1
											- countWeekExcel0;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 1) {
									// 下标为1
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel = countWeekExcel0 + 7
											- countWeekExcel1;
									nowDate.add(Calendar.DATE, countWeekExcel);
								}
								break;
							case 3:
								// 选3天
								if (y == 0) {
									// 下标为0
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel = countWeekExcel1
											- countWeekExcel0;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 1) {
									// 下标为1
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel = countWeekExcel2
											- countWeekExcel1;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 2) {
									// 下标为2
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel = countWeekExcel0 + 7
											- countWeekExcel2;
									nowDate.add(Calendar.DATE, countWeekExcel);
								}
								break;
							case 4:
								// 选4天
								if (y == 0) {
									// 下标为0
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel = countWeekExcel1
											- countWeekExcel0;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 1) {
									// 下标为1
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel = countWeekExcel2
											- countWeekExcel1;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 2) {
									// 下标为2
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel3 = Integer
											.parseInt(dayxExcel[3]);
									countWeekExcel = countWeekExcel3
											- countWeekExcel2;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 3) {
									// 下标为3
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel3 = Integer
											.parseInt(dayxExcel[3]);
									countWeekExcel = countWeekExcel0 + 7
											- countWeekExcel3;
									nowDate.add(Calendar.DATE, countWeekExcel);
								}
								break;
							case 5:
								// 选5天
								if (y == 0) {
									// 下标为0
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel = countWeekExcel1
											- countWeekExcel0;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 1) {
									// 下标为1
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel = countWeekExcel2
											- countWeekExcel1;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 2) {
									// 下标为2
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel3 = Integer
											.parseInt(dayxExcel[3]);
									countWeekExcel = countWeekExcel3
											- countWeekExcel2;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 3) {
									// 下标为3
									countWeekExcel3 = Integer
											.parseInt(dayxExcel[3]);
									countWeekExcel4 = Integer
											.parseInt(dayxExcel[4]);
									countWeekExcel = countWeekExcel4
											- countWeekExcel3;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 4) {
									// 下标为4
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel4 = Integer
											.parseInt(dayxExcel[4]);
									countWeekExcel = countWeekExcel0 + 7
											- countWeekExcel4;
									nowDate.add(Calendar.DATE, countWeekExcel);
								}
								break;
							case 6:
								// 选6天
								if (y == 0) {
									// 下标为0
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel = countWeekExcel1
											- countWeekExcel0;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 1) {
									// 下标为1
									countWeekExcel1 = Integer
											.parseInt(dayxExcel[1]);
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel = countWeekExcel2
											- countWeekExcel1;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 2) {
									// 下标为2
									countWeekExcel2 = Integer
											.parseInt(dayxExcel[2]);
									countWeekExcel3 = Integer
											.parseInt(dayxExcel[3]);
									countWeekExcel = countWeekExcel3
											- countWeekExcel2;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 3) {
									// 下标为3
									countWeekExcel3 = Integer
											.parseInt(dayxExcel[3]);
									countWeekExcel4 = Integer
											.parseInt(dayxExcel[4]);
									countWeekExcel = countWeekExcel4
											- countWeekExcel3;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 4) {
									// 下标为4
									countWeekExcel4 = Integer
											.parseInt(dayxExcel[4]);
									countWeekExcel5 = Integer
											.parseInt(dayxExcel[5]);
									countWeekExcel = countWeekExcel5
											- countWeekExcel4;
									nowDate.add(Calendar.DATE, countWeekExcel);
								} else if (y == 5) {
									// 下标为5
									countWeekExcel0 = Integer
											.parseInt(dayxExcel[0]);
									countWeekExcel5 = Integer
											.parseInt(dayxExcel[5]);
									countWeekExcel = countWeekExcel0 + 7
											- countWeekExcel5;
									nowDate.add(Calendar.DATE, countWeekExcel);
								}
								break;
							case 7:
								// 选7天
								nowDate.add(Calendar.DATE, 1);
								break;
							}
						}
					}

					es.setNextTime(nowDate.getTime());
					es.setSendStateExcel(Constants.SENDED);
					resultStrExcel = "发送成功";
					// }

				} else if (SubscribeExcelType.month.getIndex().equals(
						es.getSendTypeExcel())
						&& nowDate.getTime().before(es.getNextTime())) {
					////logger.info(Tools.getCurrFormatTimeGen()+"-----------EmailTask --------------------------------一月遍历一次");
					isSendExcelMailReport(es);
					nowDate.setTime(es.getNextTime());
					nowDate.add(Calendar.MONTH, 1);
					es.setNextTime(nowDate.getTime());
					es.setSendStateExcel(Constants.SENDED);
					resultStrExcel = "发送成功";
				} else if (SubscribeExcelType.year.getIndex().equals(
						es.getSendTypeExcel())
						&& nowDate.getTime().before(es.getNextTime())) {
					////logger.info(Tools.getCurrFormatTimeGen()+"-----------EmailTask --------------------------------一年遍历一次");
					isSendExcelMailReport(es);
					nowDate.setTime(es.getNextTime());
					nowDate.add(Calendar.YEAR, 1);
					es.setNextTime(nowDate.getTime());
					es.setSendStateExcel(Constants.SENDED);
					resultStrExcel = "发送成功";
				}
			}
		}
		return resultStrExcel;
	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	private void sendExcelEmail() throws Exception {
		
		//首先判断当前时间是否大于指定时间，如果大于指定时间，将数据库中的发送状态字段中已发送的字段都改为未发送。之后当天再查询未发送状态的数据
		Date curr=Tools.formatDateStrMin(Tools.getCurrFormatTimeGen());
		Date modifyTime=Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.MODIFYSENDSTATETIME);
		Date donotModifyTime=Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.DONOTMODIFYSENDSTATETIME);
		
		//如果当前时间大于修改时间，并且小于不修改状态的时间。需要把数据库中的发送状态修改为未发送
		if((Tools.timeDiff(curr,modifyTime)>0)&&(Tools.timeDiff(curr,donotModifyTime)<0)){
			logger.info("当前时间大于当天修改发送状态的时间("+modifyTime+"),修改数据库中的发送状态为未发送");
			excelDao.updateSendStateExcelSubscribe();
		}
		
		Calendar nowDate = Calendar.getInstance();
		// -----------------------遍历配置信息：是否需要发送邮件(时间类型、规则)------------------------------------------------//
		
		 List<ExcelSubscribe> excelSubscribeList=excelDao.getDNSExcelSubscribeList();
		 logger.info("数据库中有"+excelSubscribeList.size()+"个excel报表");
		 for (int i = 0; i < excelSubscribeList.size(); i++) {
			
			 ExcelSubscribe es =new ExcelSubscribe();
			 es=excelSubscribeList.get(i);
			//当天临时发送的时间和当前时间的时间差，当天数据未到位时，会给这个字段赋值，其他情况暂不赋值
			 long d2 =0L;
			 if(es.getTempSendTime()!=null){
				 d2 = es.getTempSendTime().getTime()- nowDate.getTime().getTime(); 
			 }
			
			 logger.info("正在循环第"+i+"个excel报表！");
		 //for (ExcelSubscribe es : InitExcelSubscribeConfig.excelSubscribeConfig) {
			if (es.getNextTime() == null) {// 时间格式不对，没有成功设置发送时间
				continue;
			}
			// 一分钟遍历一次
			if (SubscribeExcelType.once.getIndex()
					.equals(es.getSendTypeExcel()) && es.getNextTime() != null) {
				long dExcel = es.getNextTime().getTime()
						- nowDate.getTime().getTime();
				// 提前一分钟发邮件2分钟 记录下次时间(必须),以防被遗漏
				if (dExcel < Constants.ADVANCEMIN && dExcel > 0) {
					// 满足发送条件
					isSendExcelMailReport(es);
					es.setNextTime(null);// 代表已执行完毕
					es.setSendStateExcel(Constants.SENDED);
				}
				//
			}else if (SubscribeExcelType.day.getIndex().equals(es.getSendTypeExcel())){
				if(nowDate.getTime().before(es.getNextTime())){
					long dExcel = es.getNextTime().getTime()
							- nowDate.getTime().getTime();
					logger.info(Tools.getCurrFormatTimeGen()+"发送时间=" + dateSdf.format(es.getNextTime())
							+ "--当前时间=" + dateSdf.format(nowDate.getTime()));
					if (dExcel < Constants.ADVANCEMIN && dExcel > 0) {
						// 满足发送条件
						isSendExcelMailReport(es);
						nowDate.setTime(es.getNextTime());
						nowDate.add(Calendar.DATE, 1);
						es.setNextTime(nowDate.getTime());
						es.setSendStateExcel(Constants.SENDED);
					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(es.getTempSendTime()))) 
						&&
					 ("false".equals(es.getIsTodaySend()))
					 	&&
					 (d2 < Constants.ADVANCEMIN && d2 > 0)
					 ){
						//判断当前时间和23:00:00的时间差，如果当前时间大于23:00:00。就不再进行下一步，直接修改数据库的3个字段使其不再进行十分钟间隔的扫描，
						if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
							es=TableauTools.modifyExcelPropCol(es);
							excelDao.updateSendTimeExcelSubscribe(es);
						}else{//如果当前时间小于23点继续进行下一步
							//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
							isSendExcelMailReport(es);
							es.setSendStateExcel(Constants.SENDED);
						}
				
				
				}
			} else if (SubscribeExcelType.week.getIndex().equals(es.getSendTypeExcel())){
				if(nowDate.getTime().before(es.getNextTime())){
					logger.info(Tools.getCurrFormatTimeGen()+"每周：" + SubscribeExcelType.week.getIndex()
							+ "；下次发送时间es.getNextTime()：" + es.getNextTime()
							+ "获取发送时间 es.getSendTime()：" + es.getSendTimeExcel());
					String[] strExcel = es.getSendTimeExcel().split("_");
	
					String[] dayxExcel = strExcel[0].split(",");
					logger.info(Tools.getCurrFormatTimeGen()+"dayx.length长度是：" + dayxExcel.length + "内容是"
							+ dayxExcel[0]);
					// 0->length-1
					// 获取今天是星期几
					Date dateExcel = new Date();
					SimpleDateFormat dateFmExcel = new SimpleDateFormat("EEEE");
					String daExcel = dateFmExcel.format(dateExcel);
					if (daExcel.equals("星期日")) {
						daExcel = "1";
					} else if (daExcel.equals("星期一")) {
						daExcel = "2";
					} else if (daExcel.equals("星期二")) {
						daExcel = "3";
					} else if (daExcel.equals("星期三")) {
						daExcel = "4";
					} else if (daExcel.equals("星期四")) {
						daExcel = "5";
					} else if (daExcel.equals("星期五")) {
						daExcel = "6";
					} else if (daExcel.equals("星期六")) {
						daExcel = "7";
					}
	
					int countWeekExcel = 0;
					int countWeekExcel0 = 0;
					int countWeekExcel1 = 0;
					int countWeekExcel2 = 0;
					int countWeekExcel3 = 0;
					int countWeekExcel4 = 0;
					int countWeekExcel5 = 0;
	
					long dExcel = es.getNextTime().getTime()
							- nowDate.getTime().getTime();
					if (dExcel < Constants.ADVANCEMIN && dExcel > 0) {
						// 满足发送条件
						isSendExcelMailReport(es);
						nowDate.setTime(es.getNextTime());
						// nowDate.add(Calendar.DATE,7);
						// 判断今天是否等于所选的发送星期
						for (int y = 0; y < dayxExcel.length; y++) {
							// 选定的星期等于今天
							if (dayxExcel[y].equals(daExcel)) {
								// 选择的天数的判断
								switch (dayxExcel.length) {
								case 1:
									// 选1天
									nowDate.add(Calendar.DATE, 7);
									break;
								case 2:
									// 选2天
									if (y == 0) {
										// 下标为0
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel = countWeekExcel1
												- countWeekExcel0;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 1) {
										// 下标为1
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel = countWeekExcel0 + 7
												- countWeekExcel1;
										nowDate.add(Calendar.DATE, countWeekExcel);
									}
									break;
								case 3:
									// 选3天
									if (y == 0) {
										// 下标为0
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel = countWeekExcel1
												- countWeekExcel0;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 1) {
										// 下标为1
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel = countWeekExcel2
												- countWeekExcel1;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 2) {
										// 下标为2
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel = countWeekExcel0 + 7
												- countWeekExcel2;
										nowDate.add(Calendar.DATE, countWeekExcel);
									}
									break;
								case 4:
									// 选4天
									if (y == 0) {
										// 下标为0
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel = countWeekExcel1
												- countWeekExcel0;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 1) {
										// 下标为1
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel = countWeekExcel2
												- countWeekExcel1;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 2) {
										// 下标为2
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel3 = Integer
												.parseInt(dayxExcel[3]);
										countWeekExcel = countWeekExcel3
												- countWeekExcel2;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 3) {
										// 下标为3
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel3 = Integer
												.parseInt(dayxExcel[3]);
										countWeekExcel = countWeekExcel0 + 7
												- countWeekExcel3;
										nowDate.add(Calendar.DATE, countWeekExcel);
									}
									break;
								case 5:
									// 选5天
									if (y == 0) {
										// 下标为0
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel = countWeekExcel1
												- countWeekExcel0;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 1) {
										// 下标为1
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel = countWeekExcel2
												- countWeekExcel1;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 2) {
										// 下标为2
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel3 = Integer
												.parseInt(dayxExcel[3]);
										countWeekExcel = countWeekExcel3
												- countWeekExcel2;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 3) {
										// 下标为3
										countWeekExcel3 = Integer
												.parseInt(dayxExcel[3]);
										countWeekExcel4 = Integer
												.parseInt(dayxExcel[4]);
										countWeekExcel = countWeekExcel4
												- countWeekExcel3;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 4) {
										// 下标为4
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel4 = Integer
												.parseInt(dayxExcel[4]);
										countWeekExcel = countWeekExcel0 + 7
												- countWeekExcel4;
										nowDate.add(Calendar.DATE, countWeekExcel);
									}
									break;
								case 6:
									// 选6天
									if (y == 0) {
										// 下标为0
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel = countWeekExcel1
												- countWeekExcel0;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 1) {
										// 下标为1
										countWeekExcel1 = Integer
												.parseInt(dayxExcel[1]);
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel = countWeekExcel2
												- countWeekExcel1;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 2) {
										// 下标为2
										countWeekExcel2 = Integer
												.parseInt(dayxExcel[2]);
										countWeekExcel3 = Integer
												.parseInt(dayxExcel[3]);
										countWeekExcel = countWeekExcel3
												- countWeekExcel2;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 3) {
										// 下标为3
										countWeekExcel3 = Integer
												.parseInt(dayxExcel[3]);
										countWeekExcel4 = Integer
												.parseInt(dayxExcel[4]);
										countWeekExcel = countWeekExcel4
												- countWeekExcel3;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 4) {
										// 下标为4
										countWeekExcel4 = Integer
												.parseInt(dayxExcel[4]);
										countWeekExcel5 = Integer
												.parseInt(dayxExcel[5]);
										countWeekExcel = countWeekExcel5
												- countWeekExcel4;
										nowDate.add(Calendar.DATE, countWeekExcel);
									} else if (y == 5) {
										// 下标为5
										countWeekExcel0 = Integer
												.parseInt(dayxExcel[0]);
										countWeekExcel5 = Integer
												.parseInt(dayxExcel[5]);
										countWeekExcel = countWeekExcel0 + 7
												- countWeekExcel5;
										nowDate.add(Calendar.DATE, countWeekExcel);
									}
									break;
								case 7:
									// 选7天
									nowDate.add(Calendar.DATE, 1);
									break;
								}
							}
						}
	
						es.setNextTime(nowDate.getTime());
						es.setSendStateExcel(Constants.SENDED);
					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(es.getTempSendTime()))) 
						&&
					 ("false".equals(es.getIsTodaySend()))
					 	&&
					 (d2 < Constants.ADVANCEMIN && d2 > 0)
					 ){
						if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
							es=TableauTools.modifyExcelPropCol(es);
							excelDao.updateSendTimeExcelSubscribe(es);
						}else{//如果当前时间小于23点继续进行下一步
							//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
							isSendExcelMailReport(es);
							es.setSendStateExcel(Constants.SENDED);
						}
					
				}
			} else if (SubscribeExcelType.month.getIndex().equals(es.getSendTypeExcel())){
				if(nowDate.getTime().before(es.getNextTime())){
					long dExcel = es.getNextTime().getTime()
							- nowDate.getTime().getTime();
					if (dExcel < Constants.ADVANCEMIN && dExcel > 0) {
						// 满足发送条件
						isSendExcelMailReport(es);
						nowDate.setTime(es.getNextTime());
						nowDate.add(Calendar.MONTH, 1);
						es.setNextTime(nowDate.getTime());
						es.setSendStateExcel(Constants.SENDED);
					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(es.getTempSendTime()))) 
						&&
					 ("false".equals(es.getIsTodaySend()))
					 	&&
					 (d2 < Constants.ADVANCEMIN && d2 > 0)
					 ){
					if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
						es=TableauTools.modifyExcelPropCol(es);
						excelDao.updateSendTimeExcelSubscribe(es);
					}else{//如果当前时间小于23点继续进行下一步
						//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
						isSendExcelMailReport(es);
						es.setSendStateExcel(Constants.SENDED);
					}
					
				}
				
			} else if (SubscribeExcelType.year.getIndex().equals(es.getSendTypeExcel())){
				if(nowDate.getTime().before(es.getNextTime())){
					long dExcel = es.getNextTime().getTime()
							- nowDate.getTime().getTime();
					if (dExcel < Constants.ADVANCEMIN && dExcel > 0) {
						// 满足发送条件
						isSendExcelMailReport(es);
						nowDate.setTime(es.getNextTime());
						nowDate.add(Calendar.YEAR, 1);
						es.setNextTime(nowDate.getTime());
						es.setSendStateExcel(Constants.SENDED);

					}
				}
				if((Tools.getCurrFormatTimeDay(nowDate.getTime()).equals(Tools.getCurrFormatTimeDay(es.getTempSendTime()))) 
						&&
					 ("false".equals(es.getIsTodaySend()))
					 	&&
					 (d2 < Constants.ADVANCEMIN && d2 > 0)
					 ){
					if(Tools.timeDiff(Tools.formatDateStrMin(Tools.getCurrFormatTimeGen()),Tools.formatDateStrMin(Tools.getCurrFormatTimeDay(new Date())+" "+Constants.STOPSCANTASKBEGIN))>0){
						es=TableauTools.modifyExcelPropCol(es);
						excelDao.updateSendTimeExcelSubscribe(es);
					}else{//如果当前时间小于23点继续进行下一步
						//如果当前时间和下次发送的时间是同一天。并且当天没有按照用户的规则发送数据，并且该报表当天下次临时发送时间和当前时间差为2分钟
						isSendExcelMailReport(es);
						es.setSendStateExcel(Constants.SENDED);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 附件：excel文件
	 * 
	 * @param reportSubscribe
	 *            订阅规则配置
	 * @throws Exception
	 */
	public String isSendExcelMailReport(final ExcelSubscribe excelSubscribe)
			throws Exception {
		logger.info(Tools.getCurrFormatTimeGen()+"~~~~~~~~~~~~~~~~发邮件~~~~~~~~~~~~~~~~~~~~~~~~~~");
		String resultExcel = "发送失败！";
		Vector<ExcelSubscribe> listExcelSubscribe = new Vector<ExcelSubscribe>();
		listExcelSubscribe.add(excelSubscribe);
		if (listExcelSubscribe != null && listExcelSubscribe.size() > 0) {
			////logger.info(Tools.getCurrFormatTimeGen()+"-----------EmailTask------------isSendExcelMailReport------------邮件推送：" + excelSubscribe);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						sendExcel(excelSubscribe);
						// sendZipMail(reportList, reportSubscribe);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info(Tools.getCurrFormatTimeGen()+"~~~~~~~~~~~~~~~send~Excel~end~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}

			});
			resultExcel = "发送成功！";
		}
		// }
		return resultExcel;
	}

	/**
	 * 发送Excel附件
	 * */
	private void sendExcel(ExcelSubscribe excelSubscribe) throws Exception {
		int sendExcel = 0;
		String donotSendRes="";
		String excelPath = "";
		//先把excel导出到本地，再发送出去
		String excelFilePath = ByitConfig.FILE_UPLOAD_PATH;
		
		logger.info(excelSubscribe.getTableId()+"的存储的excel地址是："+excelFilePath);
		String screening = excelSubscribe.getScreening();
		//判断条件 当日跑批是否完成
		sendExcel = SendExcelOper.sendExcel(excelSubscribe);
		logger.info(excelSubscribe.getTableId()+"的sendExcel："+sendExcel);
		if(sendExcel>0 || "1".equals(screening)){
			//当天跑批有数据 或者无筛选条件
			logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"当天数据已到，准备发送该条信息");
			try {
				// 导入导出excel
				logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"正在导出，请稍候……");
				List<SystemDoMainEntity> list = systemDoMainService.getByDoMainIdList("pageSize");
				Integer pageSize = Integer.valueOf(list.get(0).getCodename());
				Map<String,String> map=ExcelOper.exportData(excelFilePath,excelSubscribe, pageSize);
				for (String key : map.keySet()) {
					logger.info(excelSubscribe.getTableId()+",key="+key+",vlaue="+map.get(key));
					if(key.equals("info")){//有info说明数据过大
						donotSendRes=map.get(key);
						logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"数据量过大，需要发邮件提示：");
						EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
						
						for (String emailExcel : excelSubscribe.getMailListExcel()) {
							MailSenderInfo mailSenderInfo=new MailSenderInfo();
							mailSenderInfo.setToAddress(emailExcel);
							mailSenderInfo.setContent(donotSendRes);
							mailSenderInfo.setSubject(excelSubscribe.getMailTitleExcel()+Constants.NO_MOREDATA_SEND_INFO);
							MailSendPack.sendContentEmailPack(mailSenderInfo,emailEntity);
							logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"给"+emailExcel+"发送提示邮件！");
						}
						
					}else if(key.equals("sizeZero")){
						logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，当天数据为0，发送提示邮件！");
						//没有数据。直接发送邮件告知用户、
						EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
						
						//如果数据不更新将当天发送失败、当天下次发送时间存储到数据库中，下次执行定时任务时依据当天下次发送时间来判断
						String isSendProp=excelSubscribe.getIsSendProp();
						logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，该提示邮件当天是否发送过"+isSendProp);
						//如果isSendProp这个字段为空或者为false。表示还暂发送提示邮件需要发送。如果为true表示已经发送过邮件了，下次不需要再次发送
						if((!Tools.isNotEmpty(isSendProp)) || ("false".equals(isSendProp))){
							//从数据库获取邮件title
							List<SystemDoMainEntity> title = systemDoMainService.getByDoMainIdList("ExcelTitle");
							//从数据库获取邮件content
							List<SystemDoMainEntity> content = systemDoMainService.getByDoMainIdList("ExcelContent");
							for (String emailExcel : excelSubscribe.getMailListExcel()) {
								MailSenderInfo mailSenderInfo=new MailSenderInfo();
								mailSenderInfo.setToAddress(emailExcel);
								String contents = content.get(0).getCodename();
								contents = contents.replace("#", excelSubscribe.getTableId());
								contents = contents.replace("@", excelSubscribe.getTableName());
								mailSenderInfo.setContent(contents);
								mailSenderInfo.setSubject(excelSubscribe.getMailTitleExcel()+title.get(0).getCodename());
								MailSendPack.sendContentEmailPack(mailSenderInfo,emailEntity);
								logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，给"+emailExcel+"发送无误数据的提示邮件");
							}
						}
						
						//如果数据不更新将当天发送失败、当天下次发送时间存储到数据库中，下次执行定时任务时依据当天下次发送时间来判断
						ExcelSubscribe tExcelSubscribe=new ExcelSubscribe();
						tExcelSubscribe.setIsTodaySend("false");
						tExcelSubscribe.setIsSendProp("true");
						Long tnextSendTime=new Date().getTime();
						Long tempSendTime=tnextSendTime+Constants.TENMIN;
						tExcelSubscribe.setPtempSendTime((Tools.getCurrFormatTimeday(new Date(tempSendTime))));
						tExcelSubscribe.setTableId(excelSubscribe.getTableId());
						excelDao.updateSendTimeExcelSubscribe(tExcelSubscribe);
						logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，修改excel报表下次发送的时间！");
					}else if(key.equals("path")){//有path说明数据无问题，path是导出路径
						excelPath=map.get(key);
						logger.info(Tools.getCurrFormatTimeGen()+"导出完成，路径是："+excelPath);
						
						List<SendExcelResult> sendExcelResultList = new ArrayList<>();
						SendExcelResult resultExcel = new SendExcelResult();
						EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
						for (String emailExcel : excelSubscribe.getMailListExcel()) {
							resultExcel = new SendExcelResult();
							resultExcel.setTableId(excelSubscribe.getTableId());
							resultExcel.setEmailId(emailExcel);
							resultExcel.setExcelTableId(excelSubscribe.getId());
							resultExcel.setSendTimeExcel(dateSdf.format(new Date()));
							resultExcel.setSendStateExcel("失败");
							
							try {
								// 发送附件
								MailUtils sendmail = new MailUtils();
								sendmail.setTo(emailExcel);
								sendmail.setSubject(excelSubscribe.getMailTitleExcel());
								
								sendmail.attachfile(excelPath);
								
								// 发送附件
								boolean sendFlag=MailSendPack.sendExcelEmailPack(sendmail,emailEntity);
								logger.info(resultExcel.getTableId()+","+Tools.getCurrFormatTimeGen()+"，发送excel邮件！");
								if(sendFlag){
									resultExcel.setSendStateExcel("成功");
									logger.info(resultExcel.getTableId()+","+Tools.getCurrFormatTimeGen()+"，excel邮件发送成功！");
								}else{
									resultExcel.setSendStateExcel("失败");
									logger.info(resultExcel.getTableId()+","+Tools.getCurrFormatTimeGen()+"，excel邮件发送失败！");
								}
								
							} catch (Exception e) {
								e.printStackTrace();
								resultExcel.setSendStateExcel("失败");
								logger.info(resultExcel.getTableId()+","+Tools.getCurrFormatTimeGen()+"，excel邮件发送失败，程序出现错误！错误原因："+e.getMessage());
							}
							
							sendExcelResultList.add(resultExcel);
						}
						
						//邮件发送成功后需要删除服务器上的文件
						FileOperation.dropFile(excelPath);
						// 存储发送excel结果到数据库中
						Map<String, List<SendExcelResult>> sendExcelResultMap = new HashMap<>();
						sendExcelResultMap.put(excelSubscribe.getTableId(), sendExcelResultList);
						excelDao.addExcelSubscribeResult(sendExcelResultMap);
						logger.info(resultExcel.getTableId()+","+Tools.getCurrFormatTimeGen()+"，发送结果保存到结果表中");
						
						//发送成功后修改主表
						excelSubscribe=TableauTools.modifyExcelPropCol(excelSubscribe);
						excelDao.updateExcelSubscribe(excelSubscribe);
						
						logger.info(resultExcel.getTableId()+","+Tools.getCurrFormatTimeGen()+"，修改发送的excel主表");
					}
				  }
				
			} catch (Exception e) {
				logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，excel邮件发送失败，可能是程序出现错误！错误原因："+e.getMessage());
				e.printStackTrace();
				EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
				for (String emailExcel : excelSubscribe.getMailListExcel()) {
					MailSenderInfo mailSenderInfo=new MailSenderInfo();
					mailSenderInfo.setToAddress(emailExcel);
					mailSenderInfo.setContent("【"+excelSubscribe.getTableId()+","+excelSubscribe.getTableName()+"】数据有误，详细信息如下："+e.getMessage());
					mailSenderInfo.setSubject(excelSubscribe.getMailTitleExcel()+Constants.ERROR_DATE_SEND_INFO);
					MailSendPack.sendContentEmailPack(mailSenderInfo,emailEntity);
					logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，发送错误的提示邮件！");
				}
			}
			
		}else{
			logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，没有当天数据，发送提示邮件！");
			//没有数据。直接发送邮件告知用户、
			EmailEntity emailEntity=MailSendPack.getEmailInfoFromDataBase();
			
			//如果数据不更新将当天发送失败、当天下次发送时间存储到数据库中，下次执行定时任务时依据当天下次发送时间来判断
			String isSendProp=excelSubscribe.getIsSendProp();
			logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，该提示邮件当天是否发送过"+isSendProp);
			//如果isSendProp这个字段为空或者为false。表示还暂发送提示邮件需要发送。如果为true表示已经发送过邮件了，下次不需要再次发送
			if((!Tools.isNotEmpty(isSendProp)) || ("false".equals(isSendProp))){
				//从数据库获取邮件title
				List<SystemDoMainEntity> title = systemDoMainService.getByDoMainIdList("ExcelTitle");
				//从数据库获取邮件content
				List<SystemDoMainEntity> content = systemDoMainService.getByDoMainIdList("ExcelContent");
				for (String emailExcel : excelSubscribe.getMailListExcel()) {
					MailSenderInfo mailSenderInfo=new MailSenderInfo();
					mailSenderInfo.setToAddress(emailExcel);
					String contents = content.get(0).getCodename();
					contents = contents.replace("#", excelSubscribe.getTableId());
					contents = contents.replace("@", excelSubscribe.getTableName());
					mailSenderInfo.setContent(contents);
					mailSenderInfo.setSubject(excelSubscribe.getMailTitleExcel()+title.get(0).getCodename());
					MailSendPack.sendContentEmailPack(mailSenderInfo,emailEntity);
					logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，给"+emailExcel+"发送无误数据的提示邮件");
				}
			}
			
			//如果数据不更新将当天发送失败、当天下次发送时间存储到数据库中，下次执行定时任务时依据当天下次发送时间来判断
			ExcelSubscribe tExcelSubscribe=new ExcelSubscribe();
			tExcelSubscribe.setIsTodaySend("false");
			tExcelSubscribe.setIsSendProp("true");
			Long tnextSendTime=new Date().getTime();
			Long tempSendTime=tnextSendTime+Constants.TENMIN;
			tExcelSubscribe.setPtempSendTime((Tools.getCurrFormatTimeday(new Date(tempSendTime))));
			tExcelSubscribe.setTableId(excelSubscribe.getTableId());
			excelDao.updateSendTimeExcelSubscribe(tExcelSubscribe);
			logger.info(excelSubscribe.getTableId()+","+Tools.getCurrFormatTimeGen()+"，修改excel报表下次发送的时间！");
		}

	}
	/**************************************************** excel end ************************************************************/
}
