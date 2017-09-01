package byit.osdp.portal.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理主页
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminIndexController {

	// ==============================Fields===========================================
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// ==============================Methods==========================================

	// ~/admin/index.html
	@RequestMapping(value = "/index.html")
	public String index(ModelMap mode) {
		return "admin/index_upAdown";
	}

	@RequestMapping(value = "/index2.html")
	public String index2(ModelMap mode) {
		return "admin/admin_index";
	}
	
	
	@RequestMapping(value = "/index3.html")
	public String index3(ModelMap mode) {
		return "admin/index_upAdown2";
	}
}
