package sieger.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The knock out with group class which extends the tournament clas.
 * 
 * @author Chen Zhang
 *
 */
@JsonTypeName("KnockOutWithGroup")
public class KnockOutWithGroup extends Tournament {
	/**
	 * The tables of all groups. Each group has a table to record.
	 */
	private Map<Integer, LeagueTable> tables;
	/**
	 * The knock out map in ko round.
	 */
	private KnockOutMapping koMapping;
	/**
	 * The games in current round in list.
	 */
	private List<Game> currentGames;
	/**
	 * No-argument constructor.
	 */
	public KnockOutWithGroup() {
		super();
	}
	/**
	 * Constructor of a knock out with group class.
	 * The size of each group is always four.
	 * The first and the second in each group will attend the ko round.
	 * 
	 * @param participantSize The size of participant.
	 * @param name The name of tournament.
	 * @param tournamentDetail Other details of this tournament.
	 */
	@JsonCreator
	public KnockOutWithGroup(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.tables = new HashMap<>();
		this.koMapping = null;
		this.currentGames = null;
		setType("KnockOutWithGroup");
	}
	/**
	 * Private method to create games for group phase.
	 * 
	 * @return The game in group phase in list.
	 */
	private List<Game> createGroupGames() {
		if(readyToBeHeld()) {
			List<Game> games = createGameList();
			for(int i = 0; i < games.size(); i++) {
				games.get(i).setTime(calculateDate(i));
			}
			setCurrentState(TournamentState.GROUP);
			return games;
		}
		return null;
		
	}
	/**
	 * Private method to create the games in first round in KO phase.
	 * 
	 * @return Return the game in first round of KO round in list.
	 */
	private List<Game> createFirstKO() {
		List<String> participants = getWinnerOfGroup();
		this.koMapping = new KnockOutMapping(participants.size() / 2);
		int currentIndex = getGameList().size();
		//create game without data
		List<Game> games = new ArrayList<>();
		for(int i = 0; i < participants.size(); i = i + 2) {
			Game game = new Game(null, participants.get(i), participants.get(i + 1));
			games.add(game);
			koMapping.mapGameToKOBracket(i + 1, game.getGameId());
			getGameList().add(game.getGameId());
		}
		//set date for game
		for(int i = currentIndex;i < getGameList().size();i++) {
			games.get(i - currentIndex).setTime(calculateDate(i));
		}
		setCurrentGames(games);
		setCurrentState(TournamentState.KOROUND);
		isFinalRound();
		return games;
	}
	/**
	 * Private method to create games in next round of ko phase.
	 * 
	 * @return Return the games of next round in list.
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
	 * Private method to get the winners of groups.
	 * 
	 * @return Return the id of the winners of groups in list.
	 */
	private List<String> getWinnerOfGroup(){
		List<String> winners = new ArrayList<>();
		for(LeagueTable table: tables.values()) {
			table.sort();
			winners.add(table.getTables().get(0).getParticipantId());
			winners.add(table.getTables().get(1).getParticipantId());
		}
		return winners;
	}
	/**
	 * Private method to create games in group phase without setting the time.
	 * This method will be called in createGroupGames.
	 * 
	 * @return Return the game in list.
	 */
	private List<Game> createGameList(){
		divideParticipants();
		List<Game> games = new ArrayList<>();
		for(LeagueTable table: tables.values()) {
			List<ParticipantActualStanding> participants = table.getTables();
			Game game1 = new Game(null, participants.get(0).getParticipantId(), participants.get(1).getParticipantId());
			Game game2 = new Game(null, participants.get(0).getParticipantId(), participants.get(2).getParticipantId());
			Game game3 = new Game(null, participants.get(0).getParticipantId(), participants.get(3).getParticipantId());
			Game game4 = new Game(null, participants.get(1).getParticipantId(), participants.get(2).getParticipantId());
			Game game5 = new Game(null, participants.get(1).getParticipantId(), participants.get(3).getParticipantId());
			Game game6 = new Game(null, participants.get(2).getParticipantId(), participants.get(3).getParticipantId());
			games.add(game1);
			games.add(game2);
			games.add(game3);
			games.add(game4);
			games.add(game5);
			games.add(game6);
			getGameList().add(game1.getGameId());
			getGameList().add(game2.getGameId());
			getGameList().add(game3.getGameId());
			getGameList().add(game4.getGameId());
			getGameList().add(game5.getGameId());
			getGameList().add(game6.getGameId());
		}
		return games;
	}
	/**
	 * Private method to divide the participants into 4-er groups.
	 */
	private void divideParticipants() {
		List<String> tempParticipants = new ArrayList<>();
		int tableIndex = 1;
		for(String participant: getParticipantList()) {
			if(tempParticipants.size() < 4) {
				tempParticipants.add(participant);
			} else {
				mapLeagueTable(tableIndex, new LeagueTable(tempParticipants));
				tableIndex++;
				tempParticipants.clear();
				tempParticipants.add(participant);
			}
		}
		mapLeagueTable(tableIndex, new LeagueTable(tempParticipants));
	}
	/**
	 * map a new league table to the table list.
	 * 
	 * @param index Index of new table.
	 * @param table The table to be added.
	 */
	public void mapLeagueTable(int index, LeagueTable table) {
		this.tables.put(index, table);
	}
	/**
	 * Setter of current games.
	 * 
	 * @param games New game list to be setted.
	 */
	public void setCurrentGames(List<Game> games) {
		this.currentGames = games;
	}
	/**
	 * Getter of current games.
	 * 
	 * @return Return the current games.
	 */
	public List<Game> getCurrentGames(){
		return this.currentGames;
	}
	/**
	 * Private method to check if it is final round.
	 * If yes, the state of tournament will be changed to finish.
	 */
	private void isFinalRound() {
		if(this.currentGames.size() == 1) {
			setCurrentState(TournamentState.FINISH);
		}
	}
	/**
	 * Override the method in tournament class.
	 * Based on different state of tournament, different method of creating new games will be called.
	 * 
	 * @return Return the new created game in list.
	 */
	@Override
	public List<Game> createGames() {
		if(getCurrentState() == TournamentState.START) {
			return createGroupGames();
		} else if (getCurrentState() == TournamentState.GROUP) {
			return createFirstKO();
		} else if(getCurrentState() == TournamentState.KOROUND) {
			return nextRoundGames();
		}
		return null;
	}
	/**
	 * Getter of knock out map.
	 * 
	 * @return Return the knock out map.
	 */
	public KnockOutMapping getKoMapping() {
		return this.koMapping;
	}
	/**
	 * Setter of the knock out map.
	 * 
	 * @param koMapping New knock out map to be setted.
	 */
	public void setKoMapping(KnockOutMapping koMapping) {
		this.koMapping = koMapping;
	}
	/**
	 * Getter of tables.
	 * 
	 * @return Return the list of table.
	 */
	public Map<Integer, LeagueTable> getTables(){
		return this.tables;
	}
	/**
	 * Setter of tables.
	 * 
	 * @param tables New tables to be setted.
	 */
	public void setTables(Map<Integer, LeagueTable> tables) {
		this.tables = tables;
	}
	/**
	 * Override the method in tournament class.
	 * If it is in group phase, the table will be updated based on the given game.
	 * If it is in Ko round, the result will be added to the game in current games.
	 * 
	 * @param game 
	 */
	@Override
	public void updateGame(Game game) {
		if(getCurrentState() == TournamentState.GROUP) {
			if(game.returnWinnerId() != null) {
				String winner = game.returnWinnerId();
				String loser;
				if(game.getFirstParticipantId().equals(winner)) {
					loser = game.getSecondParticipantId();
				} else {
					loser = game.getFirstParticipantId();
				}
				for(LeagueTable table: tables.values()) {
					if(table.getParticipantStandingById(winner) != null) {
						table.participantWin(winner);
						table.participantLose(loser);
						table.sort();
						break;
					}
				}
			} else {
				for(LeagueTable table: tables.values()) {
					if(table.getParticipantStandingById(game.getFirstParticipantId()) != null) {
						table.participantDraw(game.getFirstParticipantId());
						table.participantDraw(game.getSecondParticipantId());
						table.sort();
						break;
					}
				}
			}
			
		} else {
			for(Game tempGame: currentGames) {
				if(tempGame.getGameId().equals(game.getGameId())) {
					tempGame = game;
					break;
				}
			}
		}
		
	}

}
