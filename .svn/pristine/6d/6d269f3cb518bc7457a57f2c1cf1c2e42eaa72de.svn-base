package byit.aladdin.TableauConfigure.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import byit.aladdin.TableauConfigure.bo.CollectBo;
import byit.aladdin.TableauConfigure.entity.PmTableauserverEntity;
import byit.core.plug.mybatis.Mapper;

@Mapper
public interface PmTableauserverDao {
	//新增table数据库配置
	public void saveTableauServer(PmTableauserverEntity entity);
	//修改table数据库配置
	public void updateTableauServer(PmTableauserverEntity entity);
	//删除table数据库配置
	public void removeById(String id);
	//根据id查询一条table数据库配置的数据
	public PmTableauserverEntity getById(String serverid);
	//查询table数据库配置列表
	public List<Map<String, Object>> getPagedQuery(Map<String, Object> params, RowBounds bounds);
	//查询收藏列表
	public List<CollectBo> countCollections(String loginid);
	//取消收藏报表
	public boolean collectDelete(CollectBo collectBo);
	public void collectDeleteRetOperation(CollectBo collectBo);
	//报表收藏置顶
	public boolean collectToTop(CollectBo collectBo);
	
}
