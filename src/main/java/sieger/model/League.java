package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("League")
public class League extends Tournament{
	//table
	private LeagueTable table;
	
	public League() {
	}

	//constructor
	@JsonCreator
	public League(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.table = null;
	}

	@Override
	public List<Game> createGames() {
		if(readyToBeHeld()) {
			createTable();
			List<Game> games = createGameList();
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
			return games;
		}
		return null;
	}
	//create the table
	private LeagueTable createTable() {
		this.table = new LeagueTable(getParticipantList());
		return this.table;
	}
	//get table
	public LeagueTable getLeagueTable() {
		return this.table;
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
	
}
