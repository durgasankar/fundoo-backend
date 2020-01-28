package com.bridgeLabz.fundooNotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User model which has the parameters which will hit with the database
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 */
@Entity
@Table(name = "user_details")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long userId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String emailId;
	private String password;
	private String mobileNumber;
	private LocalDateTime createdDate;
	private boolean isVerified;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private List<Note> notes;

	/**
	 * Constructor by using fields for constructor injection
	 * 
	 * @param userId       as Long input parameter
	 * @param firstName    as String input parameter
	 * @param lastName     as String input parameter
	 * @param emailId      as String input parameter
	 * @param password     as String input parameter
	 * @param mobileNumber as boolean input parameter
	 */
	public User(long userId, String firstName, String lastName, String emailId, String password, String mobileNumber) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Constructor
	 */
	public User() {

	}

	/**
	 * Getter method for id
	 * 
	 * @return Long
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * setter method for id
	 * 
	 * @param userId as Long input parameter
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

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

	/**
	 * Getter method for Local date and time
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setter method for first name
	 * 
	 * @param createdDate as String input parameter
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Getter method for isVerified
	 * 
	 * @return Boolean
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * Setter method for isVerified
	 * 
	 * @param isVerified as boolean input parameter
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * Getter method to get all notes of user
	 * 
	 * @return List<Note>
	 */
	public List<Note> getNotes() {
		return notes;
	}

	/**
	 * Setter method to set all notes of the user
	 * 
	 * @param notes as LIst<Note> input parameter
	 */
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	/**
	 * ToString method to print the data in String format
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", password=" + password + ", mobileNumber=" + mobileNumber + ", createdDate=" + createdDate
				+ ", isVerified=" + isVerified + "]";
	}

}
