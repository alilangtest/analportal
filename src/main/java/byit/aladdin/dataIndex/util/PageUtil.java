package byit.aladdin.dataIndex.util;
/**
 * 分页帮助类
 * @author wangxingfei
 *
 */
public class PageUtil {
	/**
	 * 每页大小
	 */
	private Integer pageSize = 5;
	/**
	 * 总页数
	 */
	private Integer totlePage;
	/**
	 * 总条数
	 */
	private Integer totleNumber;
	/**
	 * 当前页数
	 */
	private Integer currentPageNumber;
	/**
	 * 开始条数
	 */
	private Integer stratNumber;
	/**
	 * 结束条数
	 */
	private Integer endNumber;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotlePage() {
		totlePage = totleNumber%pageSize == 0 ? totleNumber / pageSize : (totleNumber/pageSize) + 1;
		return totlePage;
	}
	public void setTotlePage(Integer totlePage) {
		this.totlePage = totlePage;
	}
	public Integer getTotleNumber() {
		return totleNumber;
	}
	public void setTotleNumber(Integer totleNumber) {
		this.totleNumber = totleNumber;
	}
	public Integer getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(Integer currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public Integer getStratNumber() {
		stratNumber = (this.currentPageNumber-1)*this.pageSize;
		return stratNumber;
	}
	public void setStratNumber(Integer stratNumber) {
		this.stratNumber = stratNumber;
	}
	public Integer getEndNumber() {
		endNumber = this.currentPageNumber*this.pageSize;
		return endNumber;
	}
	public void setEndNumber(Integer endNumber) {
		this.endNumber = endNumber;
	}
}
