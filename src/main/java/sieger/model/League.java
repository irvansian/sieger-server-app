package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("League")
public class League extends Tournament{
	public enum TournamentState{
		START,
		FINISH
	}
	//table
	private LeagueTable table;
	//
	private TournamentState currentState;
	
	public League() {
	}

	//constructor
	@JsonCreator
	public League(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.table = null;
	    setCurrentState(TournamentState.START);
	    setType("League");
	}

	private List<Game> createAllGames() {
		if(readyToBeHeld()) {
			createTable();
			List<Game> games = createGameList();
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
			this.setCurrentState(TournamentState.FINISH);
			return games;
		}
		return null;
	}
	//create the table
	private void createTable() {
		setLeagueTable(new LeagueTable(getParticipantList()));
	}

	//game to be planed
	private List<Game> createGameList(){
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < getParticipantList().size(); i++) {
			for (int j = i + 1; j < getParticipantList().size(); j++) {
				Game game = new Game(null, getParticipantList().get(i), getParticipantList().get(j));
				games.add(game);
				getGameList().add(game.getGameId());
			}
		}
		return games;
	}
	//set table
	public void setLeagueTable(LeagueTable table) {
		this.table = table;
	}

	public TournamentState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(TournamentState currentState) {
		this.currentState = currentState;
	}

	@Override
	public List<Game> createGames() {
		if(this.currentState == TournamentState.START) {
			return createAllGames();
		}
		return null;
	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return "";
	}
	
}
