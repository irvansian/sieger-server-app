package sieger.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * This object of the class is going to be packed inside the HTTP Response Body.
 * It contains the information if the request that the client sent was successful or not along
 * with the message.
 * @author Irvan Sian Syah Putra.
 *
 */
@Data
@JsonPropertyOrder({
		"success",
		"message"
})
public class ApiResponse implements Serializable {

	/**
	 * Serial version UID for serialization.
	 */
	@JsonIgnore
	private static final long serialVersionUID = 7702134516418120340L;

	/**
	 * Boolean of whether or not the request was successful.
	 */
	@JsonProperty("success")
	private Boolean success;

	/**
	 * Message for client.
	 */
	@JsonProperty("message")
	private String message;

	/**
	 * The Http Status for response.
	 */
	@JsonIgnore
	private HttpStatus status;

	/**
	 * Empty constructor for ApiResponse.
	 */
	public ApiResponse() {

	}

	/**
	 * Constructor for ApiResponse
	 * @param success Whether or not the request was successful
	 * @param message Message for client
	 */
	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * Constructor for ApiResponse
	 * @param success Whether or not the request was successful
	 * @param message Message for client
	 * @param httpStatus Status for HTTP Response
	 */
	public ApiResponse(Boolean success, String message, HttpStatus httpStatus) {
		this.success = success;
		this.message = message;
		this.status = httpStatus;
	}
	
	/**
	 * Get the success of response.
	 * 
	 * @return Return the success.
	 */
	public boolean getSuccess() {
		return this.success;
	}
	
	/**
	 * Get the message of response.
	 * 
	 * @return Return the message of response.
	 */
	public String getMessage() {
		return this.message;
	}
}