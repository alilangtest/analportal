package byit.osdp.base.model;
/**
* @Description: 角色与用户关联关系vo 
* @author wangxingfei   
* @date 2017年8月23日 下午2:30:06 
* @version V1.0
 */
public class AuthRoleUserVo {
	//用户账户
	private String userName;
	//用户姓名
	private String name;
	//机构名称
	private String orgName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
