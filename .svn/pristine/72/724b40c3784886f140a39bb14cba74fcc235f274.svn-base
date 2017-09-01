package byit.aladdin.dataIndex.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import byit.aladdin.dataIndex.entity.DataIndex;
import byit.aladdin.dataIndex.entity.Ind_rela;
import byit.aladdin.dataIndex.service.DataIndexService;
import byit.aladdin.dataIndex.util.ByitConfig;
import byit.aladdin.dataIndex.util.ExcelOperator;
import byit.aladdin.dataIndex.util.FileUpload;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.common.easyui.DataGridAdapter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 指标相关操作控制层
 * @author wxf
 *
 */
@Controller
@RequestMapping(value = "/index_manager")
public class DataIndexController {
	private final Logger logger = Logger.getLogger(DataIndexController.class);
	@Autowired
	private DataIndexService dataIndexService;
	Map<String, Object> resultMap;
	@Autowired
	private DataGridAdapter dataGridAdapter;

	public DataIndexController() {
		resultMap = new HashMap<String, Object>();
	}

	@RequestMapping(value = "/dataIndex.html")
	private ModelAndView dataIndex(HttpServletRequest request, ModelMap mode) {
		ModelAndView model = new ModelAndView();
		model.setViewName("dataIndex/dataIndex");
		model.addObject("error", request.getParameter("error"));
		return model;
	}

	@RequestMapping(value = "/fileUpload.html")
	private ModelAndView fileUpload(HttpServletRequest request, ModelMap mode) {
		ModelAndView model = new ModelAndView();
		model.setViewName("dataIndex/fileUpload");
		return model;
	}
	@RequestMapping(value = "/dataIndexShow.html")
	private String dataIndexShow(HttpServletRequest request, ModelMap mode) {
		return "dataIndex/dataIndexShow";
	}
	
