package byit.osdp.base.dao;

import java.util.List;
import java.util.Map;
import byit.core.plug.mybatis.Mapper;
import byit.core.plug.mybatis.PageBounds;
import byit.osdp.base.entity.AuthRoleEntity;
import byit.osdp.base.entity.AuthRolePermissionEntity;
import byit.osdp.base.entity.AuthUserEntity;
import byit.osdp.base.entity.AuthUserRoleEntity;
import byit.osdp.base.model.AuthRoleVo;
import byit.osdp.base.model.RepMenuRoleRelaVo;

/**
 * 角色管理持久层
 */
@Mapper
public interface AuthRoleDao {

	/**
	 * @param params 查询条件
	 * @param bounds 
	 * @return
	 */
	List<AuthRoleVo> pagedQuery(Map<String, Object> params, PageBounds bounds);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	AuthRoleEntity getById(String id);
	/**
	 * 保存
	 * @param entity
	 */
	void save(AuthRoleEntity entity);

	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	AuthRoleEntity getByName(String name);

	/**
	 * 更新数据
	 * @param entity
	 */
	void update(AuthRoleEntity entity);

	/**
	 * 根据id删除数据
	 * @param id
	 */
	void removeById(String id);

	/**
	 * 查询所有角色
	 * @return
	 */
	List<AuthRoleVo> findVoAll(String type);

	/**
	 * 查询角色关联功能
	 * @param roleId
	 * @return
	 */
	List<String> findPermissionIdByRoleId(String roleId);

	List<AuthRolePermissionEntity> findByRoleId(String roleId);

	void removeAll(String id);

	/**
	 * 更新角色功能权限关联表（auth_role_permission）
	 * @param authRolePermissionEntity
	 */
	void updateAuthRolePermissionEntity(AuthRolePermissionEntity authRolePermissionEntity);

	/**
	 * 保存角色功能权限关联表（auth_role_permission）
	 * @param authRolePermissionEntity
	 */
	void saveAuthRolePermissionEntity(AuthRolePermissionEntity authRolePermissionEntity);

	List<String> findRoleIdByUserId(String userId);

	List<AuthUserRoleEntity> findByRoleIds(String id);

	void removeByRoleId(String id);

	void removeByRoleIds(String id);

	List<AuthRolePermissionEntity> findByPermissionIds(String id);

	/**
	 * 查询次角色下有没有用户
	 * @param id  角色id
	 * @return
	 */
	List<AuthUserRoleEntity> getUserRoleByRoleId(String id);

	/**
	 * 查询报表菜单有没有授权此角色
	 * @param id 角色id
	 * @return
	 */
	List<RepMenuRoleRelaVo> getRepMenuRoleByRoleId(String id);

	/**
	 * 查询即时报表菜单有没有授权此角色
	 * @param id 角色id
	 * @return
	 */
	List<RepMenuRoleRelaVo> getRepMenuRoleQueryByRoleId(String id);
	/**
	* @Description: 根据角色id查询用户信息 
	* @author wangxingfei   
	* @param @param roleId
	* @param @return
	* @date 2017年8月23日 下午2:43:22 
	* @version V1.0
	 */
	List<AuthUserEntity> getAuthUserByRoleId(String roleId);

	
}
