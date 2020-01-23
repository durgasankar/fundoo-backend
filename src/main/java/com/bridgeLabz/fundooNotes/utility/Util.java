package com.bridgeLabz.fundooNotes.utility;

/**
 * This class has the all the reusable methods => createLink
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-21
 * @version 1.0
 */
public class Util {
	/**
	 * preventing from creating instance of the class
	 */
	private Util() {

	}

	/**
	 * this function just concatenate two parameter.
	 * 
	 * @param url   as String input parameter
	 * @param token as String input parameter
	 * @return String
	 */
	public static String createLink(String url, String token) {

		return url + "/" + token;
	}

}
