package com.bridgelabz.fundoonotes.response;

/**
 * Response class for login which holds the token code of the user and status
 * code.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-23
 * @version 1.0
 */
public class UserDetailResponse {

	private String message;
	private int statusCode;
	private String token;
	private String firstName;

	public UserDetailResponse(String message, int statusCode, String token, String firstName) {
		this.message = message;
		this.statusCode = statusCode;
		this.token = token;
		this.firstName = firstName;
	}

	public UserDetailResponse(String message, int statusCode) {

		this.message = message;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
