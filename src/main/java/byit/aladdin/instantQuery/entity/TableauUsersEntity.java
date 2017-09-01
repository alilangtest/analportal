package byit.aladdin.instantQuery.entity;

import java.io.Serializable;

/**
 * 判断tableau中是否有此用户实体
 * @author Mr.tang
 */
public class TableauUsersEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
