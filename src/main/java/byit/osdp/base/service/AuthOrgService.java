package byit.osdp.base.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import byit.aladdin.workBook.entity.AuthOrgInfo;
import byit.core.plug.expection.PromptException;
import byit.core.plug.mybatis.PageBounds;
import byit.core.util.identifier.IdUtil;
import byit.core.util.page.Page;
import byit.core.util.page.Pagination;
import byit.osdp.base.common.BaseConstants;
import byit.osdp.base.dao.AuthOrgsDao;
import byit.osdp.base.entity.AuthOrgEntity;
import byit.osdp.base.model.AuthOrgNodeVo;
import byit.osdp.base.model.AuthOrgVo;
import byit.osdp.base.security.UserHolder;

/**
 * 组织机构(服务类)
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AuthOrgService {
	// ==============================Fields===========================================
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AuthOrgsDao authOrgsDao;

	private static final Lock REFRESH_INDEXES_LOCK = new ReentrantLock();

	// ==============================Methods==========================================
	/**
	 * 分页查询
	 * @param pagination 分页条件
	 * @return 分页结果
	 */
	public Object pagedQuery(Pagination pagination) {
		Map<String, Object> params = Maps.newHashMap();
		String name = pagination.getFilters().getString("name");
		params.put("name", name);
		//获得分页
		PageBounds bounds = new PageBounds(pagination.getStart(),pagination.getLimit());
		Page<Map<String, Object>> page = bounds.wrap(authOrgsDao.pagedQuery(params, bounds));
		return page;
	}
	
	


	/**
	 * 查询机构
	 * @param id 机构ID
	 * @return 机构信息
	 */
	public AuthOrgVo getById(String id) {
//		AuthOrgEntity entity = authOrgDao.getById(id);
		
		AuthOrgEntity entity = authOrgsDao.getById(id);
		if (entity == null) {
			throw new PromptException("机构不存在或者已经失效");
		}

		AuthOrgVo vo = new AuthOrgVo();
		vo.setId(id);
		vo.setName(entity.getName());
		vo.setCode(entity.getCode());
		vo.setParentId(entity.getParentId());
		vo.setPhone(entity.getPhone());
		vo.setFax(entity.getFax());
		vo.setAddress(entity.getAddress());
		vo.setRemark(entity.getRemark());
		vo.setOrdinal(entity.getOrdinal() == null ? 9999 : entity.getOrdinal());

		vo.setLevel(entity.getLevel());
		vo.setIdPath(entity.getIdPath());
		vo.setNamePath(entity.getNamePath());

		String parentId = vo.getParentId();
		if (BaseConstants.ORG_ROOT_ID.equals(parentId)) {
			vo.setParentName("/");
		} else {
			AuthOrgEntity parentEntity = authOrgsDao.getById(parentId);
			vo.setParentName(parentEntity == null ? "[未知机构]" : StringUtils.defaultString(parentEntity.getNamePath(), parentEntity.getName()));
		}
		return vo;
	}

	/**
	 * 新增
	 * @param vo 模型对象
	 */
	public void save(AuthOrgVo vo) {
		validate(vo);
		String uid = UserHolder.getId();
		Date now = new Date();

		String id = IdUtil.uuid32();
		vo.setId(id);

		Indexes indexes = getIndexes(vo);

		AuthOrgEntity entity = new AuthOrgEntity();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setCode(vo.getCode());
		entity.setParentId(vo.getParentId());
		entity.setPhone(vo.getPhone());
		entity.setFax(vo.getFax());
		entity.setAddress(vo.getAddress());
		entity.setState(1);
		entity.setRemark(vo.getRemark());
		entity.setOrdinal(vo.getOrdinal() == null ? 9999 : vo.getOrdinal());
		entity.setCreatorId(uid);
		entity.setCreateTime(now);
		entity.setLevel(indexes.level);
		entity.setIdPath(indexes.idPath);
		entity.setNamePath(indexes.namePath);
//		authOrgDao.save(entity);
		authOrgsDao.saveAuthOrg(entity);
	}

	/**
	 * 修改
	 * @param vo 模型对象
	 */
	public void update(AuthOrgVo vo) {
		validate(vo);

		String uid = UserHolder.getId();
		Date now = new Date();

		AuthOrgEntity entity = authOrgsDao.getById(vo.getId());

		if (entity == null) {
			throw new PromptException("机构不存在或者已经失效");
		}

		String originalIdPath = entity.getIdPath();

		Indexes indexes = getIndexes(vo);

		if (indexes.idPath.substring(0, indexes.idPath.length() - vo.getId().length() - 1).indexOf(vo.getId()) != -1) {
			throw new PromptException("机构的父级机构不应是自身或者自身的子机构");
		}

		entity.setName(vo.getName());
		entity.setCode(vo.getCode());
		entity.setParentId(vo.getParentId());
		entity.setPhone(vo.getPhone());
		entity.setFax(vo.getFax());
		entity.setAddress(vo.getAddress());
		entity.setState(1);
		entity.setRemark(vo.getRemark());
		entity.setOrdinal(vo.getOrdinal() == null ? 9999 : vo.getOrdinal());
		entity.setUpdaterId(uid);
		entity.setUpdateTime(now);
		entity.setLevel(indexes.level);
		entity.setIdPath(indexes.idPath);
		entity.setNamePath(indexes.namePath);
//		authUserDao.update(entity);
		authOrgsDao.updateAuthOrg(entity);

		// IdPath发生更改
		if (!entity.getIdPath().equals(originalIdPath)) {
			updateChildrenIndexes(entity);
		}
	}

	/**
	 * 删除机构
	 * @param id 机构ID
	 */
	public void removeById(String id) {
//		if (authOrgDao.existsChildren(id)) { 
//			throw new PromptException("该机构下存在子机构，不能被直接删除");
//		}
//		if (authUserDao.existsByOrgId(id)) {
//			throw new PromptException("该机构下存在用户，不能被直接删除");
//		}
		
		List<AuthOrgEntity> authOrgList=authOrgsDao.existsChildren(id);
		
		if (!authOrgList.isEmpty()) {
			throw new PromptException("该机构下存在子机构，不能被直接删除");
		} 
		if (!authOrgsDao.existsByOrgId(id).isEmpty()) {
			throw new PromptException("该机构下存在用户，不能被直接删除");
		} 
		authOrgsDao.removeById(id);
	}

	/**
	 * 查询子机构树节点
	 * @param parentId 父机构ID
	 * @return 机构树节列表
	 */
	public List<AuthOrgNodeVo> findChildrenNode(String parentId) {
		List<AuthOrgNodeVo> nodes = Lists.newArrayList();
		for (AuthOrgEntity entity : authOrgsDao.findChildren(parentId)) {
			AuthOrgNodeVo node = buildNode(entity);
			nodes.add(node);
		}
		return nodes;
		
	}

	/**
	 * 获得组织机构树
	 * @return 组织机构树
	 */
	public List<AuthOrgNodeVo> getOrgTree() {
		return getOrgTree(BaseConstants.ORG_ROOT_ID);
	}

	/**
	 * 获得组织机构树
	 * @return parentId 根节点
	 * @return 组织机构树
	 */
	public List<AuthOrgNodeVo> getOrgTree(String parentId) {
//		List<AuthOrgEntity> entities = authOrgDao.find();
		List<AuthOrgEntity> entities = authOrgsDao.find();
		List<AuthOrgNodeVo> nodes = buildChildren(parentId, entities, 0);
		return nodes;
	}

	/**
	 * 强制刷新组织机构树索引(级别和ID路径)
	 */
	public void forceRefreshIndexes() {
		if (REFRESH_INDEXES_LOCK.tryLock()) {
			try {
				List<AuthOrgEntity> entities = authOrgsDao.find();
				recursiveSetIndexes(entities, BaseConstants.ORG_ROOT_ID, "/", "", 0);
			} finally {
				REFRESH_INDEXES_LOCK.unlock();
			}
		}
	}
	
