package com.bridgeLabz.fundooNotes.repository;

import com.bridgeLabz.fundooNotes.model.User;

public interface IUserRepository {

	User save(User newUser);

	User getUser(String emailId);

}


