package sieger.controller;

import sieger.service.TournamentService;


import java.util.List;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Game;
import sieger.model.Participant;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.payload.ApiResponse;

@RestController
@RequestMapping("tournaments")
public class TournamentController {
	private TournamentService tournamentService;

	@Autowired
	public TournamentController(TournamentService tournamentService) {
		this.tournamentService = tournamentService;
	}
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	
	@GetMapping
	public ResponseEntity<Tournament> getTournamentById(
			@RequestParam(name = "id") String tournamentId,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournament = tournamentService
				.getTournamentById(currentUserId, tournamentId);
		return ResponseEntity.ok(tournament);
	}
	
	@GetMapping("/{tournamentName}")
	public ResponseEntity<Tournament> getTournamentByName(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournament = 
				tournamentService.getTournamentByName(currentUserId, tournamentName);
		return ResponseEntity.ok(tournament);
	}
	
	@GetMapping("/{tournamentName}/participants")
	public ResponseEntity<List<Participant>> getTournamentParticipants(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Participant> participants = tournamentService
				.getTournamentParticipants(currentUserId, tournamentName);
		return ResponseEntity.ok(participants);
	}
	
	@PostMapping
	public ResponseEntity<Tournament> createNewTournament(
			@RequestBody Tournament tournament,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournamentReady = tournamentService.createNewTournament(currentUserId, 
				tournament);

		return ResponseEntity.ok(tournamentReady);
	}
	
	@PutMapping("/{tournamentName}")
	public ResponseEntity<Tournament> updateTournamentDetailById(
			@PathVariable("tournamentName") String tournamentName, 
			@RequestBody TournamentDetail tournamentDetail,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournament = tournamentService.updateTournamentDetailById(currentUserId, 
				tournamentName, tournamentDetail);
		return ResponseEntity.ok(tournament);
	}
	
	@DeleteMapping("/{tournamentName}")
	public ResponseEntity<ApiResponse> deleteTournament(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = tournamentService.deleteTournament(currentUserId, 
				tournamentName);
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/{tournamentName}")
	public ResponseEntity<ApiResponse> handleParticipation(
			@PathVariable("tournamentName") String tournamentName,
			@RequestBody Map<String, Boolean> participation,
			@RequestAttribute("currentUserId") String currentUserId) {
		boolean participationVal = participation.get("participation").booleanValue();
		ApiResponse res = null;
		if (participationVal == true) {
			 res = tournamentService.joinTournament(currentUserId, tournamentName);
		} else if (participationVal == false) {
			res = tournamentService.quitTournament(currentUserId, tournamentName);
		}
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/{tournamentName}/games")
	public ResponseEntity<List<Game>> getTournamentGames(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Game> games = tournamentService.getAllGame(currentUserId, tournamentName);
		return ResponseEntity.ok(games);
	}
	
	@GetMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<Game> getGameById(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId,
			@RequestAttribute("currentUserId") String currentUserId) {
		Game game = tournamentService.getGameById(currentUserId, 
				tournamentName, gameId);
		return ResponseEntity.ok(game);
	}
	
	@PutMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<Game> updateGameById(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId, 
			Game game,
			@RequestAttribute("currentUserId") String currentUserId) {
		Game gameReady = tournamentService.updateGameById(currentUserId, 
				tournamentName, gameId, game);
		return ResponseEntity.ok(gameReady);
	}
	
	@PostMapping("/{tournamentName}/games")
	public ResponseEntity<Game> createNewGame(
			@PathVariable("tournamentName") String tournamentName, Game game,
			@RequestAttribute("currentUserId") String currentUserId) {
		Game gameReady = tournamentService.createNewGame(currentUserId, tournamentName, game);
		return new ResponseEntity<Game>(gameReady, null, HttpStatus.SC_CREATED);
	}
	
	@DeleteMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<ApiResponse> deleteGame(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = tournamentService.deleteGame(currentUserId, 
				tournamentName, gameId);
		return ResponseEntity.ok(res);
	}
	
	
}
