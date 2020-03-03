package com.bridgelabz.fundoonotes.service.implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.exception.AuthorizationException;
import com.bridgelabz.fundoonotes.exception.InvalidCredentialsException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.exception.UserVerificationException;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.model.dto.LoginDTO;
import com.bridgelabz.fundoonotes.model.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.dto.UserDTO;
import com.bridgelabz.fundoonotes.repository.IUserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.service.IUserService;
import com.bridgelabz.fundoonotes.utility.EMailServiceProvider;
import com.bridgelabz.fundoonotes.utility.JWTToken;
import com.bridgelabz.fundoonotes.utility.RabbitMQSender;
import com.bridgelabz.fundoonotes.utility.Util;

/**
 * This class implements {@link IUserService} interface which has the
 * UnImplemented functionality of registering the user and verifying with the
 * identity and all implementations as carried here.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 * @modified -> 2020-02-09
 * @updated -> RabbitMQ functionality added to the existing JMS mail service.
 * @see {@link BCryptPasswordEncoder} for creating encrypted password
 * @see {@link IUserRepository} for storing data with the database
 * @see {@link JWTToken} fore creation of token
 * @see {@link RabbitMQSender}, {@link EMailServiceProvider} for mail facilities
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
	private RabbitMQSender rabbitMQSender;

	/**
	 * This class takes the user inputed data and checks whether the user present in
	 * the database or not if the user is not registered with the database then it
	 * copies all the data from DTO to normal user class and encodes the user
	 * password and save the user with the database and then by using
	 * {@link JWTToken} and {@link EMailServiceProvider} it create a token and send
	 * the user's mail id for verification.
	 */
	@Override
	public boolean register(UserDTO userDto) {
		User fetchedUser = userRepository.getUser(userDto.getEmailId());

		if (fetchedUser != null) {
			if (fetchedUser.isVerified()) {
				return false;
			}
			throw new UserException("Account already exist please verify....", 208);
		}
		// new user created and saved to DB
		User newUser = new User();
		BeanUtils.copyProperties(userDto, newUser);
		newUser.setCreatedDate(LocalDateTime.now());
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setVerified(false);
		userRepository.save(newUser);
		// user again fetched from database and mail sent for verification
		User fetchedUserForVerification = userRepository.getUser(newUser.getEmailId());
		String emailBodyContaintLink = Util.createLink(
				Util.IP_ADDRESS + Util.ANGULAR_PORT_NUMBER + Util.REGESTATION_VERIFICATION_LINK,
				jwtToken.createJwtToken(fetchedUserForVerification.getUserId()));
		if (rabbitMQSender.send(new MailObject(fetchedUserForVerification.getEmailId(), Util.REGISTRATION_EMAIL_SUBJECT,
				emailBodyContaintLink))) {
			return true;
		}
		throw new UserException("Opps...Error sending verification mail!", 500);
	}

	/**
	 * This function takes token in the form of String input parameter and then
	 * decode the token and fetch customer id and checks of his valid candidate
	 * credentials then from that id it gets the data from
	 * database @see{@link IUserRepository} and after successful verification it
	 * returns boolean value
	 */
	@Override
	public User verifiedUser(String token) {
		long fetchedId = jwtToken.decodeToken(token);
		User fetchedUser = userRepository.getUser(fetchedId);
		if (fetchedId > 0 && fetchedUser != null) {
			if (!fetchedUser.isVerified()) {
				userRepository.isVerifiedUser(fetchedId);
				return fetchedUser;
			}
			throw new UserVerificationException("Opps...User already verified!", 422);
		}
		throw new UserException(Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
	}

	/**
	 * this function takes login information from user on the basis of input user
	 * email id it fetch all information of the user from database and checks for
	 * Verification details of the user. if the user is verified then it return all
	 * information of user else it proceed with the verification.
	 */
	@Override
	public User login(LoginDTO loginInformation) {
		User fetchedUser = userRepository.getUser(loginInformation.getEmailId());
		// valid user
		if (fetchedUser != null) {
			// valid user with valid password
			if (passwordEncoder.matches(loginInformation.getPassword(), fetchedUser.getPassword())) {
				// valid user with verified
				if (fetchedUser.isVerified()) {
					// valid user with all valid properties
					return fetchedUser;
				}
				String emailBodyLink = Util.createLink(
						Util.IP_ADDRESS + Util.ANGULAR_PORT_NUMBER + Util.REGESTATION_VERIFICATION_LINK,
						jwtToken.createJwtToken(fetchedUser.getUserId()));
//				emailServiceProvider.sendMail(fetchedUser.getEmailId(), Util.REGISTRATION_EMAIL_SUBJECT, emailBodyLink);
				rabbitMQSender
						.send(new MailObject(fetchedUser.getEmailId(), Util.REGISTRATION_EMAIL_SUBJECT, emailBodyLink));
				return null;
			}
			// password don't match
			throw new InvalidCredentialsException("Opps...Invalid Credentials!", 400);
		}
		// not registered
		throw new UserException(Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
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
				String emailBodyLink = Util.createLink(Util.IP_ADDRESS + Util.ANGULAR_PORT_NUMBER + "/update-password",
						jwtToken.createJwtToken(fetchedUser.getUserId()));
//				emailServiceProvider.sendMail(fetchedUser.getEmailId(), "Update Password Link", emailBodyLink);
				rabbitMQSender.send(new MailObject(fetchedUser.getEmailId(), "Update Password Link", emailBodyLink));
				return true;
			}
			// not verified
			String emailRegistrationVerificationBodyLink = Util.createLink(
					Util.IP_ADDRESS + Util.ANGULAR_PORT_NUMBER + Util.REGESTATION_VERIFICATION_LINK,
					jwtToken.createJwtToken(fetchedUser.getUserId()));
//			emailServiceProvider.sendMail(fetchedUser.getEmailId(), Util.REGISTRATION_EMAIL_SUBJECT,
//					emailRegistrationVerificationBodyLink);
			rabbitMQSender.send(new MailObject(fetchedUser.getEmailId(), Util.REGISTRATION_EMAIL_SUBJECT,
					emailRegistrationVerificationBodyLink));
			return false;
		}
		// user not found
		throw new UserException(Util.USER_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
	}

	/**
	 * This function takes Update password information along with valid token as
	 * user input parameter and encode the recent password given by the user and
	 * after successful update of password confirmation message is sent to the
	 * user's mail id.
	 */
	@Override
	public boolean updatePassword(UpdatePassword updatePasswordInformation, String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (updatePasswordInformation.getPassword().equals(updatePasswordInformation.getConfirmPassword())) {
			updatePasswordInformation
					.setConfirmPassword(passwordEncoder.encode(updatePasswordInformation.getConfirmPassword()));
			userRepository.updatePassword(updatePasswordInformation, jwtToken.decodeToken(token));
			// sends mail after updating password
//			emailServiceProvider.sendMail(updatePasswordInformation.getEmailId(), "Password updated sucessfully...",
//					mailContaintAfterUpdatingPassword(updatePasswordInformation));
			rabbitMQSender.send(new MailObject(fetchedUser.getEmailId(), "Password updated sucessfully...",
					mailContaintAfterUpdatingPassword(fetchedUser)));
			return true;
		}
		throw new AuthorizationException("Opps...password did not match!", 401);
	}

	/**
	 * This function takes Update password details as string input parameter and
	 * prepare a body contains for sending mail to the concern user. along with user
	 * valid details it sends login link to the user.
	 * 
	 * @param fetchedUser as {@link User}
	 * @return String
	 */
	private String mailContaintAfterUpdatingPassword(User fetchedUser) {

		String passwordUpdateBodyContent = "Hallo Mr/s. " + fetchedUser.getFirstName() + " " + fetchedUser.getLastName()
				+ ",\n\n";
		String message = "password updated sucessfully at : " + LocalDateTime.now() + "\n😱. Click ☟\n\n";
		String loginLink = Util.IP_ADDRESS + Util.ANGULAR_PORT_NUMBER + "/login";
		return passwordUpdateBodyContent + message + loginLink;
	}

}