package com.bridgeLabz.fundooNotes.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

@Component
public class JWTToken {

	private static final String TOKEN_SECRET_CODE = "r20jc134";

	public String createJwtToken(long id) {
		String generatedToken = null;
		try {
			generatedToken = JWT.create().withClaim("id", id).sign(Algorithm.HMAC256(TOKEN_SECRET_CODE));
		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}
		return generatedToken;
	}

	public Long decodeToken(String jwtToken)
			throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		Long userId = (long) 0;

		try {
			if (jwtToken != null) {
				// for verification algorithm
				Verification verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET_CODE));
				JWTVerifier jwtverifier = verification.build();
				// to decode token
				DecodedJWT decodedjwt = jwtverifier.verify(jwtToken);
				// claim the token by userId
				Claim claim = decodedjwt.getClaim("id");
				userId = claim.asLong();
			}

		} catch (IllegalArgumentException | JWTCreationException e) {

			e.printStackTrace();
		}

		return userId;
	}

//	public static void main(String[] args)
//			throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
//		JwtGenerator generator = new JwtGenerator();
//		String generatedToken = generator.createJwtToken(15);
//		System.out.println(generatedToken);
//		System.out.println(generator.decodeToken(generatedToken));
//
//	}
}