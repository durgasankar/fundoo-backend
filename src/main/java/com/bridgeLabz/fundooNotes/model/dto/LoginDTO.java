package com.bridgeLabz.fundooNotes.model.dto;

import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import com.bridgeLabz.fundooNotes.annotation.ValidEmailId;

import io.swagger.annotations.ApiModelProperty;

/**
 * Model class for login which has two parameters like email id and password.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-23
 * @version 1.0
 */
@Component
public class LoginDTO {
	@ValidEmailId
	@ApiModelProperty(notes = "valid email id for login", name = "email id")
	private String emailId;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Enter a valid password")
	@ApiModelProperty("password for login")
	private String password;

	/**
	 * Getter method to fetch emailId
	 * 
	 * @return String
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Setter method to set email id
	 * 
	 * @param emailId as String input parameter
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Getter method to fetch password
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method to set password
	 * 
	 * @param password as String input parameter
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * toString method just to simple print the statement
	 */
	@Override
	public String toString() {
		return "LoginInformation [emailId=" + emailId + ", password=" + password + "]";
	}

}
