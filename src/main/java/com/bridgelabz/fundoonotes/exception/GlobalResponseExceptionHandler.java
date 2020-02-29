package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.Response;

/**
 * Global Exception Handler which handles all runtime exceptions like
 * {@link UserException}, {@link NoteException}, {@link AuthorizationException},
 * {@link InvalidCredentialsException}, {@link RemainderException},
 * {@link LabelException}, {@link MailSendingException}
 * {@link ColaboratorException} from service layer itself.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-29
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Handles all incoming {@link UserException}, {@link RemainderException},
	 * {@link MailSendingException} during Runtime.
	 * 
	 * @param userException as {@link Exception}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler({ RemainderException.class, MailSendingException.class })
	public ResponseEntity<Response> handleAllUserException(Exception exception) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response(exception.getMessage(), 502));
	}

	/**
	 * Handles all incoming {@link UserException} during Runtime.
	 * 
	 * @param userException as {@link UserException}
	 * @return ResponseEntity<Response>	
	 */
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleAllUserException(UserException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(exception.getMessage(), exception.getStatus()));
	}

	/**
	 * Handles all incoming {@link NoteException} during Runtime.
	 * 
	 * @param noteException as {@link NoteException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> handleAllNoteException(NoteException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(exception.getMessage(), exception.getStatus()));
	}

	/**
	 * Handles all incoming {@link AuthorizationException} during Runtime.
	 * 
	 * @param authorizationException as {@link AuthorizationException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<Response> handleAllNoteException(AuthorizationException authorizationException) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new Response(authorizationException.getMessage(), authorizationException.getStatus()));
	}

	/**
	 * Handles all incoming {@link InvalidCredentialsException} during Runtime.
	 * 
	 * @param invalidCredentialsException as {@link InvalidCredentialsException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Response> handelAllInvalidCredentialException(
			InvalidCredentialsException invalidCredentialsException) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(invalidCredentialsException.getMessage(), invalidCredentialsException.getStatus()));
	}

	/**
	 * Handles all incoming {@link NoteException} during Runtime.
	 * 
	 * @param noteException as {@link NoteException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(LabelException.class)
	public ResponseEntity<Response> handleAllLabelException(LabelException labelException) {
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response(labelException.getMessage(), labelException.getStatus()));
	}

	/**
	 * Handles all incoming {@link ColaboratorException} during Runtime.
	 * 
	 * @param noteException as {@link ColaboratorException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(ColaboratorException.class)
	public ResponseEntity<Response> handleAllColaboratorException(ColaboratorException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(exception.getMessage(), exception.getStatus()));
	}

}
