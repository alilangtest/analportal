package byit.osdp.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import byit.aladdin.workBook.entity.UserOrgInfo;
import byit.core.plug.mybatis.Mapper;
import byit.core.plug.mybatis.PageBounds;
import byit.osdp.base.entity.AuthUserEntity;
import byit.osdp.base.entity.AuthUserRoleEntity;
import byit.osdp.base.entity.AuthUserSso;
import byit.osdp.base.entity.SsoClientEntity;
import byit.osdp.base.model.AuthUserRoleVo;
import byit.osdp.base.model.AuthUserVo;

@Mapper
public interface AuthUserDao {

//	分页查询
	List<Map<String, Object>> pagedQuery(Map<String, Object> params, PageBounds bounds);

//  根据用户名查找用户信息
	AuthUserEntity getByUsername(String username);
	
//  根据id查找用户信息
	AuthUserVo getVoById(String id);
	
//  新增
	void save(AuthUserEntity entity);
	
//  根据id查找用户信息
	AuthUserEntity getById(String id);
	
//  修改
//	void update(AuthUserEntity entity);
	void update(AuthUserVo authUserVo);
	
//  删除用户
	void removeById(String id);
	
//  删除用户角色关联
	void removeByUserId(String id);
	
//	根据用户编码查询用户信息
	AuthUserEntity getByCode(String code);
	
//  根据用户ID查询用户角色关联
	//add by lisw
	List<String> findUserRoleByUserId(String userId);
	
	//根据用户ID查询用户角色关联
	List<AuthUserRoleVo> findByUserId(String userId);
	
//  删除用户角色关联
	void deleteUserRole(@Param("roleId")String roleId, @Param("userId")String userId);
	
//  保存用户角色关联
	void addUserRole(AuthUserRoleEntity entity);	
	
//  修改密码
	void updatePassword(AuthUserVo authUserVo);

//	获得角色关联
//	List<String> findIdByUserId(String userId);
	/**
	* @Description: 根据type查询字典表
	* @author wangxingfei   
	* @param @param type
	* @param @return
	* @date 2017年6月7日 下午2:34:42 
	* @version V1.0
	 */
	String findSystemDomainByType(String type);
	/**
	* @Description: 查询分配系统字典表信息
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月11日 上午10:02:02 
	* @version V1.0
	 */
	List<SsoClientEntity> findSystemAll();
	/**
	* @Description:根据用户id查询已分配的系统信息
	* @author wangxingfei   
	* @param @param userId
	* @param @return
	* @date 2017年8月11日 上午10:12:03 
	* @version V1.0
	 */
	List<String> findUserSystemByUserId(String userId);
	/**
	* @Description: 新增用户系统关联关系 
	* @author wangxingfei   
	* @param @param authUserSso
	* @param @return
	* @date 2017年8月11日 上午11:19:58 
	* @version V1.0
	 */
	int addUserSystem(AuthUserSso authUserSso);
	/**
	* @Description: 删除用户系统关联关系
	* @author wangxingfei   
	* @param @param roleId
	* @param @param userId
	* @param @return
	* @date 2017年8月11日 上午11:20:43 
	* @version V1.0
	 */
	int deleteUserSystem(@Param("userId")String userId);
	/**
	* @Description: 根据用户账户查询用户与相关机构信息
	* @author wangxingfei   
	* @param @param userName
	* @param @return
	* @date 2017年8月21日 下午2:35:03 
	* @version V1.0
	 */
	List<UserOrgInfo> findUserByUserName(@Param("userName") String userName);


}
