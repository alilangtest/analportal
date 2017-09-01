package byit.osdp.base.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.core.plug.expection.PromptException;
import byit.osdp.base.common.BaseConstants;
import byit.osdp.base.common.model.NodeFilter;
import byit.osdp.base.dao.AuthPermissionsDao;
import byit.osdp.base.entity.AuthPermissionEntity;
import byit.osdp.base.model.AuthPermissionNodeVo;
import byit.osdp.base.model.AuthPermissionVo;
import byit.osdp.base.security.UserHolder;

import com.google.common.collect.Lists;

/**
 * 功能权限(服务类)
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AuthPermissionService {

	// ==============================Fields===========================================
	protected final Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//	private AuthPermissionDao authPermissionDao;
	
	@Autowired
	private AuthPermissionsDao authPermissionDao;

//	@Autowired
//	private AuthRolePermissionDao authRolePermissionDao;

	private static final Lock REFRESH_INDEXES_LOCK = new ReentrantLock();

	// ==============================Methods==========================================
	/**
	 * 获得功能树
	 * @return 功能树
	 */
	public List<AuthPermissionNodeVo> getTree() {
		List<AuthPermissionEntity> entities = authPermissionDao.find();
		return buildTree(BaseConstants.ROOT_ID, DEFAULT_NODE_FILTER, 0, entities);
	}

	/**
	 * 获得符合条件的功能树
	 * @param rootId 根节点
	 * @return 功能树
	 */
	public List<AuthPermissionNodeVo> getTreeByRootId(String rootId) {
//		List<AuthPermissionEntity> entities = authPermissionDao.find();
		List<AuthPermissionEntity> entities = authPermissionDao.find();
		return buildTree(rootId, DEFAULT_NODE_FILTER, 0, entities);
	}

	/**
	 * 获得符合条件的功能树
	 * @param rootId 根节点
	 * @param permissionIds 功能ID
	 * @return 功能树
	 */
	public List<AuthPermissionNodeVo> getTreeByRootId(String rootId, String[] permissionIds) {
//		List<AuthPermissionEntity> entities = authPermissionDao.find();
		List<AuthPermissionEntity> entities = authPermissionDao.find();
		return buildTree(rootId, new PermissionNoneFilter(permissionIds), 0, entities);
	}

	/**
	 * 获得符合条件的菜单树
	 * @param rootId 根节点
	 * @return 菜单树
	 */
	public List<AuthPermissionNodeVo> getTreeMenuByRootId(String rootId) {
//		List<AuthPermissionEntity> entities = authPermissionDao.find();
		List<AuthPermissionEntity> entities = authPermissionDao.find();
		return buildTree(rootId, DEFAULT_MENU_NODE_FILTER, 0, entities);
	}

	/**
	 * 获得符合条件的菜单树
	 * @param rootId 根节点
	 * @param permissionIds 功能ID
	 * @return 菜单树
	 */
	public List<AuthPermissionNodeVo> getTreeMenuByRootId(String rootId, String[] permissionIds) {
//		List<AuthPermissionEntity> entities = authPermissionDao.find();
		List<AuthPermissionEntity> entities = authPermissionDao.find();
		return buildTree(rootId, new PermissionMenuFilter(permissionIds), 0, entities);
	}

	/**
	 * 查询功能权限
	 * @param id 功能ID
	 * @return 功能信息
	 */
	public AuthPermissionVo getById(String id) {
//		AuthPermissionEntity entity = authPermissionDao.getById(id);
		AuthPermissionEntity entity = authPermissionDao.findById(id);
		if (entity == null) {
			throw new PromptException("数据不存在或者已经失效");
		}

		AuthPermissionVo vo = new AuthPermissionVo();
		vo.setId(entity.getId());
		vo.setName(entity.getName());
		vo.setParentId(entity.getParentId());
		vo.setPath(entity.getPath());
		vo.setCode(entity.getCode());
		vo.setType(entity.getType());
		vo.setOrdinal(entity.getOrdinal() == null ? 9999 : entity.getOrdinal());
		vo.setState(entity.getState());
		vo.setRemark(entity.getRemark());
		vo.setIconCls(entity.getIconCls());

		//vo.setLevel(entity.getLevel());
		//vo.setIdPath(entity.getIdPath());

		String parentId = vo.getParentId();
		if (BaseConstants.ROOT_ID.equals(parentId)) {
			vo.setParentName("/");
		} else {
//			AuthPermissionEntity parentEntity = authPermissionDao.getById(parentId);
			AuthPermissionEntity parentEntity = authPermissionDao.findById(parentId);
			vo.setParentName(parentEntity == null ? "[未知]" : parentEntity.getName());
		}
		return vo;
	}

	/**
	 * 根据功能编码获得功能ID
	 * @param code 功能编码
	 * @return 功能ID
	 */
	public String getIdByCode(String code) {
//		return authPermissionDao.getIdByCode(code);
		AuthPermissionEntity authPermissionEntity = authPermissionDao.getByCode(code);
		return authPermissionEntity == null ? "" : authPermissionEntity.getId();
	}

	/**
	 * 新增
	 * @param vo 模型对象
	 */
	public void save(AuthPermissionVo vo) {
		validate(vo);

		String uid = UserHolder.getId();
		Date now = new Date();

		Indexes indexes = getIndexes(vo);

		AuthPermissionEntity entity = new AuthPermissionEntity();

		entity.setId(vo.getCode());
		entity.setName(vo.getName());
		entity.setParentId(vo.getParentId());
		entity.setPath(vo.getPath());
		entity.setCode(vo.getCode());
		entity.setType(vo.getType());
		entity.setOrdinal(vo.getOrdinal() == null ? 9999 : vo.getOrdinal());
		entity.setState(1);
		entity.setRemark(vo.getRemark());
		entity.setIconCls(vo.getIconCls());

		entity.setIdPath(indexes.idPath);
		entity.setLevel(indexes.level);

		entity.setCreatorId(uid);
		entity.setCreateTime(now);

//		authPermissionDao.save(entity);
		authPermissionDao.addAuthRolePermission(entity);
	}

	/**
	 * 修改
	 * @param vo 模型对象
	 */
	public void update(AuthPermissionVo vo) {
		validate(vo);

		String uid = UserHolder.getId();
		Date now = new Date();

//		AuthPermissionEntity entity = authPermissionDao.getById(vo.getId());
		AuthPermissionEntity entity = authPermissionDao.findById(vo.getId());
		if (entity == null) {
			throw new PromptException("数据不存在");
		}

		String originalIdPath = entity.getIdPath();

		Indexes indexes = getIndexes(vo);

		if (indexes.idPath.substring(0, indexes.idPath.length() - vo.getId().length() - 1).indexOf(vo.getId()) != -1) {
			throw new PromptException("功能的父节点不能是自己或者自己的子节点");
		}

		entity.setName(vo.getName());
		entity.setParentId(vo.getParentId());
		entity.setPath(vo.getPath());
		entity.setCode(vo.getCode());
		entity.setType(vo.getType());
		entity.setOrdinal(vo.getOrdinal() == null ? 9999 : vo.getOrdinal());
		entity.setState(1);
		entity.setRemark(vo.getRemark());
		entity.setIconCls(vo.getIconCls());
		entity.setUpdaterId(uid);
		entity.setUpdateTime(now);

		entity.setIdPath(indexes.idPath);
		entity.setLevel(indexes.level);

//		authPermissionDao.update(entity);
		authPermissionDao.updateAuthRolePermission(entity);

		// IdPath发生更改
		if (!entity.getIdPath().equals(originalIdPath)) {
			updateChildrenIndexes(entity);
		}
	}

	/**
	 * 根据角色ID查找关联的权限
	 * @param roleIds 角色ID
	 * @return 关联权限列表
	 */
	public List<AuthPermissionEntity> findEntityByRoleId(List<String> roleIds) {
//		return authPermissionDao.findByRoleId(roleIds);
		List<AuthPermissionEntity> authPermissionEntityList=authPermissionDao.findEntityByRoleId(roleIds);
		return authPermissionEntityList;
	}

	/**
	 * 根据id 删除权限以及关联角色表
	 * @param id 权限ID
	 */
	public void removeById(String id) {
		/*if (authPermissionDao.existsChildren(id)) {
			throw new PromptException("该功能存在子节点，不能被直接删除");
		}
		authPermissionDao.removeById(id);
		authRolePermissionDao.removeByPermissionId(id);
		*/
		if (authPermissionDao.findByParentId(id) != 0) {
			throw new PromptException("该功能存在子节点，不能被直接删除");
		}
		authPermissionDao.deleteAuthPermissionById(id);
		authPermissionDao.deleteAuthRolePermissionByPermissionId(id);
	}
	/**
	 * 更新idPath数据
	 * @param list
	 */
	public void updateAuthRolePermissionByIdPath(List<AuthPermissionEntity> list){
		for (AuthPermissionEntity authPermissionEntity : list) {
			Integer rowIndex;
			try {
				rowIndex = authPermissionDao.updateAuthRolePermissionByIdPath(authPermissionEntity);
				if(rowIndex == null || rowIndex.intValue() < 1){
					throw new PromptException("刷新失败！");
				}
			} catch (Exception e) {
				throw new PromptException("刷新失败！");
			}
		}
	}
	/**
	 * 强制刷新功能树索引(级别和ID路径)
	 */
	public List<AuthPermissionEntity> forceRefreshIndexes() {
		List<AuthPermissionEntity> list = null;
		if (REFRESH_INDEXES_LOCK.tryLock()) {
			try {
//				recursiveSetIndexes(authPermissionDao.find(), BaseConstants.ORG_ROOT_ID, "/", 0);
				list = authPermissionDao.find();
				recursiveSetIndexes(list, BaseConstants.ORG_ROOT_ID, "/", 0);
			} finally {
				REFRESH_INDEXES_LOCK.unlock();
			}
		}
		return list;
	}

	// ==============================ToolMethods======================================
	private List<AuthPermissionNodeVo> buildTree(String parentId, NodeFilter<AuthPermissionEntity> filter, int depth,
			List<AuthPermissionEntity> entities) {
		List<AuthPermissionNodeVo> nodes = Lists.newArrayList();
		for (AuthPermissionEntity entity : entities) {
			if (entity.getParentId().equals(parentId)) {
				List<AuthPermissionNodeVo> children = buildTree(entity.getId(), filter, depth + 1, entities);
				boolean leaf = children.isEmpty();
				if (filter.accept(entity, depth, leaf)) {
					AuthPermissionNodeVo node = new AuthPermissionNodeVo();
					node.setId(entity.getId());
					node.setName(entity.getName());
					node.setParentId(entity.getParentId());
					node.setPath(entity.getPath());
					node.setCode(entity.getCode());
					node.setType(entity.getType());
					node.setOrdinal(entity.getOrdinal());
					node.setRemark(entity.getRemark());
					node.setIconCls(entity.getIconCls());
					node.setChildren(children);
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	private void validate(AuthPermissionVo vo) throws PromptException {
		String name = vo.getName();
		String code = vo.getCode();
		Integer type = vo.getType();
		String parentId = vo.getParentId();

		if (StringUtils.isEmpty(name)) {
			throw new PromptException("名称不能为空");
		}
		if (StringUtils.isEmpty(code)) {
			throw new PromptException("编码不能为空");
		}

		if (type == 0) {
			throw new PromptException("类别不能为空");
		}

		if (!code.matches("[a-zA-Z0-9\\d\\_\\-]+")) {
			throw new PromptException("编码不能有空格和特殊字符");
		} else if (code.length() > 50) {
			throw new PromptException("编号长度不能超过50");
		} else {
//			AuthPermissionEntity entity = authPermissionDao.getByCode(code);
			AuthPermissionEntity entity = authPermissionDao.getByCode(code);
			if (entity != null && !StringUtils.equals(entity.getId(), vo.getId())) {
				throw new PromptException("编码重复");
			}
		}
		if (!BaseConstants.ROOT_ID.equals(parentId)) {
//			AuthPermissionEntity parentEntity = authPermissionDao.getById(parentId);
			AuthPermissionEntity parentEntity = authPermissionDao.findById(parentId);
			if (parentEntity == null) {
				throw new PromptException("没有查询到对应的上级");
			}
		}
	}

	/** 获得索引 */
	private Indexes getIndexes(AuthPermissionVo vo) {
		String id = vo.getId();
		String parentId = vo.getParentId();
		if (BaseConstants.ROOT_ID.equals(parentId)) {
			return new Indexes("/" + id + "/", 1);
		} else {
			List<String> parentIds = Lists.newArrayList();
			for (; StringUtils.isNotEmpty(parentId) && !BaseConstants.ROOT_ID.equals(parentId);) {
				parentIds.add(parentId);
//				AuthPermissionEntity parentEntity = authPermissionDao.getById(parentId);
				AuthPermissionEntity parentEntity = authPermissionDao.findById(parentId);
				if (parentEntity == null) {
					break;
				}
				parentId = parentEntity.getParentId();
			}
			StringBuilder idPathBuilder = new StringBuilder();
			idPathBuilder.append("/");
			for (int i = parentIds.size() - 1; i > -0; i--) {
				idPathBuilder.append(parentIds.get(i)).append("/");
			}
			idPathBuilder.append(id).append("/");
			return new Indexes(idPathBuilder.toString(), parentIds.size() + 1);
		}
	}

	/** 更新子节点IP路径 */
	private void updateChildrenIndexes(AuthPermissionEntity parentEntity) {
//		List<AuthPermissionEntity> entities = authPermissionDao.findAllChildren(parentEntity.getId());
		List<AuthPermissionEntity> entities = authPermissionDao.findListByParentId(parentEntity.getId());
		recursiveSetIndexes(entities, parentEntity.getId(), parentEntity.getIdPath(), parentEntity.getLevel());
		for (AuthPermissionEntity entity : entities) {
//			authPermissionDao.update(entity);
			authPermissionDao.updateAuthRolePermission(entity);
		}
	}

	/**
	 * 递归设置 ID_PATH
	 */
	private void recursiveSetIndexes(Collection<AuthPermissionEntity> entities, String parentId, String parentIdPath, int parentLevel) {
		for (AuthPermissionEntity entity : entities) {
			if (parentId.equals(entity.getParentId())) {
				String id = entity.getId();
				String idPath = parentIdPath + id + "/";
				int level = parentLevel + 1;
				entity.setIdPath(idPath);
				entity.setLevel(level);
				recursiveSetIndexes(entities, id, idPath, level);
			}
		}
	}

	// ==============================InnerClass=======================================

	private static NodeFilter<AuthPermissionEntity> DEFAULT_NODE_FILTER = new NodeFilter<AuthPermissionEntity>() {
		@Override
		public boolean accept(AuthPermissionEntity entity, int depth, boolean leaf) {
			return true;
		}
	};
	private static NodeFilter<AuthPermissionEntity> DEFAULT_MENU_NODE_FILTER = new NodeFilter<AuthPermissionEntity>() {
		@Override
		public boolean accept(AuthPermissionEntity entity, int depth, boolean leaf) {
			Integer type = entity.getType();
			return type != null && type.intValue() < 3;
		}
	};

	private static class PermissionNoneFilter implements NodeFilter<AuthPermissionEntity> {
		private final String[] permissions;

		public PermissionNoneFilter(String[] permissions) {
			this.permissions = permissions;
		}

		@Override
		public boolean accept(AuthPermissionEntity entity, int depth, boolean leaf) {
			if (!leaf) {
				return true;
			}
			String id = entity.getId();
			for (String permission : permissions) {
				if (id.equals(permission)) {
					return true;
				}
			}
			return false;
		}
	}

	private static class PermissionMenuFilter extends PermissionNoneFilter {
		public PermissionMenuFilter(String[] permissions) {
			super(permissions);
		}

		@Override
		public boolean accept(AuthPermissionEntity entity, int depth, boolean leaf) {
			Integer type = entity.getType();
			return type != null && type.intValue() < 3 && super.accept(entity, depth, leaf);
		}
	}

	private static class Indexes {
		public final String idPath;
		public final Integer level;

		protected Indexes(String idPath, Integer level) {
			this.idPath = idPath;
			this.level = level;
		}
	}
}
