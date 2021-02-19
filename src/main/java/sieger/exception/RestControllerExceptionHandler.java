package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Controller class that catches all the custom Exceptions.
 * @author Irvan Sian Syah Putra
 *
 */
@ControllerAdvice
public class RestControllerExceptionHandler {

	/**
	 * The method that catches and handles the UnauthorizedException.
	 * @param exception UnauthorizedException to handle
	 * @return ResponseEntity with ApiResponse inside the HTTP Body.
	 */
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ApiResponse> resolveException(UnauthorizedException exception) {

		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * The method that catches and handles the ResourceNotFoundException.
	 * @param exception ResourceNotFoundException to handle
	 * @return ResponseEntity with ApiResponse inside the HTTP Body.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * The method that catches and handles the BadRequestException.
	 * @param exception BadRequestException to handle
	 * @return ResponseEntity with ApiResponse inside the HTTP Body.
	 */
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * The method that catches and handles the ForbiddenException.
	 * @param exception ForbiddenException to handle
	 * @return ResponseEntity with ApiResponse inside the HTTP Body.
	 */
	@ExceptionHandler(ForbiddenException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(ForbiddenException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}