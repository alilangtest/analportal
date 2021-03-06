package byit.aladdin.workBook.dao;

import java.util.List;

import byit.aladdin.workBook.entity.Workbooks;
import byit.aladdin.workBook.entity.XmlTables;
import byit.aladdin.workBook.entity.XmlTablesMain;
import byit.core.plug.mybatis.Mapper;

@Mapper
public interface XmlTablesMainDao {
	/**
	* @Description: 根绝workbookid查询工作簿与表映射详细信息
	* @author wangxingfei   
	* @param @param workBookId
	* @param @return
	* @date 2017年6月19日 上午11:15:42 
	* @version V1.0
	 */
	XmlTablesMain queryXmlTablesMainByWorkBookId(String workBookId);
	/**
	* @Description: 更新XmlTablesMain表相关信息
	* @author wangxingfei   
	* @param @param vo
	* @param @return
	* @date 2017年6月20日 下午3:05:29 
	* @version V1.0
	 */
	int updateXmlTablesMain(XmlTablesMain xmlTablesMain);
	/**
	* @Description: 根据workbook_id删除XmlTables信息
	* @author wangxingfei   
	* @param @param id
	* @param @return
	* @date 2017年6月20日 下午3:07:36 
	* @version V1.0
	 */
	int delXmlTables(String id);
	/**
	* @Description: 批量新增XmlTables信息
	* @author wangxingfei   
	* @param @param id
	* @param @return
	* @date 2017年6月20日 下午3:07:36 
	* @version V1.0
	 */
	int addTables(List<XmlTables> list);
	/**
	* @Description: 根据workbookid获取xmltables信息
	* @author wangxingfei   
	* @param @param id
	* @date 2017年6月20日 下午3:46:28 
	* @version V1.0
	 */
	List<XmlTables> queryXmlTablesByWorkBookId(String id);
	/**
	* @Description: 按照workbookid删除xmltables 
	* @author wangxingfei   
	* @param @param workbookId
	* @param @return
	* @date 2017年6月21日 上午10:07:39 
	* @version V1.0
	 */
	int deleteTablesByWorkBookId(String workBookId);
	/**
	* @Description: 新增方法 
	* @author wangxingfei   
	* @param @param xmlTablesMain
	* @param @return
	* @date 2017年6月21日 上午10:37:53 
	* @version V1.0
	 */
	int addTablesMain(XmlTablesMain xmlTablesMain);
	/**
	* @Description:根据workbookid获取times
	* @author wangxingfei   
	* @param @param workBookId
	* @param @return
	* @date 2017年6月21日 上午10:41:55 
	* @version V1.0
	 */
	int queryTimesByWorkBookId(String workBookId);
	/**
	* @Description: 单个新增XmlTables
	* @author wangxingfei   
	* @param @param xmlTables
	* @param @return
	* @date 2017年6月21日 上午10:44:14 
	* @version V1.0
	 */
	int addTablesData(XmlTables xmlTables);
	/**
	* @Description: 获取调度状态为1（执行）任务状态1（已更新） 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月21日 下午2:28:27 
	* @version V1.0
	 */
	List<XmlTablesMain> queryXmlTablesMainList();
	/**
	* @Description: 获取调度状态为1（执行）任务状态1（已更新）刷新状态为2(刷新中) 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月21日 下午2:28:27 
	* @version V1.0
	 */
	List<XmlTablesMain> queryXmlTablesMainRefreshingList();
	/**
	* @Description: 根据id更新执行次数和工作簿刷新时间 
	* @author wangxingfei   
	* @param @param xmlTablesMain
	* @date 2017年6月21日 下午2:58:34 
	* @version V1.0
	 */
	int updateXmlTablesMainById(XmlTablesMain xmlTablesMain);
	/**
	* @Description: 根据workbookid更新刷新状态 
	* @author wangxingfei   
	* @param @param xmlTablesMain
	* @param @return
	* @date 2017年6月21日 下午3:04:34 
	* @version V1.0
	 */
	int updateXmlTablesMainRefreshStateById(XmlTablesMain xmlTablesMain);
	/**
	* @Description: 根据workbookid更新调度状态 
	* @author wangxingfei   
	* @param @param xmlTablesMain
	* @param @return
	* @date 2017年6月21日 下午3:04:34 
	* @version V1.0
	 */
	int updateXmlTablesMainDispatchStateById(XmlTablesMain xmlTablesMain);
	/**
	* @Description: 按workbookid修改刷新状态为1（完成），修改工作簿刷新时间 
	* @author wangxingfei   
	* @param @param workbooks
	* @param @return
	* @date 2017年6月21日 下午3:52:42 
	* @version V1.0
	 */
	int updateXmlTablesRefreshTime(Workbooks workbooks);
	/**
	* @Description: 根据id字符串查询xmltablesmain信息 
	* @author wangxingfei   
	* @param @param workBookIds
	* @param @return
	* @date 2017年6月21日 下午4:23:49 
	* @version V1.0
	 */
	List<XmlTablesMain> queryXmlTablesMainByWorkBookIds(String workBookIds);
	/**
	* @Description: 新增 XmlTablesMain信息
	* @author wangxingfei   
	* @param @param xmlTablesMain
	* @param @return
	* @date 2017年6月21日 下午5:16:52 
	* @version V1.0
	 */
	int addXmlTablesMain(XmlTablesMain xmlTablesMain);
	/**
	* @Description: 修改xmltablesmain任务状态为已更新，并更新任务刷新时间 
	* @author wangxingfei   
	* @param @param xmlTablesMain
	* @param @return
	* @date 2017年6月23日 下午2:16:34 
	* @version V1.0
	 */
	int updateXmlTablesMainTaskStateById(XmlTablesMain xmlTablesMain);
	/**
	* @Description: 更新所有调度任务：工作簿刷新状态为0 （未完成）任务状态为0（未更新） 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月27日 上午10:22:43 
	* @version V1.0
	 */
	int updateXmlTablesMainInit();
}
