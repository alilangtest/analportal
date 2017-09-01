package byit.osdp.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import byit.core.plug.mybatis.Mapper;
import byit.osdp.base.entity.AuthPermissionEntity;

/**
 * 权限功能持久层
 * @author wangxingfei
 *
 * @date 2017年4月28日 下午1:53:27
 */
@Mapper
public interface AuthPermissionsDao {
	/**
	 * 查询全部功能
	 * @author wangxingfei
	 * @param @return    设定文件 
	 * @return List<AuthPermissionEntity>    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午1:56:28
	 */
	List<AuthPermissionEntity> find();
	/**
	 * 根据父节点获取子节点个数
	 * @author wangxingfei
	 * @param @param parentId
	 * @param @return    设定文件 
	 * @return List<AuthPermissionEntity>    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:29:09
	 */
	List<AuthPermissionEntity> findListByParentId(String parentId);
	/**
	 * 根据父节点获取子节点个数
	 * @author wangxingfei
	 * @param @param parentId
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:29:09
	 */
	Integer findByParentId(String parentId);
	/**
	 * 根据ID删除数据
	 * @author wangxingfei
	 * @param @param id
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:37:50
	 */
	Integer deleteAuthPermissionById(String id);
	/**
	 * 根据权限ID删除角色关联关系
	 * @author wangxingfei
	 * @param @param permissionId
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:47:37
	 */
	Integer deleteAuthRolePermissionByPermissionId(String permissionId);
	/**
	 * 根据功能编码查询
	 * @author wangxingfei
	 * @param @param code
	 * @param @return    设定文件 
	 * @return AuthPermissionEntity    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:52:01
	 */
	AuthPermissionEntity getByCode(String code);
	/**
	 * 根据功能编码查询ID
	 * @author wangxingfei
	 * @param @param code
	 * @param @return    设定文件 
	 * @return AuthPermissionEntity    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:52:01
	 */
	AuthPermissionEntity getIdByCode(String code);
	/**
	 * 根据功能编码查询
	 * @author wangxingfei
	 * @param @param id
	 * @param @return    设定文件 
	 * @return AuthPermissionEntity    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午2:52:01
	 */
	AuthPermissionEntity findById(String id);
	/**
	 * 新增数据
	 * @author wangxingfei
	 * @param @param authPermissionEntity
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午3:02:11
	 */
	Integer addAuthRolePermission(AuthPermissionEntity authPermissionEntity);
	/**
	 * 更新数据
	 * @author wangxingfei
	 * @param @param authPermissionEntity
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午3:12:06
	 */
	Integer updateAuthRolePermission(AuthPermissionEntity authPermissionEntity);
	/**
	 * 更新数据
	 * @author wangxingfei
	 * @param @param authPermissionEntity
	 * @param @return    设定文件 
	 * @return Integer    返回类型 
	 * @throws 
	 * @date 2017年5月09日 下午3:12:06
	 */
	Integer updateAuthRolePermissionByIdPath(AuthPermissionEntity authPermissionEntity);
	/**
	 * 根据角色ID查找关联的权限
	 * @author wangxingfei
	 * @param @param roleIds
	 * @param @return    设定文件 
	 * @return List<AuthPermissionEntity>    返回类型 
	 * @throws 
	 * @date 2017年4月28日 下午4:10:09
	 */
	List<AuthPermissionEntity> findEntityByRoleId(@Param("roleIds") List<String> roleIds);
}
