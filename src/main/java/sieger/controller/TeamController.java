package sieger.controller;

import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Team;
import sieger.model.Tournament;
import sieger.payload.ApiResponse;
import sieger.payload.UserProfile;
import sieger.service.TeamService;
import sieger.service.UserService;

@RestController
@RequestMapping("teams")
public class TeamController {
	private TeamService teamService;
	
	@Autowired
	public TeamController(TeamService teamService, UserService userService) {
		this.teamService = teamService;
	}
	
	@GetMapping("/{teamName}")
	public ResponseEntity<Team> getTeamByName(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		Team team = 
				teamService.getTeamByName(currentUserId,teamName);
		return ResponseEntity.ok(team);
	}
	
	@GetMapping
	public ResponseEntity<Team> getTeamById(
			@RequestParam(name = "id") String teamId,
			@RequestAttribute("currentUserId") String currentUserId) {
		Team team = teamService.getTeamById(currentUserId, teamId);
		return ResponseEntity.ok(team);
	}
	
	@PostMapping
	public ResponseEntity<Team> createNewTeam(@RequestBody Team team,
			@RequestAttribute("currentUserId") String currentUserId) {
		Team res = teamService.createNewTeam(team);
		return ResponseEntity.ok(res);
	}
	
	@DeleteMapping("/{teamName}")
	public ResponseEntity<ApiResponse> deleteTeam(@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = teamService.deleteTeam(currentUserId, teamName);
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/{teamName}/members")
	public ResponseEntity<List<UserProfile>> getTeamMembers(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<UserProfile> members = teamService.getTeamMembers(currentUserId, teamName);
		return ResponseEntity.ok(members);
	}
	
	@GetMapping("/{teamName}/tournaments")
	public ResponseEntity<List<Tournament>> getTeamTournaments(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Tournament> tournaments = teamService.getTeamTournaments(currentUserId, 
				teamName);
		return ResponseEntity.ok(tournaments);
	}
	
	@DeleteMapping("/{teamName}/members/{id}")
	public ResponseEntity<ApiResponse> kickTeamMember(String adminId, 
			@PathVariable("id") String userToKickId, 
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = teamService.kickTeamMembers(currentUserId, 
				userToKickId, teamName);
		return ResponseEntity.ok(res);
		
	}
	
	@PostMapping("/{teamName}")
	public ResponseEntity<ApiResponse> handleMembership(
			@RequestAttribute("currentUserId") String currentUserId, 
			@PathVariable("teamName") String teamName, 
			@RequestBody Map<String, String> payload) {
		ApiResponse res = null;
		if (payload.get("activity").equals("join")) {
			String password = payload.get("password");
			res = teamService.joinTeam(currentUserId, teamName, password);
		} else if (payload.get("activity").equals("quit")) {
			res = teamService.quitTeam(currentUserId, teamName);
		}
		
		return ResponseEntity.ok(res);
	}
}
