package sieger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Tournament implements Searchable {
	//random id 
	private String tournamentid;
	//detail of tournament
	private TournamentDetail tournamentDetail;
	//list of Games.
	private List<String> gameList;
	//list of participants
	private String[] participantList;
	//constructor
	public Tournament(int participantSize, TournamentDetail tournamentDetail) {
		this.tournamentid = randomId();
		this.tournamentDetail = tournamentDetail;
		this.gameList = new ArrayList<>();
		this.participantList = new String[participantSize];
	}
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//abstract methode
	abstract public void createGames();
	
	//check if participant list has place
	public boolean checkSize(){
		for(String item: participantList){
			if(item == null){
				return true;
			}
		}
		return false;
	}
	//Check if allow user to join
	public boolean allowUserToJoin(){
		if (tournamentDetail.getParticipantForm() == ParticipantForm.SINGLE){
			if (checkSize()){
				return true;
			}
		}
		return false;
	}
	//check if allow team to join
	public boolean allowTeamToJoin() {
		if(tournamentDetail.getParticipantForm() == ParticipantForm.TEAM) {
			if(checkSize()) {
				return true;
			}
		}
		return false;
	}
	//check if ready to be held
	public boolean readyToBeHeld() {
		if(!checkSize()) {
			return false;
		}else {
			return true;
		}
	}
	//check if is open
	public boolean isOpen() {
		if(tournamentDetail.getTournamentTypes() == TournamentTypes.OPEN) {
			return true;
		} else {
			return false;
		}
	}
	//check if given id is admin
	public boolean isAdmin(String userId) {
		if(userId.equals(tournamentDetail.getAdminId())) {
			return true;
		} else {
			return false;
		}
	}
	//implement gettitle()
	public String getTirle() {
		return "Tournament:" + this.tournamentDetail.getTournamentName();
	}
	//implement getInformation
	public String getInformation() {
		return "todo";
	}
	//get tournamentId
	public String getTournamentId() {
		return this.tournamentid;
	}
	//get detail
	public TournamentDetail getTournamentDetail() {
		return this.tournamentDetail;
	}
	//get game list
	public List<String> getGameList(){
		return this.gameList;
	}
	//get participant list
	public String[] getParticipantList() {
		return this.participantList;
	}
	//add game
	public void addGame(String gameId){
		gameList.add(gameId);
	}
	//remove gameid
	public void removeGame(String gameId){
		gameList.remove(gameId);
	}
	//add participant
	public void addParticipant(String participantId){
		for(String item: participantList){
			if(item == null){
				item = participantId;
				break;
			}
		}
	}
	//remove participant
	public void removeParticipant(String participantId){
		for(String item: participantList){
			if(item == participantId){
				item = null;
				break;
			}
		}
	}
}
