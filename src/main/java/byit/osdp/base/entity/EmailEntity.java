package byit.osdp.base.entity;

import java.io.Serializable;

public class EmailEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;//邮箱用户
	private String password;//密码
	private String address;//邮箱地址
	private String host;//主机
	private String port;//端口
	private Boolean validate;//验证
	private String subject;//主题
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public Boolean getValidate() {
		return validate;
	}
	public void setValidate(Boolean validate) {
		this.validate = validate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
