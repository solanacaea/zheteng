package org.zheteng.email;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sun.mail.util.MailSSLSocketFactory;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class SendEmail {

	private static final String UTF8 = StandardCharsets.UTF_8.name(); 

	private static final String FROM = "xxx@163.com";
	private static final String FROM_SECRET_KEY = "xxx";
	private static final String TO = "xxx@qq.com";

	public static void main(String[] args) {
		
		Properties p = new Properties();
		p.setProperty("mail.transport.protocol", "smtp");
		p.setProperty("mail.smtp.host", "smtp.163.com");
		p.setProperty("mail.smtp.auth", "true");
		try {
			MailSSLSocketFactory msf = new MailSSLSocketFactory();
			msf.setTrustAllHosts(true);
			p.put("mail.smtp.ssl.enable", "true");
			p.put("mail.smtp.ssl.socketFactory", msf);
		}catch (java.security.GeneralSecurityException e) {
			ExceptionUtils.getStackTrace(e);
		}

		Session session = Session.getInstance(p);
		session.setDebug(true);

		MimeMessage message = createMimeMessage(session, FROM, TO);

		try {
			Transport transport = session.getTransport();
			transport.connect(FROM, FROM_SECRET_KEY);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			ExceptionUtils.getStackTrace(e);
		} finally {
		}
		log.info("Finish sending email.");
	}

	public static MimeMessage createMimeMessage(Session session, String from, String to) {
		
		MimeMessage m = new MimeMessage(session);
		try {
			m.setFrom(new InternetAddress(from, "技术宅爱折腾", UTF8));
			m.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to, "收件人名字", UTF8));
			m.setSubject("【折腾】注册验证码", UTF8);
			m.setSentDate(new Date());
			m.setContent(getContent(to, "注册账号"), "text/html;charset=UTF-8");
			m.saveChanges();
		} catch (UnsupportedEncodingException | MessagingException e) {
			ExceptionUtils.getStackTrace(e);
		}
		return m;
	}
	
	
	public static String getContent(String to, String ops) {
		String user = to.substring(0, to.indexOf("@"));
		int qrcode = RandomUtils.nextInt(1000, 9999);//这个验证码保存起来，用作注册时用来验证邮箱是否匹配。
		return String.format(CONTENT, user, ops, qrcode);
	}
	
	private static final String CONTENT = "<head>" + 
			"    <base target=\"_blank\" />" + 
			"    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>" + 
			"    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>" + 
			"    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>" + 
			"    <style type=\"text/css\">" + 
			"        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}" + 
			"        td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}" + 
			"        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%%}" + 
			"        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}" + 
			"        img{ border:0}" + 
			"        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}" + 
			"        blockquote{margin-right:0px}" + 
			"    </style>" + 
			"</head>" + 
			"<body tabindex=\"0\" role=\"listitem\">" + 
			"<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">" + 
			"    <tbody>" + 
			"    <tr>" + 
			"        <td>" + 
			"            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">" + 
			"                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">" + 
			"                    <tbody><tr><td width=\"210\"></td></tr></tbody>" + 
			"                </table>" + 
			"            </div>" + 
			"            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">" + 
			"                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">" + 
			"                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户 %s：<span style=\"color:#f60;font-size: 16px;\"></span>" + 
			"                    <strong style=\"display:block;margin-bottom:15px;\">" + 
			"                        您正在进行<span style=\"color: red\">%s</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px\">%d</span>，以完成操作。" + 
			"                    </strong>" + 
			"                </div>" + 
			"                <div style=\"margin-bottom:30px;\">" + 
			"                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">" + 
			"                        <p style=\"color:#747474;\">" + 
			"                            若非本人操作，请忽略这封邮件。" + 
			"                        </p>" + 
			"                    </small>" + 
			"                </div>" + 
			"            </div>" + 
			"            <div style=\"width:700px;margin:0 auto;\">" + 
			"                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">" + 
			"                    <p>此为系统邮件，请勿回复<br>" + 
			"                        请保管好您的邮箱，避免账号被他人盗用" + 
			"                    </p>" + 
			"					 <p>此邮件仅作为JavaMail学习使用。</p>" +
			"                    <p>头条-技术宅爱折腾</p>" + 
			"                </div>" + 
			"            </div>" + 
			"        </td>" + 
			"    </tr>" + 
			"    </tbody>" + 
			"</table>" + 
			"</body>";
}
