package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("KnockOut")
public class KnockOut extends Tournament {
	//ko mapping

	private KnockOutMapping koMapping;
	
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
		setType("KnockOut");
		
	}

	
	private List<Game> createFirstRoundGames() {
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
	public void setKoMapping(KnockOutMapping koMapping) {
		this.koMapping = koMapping;
	}
	//create game of next round
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
	private void isFinalRound() {
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
	
	@Override
	public List<Game> createGames() {
		if(getCurrentState() == TournamentState.START) {
			return createFirstRoundGames();
		} else if (getCurrentState() == TournamentState.KOROUND) {
			return nextRoundGames();
		}
		return null;
	}
	@Override
	public void updateGame(Game game) {
		for(Game tempGame: currentGames) {
			if(tempGame.getGameId().equals(game.getGameId())) {
				tempGame.setResult(game.getResult());
				break;
			}
		}
	}
}