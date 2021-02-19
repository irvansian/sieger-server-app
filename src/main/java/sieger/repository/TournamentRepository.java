package sieger.repository;

import java.util.List;

import java.util.Optional;

import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Tournament;
/**
 * The tournament repository interface.The tournament database implements it.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
public interface TournamentRepository {
	/**
	 * Retrieve the tournament from firebase with id.
	 * 
	 * @param tournamentId The id of tournament.
	 * @return Return tournament optional after searching.
	 */
	Optional<Tournament> retrieveTournamentById(String tournamentId);
	/**
	 * Retrieve the tournament with name.
	 * 
	 * @param tournamentName Name of tournament to be searched.
	 * @return Return the tournament optional after searching.
	 */
	Optional<Tournament> retrieveTournamentByName(String tournamentName);
	/**
	 * Retrieve the participant list of tournament.
	 * If participant form is single, return a list of user objects.
	 * If participant form is team, return a list of team objects.
	 * 
	 * @param tournamentId The id of tournament.
	 * @param pf The participant form of tournament.
	 * @return Return required participant list.
	 */
	List<Participant> retrieveTournamentParticipants(String tournamentId, ParticipantForm pf);
	List<Tournament> retrieveMultipleTournamentsByKeyword(String keyword);
	/**
	 * Create a new tournament in firebase.
	 * 
	 * @param tournament The tournament object to be stored.
	 * @return Return true after put it in the firebase.
	 */
	boolean createTournament(Tournament tournament);
	/**
	 * Update the data of tournament in firebase.
	 * 
	 * @param tournamentId The id of tournament to be updated.
	 * @param tournament The tournament with data to be updated.
	 * @return Return true after updating.
	 */
	boolean updateTournament(String tournamentId, Tournament tournament);
	/**
	 * Delete the tournament data in firebase.
	 * 
	 * @param tournamentId The id of touenament to be deleted.
	 * @return Return true after delete the data.
	 */
	boolean deleteTournament(String tournamentId);
}
