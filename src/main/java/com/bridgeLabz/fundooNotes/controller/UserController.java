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

import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.IUserService;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("registration")
	public ResponseEntity<Response> registration(@RequestBody UserDTO newUserDTO) {

		// check point 1
		boolean resultStatus = userService.register(newUserDTO);
//		System.out.println("Check poiont 2 " + resultStatus);
		if (resultStatus == false) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("user already exist", 400, newUserDTO));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration successful", 200, newUserDTO));

	}

	@GetMapping("verification/{token}")
	public ResponseEntity<Response> verifyRegistration(@PathVariable("token") String token) {

		System.out.println("token for verification" + token);
		if (userService.isVerifiedUserToken(token)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("not verified", 400));

		}
	}

}
