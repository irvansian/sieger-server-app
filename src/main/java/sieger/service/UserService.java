package sieger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	private TournamentService tournamentService;
	
	private TeamService teamService;
	
	private InvitationService invitationService;

	@Autowired
	public UserService(UserRepository userRepository, TournamentService tournamentService, TeamService teamService,
			InvitationService invitationService) {
		this.userRepository = userRepository;
		this.tournamentService = tournamentService;
		this.teamService = teamService;
		this.invitationService = invitationService;
	}
	
	public Optional<User> getUserByUsername(String username) {
		return userRepository.retrieveUserByUsername(username);
	}
	
	public Optional<User> getUserById(String userId) {
		return userRepository.retrieveUserById(userId);
	}
	
	public List<Tournament> getUserTournaments(String userId) {
		List<Tournament> userTournaments = new ArrayList<>();
		Optional<User> user = userRepository.retrieveUserById(userId);
		if(!user.isEmpty()) {
			for(String tournamentid: user.get().getTournamentList()) {
				userTournaments.add(tournamentService.getTournamentById(userId,tournamentid).get());
			}
		}
		return userTournaments;
	}
	
	public List<Team> getUserTeams(String userId) {
		List<Team> userTeams = new ArrayList<>();
		Optional<User> user = userRepository.retrieveUserById(userId);
		if(!user.isEmpty()) {
			for(String teamId: user.get().getTeamList()) {
				userTeams.add(teamService.getTeamById(teamId).get());
			}
		}
		return userTeams;
	}
	
	public List<Invitation> getUserInvitations(String currentUserId, String userId) {
		List<Invitation> userInvitations = new ArrayList<>();
		Optional<User> user = userRepository.retrieveUserById(userId);
		if(user.isPresent()) {
			for(String invitationId: user.get().getInvitationList()) {
				userInvitations.add(invitationService.getInvitation(currentUserId, 
						invitationId).get());
			}
		}
		return userInvitations;
	}
	
	public boolean createNewUser(User user) {
		return userRepository.createUser(user);
	}
	
	public boolean deleteUser(String userId) {
		return userRepository.deleteUser(userId);
	}
	
	public boolean updateUserDetail(String userId, String username, String surname, String forename) {
		Optional<User> user = getUserById(userId);
		if (user.isEmpty()) return false;
		user.get().setUsername(username);
		user.get().setForename(forename);
		user.get().setSurname(surname);
		return true;
	}
	
	public boolean updateUserById(String userId, User user) {
		Optional<User> retrievedUser = getUserById(userId);
		if (retrievedUser.isEmpty()) return false;
		userRepository.updateUserById(userId, user);
		return true;
	}	

}
