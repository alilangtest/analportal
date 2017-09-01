package byit.tableausubscribe.tab.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.tableausubscribe.tab.bean.IdaPmReport;
import byit.tableausubscribe.tab.dao.BankDao;



/** 
*/
@Service
@Transactional
public class BankService {

	@Autowired
	private BankDao bankDao;
	public int isNewTableTime(String tableIds){
		//拆分数据源
		return bankDao.isNewTableTime(tableIds);
	}
	
	public List<IdaPmReport> getDataSource(){
		//拆分数据源
		return bankDao.getDataSource();
	}
	
	/**************************excel***********************************/
	public int isNewExcelTableTime(String tableIds){
		//拆分数据源
		return bankDao.isNewTableTime(tableIds);
	}
	/**************************excel end***********************************/

}
