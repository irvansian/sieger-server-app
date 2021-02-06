package sieger.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	public Team getTeamById(String teamId) {
		return null;
	}
	
	public void createNewTeam(Team team) {
		
	}
	
	public void deleteTeam(String teamId) {
		
	}
	
	public List<User> getTeamMembers(String teamId) {
		return null;
	}
	
	public List<Tournament> getTeamTournaments(String teamId) {
		return null;
	}
	
	public void kickTeamMember(String adminId, String userToKickId, String teamId) {
		
	}
}
