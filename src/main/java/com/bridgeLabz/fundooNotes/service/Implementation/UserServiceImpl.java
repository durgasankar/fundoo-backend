package com.bridgeLabz.fundooNotes.service.Implementation;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.DTO.UserDTO;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.IUserService;
import com.bridgeLabz.fundooNotes.utility.EMailServiceProvider;
import com.bridgeLabz.fundooNotes.utility.JWTToken;
import com.bridgeLabz.fundooNotes.utility.Util;

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

	// working fine
	@Override
	public boolean register(UserDTO userDto) {
		User fetchedUser = userRepository.getUser(userDto.getEmailId());
		if (fetchedUser != null) {
//			throw new UserException("Opps...User already registred with us!");
			return false;
		}
		User newUser = new User();
		BeanUtils.copyProperties(userDto, newUser);
		newUser.setCreatedDate(LocalDateTime.now());
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setVerified(false);
		userRepository.save(newUser);

		String emailBodyContaintLink = Util.createLink("http://localhost:8080/user/verification",
				jwtToken.createJwtToken(newUser.getUserId()));
		emailServiceProvider.sendMail(newUser.getEmailId(), "Registration Verification Link", emailBodyContaintLink);
		return true;
	}

	@Override
	public boolean verifyToken(String token) {

		try {
			Long fetchedCustomerId = jwtToken.decodeToken(token);
//			User fetchedUser = userRepository.getUser(fetchedCustomerId);

			// need to save customer using verify
			userRepository.verifyUser(fetchedCustomerId);
			return true;
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

}
