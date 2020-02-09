package com.bridgeLabz.fundooNotes.response;

import java.io.Serializable;

/**
 * Configuration class for rabbit MQ. which contains the Template for rabbitMQ
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 *
 */
public class MailObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private String subject;

	private String message;

	public MailObject(String email, String subject, String message) {
		this.email = email;
		this.subject = subject;
		this.message = message;
	}

	public MailObject() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}