package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.exception.AuthorizationException;
import com.bridgeLabz.fundooNotes.model.Label;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;
import com.bridgeLabz.fundooNotes.repository.ILabelRepository;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.ILabelService;
import com.bridgeLabz.fundooNotes.utility.JWTToken;
import com.bridgeLabz.fundooNotes.utility.Util;

@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ILabelRepository labelJpa;
	@Autowired
	private JWTToken jwtToken;

	/**
	 * This function takes authentication token as String input parameter and decode
	 * token an authenticate user after successful authentication it return the
	 * verified user else throw {@link AuthorizationException}
	 * 
	 * @param token as String input parameter
	 * @return {@link User}
	 */
	private User authenticatedUser(String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			return fetchedUser;
		}
		throw new AuthorizationException(Util.USER_AUTHORIZATION_EXCEPTION_MESSAGE,
				Util.USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	@Override
	public boolean createLabel(String token, LabelDTO labelDTO) {
		User fetchedUser = authenticatedUser(token);
		Label fetchedLabel = labelJpa.findOneBylabelName(labelDTO.getLabelName());
		if (fetchedLabel != null) {
			return false;
		}
		Label newLabel = new Label();
		BeanUtils.copyProperties(labelDTO, newLabel);
		newLabel.setCreatedDate(LocalDateTime.now());
		fetchedUser.getLabels().add(newLabel);
		labelJpa.save(newLabel);
		return true;
	}

}
