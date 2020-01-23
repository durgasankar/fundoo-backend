package com.bridgeLabz.fundooNotes.model.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Component
public class LoginInformation {
	@Email
	private String emailId;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Enter a valid password")
	private String password;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
