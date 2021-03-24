package sieger.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Game;
import sieger.model.KnockOut;
import sieger.model.KnockOutWithGroup;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Result;
import sieger.model.ScoreResult;
import sieger.model.Team;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.payload.ApiResponse;
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
	void test_getTournamentByName_NotFound() {
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
	@Test
	void test_updateGame_success() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		Game game = new Game(new Date(),"first", "second");
		tournament.setCurrentGames(new ArrayList<Game>());
		tournament.getCurrentGames().add(game);
		tournament.addGame(game.getGameId());
		Result result = new ScoreResult(19,11);
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(gameRepository.retrieveGameById(tournament.getTournamentId(), game.getGameId())).thenReturn(Optional.ofNullable(game));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		when(gameRepository.updateGame(tournament.getTournamentId(), game.getGameId(), game)).thenReturn(true);
		assertEquals(tournamentService.updateGameById("organisator", "name", game.getGameId(), result).getResult(), result);
	}
	@Test
	void test_updateGame_Nopermission() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(user.getUserId());
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.updateGameById("userId", "name", "", null);
		});
	}
	@Test
	void test_createGame_success() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", new Date(),new Date(),new Date(),ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
		when(gameRepository.createGame(any(), any())).thenReturn(true);
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		assertEquals(tournamentService.createGames("organisator", "name").size(), 2);
	}
	@Test
	void test_createGame_Nopermission() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(user.getUserId());
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.createGames("userId", "name");
		});
	}
	@Test
	void test_deleteGame_success() {
		User user = new User("username","surname", "forename", "organisator");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		Game game = new Game(new Date(),"first", "second");
		tournament.addGame(game.getGameId());
		when(userRepository.retrieveUserById("organisator")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(gameRepository.retrieveGameById(tournament.getTournamentId(), game.getGameId())).thenReturn(Optional.ofNullable(game));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		when(gameRepository.deleteGame(tournament.getTournamentId(), game.getGameId())).thenReturn(true);
		assertEquals(tournamentService.deleteGame("organisator", "name", game.getGameId()).getMessage(), "Successfully delete the game");
		assertEquals(tournamentService.deleteGame("organisator", "name", game.getGameId()).getSuccess(), true);
	}
	@Test
	void test_deleteGame_Nopermission() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant(user.getUserId());
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.deleteGame("userId", "name", "gameId");
		});
	}
	
	@Test
	void test_joinTournament_Single_success() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date deadline = c1.getTime();
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", deadline,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.updateUserById("userId", user)).thenReturn(true);
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		assertEquals(tournamentService.joinTournament("userId", "name").getMessage(), "Successfully joined the tournament");
		assertEquals(tournamentService.joinTournament("userId", "name").getSuccess(), true);

	}
	@Test
	void test_joinTournament_Team_success() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date deadline = c1.getTime();
		Team team = new Team("admin", "name", "password");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", deadline,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		assertEquals(tournamentService.joinTournament(team.getTeamId(), "name").getMessage(), "Successfully joined the tournament");
		assertEquals(tournamentService.joinTournament(team.getTeamId(), "name").getSuccess(), true);

	}
	@Test
	void test_joinTournament_tournamentNotFound() {
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.joinTournament("participantId", "name");
		});
	}
	@Test
	void test_joinTournament_UserNotFound() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date deadline = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", deadline,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.joinTournament("userId", "name");
		});
	}
	@Test
	void test_joinTournament_TeamNotFound() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date deadline = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", deadline,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(teamRepository.retrieveTeamById("teamId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.joinTournament("teamId", "name");
		});
	}
	@Test
	void test_joinTournament_badrequest() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2019, 12, 12);
		Date deadline = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", deadline,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		Assertions.assertThrows(BadRequestException.class, () ->{
			tournamentService.joinTournament("teamId", "name");
		});
	}
	@Test
	void test_quitTournament_Single_success() {
		User user = new User("username","surname", "forename", "userId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		user.joinTournament(tournament);
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(user));
		when(userRepository.updateUserById("userId", user)).thenReturn(true);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		ApiResponse res = tournamentService.quitTournament("userId", "name");
		assertEquals(res.getSuccess(), true);
		assertEquals(res.getMessage(), "Successfully quit the tournament");
	}
	@Test
	void test_quitTournament_Team_success() {
		Team team = new Team("admin", "name", "password");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		team.joinTournament(tournament);
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		ApiResponse res = tournamentService.quitTournament(team.getTeamId(), "name");
		assertEquals(res.getSuccess(), true);
		assertEquals(res.getMessage(), "Successfully quit the tournament");
	}
	@Test
	void test_quitTournament_TournamentNotFound() {
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.quitTournament("participantId", "name");
		});
	}
	@Test
	void test_quitTournament_Nopermission() {
		Team team = new Team("admin", "name", "password");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		Assertions.assertThrows(ForbiddenException.class, () ->{
			tournamentService.quitTournament(team.getTeamId(), "name");
		});
	}
	@Test
	void test_quitTournament_UserNotFound() {
		
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant("userId");
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(userRepository.retrieveUserById("userId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.quitTournament("userId", "name");
		});
	}
	@Test
	void test_quitTournament_TeamNotFound() {
		
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		KnockOutWithGroup tournament = new KnockOutWithGroup(4, "name", detail);
		tournament.addParticipant("teamId");
		when(tournamentRepository.retrieveTournamentByName("name")).thenReturn(Optional.ofNullable(tournament));
		when(teamRepository.retrieveTeamById("teamId")).thenReturn(Optional.ofNullable(null));
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			tournamentService.quitTournament("teamId", "name");
		});
	}
}
