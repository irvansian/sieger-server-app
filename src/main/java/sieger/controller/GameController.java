package sieger.controller;

import java.util.List;

import sieger.model.Game;
import sieger.service.GameService;

public class GameController {
	private GameService gameService;
	
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	public List<Game> getAllGame(String tournamentId) {
		return null;
	}
	
	public Game getGameById(String tournamentId, String gameId) {
		return null;
	}
	
	public void updateGameById(String tournamentId, String gameId, Game game) {
		
	}
	
	public void createNewGame(String tournamentId, Game game) {
		
	}
	
	public void deleteGame(String gameId) {
		
	}
	
}
