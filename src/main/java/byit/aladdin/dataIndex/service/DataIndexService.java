package byit.aladdin.dataIndex.service;

import java.util.List;
import java.util.Map;

import byit.aladdin.dataIndex.entity.DataIndex;
import byit.aladdin.dataIndex.entity.Ind_rela;
import byit.core.util.page.Pagination;
/***
 * 
 * 类/接口描述:数据指标
 *
 * @author 阮海东
 * 创建时间： 2017年1月5日 下午2:08:25 
 *********************************更新记录******************************
 * 版本：  <版本号>        修改日期：  <日期>        修改人： <修改人姓名>
 * 修改内容：  <修改内容描述>
 **********************************************************************
 */

public interface DataIndexService {

	public int addUpload(DataIndex dataIndex);
	Object queryDataIndex(Pagination pagination);
	int addInd(List<Ind_rela> listexc, String fileName, String storeName, Integer versionNumber);
	public List<DataIndex> SearchDataIndex(Object dataIndex);
	int queryIndCode(String ind_code);
	int updateInd(Ind_rela ind_rela);
	/**
	* @Description: 查询所有数据导入记录 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月2日 上午10:51:48 
	* @version V1.0
	 */
	int queryDataIndex();
	/**
	* @Description: with ID query IND_RELA file upload info
	* @author wangxingfei   
	* @date 2017年5月15日 上午10:46:39
	 */
	DataIndex queryDataIndexById(String id);
	/**
	 * @Description: with fields reportClass,reportSubclass group query IND_RELA 
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月16日 下午2:45:42
	  */
	Map<String, Map<String, List<String>>> queryIndRelaByGroup();
	 /**
	 * @Description: with  reportClass,reportSubclass query IND_RELA
	 * @author wangxingfei
	 * @param @param indRela
	 * @param @return
	 * @return List<String>
	 * @date 2017年5月16日 下午4:20:58
	  */
	 List<String> queryIndRela(Ind_rela indRela);
	 /**
	 * @Description: query index and weidu 
	 * @author wangxingfei
	 * @param @return
	 * @return Map<String,List<String>>
	 * @date 2017年5月17日 上午10:44:08
	  */
	 Map<String, Object> queryIndrelaData(Ind_rela indRela, String currentPageNumber);
	 /**
	 * @Description: with  reportClass,reportSubclass,reportName query IND_RELA 
	 * @author wangxingfei
	 * @param @param indRela
	 * @param @return
	 * @return List<String>
	 * @date 2017年5月17日 下午2:42:12
	  */
	 List<String> queryReportName(Ind_rela indRela);
	 /**
	 * @Description: 根据不同类型查询指标或者维度
	 * @author wangxingfei
	 * @param @return
	 * @return Map<String, List<Ind_rela>>
	 * @date 2017年5月17日 上午10:42:28
	  */
	 Map<String, Object> queryIndrelaDataByIndexName(Ind_rela indRela, String currentPageNumber, String type);
	 /**
	 * @Description: 检查导入文件头 
	 * @author wangxingfei   
	 * @param @param header
	 * @param @return
	 * @date 2017年6月6日 上午11:32:17 
	 * @version V1.0
	  */
	 boolean checkExcelHeader(String header);
	 /**
	 * @Description: 获取版本号 
	 * @author wangxingfei   
	 * @param @return
	 * @date 2017年6月16日 上午9:42:04 
	 * @version V1.0
	  */
	 Integer queryVersionNumber();
	 /**
	 * @Description: 根据版本号查询历史导入数据
	 * @author wangxingfei   
	 * @param @return
	 * @date 2017年6月16日 上午10:26:42 
	 * @version V1.0
	  */
	 List<Ind_rela> queryIndrelaOldAllData(Integer versionNumber);
	 /**
	 * @Description: 根据版本号查询所有导入数据
	 * @author wangxingfei   
	 * @param @return
	 * @date 2017年6月16日 上午10:26:42 
	 * @version V1.0
	  */
	 List<Ind_rela> queryIndrelaAllData(Integer versionNumber);
}
