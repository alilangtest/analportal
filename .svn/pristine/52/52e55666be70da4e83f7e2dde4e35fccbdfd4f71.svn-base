package byit.osdp.portal.interceptor.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.osdp.portal.interceptor.dao.InsertSystemLogDao;
import byit.osdp.portal.interceptor.entity.SystemLogEntity;
import byit.osdp.portal.interceptor.entity.SystemPathEntity;
/*
 * 以前可能导入错包了，导入的是javax.transaction.Transactional，事物管理应该用Spring的jar包
 * modify  by lisw
 */
@Transactional
@Service("insertSystemLogService")
public class InsertSystemLogService {

	// ==============================Fields===========================================
	@Autowired
	private InsertSystemLogDao insertSystemLogDao;
	// ==============================Method===========================================

	// 向system_log表插入数据(要想向system_log表中插入数据需先在system_path表中查出数据)
	public void insertSystemLog(String pageurl, String uid,HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<SystemPathEntity> pathlist = new ArrayList<SystemPathEntity>();
		pathlist = insertSystemLogDao.queryByPageurl(pageurl);
		for (SystemPathEntity systemPathEntity : pathlist) {
			// 系统访问记录表实体类
			SystemLogEntity systemLogEntity = new SystemLogEntity();
			// 当前系统时间
			Date date = new Date();
			// 主键
			systemLogEntity.setEventid(UUID.randomUUID().toString());
			// 操作时间
			systemLogEntity.setLogdate(date);
			// 菜单id
			systemLogEntity.setMenuid(systemPathEntity.getMenuid());
			// 菜单名称
			systemLogEntity.setMenu(systemPathEntity.getMenu());
			// 页面URL
			systemLogEntity.setUrl(systemPathEntity.getPageurl());
			// 用户id
			systemLogEntity.setUserid(uid);
			// 菜单类别（1：一级菜单，2：2级菜单）
			systemLogEntity.setType(systemPathEntity.getMenutype());
			// 访问菜单的日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date1 = sdf.format(date);
			String days = date1.substring(0, 10);
			systemLogEntity.setDays(days);
			// 访问菜单的小时
			String hours = date1.substring(11, 13);
			systemLogEntity.setHours(hours);
			//用户ip地址
			String ipaddress = getIpAddr(request);
			systemLogEntity.setIpaddress(ipaddress);
			//操作类型
			//操作内容
			// 保存数据到表system_log
			this.insertSystemLogDao.insertSystemLog(systemLogEntity);
		}
	}
	//用户ip地址
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
}
