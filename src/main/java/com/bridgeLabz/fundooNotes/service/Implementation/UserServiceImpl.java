package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.exception.UserException;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.DTO.LoginInformation;
import com.bridgeLabz.fundooNotes.model.DTO.UpdatePassword;
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
	public static final String REGISTRATION_EMAIL_SUBJECT = "Registration Verification Link";
	public static final String SERVER_ADDRESS = "http://192.168.1.41:8080";
	private static final String REGESTATION_VERIFICATION_LINK = "/user/verification";
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

		String emailBodyContaintLink = Util.createLink(SERVER_ADDRESS + REGESTATION_VERIFICATION_LINK,
				jwtToken.createJwtToken(newUser.getUserId()));
		emailServiceProvider.sendMail(newUser.getEmailId(), REGISTRATION_EMAIL_SUBJECT, emailBodyContaintLink);

		return true;
	}

	/**
	 * This function takes token in the form of String input paraameter and then
	 * decode the token and fetch customer id and checks fo his valid candidancy
	 * then from that id it gets the data from database @see{@link IUserRepository}
	 * and after successful varification it returns boolean value
	 */
	@Override
	public boolean isVerifiedUserToken(String token) {
		long fetchedId = jwtToken.decodeToken(token);
		if (fetchedId > 0) {
			userRepository.isVerifiedUser(fetchedId);
			return true;
		}
		return false;
	}

	/**
	 * this function takes login information from user on the basis of input user
	 * email id it fetch all information of the user from database and checks for
	 * varification details of the user. if the user is verified then it return all
	 * information of user else it proceed with the verification.
	 */
	@Override
	public User login(LoginInformation loginInformation) {
		User fetchedUser = userRepository.getUser(loginInformation.getEmailId());
		// valid user
		if (fetchedUser != null) {
			// valid user with valid password
			if (passwordEncoder.matches(loginInformation.getPassword(), fetchedUser.getPassword())) {
				// valid user with verified
				if (fetchedUser.isVerified()) {
					return fetchedUser;
				}
				String emailBodyLink = Util.createLink(SERVER_ADDRESS + REGESTATION_VERIFICATION_LINK,
						jwtToken.createJwtToken(fetchedUser.getUserId()));
				emailServiceProvider.sendMail(fetchedUser.getEmailId(), REGISTRATION_EMAIL_SUBJECT, emailBodyLink);
				return null;
			}
			// password dont match
			throw new UserException("Opps...Invalid credentials!", 400);
		}
		// not registered
		throw new UserException("Opps...User not found!", 400);
	}

	/**
	 * This function takes Email id as String input parameter and checks for user if
	 * user not found it throws user not found exception. if user is found it checks
	 * for verification status if user is verified then it sends the user with
	 * update password link else it send the user with verification link to verify
	 * his identity before reseting his password.
	 */
	@Override
	public boolean isUserPresent(String emailId) {
		User fetchedUser = userRepository.getUser(emailId);
		// user found
		if (fetchedUser != null) {
			// user verified
			if (fetchedUser.isVerified()) {
				String emailBodyLink = Util.createLink(SERVER_ADDRESS + "/user/updatePassword",
						jwtToken.createJwtToken(fetchedUser.getUserId()));
				emailServiceProvider.sendMail(fetchedUser.getEmailId(), "Update Password Link", emailBodyLink);
				System.out.println("is userPresent token : " + jwtToken.createJwtToken(fetchedUser.getUserId()));
				return true;
			}
			// not verified
			String emailRegistrationVerificationBodyLink = Util.createLink(
					SERVER_ADDRESS + REGESTATION_VERIFICATION_LINK, jwtToken.createJwtToken(fetchedUser.getUserId()));
			emailServiceProvider.sendMail(fetchedUser.getEmailId(), REGISTRATION_EMAIL_SUBJECT,
					emailRegistrationVerificationBodyLink);
			return false;
		}
		// user not found
		throw new UserException("Opps...User not found!", 400);
	}

	@Override
	public boolean updatePassword(UpdatePassword updatePasswordInformation, String token) {

		if (updatePasswordInformation.getPassword().equals(updatePasswordInformation.getConfirmPassword())) {
			updatePasswordInformation
					.setConfirmPassword(passwordEncoder.encode(updatePasswordInformation.getConfirmPassword()));
			userRepository.updatePassword(updatePasswordInformation, jwtToken.decodeToken(token));
			return true;
		}
		throw new UserException("Opps...password did not match!", 400);
	}

}