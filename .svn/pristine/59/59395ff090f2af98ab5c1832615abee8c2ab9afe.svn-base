package byit.aladdin.TableauConfigure.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import byit.aladdin.TableauConfigure.bo.SystemDomainBo;
import byit.aladdin.TableauConfigure.entity.PmTableauuserEntity;
import byit.core.plug.mybatis.Mapper;

@Mapper
public interface PmTableauuserDao {
	//新增table权限限设置
	public void saveTableauuser(PmTableauuserEntity entity);
	//修改table权限限设置
	public void updateTableauuser(PmTableauuserEntity entity);
	//删除table权限设置
	public void removeById(String serverid);
	//根据id查询table权限设置
	public PmTableauuserEntity getById(String serverid);
	//查询table权限列表
	public List<Map<String, Object>> getPagedQuery(Map<String, Object> params, RowBounds bounds);
	//查询操作权限
	public List<SystemDomainBo> querySystemDomainByDomainid();
}
