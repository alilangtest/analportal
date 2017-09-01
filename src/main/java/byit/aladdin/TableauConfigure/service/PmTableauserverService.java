package byit.aladdin.TableauConfigure.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import byit.aladdin.TableauConfigure.bo.CollectBo;
import byit.aladdin.TableauConfigure.dao.PmTableauserverDao;
import byit.aladdin.TableauConfigure.entity.PmTableauserverEntity;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;

/**
 * table数据库配置service层
 * @author tanghuabo
 */
/*
 * 以前可能导入错包了，导入的是javax.transaction.Transactional，事物管理应该用Spring的jar包
 * modify  by lisw
 */
@Transactional
@Service("pmTableauserverService")
public class PmTableauserverService {
	// ==============================Fields===========================================
	@Autowired
	private PmTableauserverDao tableauserverdao;
	// ==============================Method===========================================
	/**
	 * 查询table数据库配置列表
	 * @param entity
	 * @return
	 */
	public Page<Map<String, Object>> getPagedQuery(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		String postgreuname=pagination.getFilters().get("postgreuname").toString();
		if(postgreuname !=null && !postgreuname.equals("")){
			params.put("postgreuname", postgreuname);
		}
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());

		Page<Map<String, Object>> page = bounds.wrap(tableauserverdao.getPagedQuery(params, bounds));
		return page;
	}
	/**
	 * 新增或修改table数据库配置
	 * @param tableauServer 前台传入的要保存数据
	 * @return
	 */
	public void saveOrUpdate(PmTableauserverEntity tableauServer) {
		String id = tableauServer.getServerid();
		// true 新增 false 修改  
		boolean isNew = false;//(如果是null或“” 返回true)
		if(id==null || id.equals("")){
			isNew = true;
		}
		PmTableauserverEntity entity = new PmTableauserverEntity();
		if(isNew){
			//新增
			entity.setServerid(UUID.randomUUID().toString());
			entity.setTableauserverip(tableauServer.getTableauserverip());
			entity.setPostgreurl(tableauServer.getPostgreurl());
			entity.setPostgreuname(tableauServer.getPostgreuname());
			entity.setPostgrepwd(tableauServer.getPostgrepwd());
			tableauserverdao.saveTableauServer(entity);
		}else{
			//修改
			entity.setServerid(id);
//			entity.setServerid(UUID.randomUUID().toString());
			entity.setTableauserverip(tableauServer.getTableauserverip());
			entity.setPostgreurl(tableauServer.getPostgreurl());
			entity.setPostgreuname(tableauServer.getPostgreuname());
			entity.setPostgrepwd(tableauServer.getPostgrepwd());
			tableauserverdao.updateTableauServer(entity);
		}
	}
	/**
	 * 删除table数据库配置
	 * @param idArray 传入的数组
	 */
	public void removeAll(String idArray) {
		String[] serverid= idArray.split(",");
		for(int i=0;i<serverid.length;i++){
			tableauserverdao.removeById(serverid[i]);
		}
	}
	
	/**
	 * 根据id查询一条table数据库配置的数据
	 * @param serverid
	 * @return
	 */
	public PmTableauserverEntity getTableauserverserviceById(String serverid) {
		return tableauserverdao.getById(serverid);
	}
	//收藏列表
	public List<CollectBo> countCollections(String loginid) {
		// TODO Auto-generated method stub
		return tableauserverdao.countCollections(loginid);
	}
	//取消收藏
	public boolean collectDelete(CollectBo collectBo) {
		// TODO Auto-generated method stub
		//删除查看、分享、点赞表的数据（REP_OPERATION）
		tableauserverdao.collectDeleteRetOperation(collectBo);
		//报表收藏表（PM_REPORTCOLLECT）
		return tableauserverdao.collectDelete(collectBo);
	}
	//报表收藏置顶
	public boolean collectToTop(CollectBo collectBo) {
		// TODO Auto-generated method stub
		return tableauserverdao.collectToTop(collectBo);
	}
}
