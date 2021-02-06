package sieger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Team getTeamByName(String teamName) {
		return null;
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
