package sieger.controller;

import sieger.service.TournamentService;

import java.util.List;

import sieger.model.Participant;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;

public class TournamentController {
	private TournamentService tournamentService;

	public TournamentController(TournamentService tournamentService) {
		this.tournamentService = tournamentService;
	}
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	
	public Tournament getTournamentById(String tournamentId) {
		return null;
	}
	
	public Tournament getTournamentByName(String tournamentName) {
		return null;
	}
	
	public List<Participant> getTournamentParticipants(String tournamentId) {
		return null;
	}
	
	public void createNewTournament(Tournament tournament) {
	
	}
	
	public void updateTournamentDetailById(String tournamentId, TournamentDetail tournamentDetail) {
		
	}
	
	public void deleteTournament(String tournamentId) {
		
	}
	
	
}
