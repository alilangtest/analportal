package byit.osdp.tableau.service;

import java.rmi.Naming;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Tableau授信服务
 */
public class TableauTrustedServiceFactoryBean implements FactoryBean<TableauTrustedService>, InitializingBean {

	// ==============================Fields===========================================
	private String rmiUrl;
	private TableauTrustedService instance;

	// ==============================Constructors=====================================
	public TableauTrustedServiceFactoryBean() {
	}

	// ==============================Methods==========================================
	@Override
	public TableauTrustedService getObject() throws Exception {
		return instance;
	}

	@Override
	public Class<?> getObjectType() {
		return instance != null ? instance.getClass() : TableauTrustedService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		instance = (TableauTrustedService) Naming.lookup(rmiUrl);
	}

	// ==============================IOC==============================================
	public void setRmiUrl(String rmiUrl) {
		this.rmiUrl = rmiUrl;
	}
}
