package sieger.service;

import java.util.List;
import java.util.Optional;

import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.repository.TournamentRepository;

public class TournamentService {
	private TournamentRepository tournamentRepository;
	private UserService userService;
	private TeamService teamService;
	
	public TournamentService(TournamentRepository tournamentRepository, UserService userService,
			TeamService teamService) {
		this.tournamentRepository = tournamentRepository;
		this.userService = userService;
		this.teamService = teamService;
	}
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	
	public Optional<Tournament> getTournamentById(String currentUserId, 
			String tournamentId) {
		return null;
	}
	
	
	public Optional<Tournament> getTournamentByName(String tournamentName) {
		return tournamentRepository.retrieveTournamentByName(tournamentName);
	}
	
	public List<Participant> getTournamentParticipants(String tournamentName) {
		Optional<Tournament> tournament = getTournamentByName(tournamentName);
		if (tournament.isEmpty()) {
			//throw exception
		}
		String tournamentId = tournament.get().getTournamentId();
		ParticipantForm pf = tournament.get().getTournamentDetail()
				.getParticipantForm();
		return tournamentRepository.retrieveTournamentParticipants(tournamentId, pf);
	}
	
	public boolean createNewTournament(Tournament tournament) {
		return false;
	}
	
	public boolean updateTournamentDetailById(String tournamentId, TournamentDetail tournamentDetail) {
		return false;
	}
	
	public boolean updateTournamentById(String tournamentId, Tournament tournament) {
		return false;
	}
	
	public boolean deleteTournament(String tournmaentId) {
		return false;
	}	
	
}
