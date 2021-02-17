package sieger.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Invitation;
import sieger.service.InvitationService;

@RestController
@RequestMapping("invitations")
public class InvitationController {
	@Autowired
	private InvitationService invitationService;
	
	@PostMapping
	public ResponseEntity<Invitation> createInvitation(
			@RequestAttribute("currentUserId") String currentUserId, 
			@RequestBody Invitation invitation) {
		Invitation invitationReady = invitationService.createInvitation(currentUserId, invitation);
		return ResponseEntity.ok(invitationReady);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<String> handleInvitationAcceptation(
			@RequestAttribute("currentUserId") String currentUserId, 
			@PathVariable("id") String invitationId,
			@RequestBody Map<String, Boolean> acceptation) {
		boolean acceptationVal = acceptation.get("accept").booleanValue();
		if (acceptationVal) {
			invitationService.acceptInvitation(currentUserId, invitationId);
		} else {
			invitationService.declineInvitation(currentUserId, invitationId);
		}
		return ResponseEntity.ok(null);
	}
	
}
