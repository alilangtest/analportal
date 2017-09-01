package byit.osdp.tableau.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Tableau授信服务(RMI远程接口)
 */
public interface TableauTrustedService extends Remote {

	/**
	 * 获得用户授信票据
	 * @param username 用户名称
	 * @param clientIp 客户端地址
	 * @return 用户授信票据
	 */
	String getTrustedTicket(String username, String clientIp) throws RemoteException;
}
