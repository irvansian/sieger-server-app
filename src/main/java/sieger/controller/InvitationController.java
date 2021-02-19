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
import sieger.payload.ApiResponse;
import sieger.service.InvitationService;
/**
 * The invitation controller class, which handles the request from client.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@RestController
@RequestMapping("invitations")
public class InvitationController {
	/**
	 * The invitstion service, which connects controller and repository.
	 */
	@Autowired
	private InvitationService invitationService;
	/**
	 * Post request from client.To create a new invitation in database.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param invitation Json object passed from client.
	 * @return Return the 200OK response with invitation details.
	 */
	@PostMapping
	public ResponseEntity<Invitation> createInvitation(
			@RequestAttribute("currentUserId") String currentUserId, 
			@RequestBody Invitation invitation) {
		Invitation invitationReady = invitationService.createInvitation(currentUserId, invitation);
		return ResponseEntity.ok(invitationReady);
	}
	/**
	 * Post request from client.To accept or decline the invitation.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param invitationId The id of Invitation.
	 * @param acceptation The json object from client, which contains the choice of user.
	 * @return Return the 200OK response with message.
	 */
	@PostMapping("/{id}")
	public ResponseEntity<ApiResponse> handleInvitationAcceptation(
			@RequestAttribute("currentUserId") String currentUserId, 
			@PathVariable("id") String invitationId,
			@RequestBody Map<String, Boolean> acceptation) {
		boolean acceptationVal = acceptation.get("accept").booleanValue();
		ApiResponse res = null;
		if (acceptationVal) {
			res = invitationService.acceptInvitation(currentUserId, invitationId);
		} else {
			res = invitationService.declineInvitation(currentUserId, invitationId);
		}
		return ResponseEntity.ok(res);
	}
	
}
