package sieger.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public ResponseEntity<String> createInvitation(Invitation invitation) {
		if (invitationService.createInvitation(invitation)) {
			return ResponseEntity.ok(null);
		}
		
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}
	
	public void acceptInvitation(String userId, String invitationId) {
		
	}
	
	public void declineInvitation(String userId, String invitationId) {
		
	}
	
}
