package byit.aladdin.dataAnalysis.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

import byit.aladdin.dataAnalysis.dao.LogDao;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;

/*
 * 以前可能导入错包了，导入的是javax.transaction.Transactional，事物管理应该用Spring的jar包
 * modify  by lisw
 */
@Transactional
@Service("logService")
public class LogService {
	
	@Autowired
	private LogDao logDao;
	/**
	 * 根据分页参数查询列表
	 * @param pagination
	 * @return
	 */
	public Page<Map<String, Object>> queryLogListforPage(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		//获得参数
		String authOrg = pagination.getFilters().getString("authOrg");
		if(!StringUtils.isEmpty(authOrg)){
			//将参数传入
			params.put("authOrg", authOrg);
		}
		String username=pagination.getFilters().get("username").toString();
		if(username !=null && !username.equals("")){
			//将参数传入
			params.put("username", username);
		}
		String menu=pagination.getFilters().get("menu").toString();
		if(menu !=null && !menu.equals("")){
			//将参数传入
			params.put("menu", menu);
		}
		String beginlogdate=pagination.getFilters().get("beginlogdate").toString();
		if(beginlogdate !=null && !beginlogdate.equals("")){
			//将参数传入
			params.put("beginlogdate", beginlogdate);
		}
		String endlogdate1=pagination.getFilters().get("endlogdate").toString();
		//判断选择前后时间是否一样
		if(beginlogdate !=null && !beginlogdate.equals("") && endlogdate1 !=null && !endlogdate1.equals("")){
			String endlogdate = getDate(endlogdate1);
			params.put("endlogdate", endlogdate);
		}
		if((beginlogdate ==null || beginlogdate.equals("")) && !endlogdate1.equals("")){
			String endlogdate = getDate(endlogdate1);
			params.put("endlogdate", endlogdate);
		}
		//获得分页
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());

		Page<Map<String, Object>> page = bounds.wrap(logDao.queryLogListforPage(params,bounds));
		return page;
	}
	
	//日期字符串加一天
	public static String getDate(String endlogdate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str="";
		try {
			Date date = sdf.parse(endlogdate);
			Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(date); 
		    calendar.add(Calendar.DATE,1);
		    Date date1=calendar.getTime();  
		    str = sdf.format(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	* @Description: 查询所有机构名称 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月9日 下午3:22:41 
	* @version V1.0
	 */
	public List<String> queryAuthOrg(){
		return this.logDao.queryAuthOrg();
	}
	/**
	* @Description: 查询所有用户名称 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月9日 下午3:22:41 
	* @version V1.0
	 */
	public List<String> queryAuthUser(String authOrg){
		return this.logDao.queryAuthUser(authOrg);
	}
	/**
	* @Description: 查询所有操作内容 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月9日 下午3:22:41 
	* @version V1.0
	 */
	public List<String> queryOpercontent(){
		return this.logDao.queryOpercontent();
	}
}
