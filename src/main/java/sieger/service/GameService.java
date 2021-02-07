package sieger.service;

import java.util.List;
import java.util.Optional;

import sieger.model.Game;
import sieger.model.Tournament;
import sieger.repository.GameRepository;

public class GameService {
	private GameRepository gameRepository;
	
	private TournamentService tournamentService;

	public GameService(GameRepository gameRepository, TournamentService tournamentService) {
		super();
		this.gameRepository = gameRepository;
		this.tournamentService = tournamentService;
	}
	
	public List<Game> getAllGame(String currentUserId, String tournamentName) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		String[] ids = (String[]) tournament.getGameList().toArray();
		List<Game> gameList = gameRepository.retrieveMultipleGamesById(ids);
		return gameList;
		
	}
	
	public Optional<Game> getGameById(String currentUserId, 
			String tournamentName, String gameId) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		Optional<Game> gameOpt = gameRepository
				.retrieveGameById(tournament.getTournamentId(), gameId);
		if (gameOpt.isEmpty()) {
			//throw resource not found exception
		}
		return gameOpt;
	}
	
	public boolean updateGameById(String currentUserId, 
			String tournamentName, String gameId, Game game) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		if (!tournament.isAdmin(currentUserId)) {
			//throw forbidden exception
		}
		gameRepository.updateGame(tournament.getTournamentId(), gameId, game);
		return true;
	}
	
	public boolean createNewGame(String currentUserId, 
			String tournamentName, Game game) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		if (!tournament.isAdmin(currentUserId)) {
			//throw forbidden exception
		}
		gameRepository.createGame(tournament.getTournamentId(), game);
		return true;
	}
	
	public boolean deleteGame(String currentUserId, 
			String tournamentName, String gameId) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		if (!tournament.isAdmin(currentUserId)) {
			//throw forbidden exception
		}
		gameRepository.deleteGame(tournament.getTournamentId(), gameId);
		return true;
	}
	
}
