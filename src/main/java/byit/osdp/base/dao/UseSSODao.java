package byit.osdp.base.dao;

import java.util.HashMap;

import byit.core.plug.mybatis.Mapper;
@Mapper
public interface UseSSODao {

	String findSystemDomain(HashMap<String, String> map);

}
