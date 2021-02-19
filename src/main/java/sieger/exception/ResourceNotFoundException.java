package sieger.exception;

import sieger.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that will be thrown when the client tries to access
 * a resource that doesn't exist. 404 HTTP Status Code.
 * @author Irvan Sian Syah Putra
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ApiResponse that will be packed inside the HTTP Response Body.
	 */
	private ApiResponse apiResponse;

	/**
	 * The name of the resource that the client wants to access.
	 */
	private String resourceName;
	
	/**
	 * The identifier of a resource with which the client tries to access the resource.
	 */
	private String fieldName;
	
	/**
	 * The value of the identifier that the client use to access a resource.
	 */
	private Object fieldValue;

	/**
	 * Construtor for ResourceNotFoundException
	 * @param resourceName Name of resource the client tries to access.
	 * @param fieldName Identifier of a resource with which the client tries to access the resource.
	 * @param fieldValue The value of the identifier that the client use to access a resource.
	 */
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super();
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		setApiResponse();
	}

	/**
	 * Getter for resourceName.
	 * @return resource name.
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Getter for fieldName.
	 * @return fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Getter for fieldValue
	 * @return fieldValue.
	 */
	public Object getFieldValue() {
		return fieldValue;
	}

	/**
	 * Getter for apiResponse.
	 * @return apiResponse.
	 */
	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	/**
	 * Private setter to format the ApiResponse message.
	 */
	private void setApiResponse() {
		String message = resourceName + " not found with " + fieldName + " : " 
				+ fieldValue;

		apiResponse = new ApiResponse(Boolean.FALSE, message);
	}
}