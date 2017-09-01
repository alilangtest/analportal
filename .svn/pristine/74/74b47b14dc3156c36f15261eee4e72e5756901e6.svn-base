package byit.tableausubscribe.common.util.mail;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮箱工具类，封装邮件发送流程
 *
 */
public class SimpleMailSender {
	/**
	 * 正文图片
	 * @param mailInfo
	 * @param tSubject，发件箱前面展示的
	 * @return
	 * @throws MessagingException
	 */
	@SuppressWarnings("unused")
	public static boolean sendHtmlMailImge(MailSenderInfo mailInfo,String tSubject) throws MessagingException {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getMailProp(mailInfo);
		// 如果需要身份认证，则创建一个密码验证器
		//if (mailInfo.isValidate()) {
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		 String nick="";  
	        try {
	            //nick=javax.mail.internet.MimeUtility.encodeText(mailInfo.getSubject());  
	        	nick=javax.mail.internet.MimeUtility.encodeText(tSubject);
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }   
	        mailMessage.setFrom(new InternetAddress(nick+" <"+from+">")); 
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address[] to = InternetAddress.parse(mailInfo.getToAddress(), false);
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipients(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart("related");
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
	
		
		StringBuffer content=new StringBuffer();
		if(mailInfo.getContent()!=null){
			content.append(mailInfo.getContent());
		}
		//添加附件
		Vector<String> attachs = mailInfo.getAttachFileNames();
		//System.out.println("-------------------------attachs:"+attachs);
		String file;
		String fileName;
		if (attachs != null) {
			int num=0;
			for (Enumeration<String> fileList = attachs.elements(); fileList
					.hasMoreElements();) {
				num++;
				content.append("<img src='cid:img-"+num+"'/><br/>");
				file = fileList.nextElement();
				//System.out.println("-------------------------file:"+file);
				fileName = file.substring(file.lastIndexOf("/") + 1);
				MimeBodyPart mBodyPart = new MimeBodyPart();
				//远程资源
				FileDataSource fds = new FileDataSource(file);
				mBodyPart.setDataHandler(new DataHandler(fds));
				mBodyPart.setContentID("img-"+num);
/*				//System.out.println("-------------------------mBodyPart:"+mBodyPart);
				//mBodyPart.setFileName(fileName.substring(fileName.lastIndexOf("_")+1));
				//把图片从内容改为附件 attach:附加 attachFile:附件
				System.out.println("mBodyPart.attachFile(file)的try-catch");
				try {
					System.out.println("进入mBodyPart.attachFile(file)");
					mBodyPart.attachFile(file);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("出mBodyPart.attachFile(file)");
				mBodyPart.setFileName(fileName);*/
				mainPart.addBodyPart(mBodyPart);
				//System.out.println("-------------------------mainPart:"+mainPart);
			}
		}
		// 设置HTML内容
		//html.setContent(content.toString(), "text/html; charset=utf-8");
		html.setContent(content.toString(), "text/html; charset=gb2312");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 存储邮件信息  
		mailMessage.saveChanges();
		// 发送邮件
		Transport.send(mailMessage);
		
		return true;
	}
}