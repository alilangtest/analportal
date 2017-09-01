package byit.aladdin.workBook.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询条件参数(接口) 包含分页查询的页数，每页最大记录，查询条件，排序条件等信息
 * @author YYL
 * @version 1.0 2012-10-08
 */
@SuppressWarnings("serial")
public class Pagination implements Serializable {

	// =================================Constants=============================================
	public static final int DEFAULT_LIMIT = 20;

	// =================================Fields=================================================
	/** 开始查询 的数据索引号 (从0开始) */
	private int start;

	/** 每页条数 */
	private int limit;

	/** 查询参数 */
	private Map<String, String> filters;

	// =================================Constructors===========================================
	/** 构造函数 */
	public Pagination() {
		this(0, DEFAULT_LIMIT);
	}

	/** 构造函数 */
	public Pagination(int start, int limit) {
		this(start, limit, new HashMap<String, String>());
	}

	/** 构造函数 */
	public Pagination(int start, int limit, Map<String, String> filters) {
		this.start = start;
		this.limit = limit;
		this.filters = filters;
	}

	// =================================Methods================================================

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	// =================================HashCode_Equals========================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + limit;
		result = prime * result + start;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Pagination other = (Pagination) o;
		return (filters == other.filters || (filters != null && filters.equals(other.filters))) //
				&& limit == other.limit //
				&& start == other.start;
	}
}
