package sieger.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	private final TournamentService tournamentService;
	
	private final TeamService teamService;
	
	private final InvitationService invitationService;

	public UserService(UserRepository userRepository, TournamentService tournamentService, TeamService teamService,
			InvitationService invitationService) {
		this.userRepository = userRepository;
		this.tournamentService = tournamentService;
		this.teamService = teamService;
		this.invitationService = invitationService;
	}
	
	public Optional<User> getUserByUsername(String username) {
		return null;
	}
	
	public Optional<User> getUserById(String userId) {
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
	
	public boolean createNewUser(User user) {
		return false;
	}
	
	public boolean deleteUser(String userId) {
		return false;
	}
	
	public boolean joinTeam(String userId, String teamName, String teamPassword) {
		return false;
	}
	
	public boolean quitTeam(String userId, String teamId) {
		return false;
	}
	
	public boolean updateUserDetail(String userId, String username, String surname, String forename) {
		return false;
	}
	
	public boolean updateUserById(String userId, User user) {
		return false;
	}
	
	
	
	
	

}
