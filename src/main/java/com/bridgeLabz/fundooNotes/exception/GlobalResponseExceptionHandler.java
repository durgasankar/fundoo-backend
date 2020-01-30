package com.bridgeLabz.fundooNotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgeLabz.fundooNotes.response.Response;

/**
 * Global Exception Handler which handles all runtime exceptions like
 * {@link UserException}, {@link NoteException}, {@link AuthorizationException},
 * {@link InvalidCredentialsException}, {@link RemainderException} from service
 * layer itself.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-29
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Handles all incoming {@link UserException} during Runtime.
	 * 
	 * @param userException as {@link UserException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleAllUserException(UserException userException) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response(userException.getMessage(), 502));
	}

	/**
	 * Handles all incoming {@link NoteException} during Runtime.
	 * 
	 * @param noteException as {@link NoteException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> handleAllNoteException(NoteException noteException) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(noteException.getMessage(), 404));
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
				.body(new Response(authorizationException.getMessage(), 401));
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
				.body(new Response(invalidCredentialsException.getMessage(), 400));
	}

	/**
	 * Handles all incoming {@link RemainderException} during Runtime.
	 * 
	 * @param invalidCredentialsException as {@link RemainderException}
	 * @return ResponseEntity<Response>
	 */
	@ExceptionHandler(RemainderException.class)
	public ResponseEntity<Response> handleAllRemainderException(RemainderException remainderException) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response(remainderException.getMessage(), 502));
	}
}
