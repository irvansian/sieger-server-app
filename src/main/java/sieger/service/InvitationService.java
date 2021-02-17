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

@Service
public class InvitationService {
	@Qualifier("invitationDB")
	@Autowired
	private InvitationRepository invitationRepository;
	
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	
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
	
	public ApiResponse acceptInvitation(String currentUserId, String invitationId) {
		Invitation invitation = getInvitation(currentUserId, invitationId).get();
		Tournament tournament = tournamentRepository.retrieveTournamentById(invitation
				.getTournamentId()).get();
		if (invitation.getParticipantForm().equals(ParticipantForm.SINGLE)) {
			User user = userRepository.retrieveUserById(invitation.getRecipientId()).get();
			if (!currentUserId.equals(user.getUserId())) {
				ApiResponse res = new ApiResponse(false, "You have no permission to "
						+ "accept the invitation");
				throw new ForbiddenException(res);
			}
			user.joinTournament(tournament);
			user.removeInvitation(invitation.getInvitationId());
			userRepository.updateUserById(user.getUserId(), user);
		} else {
			Team team = teamRepository.retrieveTeamById(invitation.getRecipientId()).get();
			if (!team.getAdminId().equals(currentUserId)) {
				ApiResponse res = new ApiResponse(false, "You have no permission to "
						+ "accept the invitation");
				throw new ForbiddenException(res);
			}
			team.joinTournament(tournament);
			team.removeInvitation(invitation.getInvitationId());
			teamRepository.updateTeam(team.getTeamId(), team);
		}
		invitationRepository.deleteInvitation(invitation.getInvitationId());
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		ApiResponse res = new ApiResponse(true, "Successfully accepted the invitation");
		return res;
	}
	
	public ApiResponse declineInvitation(String currentUserId, String invitationId) {
		Invitation invitation = getInvitation(currentUserId, invitationId).get();
		if (invitation.getParticipantForm().equals(ParticipantForm.SINGLE)) {
			User user = userRepository.retrieveUserById(invitation.getRecipientId()).get();
			if (!currentUserId.equals(user.getUserId())) {
				ApiResponse res = new ApiResponse(false, "You have no permission to "
						+ "decline the invitation");
				throw new ForbiddenException(res);
			}
			user.removeInvitation(invitation.getInvitationId());
			userRepository.updateUserById(user.getUserId(), user);
		} else {
			Team team = teamRepository.retrieveTeamById(invitation.getRecipientId()).get();
			if (!team.getAdminId().equals(currentUserId)) {
				ApiResponse res = new ApiResponse(false, "You have no permission to "
						+ "decline the invitation");
				throw new ForbiddenException(res);
			}
			team.removeInvitation(invitation.getInvitationId());
			teamRepository.updateTeam(team.getTeamId(), team);
		}
		invitationRepository.deleteInvitation(invitation.getInvitationId());
		ApiResponse res = new ApiResponse(true, "Successfully declined the invitation");
		return res;
	}
	
	
}
