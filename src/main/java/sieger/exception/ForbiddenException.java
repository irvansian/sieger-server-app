package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that will be thrown when the client is not allowed to do something
 * to a resource. 403 HTTP Status Code.
 * @author Irvan Sian Syah Putra
 *
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Api Response inside the HTTP Response body.
	 */
	private ApiResponse apiResponse;

	/**
	 * The message for client.
	 */
	private String message;

	/**
	 * Constructor for ForbiddenException
	 * @param apiResponse
	 */
	public ForbiddenException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	/**
	 * Constructor for ForbiddenException
	 * @param message Message for client
	 */
	public ForbiddenException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Contructor for ForbiddenException
	 * @param message Message for client
	 * @param cause The cause of the error
	 */
	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Getter for ApiResponse attribute.
	 * @return ApiResponse
	 */
	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	/**
	 * Setter for ApiResponse
	 * @param apiResponse ApiResponse to be packed inside the http response body.
	 */
	public void setApiResponse(ApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	/**
	 * Getter for message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}