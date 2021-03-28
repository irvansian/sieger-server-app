package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
/**
 * The league tournament class which entends tournament class.
 * 
 * @author Chen Zhang
 *
 */
@JsonTypeName("League")
public class League extends Tournament{
	/**
	 * The table that records the score of all participant.
	 */
	private LeagueTable leagueTable;
	/**
	 * No-argument constructor.
	 */
	public League() {
	}
	/**
	 * Constructor of league tournament class.
	 * 
	 * @param participantSize The max number of participant.
	 * @param name The name of tournament.
	 * @param tournamentDetail  Other details of this tournament.
	 */
	@JsonCreator
	public League(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.leagueTable = null;
	    setType("League");
	}
	/**
	 * Private method to create all the games.
	 * 
	 * @return Return the game in list.
	 */
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
	/**
	 * Private method to create a table for tournament.
	 */
	private void createTable() {
		setLeagueTable(new LeagueTable(getParticipantList()));
	}

	/**
	 * Private method to create game without setting time.
	 * 
	 * @return Return the game without time in list.
	 */
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
	/**
	 * Override the method in tournament class.
	 * 
	 * @return Return the new created game in list.
	 */
	@Override
	public List<Game> createGames() {
		if(super.getCurrentState() == TournamentState.START) {
			return createAllGames();
		} else {
			return null;
		}
	}
	/**
	 * Getter of league table
	 * 
	 * @return Return the table of tournament.
	 */
	public LeagueTable getLeagueTable() {
		return leagueTable;
	}
	/**
	 * Setter of league table.
	 * 
	 * @param table The table to be setted.
	 */
	public void setLeagueTable(LeagueTable table) {
		this.leagueTable = table;
	}
	/**
	 * Update the table with the given game.
	 * 
	 * @param game The given game.
	 */
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
