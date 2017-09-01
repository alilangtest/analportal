package byit.osdp.base.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import byit.core.plug.expection.PromptException;
import byit.osdp.base.common.BaseConstants;
import byit.osdp.base.dao.AuthUserDao;
import byit.osdp.base.dao.ShowDao;
import byit.osdp.base.entity.AuthUserEntity;
import byit.osdp.base.entity.ShowEntity;
import byit.osdp.base.model.AuthUserVo;
import byit.osdp.base.model.PasswordUpdateDto;
import byit.osdp.base.security.UserHolder;
import byit.osdp.base.security.password.PasswordEncoder;

/**
 * 获取下拉菜单授权显示
 * @author Mr.tang
 */
@Transactional
@Service("showService")
public class ShowService {
	
	@Autowired
	private ShowDao showDao;
	
	@Autowired
	private AuthUserDao authUserDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//获取下拉菜单授权显示数据
	public List<ShowEntity> queryUserById(String loginid) {
		List<ShowEntity> list = showDao.queryUserById(loginid);
		return list;
	}
	
	//验证是否为初始密码
	public Boolean checkPassword() {
		String defaultPassword = passwordEncoder.encode(BaseConstants.GET_PASSWORD);
		//获取当前登录用户
		String loginid=UserHolder.getId();
		AuthUserEntity authUserEntity = showDao.getById(loginid);
		if(authUserEntity != null){
			String password = authUserEntity.getPassword();
			if(password.equals(defaultPassword)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 修改密码
	 * @param dto 密码对象
	 */
	public void updatePassword(PasswordUpdateDto dto) {
		if (dto == null) {
			return;
		}
		String id = dto.getUserId();
		String newPassword = dto.getNewPassword();

		if (BaseConstants.PASSWORD_PLACEHOLDER.equals(newPassword)) {
			return;
		}

		if (StringUtils.isEmpty(newPassword) || BaseConstants.PASSWORD_PLACEHOLDER.equals(newPassword)) {
			throw new PromptException("新登录密码不能为空！");
		}
		AuthUserVo authUserVo =authUserDao.getVoById(id);
		if (authUserVo == null) {
			throw new PromptException("用户不存在！");
		}

		String newEncodedPassword = passwordEncoder.encode(newPassword);
		authUserVo.setPassword(newEncodedPassword);
		authUserDao.updatePassword(authUserVo);
	}
}
