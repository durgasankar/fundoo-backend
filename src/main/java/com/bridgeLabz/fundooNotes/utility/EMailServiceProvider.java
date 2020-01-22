package com.bridgeLabz.fundooNotes.utility;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EMailServiceProvider {
	private static final String SENDER_EMAIL_ID = System.getenv("emailId");
	private static final String SENDER_PASSWORD = System.getenv("password");

	public void sendMail(String toEmailId, String subject, String bodyContaint) {

		Properties properties = new Properties();

		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator authentication = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL_ID, SENDER_PASSWORD);
			}
		};

		Session session = Session.getInstance(properties, authentication);
		try {
			Transport.send(mimeMessageConfiguration(session, toEmailId, subject, bodyContaint));
			System.out.println("Email Sent Successfully!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private MimeMessage mimeMessageConfiguration(Session session, String toEmail, String subject, String body) {
		
		MimeMessage mimeMessage = new MimeMessage(session);
		// set message headers
		try {
			mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			mimeMessage.addHeader("format", "flowed");
			mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
			mimeMessage.setFrom(new InternetAddress(System.getenv("emailId"), "Fundoo Note Application"));
			mimeMessage.setReplyTo(InternetAddress.parse(System.getenv("emailId"), false));
			mimeMessage.setSubject(subject, "UTF-8");
			mimeMessage.setText(body, "UTF-8");
			mimeMessage.setSentDate(new Date());
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return mimeMessage;

	}

//	public static void main(String[] args) {
//		EMailServiceProvider.sendMail("durgasankar.raja500@gmail.com", "chk", "ouu.. working fine");
//	}

}
