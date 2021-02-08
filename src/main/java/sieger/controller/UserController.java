package sieger.controller;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.service.InvitationService;
import sieger.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	private final UserService userService;
	private InvitationService invitationService;
	
	@Autowired
	public UserController(UserService userService, InvitationService invitationService) {
		this.userService = userService;
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(
			@PathVariable("username") String username,
			String currentUserId) {
		User user = userService.getUserByUsername(currentUserId, username);

		return ResponseEntity.ok(user);
	}
	
	@GetMapping
	public ResponseEntity<User> getUserById(
			@RequestParam(name = "id") String userToGetId,
			String currentUserId) {
		User user = userService.getUserById(currentUserId, userToGetId);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{username}/tournaments")
	public ResponseEntity<List<Tournament>> getUserTournaments(
			@PathVariable("username") String username,
			String currentUserId) {
		List<Tournament> tournaments = userService
				.getUserTournaments(currentUserId, username);
		return ResponseEntity.ok(tournaments);
	}
	
	@GetMapping("/{username}/teams")
	public ResponseEntity<List<Team>> getUserTeams(
			@PathVariable("username") String username,
			String currentUserId) {
		List<Team> teams = userService.getUserTeams(currentUserId, username);
		return ResponseEntity.ok(teams);
	}
	
	@GetMapping("/{username}/invitations")
	public ResponseEntity<List<Invitation>> getUserInvitations(
			@PathVariable("username") String username,
			String currentUserId) {
		List<Invitation> invitations = userService
				.getUserInvitations(currentUserId, username);
		return ResponseEntity.ok(invitations);
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<String> updateUserDetail(@PathVariable("username") String oldUsername, 
			@RequestBody Map<String, String> userDetail) {
		String newUsername = userDetail.get("username");
		String forename = userDetail.get("forename");
		String surname = userDetail.get("surname");
		userService.updateUserDetail(oldUsername, newUsername, surname, forename);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/{username}/invitations/{id}")
	public ResponseEntity<String> handleInvitationAcceptation(
			String currentUserId, 
			@PathVariable("id") String invitationId,
			@RequestBody Map<String, Boolean> acceptation) {
		boolean acceptationVal = acceptation.get("accept").booleanValue();
		if (acceptationVal == true) {
			invitationService.acceptInvitation(currentUserId, invitationId);
		} else if (acceptationVal == false) {
			invitationService.declineInvitation(currentUserId, invitationId);
		}
		return ResponseEntity.ok(null);
	}
	
	
}
