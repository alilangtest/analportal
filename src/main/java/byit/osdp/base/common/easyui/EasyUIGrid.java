package byit.osdp.base.common.easyui;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import byit.core.util.page.Page;

/**
 * 分页表格数据模型<br>
 */
@SuppressWarnings("serial")
public class EasyUIGrid implements Serializable {
	// =================================Fields================================================
	/** 当前页中存放的记录 */

	private List<?> rows;
	/** 总记录数 */
	private long total;

	// =================================Constructors===========================================
	/**
	 * 构造函数
	 * @param page 分页对象
	 */
	public EasyUIGrid(Page<?> page) {
		this.rows = page.getRecords();
		this.total = page.getTotal();
	}

	/**
	 * 默认构造函数
	 */
	public EasyUIGrid() {
		this.rows = Lists.newArrayList();
		this.total = 0;
	}

	// =================================Methods================================================
	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
