package com.bridgeLabz.fundooNotes.response;

public class UserDetailResponse {

	private String tokenCode;
	private int statusCode;

	public UserDetailResponse(String tokenCode, int statusCode) {
		this.tokenCode = tokenCode;
		this.statusCode = statusCode;
	}

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
