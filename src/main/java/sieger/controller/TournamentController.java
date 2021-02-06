package sieger.controller;

import sieger.service.GameService;
import sieger.service.TournamentService;


import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Game;
import sieger.model.Participant;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;

@RestController
@RequestMapping("tournaments")
public class TournamentController {
	private TournamentService tournamentService;
	private GameService gameService;

	@Autowired
	public TournamentController(TournamentService tournamentService, 
			GameService gameService) {
		this.tournamentService = tournamentService;
	}
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	
	@GetMapping
	public ResponseEntity<Tournament> getTournamentById(
			@RequestParam(name = "id") String tournamentId) {
		Optional<Tournament> tournament = tournamentService
				.getTournamentById(tournamentId);
		if (tournament.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tournament.get());
	}
	
	@GetMapping("/{tournamentName}")
	public ResponseEntity<Tournament> getTournamentByName(
			@PathVariable("tournamentName") String tournamentName) {
		Optional<Tournament> tournament = 
				tournamentService.getTournamentByName(tournamentName);
		if (tournament.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tournament.get());
	}
	
	@GetMapping("/{tournamentName}/participants")
	public ResponseEntity<List<Participant>> getTournamentParticipants(
			@PathVariable("tournamentName") String tournamentName) {
		List<Participant> participants = tournamentService
				.getTournamentParticipants(tournamentName);
		return ResponseEntity.ok(participants);
	}
	
	@PostMapping
	public ResponseEntity<String> createNewTournament(Tournament tournament) {
		if (tournamentService.createNewTournament(tournament)) {
			return ResponseEntity.ok(null);
		}
		
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}
	
	@PutMapping("/{tournamentName}")
	public ResponseEntity<String> updateTournamentDetailById(
			@PathVariable("tournamentName") String tournamentName, 
			@RequestBody TournamentDetail tournamentDetail) {
		if (tournamentService.updateTournamentDetailById(tournamentName, 
				tournamentDetail)) {
			return ResponseEntity.ok(null);
		}
		
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_UNPROCESSABLE_ENTITY);
	}
	
	@DeleteMapping("/{tournamentName}")
	public ResponseEntity<String> deleteTournament(
			@PathVariable("tournamentName") String tournamentName) {
		if (tournamentService.deleteTournament(tournamentName)) {
			return ResponseEntity.ok(null);
		}
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{tournamentName}/games")
	public ResponseEntity<List<Game>> getTournamentGames(
			@PathVariable("tournamentName") String tournamentName) {
		List<Game> games = gameService.getAllGame(tournamentName);
		return ResponseEntity.ok(games);
	}
	
	@GetMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<Game> getGameById(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId) {
		Optional<Game> game = gameService.getGameById(tournamentName, gameId);
		if (game.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game.get());
	}
	
	public void updateGameById(String tournamentId, String gameId, Game game) {
		
	}
	
	public void createNewGame(String tournamentId, Game game) {
		
	}
	
	public void deleteGame(String gameId) {
		
	}
	
	
}
