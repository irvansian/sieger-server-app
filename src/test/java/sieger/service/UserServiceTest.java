package sieger.service;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Invitation;
import sieger.model.KnockOutWithGroup;
import sieger.model.League;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.payload.InvitationDTO;
import sieger.repository.InvitationRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

class UserServiceTest {
	
    @InjectMocks
    private UserService userService;
 
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private TournamentRepository tournamentRepository;
    
    @Mock
    private TeamRepository teamRepository;
    
    @Mock
    private InvitationRepository invitationRepository;
    
    @Mock
    private InvitationService invitationService;
 
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	void test_getUserByName_success() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		assertEquals(user, userService.getUserByUsername("userID", "username"));
	}
	
	@Test
	void test_getUserByName_UserNotFound() {
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(null));
		Executable exception =() ->userService.getUserByUsername("userID", "username");
		Assertions.assertThrows(ResourceNotFoundException.class, exception);
		
	}
	
	@Test
	void test_getUserByName_NoPermission() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		Executable exception =() ->userService.getUserByUsername("noPermission", "username");
		Assertions.assertThrows(ForbiddenException.class, exception);
		
	}
	
	@Test
	void test_getUserById_success() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		assertEquals(user, userService.getUserById("userID", "userID"));
	}
	
	@Test
	void test_getUserById_UserNotFound() {
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(null));
		Executable exception =() ->userService.getUserById("userID", "userID");
		Assertions.assertThrows(ResourceNotFoundException.class, exception);
		
	}
	
	@Test
	void test_getUserById_NoPermission() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		Executable exception =() ->userService.getUserById("noPermission", "userID");
		Assertions.assertThrows(ForbiddenException.class, exception);
		
	}
	
	@Test
	void test_getUserTournaments() {
		User user = new User("username","surname", "forename", "userID");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		user.addTournament(tournament.getTournamentId());
		
		List<Tournament> tournaments = new ArrayList<Tournament>();
		tournaments.add(tournament);
		
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		assertEquals(tournaments, userService.getUserTournaments("userID", "username"));
	}
	
	@Test
	void test_getUserTeams() {
		User user = new User("username","surname", "forename", "userID");
		Team team = new Team("admin", "name", "password");
		user.addTeam(team.getTeamId());
		
		List<Team> teams = new ArrayList<Team>();
		teams.add(team);
		
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		assertEquals(teams, userService.getUserTeams("userID", "username"));
	}

	@Test
	void test_getUserInvitations() {
		User user = new User("username","surname", "forename", "userID");
		Invitation invitation = new Invitation("senderId", "recipientId", "tournamentId", ParticipantForm.SINGLE);
		user.addInvitation(invitation.getInvitationId());
		List<Invitation> invitations = new ArrayList<>();
		invitations.add(invitation);
		ModelMapper mapper = new ModelMapper();
		InvitationDTO invDTO = mapper.map(invitation, InvitationDTO.class);

		List<InvitationDTO> invitationDTOs = new ArrayList<InvitationDTO>();
		invitationDTOs.add(invDTO);
		
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));	
		when(invitationService.convertToInvitationDTOList(invitations)).thenReturn(invitationDTOs);
		assertEquals(invitationDTOs.get(0).getInvitationId(), userService.getUserInvitations("userID", "username").get(0).getInvitationId());
	}
	
	@Test
	void test_createNewUser_success() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(null));
		assertEquals(user, userService.createNewUser(user));
	}
	
	@Test
	void test_createNewUser_alreadyExist() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		Executable exception =() ->userService.createNewUser(user);
		Assertions.assertThrows(BadRequestException.class, exception);
		
	}
	
	@Test
	void test_updateUserDetail_success() {
		User user = new User("oldUsername","surname", "forename", "userID");
		User userUpdated = new User("newUsername","surname", "forename", "userID");
		
		when(userRepository.retrieveUserByUsername("oldUsername")).thenReturn(Optional.ofNullable(user));
		when(userRepository.retrieveUserByUsername("newUsername")).thenReturn(Optional.ofNullable(null));

		assertTrue(userUpdated.getUsername().equals(userService.updateUserDetail("userID", "oldUsername", "newUsername", "surname", "forename").getUsername()));
		assertTrue(userUpdated.getSurname().equals(userService.updateUserDetail("userID", "oldUsername", "newUsername", "surname", "forename").getSurname()));
		assertTrue(userUpdated.getForename().equals(userService.updateUserDetail("userID", "oldUsername", "newUsername", "surname", "forename").getForename()));
	}
	
	@Test
	void test_updateUserDetail_alreadyExist() {
		User user = new User("oldUsername","surname", "forename", "userID");
		User userUpdated = new User("newUsername","surname", "forename", "userID");
		
		when(userRepository.retrieveUserByUsername("oldUsername")).thenReturn(Optional.ofNullable(user));
		when(userRepository.retrieveUserByUsername("newUsername")).thenReturn(Optional.ofNullable(userUpdated));
		Executable exception =() ->userService.updateUserDetail("userID", "oldUsername", "newUsername", "surname", "forename");
		Assertions.assertThrows(BadRequestException.class, exception);
		
	}

}
