package com.bridgelabz.fundoonotes.exception;

/**
 * This class extends {@link RuntimeException} and creates a custom exception
 * which would be thrown during the user's abnormal activity while performing
 * mail sending operations.
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 * @see {@link GlobalResponseExceptionHandler}
 */
public class MailSendingException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int status;

	/**
	 * Constructor takes message and Status code as input parameter and fetch
	 * message from its superclass.
	 * 
	 * @param message as String input parameter
	 * @param status  as Integer input parameter
	 */
	public MailSendingException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
