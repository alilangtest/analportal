package byit.osdp.base.entity;

import java.util.Date;
/**
* @Description: 用户分配系统关联关系表 
* @author wangxingfei   
* @date 2017年8月11日 上午10:30:02 
* @version V1.0
 */
public class AuthUserSso {
	//id
	private String id;
	//用户id
	private String userId;
	//角色id
	private String roleId;
	//sso系统id
	private String ssoId;
	//创建人
	private String creatorId;
	//创建时间
	private Date createTime;
	//修改者
	private String updaterId;
	//修改时间
	private Date updateTime;
	//记录状态（1有效，0无效）
	private String state;
	//备注
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
