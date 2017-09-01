package byit.tableausubscribe.tab.service;

import java.util.List;
import byit.osdp.base.entity.EmailEntity;
import byit.osdp.base.entity.SystemDoMainEntity;
import byit.tableausubscribe.common.util.mail.MailConstants;
import byit.tableausubscribe.tab.dao.EmailDao;

public class EmailService {
	
	//查询数据库中的邮箱配置
	public EmailEntity getEmail(){
		EmailDao emailDao=new EmailDao();
		EmailEntity tEmailEntity=new EmailEntity();
		List<SystemDoMainEntity> list = emailDao.getEmailInfo();
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
