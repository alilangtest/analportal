package byit.osdp.base.model;

import java.io.Serializable;

import byit.osdp.base.util.AuthRealTypeUtil;


@SuppressWarnings("serial")
public class AuthRoleVo implements Serializable {
	// ==============================Fields========================================
	/** 主键 */
	private String id;

	/** 角色名 */
	private String name;

	/** 备注 */
	private String remark;
	/**角色类型*/
	private String type;
	/**
	 * 角色管理页面展示中文使用
	 */
	private String typeStr;
	// ==============================PropertyAccessors================================
	
	public String getId() {
		return id;
	}

	public String getTypeStr() {
		if(AuthRealTypeUtil.map != null){
			return AuthRealTypeUtil.map.get(type);
		}else{
			return null;
		}
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
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
}
