package byit.osdp.base.common.easyui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import byit.core.util.collect.Mapx;
import byit.core.util.collect.Mapxs;
import byit.core.util.convert.ConvertUtil;
import byit.core.util.json.JsonUtil;
import byit.core.util.page.Page;
import byit.core.util.page.PageUtil;
import byit.core.util.page.Pagination;

/**
 * 分页条件获取工具类,用于从页面组建表格中获得查询的条件.<br>
 * 支持EasyUiGrid组件.<br>
 * @author _yyl
 * @version 0.1 2012-10-14
 */
@Repository
//@Component
public class DataGridAdapter {

	// ==============================Fields===========================================
	private static final String PAGE_NO_KEY = "page";
	private static final String PAGE_SIZE_KEY = "rows";
	private static final int DEFAULT_PAGE_SIZE = 15;

	// ==============================Methods==========================================
	/**
	 * 获得查询条件
	 * @param 查询条件
	 */
	public Pagination getPagination() {
		HttpServletRequest request = getRequest();
		Pagination pagination = new Pagination();

		Mapx filters = null;
		String pfilters = request.getParameter("filters");
		if (StringUtils.isNotEmpty(pfilters)) {
			filters = JsonUtil.toMapx(pfilters);
		}
		if (filters == null) {
			filters = Mapxs.newMapx();
			for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				String name = entry.getKey();
				String[] values = entry.getValue();
				if (StringUtils.equals(name, PAGE_NO_KEY) || StringUtils.equals(name, PAGE_SIZE_KEY)) {
					continue;
				}
				if (ArrayUtils.isNotEmpty(values)) {
					filters.put(name, values[0]);
				}
			}
		}

		int page = ConvertUtil.toInteger(request.getParameter(PAGE_NO_KEY), 1);
		int limit = ConvertUtil.toInteger(request.getParameter(PAGE_SIZE_KEY), DEFAULT_PAGE_SIZE);
		int start = PageUtil.getStart(page, limit);
		pagination.setStart(start);
		pagination.setLimit(limit);
		pagination.setFilters(filters);
		return pagination;
	}

	/**
	 * 封装分页结果
	 * @return 封装分页结果
	 */
	public EasyUIGrid wrap(Page<?> page) {
		return new EasyUIGrid(page);
	}

	/**
	 * 获得 HTTP请求对象
	 * @return HTTP请求对象
	 */
	private HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
