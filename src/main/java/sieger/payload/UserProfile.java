package sieger.payload;

/**
 * When a user A tries to access user B resource, the user B resource will be
 * converted to UserProfile, so that user A doesn't know private information of user B.
 * @author Irvan Sian Syah Putra
 *
 */
public class UserProfile {
	private String id;
	private String forename;
	private String surname;
	private String username;
	
	/**
	 * Constructor for UserProfile.
	 * @param id Id of the user
	 * @param forename First name of the user
	 * @param surname Last name of the user
	 * @param username Username of the user
	 */
	public UserProfile(String id, String forename, String surname, String username) {
		this.id = id;
		this.forename = forename;
		this.surname = surname;
		this.username = username;
	}

	/**
	 * Getter for Id
	 * @return Id attribute
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for Id
	 * @param id Id to be passed
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for forename attirbute.
	 * @return forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * Setter for forename attribute.
	 * @param forename Forename to be passed
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * Getter for surname.
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Setter for surname
	 * @param surname Surname to be passed
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Getter for username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for username
	 * @param username Username to be passed.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}
