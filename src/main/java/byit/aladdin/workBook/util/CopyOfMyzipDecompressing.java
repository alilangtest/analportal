package byit.aladdin.workBook.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class CopyOfMyzipDecompressing {
//	private final Logger logger = Logger.getLogger(CopyOfMyzipDecompressing.class);
	public static int unzip(String unzipFile,String toFile){
		 File Fout=null;  
	       try {  
	           ZipInputStream Zin=new ZipInputStream(new FileInputStream(unzipFile));//输入源zip路径  
	           BufferedInputStream Bin=new BufferedInputStream(Zin);  
	           String Parent=toFile; //输出路径（文件夹目录）  
	           ZipEntry entry;  
	           try {  
	               while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
	                   Fout=new File(Parent,entry.getName()); 
	                   if(!Fout.exists()){  
	                       (new File(Fout.getParent())).mkdirs();  
	                   }  
	                   FileOutputStream out=new FileOutputStream(Fout);  
	                   BufferedOutputStream Bout=new BufferedOutputStream(out);  
	                   int b;  
	                   while((b=Bin.read())!=-1){  
	                       Bout.write(b);  
	                   }  
	                   Bout.close();  
	                   out.close(); 
	               }
	               Bin.close();  
	               Zin.close();
	               return 0;
	           } catch (IOException e) {  
	               // TODO Auto-generated catch block  
	               e.printStackTrace();  
	               return -1;
	           }  
	       } catch (FileNotFoundException e) {  
	           // TODO Auto-generated catch block  
	           e.printStackTrace(); 
	           return -1;
	       }  
	}
	
	/**
	  *  复制单个问价到制定目录
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static int copyFile(String oldPath, String newPath) { 
		try { 
		int bytesum = 0; 
		int byteread = 0; 
		File oldfile = new File(oldPath); 
		if (oldfile.exists()) { //文件存在时 
			InputStream inStream = new FileInputStream(oldPath); //读入原文件 
			FileOutputStream fs = new FileOutputStream(newPath); 
			byte[] buffer = new byte[1444]; 
			int length; 
			while ( (byteread = inStream.read(buffer)) != -1) { 
				bytesum += byteread; //字节数 文件大小 
				fs.write(buffer, 0, byteread); 
			} 
			inStream.close(); 
		} 
	    return 0;
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		return -1;
		}
	}
	
	 /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    
    /** 
     * 获取一个文件夹下的所有文件 要求：后缀名为txt (可自己修改) 
     */  
    public static List<String> getFileList(File file,final String suffix) {  
        List<String> result = new ArrayList<String>();  
        if (!file.isDirectory()) {  
            result.add(file.getAbsolutePath());  
        } else {  
            // 内部匿名类，用来过滤文件类型  
            File[] directoryList = file.listFiles(new FileFilter() {  
                public boolean accept(File file) {  
                    if (file.isFile() && file.getName().indexOf(suffix) > -1) {  
                        return true;  
                    } else {  
                        return false;  
                    }  
                }  
            });  
            for (int i = 0; i < directoryList.length; i++) {  
                result.add(directoryList[i].getPath());  
            }  
        }  
        return result;  
    }  
    
    /**
     * 删除zip
     */
    public static void deleteZip(String path,String endName) {
        File file = new File(path);// 里面输入特定目录
        File temp = null;
        File[] filelist = file.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            temp = filelist[i];
            if (temp.getName().endsWith(endName))// 获得文件名，如果后缀为“”，这个你自己写，就删除文件
            {
                temp.delete();// 删除文件}
            }
        }
    }
 
}
