package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("KnockOut")
public class KnockOut extends Tournament {
	 enum TournamentState{
		START,
		KOROUND,
		FINISH
	}
	//ko mapping
	@JsonIgnore
	private KnockOutMapping koMapping;
	private TournamentState currentState;
	//current games
	private List<Game> currentGames;
	public KnockOut() {
		super();
	}
	//constructor
	@JsonCreator
	public KnockOut(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.koMapping = new KnockOutMapping(participantSize / 2);
		this.currentGames = null;
		this.currentState = TournamentState.START;
	}

	@Override
	public List<Game> createGames() {
		if(readyToBeHeld()) {
			List<Game> games = createGameList();
			setCurrentGames(games);
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
			setCurrentState(TournamentState.KOROUND);
			return games;
		}
		return null;
	}
	//getter
	public KnockOutMapping getKoMapping() {
		return this.koMapping;
	}
	//create game of next round
	public void nextRoundGames() {
		int currentIndex = getGameList().size();
		List<Game> tempgames = new ArrayList<>();
		//create game without date
		for(int i = 0; i < currentGames.size();i = i + 2) {
			Game game = new Game(null, currentGames.get(i).getWinnerId(), currentGames.get(i + 1).getWinnerId());
			int newKey = (koMapping.getKeyByValue(currentGames.get(i).getGameId()) + koMapping.getKeyByValue(currentGames.get(i + 1).getGameId())) / 2;
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
	}
	//game to be planed
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
	//tournament finish
	public void isFinalRound() {
		if(this.currentGames.size() == 1) {
			setCurrentState(TournamentState.FINISH);
		}
	}
	//set current games
	public void setCurrentGames(List<Game> games) {
		this.currentGames = games;
	}
	//get current games
	public List<Game> getCurrentGames(){
		return this.currentGames;
	}
	public TournamentState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(TournamentState currentState) {
		this.currentState = currentState;
	}
}
