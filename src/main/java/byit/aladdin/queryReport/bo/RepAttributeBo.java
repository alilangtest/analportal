package byit.aladdin.queryReport.bo;

import java.util.Date;

public class RepAttributeBo {
	private String id;
	private String name;
	private String creator;
	private Date create_date;
	
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	public RepAttributeBo(String id, String name, String creator, Date create_date) {
		super();
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.create_date = create_date;
	}
	public RepAttributeBo() {
		super();
	}
	
}
