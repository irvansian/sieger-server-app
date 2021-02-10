package sieger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.InvitationRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	
	@Autowired
	@Qualifier("invitationDB")
	private InvitationRepository invitationRepository;
	
	public User getUserByUsername(String currentUserId, String username) {
		User user = userRepository.retrieveUserByUsername(username)
				.orElseThrow(() -> 
				new ResourceNotFoundException("User", "username", username));
		
		if (!user.getUserId().equals(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to view the user.");
			throw new ForbiddenException(response);
		}
		return user;
	}
	
	public User getUserById(String currentUserId, String userToGetId) {
		User user = userRepository.retrieveUserById(userToGetId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", 
						userToGetId));
		
		if (!userToGetId.equals(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to view the user.");
			throw new ForbiddenException(response);
		}
		return user;
	}
	
	public List<Tournament> getUserTournaments(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		List<Tournament> tournaments = new ArrayList<Tournament>();
		for (String id : user.getTournamentList()) {
			tournaments.add(tournamentRepository
					.retrieveTournamentById(id).get());
		}
		return tournaments;
	}
	
	public List<Team> getUserTeams(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		List<Team> teams = new ArrayList<Team>();
		for (String id : user.getTeamList()) {
			teams.add(teamRepository
					.retrieveTeamById(id).get());
		}
		return teams;
	}
	
	public List<Invitation> getUserInvitations(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		List<Invitation> invitations = new ArrayList<Invitation>();
		for (String id : user.getTeamList()) {
			invitations.add(invitationRepository
					.retrieveInvitationById(id).get());
		}
		return invitations;
	}
	
	public User createNewUser(User user) {
		if (userRepository.retrieveUserByUsername(user.getUserName())
				.isPresent()) {
			ApiResponse response = new ApiResponse(false, "User with the username < " 
					+ user.getUserName() + "> already exist.");
			throw new BadRequestException(response);
		}
		userRepository.createUser(user);
		return user;
	}
	
	public ApiResponse deleteUser(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		userRepository.deleteUser(user.getUserId());
		ApiResponse res = new ApiResponse(true, "User <" + username + "> "
				+ "is successfully deleted");
		return res;
	}
	
	public User updateUserDetail(String currentUserId,
			String oldUsername,
			String newUsername, 
			String surname, String forename) {
		User user = getUserByUsername(currentUserId, oldUsername);
		user.setUserName(newUsername);
		user.setForename(forename);
		user.setSurname(surname);
		userRepository.updateUserById(user.getUserId(), user);
		return user;
	}	

}
