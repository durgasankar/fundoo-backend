package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.model.dto.LoginDTO;
import com.bridgelabz.fundoonotes.model.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.dto.UserDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetailResponse;
import com.bridgelabz.fundoonotes.service.IUserService;
import com.bridgelabz.fundoonotes.utility.JWTToken;

/**
 * By using the object reference of service class This class has the
 * functionality of getting connected with the user on the user specific request
 * it will redirect to the respective controller method and carry out
 * functionality of that particular request.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 * @see {@link IUserService} implementation of all the required functionality*
 * @see {@link Response} if there is any type of response it will reflect out
 * @updated 2020-01-29
 * @modified -> {@link HttpStatus} for all API
 */
@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200")
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
					.body(new Response("user already exist... please login!", 208, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("registration successful...login to continue!", 201, resultStatus));

	}

	/**
	 * This function takes generated token of the user as String input as input
	 * parameter and checks the user verification @see {@link IUserService} and
	 * accordingly returns the response.
	 * 
	 * @param newUserDTO as DTO class input parameter
	 * @return ResponseEntity<Response>
	 */
	@PutMapping("verification/{token}")
	public ResponseEntity<Response> verifyRegistration(@PathVariable("token") String token) {
		User fetchedUser = userService.verifiedUser(token);
		
		if (fetchedUser != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("account verified sucessfully.", 200, fetchedUser.getFirstName()));
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response("Invalid verification attempt", 406));

	}

	/**
	 * This function takes LongIn information data given by user on the basis of
	 * user input information it fetch the complete user checks for conditions
	 * whether the user is verified or not and also checks whether he is registered
	 * or not and works accordingly.
	 * 
	 * @param loginInformation as {@link LoginDTO}
	 * @return ResponseEntity<Response>
	 */
	@PostMapping("login")
	public ResponseEntity<UserDetailResponse> loginUser(@RequestBody LoginDTO loginInformation) {
		User fetchedUserInformation = userService.login(loginInformation);

		if (fetchedUserInformation != null) {
			String generatedToken = jwtToken.createJwtToken(fetchedUserInformation.getUserId());
			System.out.println("generated token : " + generatedToken);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new UserDetailResponse("login successful", 200, generatedToken));
		}
		// not registered
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new UserDetailResponse("Check Your mail for verification...", 401));
	}

	/**
	 * This function takes email id as string input parameter and checks the
	 * Existence of user from service class on validate credentials, it allows the
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
			return ResponseEntity.status(HttpStatus.FOUND).body(new Response("found user", 302));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("not verified", 401));
	}

	/**
	 * This function takes update password credentials input parameter along with
	 * valid token as String input parameter after verifying the credentials and
	 * update the password and after successful update it displays corresponding
	 * message.
	 * 
	 * @param token           as String Input parameter
	 * @param upadatePassword as {@link UpdatePassword} class
	 * @return ResponseEntity<Response>
	 */
	@PutMapping("updatePassword/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody() UpdatePassword upadatePassword) {
		boolean updationStatus = userService.updatePassword(upadatePassword, token);
		if (updationStatus) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("updated sucessfully", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("updation failed", 400));

	}

}
