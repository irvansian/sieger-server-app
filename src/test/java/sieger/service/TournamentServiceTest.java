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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Game;
import sieger.model.KnockOutWithGroup;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.repository.GameRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

class TournamentServiceTest {
	
    @InjectMocks
    private TournamentService tournamentService;
 
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private TournamentRepository tournamentRepository;
    
    @Mock
    private TeamRepository teamRepository;
    
    @Mock
    private GameRepository gameRepository;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	@Test
	void test_getTournamentByName_TeamNotFound() {
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.getTournamentByName("userID", "name");
		});
	}
	@Test
	void test_getTournamentByName_Nopermission() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		User user = new User("username","surname", "forename", "userId");
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));

		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.getTournamentByName("userId", "name");
		});
	}
	@Test
	void test_getTournamentByName_success() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		User user = new User("username","surname", "forename", "userId");
		tournament.addParticipant("userId");
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		assertEquals(tournament, tournamentService.getTournamentByName("userId", "name"));
	}
	@Test
	void test_getTournamentById_TeamNotFound() {
		when(tournamentRepository.retrieveTournamentById("tournamentId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.getTournamentById("userID", "tournamentId");
		});
	}
	@Test
	void test_getTournamentById_Nopermission() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		User user = new User("username","surname", "forename", "userId");
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));

		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.getTournamentById("userId", tournament.getTournamentId());
		});
	}
	@Test
	void test_getTournamentById_success() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		User user = new User("username","surname", "forename", "userId");
		tournament.addParticipant("userId");
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		assertEquals(tournament, tournamentService.getTournamentById("userId", tournament.getTournamentId()));
	}
	@Test
	void test_getTournamentParticipants() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		User user = new User("username","surname", "forename", "userId");
		tournament.addParticipant("userId");
		List<Participant> participants = new ArrayList<>();
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.retrieveTournamentParticipants(tournament.getTournamentId(), ParticipantForm.SINGLE)).thenReturn(participants);
		assertEquals(participants, tournamentService.getTournamentParticipants("userId", "name"));

	}
	@Test
	void test_createNewTournament_success() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(null));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.createTournament(tournament)).thenReturn(true);
		when(userRepository.updateUserById("userId", user)).thenReturn(true);
		assertEquals(tournament, tournamentService.createNewTournament("userId", tournament));
	}
	@Test
	void test_createNewTournament_Alreadyexist() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		Assertions.assertThrows(BadRequestException.class, () ->{
			tournamentService.createNewTournament("userId", tournament);
		});
	}
	@Test
	void test_updateTournament_success() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		assertEquals(tournament, tournamentService.updateTournamentDetail("organisator", "name", detail));
	}
	
	@Test
	void test_updateTournament_Nopermission() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(user.getUserId());
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.updateTournamentDetail("userId", "name", detail);
		});
	}
	@Test
	void test_deleteTournament_success_single() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("userId", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(user.getUserId());
		user.addTournament(tournament.getTournamentId());
		
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.deleteTournament(tournament.getTournamentId())).thenReturn(true);
		when(userRepository.updateUserById("userId", user)).thenReturn(true);
		assertEquals(tournamentService.deleteTournament("userId", "name").getMessage(), "Successfully deleted the tournament");
		assertEquals(tournamentService.deleteTournament("userId", "name").getSuccess(), true);

	}
	@Test
	void test_deleteTournament_success_Team() {
		User user = new User("username","surname", "forename", "userId");
		Team team = new Team("admin", "name", "password");

		TournamentDetail detail = new TournamentDetail("userId", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(team.getTeamId());
		team.addTournament(tournament.getTournamentId());
		
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.deleteTournament(tournament.getTournamentId())).thenReturn(true);
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));

		assertEquals(tournamentService.deleteTournament("userId", "name").getMessage(), "Successfully deleted the tournament");
		assertEquals(tournamentService.deleteTournament("userId", "name").getSuccess(), true);

	}
	@Test
	void test_deleteTournament_Nopermission() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(user.getUserId());
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.deleteTournament("userId", "name");
		});
	}
	
	@Test
	void test_getAllgames() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		Game game = new Game(new Date(),"first", "second");
		tournament.addGame(game.getGameId());
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(gameRepository.retrieveGameById(tournament.getTournamentId(), game.getGameId())).thenReturn(Optional.ofNullable(game));
		assertEquals(tournamentService.getAllGame("organisator", "name").get(0), game);
	}
	
	@Test
	void test_getGameById_success() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		Game game = new Game(new Date(),"first", "second");
		tournament.addGame(game.getGameId());
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(gameRepository.retrieveGameById(tournament.getTournamentId(), game.getGameId())).thenReturn(Optional.ofNullable(game));
		assertEquals(tournamentService.getGameById("organisator", "name", game.getGameId()), game);
	}
	@Test
	void test_getGameById_NotFound() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(gameRepository.retrieveGameById(tournament.getTournamentId(), "gameId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.getGameById("organisator", "name", "gameId");
		});
	}
}
