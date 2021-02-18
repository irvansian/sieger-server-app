package sieger.model;

import java.util.List;
/**
 * The participant class.
 * 
 * @author Chen Zhang
 *
 */
public abstract class Participant {
	/**
	 * The list of tournaments that participant took part in.
	 */
	public List<String> tournamentList;
	/**
	 * Get the name of participant.
	 * 
	 * @return Return the name of participant.
	 */
	abstract public String findParticipantName();
	/**
	 * Participant joins a tournament.
	 * 
	 * @param tournament The tournament to be joined in.
	 * @return Return true if success.
	 */
	abstract public boolean joinTournament(Tournament tournament);
	/**
	 * Participant quits a tournament.
	 * 
	 * @param tournament The tournament from that participant quits.
	 * @return Return true if succedd.
	 */
	abstract public boolean quitTournament(Tournament tournament);
	/**
	 * Add new tournament to the tournament list.
	 * 
	 * @param tournamentId The id of new tournament.
	 */
	public void addTournament(String tournamentId) {
		tournamentList.add(tournamentId);
	}
	/**
	 * Remove the tournament from the list.
	 * 
	 * @param tournamentId The id of the tournament.
	 */
	public void removeTournament(String tournamentId) {
		tournamentList.remove(tournamentId);
	}
	/**
	 * Getter of the tournament list.
	 * 
	 * @return Retuen the list of tournament ids.
	 */
	public List<String> getTournamentList(){
		return this.tournamentList;
	}
}
