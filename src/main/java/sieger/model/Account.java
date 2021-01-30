package sieger.model;

import java.util.UUID;

public class Account {
	//Id of account
	private String id;
	//email adress
	private String email;
	//user passwort
	private String password;
	//username
	private String username;
	//Id of user
	private String userId;
	
	//constructor
	public Account(String email, String password, String username) {
		this.email = email;
		this.password = password;
		this.username = username;
		this.id = randomId();
	}
	
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	
	//Set userid
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	//Set password
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	//Get Password
	public String getPassword() {
		return password;
	}
	
	//Get id
	public String getId() {
		return this.id;
	}
	
	//Get email
	public String getEmail() {
		return this.email;
	}
	
	//Get username
	public String getUsername() {
		return this.username;
	}
	
	//Get userId
	public String getUserId() {
		return this.userId;
	}
}
