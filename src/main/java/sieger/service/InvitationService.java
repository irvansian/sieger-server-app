package sieger.service;

import java.util.Optional;

import sieger.model.Invitation;
import sieger.repository.InvitationRepository;

public class InvitationService {
	private InvitationRepository invitationRepository;
	private UserService userService;
	
	public InvitationService(InvitationRepository invitationRepository, UserService userService) {
		this.invitationRepository = invitationRepository;
		this.userService = userService;
	}
	
	public Optional<Invitation> getInvitation(String invitationId) {
		return null;
	}
	
	public boolean createInvitation(Invitation invitation) {
		return false;
	}
	
	public boolean acceptInvitation(String userId, String invitationId) {
		return false;
	}
	
	public boolean declineInvitation(String userId, String invitationId) {
		return false;
	}
	
	
	
	
}
