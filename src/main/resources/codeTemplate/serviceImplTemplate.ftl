package ${basePackage}.service.impl;


import java.util.List;

<#--因为基于spring所以固定-->
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ${clazzFullName};
import ${basePackage}.service.I${clazzShortName}Service;

@Service("${param_model}Service")
@Transactional(rollbackFor=Exception.class,isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
public class ${clazzShortName}ServiceImpl implements I${clazzShortName}Service {

	@Override
	public void add(${clazzShortName} ${param_model}) throws ${exceptionName} {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(${clazzShortName} ${param_model}) throws ${exceptionName} {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByKeys(String ${param_keys}) throws ${exceptionName} {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly=true)
	public ${clazzShortName} queryByKey(String ${param_key}) throws ${exceptionName} {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<${clazzShortName}> queryAll() throws ${exceptionName} {
		// TODO Auto-generated method stub
		return null;
	}
}