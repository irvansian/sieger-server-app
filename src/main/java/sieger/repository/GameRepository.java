package sieger.repository;

import java.util.Optional;

import sieger.model.Game;
/**
 * The game repository interface.The game database implements it.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
public interface GameRepository {
	/**
	 * Retrieve the game from firebase with id.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param gameId The id of game.
	 * @return Return game optional after searching.
	 */
	Optional<Game> retrieveGameById(String tournmanetId, String gameId);
	/**
	 * Create a new game in firebase.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param game The team object to be stored.
	 * @return Return true after put it in the firebase.
	 */
	boolean createGame(String tournamentId, Game game);
	/**
	 * Update the data of game in firebase.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param gameId The id of game to be updated.
	 * @param game The game with data to be updated.
	 * @return Return true after updating.
	 */
	boolean updateGame(String tournamentId, String gameId, Game game);
	/**
	 * Delete the game data in firebase.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param gameId The id of game to be deleted.
	 * @return Return true after delete the data.
	 */
	boolean deleteGame(String tournamentId, String gameId);
}
