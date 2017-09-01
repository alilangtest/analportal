package byit.osdp.base.dao;


import java.util.List;

import byit.core.plug.mybatis.Mapper;
import byit.osdp.base.entity.SystemDoMainEntity;

/**
 * 查询字典表dao层
 * @author Mr.tang
 */
@Mapper
public interface SystemDoMainDao {

	/**
	 * 更具doMainId查询字典表数据
	 * @param doMainId
	 * @return
	 */
	List<SystemDoMainEntity> getByDoMainId(String doMainId);
	/**
	 * 更具doMainId查询字典表数据
	 * @param doMainId
	 * @return
	 */
	List<SystemDoMainEntity> getByDoMainIdList(String doMainId);

}
