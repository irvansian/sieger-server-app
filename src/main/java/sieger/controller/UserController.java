package sieger.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		Optional<User> user = userService.getUserByUsername(username);
		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get());
	}
	
	public ResponseEntity<User> getUserById(String userId) {
		return null;
	}
	
	public ResponseEntity<List<Tournament>> getUserTournaments(String userId) {
		return null;
	}
	
	public ResponseEntity<List<Team>> getUserTeams(String userId) {
		return null;
	}
	
	public ResponseEntity<List<Invitation>> getUserInvitations(String userId) {
		return null;
	}
	
	public void joinTeam(String username, String teamName, String password) {
		
	}
	
	public void quitTeam(String username, String teamId) {
		
	}
	
	public void updateUserDetail(String userId, String forename, String surname) {
		
	}
	
	
}
