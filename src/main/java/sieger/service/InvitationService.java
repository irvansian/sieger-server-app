package sieger.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Invitation;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.InvitationRepository;

@Service
public class InvitationService {
	private InvitationRepository invitationRepository;
	private UserService userService;
	
	@Autowired
	public InvitationService(
			@Qualifier("invitationDB") InvitationRepository invitationRepository, 
			UserService userService) {
		this.invitationRepository = invitationRepository;
		this.userService = userService;
	}
	
	public Optional<Invitation> getInvitation(String currentUserId, String invitationId) {
		Optional<Invitation> invitationOpt = invitationRepository
				.retrieveInvitationById(invitationId);
		if (invitationOpt.isEmpty()) {
			throw new ResourceNotFoundException("Invitation", "id", invitationId);
		}
		User user = userService.getUserById(currentUserId).get();
		if (!invitationOpt.get().getRecipientUsername().equals(user.getUserName())) {
			ApiResponse response = new ApiResponse(false, "You can't view the <" 
					+ invitationId + "> invitation");
			throw new ForbiddenException(response);
		}
 		return invitationOpt; 
	}
	
	public boolean createInvitation(String currentUserId, Invitation invitation) {
		User user = userService.getUserById(currentUserId).get();
		if (!user.getUserName().equals(invitation.getSenderUsername())) {
			ApiResponse response = new ApiResponse(false, "You can't create an "
					+ "invitation for other people.");
			throw new ForbiddenException(response);
		}
		invitationRepository.createInvitation(invitation);
		return true;
	}
	
	public boolean acceptInvitation(String currentUserId, String invitationId) {
		Invitation invitation = getInvitation(currentUserId, invitationId).get();
		User user = userService.getUserById(currentUserId).get();

		user.removeInvitation(invitationId);
		user.addTournament(invitation.getTournamentId());
		invitationRepository.deleteInvitation(invitationId);
		userService.updateUserById(currentUserId, user);
		return true;
	}
	
	public boolean declineInvitation(String currentUserId, String invitationId) {
		User user = userService.getUserById(currentUserId).get();
		user.removeInvitation(invitationId);
		invitationRepository.deleteInvitation(invitationId);
		userService.updateUserById(currentUserId, user);
		return true;
	}
	
	
}
