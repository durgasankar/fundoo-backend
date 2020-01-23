package com.bridgeLabz.fundooNotes.service;

import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;

/**
 * This inetrface has the UnImplemented functionality of registering the user
 * and verifying with the identity.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 */
public interface IUserService {
	/**
	 * UnImplemented functionality of adding the user DTO class and registering with
	 * the database and after sucessful addition returns boolean value.
	 * 
	 * @param userDto as DTO class user input parameter
	 * @return Boolean
	 */
	public boolean register(UserDTO userDto);

	/**
	 * UnImplemented functionality of verifying the user from the data fetched from
	 * repository and after sucessful addition returns boolean value.
	 * 
	 * @param token as String input parameter
	 * @return Boolean
	 */
	public boolean isVerifiedUserToken(String token);
	
	

}
