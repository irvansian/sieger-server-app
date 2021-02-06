package sieger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Invitation;
import sieger.service.InvitationService;

@RestController
@RequestMapping("invitations")
public class InvitationController {
	private InvitationService invitationService;

	@Autowired
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
