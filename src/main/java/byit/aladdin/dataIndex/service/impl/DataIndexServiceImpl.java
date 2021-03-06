package byit.aladdin.dataIndex.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import byit.aladdin.dataIndex.dao.DataIndexDao;
import byit.aladdin.dataIndex.entity.DataIndex;
import byit.aladdin.dataIndex.entity.Ind_rela;
import byit.aladdin.dataIndex.service.DataIndexService;
import byit.aladdin.dataIndex.util.PageUtil;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.security.UserHolder;

/***
 * 
 * 类/接口描述: 
 *
 * @author 阮海东
 * @version 1.0
 * JDK版本：sun jdk 1.7
 */
@Service("DataIndexService")
public class DataIndexServiceImpl implements DataIndexService{
	@Autowired
	private DataIndexDao dataIndexDao;
	
	public int addUpload(DataIndex dataIndex){
	  return dataIndexDao.addUpload(dataIndex);
	}
	@Override
	public int queryDataIndex(){
		return dataIndexDao.queryDataIndexCount();
	}
	@Override
	public Object queryDataIndex(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		//获得参数
		String file_name=pagination.getFilters().get("file_name").toString();
		String operation_name=pagination.getFilters().get("operation_name").toString();
		params.put("file_name", file_name);
		params.put("operation_name", operation_name);
		//获得分页
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());
		Page<Map<String, Object>> page = bounds.wrap(dataIndexDao.queryDataIndex(params, bounds));
		return page;
	}
	@Override
	public int addInd(List<Ind_rela> listexc, String fileName, String storeName, Integer versionNumber) {
		//删除备份表信息
		//dataIndexDao.deleteIndRelaold();
		//备份数据到备份表
		dataIndexDao.insertIndRelaold();
		//删除指标表信息 
		dataIndexDao.deleteIndRela();
		int row = 0;
		DataIndex dataIndex = new DataIndex();
		UUID uuid = UUID.randomUUID();
		String operation_name = UserHolder.getUsername();// 获取当前登录用户
		dataIndex.setId(uuid.toString());
		dataIndex.setFile_name(fileName);
		dataIndex.setStoreName(storeName);
		dataIndex.setVersionNumber(versionNumber);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(new Date());
		try {
			dataIndex.setOperation_date(formatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dataIndex.setOperation_name(operation_name);
		dataIndex.setRemarks("");
		
		for (Ind_rela ind_rela : listexc) {
			UUID uuid1 = UUID.randomUUID();
			ind_rela.setId(uuid1.toString());
			ind_rela.setVersionNumber(versionNumber);
			dataIndexDao.addInd(ind_rela);
		}
		row = addUpload(dataIndex);
//		FileUpload.delAllFile(url);
		return row;
	}
	
	public List<DataIndex> SearchDataIndex(Object dataIndex) {
		return dataIndexDao.SearchDataIndex(dataIndex);
	}
	@Override
	public int queryIndCode(String ind_code) {
		return dataIndexDao.queryIndCode(ind_code);
	}
	public int updateInd(Ind_rela ind_rela){
		/*return dataIndexDao.updateInd(ind_rela);*/
		return 0;
	}
	@Override
	public DataIndex queryDataIndexById(String id) {
		return dataIndexDao.queryDataIndexById(id);
	}
	@Override
	public Map<String, Map<String, List<String>>> queryIndRelaByGroup() {
		Map<String, Map<String, List<String>>> resultMap = new HashMap<String, Map<String, List<String>>>();
		//数据查询
		List<Ind_rela> relas = this.dataIndexDao.queryIndRelaByGroup();
		//数据处理组装
		for (Ind_rela ind_rela : relas) {
			if(resultMap.containsKey(ind_rela.getFunctionClass())){
				if(resultMap.get(ind_rela.getFunctionClass()).containsKey(ind_rela.getReportClass())){
					resultMap.get(ind_rela.getFunctionClass()).get(ind_rela.getReportClass()).add(ind_rela.getReportSubclass());
				}else{
					List<String> result = new ArrayList<String>();
					result.add(ind_rela.getReportSubclass());
					resultMap.get(ind_rela.getFunctionClass()).put(ind_rela.getReportClass(), result);
				}
			}else{
				Map<String, List<String>> map = new HashMap<String,List<String>>();
				List<String> result = new ArrayList<String>();
				result.add(ind_rela.getReportSubclass());
				map.put(ind_rela.getReportClass(), result);
				resultMap.put(ind_rela.getFunctionClass(), map);
			}
		}
		return resultMap;
	}
	@Override
	public List<String> queryIndRela(Ind_rela indRela) {
		return this.dataIndexDao.queryIndRela(indRela);
	}
	@Override
	public Map<String, Object> queryIndrelaData(Ind_rela indRela, String currentPageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageUtil pageUtil = new PageUtil();
		pageUtil.setCurrentPageNumber(Integer.valueOf(currentPageNumber));
		PageUtil weiduPageUtil = new PageUtil();
		weiduPageUtil.setCurrentPageNumber(Integer.valueOf(currentPageNumber));
		//没有总条数时获取总条数
//		if(pageUtil.getTotleNumber() == null || weiduPageUtil.getTotleNumber() == null){
		//获取指标总条数
		Integer index = this.dataIndexDao.queryIndrelaDataTotleNumbers(indRela);
		//获取维度总条数
		Integer weidu = this.dataIndexDao.queryIndrelaDataWeiDuTotleNumbers(indRela);
		//设置指标/维度总条数
		pageUtil.setTotleNumber(index);
		weiduPageUtil.setTotleNumber(weidu);
		//分页信息返回到前台
		map.put("indexPage", pageUtil);
		map.put("weiduPage", weiduPageUtil);
//		}
		indRela.setStratRow(pageUtil.getStratNumber());
		indRela.setEndRow(pageUtil.getEndNumber());
		List<Ind_rela> indexs = this.dataIndexDao.queryIndrelaData(indRela);
		indRela.setStratRow(weiduPageUtil.getStratNumber());
		indRela.setEndRow(weiduPageUtil.getEndNumber());
		List<Ind_rela> weiDus = this.dataIndexDao.queryIndrelaDataWeiDu(indRela);
		map.put("index", indexs);
		map.put("weidu", weiDus);
		return map;
	}
	@Override
	public List<String> queryReportName(Ind_rela indRela) {
		return this.dataIndexDao.queryReportName(indRela);
	}
	@Override
	public Map<String, Object> queryIndrelaDataByIndexName(Ind_rela indRela, String currentPageNumber, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ind_rela> indexs;
		PageUtil pageUtil = new PageUtil();
		pageUtil.setCurrentPageNumber(Integer.valueOf(currentPageNumber));
		indRela.setStratRow(pageUtil.getStratNumber());
		indRela.setEndRow(pageUtil.getEndNumber());
		if("index".equals(type)){
			if(indRela.getIndexName() != null && !indRela.getIndexName().isEmpty()){
				//获取指标总条数
				Integer index = this.dataIndexDao.queryIndrelaDataTotleNumbers(indRela);
				pageUtil.setTotleNumber(index);
				map.put("indexPage", pageUtil);
				indexs = this.dataIndexDao.queryIndrelaDataByIndexName(indRela);
			}else{
				indexs = this.dataIndexDao.queryIndrelaData(indRela);
			}
			map.put("index", indexs);
		}else{
			indexs = this.dataIndexDao.queryIndrelaDataWeiDu(indRela);
			map.put("weidu", indexs);
		}
		return map;
	}
	@Override
	public boolean checkExcelHeader(String header) {
		String rsult = dataIndexDao.findSystemDomainByType("excelHeader");
		return rsult.equals(header);
	}
	@Override
	public Integer queryVersionNumber() {
		return dataIndexDao.queryVersionNumber();
	}
	@Override
	public List<Ind_rela> queryIndrelaOldAllData(Integer versionNumber) {
		return dataIndexDao.queryIndrelaAllData(versionNumber);
	}
	@Override
	public List<Ind_rela> queryIndrelaAllData(Integer versionNumber) {
		return dataIndexDao.queryIndrelaAllData(versionNumber);
	}
}
