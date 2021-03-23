package sieger.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.payload.ApiResponse;
import sieger.payload.InvitationDTO;
import sieger.payload.TournamentDTO;
import sieger.payload.UserProfile;
import sieger.service.TeamService;

class TeamControllerTest {

	
    @InjectMocks
    private TeamController teamController;
 

    @Mock
    private TeamService teamService;
    
 
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	void test_getTeamByName() {
		Team team = new Team("admin", "name", "password");
		when(teamService.getTeamByName("userID", "name")).thenReturn(team);
        ResponseEntity<Team> response = teamController.getTeamByName("name", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), team);
	}
	
	@Test
	void test_getTeamById() {
		Team team = new Team("admin", "name", "password");
		when(teamService.getTeamById("userID", team.getTeamId())).thenReturn(team);
        ResponseEntity<Team> response = teamController.getTeamById(team.getTeamId(), "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), team);
	}
	
	@Test
	void test_NewTeam() {
		Team team = new Team("admin", "name", "password");
		when(teamService.createNewTeam(team)).thenReturn(team);
		ResponseEntity<Team> response = teamController.createNewTeam(team, "admin");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), team);
	}
	
	@Test
	void test_deleteTeam() {
		ApiResponse res = new ApiResponse(true, "You deleted the team <" + "name" + ">");
		when(teamService.deleteTeam("admin", "name")).thenReturn(res);
		ResponseEntity<ApiResponse> response = teamController.deleteTeam("name", "admin");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_getTeamMembers() {
		List<UserProfile> members = new ArrayList<UserProfile>();
		when(teamService.getTeamMembers("admin", "name")).thenReturn(members);
		ResponseEntity<List<UserProfile>> response = teamController.getTeamMembers("name", "admin");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), members);
	}
	
	@Test
	void test_getTeamTournaments() {
		List<Tournament> tournaments = new ArrayList<Tournament>();
		when(teamService.getTeamTournaments("userID", "name")).thenReturn(tournaments);
        ResponseEntity<List<TournamentDTO>> response = teamController.getTeamTournaments("name", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), tournaments);
	}
	
	@Test
	public void test_getUserInvitations() {
		List<InvitationDTO> invitations = new ArrayList<InvitationDTO>();
		when(teamService.getTeamInvitations("userID", "name")).thenReturn(invitations);
        ResponseEntity<List<InvitationDTO>> response = teamController.getTeamInvitations("name", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), invitations);
	}
	
	@Test
	void test_kickMember() {
		ApiResponse res = new ApiResponse(true, "You kicked a member with ID <" 
				+ "userToKickId" + ">");
		when(teamService.kickTeamMembers("admin", "userToKickId", "name")).thenReturn(res);
		ResponseEntity<ApiResponse> response = teamController.kickTeamMember("admin", "userToKickId", "name", "admin");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_joinTeam() {
		ApiResponse res = new ApiResponse(true, "Successfully joined the team");
		Map<String, String> payload = new HashMap<>();
		payload.put("activity", "join");
		payload.put("password", "password");
		when(teamService.joinTeam("admin", "name", "password")).thenReturn(res);
		ResponseEntity<ApiResponse> response = teamController.handleMembership("admin", "name", payload);
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_quitTeam() {
		ApiResponse res = new ApiResponse(true, "Successfully quit the team");
		Map<String, String> payload = new HashMap<>();
		payload.put("activity", "quit");
		when(teamService.quitTeam("admin", "name")).thenReturn(res);
		ResponseEntity<ApiResponse> response = teamController.handleMembership("admin", "name", payload);
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}

}
