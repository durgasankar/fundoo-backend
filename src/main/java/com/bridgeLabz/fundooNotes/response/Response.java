package com.bridgeLabz.fundooNotes.response;

import java.util.List;

/**
 * Thsi class has the functionality of capturing response of user which would be
 * recorded when user does any type of operation.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-21
 * @version 1.0
 */
public class Response {

	private String message;

	private int statusCode;

	private Object object;

	List<String> details;

	public Response(String message, int statusCode, Object object) {
		this.message = message;
		this.statusCode = statusCode;
		this.object = object;
	}

	public Response(String message, int statusCode) {

		this.message = message;
		this.statusCode = statusCode;
	}

	public Response() {

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

	public Object getObj() {
		return object;
	}

	public void setObj(Object obj) {
		this.object = obj;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", statusCode=" + statusCode + ", object=" + object + ", details="
				+ details + "]";
	}

}
