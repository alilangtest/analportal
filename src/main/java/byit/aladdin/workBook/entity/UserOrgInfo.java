package byit.aladdin.workBook.entity;
/**
* @Description: 查询用户与相关机构信息临时存储对象 
* @author wangxingfei   
* @date 2017年8月21日 下午2:31:26 
* @version V1.0
 */
public class UserOrgInfo {
	//用户id
	private String userId;
	//用户名称
	private String userName;
	//是否启用
	private String enabled;
	//有效期
	private String validity;
	//机构编码
	private String code;
	//机构名称
	private String name;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
