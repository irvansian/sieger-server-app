package sieger.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.payload.UserProfile;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

@Service
public class TeamService {
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	
	public Team getTeamByName(String currentUserId, 
			String teamName) {
		Team team = teamRepository.retrieveTeamByName(teamName)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Team", "name", teamName));
		if (!team.getMemberList().contains(currentUserId)) {
			ApiResponse res = new ApiResponse(false, "You have no permission to "
					+ "view team <" + teamName + ">");
			throw new ForbiddenException(res);
		}
		return team;
	}
	
	public Team getTeamById(String currentUserId, String teamId) {
		Team team = teamRepository.retrieveTeamById(teamId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Team", "id", teamId));
		if (!team.getMemberList().contains(currentUserId)) {
			ApiResponse res = new ApiResponse(false, "You have no permission to "
					+ "view team with ID <" + teamId + ">");
			throw new ForbiddenException(res);
		}
		return team;
	}
	
	public Team createNewTeam(Team team) {
		if (teamRepository.retrieveTeamByName(team.getTeamName()).isPresent()) {
			ApiResponse response = new ApiResponse(false, "Team with the name <" 
					+ team.getTeamName() + "> already exist.");
			throw new BadRequestException(response);
		}
		teamRepository.createTeam(team);
		return team;
	}
	
	public ApiResponse deleteTeam(String currentUserId, String teamName) {
		Team team = getTeamByName(currentUserId, teamName);
		if (!team.getAdminId().equals(currentUserId)) {
			ApiResponse res = new ApiResponse(false, "You have no permission to "
					+ "delete team <" + teamName + ">");
			throw new ForbiddenException(res);
		}
		teamRepository.deleteTeam(teamName);
		return new ApiResponse(true, "You deleted the team <" + teamName + ">");	
	}
	
	public List<UserProfile> getTeamMembers(String currentUserId, String teamName) {
		List<UserProfile> members = new ArrayList<UserProfile>();
		Team team = getTeamByName(currentUserId, teamName);
		for (String id : team.getMemberList()) {
			User user = userRepository.retrieveUserById(id).get();
			UserProfile up = new UserProfile(user.getUserId(),
					user.getForename(),
					user.getSurname(),
					user.getUserName());
			members.add(up);
		}
		return members;
	}
	
	public List<Tournament> getTeamTournaments(String currentUserId, 
			String teamName) {
		List<Tournament> teamTournament = new ArrayList<Tournament>();
		Team team = getTeamByName(currentUserId, teamName);
		for(String id: team.getTournamentList()) {
			teamTournament.add(tournamentRepository.retrieveTournamentById(id).get());
		}
		return teamTournament;
	}
	
	public ApiResponse kickTeamMembers(String currentUserId, 
			String userToKickId, String teamName) {
		Team team = getTeamByName(currentUserId, teamName);
		User userToKick = userRepository.retrieveUserById(userToKickId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("User", "id", userToKickId));
		if (!team.getAdminId().equals(currentUserId)) {
			ApiResponse res = new ApiResponse(false, "You have no permission to "
					+ "kick a member.");
			throw new ForbiddenException(res);
		}
		team.kickMember(userToKick);
		teamRepository.updateTeam(team.getTeamId(), team);
		userRepository.updateUserById(userToKick.getUserId(), userToKick);
		ApiResponse res = new ApiResponse(true, "You kicked a member with ID <" 
		+ userToKickId + ">");
		return res;
	}
	
	public ApiResponse joinTeam(String currentUserId, String teamName, 
			String teamPassword) {
		User user = userRepository.retrieveUserById(currentUserId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"User", "id", currentUserId));
		Team team = teamRepository.retrieveTeamByName(teamName)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Team", "name", teamName));
		
		boolean success = user.joinTeam(team, teamPassword);
		
		if (!success) {
			ApiResponse response = new ApiResponse(false, "Failed to join the team.");
			throw new BadRequestException(response);
		}
		userRepository.updateUserById(currentUserId, user);
		teamRepository.updateTeam(team.getTeamId(), team);
		ApiResponse res = new ApiResponse(true, "Successfully joined the team");
		return res;
	}
	
	public ApiResponse quitTeam(String currentUserId, String teamName) {
		Team team = getTeamByName(currentUserId,teamName);
		User user = userRepository.retrieveUserById(currentUserId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"User", "id", currentUserId));
		
		boolean success = user.quitTeam(team);
		
		if (!success) {
			ApiResponse response = new ApiResponse(false, "Failed to quit the team.");
			throw new BadRequestException(response);
		}
		userRepository.updateUserById(currentUserId, user);
		teamRepository.updateTeam(team.getTeamId(), team);
		ApiResponse res = new ApiResponse(true, "Successfully quit the team");
		return res;
	}
	
}
