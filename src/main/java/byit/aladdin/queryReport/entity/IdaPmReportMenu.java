package byit.aladdin.queryReport.entity;

/**
 * 
 * 类名称：IdaPmReportMenu   
 */


public class IdaPmReportMenu implements java.io.Serializable {
	private static final long serialVersionUID = -6547383697947440031L;
	private String menuId;//菜单编号
	private String menuparent;//父类菜单
	private String menuname;//菜单名称
	private String menudesp;//菜单描述
	private String rem;//菜单备注
	private String url;//菜单备注
	private String isused;//是否启用
	private Long ordinal;
	public IdaPmReportMenu() {
	}
	public IdaPmReportMenu(String menuId, String menuparent, String menuname, String menudesp, String rem, String url,
			String isused) {
		super();
		this.menuId = menuId;
		this.menuparent = menuparent;
		this.menuname = menuname;
		this.menudesp = menudesp;
		this.rem = rem;
		this.url = url;
		this.isused = isused;
	}
	
	public Long getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(Long ordinal) {
		this.ordinal = ordinal;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuparent() {
		return menuparent;
	}
	public void setMenuparent(String menuparent) {
		this.menuparent = menuparent;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getMenudesp() {
		return menudesp;
	}
	public void setMenudesp(String menudesp) {
		this.menudesp = menudesp;
	}
	public String getRem() {
		return rem;
	}
	public void setRem(String rem) {
		this.rem = rem;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsused() {
		return isused;
	}
	public void setIsused(String isused) {
		this.isused = isused;
	}
	@Override
	public String toString() {
		return "IdaPmReportMenu [menuId=" + menuId + ", menuparent=" + menuparent + ", menuname=" + menuname
				+ ", menudesp=" + menudesp + ", rem=" + rem + ", url=" + url + ", isused=" + isused + "]";
	}

	
	
}
