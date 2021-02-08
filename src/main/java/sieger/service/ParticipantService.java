package sieger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sieger.model.Participant;
import sieger.model.Tournament;

@Service
public class ParticipantService {
	private UserService userService;
	private TeamService teamService;
	private TournamentService tournamentService;
	
	@Autowired
	public ParticipantService(UserService userService, TeamService teamService, TournamentService tournamentService) {
		this.userService = userService;
		this.teamService = teamService;
		this.tournamentService = tournamentService;
	}
	
	public boolean joinTournament(String participantId, String tournamentId) {
		Tournament tournament = tournamentService.getTournamentById(participantId,tournamentId).get();
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
		Tournament tournament = tournamentService.getTournamentById(participantId,tournamentId).get();
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
