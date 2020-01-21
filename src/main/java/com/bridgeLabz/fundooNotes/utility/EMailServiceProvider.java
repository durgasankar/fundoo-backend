package com.bridgeLabz.fundooNotes.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.stereotype.Component;

@Component
public class EMailServiceProvider {
	private static final String SENDER_EMAIL_ID = System.getenv("emailId");
	private static final String SENDER_PASSWORD = System.getenv("password");

	public static void sendMail(String toEmailId, String subject, String bodyContaint) {

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

		Util.sendEmail(session, toEmailId, subject, bodyContaint);

		System.out.println("finally sent a mail");

	}

//	public static void main(String[] args) {
//		EMailServiceProvider.sendMail("durgasankar.raja500@gmail.com", "chk", "ouu.. working fine");
//	}

}
