package byit.tableausubscribe.tab.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.osdp.base.security.UserHolder;
import byit.tableausubscribe.tab.bean.ExcelColumnSubscribe;
import byit.tableausubscribe.tab.bean.ExcelSubscribe;
import byit.tableausubscribe.tab.bean.SendExcelResult;
import byit.tableausubscribe.tab.dao.PushExcelColumnDao;
import byit.tableausubscribe.tab.dao.PushExcelDao;
import byit.utils.Tools;



/** 
* Comments:操作XML文件---ok
*/
@Service
@Transactional
public class PushExcelService extends EmailTask{
	private static final Logger logger = Logger.getLogger(PushExcelService.class);

	
	@Autowired
	private PushExcelDao excelDao ;
	@Autowired
	private PushExcelColumnDao columnDao ;
	/*
	 * 获取订阅列表
	 */
	public List<ExcelSubscribe> getExcelSubscribeList(String searchValue){
		List<ExcelSubscribe> reusltSub = new ArrayList<>();
		reusltSub=excelDao.getExcelSubscribeList(searchValue);
		return reusltSub;
		
	}
	/*
	 * 添加订阅规则
	 */
	/*public String addExcelSubscribe(ExcelSubscribe excel) {
		String result = null;
		String id = excel.getTableId();
		if(id.endsWith(",")){
			excel.setTableId(id.substring(0, id.length()-1));
		}
		
		//不让填重复，类似表的唯一键
		ExcelSubscribe texcel=excelDao.getExcelSubscribeById(excel.getTableId());
		if(Tools.isNotEmpty(texcel.getTableId())){
			result="添加失败：该规则已存在！";
			return result;
		}
		
		 excel.setNextTime();
		 excel.setUserId(UserHolder.getId());
		 boolean flag=excelDao.addExcelSubscribe(excel);
		 if(flag){
			 result = "添加成功！";
		 }else{
			 result = "添加失败！";
		 }
		 logger.info("excel:"+excel+"");
		return result;
	}*/
	
	/**
	 * 
		 *@author lisw
		 *@Description: 同时添加excel主表和字段表
		 *@creatTime:2017年6月16日 下午1:38:27 
		 *@return:@param excel
		 *@return:@return String
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public String addExcelAndColSubscribe(ExcelSubscribe excel,ExcelColumnSubscribe column) {
		String result = null;
		
		//不让填重复，类似表的唯一键
		ExcelSubscribe texcel=excelDao.getExcelSubscribeById(excel.getTableId());
		if(Tools.isNotEmpty(texcel.getTableId())){
			result="添加失败：该规则已存在！";
			return result;
		}
		
		 excel.setNextTime();
		 excel.setUserId(UserHolder.getId());
		 column.setUserId(UserHolder.getId());
		 boolean flag=excelDao.addExcelAndCol(excel,column);
		 if(flag){
			 result = "添加成功！";
		 }else{
			 result = "添加失败！";
		 }
		 logger.info("excel:"+excel+"");
		return result;
	}
	
	
	/*
	 * 查看邮件发送结果
	 */
	public String sendExcelEmail(String id){
		String result = "发送失败！";
		//发送邮件
		try {
			ExcelSubscribe excelSubscribe=excelDao.getExcelSubscribeById(id);
			String tableId=excelSubscribe.getTableId();
			result = this.sendExcelEmailByManual(tableId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * 获取订阅列表
	 */
	public ExcelSubscribe queryExcelSubscribe(String id){
		ExcelSubscribe excelSubscribe=excelDao.getExcelSubscribeById(id);
		return excelSubscribe;
	}
	
	
	
	
	
	
	/*
	 * 更新订阅规则
	 * 
	 */
	public  String updateExcelSubscribe(ExcelSubscribe excel){
		////logger.info("----------------updateExcelSubscribe-begin-------------------------");
		String result = "修改成功！";
		try {
			String id = excel.getTableId();
			if(id.endsWith(",")){
				excel.setTableId(id.substring(0, id.length()-1));
			}
			excel.setNextTime();
			excel.setUserId(UserHolder.getId());
			excelDao.updateExcelSubscribe(excel);
		} catch (Exception e) {
			result="修改失败！";
			e.printStackTrace();
		}
		return result;
	}
	
	public  String updateExcelAndColSubscribe(ExcelSubscribe excel,ExcelColumnSubscribe column){
		String result = "";
		try {
			excel.setNextTime();
			excel.setUserId(UserHolder.getId());
			column.setUserId(UserHolder.getId());
			boolean flag=excelDao.updateExcelAndColSubscribe(excel,column);
			if(flag){
				result = "修改成功！";
			}else{
				result="修改失败！";
			}
		} catch (Exception e) {
			result="修改失败！";
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	private void deleteSendExcelResult(String tableId) throws Exception{
		excelDao.dropSendExcelResult(tableId);
	}
	
	
	/*
	 * 删除订阅规则
	 * 可以不删除发送结果
	 */
	public  boolean  deleteExcelSubscribe( String tableId)  {
		boolean result = false;
		try {
			//删除表
			result=excelDao.deleteExcelConfig(tableId);
			//删除字段
			result=columnDao.deleteExcelColumnSubscribe(tableId);
			
			//删除结果
			result=excelDao.dropSendExcelResult(tableId);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 
		 *@author lisw
		 *@Description: 删除excel主表、字段表、结果表
		 *@creatTime:2017年6月16日 下午4:19:05 
		 *@return:@param tableId
		 *@return:@return boolean
		 *@modifier:
		 *@modifTime:
		 *@throws
	 */
	public  boolean  deleteExcelAndCol( String id)  {
		boolean result = false;
		try {
			//删除表
			result=excelDao.deleteExcelAndCol(id);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/*
	 * 查看邮件发送结果
	 */
	public List<SendExcelResult> querySendExcelResult(String id){
		////logger.info("------------ querySendExcelResult- ------------");
		return excelDao.getExcelSendResultList(id);
	}
	
	
}
