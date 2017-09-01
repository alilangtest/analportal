package byit.osdp.portal.interceptor.entity;

import java.util.Date;

/**
 * 系统访问记录表实体类
 * @author thb
 */
public class SystemLogEntity {
	private String eventid;//主键
	private Date logdate;//操作时间
	private String url;//页面URL
	private String menu;//菜单名称
	private String menuid;//菜单id
	private String userid;//用户id
	private String type;//菜单类别（1一级菜单 2二级菜单 ）
	private String days;//访问菜单的日期
	private String hours;//访问菜单的小时
	private String ipaddress;//用户ip地址
	private String opertype;//操作类型
	private String opercontent;//操作内容
	public SystemLogEntity() {
		super();
	}
	public SystemLogEntity(String eventid, Date logdate, String url, String menu, String menuid, String userid,
			String type, String days, String hours, String ipaddress, String opertype, String opercontent) {
		super();
		this.eventid = eventid;
		this.logdate = logdate;
		this.url = url;
		this.menu = menu;
		this.menuid = menuid;
		this.userid = userid;
		this.type = type;
		this.days = days;
		this.hours = hours;
		this.ipaddress = ipaddress;
		this.opertype = opertype;
		this.opercontent = opercontent;
	}

	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public Date getLogdate() {
		return logdate;
	}
	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getOpertype() {
		return opertype;
	}
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	public String getOpercontent() {
		return opercontent;
	}
	public void setOpercontent(String opercontent) {
		this.opercontent = opercontent;
	}
}
