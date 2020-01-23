package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.DTO.LoginInformation;
import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.IUserService;

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
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

	@Autowired
	private IUserService userService;

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
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user already exist", 400));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration successful", 200));

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
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("not verified", 400));

	}


}
