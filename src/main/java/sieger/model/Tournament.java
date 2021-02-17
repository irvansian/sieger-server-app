package sieger.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import sieger.model.League.TournamentState;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "League", value = League.class),
        @JsonSubTypes.Type(name = "KnockOut", value = KnockOut.class),
        @JsonSubTypes.Type(name = "KnockOutWithGroup", value = KnockOutWithGroup.class),
})
public abstract class Tournament implements Searchable {

	//random id 
	private String tournamentid;
	//detail of tournament
	private TournamentDetail tournamentDetail;
	//list of Games.
	private List<String> gameList;
	//list of participants
	private List<String> participantList;
	//temp notification
	private List<Notification> notificationList;
    //name of tournament
	private String tournamentName;
    //mac number
	private int maxParticipantNumber;
	private String type;

	public Tournament() {
		
	}
	//constructor
	@JsonCreator
	public Tournament(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		this.tournamentDetail = tournamentDetail;
		this.gameList = new ArrayList<>();
		this.tournamentid = randomId();
		this.notificationList = new ArrayList<>();
		this.participantList = new ArrayList<String>();
		this.tournamentName = name;
		this.maxParticipantNumber = participantSize;
		
	}
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//abstract methode
	public abstract List<Game> createGames();
	//check if user participate the tournament
	public boolean isParticipant(User user) {
		if(tournamentDetail.getParticipantForm() == ParticipantForm.SINGLE) {
			return participateAsSingle(user);
		} else if(tournamentDetail.getParticipantForm() == ParticipantForm.TEAM) {
			return participateAsTeam(user);
		}
		return false;
	}
	//if user participate as single.
	private boolean participateAsSingle(User user) {
		String userId = user.getUserId();
		return participantList.contains(userId);
	}
	//if user participate as team
	private boolean participateAsTeam(User user) {
		for(String teamId: user.getTeamList()) {
			if(participantList.contains(teamId)) {
				return true;
			}
		}
		return false;
	}
	//check if participant list has place
	private boolean checkSize(){
		if(participantList.size() < this.maxParticipantNumber) {
			return true;
		} else {
			return false;
		}
	}
	//Check if allow user to join
	public boolean allowUserToJoin(){
		if(tournamentDetail.getParticipantForm() == ParticipantForm.SINGLE) {
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
		if(!checkSize() && getTournamentDetail().getRegistrationDeadline().before(new Date())) {
			return true;
		}else {
			return false;
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
	public String Title() {
		return "Tournament:" + getTournamentName();
	}
	//implement getInformation
	public String Information() {
		return  "Tournament:" + getTournamentName() 
		+ "\r\n" + "ParticipantForm:" + tournamentDetail.getParticipantForm() 
		+ "\r\n" + "Location:" + tournamentDetail.getLocation()
		+ "\r\n" + "Game:" + tournamentDetail.getTypeOfGame()
		+ "\r\n" + "Time: from" + tournamentDetail.getStartTime() + "to" + tournamentDetail.getEndTime()
		+ "\r\n" + "Register: before" + tournamentDetail.getRegistrationDeadline().toString();
	}
	//calculate the Date of game
	public Date calculateDate(int index) {
		Date date = this.tournamentDetail.getStartTime();
		int temp = index / calculateCapacity();
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(Calendar.DATE,temp); 
		date = calendar.getTime(); 
		return date;
	}
	//get capacity
	private int calculateCapacity() {
		int daysBetween = daysBetween(this.tournamentDetail.getStartTime(), this.tournamentDetail.getEndTime());
		if(this.maxParticipantNumber % daysBetween == 0) {
			return (maxParticipantNumber / daysBetween);
		} else {
			return (maxParticipantNumber / daysBetween) + 1;
		}
	}
	private int daysBetween(Date smdate,Date bdate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);
		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}
	//get maxparticipantnumber
	public int getMaxParticipantNumber() {
		return this.maxParticipantNumber;
	}
	//get tournamentId
	public String getTournamentId() {
		return this.tournamentid;
	}
	//get name of tournament
    public String getTournamentName(){
        return this.tournamentName;
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
	public List<String> getParticipantList() {
		return this.participantList;
	}
	//get notificationList
	public List<Notification> getNotificationList(){
		return this.notificationList;
	}

	//set detail
	public void setTournamentDetail(TournamentDetail detail) {
		this.tournamentDetail = detail;
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
		participantList.add(participantId);
	}
	//remove participant
	public void removeParticipant(String participantId){
		participantList.remove(participantId);
	}
	//add notification
	public void addNotification(Notification notification) {
		notificationList.add(notification);
	}
	//remove notification
	public void removeNotification(Notification notification) {
		notificationList.remove(notification);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public abstract String getState();
}
