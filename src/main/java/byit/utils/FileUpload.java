package byit.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
/**
 * 文件上传
 * @author: xuzhongqiang
 * @since: 2015年9月24日 上午11:14:11
 * @描述:
 */
public class FileUpload {
	private static final Logger logger = Logger.getLogger(FileUpload.class);
	/**
	 * 使用springMVC解析器进行活动图片上传(可以多文件上传)
	 * @param request
	 * @param dirPath
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String uploadImage(HttpServletRequest request, String dirPath) throws IllegalStateException,
			IOException {
		//获得项目全路径
		String dirFullPath = request.getSession().getServletContext().getRealPath("/");
		dirFullPath = dirFullPath +  "//images//" + dirPath;
		File targetDir = new File(dirFullPath);
		if (!targetDir.exists()) {// 判断目录是否存在
			targetDir.mkdirs();
		}
		
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String path = "";
		String fileName = "";
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						logger.debug(myFileName);
						// 重命名上传后的文件名
						fileName = dirPath + "_" + file.getOriginalFilename();
						// 定义上传路径
						path = dirFullPath + "//" + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}else{
					return null;
				}
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				logger.debug(finaltime - pre);
			}
			return "//images//" + dirPath + "//" + fileName;
		}else{
			return null;
		}
	}
	
	/**
	 * 图片上传
	 * @param request
	 * @param response
	 * @param path 文件保存路径
	 * @return
	 */
	public static String uploadPic(HttpServletRequest request, HttpServletResponse response,
			String path) {
//		File targetDir = new File(path);
		File targetDir = new File(request.getSession().getServletContext().getRealPath("/") + "/pic/");
		if (!targetDir.exists()) {// 判断目录是否存在
			targetDir.mkdirs();
		}
		
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		File temp = new File(request.getSession().getServletContext().getRealPath("/") + "/temp");
		if (!temp.exists()) {// 判断目录是否存在
			temp.mkdirs();
		}
		factory.setRepository(temp);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			// 请自行组织代码
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);
			// 获取文件名
			String filename = getUploadFileName(item);
			String type = filename.substring(filename.lastIndexOf(".")+1, filename.length()).toLowerCase();
			
			PrintWriter writer = response.getWriter();
			
			if(!"gif".equals(type) && !"jpg".equals(type) && !"jpeg".equals(type) && !"png".equals(type) && !"bmp".equals(type)){
				writer.print("{");
				writer.print("error:\"请上传格式为 jpg|png|bmp|jpeg|gif 的报表图片！\"");
				writer.print("}");

			}else{
				
				// 保存后的文件名
				String saveName = new Date().getTime() + "_" + filename;
				// 保存后图片的浏览器访问路径
//				String picUrl = request.getScheme() + "://"
//						+ request.getServerName() + ":" + request.getServerPort()
//						+ request.getContextPath() + "/upload/" + saveName;
//				System.out.println(request.getSession().getServletContext().getRealPath("/"));
				// 真正写到磁盘上
				item.write(new File(request.getSession().getServletContext().getRealPath("/") + "/pic/", saveName)); // 第三方提供的
	
				writer.print("{");
				writer.print("msg:\"文件大小:" + item.getSize() + ",文件名:" + filename
						+ "\"");
//				writer.print(",imgName:\"" + item.getName() + "\"" + ",savePath:\"/" + path.substring(path.indexOf("/")+1, path.length()) + "/" + saveName + "\"");
				writer.print(",imgName:\"" + item.getName() + "\"" + ",savePath:\"//pic/" + saveName + "\"");
				writer.print("}");

			}
			writer.close();

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 图片上传
	 * @param request
	 * @param response
	 * @param path 文件保存路径
	 * @return
	 */
	public static void uploadDoc(HttpServletRequest request, HttpServletResponse response,
			String path) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		File targetDir = new File(path);
//		File targetDir = new File(request.getSession().getServletContext().getRealPath("/") + "/doc/");
		if (!targetDir.exists()) {// 判断目录是否存在
			targetDir.mkdirs();
		}
		
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		File temp = new File(request.getSession().getServletContext().getRealPath("/") + "/temp");
		if (!temp.exists()) {// 判断目录是否存在
			temp.mkdirs();
		}
		factory.setRepository(temp);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
	
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			// 请自行组织代码
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);
			// 获取文件名
			String filename = getUploadFileName(item);
			//String type = filename.substring(filename.lastIndexOf(".")+1, filename.length()).toLowerCase();
			PrintWriter writer = response.getWriter();
			
			// 保存后的文件名
			String saveName = new Date().getTime() + "_" + filename;
			// 真正写到磁盘上
			item.write(new File(targetDir + "/", saveName)); // 第三方提供的
//			item.write(new File(request.getSession().getServletContext().getRealPath("/") + "/doc/", saveName)); // 第三方提供的

			writer.print("{");
			writer.print("msg:\"文件大小:" + item.getSize() + ",文件名:" + filename
					+ "\"");
			writer.print(",docName:\"" + item.getName() + "\"" + ",doc_Url:\"" + path + "/" + saveName + "\"");
//			writer.print(",docName:\"" + item.getName() + "\"" + ",doc_Url:\"//doc/" + saveName + "\"");
			writer.print("}");

			writer.close();

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}

	private static String getUploadFileName(FileItem item) {
		// 获取路径名
		String value = item.getName();
		// 索引到最后一个反斜杠
		int start = value.lastIndexOf("\\");
		// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
		String filename = value.substring(start + 1);

		return filename;
	}

}
