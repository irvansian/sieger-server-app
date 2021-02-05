package sieger.service;

import sieger.model.Participant;
import sieger.model.Tournament;

public class ParticipantService {
	private UserService userService;
	private TeamService teamService;
	private TournamentService tournamentService;
	
	public ParticipantService(UserService userService, TeamService teamService, TournamentService tournamentService) {
		this.userService = userService;
		this.teamService = teamService;
		this.tournamentService = tournamentService;
	}
	
	public boolean joinTournament(String participantId, String tournamentId) {
		Tournament tournament = tournamentService.getTournamentById(tournamentId).get();
		Participant participant;
		if(tournament.allowUserToJoin()) {
			participant = userService.getUserById(participantId).get();
			return participant.joinTournament(tournament);
		} else if(tournament.allowTeamToJoin()) {
			participant = teamService.getTeamById(participantId).get();
			return participant.joinTournament(tournament);
		} else {
			return false;
		}
	}
	
	public boolean quitTournament(String participantId, String tournamentId) {
		Tournament tournament = tournamentService.getTournamentById(tournamentId).get();
		Participant participant;
		if(tournament.allowUserToJoin()) {
			participant = userService.getUserById(participantId).get();
			return participant.quitTournament(tournament);
		} else if(tournament.allowTeamToJoin()) {
			participant = teamService.getTeamById(participantId).get();
			return participant.quitTournament(tournament);
		} else {
			return false;
		}
	}
	
	
}