/*	public List<AuthOrgEntity> find() {
		Criteria criteria = createCriteria(AuthOrgEntity.class);
		criteria.add(Restrictions.eq("this.state", 1));
		criteria.addOrder(Order.asc("this.ordinal"));
		criteria.addOrder(Order.asc("this.name"));
		return findByCriteria(criteria);
	}*/
	
	

	// ==============================ToolMethods======================================
	private List<AuthOrgNodeVo> buildChildren(String parentId, List<AuthOrgEntity> entities, int depth) {
		List<AuthOrgNodeVo> children = Lists.newArrayList();
		if (depth > 12) {
			return children;
		}
		parentId = StringUtils.defaultString(parentId);
		for (AuthOrgEntity entity : entities) {
			if (StringUtils.defaultString(entity.getParentId()).equals(parentId)) {
				AuthOrgNodeVo node = buildNode(entity);
				node.setChildren(buildChildren(entity.getId(), entities, depth + 1));
				children.add(node);
			}
		}
		return children;
	}

	private AuthOrgNodeVo buildNode(AuthOrgEntity entity) {
		AuthOrgNodeVo node = new AuthOrgNodeVo();
		node.setId(entity.getId());//主键 
		node.setName(entity.getName());//名称
		node.setCode(entity.getCode());//编码
		node.setParentId(entity.getParentId());//上级部门ID
		node.setPhone(entity.getPhone());//电话
		node.setFax(entity.getFax());//传真
		node.setAddress(entity.getAddress());//所在地址
		node.setRemark(entity.getRemark());//备注
		node.setLevel(entity.getLevel());//层级
		node.setOrdinal(entity.getOrdinal());//排序
		node.setIdPath(entity.getIdPath());//ID路径
		node.setNamePath(entity.getNamePath());//NAME路径
		return node;
	}

	/**
	 * 验证数据
	 * @param vo 机构数据
	 */
	private void validate(AuthOrgVo vo) throws PromptException {
		String name = vo.getName();
		String code = vo.getCode();
		String parentId = vo.getParentId();

		if (StringUtils.isEmpty(parentId)) {
			throw new PromptException("请选择上级部门");
		}

		if (StringUtils.isEmpty(name)) {
			throw new PromptException("机构名称不能为空");
		} else {
			HashMap<String, String> map=new HashMap<String,String>();
			map.put("name", name);
			map.put("parentId", parentId);
			AuthOrgEntity entity = authOrgsDao.getByNameParentId(map);
			if (entity != null && !StringUtils.equals(entity.getId(), vo.getId())) {
				throw new PromptException("已经存在相同名称的机构");
			}
		}
		if (StringUtils.isNotEmpty(code)) {
			if (code.length() > 50) {
				throw new PromptException("部门编号长度不能超过50");
			}
			if (!code.matches("[a-zA-z\\d]+")) {
				throw new PromptException("部门编号应为英文和数字组成，不能有空格和特殊字符");
			}
			AuthOrgEntity entity = authOrgsDao.getByCode(code);
			if (entity != null && !StringUtils.equals(entity.getId(), vo.getId())) {
				throw new PromptException("已经存在相同编码的机构");
			}
		}

		if (!BaseConstants.ORG_ROOT_ID.equals(parentId)) {
			AuthOrgEntity parentEntity = authOrgsDao.getById(parentId);
			if (parentEntity == null) {
				throw new PromptException("没有查询到对应的上级部门，请确认上级部门");
			}
		}
	}

	/** 获得索引 */
	private Indexes getIndexes(AuthOrgVo vo) {
		String id = vo.getId();
		String name = vo.getName();
		String parentId = vo.getParentId();
		if (BaseConstants.ORG_ROOT_ID.equals(parentId)) {
			return new Indexes("/" + id + "/", "/" + name, 1);
		} else {
			List<String> parentIds = Lists.newArrayList();
			List<String> parentNames = Lists.newArrayList();
			for (; StringUtils.isNotEmpty(parentId) && !BaseConstants.ORG_ROOT_ID.equals(parentId);) {
				parentIds.add(parentId);
				AuthOrgEntity parentEntity = authOrgsDao.getById(parentId);
				if (parentEntity == null) {
					break;
				}
				parentId = parentEntity.getParentId();
				parentNames.add(parentEntity.getName());
			}
			StringBuilder idPathBuilder = new StringBuilder();
			StringBuilder namePathBuilder = new StringBuilder();
			idPathBuilder.append("/");
			namePathBuilder.append("/");
			for (int i = parentIds.size() - 1; i > -0; i--) {
				idPathBuilder.append(parentIds.get(i)).append("/");
				namePathBuilder.append(parentNames.get(i)).append("/");
			}
			idPathBuilder.append(id).append("/");
			namePathBuilder.append(name);
			return new Indexes(idPathBuilder.toString(), namePathBuilder.toString(), parentIds.size() + 1);
		}
	}

	/** 更新子节点IP路径 */
	private void updateChildrenIndexes(AuthOrgEntity parentEntity) {
		List<AuthOrgEntity> entities = authOrgsDao.findAllChildren(parentEntity.getId());
		recursiveSetIndexes(entities, parentEntity.getId(), parentEntity.getIdPath(), parentEntity.getNamePath(), parentEntity.getLevel());
		for (AuthOrgEntity entity : entities) {
//			authOrgDao.update(entity);
			authOrgsDao.updateAuthOrg(entity);
		}
	}

	/**
	 * 递归设置 ID_PATH
	 */
	private void recursiveSetIndexes(Collection<AuthOrgEntity> entities, String parentId, String parentIdPath, String parentNamePath,
			int parentLevel) {
		for (AuthOrgEntity entity : entities) {
			if (parentId.equals(entity.getParentId())) {
				String id = entity.getId();
				String name = entity.getName();
				String idPath = parentIdPath + id + "/";
				String namePath = parentNamePath + "/" + name;
				int level = parentLevel + 1;
				entity.setIdPath(idPath);
				entity.setNamePath(namePath);
				entity.setLevel(level);
				recursiveSetIndexes(entities, id, idPath, namePath, level);
			}
		}
	}
	
	

	// ==============================InnerClass=======================================
	private static class Indexes {
		public final String idPath;
		public final String namePath;
		public final Integer level;

		protected Indexes(String idPath, String namePath, Integer level) {
			this.idPath = idPath;
			this.namePath = namePath;
			this.level = level;
		}
	}
	
	/**
	* @Description: 查询所有机构信息 
	* @author wangxingfei   
	* @param @return
	* @date 2017年8月23日 下午2:58:46 
	* @version V1.0
	 */
	public List<AuthOrgInfo> getAuthOrgAll(){
		return authOrgsDao.getAuthOrgAll();
	}
}
