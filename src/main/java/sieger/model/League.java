package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("League")
public class League extends Tournament{

	//table
	private LeagueTable leagueTable;
	
	
	public League() {
	}

	//constructor
	@JsonCreator
	public League(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.leagueTable = null;
	    setType("League");
	}

	private List<Game> createAllGames() {
		if(readyToBeHeld()) {
			createTable();
			List<Game> games = createGameList();
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
			setCurrentState(TournamentState.FINISH);
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
	


	@Override
	public List<Game> createGames() {
		if(super.getCurrentState() == TournamentState.START) {
			return createAllGames();
		}
		return null;
	}

	public LeagueTable getLeagueTable() {
		return leagueTable;
	}

	public void setLeagueTable(LeagueTable table) {
		this.leagueTable = table;
	}

	@Override
	public void updateGame(Game game) {
		if(game.returnWinnerId() != null) {
			String winner = game.returnWinnerId();
			String loser;
			if(game.getFirstParticipantId().equals(winner)) {
				loser = game.getSecondParticipantId();
			} else {
				loser = game.getFirstParticipantId();
			}
			getLeagueTable().participantWin(winner);
			getLeagueTable().participantLose(loser);
		} else {
			getLeagueTable().participantDraw(game.getFirstParticipantId());
			getLeagueTable().participantDraw(game.getSecondParticipantId());
		}
		getLeagueTable().sort();
		
	}


	
}
