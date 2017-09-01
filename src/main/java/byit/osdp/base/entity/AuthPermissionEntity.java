package byit.osdp.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * 功能权限管理
 */

public class AuthPermissionEntity implements Serializable {

	// ==============================Fields========================================
	/** 主键 */
	private String id;

	/** 名称 */
	private String name;

	/** 上级功能菜单ID */
	private String parentId;

	/** 访问路径 */
	private String path;

	/** 权限编码 */
	private String code;

	/** 类别 */
	private Integer type;

	/** 排序 */
	private Long ordinal;

	private Integer state;

	/** 备注 */
	private String remark;

	/** 图标样式 */
	private String iconCls;

	/** ID路径 */
	private String idPath;

	/** 层级 */
	private Integer level;

	/** 创建者ID */
	private String creatorId;

	/** 创建时间 */
	private Date createTime;

	/** 修改者ID */
	private String updaterId;

	/** 修改时间 */
	private Date updateTime;

	// ==============================RelevanceFields==================================
	// ==============================PropertyAccessors================================

	public String getId() {
		return id;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Long ordinal) {
		this.ordinal = ordinal;
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
