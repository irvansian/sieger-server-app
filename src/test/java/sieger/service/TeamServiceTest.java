package sieger.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.League;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.payload.UserProfile;
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
	void test_getTeamByName_Nopression() {
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
	void test_getTeamById_Nopression() {
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
}