	@RequestMapping(value = "/dataIndexOpenShow.html")
	private Object dataIndexOpenShow(HttpServletRequest request, ModelMap mode) {
		String reportName = "";
		String functionClass = "";
		String reoprtClass = "";
		String reportSubclass = "";
		try {
			reportName = URLDecoder.decode(request.getParameter("reportName"), "UTF-8");
			functionClass = URLDecoder.decode(request.getParameter("functionClass"), "UTF-8");
			reoprtClass = URLDecoder.decode(request.getParameter("reoprtClass"), "UTF-8");
			reportSubclass = URLDecoder.decode(request.getParameter("reportSubclass"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.debug("reportName:" + reportName);
		logger.debug("functionClass:" + functionClass);
		logger.debug("reoprtClass:" + reoprtClass);
		logger.debug("reportSubclass:" + reportSubclass);
		ModelAndView map = new ModelAndView();
		map.setViewName("dataIndex/dataIndexOpenShow");
		map.addObject("reportName",reportName);
		map.addObject("functionClass",functionClass);
		map.addObject("reoprtClass",reoprtClass);
		map.addObject("reportSubclass",reportSubclass);
		return map;
	}
	@RequestMapping(value = "/weiDuEdit.html")
	public Object weiDuEdit(){
		return "dataIndex/weiDuEdit";
	}
	
	
	
	/**
	 *  查询数据导入记录表条数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDataIndexCount")
	@ResponseBody
	public String queryDataIndexCount(HttpServletRequest request) {
		int result = dataIndexService.queryDataIndex();
		if(result > 0){
			return "true";
		}else{
			return "false";
		}
	}
	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(HttpServletRequest request) {
		return "queryDataIndexCountqueryDataIndexCount";
	}
	/**
	* @Description: upload Data
	* @author wxf
	* @param @param request
	* @param @param response
	* @param @param file
	* @param @return
	* @return Map<String,Object>
	* @date 2017年5月15日 上午11:08:38
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploaddata", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> uploaddata(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//获取版本号
			int versionNumber = dataIndexService.queryDataIndex();
			versionNumber ++;
			String fileName = file.getOriginalFilename();
			logger.debug("fileName:" + fileName);
			if (fileName != null && fileName != "") {
				String url = FileUpload.upload(file, request, response);
				logger.debug("upload File URL:" + url);
				String storeName = url.substring(url.lastIndexOf(File.separator) + 1);
				logger.debug("upload storeName:" + url);
				if(ExcelOperator.initType(storeName) == null){
					map.put("success", false);
					map.put("msg", "数据文件格式错误！");
					return map;
				}
				Map<String, Object> result = ExcelOperator.readExcel(url, storeName);
				if(result.get("header") == null){
					map.put("success", false);
					map.put("msg", "数据文件标题错误！");
					return map;
				}
				boolean flg = dataIndexService.checkExcelHeader(result.get("header").toString());
				if(!flg){
					map.put("success", false);
					map.put("msg", "数据文件标题错误！");
					return map;
				}
				if("1".equals(result.get("error").toString())){
					map.put("success", false);
					map.put("path", "error.text");
					map.put("msg", "上传文件错误，点击我下载错误列表！");
					return map;
				}
				List<Ind_rela> listexc = (List<Ind_rela>) result.get("sucess");
				if(listexc == null || listexc.isEmpty()){
					map.put("success", false);
					map.put("msg", "数据文件中没有指标内容");
					return map;
				}
				int row = dataIndexService.addInd(listexc, fileName, storeName, versionNumber);
				if (row == 1) {
					map.put("success", true);
					map.put("msg", "数据文件上传成功！");
				}
			}else{
				map.put("success", false);
				map.put("msg", "上传出错，请重新选择文件！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("===指标数据文件上传出错了===", e);
			map.put("success", false);
			map.put("msg", "指标数据文件上传失败！");
		}
		return map;
	}

	/**
	 * 查询数据指标
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querydata")
	@ResponseBody
	public Map<String, Object> querydata(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pagination pagination = dataGridAdapter.getPagination();
		try {
			Page<Map<String, Object>> page = (Page<Map<String, Object>>) this.dataIndexService.queryDataIndex(pagination);
			map.put("rows", page.getRecords());
			map.put("total", page.getTotal());
			map.put("success", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 瀵煎嚭鎽告澘
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/dol")
	@ResponseBody
	public void exportExcelInfo(HttpServletRequest request, HttpServletResponse response) {
		URL url = this.getClass().getClassLoader().getResource("");
		String path = url.getPath();
		path = path.substring(0, path.length() - 16);
		//设置文件下载名称
		response.setHeader("Content-Disposition", "attachment;filename=template.xlsx");
		File file = new File(path + File.separator + "template" + File.separator + "dataIndexTemplate.xlsx");
		writeFile(request, response, file);
	}
	/**
	* @Description: download file 
	* @author wxf
	* @param @param request
	* @param @param response
	* @param @param id
	* @return void
	* @date 2017年5月15日 上午11:06:17
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	public void downloadFile(HttpServletRequest request, HttpServletResponse response , @RequestParam(value = "id") String id){
		DataIndex index = dataIndexService.queryDataIndexById(id);
		try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String(index.getFile_name().getBytes("gb2312"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		File file = new File(ByitConfig.FILE_UPLOAD_PATH + index.getStoreName());
		if(file.exists()){
			writeFile(request, response, file);
		}else{
			//根据版本号查询历史导入数据
			List<Ind_rela>  list = dataIndexService.queryIndrelaOldAllData(index.getVersionNumber());
			//历史导入数据查询不到则去主表查询(第一次可能出现此情况)
			if(list == null || list.isEmpty()){
				list = dataIndexService.queryIndrelaAllData(index.getVersionNumber());
			}
			List<String> cells = new ArrayList<String>();
			cells.add("功能分类");
			cells.add("报表分类");
			cells.add("报表子类");
			cells.add("报表名称");
			cells.add("分类");
			cells.add("指标");
			cells.add("数据库");
			cells.add("表中文名");
			cells.add("表英文名");
			cells.add("表含义描述");
			cells.add("是否展示");
			cells.add("字段中文名");
			cells.add("字段英文名");
			cells.add("字段含义描述");
			cells.add("计算公式");
			cells.add("字段值");
			cells.add("状态");
			Workbook wbk = ExcelOperator.createExcel(list, cells, index.getFile_name().substring(index.getFile_name().lastIndexOf(".") + 1, index.getFile_name().length()));
			response.setContentType("application/vnd.ms-excel");
	        try {
	        	OutputStream ouputStream = response.getOutputStream();    
	        	wbk.write(ouputStream);    
	        	ouputStream.flush();    
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	* @Description: write file to page
	* @author wangxingfei
	* @param @param request
	* @param @param response
	* @param @param file
	* @return void
	* @date 2017年5月19日 下午4:45:24
	 */
	private void writeFile(HttpServletRequest request, HttpServletResponse response, File file){
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
		try {
			response.setContentType("application/force-download");// 设置强制下载不打开
			
			if(file.exists()){
				 byte[] buffer = new byte[1024];
				 fis = new FileInputStream(file);
				 bis = new BufferedInputStream(fis);
				 os = response.getOutputStream();
				 int i = bis.read(buffer);
				 while (i != -1) {
				     os.write(buffer, 0, i);
				     i = bis.read(buffer);
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	* @Description: 下载错误列表文件 
	* @author wangxingfei
	* @param @param request
	* @param @param response
	* @param @param id
	* @return void
	* @date 2017年5月19日 下午2:09:27
	 */
	@RequestMapping(value = "/downloadErrorFile")
	@ResponseBody
	public void downloadErrorFile(HttpServletRequest request, HttpServletResponse response){
		File file = new File(ByitConfig.FILE_ERROR_PATH + "error.txt");
		response.setHeader("Content-Disposition", "attachment;filename=error.txt");
		writeFile(request, response, file);
	}
	/**
	 * 搜索
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDataIndex")
	@ResponseBody
	public Map<String, Object> queryDataIndex(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String file_name = request.getParameter("file_name");
		String operation_name = request.getParameter("uploadPeople");
		DataIndex dataIndex = new DataIndex();
		if (file_name != "" && file_name != null) {
			dataIndex.setFile_name(file_name);
		}
		if (operation_name != "" && operation_name != null) {
			dataIndex.setOperation_name(operation_name);
		}
		List<DataIndex> searchList = dataIndexService.SearchDataIndex(dataIndex);
		map.put("rows", searchList);
		map.put("row", searchList);
		return map;
	}
	/**
	 * 查询左侧菜单数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryLeftData")
	@ResponseBody
	public String queryLeftData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Map<String, List<String>>> map = dataIndexService.queryIndRelaByGroup();
		JSONArray arrays = new JSONArray();
		
		int parentId = 0;
		int index = 2;
		for(Map.Entry<String, Map<String, List<String>>> entry1 : map.entrySet()){
			parentId++;
			JSONObject obj = new JSONObject();
			obj.put("id", parentId);
			obj.put("name", entry1.getKey());
			obj.put("parentId", "0");
			obj.put("url", "");
			obj.put("icon", "");
			obj.put("order", "1");
			obj.put("isHeader", "1");
			//二级
			JSONArray array = new JSONArray();
			for (Map.Entry<String, List<String>> entry : entry1.getValue().entrySet()) {
				index++;
				//二级菜单
				JSONObject obj1 = new JSONObject();
				obj1.put("id", index + "_tow");
				obj1.put("name", entry.getKey());
				obj1.put("parentId", parentId);
				obj1.put("url", "");
				obj1.put("icon", "&#xe604");
				obj1.put("order", "1");
				obj1.put("isHeader", "0");
				JSONArray array1 = new JSONArray();
				//三级菜单
				for (String value : entry.getValue()) {
					JSONObject obj2 = new JSONObject();
					obj2.put("id", "");
					obj2.put("name", value);
					obj2.put("parentId", index);
					obj2.put("url", "");
					obj2.put("icon", "&#xe604");
					obj2.put("order", "1");
					obj2.put("isHeader", "0");
					obj2.put("childMenus", "");
					array1.add(obj2);
				}
				obj1.put("childMenus", array1);
				array.add(obj1);
			}
			obj.put("childMenus", array);
			arrays.add(obj);
		}
		return arrays.toString();
	}
	/**
	* @Description: 根据分类查询报表信息 
	* @author wangxingfei
	* @param @param request
	* @param @param response
	* @param @param reoprtClass
	* @param @param reportSubclass
	* @param @return
	* @return Map<String,List<String>>
	* @date 2017年5月16日 下午4:18:41
	 */
	@RequestMapping(value = "/queryReportNameData")
	@ResponseBody
	public Object queryReportNameData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "functionClass") String functionClass, @RequestParam(value = "reportClass") String reoprtClass, @RequestParam(value = "reportSubclass") String reportSubclass) {
		Ind_rela indRela = new Ind_rela();
		indRela.setFunctionClass(functionClass);
		indRela.setReportClass(reoprtClass);
		indRela.setReportSubclass(reportSubclass);
		List<String> data = dataIndexService.queryIndRela(indRela);
		return data;
	}
	/**
	* @Description: 根据分类、报表名称模糊查询报表信息 
	* @author wangxingfei
	* @param @param request
	* @param @param response
	* @param @param reoprtClass
	* @param @param reportSubclass
	* @param @return
	* @return Map<String,List<String>>
	* @date 2017年5月16日 下午4:18:41
	 */
	@RequestMapping(value = "/queryReportName")
	@ResponseBody
	public Object queryReportName(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "functionClass") String functionClass, @RequestParam(value = "reportClass") String reoprtClass, @RequestParam(value = "reportSubclass") String reportSubclass, @RequestParam(value = "reportName") String reportName) {
		Ind_rela indRela = new Ind_rela();
		indRela.setFunctionClass(functionClass);
		indRela.setReportClass(reoprtClass);
		indRela.setReportSubclass(reportSubclass);
		indRela.setReportName(reportName);
		List<String> data = dataIndexService.queryReportName(indRela);
		return data;
	}
	/**
	* @Description: 根据 条件查询指标/维度信息 
	* @author wangxingfei
	* @param @param request
	* @param @param response
	* @param @param reoprtClass
	* @param @param reportSubclass
	* @param @param reportName
	* @param @return
	* @return Map<String,List<Ind_rela>>
	* @date 2017年5月17日 下午2:21:08
	 */
	@RequestMapping(value = "/queryIndrelaData")
	@ResponseBody
	public Map<String, Object> queryIndrelaData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "functionClass") String functionClass, @RequestParam(value = "reportClass") String reoprtClass
			, @RequestParam(value = "reportSubclass") String reportSubclass, @RequestParam(value = "reportName") String reportName, @RequestParam(value = "indexName") String indexName
			, @RequestParam(value = "currentPageNumber") String currentPageNumber, @RequestParam(value = "vagueReportName") String vagueReportName) {
		Ind_rela indRela = new Ind_rela();
		indRela.setFunctionClass(functionClass);
		indRela.setReportClass(reoprtClass);
		indRela.setReportSubclass(reportSubclass);
		indRela.setReportName(reportName);
		indRela.setVagueReportName(vagueReportName);
		Map<String, Object> map = new HashMap<String, Object>();
		if(indexName != null && !indexName.isEmpty()){
			indRela.setIndexName(indexName);
			map = dataIndexService.queryIndrelaDataByIndexName(indRela, currentPageNumber, "index");
		}else{
			map = dataIndexService.queryIndrelaData(indRela, currentPageNumber);
		}
		return map;
	}
	/**
	* @Description: 根据 条件查询指标/维度信息 
	* @author wangxingfei
	* @param @param request
	* @param @param response
	* @param @param reoprtClass
	* @param @param reportSubclass
	* @param @param reportName
	* @param @return
	* @return Map<String,List<Ind_rela>>
	* @date 2017年5月17日 下午2:21:08
	 */
	@RequestMapping(value = "/queryIndrelaPageData")
	@ResponseBody
	public Map<String, Object> queryIndrelaPageData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "functionClass") String functionClass, @RequestParam(value = "reportClass") String reoprtClass
			, @RequestParam(value = "reportSubclass") String reportSubclass, @RequestParam(value = "reportName") String reportName, @RequestParam(value = "indexName") String indexName
			, @RequestParam(value = "currentPageNumber") String currentPageNumber, @RequestParam(value = "type") String type, @RequestParam(value = "vagueReportName") String vagueReportName) {
		Ind_rela indRela = new Ind_rela();
		indRela.setFunctionClass(functionClass);
		indRela.setReportClass(reoprtClass);
		indRela.setReportSubclass(reportSubclass);
		indRela.setReportName(reportName);
		Map<String, Object> map = new HashMap<String, Object>();
		if(indexName != null && !indexName.isEmpty()){
			indRela.setIndexName(indexName);
		}
		indRela.setVagueReportName(vagueReportName);
		map = dataIndexService.queryIndrelaDataByIndexName(indRela, currentPageNumber, type);
		return map;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
}
