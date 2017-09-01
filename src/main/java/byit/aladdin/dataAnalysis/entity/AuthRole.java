package byit.aladdin.dataAnalysis.entity;

import java.io.Serializable;

/**
 * 角色表
 * @author Administrator
 *
 */
public class AuthRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//角色id
	private String name;//角色名称
	
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
}
