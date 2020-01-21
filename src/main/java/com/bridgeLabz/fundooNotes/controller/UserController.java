package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.DTOModel.UserDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.IUserService;

@RestController
@RequestMapping
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("user/registration")
	public ResponseEntity<Response> registration(@RequestBody UserDTO newUserDTO) {

		boolean resultStatus = userService.register(newUserDTO);

		if (resultStatus == false) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration successfull", 2468, newUserDTO));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("user already exist", 1024, newUserDTO));
		}

	}

}
