package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

/**
 * This class has the functionality of creating a jwt token and decode the token
 * based on user's provided data and provided secret code.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 * @see {@link JWT}
 */
@Component
public class JWTToken {

	private static final String TOKEN_SECRET_CODE = "r20jc134";

	/**
	 * This function takes user id as input parameter with that id it sign with
	 * algorithms{@link Algorithm HMAC256} with the assigned secret code then it
	 * generate unique String consist of header.payload.signature.
	 * 
	 * @param id as Long input parameter
	 * @return String generatedToken
	 */
	public String createJwtToken(long id) {
		String generatedToken = null;
		try {
			generatedToken = JWT.create().withClaim("id", id).sign(Algorithm.HMAC256(TOKEN_SECRET_CODE));
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return generatedToken;
	}

	/**
	 * This function takes jwtToken as String input parameter and verifies with the
	 * given secret code with concerned algorithms{@link Algorithm HMAC256} then
	 * decode the token and claims it with the user's provided data during token
	 * generation then returns the user's provided data.
	 * 
	 * @param jwtToken as String input parameter
	 * @return userId as Long
	 */
	public Long decodeToken(String jwtToken) {
		Long userId = (long) 0;
		try {
			if (jwtToken != null) {
				Verification verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET_CODE));
				JWTVerifier jwtverifier = verification.build();
				DecodedJWT decodedjwt = jwtverifier.verify(jwtToken);
				Claim claim = decodedjwt.getClaim("id");
				userId = claim.asLong();
			}
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return userId;
	}
}