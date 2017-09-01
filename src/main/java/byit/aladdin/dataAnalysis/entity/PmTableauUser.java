package byit.aladdin.dataAnalysis.entity;

import java.io.Serializable;

public class PmTableauUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String optype;//操作类型  P 1-查看 2-下载 3-编辑 4-发布
	private String username;//tableau用户名
	private String password;//tableau密码
	private String projectname;//项目名称   P从postgre数据库同步过来 默认：idata
	private String tableauip;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
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
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getTableauip() {
		return tableauip;
	}
	public void setTableauip(String tableauip) {
		this.tableauip = tableauip;
	}
}
