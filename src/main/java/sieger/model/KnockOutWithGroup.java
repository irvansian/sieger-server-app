package sieger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;




@JsonTypeName("KnockOutWithGroup")
public class KnockOutWithGroup extends Tournament {

	//table list
	@JsonIgnore
	private List<LeagueTable> tables;
	private TournamentState currentState;
	//ko map
	@JsonIgnore
	private KnockOutMapping koMapping;
	//current games
	private List<Game> currentGames;
	public KnockOutWithGroup() {
		super();
	}

	@JsonCreator
	public KnockOutWithGroup(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		super(participantSize, name, tournamentDetail);
		this.tables = new ArrayList<>();
		this.koMapping = null;
		this.currentGames = null;
		setType("KnockOutWithGroup");
	}

	
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
	//first round of KO
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
	//create game of next round
	private List<Game> nextRoundGames() {
		int currentIndex = getGameList().size();
		List<Game> tempgames = new ArrayList<>();
		//create game without date
		for(int i = 0; i < currentGames.size();i = i + 2) {
			Game game = new Game(null, currentGames.get(i).returnWinnerId(), currentGames.get(i + 1).returnWinnerId());
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
		return tempgames;
	}
	//get winner of group phase
	private List<String> getWinnerOfGroup(){
		List<String> winners = new ArrayList<>();
		for(LeagueTable table: tables) {
			table.sort();
			winners.add(table.getTables().get(0).getParticipantId());
			winners.add(table.getTables().get(1).getParticipantId());
		}
		return winners;
	}
	//game to be planed
	private List<Game> createGameList(){
		divideParticipants();
		List<Game> games = new ArrayList<>();
		for(LeagueTable table: tables) {
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
	//Divide participant into groups
	private void divideParticipants() {
		List<String> tempParticipants = new ArrayList<>();
		for(String participant: getParticipantList()) {
			if(tempParticipants.size() < 4) {
				tempParticipants.add(participant);
			} else {
				addLeagueTable(new LeagueTable(tempParticipants));
				tempParticipants.clear();
			}
		}
		addLeagueTable(new LeagueTable(tempParticipants));
	}
	//add table
	public void addLeagueTable(LeagueTable table) {
		this.tables.add(table);
	}
	//remove table
	public void removeLeagueTable(LeagueTable tbale) {
		this.tables.remove(tbale);
	}
	//set current games
	public void setCurrentGames(List<Game> games) {
		this.currentGames = games;
	}
	//get current games
	public List<Game> getCurrentGames(){
		return this.currentGames;
	}
	//tournament finish
	private void isFinalRound() {
		if(this.currentGames.size() == 1) {
			setCurrentState(TournamentState.FINISH);
		}
	}

	@Override
	public List<Game> createGames() {
		if(this.currentState == TournamentState.START) {
			return createGroupGames();
		} else if (this.currentState == TournamentState.GROUP) {
			return createFirstKO();
		} else if(this.currentState == TournamentState.KOROUND) {
			return nextRoundGames();
		}
		return null;
	}

}
