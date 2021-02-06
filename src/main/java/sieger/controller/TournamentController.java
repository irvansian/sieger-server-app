package sieger.controller;

import sieger.service.TournamentService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Participant;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.User;

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
			@RequestParam(name = "id") String tournamentId) {
		Optional<Tournament> tournament = tournamentService.getTournamentById(tournamentId);
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
	
	public void createNewTournament(Tournament tournament) {
	
	}
	
	public void updateTournamentDetailById(String tournamentId, TournamentDetail tournamentDetail) {
		
	}
	
	public void deleteTournament(String tournamentId) {
		
	}
	
	
}
