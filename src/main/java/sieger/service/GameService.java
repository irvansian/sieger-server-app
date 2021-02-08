package sieger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Game;
import sieger.model.Tournament;
import sieger.payload.ApiResponse;
import sieger.repository.GameRepository;

@Service
public class GameService {
	private GameRepository gameRepository;
	
	private TournamentService tournamentService;

	@Autowired
	public GameService(GameRepository gameRepository, TournamentService tournamentService) {
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
			throw new ResourceNotFoundException("Game", "id", gameId);
		}
		return gameOpt;
	}
	
	public boolean updateGameById(String currentUserId, 
			String tournamentName, String gameId, Game game) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to update this game.");
			throw new ForbiddenException(response);
		}
		gameRepository.updateGame(tournament.getTournamentId(), gameId, game);
		return true;
	}
	
	public boolean createNewGame(String currentUserId, 
			String tournamentName, Game game) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to create a game in <" + tournamentName + "> tournament.");
			throw new ForbiddenException(response);
		}
		gameRepository.createGame(tournament.getTournamentId(), game);
		return true;
	}
	
	public boolean deleteGame(String currentUserId, 
			String tournamentName, String gameId) {
		Tournament tournament = tournamentService
				.getTournamentByName(currentUserId, tournamentName).get();
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to delete a game in <" + tournamentName + "> tournament.");
			throw new ForbiddenException(response);
		}
		gameRepository.deleteGame(tournament.getTournamentId(), gameId);
		return true;
	}
	
}
