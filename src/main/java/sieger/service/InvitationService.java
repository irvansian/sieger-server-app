package sieger.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Invitation;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.InvitationRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;
/**
 * The invitation servie class. 
 * The class will be called in controller and then called repository.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@Service
public class InvitationService {
	/**
	 * The invitation repository that connect to the database.
	 */
	@Qualifier("invitationDB")
	@Autowired
	private InvitationRepository invitationRepository;
	/**teamService
	 * The user repository that connect to the database.
	 */
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	/**
	 * The team repository that connect to the database.
	 */
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	/**
	 * The tournament repository that connect to the database.
	 */
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	/**
	 * Get the invitation by its id.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param invitationId The id of invitation.
	 * @return Return the optional invitation or throw exception when no permission.
	 * @throws ResourceNotFoundException When resource not exists in database.
	 * @throws ForbiddenException When user has no permission.
	 */
	public Optional<Invitation> getInvitation(String currentUserId, String invitationId) {
		Optional<Invitation> invitationOpt = invitationRepository
				.retrieveInvitationById(invitationId);
		if (invitationOpt.isEmpty()) {
			throw new ResourceNotFoundException("Invitation", "id", invitationId);
		}
		User user = userRepository.retrieveUserById(currentUserId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"User", "id", currentUserId));
		if(invitationOpt.get().getParticipantForm().equals(ParticipantForm.SINGLE)) {
			if (!invitationOpt.get().getRecipientId().equals(user.getUserId())) {
				ApiResponse response = new ApiResponse(false, "You can't view the <" 
						+ invitationId + "> invitation");
				throw new ForbiddenException(response);
			}
		}
		if(invitationOpt.get().getParticipantForm().equals(ParticipantForm.TEAM)){
			Team team = teamRepository.retrieveTeamById(invitationOpt.get().getRecipientId()).get();
			if(!user.getUserId().equals(team.getAdminId())) {
				ApiResponse response = new ApiResponse(false, "You can't view the <" 
						+ invitationId + "> invitation");
				throw new ForbiddenException(response);
			}
		}
		
	
 		return invitationOpt; 
	}
	/**
	 * Create new invitation and stored in database.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param invitation The invitation to be stored.
	 * @return Return the invitation after creation.
	 */
	public Invitation createInvitation(String currentUserId, Invitation invitation) {
		if (invitation.getParticipantForm().equals(ParticipantForm.SINGLE)) {
			User recipient = userRepository.retrieveUserById(invitation
					.getRecipientId()).get();
			recipient.addInvitation(invitation.getInvitationId());
			userRepository.updateUserById(recipient.getUserId(), recipient);
		} else {
			Team recipient = teamRepository.retrieveTeamById(invitation
					.getRecipientId()).get();
			recipient.addInvitation(invitation.getInvitationId());
			teamRepository.updateTeam(recipient.getTeamId(), recipient);
		}
		Invitation res = invitationRepository.createInvitation(invitation);
		return res;
	}
	/**
	 * Accept the invitation. Throw forbidden exception if no permission.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param invitationId The id of invitation.
	 * @return Return the api resonse in different situation.
	 * @throws ForbiddenException When user has no permission.
	 */
	public ApiResponse acceptInvitation(String currentUserId, String invitationId) {
		Invitation invitation = getInvitation(currentUserId, invitationId).get();
		Tournament tournament = tournamentRepository.retrieveTournamentById(invitation
				.getTournamentId()).get();
		if (invitation.getParticipantForm().equals(ParticipantForm.SINGLE)) {
			User user = userRepository.retrieveUserById(invitation.getRecipientId()).get();
			user.joinTournament(tournament);
			user.removeInvitation(invitation.getInvitationId());
			userRepository.updateUserById(user.getUserId(), user);
		} else {
			Team team = teamRepository.retrieveTeamById(invitation.getRecipientId()).get();
			team.joinTournament(tournament);
			team.removeInvitation(invitation.getInvitationId());
			teamRepository.updateTeam(team.getTeamId(), team);
		}
		invitationRepository.deleteInvitation(invitation.getInvitationId());
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		ApiResponse res = new ApiResponse(true, "Successfully accepted the invitation");
		return res;
	}
	/**
	 * Decline the invitation and throw forbidden exception when no permission.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param invitationId The id of invitation.
	 * @return Return the api response in different situations.
	 * @throws ForbiddenException When user has no permission.
	 */
	public ApiResponse declineInvitation(String currentUserId, String invitationId) {
		Invitation invitation = getInvitation(currentUserId, invitationId).get();
		if (invitation.getParticipantForm().equals(ParticipantForm.SINGLE)) {
			User user = userRepository.retrieveUserById(invitation.getRecipientId()).get();
			user.removeInvitation(invitation.getInvitationId());
			userRepository.updateUserById(user.getUserId(), user);
		} else {
			Team team = teamRepository.retrieveTeamById(invitation.getRecipientId()).get();
			team.removeInvitation(invitation.getInvitationId());
			teamRepository.updateTeam(team.getTeamId(), team);
		}
		invitationRepository.deleteInvitation(invitation.getInvitationId());
		ApiResponse res = new ApiResponse(true, "Successfully declined the invitation");
		return res;
	}
	
	
}
