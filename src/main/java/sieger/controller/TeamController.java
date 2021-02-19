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

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.payload.ApiResponse;
import sieger.payload.UserProfile;
import sieger.service.TeamService;
/**
 * The team controller class, which handles the request from client.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@RestController
@RequestMapping("teams")
public class TeamController {
	/**
	 * The team service, which connects controller and repository.
	 */
	private TeamService teamService;
	/**
	 * Constructor of team controller.
	 * 
	 * @param teamService The team service class.
	 */
	@Autowired
	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}
	/**
	 * Get request from client.To get the team from database with name.
	 * 
	 * @param teamName The name of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with team details.
	 */
	@GetMapping("/{teamName}")
	public ResponseEntity<Team> getTeamByName(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		Team team = 
				teamService.getTeamByName(currentUserId,teamName);
		return ResponseEntity.ok(team);
	}
	/**
	 * Get request from client.To get the team from database with id.
	 * 
	 * @param teamId The id of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with team details.
	 */
	@GetMapping
	public ResponseEntity<Team> getTeamById(
			@RequestParam(name = "id") String teamId,
			@RequestAttribute("currentUserId") String currentUserId) {
		Team team = teamService.getTeamById(currentUserId, teamId);
		return ResponseEntity.ok(team);
	}
	/**
	 * Post request from client.To create new team.
	 * 
	 * @param team The team as Json object that needs to be stored in database.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with team details.
	 */
	@PostMapping
	public ResponseEntity<Team> createNewTeam(@RequestBody Team team,
			@RequestAttribute("currentUserId") String currentUserId) {
		Team res = teamService.createNewTeam(team);
		return ResponseEntity.ok(res);
	}
	/**
	 * Delete request from client.To delete team with its name.
	 * 
	 * @param teamName The name of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with message.
	 */
	@DeleteMapping("/{teamName}")
	public ResponseEntity<ApiResponse> deleteTeam(@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = teamService.deleteTeam(currentUserId, teamName);
		return ResponseEntity.ok(res);
	}
	/**
	 * Get request from client.To get team members from database with team name.
	 * 
	 * @param teamName The name of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with member lsit.
	 */
	@GetMapping("/{teamName}/members")
	public ResponseEntity<List<UserProfile>> getTeamMembers(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<UserProfile> members = teamService.getTeamMembers(currentUserId, teamName);
		return ResponseEntity.ok(members);
	}
	/**
	 * Get request from client.To get tournament list from database with team name.
	 * 
	 * @param teamName The name of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with tournament lsit.
	 */
	@GetMapping("/{teamName}/tournaments")
	public ResponseEntity<List<Tournament>> getTeamTournaments(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Tournament> tournaments = teamService.getTeamTournaments(currentUserId, 
				teamName);
		return ResponseEntity.ok(tournaments);
	}
	/**
	 * Get request from client.To get invitation list of team with team name.
	 * 
	 * @param teamName The name of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with invitation lsit.
	 */
	@GetMapping("/{teamName}/invitations")
	public ResponseEntity<List<Invitation>> getTeamInvitations(
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Invitation> invitations = teamService.getTeamInvitations(currentUserId, 
				teamName);
		return ResponseEntity.ok(invitations);
	}
	/**
	 * Delete request from client.To kick member from a team.
	 * 
	 * @param adminId The id of team admin.
	 * @param userToKickId The id of user, who will be kicked from team.
	 * @param teamName The name of team.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return 
	 */
	@DeleteMapping("/{teamName}/members/{id}")
	public ResponseEntity<ApiResponse> kickTeamMember(String adminId, 
			@PathVariable("id") String userToKickId, 
			@PathVariable("teamName") String teamName,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = teamService.kickTeamMembers(currentUserId, 
				userToKickId, teamName);
		return ResponseEntity.ok(res);
		
	}
	/**
	 * Post request from client.To join or quit the team.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param teamName The name of team.
	 * @param payload The activity of user,it can be join or quit.
	 * @return Return the 200Ok response with message.
	 */
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
