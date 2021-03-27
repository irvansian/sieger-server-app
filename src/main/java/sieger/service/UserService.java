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
import sieger.payload.InvitationDTO;
import sieger.repository.InvitationRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;
/**
 * The user servie class. 
 * The class will be called in controller and then called repository.
 * 
 * @author Irvan Sian Syah Putra, Chen Zhang
 *
 */
@Service
public class UserService {
	/**
	 * The user repository that connect to the database.
	 */
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	/**
	 * The tournament repository that connect to the database.
	 */
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	/**
	 * The team repository that connect to the database.
	 */
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	/**
	 * The invitation repository that connect to the database.
	 */
	@Autowired
	@Qualifier("invitationDB")
	private InvitationRepository invitationRepository;
	
	@Autowired
	private InvitationService invitationService;
	/**
	 * Get user from database by username
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param username The user name.
	 * @return Return the result as user object.
	 * @throws ResourceNotFoundException When user not exists in database.
	 * @throws ForbiddenException When user has no permission.
	 */
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
	/**
	 * Get user from database by userid.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param userToGetId The id of user, which will be got from database.
	 * @return Return the result as user object.
	 * @throws ForbiddenException When user has no permission.
	 */
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
	/**
	 * Get the tournament list of user.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param username The username.
	 * @return Return the tournament objects in list.
	 */
	public List<Tournament> getUserTournaments(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		List<Tournament> tournaments = new ArrayList<Tournament>();
		for (String id : user.getTournamentList()) {
			tournaments.add(tournamentRepository
					.retrieveTournamentById(id).get());
		}
		return tournaments;
	}
	/**
	 * Get the team list of user.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param username The username.
	 * @return Return the team objects in list.
	 */
	public List<Team> getUserTeams(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		List<Team> teams = new ArrayList<Team>();
		for (String id : user.getTeamList()) {
			teams.add(teamRepository
					.retrieveTeamById(id).get());
		}
		return teams;
	}
	/**
	 * Get the invitation list of user.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param username The username.
	 * @return Return the invitation objects in list.
	 */
	public List<InvitationDTO> getUserInvitations(String currentUserId, String username) {
		User user = getUserByUsername(currentUserId, username);
		List<Invitation> invitations = new ArrayList<Invitation>();
		for (String id : user.getInvitationList()) {
			invitations.add(invitationRepository
					.retrieveInvitationById(id).get());
		}
		return invitationService.convertToInvitationDTOList(invitations);
	}
	/**
	 * Create new user. 
	 * Creation failed if a user with given name exists.
	 * 
	 * @param user User to be created and will be stored in databade.
	 * @return Return the user object after creation.
	 * @throws BadRequestException When user exists with the name.
	 */
	public User createNewUser(User user) {
		if (userRepository.retrieveUserByUsername(user.getUsername())
				.isPresent()) {
			ApiResponse response = new ApiResponse(false, "User with the username < " 
					+ user.getUsername() + "> already exist.");
			throw new BadRequestException(response);
		}
		userRepository.createUser(user);
		return user;
	}

	/**
	 * Update user detail.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param oldUsername Old username of user.
	 * @param newUsername New username of user. Updating failed if new username exists.
	 * @param surname Surname of user.
	 * @param forename Forename of user.
	 * @return Return user object if successfully updated.
	 * @throws BadRequestException When user exists with the name.
	 */
	public User updateUserDetail(String currentUserId,
			String oldUsername,
			String newUsername, 
			String surname, String forename) {
		User user = getUserByUsername(currentUserId, oldUsername);
		if (userRepository.retrieveUserByUsername(newUsername)
				.isPresent()) {
			ApiResponse response = new ApiResponse(false, "User with the username < " 
					+ user.getUsername() + "> already exist.");
			throw new BadRequestException(response);
		}
		user.setUsername(newUsername);
		user.setForename(forename);
		user.setSurname(surname);
		userRepository.updateUserById(user.getUserId(), user);
		return user;
	}	

}
