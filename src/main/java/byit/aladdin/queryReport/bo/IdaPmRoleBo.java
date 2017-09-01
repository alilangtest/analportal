package byit.aladdin.queryReport.bo;


/**
 * 角色
 */
 
  
public class IdaPmRoleBo {

	private String id;
	private String name;
	public IdaPmRoleBo() {
		super();
	}
	public IdaPmRoleBo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
