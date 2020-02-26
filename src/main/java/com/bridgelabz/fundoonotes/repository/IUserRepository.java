package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.model.dto.UpdatePassword;

/**
 * Repository Interface which has the unimplemented functionality of all
 * repository related work which has the direct access with the database
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-21
 * @version 1.0
 */
public interface IUserRepository {
	/**
	 * UnImplemented functionality of adding a user to database
	 * 
	 * @param newUser as User class type input parameter
	 * @return User class
	 */
	public User save(User newUser);

	/**
	 * UnImplemented functionality of finding a user from the database by taking
	 * EmailId as input parameter.
	 * 
	 * @param emailId as String input parameter
	 * @return User class
	 */
	public User getUser(String emailId);

	/**
	 * UnImplemented functionality of finding a user from the database by taking
	 * userId as input parameter.
	 * 
	 * @param id as Long input parameter
	 * @return User class
	 */
	public User getUser(Long id);

	/**
	 * Unimplemented functionality of verifying an user which takes userId as input
	 * parameter
	 * 
	 * @param id as Long input parameter
	 * @return Boolean value
	 */
	public boolean isVerifiedUser(Long id);

	/**
	 * UnImplemented functionality of updating user update password functionality
	 * with the database.
	 * 
	 * @param updatePasswordinformation as {@link UpdatePassword}
	 * @param id                        as Long input parameter
	 * @return
	 */
	public boolean updatePassword(UpdatePassword updatePasswordinformation, long id);

}
