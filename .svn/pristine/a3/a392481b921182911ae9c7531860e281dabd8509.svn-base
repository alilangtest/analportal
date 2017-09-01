package byit.aladdin.workBook.job;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import byit.aladdin.workBook.entity.Workbooks;
import byit.aladdin.workBook.entity.XmlTablesMain;
import byit.aladdin.workBook.service.WorkBookService;
import byit.aladdin.workBook.util.CopyOfMyzipDecompressing;
import byit.aladdin.workBook.util.HttpClientUtils;
import byit.aladdin.workBook.util.TableauRestAPIUtils;
import byit.core.util.convert.ConvertUtil;
import byit.osdp.base.service.SystemDoMainService;
import byit.osdp.tableau.TableauConfig;
import byit.tableausubscribe.tab.bean.SubscribeType;
import net.sf.json.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;


public class SchedulerJob {
	private final Logger logger = Logger.getLogger(SchedulerJob.class);
	
	private int refreshTimes = Integer.valueOf(HttpClientUtils.pro.getProperty("tableau.task.refreshtimes"));
	private int refreshInterval = Integer.valueOf(HttpClientUtils.pro.getProperty("tableau.task.refreshinterval"));
	
	private String tableauServerUrl = HttpClientUtils.pro.getProperty("tableau.embed.server.url");
	private String cmdEncoding = HttpClientUtils.pro.getProperty("tableau.embed.cmd-encoding");
	private String adminUsername = TableauConfig.TABLEAU_SERVER_NAME;
	private String adminPassword = TableauConfig.TABLEAU_SERVER_PASSWORD;
	@Autowired
	private WorkBookService workBookService;
	@Autowired
	private SystemDoMainService systemDoMainService;
  
