package sieger.repository;

import java.util.List;
import java.util.Optional;

import sieger.model.Game;

public interface GameRepository {
	Optional<Game> retrieveGameById(String tournmanetId, String gameId);
	List<Game> retrieveMultipleGamesById(String[] ids);
	boolean createGame(String tournamentId, Game game);
	boolean updateGame(String tournamentId, String gameId, Game game);
	boolean deleteGmae(String tournamentId, String gameId);
}
