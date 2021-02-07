package sieger.service;

import java.util.Optional;

import sieger.model.Invitation;
import sieger.model.User;
import sieger.repository.InvitationRepository;

public class InvitationService {
	private InvitationRepository invitationRepository;
	private UserService userService;
	
	public InvitationService(InvitationRepository invitationRepository, UserService userService) {
		this.invitationRepository = invitationRepository;
		this.userService = userService;
	}
	
	public Optional<Invitation> getInvitation(String invitationId) {
		Optional<Invitation> invitationOpt = invitationRepository
				.retrieveInvitationById(invitationId);
		if (invitationOpt.isEmpty()) {
			// throw resource not found exception
		}
 		return invitationOpt; 
	}
	
	public boolean createInvitation(String currentUserId, Invitation invitation) {
		User user = userService.getUserById(currentUserId).get();
		if (!user.getUserName().equals(invitation.getSenderUsername())) {
			// throw forbidden exception
		}
		invitationRepository.createInvitation(invitation);
		return true;
	}
	
	public boolean acceptInvitation(String userId, String invitationId) {
		return false;
	}
	
	public boolean declineInvitation(String userId, String invitationId) {
		return false;
	}
	
	
	
	
}
