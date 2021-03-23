package sieger.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import sieger.model.League;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.payload.InvitationDTO;
import sieger.repository.InvitationRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

class TeamServiceTest {

	
    @InjectMocks
    private TeamService teamService;
 
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
	void test_getTeamByName_TeamNotFound() {
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			teamService.getTeamByName("userID", "name");
		});
	}
	@Test
	void test_getTeamByName_Nopermission() {
		Team team = new Team("admin", "name", "password");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			teamService.getTeamByName("nopression", "name");
		});
	}
	@Test
	void test_getTeamByName_success() {
		Team team = new Team("admin", "name", "password");
		team.addMember("userId");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		assertEquals(team, teamService.getTeamByName("userId", "name"));
	}
	@Test
	void test_getTeamById_TeamNotFound() {
		when(teamRepository.retrieveTeamById("teamId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			teamService.getTeamById("userID", "teamId");
		});
	}
	@Test
	void test_getTeamById_Nopermission() {
		Team team = new Team("admin", "name", "password");
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			teamService.getTeamById("nopression", team.getTeamId());
		});
	}
	@Test
	void test_getTeamById_success() {
		Team team = new Team("admin", "name", "password");
		team.addMember("userId");
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		assertEquals(team, teamService.getTeamById("userId", team.getTeamId()));
	}
	@Test
	void test_NewTeam_alreadyExist() {
		Team team = new Team("admin", "name", "password");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		Assertions.assertThrows(BadRequestException.class, () ->{
			teamService.createNewTeam(team);
		});
	}
	@Test
	void test_NewTeam_success() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserById("admin")).thenReturn(Optional.ofNullable(user));
		when(userRepository.updateUserById("admin", user)).thenReturn(true);
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(null));
		assertEquals(team, teamService.createNewTeam(team));
	}
	@Test
	void test_deleteTeam_Nopression() {
		Team team = new Team("admin", "name", "password");
		team.addMember("userId");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			teamService.deleteTeam("userId", "name");
		});
	}
	@Test
	void test_deleteTeam_success() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username","surname", "forename", "admin");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(userRepository.retrieveUserById("admin")).thenReturn(Optional.ofNullable(user));
		when(userRepository.updateUserById("admin", user)).thenReturn(true);
		assertEquals(false, user.getTeamList().contains(team.getTeamId()));
		assertEquals(teamService.deleteTeam("admin", "name").getMessage(), "You deleted the team <name>");
		assertEquals(teamService.deleteTeam("admin", "name").getSuccess(), true);

	}
	@Test
	void test_getTeamMembers() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username","surname", "forename", "admin");
	
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(userRepository.retrieveUserById("admin")).thenReturn(Optional.ofNullable(user));
		assertEquals("username", teamService.getTeamMembers("admin", "name").get(0).getUsername());
		assertEquals("surname", teamService.getTeamMembers("admin", "name").get(0).getSurname());
		assertEquals("forename", teamService.getTeamMembers("admin", "name").get(0).getForename());
		assertEquals("admin", teamService.getTeamMembers("admin", "name").get(0).getId());
	}
	@Test
	void test_getTeamTournaments() {
		Team team = new Team("admin", "name", "password");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		League tournament = new League(4, "name", detail);
		team.addTournament(tournament.getTournamentId());
		
		List<Tournament> tournaments = new ArrayList<Tournament>();
		tournaments.add(tournament);
		
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		assertEquals(tournaments, teamService.getTeamTournaments("admin", "name"));
	}
	@Test
	void test_getTeamInvitations() {
		Team team = new Team("admin", "name", "password");
		Invitation invitation = new Invitation("admin", "recipientId", "tournamentId", ParticipantForm.SINGLE);
		team.addInvitation(invitation.getInvitationId());
		List<Invitation> invitations = new ArrayList<>();
		invitations.add(invitation);
		ModelMapper mapper = new ModelMapper();
		InvitationDTO invDTO = mapper.map(invitation, InvitationDTO.class);
		invDTO.setTournamentName("tournamentName");
		invDTO.setSenderUsername("senderName");
		List<InvitationDTO> invitationDTOs = new ArrayList<InvitationDTO>();
		invitationDTOs.add(invDTO);
		
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));	
		when(invitationService.convertToInvitationDTOList(invitations)).thenReturn(invitationDTOs);
		assertEquals(invitationDTOs.get(0).getInvitationId(), teamService.getTeamInvitations("admin", "name").get(0).getInvitationId());
		assertEquals(invitationDTOs.get(0).getRecipientId(), teamService.getTeamInvitations("admin", "name").get(0).getRecipientId());
		assertEquals(invitationDTOs.get(0).getSenderId(), teamService.getTeamInvitations("admin", "name").get(0).getSenderId());
		assertEquals(invitationDTOs.get(0).getTournamentId(), teamService.getTeamInvitations("admin", "name").get(0).getTournamentId());
		assertEquals(invitationDTOs.get(0).getTournamentName(), teamService.getTeamInvitations("admin", "name").get(0).getTournamentName());
		assertEquals(invitationDTOs.get(0).getSenderUsername(), teamService.getTeamInvitations("admin", "name").get(0).getSenderUsername());
	}
	@Test
	void test_kickMembers_NotFound() {
		Team team = new Team("admin", "name", "password");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			teamService.kickTeamMembers("admin", "userID", "name");
		});
	}
	@Test
	void test_kickMembers_Nopermission() {
		Team team = new Team("admin", "name", "password");
		team.addMember("normalUser");
		User user = new User("username","surname", "forename", "userID");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			teamService.kickTeamMembers("normalUser", "userID", "name");
		});
	}
	@Test
	void test_kickMembers_success() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username","surname", "forename", "normalUser");
		user.joinTeam(team, "password");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(userRepository.retrieveUserById("normalUser")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(userRepository.updateUserById("normalUser", user)).thenReturn(true);
		teamService.kickTeamMembers("admin", "normalUser", "name");
		assertFalse(user.getTeamList().contains(team.getTeamId()));
		assertFalse(team.getMemberList().contains("normalUser"));
	}
	@Test
	void test_joinTeam_UserNotFound() {
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			teamService.joinTeam("userID", "name", "password");
		});
	}
	@Test
	void test_joinTeam_TeamNotFound() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			teamService.joinTeam("userID", "name", "password");
		});
	}
	@Test
	void test_joinTeam_FalsePassword() {
		User user = new User("username","surname", "forename", "userID");
		Team team = new Team("admin", "name", "password");
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		Assertions.assertThrows(BadRequestException.class, () ->{
			teamService.joinTeam("userID", "name", "falsepassword");
		});
	}
	@Test
	void test_joinTeam_success() {
		User user = new User("username","surname", "forename", "userID");
		Team team = new Team("admin", "name", "password");
		user.joinTeam(team, "password");
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(userRepository.updateUserById("userID", user)).thenReturn(true);
		ApiResponse res = teamService.joinTeam("userID", "name", "password");
		assertTrue(user.getTeamList().contains(team.getTeamId()));
		assertTrue(team.getMemberList().contains("userID"));
		assertEquals("Successfully joined the team", res.getMessage());
		assertEquals(true, res.getSuccess());
	}
	@Test
	void test_quitTeam_UserNotFound() {
		Team team = new Team("admin", "name", "password");
		team.addMember("userID");
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(null));
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			teamService.quitTeam("userID", "name");
		});
	}
	@Test
	void test_quitTeam_success() {
		User user = new User("username","surname", "forename", "userID");
		Team team = new Team("admin", "name", "password");
		user.joinTeam(team, "password");
		
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(userRepository.updateUserById("userID", user)).thenReturn(true);
		
		ApiResponse res = teamService.quitTeam("userID", "name");
		
		assertEquals("Successfully quit the team", res.getMessage());
		assertEquals(true, res.getSuccess());
	}
}
