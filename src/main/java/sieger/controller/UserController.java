package sieger.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
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


public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	public User getUserByUsername(String username) {
		return null;
	}
	
	public User getUserById(String userId) {
		return null;
	}
	
	public List<Tournament> getUserTournaments(String userId) {
		return null;
	}
	
	public List<Team> getUserTeams(String userId) {
		return null;
	}
	
	public List<Invitation> getUserInvitations(String userId) {
		return null;
	}
	
	public void joinTeam(String username, String teamName, String password) {
		
	}
	
	public void quitTeam(String username, String teamId) {
		
	}
	
	public void updateUserDetail(String userId, String forename, String surname) {
		
	}
	
	
}
