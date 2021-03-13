package sieger.controller;

import java.util.List;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.service.UserService;
/**
 * The user controller class, which handles the request from client.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@RestController
@RequestMapping("users")
public class UserController {
	/**
	 * The user service, which connects controller and repository.
	 */
	private UserService userService;
	/**
	 * Constructor of user controller.
	 * 
	 * @param userService The user service.
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	/**
	 * Post request from client.To store a new user in database.
	 * 
	 * @param user The json object which contains the user.
	 * @return Return 200OK response with user.
	 */
	@PostMapping
	public ResponseEntity<User> createNewUser(@RequestBody User user) {
		User resUser = userService.createNewUser(user);
		System.out.println("id " + user.getUserId());
		return ResponseEntity.ok(resUser);
	}
	/**
	 * Get request from client.To get user from database with username.
	 * 
	 * @param username The name of user.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with user.
	 */
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(
			@PathVariable("username") String username,
			@RequestAttribute("currentUserId") String currentUserId) {
		User user = userService.getUserByUsername(currentUserId, username);

		return ResponseEntity.ok(user);
	}
	/**
	 * Get request from client.To get user from database with id.
	 * 
	 * @param userToGetId The id of user.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with user.
	 */
	@GetMapping
	public ResponseEntity<User> getUserById(
			@RequestParam(name = "id") String userToGetId,
			@RequestAttribute("currentUserId") String currentUserId) {
		User user = userService.getUserById(currentUserId, userToGetId);
		return ResponseEntity.ok(user);
	}
	/**
	 * Get request from client.To get tournament list from database with username.
	 * 
	 * @param username The name of user.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with tournament list.
	 */
	@GetMapping("/{username}/tournaments")
	public ResponseEntity<List<Tournament>> getUserTournaments(
			@PathVariable("username") String username,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Tournament> tournaments = userService
				.getUserTournaments(currentUserId, username);
		return ResponseEntity.ok(tournaments);
	}
	/**
	 * Get request from client.To get teams list from database with username.
	 * 
	 * @param username The name of user.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with team list.
	 */
	@GetMapping("/{username}/teams")
	public ResponseEntity<List<Team>> getUserTeams(
			@PathVariable("username") String username,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Team> teams = userService.getUserTeams(currentUserId, username);
		return ResponseEntity.ok(teams);
	}
	/**
	 * Get request from client.To get invitation list from database with username.
	 * 
	 * @param username The name of user.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with invitation list.
	 */
	@GetMapping("/{username}/invitations")
	public ResponseEntity<List<Invitation>> getUserInvitations(
			@PathVariable("username") String username,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Invitation> invitations = userService
				.getUserInvitations(currentUserId, username);
		return ResponseEntity.ok(invitations);
	}
	/**
	 * Put request from client.To update the user data.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param oldUsername Old username.
	 * @param userDetail New user data.
	 * @return Return 200Ok response.
	 */
	@PutMapping("/{username}")
	public ResponseEntity<User> updateUserDetail(
			@RequestAttribute("currentUserId") String currentUserId,
			@PathVariable("username") String oldUsername, 
			@RequestBody Map<String, String> userDetail) {
		String newUsername = userDetail.get("username");
		String forename = userDetail.get("forename");
		String surname = userDetail.get("surname");
		User user = userService.updateUserDetail(currentUserId, oldUsername, newUsername, 
				surname, forename);
		return ResponseEntity.ok(user);
	}
	
	
}
