package sieger.model;

import java.util.ArrayList;
import java.util.List;

public class League extends Tournament{
	//table
	private LeagueTable table;
	public League() {
		super();
	}
	//constructor
	public League(int participantSize, String name, TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.table = null;
	}

	@Override
	public void createGames() {
		if(readyToBeHeld()) {
			createTable();
			List<Game> games = createGameList();
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
		}		
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
