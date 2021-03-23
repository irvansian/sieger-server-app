package sieger.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sieger.model.Invitation;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.payload.InvitationDTO;
import sieger.payload.TournamentDTO;
import sieger.service.UserService;
import sieger.util.TournamentConverter;

class UserControllerTest {
	
	
    @InjectMocks
    private UserController userController;
 

    @Mock
    private UserService userService;
    
 
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void test_NewUser() {
		User user = new User("username","surname", "forename", "userID");
        when(userService.createNewUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.createNewUser(user);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), user);
	}
	
	@Test
	public void test_getUserByName() {
		User user = new User("username","surname", "forename", "userID");
		when(userService.getUserByUsername("userID", "username")).thenReturn(user);
        ResponseEntity<User> response = userController.getUserByUsername("username", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), user);
	}
	
	@Test
	public void test_getUserById() {
		User user = new User("username","surname", "forename", "userID");
		when(userService.getUserById("userID", "userID")).thenReturn(user);
        ResponseEntity<User> response = userController.getUserById("userID", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), user);
	}
	
	@Test
	public void test_getUserTeams() {
		
		List<Team> teams = new ArrayList<Team>();
		when(userService.getUserTeams("userID", "username")).thenReturn(teams);
        ResponseEntity<List<Team>> response = userController.getUserTeams("username", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), teams);
	}
	
	@Test
	public void test_getUserInvitations() {
		
		List<InvitationDTO> invitations = new ArrayList<InvitationDTO>();
		when(userService.getUserInvitations("userID", "username")).thenReturn(invitations);
        ResponseEntity<List<InvitationDTO>> response = userController.getUserInvitations("username", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), invitations);
	}
	
	@Test
	public void test_getUserTournaments() {
		
		List<Tournament> tournaments = new ArrayList<Tournament>();
		List<TournamentDTO> tournamentsDTO = TournamentConverter
				.convertToTournamentDTOList(tournaments);
		when(userService.getUserTournaments("userID", "username")).thenReturn(tournaments);
        ResponseEntity<List<TournamentDTO>> response = userController.getUserTournaments("username", "userID");
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), tournamentsDTO);
	}
	
	@Test
	public void test_UpdateUser() {
		Map<String, String> userDetail = new HashMap<>();
		userDetail.put("username", "newName");
		userDetail.put("surname", "surname");
		userDetail.put("forename", "forename");
		 User user = new User("newName","surname", "forename", "userID");
        when(userService.updateUserDetail("userID", "oldName", "newName", "surname", "forename")).thenReturn(user);
        ResponseEntity<User> response = userController.updateUserDetail("userID", "oldName", userDetail);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), user);
	}
	
}
