package sieger.model;
/**
 * The possible state of tournament.
 * 
 * @author Chen Zhang
 *
 */
public enum TournamentState {
	/**
	 * The tournament starts.
	 */
	START,
	/**
	 * The tournament is in KO round.
	 */
	KOROUND,
	/**
	 * The tournament is in group phase.
	 */
	GROUP,
	/**
	 * The tournament is finished or dont create new games.
	 */
	FINISH
}
