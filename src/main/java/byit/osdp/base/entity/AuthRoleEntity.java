package byit.osdp.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色管理
 */
public class AuthRoleEntity implements Serializable {

	// ==============================Fields========================================
	/** 主键 */
	private String id;

	/** 角色名 */
	private String name;

	/** 备注 */
	private String remark;

	/** 记录状态（1有效，0无效） */
	private Integer state = 1;
	/**角色类型*/
	private String type;

	//~审计字段
	private String creatorId;
	private Date createTime;
	private String updaterId;
	private Date updateTime;
	// ==============================RelevanceFields==================================

	// ==============================Methods==========================================
	// ==============================PropertyAccessors================================
	
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

}
