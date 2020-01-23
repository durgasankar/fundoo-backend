package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.IUserService;
import com.bridgeLabz.fundooNotes.utility.EMailServiceProvider;
import com.bridgeLabz.fundooNotes.utility.JWTToken;
import com.bridgeLabz.fundooNotes.utility.Util;

/**
 * This class implements {@link IUserService} inetrface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementions as carried here.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 * @see {@link BCryptPasswordEncoder} for creating encrypted password
 * @see {@link IUserRepository} for storing data with the database
 * @see {@link JWTToken} fore creatuion of token
 * @see {@link EMailServiceProvider} for mail facilities
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private JWTToken jwtToken;
	@Autowired
	private EMailServiceProvider emailServiceProvider;

	/**
	 * This class takes the user inputed data and checks whether the user present in
	 * the database or not if the user is not registered with the database then it
	 * copies all the data from dto to normal user class and encodes the user
	 * password and save the user with the database and then by using
	 * {@link JWTToken} and {@link EMailServiceProvider} it create a token and send
	 * the user's mail id for verification.
	 */
	@Override
	public boolean register(UserDTO userDto) {
		User fetchedUser = userRepository.getUser(userDto.getEmailId());
		if (fetchedUser != null) {
			return false;
		}
		User newUser = new User();
		BeanUtils.copyProperties(userDto, newUser);
		newUser.setCreatedDate(LocalDateTime.now());
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setVerified(false);
		userRepository.save(newUser);

		String emailBodyContaintLink = Util.createLink("http://192.168.1.41:8080/user/verification",
				jwtToken.createJwtToken(newUser.getUserId()));
		emailServiceProvider.sendMail(newUser.getEmailId(), "Registration Verification Link", emailBodyContaintLink);

		return true;
	}

	/**
	 * This function takes token in the form of String input paraameter and then
	 * decode the token and fetch customer id and and from that id it gets the data
	 * from database @see{@link IUserRepository} and after successful varification
	 * it returns boolean value
	 */
	@Override
	public boolean isVerifiedUserToken(String token) {
		userRepository.isVerifiedUser(jwtToken.decodeToken(token));
		return true;

	}

}
