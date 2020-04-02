package com.bridgelabz.fundoonotes.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

/**
 * User DTO class which has the parameters which user will give the data
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.1
 * @updated -> 2020-04-02
 * @modified -> added extra field address on registration form.
 */
@Component
public class UserDTO {

	@Pattern(regexp = "[a-zA-Z]*", message = "only alphabets are allowed")
	private String firstName;
	@Pattern(regexp = "[a-zA-Z]*", message = "only alphabets are allowed")
	private String lastName;
	@Email
	private String emailId;
	private String password;
	@NotNull(message = "field should not be empty")
	private String mobileNumber;
	private String address;

	/**
	 * Getter method for first name
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter method for first name
	 * 
	 * @param firstName as String input parameter
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for Last name
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter method for Last name
	 * 
	 * @param lastName as String input parameter
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for email id
	 * 
	 * @return String
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Setter method for Email id
	 * 
	 * @param emailId as String input parameter
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Getter method for password
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter method for password
	 * 
	 * @param password as String input parameter
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter method for mobile number
	 * 
	 * @return String
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Setter method for mobile number
	 * 
	 * @param mobileNumber as String input parameter
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId + ", password="
				+ password + ", mobileNumber=" + mobileNumber + ", address=" + address + "]";
	}

	/**
	 * ToString method to print the data in String format
	 */

}
