package byit.aladdin.TableauConfigure.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import byit.aladdin.TableauConfigure.bo.SystemDomainBo;
import byit.aladdin.TableauConfigure.dao.PmTableauuserDao;
import byit.aladdin.TableauConfigure.entity.PmTableauuserEntity;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;

/**
 * table数据库配置权限设置service层
 * @author tanghuabo
 */
/*
 * 以前可能导入错包了，导入的是javax.transaction.Transactional，事物管理应该用Spring的jar包
 * modify  by lisw
 */
@Transactional
@Service(value = "pmTableauuserService")
public class PmTableauuserService {
	// ==============================Fields===========================================
	@Autowired
	private PmTableauuserDao tableauuserdao;
	// ==============================Method===========================================
	
	
	/**
	 * 查询列表及分页
	 * pagequery
	 */
	public Page<Map<String, Object>> getPagedQuery(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		String projectname=pagination.getFilters().get("projectname").toString();
		if(projectname !=null && !projectname.equals("")){
			params.put("projectname", projectname);
		}
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());

		Page<Map<String, Object>> page = bounds.wrap(tableauuserdao.getPagedQuery(params, bounds));
		return page;
	}
	
	/**
	 * 新增或修改table权限限设置
	 * @param tableauServer 前台传入的要保存数据
	 * @return
	 */
	public void saveOrUpdate(PmTableauuserEntity tableauuser) {
		String id = tableauuser.getUserid();
		// true 新增 false 修改  
		boolean isNew = false;//(如果是null或“” 返回true)
		if(id==null || id.equals("")){
			isNew = true;
		}
		PmTableauuserEntity entity = new PmTableauuserEntity();
		if(isNew){
			//新增
			entity.setUserid(UUID.randomUUID().toString());
			entity.setOptype(tableauuser.getOptype());
			entity.setUsername(tableauuser.getUsername());
			entity.setPassword(tableauuser.getPassword());
			entity.setProjectname(tableauuser.getProjectname());
			entity.setTableauip(tableauuser.getTableauip());
			tableauuserdao.saveTableauuser(entity);
		}else{
			//修改
			entity.setUserid(id);
			entity.setOptype(tableauuser.getOptype());
			entity.setUsername(tableauuser.getUsername());
			entity.setPassword(tableauuser.getPassword());
			entity.setProjectname(tableauuser.getProjectname());
			entity.setTableauip(tableauuser.getTableauip());
			tableauuserdao.updateTableauuser(entity);
		}
	}
	/**
	 * 删除table权限设置
	 * @param idArray 传入的数组
	 */
	public void removeAll(String idArray) {
		String[] serverid= idArray.split(",");
		for(int i=0;i<serverid.length;i++){
			tableauuserdao.removeById(serverid[i]);
		}
	}
	/**
	 * 根据id查询一条table数据库配置的数据
	 * @param serverid
	 * @return
	 */
	public PmTableauuserEntity getTableauserverserviceById(String serverid) {
		return tableauuserdao.getById(serverid);
	}

	public List<SystemDomainBo> querySystemDomainByDomainid() {
		// TODO Auto-generated method stub
		return tableauuserdao.querySystemDomainByDomainid();
	}
}
