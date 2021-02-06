package sieger.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
			@PathVariable("username") String username) {
		Optional<User> user = userService.getUserByUsername(username);
		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping
	public ResponseEntity<User> getUserById(
			@RequestParam(name = "id") String userId) {
		Optional<User> user = userService.getUserById(userId);
		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping("/{username}/tournaments")
	public ResponseEntity<List<Tournament>> getUserTournaments(
			@PathVariable("username") String username) {
		List<Tournament> tournaments = userService.getUserTournaments(username);
		return ResponseEntity.ok(tournaments);
	}
	
	@GetMapping("/{username}/teams")
	public ResponseEntity<List<Team>> getUserTeams(
			@PathVariable("username") String username) {
		List<Team> teams = userService.getUserTeams(username);
		return ResponseEntity.ok(teams);
	}
	
	@GetMapping("/{username}/invitations")
	public ResponseEntity<List<Invitation>> getUserInvitations(
			@PathVariable("username") String username) {
		List<Invitation> invitations = userService.getUserInvitations(username);
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
