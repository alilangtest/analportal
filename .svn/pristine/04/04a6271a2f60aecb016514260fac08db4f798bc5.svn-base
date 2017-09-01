package byit.osdp.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byit.aladdin.workBook.entity.AuthOrgInfo;
import byit.core.plug.mybatis.Mapper;
import byit.core.plug.mybatis.PageBounds;
import byit.osdp.base.entity.AuthOrgEntity;
import byit.osdp.base.entity.AuthUserEntity;

@Mapper
public interface AuthOrgsDao {

	void removeById(String id);

	List<AuthOrgEntity> existsChildren(String id);

	List<AuthUserEntity> existsByOrgId(String id);

	Integer saveAuthOrg(AuthOrgEntity entity);

	Integer updateAuthOrg(AuthOrgEntity entity);

	AuthOrgEntity getById(String id);

	List<AuthOrgEntity> find();

	List<AuthOrgEntity> findChildren(String parentId);

	AuthOrgEntity getByCode(String code);

	AuthOrgEntity getByNameParentId(HashMap<String, String> map);

	List<AuthOrgEntity> findAllChildren(String id);

	//分页查询
	List<Map<String, Object>> pagedQuery(Map<String, Object> params, PageBounds bounds);
	/**
	* @Description: 根据用户id查询机构信息 
	* @author wangxingfei   
	* @param @param userId
	* @param @return
	* @date 2017年8月23日 下午2:58:46 
	* @version V1.0
	 */
	List<AuthOrgEntity> getAuthOrgByUserId(String userId);
	/**
	* @Description: 查询所有机构信息 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月23日 下午2:58:46 
	* @version V1.0
	 */
	List<AuthOrgInfo> getAuthOrgAll();
	
}
