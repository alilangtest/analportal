package byit.aladdin.dataAnalysis.entity;

import java.io.Serializable;

public class Org implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//机构id
	private String name;//机构名称
	private String code;//组织机构代码
	private String parentId; //上级机构Id
	private String managerId;//机构领导Id，预留
	private String state;//记录状态（1有效，0无效）
	private String lev;//机构层级
	
	
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
