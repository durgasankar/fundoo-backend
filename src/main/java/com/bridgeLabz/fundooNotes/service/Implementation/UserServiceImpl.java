package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.DTOModel.UserDTO;
import com.bridgeLabz.fundooNotes.exception.UserException;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.IUserService;
import com.bridgeLabz.fundooNotes.utility.JWTToken;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private JWTToken jwtToken;

	@Override
	public boolean register(UserDTO userDto) {
		System.out.println("Inside service");
		// fetched the user from db
		User fetchedUser = userRepository.getUser(userDto.getEmailId());

		if (fetchedUser != null) {
			throw new UserException("Opps...User already registred with us!");
		}
		User newUser = new User();
		newUser.setPassword("abc");

		System.out.println(newUser.getPassword());
		BeanUtils.copyProperties(userDto, newUser);

		newUser.setCreatedDate(LocalDateTime.now());
		System.out.println(newUser.getPassword());
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setVerified(false);

		userRepository.save(newUser);

		return true;

	}

}
