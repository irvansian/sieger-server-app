package sieger.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import sieger.model.Invitation;
import sieger.model.ParticipantForm;
import sieger.payload.ApiResponse;
import sieger.service.InvitationService;



class InvitationControllerTest {
	
    @InjectMocks
    private InvitationController invitationController;
 

    @Mock
    private InvitationService invitationService;
    
 
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	void test_createInvitation() {
		Invitation invitation = new Invitation("senderId", "recipientId", "tournamentId", ParticipantForm.SINGLE);
		when(invitationService.createInvitation("userId", invitation)).thenReturn(invitation);
		ResponseEntity<Invitation> response = invitationController.createInvitation("userId", invitation);
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), invitation);
	}
	
	@Test
	void test_handleInvitationAcceptation_Accept() {
		ApiResponse res = new ApiResponse(true, "Successfully accepted the invitation");
		Map<String, Boolean> acceptation = new HashMap<>();
		acceptation.put("accept", true);
		when(invitationService.acceptInvitation("userId", "invitationId")).thenReturn(res);
		ResponseEntity<ApiResponse> response = invitationController.handleInvitationAcceptation("userId", "invitationId", acceptation);
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_handleInvitationAcceptation_Decline() {
		ApiResponse res = new ApiResponse(true, "Successfully declined the invitation");
		Map<String, Boolean> acceptation = new HashMap<>();
		acceptation.put("accept", false);
		when(invitationService.declineInvitation("userId", "invitationId")).thenReturn(res);
		ResponseEntity<ApiResponse> response = invitationController.handleInvitationAcceptation("userId", "invitationId", acceptation);
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
}
