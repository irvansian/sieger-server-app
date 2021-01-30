package sieger.controller;

import sieger.model.Invitation;
import sieger.service.InvitationService;

public class InvitationController {
	private InvitationService invitationService;

	public InvitationController(InvitationService invitationService) {
		this.invitationService = invitationService;
	}
	
	public void createInvitation(Invitation invitation) {
		
	}
	
	public void acceptInvitation(String userId, String invitationId) {
		
	}
	
	public void declineInvitation(String userId, String invitationId) {
		
	}
	
}
