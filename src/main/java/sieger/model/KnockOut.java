package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The kncok out tournament which extends tournament.
 * 
 * @author Chen Zhang
 *
 */
@JsonTypeName("KnockOut")
public class KnockOut extends Tournament {
	/**
	 * The KO map which records the id of games in a map.
	 */
	private KnockOutMapping koMapping;
	/**
	 * The list of game in current round.
	 */
	private List<Game> currentGames;
	/**
	 * No-argument of KnockOut class.
	 */
	public KnockOut() {
		super();
	}
	/**
	 * Constructor of a knock out tournament.
	 * 
	 * @param participantSize The max size of participant.
	 * @param name The name of the tournament.
	 * @param tournamentDetail Other details of tournament.
	 */
	@JsonCreator
	public KnockOut(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.setKoMapping(new KnockOutMapping(participantSize));
		this.currentGames = null;
		setType("KnockOut");
	}
	/**
	 * Private method to create game in first round.
	 * 
	 * @return Return games of first round in list.
	 */
	private List<Game> createFirstRoundGames() {
		if(readyToBeHeld()) {
			List<Game> games = createGameList();
			setCurrentGames(games);
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
			isFinalRound();
			return games;
		}
		return null;
	}
	/**
	 * Getter of knock out map.
	 * 
	 * @return Return the knock out map of tournament.
	 */
	public KnockOutMapping getKoMapping() {
		return this.koMapping;
	}
	/**
	 * Setter of knock out map.
	 * 
	 * @param koMapping The knock out map to be setted.
	 */
	public void setKoMapping(KnockOutMapping koMapping) {
		this.koMapping = koMapping;
	}
	/**
	 * Private method to create games of next round, based on the current game list.
	 * 
	 * @return Return the game of next round in list.
	 */
	private List<Game> nextRoundGames() {
		int currentIndex = getGameList().size();
		List<Game> tempgames = new ArrayList<>();
		//create game without date
		for(int i = 0; i < currentGames.size();i = i + 2) {
			Game game = new Game(null, currentGames.get(i).returnWinnerId(), currentGames.get(i + 1).returnWinnerId());
			int newKey = (Integer.parseInt(koMapping.getKeyByValue(currentGames.get(i).getGameId())) 
					+ Integer.parseInt(koMapping.getKeyByValue(currentGames.get(i + 1).getGameId()))) / 2;
			koMapping.mapGameToKOBracket(newKey, game.getGameId());
			tempgames.add(game);
		    getGameList().add(game.getGameId());
		}
		//add date to each game
		for(int i = currentIndex;i < getGameList().size();i++) {
			tempgames.get(i - currentIndex).setTime(calculateDate(i));
		}
		setCurrentGames(tempgames);
		isFinalRound();
		return tempgames;
	}
	/**
	 * Private method to create game without setting the time.
	 * 
	 * @return Return the game without time in a list.This method will be called in create first round.
	 */
	private List<Game> createGameList(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < getParticipantList().size(); i = i + 2) {
			Game game = new Game(null, getParticipantList().get(i), getParticipantList().get(i + 1));
			games.add(game);
			koMapping.mapGameToKOBracket(i + 1, game.getGameId());
			getGameList().add(game.getGameId());
		}
		return games;
	}
	/**
	 * Private method to check if it is final round. If yes, the state will be changed to finish.
	 */
	private void isFinalRound() {
		if(this.currentGames.size() == 1) {
			setCurrentState(TournamentState.FINISH);
		} else {
			setCurrentState(TournamentState.KOROUND);
		}
	}
	/**
	 * Setter of the current games
	 * 
	 * @param games The new current games.
	 */
	public void setCurrentGames(List<Game> games) {
		this.currentGames = games;
	}
	/**
	 * Getter of current games.
	 * 
	 * @return Return the current games in a list.
	 */
	public List<Game> getCurrentGames(){
		return this.currentGames;
	}
	/**
	 * Override the method in tournament class.
	 * Based on different state of tournament, different kinds of method will be called to create new games.
	 * 
	 * @return Return the new created game in list.
	 */
	@Override
	public List<Game> createGames() {
		if(getCurrentState() == TournamentState.START) {
			return createFirstRoundGames();
		} else if (getCurrentState() == TournamentState.KOROUND) {
			return nextRoundGames();
		}
		return null;
	}
	/**
	 * Update the game. The result of the game will be added to game in current game list.
	 * 
	 * @param game The given game.
	 */
	@Override
	public void updateGame(Game game) {
		for(Game tempGame: currentGames) {
			if(tempGame.getGameId().equals(game.getGameId())) {
				tempGame.setResult(game.getResult());
			}
		}
	}
}
