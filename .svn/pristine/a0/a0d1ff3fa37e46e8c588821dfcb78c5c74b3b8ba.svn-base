package byit.osdp.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import byit.osdp.base.dao.SystemDoMainDao;
import byit.osdp.base.entity.EmailEntity;
import byit.osdp.base.entity.SystemDoMainEntity;
import byit.tableausubscribe.common.util.mail.MailConstants;
import byit.utils.Constants;

/**
 * 查询字典表数据
 * @author Mr.tang
 */
@Service
public class SystemDoMainService {
	
	@Autowired
	private SystemDoMainDao systemDoMainDao;
	/**
	 * 根据domainid查询字典表数据
	 * @return
	 */
	public List<SystemDoMainEntity> getByDoMainIdList(String doMainId){
		List<SystemDoMainEntity> list = systemDoMainDao.getByDoMainIdList(doMainId);
		return list;
	}
	/**
	 * 根据domainid查询字典表数据
	 * @return
	 */
	public Map<String ,String> getByDoMainId(String doMainId){
		Map<String,String> map = new HashMap<String,String>();
		List<SystemDoMainEntity> list = systemDoMainDao.getByDoMainId(doMainId);
		if(list.size()>0){
			for (SystemDoMainEntity systemDoMainEntity : list) {
				map.put(systemDoMainEntity.getCodeid(),systemDoMainEntity.getCodename());
			}
		}
		return map;
	}
	
	public EmailEntity getEmail(){
		EmailEntity tEmailEntity=new EmailEntity();
		List<SystemDoMainEntity> list = systemDoMainDao.getByDoMainId(Constants.EMAIL);
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				
				if(MailConstants.USERNAME.equals(list.get(i).getCodeid())){
					tEmailEntity.setUsername(list.get(i).getCodename());
				}
				if(MailConstants.PASSWORD.equals(list.get(i).getCodeid())){
					tEmailEntity.setPassword(list.get(i).getCodename());
				}
				if(MailConstants.ADDRESS.equals(list.get(i).getCodeid())){
					tEmailEntity.setAddress(list.get(i).getCodename());
				}
				if(MailConstants.HOST.equals(list.get(i).getCodeid())){
					tEmailEntity.setHost(list.get(i).getCodename());
				}
				if(MailConstants.PORT.equals(list.get(i).getCodeid())){
					tEmailEntity.setPort(list.get(i).getCodename());
				}
				if(MailConstants.VALIDATE.equals(list.get(i).getCodeid())){
					tEmailEntity.setValidate(Boolean.parseBoolean(list.get(i).getCodename()));
				}
				if(MailConstants.SUBJECT.equals(list.get(i).getCodeid())){
					tEmailEntity.setSubject(list.get(i).getCodename());
				}
			}
		}
		return tEmailEntity;
	}
}
