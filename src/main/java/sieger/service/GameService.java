package sieger.service;

import java.util.List;
import java.util.Optional;

import sieger.model.Game;
import sieger.repository.GameRepository;

public class GameService {
	private GameRepository gameRepository;
	
	private TournamentService tournamentService;

	public GameService(GameRepository gameRepository, TournamentService tournamentService) {
		super();
		this.gameRepository = gameRepository;
		this.tournamentService = tournamentService;
	}
	
	public List<Game> getAllGame(String tournamentId) {
		return null;
	}
	
	public Optional<Game> getGameById(String tournamentId, String gameId) {
		return null;
	}
	
	public boolean updateGameById(String tournamentId, String gameId, Game game) {
		return false;
	}
	
	public boolean createNewGame(String tournamentId, Game game) {
		return false;
	}
	
	public boolean deleteGame(String gameId) {
		return false;
	}
	
}
