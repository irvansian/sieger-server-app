package sieger.service;

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
		return false;
	}
	
	public boolean quitTournament(String participantId, String tournamentId) {
		return false;
	}
	
	
}
