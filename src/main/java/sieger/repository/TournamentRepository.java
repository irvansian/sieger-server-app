package sieger.repository;

import java.util.List;
import java.util.Optional;

import sieger.model.Tournament;

public interface TournamentRepository {
	Optional<Tournament> retrieveTournamentById(String tournamentId);
	Optional<Tournament> retrieveTournamentByName(String tournamentName);
	List<Tournament> retrieveMultipleTournamentsByKeyword(String keyword);
	boolean createTournament(Tournament tournament);
	boolean updateTournament(String tournamentId, Tournament tournament);
	boolean deleteTournament(String tournamentId);
}
