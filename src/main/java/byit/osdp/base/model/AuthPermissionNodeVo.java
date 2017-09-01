package byit.osdp.base.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class AuthPermissionNodeVo implements Serializable {

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

	/** 状态 */
	private Integer state;
	
	/**
	 * 图标样式
	 */
	private String iconCls;

	/** 备注 */
	private String remark;

	/** 子节点# */
	private List<AuthPermissionNodeVo> children;
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<AuthPermissionNodeVo> getChildren() {
		return children;
	}

	public void setChildren(List<AuthPermissionNodeVo> children) {
		this.children = children;
	}
}
