package com.bridgeLabz.fundooNotes.utility;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Util {

	public static void sendEmail(Session session, String toEmail, String subject, String body) {

		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			// set message headers
			mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			mimeMessage.addHeader("format", "flowed");
			mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");

			mimeMessage.setFrom(new InternetAddress(System.getenv("emailId"), "Fundoo Note Application"));

			mimeMessage.setReplyTo(InternetAddress.parse(System.getenv("emailId"), false));

			mimeMessage.setSubject(subject, "UTF-8");

			mimeMessage.setText(body, "UTF-8");

			mimeMessage.setSentDate(new Date());

			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
//			System.out.println("Message is ready");
			Transport.send(mimeMessage);

			System.out.println("Email Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
