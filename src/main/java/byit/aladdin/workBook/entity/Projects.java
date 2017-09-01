package byit.aladdin.workBook.entity;

/**
 * 项目
 */
public class Projects {
	// ==============================Fields========================================
	private Long id;
	private String name;
	private Long siteId;
	private String siteName;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getLuid() {
		return luid;
	}

	public void setLuid(String luid) {
		this.luid = luid;
	}
	
	
}
