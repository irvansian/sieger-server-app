package sieger.payload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sieger.model.TournamentDetail;
import sieger.model.TournamentState;

public class TournamentDTO {
	private String tournamentid;
	private TournamentDetail tournamentDetail;
	private List<String> gameList;
	private List<String> participantList;
	private String tournamentName;
	private int maxParticipantNumber;
	private String type;
	private TournamentState currentState;
	private boolean open;
	private Map<String, Object> specifiedAttributes = new HashMap<String, Object>();
	
	public String getTournamentId() {
		return tournamentid;
	}
	public void setTournamentId(String tournamentId) {
		this.tournamentid = tournamentId;
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
	public Map<String, Object> getSpecifiedAttributes() {
		return specifiedAttributes;
	}
	public void setSpecifiedAttributes(Map<String, Object> specifiedAttributes) {
		this.specifiedAttributes = specifiedAttributes;
	}
	
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}
