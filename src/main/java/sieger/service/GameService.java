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
	
	public List<Game> getAllGame(String tournamentName) {
		return null;
	}
	
	public Optional<Game> getGameById(String tournamentName, String gameId) {
		return null;
	}
	
	public boolean updateGameById(String tournamentName, String gameId, Game game) {
		return false;
	}
	
	public boolean createNewGame(String tournamentName, Game game) {
		return false;
	}
	
	public boolean deleteGame(String tournamentName, String gameId) {
		return false;
	}
	
}
