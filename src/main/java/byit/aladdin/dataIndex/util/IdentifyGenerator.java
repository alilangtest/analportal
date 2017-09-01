package byit.aladdin.dataIndex.util;

import java.util.UUID;

/**
 * @Description 主键生成器
 * @author wych
 * @date 2016年3月27日
 * @version 1.0.0
 */
public class IdentifyGenerator {

	/**
	 * 使用UUID生成一个32位的主键
	 * @return
	 */
	public static String generateKeyUUID(){
		String key = UUID.randomUUID().toString();
		key = key.replaceAll("-", "");
		return key;
	}
}
