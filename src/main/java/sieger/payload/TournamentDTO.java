package sieger.payload;

import java.util.List;

import sieger.model.KnockOutMapping;
import sieger.model.LeagueTable;
import sieger.model.TournamentDetail;
import sieger.model.TournamentState;

public class TournamentDTO {
	private String tournamentId;
	private TournamentDetail tournamentDetail;
	private List<String> gameList;
	private List<String> participantList;
	private String tournamentName;
	private int maxParticipantNumber;
	private String type;
	private TournamentState currentState;
	private LeagueTable table;
	private List<LeagueTable> groupTables;
	private KnockOutMapping koMapping;
	public String getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(String tournamentId) {
		this.tournamentId = tournamentId;
	}
	public TournamentDetail getTournamentDetail() {
		return tournamentDetail;
	}
	public void setTournamentDetail(TournamentDetail tournamentDetail) {
		this.tournamentDetail = tournamentDetail;
	}
	public List<String> getGameList() {
		return gameList;
	}
	public void setGameList(List<String> gameList) {
		this.gameList = gameList;
	}
	public List<String> getParticipantList() {
		return participantList;
	}
	public void setParticipantList(List<String> participantList) {
		this.participantList = participantList;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	public int getMaxParticipantNumber() {
		return maxParticipantNumber;
	}
	public void setMaxParticipantNumber(int maxParticipantNumber) {
		this.maxParticipantNumber = maxParticipantNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public TournamentState getCurrentState() {
		return currentState;
	}
	public void setCurrentState(TournamentState currentState) {
		this.currentState = currentState;
	}
	public LeagueTable getTable() {
		return table;
	}
	public void setTable(LeagueTable table) {
		this.table = table;
	}
	public List<LeagueTable> getGroupTables() {
		return groupTables;
	}
	public void setGroupTables(List<LeagueTable> groupTables) {
		this.groupTables = groupTables;
	}
	public KnockOutMapping getKoMapping() {
		return koMapping;
	}
	public void setKoMapping(KnockOutMapping koMapping) {
		this.koMapping = koMapping;
	}
	
	
}
