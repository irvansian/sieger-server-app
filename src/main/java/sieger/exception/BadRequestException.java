package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that will be thrown when the client inputs a bad request.
 * Returns a 400 HTTP Status Code.
 * @author Irvan Sian Syah Putra
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * This will be packed inside the HTTP Response body.
	 */
	private ApiResponse apiResponse;

	/**
	 * Constructor for BadRequestException.
	 * @param apiResponse the response to client, client will know if the request
	 * was successful or not, a message will also be attached to it.
	 */
	public BadRequestException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	/**
	 * Constructor for BadRequestException.
	 * @param message of why the request was bad.
	 */
	public BadRequestException(String message) {
		super(message);
	}

	/**
	 * Constructor for BadRequestException.
	 * @param message message for client
	 * @param cause the reason why the request was bad
	 */
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Getter for ApiResponse.
	 * @return apiResponse
	 */
	public ApiResponse getApiResponse() {
		return apiResponse;
	}
}