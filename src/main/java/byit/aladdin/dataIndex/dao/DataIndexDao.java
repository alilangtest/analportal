package byit.aladdin.dataIndex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import byit.aladdin.dataIndex.entity.DataIndex;
import byit.aladdin.dataIndex.entity.Ind_rela;
import byit.core.plug.mybatis.Mapper;

/**
 * 
 * @author: 阮海东
 *
 * 
 */
@Mapper
public interface DataIndexDao {

	/**
	 * 添加数据
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>>  queryDataIndex(Map<String, Object> p, RowBounds bounds);
	 int addUpload(DataIndex dataIndex);
	// List<DataIndex> queryDataIndex();
	 int addInd(Ind_rela ind_rela);
	 List<DataIndex> SearchDataIndex(Object dataIndex);
	 int queryIndCode(String ind_code);
	 /*int updateInd(Ind_rela ind_rela);*/
	 /**
	 * @Description: 获取上传记录总条数 
	 * @author wangxingfei   
	 * @param @return
	 * @date 2017年6月2日 上午11:25:38 
	 * @version V1.0
	  */
	 int queryDataIndexCount();
	 /**
	 * @Description: delete IND_RELA_OLD data 
	 * @author wangxingfei   
	 * @date 2017年5月15日 上午10:44:45
	  */
	 int deleteIndRelaold();
	 /**
	 * @Description: delete IND_RELA data
	 * @author wangxingfei   
	 * @date 2017年5月15日 上午10:45:07
	  */
	 int deleteIndRela();
	 /**
	 * @Description: copy IND_RELA data to IND_RELA_OLD
	 * @author wangxingfei   
	 * @date 2017年5月15日 上午10:45:30
	  */
	 int insertIndRelaold();
	 /**
	 * @Description: with ID query IND_RELA file upload info
	 * @author wangxingfei   
	 * @date 2017年5月15日 上午10:45:54
	  */
	 DataIndex queryDataIndexById(String id);
	 /**
	 * @Description: with fields reportClass,reportSubclass group query IND_RELA 
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月16日 下午2:45:42
	  */
	 List<Ind_rela> queryIndRelaByGroup();
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
	 * @Description: with  reportClass,reportSubclass,reportName query IND_RELA 
	 * @author wangxingfei
	 * @param @param indRela
	 * @param @return
	 * @return List<String>
	 * @date 2017年5月17日 下午2:42:12
	  */
	 List<String> queryReportName(Ind_rela indRela);
	 /**
	 * @Description: with  reportClass,reportSubclass,reportName query index
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月17日 上午10:42:28
	  */
	 List<Ind_rela> queryIndrelaData(Ind_rela indRela);
	 /**
	 * @Description: with  reportClass,reportSubclass,reportName query index
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月17日 上午10:42:28
	  */
	 Integer queryIndrelaDataTotleNumbers(Ind_rela indRela);
	 /**
	 * @Description: with  reportClass,reportSubclass,reportName,indexName query index
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月17日 上午10:42:28
	  */
	 List<Ind_rela> queryIndrelaDataByIndexName(Ind_rela indRela);
	 /**
	 * @Description: with  reportClass,reportSubclass,reportName query weidu 
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月17日 上午10:42:32
	  */
	 List<Ind_rela> queryIndrelaDataWeiDu(Ind_rela indRela);
	 /**
	 * @Description: with  reportClass,reportSubclass,reportName query weidu 
	 * @author wangxingfei
	 * @param @return
	 * @return List<Ind_rela>
	 * @date 2017年5月17日 上午10:42:32
	  */
	 Integer queryIndrelaDataWeiDuTotleNumbers(Ind_rela indRela);
	 /**
	 * @Description: with domainid query systemDomain
	 * @author wangxingfei   
	 * @param @param type
	 * @param @return
	 * @date 2017年6月6日 上午11:28:58 
	 * @version V1.0
	  */
	 String findSystemDomainByType(String type);
	 /**
	 * @Description: 查询上一次导入文件的版本号
	 * @author wangxingfei   
	 * @param @return
	 * @date 2017年6月16日 上午9:43:24 
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
