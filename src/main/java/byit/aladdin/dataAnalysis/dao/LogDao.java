package byit.aladdin.dataAnalysis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import byit.core.plug.mybatis.Mapper;

@Mapper
public interface LogDao {
	/**
	 * 根据分页参数查询列表
	 * @param params
	 * @param bounds
	 * @return
	 */
	public List<Map<String, Object>> queryLogListforPage(Map<String, Object> params, RowBounds bounds);
	/**
	* @Description: 查询所有机构名称 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月9日 下午3:22:41 
	* @version V1.0
	 */
	List<String> queryAuthOrg();
	/**
	* @Description: 查询所有用户名称 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月9日 下午3:22:41 
	* @version V1.0
	 */
	List<String> queryAuthUser(@Param("authOrg") String authOrg);
	/**
	* @Description: 查询所有操作内容 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月9日 下午3:22:41 
	* @version V1.0
	 */
	List<String> queryOpercontent();
}
