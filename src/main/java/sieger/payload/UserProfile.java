package sieger.payload;

public class UserProfile {
	private String id;
	private String forename;
	private String surname;
	private String username;
	
	public UserProfile(String id, String forename, String surname, String username) {
		this.id = id;
		this.forename = forename;
		this.surname = surname;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}
