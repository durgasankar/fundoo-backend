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

/**
 * This is the configuration mail class which uses SMTP authentication and runs
 * the service in the port 587 and gives the functionality of sending the mail
 * to user.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 */
@Component
public class EMailServiceProvider {

	private static final String SENDER_EMAIL_ID = System.getenv("email");
	private static final String SENDER_PASSWORD = System.getenv("password");

	/**
	 * This function takes following input parameter and configure the
	 * authentication of SMTP and port 587 and authorization and send the mail to
	 * the assigned user details.
	 * 
	 * @param toEmailId
	 * @param subject
	 * @param bodyContaint
	 */
	public void sendMail(String toEmailId, String subject, String bodyContaint) {
		Authenticator authentication = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL_ID, SENDER_PASSWORD);
			}
		};
		Session session = Session.getInstance(mailPropertiesSettings(), authentication);
		try {
			Transport.send(mimeMessageConfiguration(session, toEmailId, subject, bodyContaint));
		} catch (MessagingException e) {
			e.printStackTrace();

		}

	}

	/**
	 * This function takes following information and sets all the header information
	 * of the email.
	 * 
	 * @param session as Current session information
	 * @param toEmail as String receiver's mail id
	 * @param subject as String input parameter
	 * @param body    as String input parameter
	 * @return MimeMessage class
	 */
	private MimeMessage mimeMessageConfiguration(Session session, String toEmail, String subject, String body) {

		MimeMessage mimeMessage = new MimeMessage(session);
		// set message headers
		try {
			mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			mimeMessage.addHeader("format", "flowed");
			mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");
			mimeMessage.setFrom(new InternetAddress(SENDER_EMAIL_ID, "Fundoo Note Application"));
			mimeMessage.setReplyTo(InternetAddress.parse(SENDER_EMAIL_ID, false));
			mimeMessage.setSubject(subject, "UTF-8");
			mimeMessage.setText(body, "UTF-8");
			mimeMessage.setSentDate(new Date());
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return mimeMessage;
	}

	/**
	 * This class sets the properties configuaration of the mail and return it.
	 * 
	 * @return Properties class
	 */
	private Properties mailPropertiesSettings() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS
		return properties;

	}
}
