package com.bridgeLabz.fundooNotes.model.DTO;

/**
 * Model class for Update password which has three parameters like email id,
 * password and confirm password.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-24
 * @version 1.0
 */
public class UpdatePassword {

	private String emailId;
	private String password;
	private String confirmPassword;

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
	 * Setter method to set confirmPassword
	 * 
	 * @return String
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Getter method to fetch confirmPassword
	 * 
	 * @param confirmPassword as String input parameter
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * toString method to print the statement.
	 */
	@Override
	public String toString() {
		return "UpdatePassword [emailId=" + emailId + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ "]";
	}

}
