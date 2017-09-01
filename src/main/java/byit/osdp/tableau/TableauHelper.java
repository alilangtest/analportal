package byit.osdp.tableau;

import byit.osdp.tableau.util.TableauUtil;

public class TableauHelper {
	public static String getTrustedTicket(String wgserver, String username, String clientIp,String url_namespace) {
		//try {
		//	TableauTrustedService service = SpringEnvironment.getSpringBean("tableauTrustedService");
		//	return service.getTrustedTicket(username, clientIp);
		//} catch (RemoteException e) {
		//	return "";
		//}
		return TableauUtil.getTrustedTicket(TableauConfig.TABLEAU_TRUSTED_HOST, username, clientIp,url_namespace);
	}
}
