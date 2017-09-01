package byit.aladdin.workBook.service;

import java.util.List;
import java.util.Map;

import byit.aladdin.workBook.entity.Projects;
import byit.aladdin.workBook.entity.Sites;
import byit.aladdin.workBook.entity.WorkbookPlanTaskVo;
import byit.aladdin.workBook.entity.Workbooks;
import byit.aladdin.workBook.entity.XmlTablesMain;
import byit.aladdin.workBook.entity.XmlTablesVo;
import byit.aladdin.workBook.util.Pagination;

public interface WorkBookService {
	/**
	* @Description: 根据条件查询站点下的工作簿与映射表详细信息 
	* @author wangxingfei   
	* @param @param pagination
	* @param @return
	* @date 2017年6月19日 上午11:44:22 
	* @version V1.0
	 */
	List<WorkbookPlanTaskVo> pagedQuery(Pagination pagination);
	/**
	* @Description: 根据条件查询站点下的分页信息 
	* @author wangxingfei   
	* @param @param pagination
	* @param @return
	* @date 2017年6月19日 上午11:44:22 
	* @version V1.0
	 */
	int pagedQueryCount(Pagination pagination);
	/**
	* @Description: 根据站点id查询站点信息 
	* @author wangxingfei   
	* @param @param siteId
	* @param @return
	* @date 2017年6月20日 上午9:34:11 
	* @version V1.0
	 */
	List<Projects> findBySiteId(Long siteId);
	/**
	* @Description: 查询所有站点信息 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月20日 上午9:53:09 
	* @version V1.0
	 */
	List<Sites> findAll();
	/**
	* @Description: 添加工作簿与表映射关系 
	* @author wangxingfei   
	* @param @param vo
	* @param @return
	* @date 2017年6月20日 下午2:58:42 
	* @version V1.0
	 */
	boolean Updateconfirm(WorkbookPlanTaskVo vo);
	/**
	* @Description: 根据workbookid获取xmltables信息
	* @author wangxingfei   
	* @param @param id
	* @param @return
	* @date 2017年6月20日 下午3:45:37 
	* @version V1.0
	 */
	List<XmlTablesVo> queryXmlTablesByWorkBookId(String id);
	/**
	* @Description: 查询需要下载的文件列表
	* @author wangxingfei   
	* @param @param param
	* @param @return
	* @date 2017年6月20日 下午5:58:28 
	* @version V1.0
	 */
	List<Workbooks> queryWorkBookByExtracts(String param);
	/**
	* @Description: 按照workbookid删除xmltables 
	* @author wangxingfei   
	* @param @param workbookId
	* @param @return
	* @date 2017年6月21日 上午10:07:39 
	* @version V1.0
	 */
	int deleteTablesByWorkBookId(String workBookId);
	
	void addTables(List<Map<String, String>> tablesInfoList);
	
	/**
	* @Description: 获取调度状态为1（执行）任务状态1（已更新） 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月21日 下午2:28:27 
	* @version V1.0
	 */
	List<XmlTablesMain> queryXmlTablesMainList();
	/**
	* @Description: 根据工作簿id获取times
	* @author wangxingfei   
	* @param @param workBookId
	* @param @return
	* @date 2017年6月21日 下午2:46:12 
	* @version V1.0
	 */
	int getTimesByWorkbookId(String workBookId);
	/**
	* @Description: 根据id获取工作簿信息
	* @author wangxingfei   
	* @param @param workBookId
	* @param @return
	* @date 2017年6月21日 下午2:46:49 
	* @version V1.0
	 */
	Workbooks getWorkBooksById(Long workBookId);
	/**
	* @Description: 根据id查询xmltablesmain并更新其中的刷新次数与工作簿刷新日期并返回times
	* @author wangxingfei   
	* @param @param workbookId
	* @param @return
	* @date 2017年6月21日 下午3:00:58 
	* @version V1.0
	 */
	int getAndIncrementCountsByWorkbookId(String workbookId);
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
	* @Description: 获取调度状态为1（执行）任务状态1（已更新）刷新状态为2(刷新中) 
	* @author wangxingfei   
	* @param @return
	* @date 2017年6月21日 下午2:28:27 
	* @version V1.0
	 */
	List<XmlTablesMain> queryXmlTablesMainRefreshingList();
	/**
	* @Description: 根据id查询workbooks信息 
	* @author wangxingfei   
	* @param @param ids
	* @param @return
	* @date 2017年6月21日 下午3:39:02 
	* @version V1.0
	 */
	List<Workbooks> findRefreshTime(String ids);
	
	/**
	* @Description: 按workbookid修改刷新状态为1（完成），修改工作簿刷新时间 
	* @author wangxingfei   
	* @param @param workbooks
	* @param @return
	* @date 2017年6月21日 下午3:52:42 
	* @version V1.0
	 */
	void updateXmlTablesRefreshTime(List<Workbooks> workbooks);
	/**
	* @Description: 按workbookid修改调度任务状态为0（停止 ） 
	* @author wangxingfei   
	* @param @param workbooks
	* @date 2017年6月21日 下午4:28:22 
	* @version V1.0
	 */
	void updateOneTask(List<Workbooks> workbooks);
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
