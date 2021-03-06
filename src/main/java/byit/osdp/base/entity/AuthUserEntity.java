package byit.osdp.base.entity;

import java.util.Date;


/**
 * 系统用户
 */

public class AuthUserEntity implements java.io.Serializable {

	/** 主键 */
	private String id;

	/** 登录名 */
	private String username;

	/** 登录密码 */
	private String password;

	/** 用户姓名 */
	private String name;

	/** 用户编码 */
	private String code;

	/** 所属机构ID */
	private String orgId;

	/** 性别(男|女) */
	private String sex;

	/** 出生日期 */
	private Date birthday;

	/** 身份证号 */
	private String idCard;

	/** 电话 */
	private String phone;
	/** 手机 */
	private String mobile;
	/** 邮箱 */
	private String email;

	/** 是否可用 */
	private String enabled;

	/** 记录状态（1有效，0无效） */
	private Integer state = 1;
	/** 备注 */
	private String remark;
	/**有效期*/
	private Date validity;
	/**用户区分*/
	private Integer userDistinguish;

	private String creatorId;
	private Date createTime;
	private String updaterId;
	private Date updateTime;
	
	public Integer getUserDistinguish() {
		return userDistinguish;
	}
	public void setUserDistinguish(Integer userDistinguish) {
		this.userDistinguish = userDistinguish;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
	}
	private String sessionId;//用户的sessionid
	private String clientIp;//客户端ip地址
	private String ticket;//用户单点登录票据
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
