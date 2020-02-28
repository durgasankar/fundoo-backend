package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.model.dto.LoginDTO;
import com.bridgelabz.fundoonotes.model.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.dto.UserDTO;

/**
 * This interface has the UnImplemented functionality of registering the user
 * and verifying with the identity, login, forget password and update password
 * functionality.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 */
public interface IUserService {
	/**
	 * UnImplemented functionality of adding the user DTO class and registering with
	 * the database and after successful addition returns boolean value.
	 * 
	 * @param userDto as {@link UserDTO} class
	 * @return Boolean
	 */
	public boolean register(UserDTO userDto);

	/**
	 * UnImplemented functionality of verifying the user from the data fetched from
	 * repository and after successful addition returns boolean value.
	 * 
	 * @param token as String input parameter
	 * @return Boolean
	 */
	public User verifiedUser(String token);

	/**
	 * UnImplemented functionality of login which takes userDto as class type input
	 * parameter and returns complete user class after verifying the user.
	 * 
	 * @param loginInformation as {@link LoginDTO}
	 * @return User class
	 */

	public User login(LoginDTO loginInformation);

	/**
	 * UnImplemented functionality of verifying user which takes email id as String
	 * input parameter checks the verification and existence status of user and
	 * returns boolean value
	 * 
	 * @param emailId as String input parameter
	 * @return Boolean
	 */
	public boolean isUserPresent(String emailId);

	/**
	 * UnImplemented functionality of updating the password information when user
	 * forgets his old password.and after update, confirmation mail should be sent
	 * to the user.
	 * 
	 * @param updatePassword as {@link UpdatePassword} class
	 * @param token          as String input parameter
	 * @return Boolean
	 */
	public boolean updatePassword(UpdatePassword updatePassword, String token);

}
