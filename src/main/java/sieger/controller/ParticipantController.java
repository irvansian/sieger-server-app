package sieger.controller;

import sieger.service.ParticipantService;

public class ParticipantController {
	private ParticipantService participantService;

	public ParticipantController(ParticipantService participantService) {
		this.participantService = participantService;
	}
	
	public void joinTournament(String participantId, String tournamentId) {
		
	}
	
	public void quitTournament(String participantId, String tournamentId) {
		
	}
	
}
