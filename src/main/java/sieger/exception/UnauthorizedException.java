package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Exception that will be thrown when client is not authorized to access a resource.
 * @author Irvan Sian Syah Putra
 *
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ApiResponse that will be packed inside the HTTP Response Body.
	 */
	private ApiResponse apiResponse;

	/**
	 * Message for client.
	 */
	private String message;

	/**
	 * Constructor for UnauthorizedException.
	 * @param apiResponse ApiResponse that will be packed inside the HTTP Response Body.
	 */
	public UnauthorizedException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	/**
	 * Constructor for UnauthorizedException.
	 * @param message Message for client.
	 */
	public UnauthorizedException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Constructor for UnauthorizedException
	 * @param message Message for client.
	 * @param cause Cause of the error.
	 */
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Getter for ApiResponse.
	 * @return ApiResponse attribute.
	 */
	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	/**
	 * Setter for ApiResponse
	 * @param apiResponse ApiResponse to replace the current ApiResponse.
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
	 * Setter for message
	 * @param message Message for client.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}