package byit.aladdin.workBook.entity;

/**
 * 站点
 */
public class Sites {
	// ==============================Fields========================================
	private Long id;
	private String name;
	private String urlNamespace;
	private String status;
	private String luid;
	// ==============================Methods==========================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlNamespace() {
		return urlNamespace;
	}

	public void setUrlNamespace(String urlNamespace) {
		this.urlNamespace = urlNamespace;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLuid() {
		return luid;
	}

	public void setLuid(String luid) {
		this.luid = luid;
	}
	
	
}
