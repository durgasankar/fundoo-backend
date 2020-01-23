package com.bridgeLabz.fundooNotes.utility;

public class Util {

	private Util() {

	}

	public static String createLink(String url, String token) {

		return url + "/" + token;
	}

}
