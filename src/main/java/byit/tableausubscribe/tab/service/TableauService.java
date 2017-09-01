package byit.tableausubscribe.tab.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.tableausubscribe.tab.bean.IdaPmReport;
import byit.tableausubscribe.tab.dao.BankDao;
import byit.tableausubscribe.tab.dao.TableauDao;


/** 
*/
@Service
@Transactional
public class TableauService {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TableauService.class);
	@Autowired
	private TableauDao tabDao;
	@Autowired
	private BankDao bankDao;
	public List<Map<String,Object>> getAllWorkbooks() {
		return tabDao.getAllWorkbooks();
	}
	/**
	 * 一个仪表板对应多个数据源，不同仪表板数据源可能相同
	 * @param dashboardId
	 * @return
	 */
	public List<IdaPmReport> getDataResource1(String dashboardId){
		//logger.info("--------getDataResource---begin------------------");
		//不同仪表板的数据源可能重复，去重
		Map<String,IdaPmReport> iprMap = new HashMap<>();
				
		List<IdaPmReport> dataTables = new ArrayList<>();
		if(dashboardId.endsWith(",")){
			dashboardId = dashboardId.substring(0,dashboardId.length()-1);
		}
		//一个仪表板对应多个数据源
		List<IdaPmReport> iprList = tabDao.getViewDataSources(dashboardId);
		for(IdaPmReport report:iprList){
			if(report.getDatasourceName()!=null){
				//拆分数据源
				for(String ds:report.getDatasourceName().split("#")){
					if(iprMap.get(report.getDatasourceName())==null){
						IdaPmReport rp = new IdaPmReport();
						rp.setDatasourceId(ds);
						rp.setDatasourceName(ds);
						dataTables.add(rp);
						iprMap.put(report.getDatasourceName(), rp);
					}
				}
			}
		}
		
		//logger.info("--------getDataResource---end------------------");
		return dataTables;
	}
	public List<IdaPmReport> getViews(String workbookId){
		//logger.info("--------getViews---------------------");
		return tabDao.getViews(workbookId);
	}
	public List<IdaPmReport> getViewsByWorkBook(String workbookId){
		//logger.info("--------getViewsByWorkBook---------------------");
		return tabDao.getViewsByWorkBook(workbookId);
	}
	public IdaPmReport getView(String viewId) {
		//logger.info("--------getView---------------------");
		return tabDao.getView(Integer.parseInt(viewId));
	}
	public List<IdaPmReport> getViewDataSources(String views) {
		//String views_ = "'"+views.replace(",", "','")+"'";
		return tabDao.getViewDataSources(views);
	}

	public List<Map<String,Object>> getNowUpdate(String reports){
		reports="'"+reports.replaceAll(",", "','")+"'";
		return tabDao.getNowUpdate(reports);
	}
}
