package ${basePackage}.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ${clazzFullName};
import ${basePackage}.service.I${clazzShortName}Service;

/**
 * @Description ${modelDesc}控制器
 * @author codeGenerator
 * @date ${date?string("yyyy-MM-dd HH:mm:ss")}
 * @version 1.0.0
 */
@Controller
@RequestMapping("${param_model}Controller")
public class ${clazzShortName}Controller {
	private final Logger logger = Logger.getLogger(${clazzShortName}Controller.class);
	private final String EDIT_PATH = "";
	private final String MAIN_VIEW_PATH = "";
	@Autowired
	private I${clazzShortName}Service ${param_model}Service;
	
	/**
	 * 跳转到${modelDesc}编辑界面——新增或者修改${modelDesc}
	 * @return
	 */
	@RequestMapping("edit")
	public ModelAndView edit(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(EDIT_PATH);
		return modelAndView;
	}
	
	/**
	 * 跳转到${modelDesc}展示主页面
	 * @return
	 */
	@RequestMapping("showMainView")
	public ModelAndView showMainView(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(MAIN_VIEW_PATH);
		return modelAndView;
	}
	
	/**
	 * 保存${modelDesc}
	 * @param ${param_model}
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save(${clazzShortName} ${param_model}) {
		Map<String, Object> map = new HashMap<>();
		try {
			if(StringUtils.isNotBlank(${param_model}.getId())){
				this.${param_model}Service.modify(${param_model});
			}else{
				this.${param_model}Service.add(${param_model});
			}
			map.put("msg", "保存成功！");
			map.put("success", true);
		} catch (Exception e) {
			this.logger.error("===${modelDesc}保存错误===", e);
			map.put("msg", "保存失败！");
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 根据${modelDesc}主键获取${modelDesc}
	 * @param ${param_model}
	 * @return
	 */
	@RequestMapping("queryByKey")
	public ModelAndView queryByKey(${clazzShortName} ${param_model}) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			${param_model} = this.${param_model}Service.queryByKey(${param_model}.getId());
			modelAndView.addObject("model", ${param_model});
		} catch (Exception e) {
			this.logger.error("===根据${modelDesc}主键获取${modelDesc}错误===", e);
			modelAndView.addObject("msg", "查询失败！");
			modelAndView.addObject("success", false);
		}
		return modelAndView;
	}
	
	/**
	 * 根据${modelDesc}主键删除${modelDesc}
	 * @param ${param_keys}
	 * @return
	 */
	@RequestMapping("deleteByKeys")
	public ModelAndView deleteByKeys(String ${param_keys}) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			this.${param_model}Service.deleteByKeys(${param_keys});
			modelAndView.addObject("msg", "删除成功！");
			modelAndView.addObject("success", true);
		} catch (Exception e) {
			this.logger.error("===根据${modelDesc}主键删除${modelDesc}错误===", e);
			modelAndView.addObject("msg", "删除失败！");
			modelAndView.addObject("success", false);
		}
		return modelAndView;
	}
	

}