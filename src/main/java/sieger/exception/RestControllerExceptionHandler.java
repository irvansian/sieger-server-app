package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ApiResponse> resolveException(UnauthorizedException exception) {

		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
}