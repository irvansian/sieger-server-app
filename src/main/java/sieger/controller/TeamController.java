package sieger.controller;

import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.service.TeamService;

@RestController
@RequestMapping("teams")
public class TeamController {
	private TeamService teamService;
	
	@Autowired
	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}
	
	@GetMapping("/{teamName}")
	public ResponseEntity<Team> getTeamByName(
			@PathVariable("teamName") String teamName) {
		Optional<Team> team = 
				teamService.getTeamByName(teamName);
		if (team.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(team.get());
	}
	
	@GetMapping
	public ResponseEntity<Team> getTeamById(
			@RequestParam(name = "id") String teamId) {
		Optional<Team> team = teamService.getTeamById(teamId);
		if (team.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(team.get());
	}
	
	@PostMapping
	public ResponseEntity<String> createNewTeam(Team team) {
		if (teamService.createNewTeam(team)) {
			return ResponseEntity.ok(null);
		}
		
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}
	
	@DeleteMapping("/{teamName}")
	public ResponseEntity<String> deleteTeam(@PathVariable("teamName") String teamName) {
		if (teamService.deleteTeam(teamName)) {
			return ResponseEntity.ok(null);
		}
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{teamName}/members")
	public ResponseEntity<List<User>> getTeamMembers(
			@PathVariable("teamName") String teamName) {
		List<User> members = teamService.getTeamMembers(teamName);
		return ResponseEntity.ok(members);
	}
	
	@GetMapping("/{teamName}/tournaments")
	public ResponseEntity<List<Tournament>> getTeamTournaments(
			@PathVariable("teamName") String teamName) {
		List<Tournament> tournaments = teamService.getTeamTournaments(teamName);
		return ResponseEntity.ok(tournaments);
	}
	
	@PutMapping("/{teamName}/members/{id}/kick")
	public ResponseEntity<String> kickTeamMember(String adminId, 
			@PathVariable("id") String userToKickId, 
			@PathVariable("teamName") String teamName) {
		if (teamService.kickTeamMembers(userToKickId, teamName)) {
			return ResponseEntity.ok(null);
		}
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_INTERNAL_SERVER_ERROR);
		
	}
	
	public void joinTeam(String username, String teamName, String password) {
		
	}
	
	public void quitTeam(String username, String teamId) {
		
	}
}
