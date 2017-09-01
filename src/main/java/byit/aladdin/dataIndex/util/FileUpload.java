package byit.aladdin.dataIndex.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;


/***
 * 
 * 类/接口描述:文件上传工具类
 * 
 * @author 阮海东
 * @version 1.0 创建时间： 2017年1月4日 下午2:27:25 JDK版本：sun jdk 1.7
 */
public class FileUpload {
	public static String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String url = null;// 上传附件保存路径
		//获取文件保存路径
		String path = ByitConfig.FILE_UPLOAD_PATH;
		File saveDirFile = new File(path);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));//截取文件后缀
		String storeName = IdentifyGenerator.generateKeyUUID() + suffix;//文件在服务器上的存储名称
		File targetFile = new File(path, storeName);
		// 保存
		try {
			file.transferTo(targetFile);
			url = path + File.separator +storeName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		file.delete();
		flag = true;
		return flag;
	}
}
