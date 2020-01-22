package com.bridgeLabz.fundooNotes.service;

import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;

public interface IUserService {

	public boolean register(UserDTO userDto);

	public boolean isVerifiedUserToken(String token);

}
