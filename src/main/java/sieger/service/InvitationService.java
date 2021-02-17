package sieger.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Invitation;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.InvitationRepository;
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
		if (!invitationOpt.get().getRecipientUsername().equals(user.getUsername())) {
			ApiResponse response = new ApiResponse(false, "You can't view the <" 
					+ invitationId + "> invitation");
			throw new ForbiddenException(response);
		}
 		return invitationOpt; 
	}
	
	public Invitation createInvitation(String currentUserId, Invitation invitation) {
		User user = userRepository.retrieveUserById(currentUserId).get();
		if (!user.getUsername().equals(invitation.getSenderUsername())) {
			ApiResponse response = new ApiResponse(false, "You can't create an "
					+ "invitation for other people.");
			throw new ForbiddenException(response);
		}
		Optional<User> receive = userRepository.retrieveUserByUsername(invitation.getRecipientUsername());
		if(receive.isEmpty()) {
			ApiResponse response = new ApiResponse(false, "You can't send an "
					+ "invitation to him.");
			throw new ForbiddenException(response);
		}
		String invitationId = invitationRepository.createInvitation(invitation);
		receive.get().addInvitation(invitationId);
		userRepository.updateUserById(receive.get().getUserId(), receive.get());
		return invitation;
	}
	
	public ApiResponse acceptInvitation(String currentUserId, String invitationId) {
		Invitation invitation = getInvitation(currentUserId, invitationId).get();
		User user = userRepository.retrieveUserById(currentUserId).get();

		user.removeInvitation(invitationId);
		Tournament tournament = tournamentRepository.retrieveTournamentById(invitation.getTournamentId()).get();
		user.joinTournament(tournament);
		invitationRepository.deleteInvitation(invitationId);
		userRepository.updateUserById(currentUserId, user);
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		ApiResponse res = new ApiResponse(true, "Successfully accepted the invitation");
		return res;
	}
	
	public ApiResponse declineInvitation(String currentUserId, String invitationId) {
		User user = userRepository.retrieveUserById(currentUserId).get();
		user.removeInvitation(invitationId);
		invitationRepository.deleteInvitation(invitationId);
		userRepository.updateUserById(currentUserId, user);
		ApiResponse res = new ApiResponse(true, "Successfully declined the invitation");
		return res;
	}
	
	
}
