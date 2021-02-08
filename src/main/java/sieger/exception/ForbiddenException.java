package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private ApiResponse apiResponse;

	private String message;

	public ForbiddenException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public ForbiddenException(String message) {
		super(message);
		this.message = message;
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(ApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}