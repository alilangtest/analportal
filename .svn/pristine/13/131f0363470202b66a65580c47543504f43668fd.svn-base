package byit.osdp.portal.interceptor.dao;

import java.util.List;

import byit.core.plug.mybatis.Mapper;
import byit.osdp.portal.interceptor.entity.SystemLogEntity;
import byit.osdp.portal.interceptor.entity.SystemPathEntity;

@Mapper
public interface InsertSystemLogDao {
	
	//根据pageurl查询数据（system_path）
	public List<SystemPathEntity> queryByPageurl(String pageurl);
	//保存数据到表system_log
	public void insertSystemLog(SystemLogEntity systemLogEntity);

}
