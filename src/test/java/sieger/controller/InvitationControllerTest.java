package sieger.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import sieger.model.Invitation;
import sieger.model.ParticipantForm;
import sieger.payload.ApiResponse;
import sieger.payload.InvitationDTO;
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
		ModelMapper mapper = new ModelMapper();
		InvitationDTO invDTO = mapper.map(invitation, InvitationDTO.class);

		when(invitationService.createInvitation("userId", invitation)).thenReturn(invDTO);
		ResponseEntity<InvitationDTO> response = invitationController.createInvitation("userId", invitation);
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), invDTO);
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
	
	@Test
	void test_getOtherUserIdByNameInvitation() {
		when(invitationService.getRecipientId("recipientUsername")).thenReturn("userId");
		ResponseEntity<Map<String, String>> response = invitationController.getOtherUserIdByNameInvitation("recipientUsername");
		assertEquals(response.getBody().get("userId"), "userId");
		assertEquals(response.getStatusCodeValue(), 200);
	}
	@Test
	void test_getOtherTeamIdByNameInvitation() {
		when(invitationService.getRecipientTeamId("recipientTeamName")).thenReturn("teamId");
		ResponseEntity<Map<String, String>> response = invitationController.getOtherTeamIdByNameInvitation("recipientTeamName");
		assertEquals(response.getBody().get("userId"), "teamId");
		assertEquals(response.getStatusCodeValue(), 200);
	}
}
