package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.DTO.LoginInformation;
import com.bridgeLabz.fundooNotes.model.DTO.UpdatePassword;
import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.response.UserDetailResponse;
import com.bridgeLabz.fundooNotes.service.IUserService;
import com.bridgeLabz.fundooNotes.utility.JWTToken;

/**
 * By using the object reference of service class This class has the
 * functionality of getting connected with the user on the user specific request
 * it will redirect to the respective controller method and carryout
 * functionality of that perticular request.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 * @see {@link IUserService} implementation of all the required functionality*
 * @see {@link Response} if there is any type of response it will reflect out
 */
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private JWTToken jwtToken;

	/**
	 * This function takes all user info input as input parameter and checks the
	 * user validity@see {@link IUserService} and accordingly returns the response.
	 * 
	 * @param newUserDTO as DTO class input parameter
	 * @return ResponseEntity<Response>
	 */
	@PostMapping("registration")
	public ResponseEntity<Response> registration(@RequestBody UserDTO newUserDTO) {

		boolean resultStatus = userService.register(newUserDTO);
		if (!resultStatus) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("user already exist", 400, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("registration successful", 200, resultStatus));

	}

	/**
	 * This function takes geneerated token of the user as String input as input
	 * parameter and checks the user varification @see {@link IUserService} and
	 * accordingly returns the response.
	 * 
	 * @param newUserDTO as DTO class input parameter
	 * @return ResponseEntity<Response>
	 */
	@GetMapping("verification/{token}")
	public ResponseEntity<Response> verifyRegistration(@PathVariable("token") String token) {

		if (userService.isVerifiedUserToken(token)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified sucessfully.", 200));
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response("not verified", 400));

	}

	/**
	 * This function takes LongIn information data given by user on the basis of
	 * user input information it fetch the complete user checks for conditions
	 * whether the user is verified or not and also checks whether he is registered
	 * or not and works accordingly.
	 * 
	 * @param loginInformation
	 * @return ResponseEntity<Response>
	 */
	@PostMapping("login")
	public ResponseEntity<UserDetailResponse> loginUser(@RequestBody LoginInformation loginInformation) {

		User fetchedUserInformation = userService.login(loginInformation);
		if (fetchedUserInformation != null) {
			if (fetchedUserInformation.isVerified()) {
				// verified
				String generatedToken = jwtToken.createJwtToken(fetchedUserInformation.getUserId());
				System.out.println("Generated during login" + generatedToken);
				return ResponseEntity.status(HttpStatus.ACCEPTED).header(generatedToken, loginInformation.getEmailId())
						.body(new UserDetailResponse("login successful", 200, loginInformation));
			}
			// registered but not verified
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new UserDetailResponse("Please verify your account", 503, loginInformation));
		}
		// not registered
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetailResponse("login failed", 400));
	}

	/**
	 * This function takes email id as string input parameter and checks the
	 * existance of user from service class on validate credentials, it allows the
	 * user to reset his password by his mail. Or else, it sends the mail to user
	 * for verification of his account
	 * 
	 * @param emailId as String input parameter
	 * @return ResponseEntity<Response>
	 */
	@PostMapping("forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("emailId") String emailId) {
		boolean fetchedUserStatus = userService.isUserPresent(emailId);
		if (fetchedUserStatus) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new Response("found user", 200));
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response("not verified", 400));
	}

	@PutMapping("updatePassword/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody() UpdatePassword upadatePassword) {
		System.out.println("fetched token : " + token);
		boolean updationStatus = userService.updatePassword(upadatePassword, token);
		if (!updationStatus) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("updated sucessfully", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("updation failed", 400));

	}

}