	private final SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void execute(JobExecutionContext jobExecutionContext) {
		//		Properties pro= ReadProperties.read("application.properties");
		//		String rootPath = pro.getProperty("scheduler.rootPath");
		//获取下载文件列表
		List<Workbooks> workbookList = this.workBookService.queryWorkBookByExtracts("");
		logger.debug("1.共查询到" + workbookList.size() + "个要下载文件-----" + dateSdf.format(new Date()));
		this.logger.info("1.共查询到" + workbookList.size() + "个要下载文件-----" + dateSdf.format(new Date()));
		for (int i = 0; i < workbookList.size(); i++) {
			try {
				Workbooks workbook = workbookList.get(i);
				String siteId = workbook.getSluid();
				String workbookId = workbook.getWluid();
				String url = HttpClientUtils.doGetUrl("/api/api-version/sites/" + siteId + "/workbooks/" + workbookId + "/content");
				String token = TableauRestAPIUtils.doGetUserToken(workbook.getUrlNamespace()); //workbook.getUrlNamespace()
				HttpClientUtils.doGet(url, null, "UTF-8", token, workbook.getId());
				this.logger.info("2.第" + i + "个文件下载成功！----------" + dateSdf.format(new Date()));
			} catch (Exception e) {
				this.logger.error("===下载文件出错了===", e);
			}
		}
		if (workbookList.size() > 0) {
			//获取zip文件路径
			String filepath = HttpClientUtils.getSaveFilePath();
			File zipfile = new File(filepath);
			//获取下载文件列表解压zip文件
			List<String> filelist = CopyOfMyzipDecompressing.getFileList(zipfile, "zip");
			String tofilepath = filepath + "temp\\";
			for (int i = 0; i < filelist.size(); i++) {
				String filename = filelist.get(i);
				File pfilepath = new File(filename);
				try {
					//获取zip文件名
					String workbookid = pfilepath.getName().substring(0, pfilepath.getName().lastIndexOf("."));
					//解压文件
					CopyOfMyzipDecompressing.unzip(filename, tofilepath);
					File copyfile = new File(tofilepath);
					List<String> copyFileList = CopyOfMyzipDecompressing.getFileList(copyfile, "twb");
					for (int j = 0; j < copyFileList.size(); j++) {
						//将文件复制到制定文件夹
						CopyOfMyzipDecompressing.copyFile(copyFileList.get(j), filepath + "\\" + workbookid + ".twb");
						File delfile = new File(tofilepath);
						//删除temp文件夹
						CopyOfMyzipDecompressing.deleteDir(delfile);
					}
				} catch (Exception e) {
					this.logger.error("===未找到复制文件===");
				}
				this.logger.info("3.解压第" + i + "个文件！----------" + dateSdf.format(new Date()));
			}

			try {
				//删除目录下zip文件
				CopyOfMyzipDecompressing.deleteZip(filepath, "zip");
			} catch (Exception e) {
				this.logger.info("===删除zip文件失败===");
			}

			//解析文件入库
			List<File> listFiles = new ArrayList<>();
			try {
				this.getAllFiles(new File(filepath), listFiles);
			} catch (Exception e) {
				this.logger.error("===未获取到下载文件列表===", e);
			}

			for (File file : listFiles) {
				try {
					List<Map<String, String>> tablesInfoList = this.getTableInfos(file);
					String workbookId = file.getName().substring(0, file.getName().lastIndexOf("."));
					this.workBookService.deleteTablesByWorkBookId(workbookId);
					this.workBookService.addTables(tablesInfoList);
					
				} catch (Exception e) {
					this.logger.error("===保存解析的参数出错了===", e);
				}
			}
			this.logger.info("文件解析成功已入库！---------" + dateSdf.format(new Date()));
		}

		//TODO
		try {
			this.logger.info("xmlTables已完成！------" + dateSdf.format(new Date()));
		} catch (Exception e) {
			this.logger.error("===出错了===", e);
		}

	}
	/**
	 * 获取指定目录下的所有文件
	 * @param dir
	 * @param listFiles
	 */
	private void getAllFiles(File dir, List<File> listFiles) {
		File[] childrenFiles = dir.listFiles();
		for (File file : childrenFiles) {
			if (file.isFile()) {
				listFiles.add(file);
			} else {
				getAllFiles(file, listFiles);
			}
		}
	}
	/**
	 * 获取文件中的所有表名称
	 * @param file
	 * @return
	 * @throws JSQLParserException
	 * @throws DocumentException
	 */
	private List<Map<String, String>> getTableInfos(File file) throws JSQLParserException, DocumentException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(file);
		Element root = document.getRootElement();
		List<Element> elementList = new ArrayList<>();
		elementList = this.getElmentsByName(root, "relation");
		List<Map<String, String>> tableInfoList = new ArrayList<>();
		for (Element element : elementList) {
			String sql = element.getTextTrim();
			//System.out.println("sql===" + sql);
			if (StringUtils.isBlank(sql)) {
				Map<String, String> tableInfo = new HashMap<>();
				Attribute table = element.attribute("table");
				if (table != null && StringUtils.isNotBlank(table.getValue())) {
					String tableName = table.getValue();//获取表名称
					if (tableName.indexOf(".") != -1) {
						String[] names = tableName.split("\\.");
						String dbname = names[0].substring(1, names[0].length() - 1);
						tableName = names[1].substring(1, names[1].length() - 1);
						tableInfo.put("dbName", dbname);
						tableInfo.put("tableName", tableName);
						String workbookId = file.getName().substring(0, file.getName().lastIndexOf("."));
						tableInfo.put("workbookId", workbookId);
						tableInfoList.add(tableInfo);
						continue;
					}
					tableName = tableName.substring(1, tableName.length() - 1);
					//System.out.println("table===" + tableName);
					tableInfo.put("tableName", tableName);
				}
				Attribute connection = element.attribute("connection");
				if (connection != null && StringUtils.isNotBlank(connection.getValue())) {
					String connectionName = connection.getValue();
					String dbName = this.getDBName(root, connectionName);
					tableInfo.put("dbName", dbName);
				}
				String workbookId = file.getName().substring(0, file.getName().lastIndexOf("."));
				tableInfo.put("workbookId", workbookId);
				tableInfoList.add(tableInfo);
			} else {
				Reader statementReader = new StringReader(sql);
				Statement statement = new CCJSqlParserManager().parse(statementReader);
				List<String> tablesList = new TablesNamesFinder().getTableList(statement);
				for (String tableName : tablesList) {
					Map<String, String> tableInfo = new HashMap<>();
					//判断表名是否带点
					if (tableName.indexOf(".") != -1) {
						String[] names = tableName.split("\\.");
						tableName = names[1];
						String dbname = names[0];
						tableInfo.put("dbName", dbname);
						tableInfo.put("tableName", tableName);
						String workbookId = file.getName().substring(0, file.getName().lastIndexOf("."));
						tableInfo.put("workbookId", workbookId);
						tableInfoList.add(tableInfo);
						continue;
					}
					//System.out.println("tableName===" + tableName);
					tableInfo.put("tableName", tableName);
					Attribute connection = element.attribute("connection");
					if (connection != null && StringUtils.isNotBlank(connection.getValue())) {
						String connectionName = connection.getValue();
						String dbName = this.getDBName(root, connectionName);
						tableInfo.put("dbName", dbName);
					}
					String workbookId = file.getName().substring(0, file.getName().lastIndexOf("."));
					tableInfo.put("workbookId", workbookId);
					tableInfoList.add(tableInfo);
				}
			}
		}
		return tableInfoList;
	}
	private List<Element> getElmentsByName(Element element, String tagName) {
		List<Element> elementList = new ArrayList<>();
		List<Element> elementAllList = new ArrayList<>();
		this.getElementsAll(element, elementAllList);
		for (Element e : elementAllList) {
			Attribute attr = e.attribute("connection");
			if (attr == null || StringUtils.isBlank(attr.getValue()) || attr.getValue().startsWith("dataengine.")
					|| attr.getValue().startsWith("excel-direct.") || attr.getValue().startsWith("textscan."))
				continue;//只要包含connection属性的元素
			if (tagName.equals(e.getName())) {
				elementList.add(e);
			}
		}
		return elementList;
	}
	/**
	 * 获取所有元素
	 * @param element
	 * @param elementAllList
	 */
	@SuppressWarnings("unchecked")
	private void getElementsAll(Element element, List<Element> elementAllList) {
		Iterator<Element> iterator = element.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			elementAllList.add(e);
			getElementsAll(e, elementAllList);
		}
	}
	/**
	 * @param element 文档根节点
	 * @param connectionName <named-connection>节点的名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDBName(Element element, String connectionName) {
		List<Element> namedConnection = this.getElementByAttr(element, "name", connectionName);
		if (namedConnection != null && !namedConnection.isEmpty()) {
			List<Element> connections = namedConnection.get(0).elements();
			if (connections != null && !connections.isEmpty()) {
				//System.out.println(connections.get(0));
				//System.out.println(connections.get(0).attribute("dbname"));
				return connections.get(0).attribute("dbname").getValue();
			}
		}
		return null;
	}
	/**
	 * @param root 文档根节点
	 * @param attr 属性节点
	 * @param value 属性值
	 * @return
	 */
	private List<Element> getElementByAttr(Element root, String attrName, String value) {
		List<Element> elementList = new ArrayList<>();
		List<Element> elementAllList = new ArrayList<>();
		this.getElementsAll(root, elementAllList);
		for (Element e : elementAllList) {
			Attribute attr = e.attribute(attrName);
			if (attr == null || StringUtils.isBlank(attr.getValue()))
				continue;
			if (value.equals(attr.getValue())) {
				elementList.add(e);
			}
		}
		return elementList;
	}
	//定时发送模式：定时任务指向的execute()方法
	public void tableauRefreshExecute(JobExecutionContext jobExecutionContext) {
		try {
			logger.debug("adminUsername:" + adminUsername);
			this.logger.debug("每隔2分钟定时任务》》》》》》》》》》》》》》》》 当前时间:" + dateSdf.format(new Date()));
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!--tableauRefreshExecute* tableau 刷新任务******-----" + dateSdf.format(new Date())
			//		+ "------------------------emailTask--------");
			//for循环遍历 
			//符合刷新时间的规则，判断调度状态和任务状态都为 【1】的情况下， 执行工作簿数据刷新
			Calendar nowDate = Calendar.getInstance();
			//循环信息
			for (XmlTablesMain rs : workBookService.queryXmlTablesMainList()) { //查询调度状态和任务状态都为 【1】
				//判断是否符合刷新时间的规则
				/*if (rs.getNextTime() == null) {//时间格式不对，没有成功设置发送时间
					continue;
				}*/
				//task_ref_time任务刷新时间是否大于api_exec_time上一次api任务执行时间
				if(isPrep(rs)==false){//是否需要执行
					this.logger.debug(rs.getWorkbookId()+"task 未刷新 》》》》》》》》》》》》》》》》》");
					continue;
				}
				this.logger.debug(rs.getWorkbookId()+"task 已经刷新 》》》》》》》》》》》》》》》》》");
				//hive数据更新时间>本次的运行时间， 
				long wkRefTime = ConvertUtil.toDate(rs.getTaskRefTime(), ZERO).getTime();
				long nextTime = ObjectUtils.defaultIfNull(rs.getNextTime(), ZERO).getTime();//本次的运行时间，如果超时变为下次的运行时间
				Date today=new Date();
				//更新完就自动刷新的情况 当天 10点刷新  8点就更新完毕数据，则检测到8点多更新完毕后，就进行刷新 ，但是这样与定时刷新有点冲突  8点刷了，10点还会刷 资源浪费
			/*	if((nextTime==0 || wkRefTime<nextTime &&  DateUtils.isSameDay(rs.getNextTime(),today) ) && DateUtils.isSameDay(ConvertUtil.toDate(rs.getTaskRefTime()),today)){
					System.out.println("当前执行时间》》》》》》》"+rs.getNextTime()+"当前时间》》》》》》》》"+new Date()+"是否同一天》》》》"+DateUtils.isSameDay(rs.getNextTime(),new Date()));
					int timesByToday = xmlTablesMainService.getCountsByWorkbookId(rs.getWorkbookId());
					if(timesByToday==1){
						//refreshextractsWorkbook(rs.getWorkbookId());						
					}
					continue;
				}*/
				
				//超过执行时间  hive数据更新完毕的情况 如每天的10点 刷新，但是本次的数据更新完成是11点//该注释标识不明确，标注者：wangxingfei
				//根据刷新频率转换调度任务刷新时间
				Date lastTime=getLastTime(rs);
				Long lastTimeL=lastTime.getTime();
				//任务刷新时间超时时逻辑判断
				//(下一次运行时间==0或者任务刷新时间大于调度任务刷新时间并且调度任务刷新时间是当天)并且(任务刷新时间是当天)
				if((nextTime==0 || wkRefTime>lastTimeL &&  DateUtils.isSameDay(lastTime,today) ) && DateUtils.isSameDay(ConvertUtil.toDate(rs.getTaskRefTime()),today)){
					this.logger.debug("》》》》》上次的执行时间》》》》》》》" + lastTime);
					this.logger.debug(rs.getWorkbookId() + "task hive已刷新  并且hive刷新时间超过了本次的执行时间  并且 为第一次执行工作簿的刷新 》》》》》》》》》》》》》》》》》");
					this.logger.debug("》》》》》下次的执行时间》》》》》》》" + rs.getNextTime() + "当前时间》》》》》》》》" + new Date() + "是否同一天》》》》");
					//根据workbookid获取xmltablesmain表times信息(如果执行工作簿刷新时间等于当前时间times+1，否则times=1)
					int timesByToday = workBookService.getTimesByWorkbookId(rs.getWorkbookId());
					this.logger.debug("》》》》》timesByToday>>>>>>>>>>>> " + timesByToday);
					//相当于今天第一次刷新，第一次刷新需要修改调度任务相关状态
					if(timesByToday==1){
						refreshextractsWorkbook(rs);
						this.logger.debug("》》》》》正式开始执行api refreshextractsWorkbook ");
					}
					continue;
				}
				//以下是正常逻辑判断
				//二分钟遍历一次 
				if (SubscribeType.once.getIndex().equals(rs.getRefreshFreq()) && rs.getNextTime() != null) {
					long d = rs.getNextTime().getTime() - nowDate.getTime().getTime();
					//提前一分钟发邮件,以防被遗漏
					if (d <= 2 * 60 * 1000 && d > 0) {
						//满足发送条件
						refreshextractsWorkbook(rs);
					}
					//判断发送类型是否相同和当前时间是否在发送时间之前 如 当前14：20 在发送时间17：00之前
				} else if (SubscribeType.day.getIndex().equals(rs.getRefreshFreq()) && nowDate.getTime().before(rs.getNextTime())) {
					//计算下次发送时间和当前时间的时间差
					long d = rs.getNextTime().getTime() - nowDate.getTime().getTime();
					//如果时间差>0分钟且小于1分钟（提前1分钟发送）
					if (d <= 2 * 60 * 1000 && d > 0) {
						//满足发送条件发送
						refreshextractsWorkbook(rs);
					}
				} else if (SubscribeType.week.getIndex().equals(rs.getRefreshFreq()) && nowDate.getTime().before(rs.getNextTime())) {
					long d = rs.getNextTime().getTime() - nowDate.getTime().getTime();
					if (d <= 2 * 60 * 1000 && d > 0) {
						//满足发送条件
						refreshextractsWorkbook(rs);

					}
				} else if (SubscribeType.month.getIndex().equals(rs.getRefreshFreq()) && nowDate.getTime().before(rs.getNextTime())) {
					long d = rs.getNextTime().getTime() - nowDate.getTime().getTime();
					if (d <= 2 * 60 * 1000 && d > 0) {
						//满足发送条件
						refreshextractsWorkbook(rs);
					}
				} else if (SubscribeType.year.getIndex().equals(rs.getRefreshFreq()) && nowDate.getTime().before(rs.getNextTime())) {
					long d = rs.getNextTime().getTime() - nowDate.getTime().getTime();
					if (d <= 2 * 60 * 1000 && d > 0) {
						//满足发送条件
						refreshextractsWorkbook(rs);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
		}
	}
	private Date ZERO = new Date(0);
	/**
	 * 是否需要执行
	 * @return
	 */
	public boolean isPrep(XmlTablesMain main) {
		if (main == null) {
			return false;
		}
		//滴滴调度任务完成的回调api的时间
		long wkRefTime = ConvertUtil.toDate(main.getTaskRefTime(), ZERO).getTime();

		//上一次刷新API执行时间
		long apiExecTime = ObjectUtils.defaultIfNull(main.getApiExecTime(), ZERO).getTime();

		return wkRefTime > apiExecTime;
	}
	//设置下次执行时间，当执行后会重新设置 以每天几点发送为例
	public Date getLastTime(XmlTablesMain main) {
		//获取当前时间
		Calendar current =Calendar.getInstance();
		//获取发送时间
		Calendar sendCl =Calendar.getInstance();
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat daySdf = new SimpleDateFormat("HH:mm");
		
		Date lastTime=null;
		try {
			if(SubscribeType.once.getIndex().equals(main.getRefreshFreq())){
				sendCl.setTime(dateSdf.parse(main.getRefreshTime()));
				lastTime=sendCl.getTime();
			}else if(SubscribeType.day.getIndex().equals(main.getRefreshFreq())){
				sendCl.setTime(daySdf.parse(main.getRefreshTime()));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.DAY_OF_YEAR, current.get(Calendar.DAY_OF_YEAR));
				sendCl.add(Calendar.DATE,0);
				lastTime= sendCl.getTime();
			}else if(SubscribeType.week.getIndex().equals(main.getRefreshFreq())){
				//发送时间在当前时间后面
				String[] str = main.getRefreshTime().split("_");
				sendCl.setTime(daySdf.parse(str[1]));
				
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.WEEK_OF_YEAR, current.get(Calendar.WEEK_OF_YEAR));
				sendCl.set(Calendar.DAY_OF_WEEK, Integer.parseInt(str[0]));
				//加7天
				sendCl.add(Calendar.DATE,0);
				lastTime= sendCl.getTime();
			}else if(SubscribeType.month.getIndex().equals(main.getRefreshFreq())){
				SimpleDateFormat monthSdf = new SimpleDateFormat("dd HH:mm");
				//发送时间在当前时间后面
				sendCl.setTime(monthSdf.parse(main.getRefreshTime()));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.MONTH, current.get(Calendar.MONTH));
				//加1月
				sendCl.add(Calendar.MONTH,0);
				lastTime= sendCl.getTime();
			}else if(SubscribeType.year.getIndex().equals(main.getRefreshFreq())){
				SimpleDateFormat yearSdf = new SimpleDateFormat("MM-dd HH:mm");
				//发送时间在当前时间后面
				sendCl.setTime(yearSdf.parse(main.getRefreshTime()));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				//加1年
				sendCl.add(Calendar.YEAR,0);
				lastTime= sendCl.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			this.logger.error(e);
			lastTime = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
			lastTime = null;
		}
		logger.debug("lastTime===========>"+lastTime);
		return lastTime;
	}
	public boolean refreshextractsWorkbook(XmlTablesMain rs) {
		try {
			String workbookId=rs.getWorkbookId();
			Long id = ConvertUtil.toLong(workbookId, 0L);
			Workbooks workbooks = workBookService.getWorkBooksById(id);
			if (workbooks == null) {
				return false;
			}
			//获取刷新次数，并更新执行工作簿刷新日期与刷新次数
			int timesByToday = workBookService.getAndIncrementCountsByWorkbookId(workbookId);
			if (timesByToday == -1) {
				return false;
			}
			//判断今天刷新次数是否大于设置中的次数
			if (timesByToday > refreshTimes) {
				this.logger.debug("timesByToday====>>>>>" + timesByToday);
				//如果今天的刷新次数超过了定义的刷新次数默认5  
				//工作簿的更新状态refreshState改为0未刷新
				rs.setRefreshState("0");
				workBookService.updateXmlTablesMainRefreshStateById(rs);
				//如果刷新频率是仅一次 则修改调度状态改为未启动0 
				this.logger.debug("rs.getRefreshFreq()=======>" + rs.getRefreshFreq());
				if("1".equals(rs.getRefreshFreq())){
					rs.setDispatchState("0");
					workBookService.updateXmlTablesMainDispatchStateById(rs);
					//删除滴滴端调度任务，恒丰不需要wangxingfei
					//xmlTablesMainService.delOneTask(rs);
				}
				return false;
			}
			if (workbooks != null) {
				String siteId = workbooks.getUrlNamespace();
				String projectName = workbooks.getProjectName();
				String workbookName = workbooks.getName();
				//调用命令执行api
				String apiFlag = httpClientCmd(workbookName, siteId, projectName);
				if("200".equals(apiFlag)){
					this.logger.debug(workbookId + "工作簿刷新apizhi执行成功！！！！！》》》》》》》》》》》》");
					//if(apiFlag==true){//api 执行成功
					//定时刷新的api执行成功，然后数据成功修改，至于报表有没有成功刷新不确定
					XmlTablesMain main = new XmlTablesMain();
					main.setWorkbookId(workbookId);
					//修改刷新状态为刷新中,并更新api执行时间
					main.setRefreshState("2");
					main.setApiExecTime(new Date());
					workBookService.updateXmlTablesMainRefreshStateById(main);
					return true;
				}else{
					this.logger.info("执行api失败apiFlag：" + apiFlag);
					return false;
				}
				/*
				 * }else{ //api 执行失败 //执行失败要么抛出异常不走刷新了 要么执行下次刷新 throw new Exception("tableau-工作簿刷新的api执行失败!"); //根据时间间隔，计算下一次的是运行时间 }
				 */
			}

		} catch (Exception e) {
			this.logger.error("!", e);
			e.printStackTrace();
		}
		return false;

	}
	/**
	* @Description: cmd请求方法，执行工作簿刷新操作 
	* @author wangxingfei   
	* @param @param workbookName
	* @param @param siteId
	* @param @param projectName
	* @param @return
	* @param @throws Exception
	* @date 2017年6月28日 下午6:00:13 
	* @version V1.0
	 */
	private String httpClientCmd(String workbookName, String siteId, String projectName){
		String responseMsg = "";
		this.logger.debug("调用zpiCmd命令开始");
		try {
			Map<String, String> map = systemDoMainService.getByDoMainId("Cmd");
			HttpClient httpClient = new HttpClient();
			//设置链接超时时间
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
			//设置读取超时时间
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(15000);
			PostMethod getMethod = new PostMethod(map.get("cmdUrl"));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("cmdEncoding", cmdEncoding);
			jsonObject.put("tmpDir", map.get("tmpdir"));
			jsonObject.put("workbookName", workbookName);
			jsonObject.put("tableauServerUrl", tableauServerUrl);
			jsonObject.put("adminUsername", adminUsername);
			jsonObject.put("adminPassword", adminPassword);
			jsonObject.put("siteId", siteId);
			jsonObject.put("projectName", projectName);
			this.logger.debug("cmdEncoding:" + cmdEncoding + "---tmpDir:" + map.get("tmpdir") + "--workbookName:" + workbookName + "---tableauServerUrl:" + tableauServerUrl + "---adminUsername:" + adminUsername + "---adminPassword:" + adminPassword + "---siteId:" + siteId + "---projectName:" + projectName);
			RequestEntity se = new StringRequestEntity(jsonObject.toString(), "application/json", "UTF-8");
			getMethod.setRequestEntity(se); 
			httpClient.executeMethod(getMethod);
			responseMsg = getMethod.getResponseBodyAsString();
			if (getMethod.getStatusCode() != HttpStatus.SC_OK) {
				logger.debug("Method failed: " + getMethod.getStatusLine());
			} else {
				logger.debug("success");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			this.logger.error(e);
		} catch (HttpException e) {
			e.printStackTrace();
			this.logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			this.logger.error(e);
		}
		logger.debug(responseMsg);
		return responseMsg;
	}
	//每天23:59分执行更新所有的工作簿的状态，将任务状态task_state=0，工作簿的刷新状态refresh_state=0
	public void tableauRefreshInitExecute(JobExecutionContext jobExecutionContext) {
		try {
			this.logger.debug("进入每天23:59分清空定时任务调度状态方法》》》》》》》》》》》》》》》》 当前时间:" + dateSdf.format(new Date()));
			//更新状态
			workBookService.updateXmlTablesMainInit();
			this.logger.debug("更新所有的工作簿的状态成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
		}
	}
	
	//每隔10分钟去查询一次工作簿刷新时间 
	//如果 工作簿的刷新时间>刷新规则的时间  或者 <当前时间2分钟 因为考虑提前1分钟刷新  ，则修改工作簿的刷新时间和刷新状态
	public void tableauTenRefreshExecute(JobExecutionContext jobExecutionContext) {
		try {
			this.logger.debug("每隔10分钟去查询一次工作簿刷新时间》》》》》》》》》》》》》》》》 当前时间:" + dateSdf.format(new Date()));
			//System.out.println("!!!!!!!!TenTenTenTenTenTen!!!!!!!!!!!!!!!--tableauRefreshExecute* tableau 刷新任务******-----"
				//	+ dateSdf.format(new Date()) + "------------------------emailTask--------");
			//符合刷新时间的规则，判断调度状态和任务状态都为 【1】的情况下， 执行工作簿数据刷新
			//循环信息
			String wkIds = "";
			Map<Object, Object> map = new HashMap<Object, Object>();
			//循环状态为1（执行）任务状态1（已更新）刷新状态为2(刷新中) 
			for (XmlTablesMain rs : workBookService.queryXmlTablesMainRefreshingList()) {
				wkIds += rs.getWorkbookId() + ",";
				map.put("id_" + rs.getWorkbookId(), rs.getApiExecTime()); //id,api_exec_time
				map.put("id_wkRefTime_" + rs.getWorkbookId(), rs.getWorkbookRefTime()); //id,wk_ref_time
				//api执行成功时间大于api执行时间的时候返回false
				//api执行时间+2小时小于当前时间则返回true(当api执行开始之后的两小时内刷新失败都会重新刷新,时间在配置文件中配置)
				boolean flag=isFail(rs);
				if(flag==true){ //失败重新进行刷新
					this.logger.debug(rs.getWorkbookId() + "工作簿刷新失败，进行重新刷新》》》》》》》》》》》》》》》》 ");
					refreshextractsWorkbook(rs);
				}
			}
			if(StringUtils.isNotBlank(wkIds)){
				wkIds = wkIds.substring(0, wkIds.length() - 1); //1,2,3,4 
				this.logger.debug("wkIds=====>" + wkIds);
				//根据 ids 查询 工作簿的刷新时间 如果工作簿的刷新时间>刷新规则的时间 ，则修改工作簿的刷新时间和刷新状态
				//修改主表的状态、时间
				//查询workbooks表extracts_refreshed_at字段并在此字段上增加8小时的时间（根据此字段判断工作簿是否刷新完成）
				List<Workbooks> list = workBookService.findRefreshTime(wkIds);
				List<Workbooks> updDataList = new ArrayList<Workbooks>();
				for (Workbooks wk : list) {

					Date apiExecTime = (Date) map.get("id_" + wk.getId());
					Date wkRefTime=(Date) map.get("id_wkRefTime_" + wk.getId());
					if (apiExecTime != null) {
						if (wk.getUpdtime() != null ) { //
							long wkRefTimeL =ObjectUtils.defaultIfNull(wkRefTime, ZERO).getTime();
							long updtimeL=strDateToDate(dateToStrDate(wk.getUpdtime())).getTime();
							long apiExecTimeL=strDateToDate(dateToStrDate(apiExecTime)).getTime();
							this.logger.debug("api执行时间===》" + apiExecTimeL);
							this.logger.debug("tableau中的工作簿刷新时间===》" + updtimeL);
							this.logger.debug("mysql中的工作簿刷新时间===》" + wkRefTimeL);
							this.logger.debug("条件1：" + (updtimeL> apiExecTimeL) + "。条件2：" + (updtimeL>wkRefTimeL));
							//上一次刷新API执行时间
							if( updtimeL> apiExecTimeL &&  updtimeL>wkRefTimeL){
								this.logger.debug(wk.getId() + "工作簿刷新成功！！！！》》》》》》》》》》》》》》》》 ");
								updDataList.add(wk);
							}
						}
					} else {
						if (wk.getUpdtime() != null) {
							updDataList.add(wk);
						}
					}
				}
				if (updDataList != null && updDataList.size() > 0) {
					//System.out.println(updDataList.size() +"个工作簿刷新成功！！！！》》》》》》》》》》》》》》》》 ");
					//修改工作簿刷新时间为workbooks中的工作簿刷新时间，工作簿刷新状态为1（已完成）
					workBookService.updateXmlTablesRefreshTime(updDataList);
					//修改刷新频率为一次调度任务状态为停止
					workBookService.updateOneTask(updDataList);;
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e);
		}
	}
	/**
	 * 是否需要执行
	 * @return
	 */
	public boolean isFail(XmlTablesMain main) {
		if (main == null) {
			return false;
		}

		//刷新API执行时间
		long apiExecTime = ObjectUtils.defaultIfNull(main.getApiExecTime(), ZERO).getTime();

		//API执行成功时间即工作簿的刷新成功时间
		long endTime = ObjectUtils.defaultIfNull(main.getWorkbookRefTime(), ZERO).getTime();
		if (endTime > apiExecTime) {
			return false;
		}
		return (apiExecTime + (refreshInterval * (60 * 60 * 1000))) < System.currentTimeMillis();  //如1点api 执行刷新 +2个小时重新刷新一次<当前时间
	}
	public  String dateToStrDate(Date date) {
		String strdate="";
		try {
			if (date != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    strdate = format.format(date);
			}
		} catch (Exception ex) {
		     ex.printStackTrace();
		}
		return strdate;
	}
	public static Date strDateToDate(String dateString) {
		Date date=null; 
		try  
		 {  
			if(StringUtils.isNotBlank(dateString)){
			     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			     date = sdf.parse(dateString);  
			}
		 }  
		 catch (Exception e){  
			 e.printStackTrace();
		 } 
		 return date;
	}
}
