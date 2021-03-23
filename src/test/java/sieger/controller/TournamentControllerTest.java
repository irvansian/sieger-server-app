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

import sieger.model.Game;
import sieger.model.League;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Result;
import sieger.model.ScoreResult;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.payload.ApiResponse;
import sieger.payload.TournamentDTO;
import sieger.service.TournamentService;
import sieger.util.TournamentConverter;


class TournamentControllerTest {
	
    @InjectMocks
    private TournamentController tournamentController;
 

    @Mock
    private TournamentService tournamentService;
    
 
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	void test_getTournamentById() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		TournamentDTO tourneyDTO = TournamentConverter.convertToTournamentDTO(tournament);
		when(tournamentService.getTournamentById("userId", tournament.getTournamentId())).thenReturn(tournament);
		ResponseEntity<TournamentDTO> response = tournamentController.getTournamentById(tournament.getTournamentId(), "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getTournamentId(), tourneyDTO.getTournamentId());
	}

	@Test
	void test_getTournamentByName() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		TournamentDTO tourneyDTO = TournamentConverter.convertToTournamentDTO(tournament);
		when(tournamentService.getTournamentByName("userId", "name")).thenReturn(tournament);
		ResponseEntity<TournamentDTO> response = tournamentController.getTournamentByName("name", "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getTournamentId(), tourneyDTO.getTournamentId());
	}
	
	@Test
	void test_getTournamentParticipants() {
		List<Participant> participants = new ArrayList<Participant>();
		when(tournamentService.getTournamentParticipants("userId", "name")).thenReturn(participants);
		ResponseEntity<List<Participant>> response = tournamentController.getTournamentParticipants("name", "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), participants);
	}
	
	@Test
	void test_getTournamentGames() {
		List<Game> games = new ArrayList<Game>();
		when(tournamentService.getAllGame("userId", "name")).thenReturn(games);
		ResponseEntity<List<Game>> response = tournamentController.getTournamentGames("name", "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), games);
	}
	
	@Test
	void test_createNewTournament() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		TournamentDTO tourneyDTO = TournamentConverter.convertToTournamentDTO(tournament);

		when(tournamentService.createNewTournament("userId", tournament)).thenReturn(tournament);
		ResponseEntity<TournamentDTO> response = tournamentController.createNewTournament(tournament, "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getTournamentId(), tourneyDTO.getTournamentId());
	}
	
	@Test
	void test_updateTournamentByDetail() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		TournamentDTO tourneyDTO = TournamentConverter.convertToTournamentDTO(tournament);

		when(tournamentService.updateTournamentDetail("userId", "name", detail)).thenReturn(tournament);
		ResponseEntity<TournamentDTO> response = tournamentController.updateTournamentDetail("name", detail, "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody().getTournamentId(), tourneyDTO.getTournamentId());
	}
	
	@Test
	void test_deleteTournament() {
		ApiResponse res = new ApiResponse(true, "Successfully deleted the tournament");
		when(tournamentService.deleteTournament("userId", "name")).thenReturn(res);
		ResponseEntity<ApiResponse> response = tournamentController.deleteTournament("name", "userId");
		assertEquals(response.getStatusCodeValue(), 200);
		assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_handleParticipation_Accept() {
		ApiResponse res = new ApiResponse(true, "Successfully joined the tournament");
		Map<String, Boolean> participation = new HashMap<>();
		participation.put("participation", true);
		when(tournamentService.joinTournament("userId", "name")).thenReturn(res);
		ResponseEntity<ApiResponse> response = tournamentController.handleParticipation("name", participation, "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_handleParticipation_Quit() {
		ApiResponse res = new ApiResponse(true, "Successfully quit the tournament");
		Map<String, Boolean> participation = new HashMap<>();
		participation.put("participation", false);
		when(tournamentService.quitTournament("userId", "name")).thenReturn(res);
		ResponseEntity<ApiResponse> response = tournamentController.handleParticipation("name", participation, "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), res);
	}
	
	@Test
	void test_getGameById() {
		Game game = new Game(null, "first", "second");
		when(tournamentService.getGameById("userId", "name", game.getGameId())).thenReturn(game);
		ResponseEntity<Game> response = tournamentController.getGameById("name", game.getGameId(), "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), game);
	}
	
	@Test
	void test_updateGameById() {
		Game game = new Game(null, "first", "second");
		Result result = new ScoreResult(10,1);
		game.setResult(result);
		when(tournamentService.updateGameById("userId", "name", game.getGameId(), result)).thenReturn(game);
		ResponseEntity<Game> response = tournamentController.updateGameById("name", game.getGameId(), result, "userId");
		assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), game);
	}
	
	@Test
	void test_createGames() {
		List<Game> games = new ArrayList<Game>();
		when(tournamentService.createGames("userId", "name")).thenReturn(games);
		ResponseEntity<List<Game>> response = tournamentController.createGames("name", "userId");
		assertEquals(response.getStatusCodeValue(), 201);
        assertEquals(response.getBody(), games);
	}
	
	@Test
	void test_deleteGames() {
		ApiResponse res = new ApiResponse(true, "Successfully delete the game");
		when(tournamentService.deleteGame("userId", "name", "gameId")).thenReturn(res);
		ResponseEntity<ApiResponse> response = tournamentController.deleteGame("name", "gameId", "userId");
		assertEquals(response.getStatusCodeValue(), 200);
		assertEquals(response.getBody(), res);
	}
	
}
