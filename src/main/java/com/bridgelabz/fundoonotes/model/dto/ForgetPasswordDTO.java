package com.bridgelabz.fundoonotes.model.dto;

import com.bridgelabz.fundoonotes.annotation.ValidEmailId;

/**
 * Forget password DTO.
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-29
 * @version 1.0
 */
public class ForgetPasswordDTO {

	@ValidEmailId
	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
