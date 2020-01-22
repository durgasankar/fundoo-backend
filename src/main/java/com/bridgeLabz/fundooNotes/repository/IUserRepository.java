package com.bridgeLabz.fundooNotes.repository;

import com.bridgeLabz.fundooNotes.model.User;

public interface IUserRepository {

	public User save(User newUser);

	public User getUser(String emailId);

	public User getUser(Long id);

}
