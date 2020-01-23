package com.bridgeLabz.fundooNotes.response;

/**
 * Response class for login which holds the token code of the user and status
 * code.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-23
 * @version 1.0
 */
public class UserDetailResponse {

	private String tokenCode;
	private int statusCode;

	/**
	 * Constructor takes token code and status code as input parameter
	 * 
	 * @param tokenCode  as String input parameter
	 * @param statusCode as Integer input parameter
	 */
	public UserDetailResponse(String tokenCode, int statusCode) {
		this.tokenCode = tokenCode;
		this.statusCode = statusCode;
	}

	/**
	 * super class non parameterized constructor
	 */
	public UserDetailResponse() {
		super();

	}

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
