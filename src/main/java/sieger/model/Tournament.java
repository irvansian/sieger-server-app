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
/**
 * The tournament class.
 * 
 * @author Chen Zhang
 *
 */
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

	/**
	 * The id of tournament.
	 */
	private String tournamentid;
	/**
	 * Other detail of tournament.
	 */
	private TournamentDetail tournamentDetail;
	/**
	 * List of all the game id.
	 */
	private List<String> gameList;
	/**
	 * List of participants.
	 */
	private List<String> participantList;
    /**
     * Name of tournament.
     */
	private String tournamentName;
    /**
     * Number of participant.
     */
	private int maxParticipantNumber;
	/**
	 * Type of tournament.Only used for serialization so never used locally.
	 */
	@SuppressWarnings("unused")
	private String type;
	/**
	 * The current state of tournament.
	 */
	private TournamentState currentState;
	/**
	 * No-argument constructor.
	 */
	public Tournament() {
		
	}
	/**
	 * Constructor of tournament. The id will be created automatically.
	 * 
	 * @param participantSize The size of participant.
	 * @param name The name of tournament.
	 * @param tournamentDetail The detail of tournamet.
	 */
	@JsonCreator
	public Tournament(@JsonProperty("participantSize")int participantSize, @JsonProperty("name")String name, @JsonProperty("tournamentDetail")TournamentDetail tournamentDetail) {
		this.setTournamentDetail(tournamentDetail);
		this.gameList = new ArrayList<>();
		this.tournamentid = randomId();
		this.participantList = new ArrayList<String>();
		this.setTournamentName(name);
		this.setMaxParticipantNumber(participantSize);
		this.setCurrentState(TournamentState.START);
		
	}
	/**
	 * Create the random id for tournament by UUID.
	 * 
	 * @return Return the id as string.
	 */
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	/**
	 * Create gems based on the current state.
	 * 
	 * @return Return the list of games.
	 */
	public abstract List<Game> createGames();
	/**
	 * Update the table of Ko map with the given game.
	 * 
	 * @param game The given game.
	 */
	public abstract void updateGame(Game game);
	/**
	 * To check if the user is participant of tournament.
	 * 
	 * @param user The given user.
	 * @return Return true if the user is participant.
	 */
	public boolean isParticipant(User user) {
		if(tournamentDetail.getParticipantForm() == ParticipantForm.SINGLE) {
			return participateAsSingle(user);
		} else if(tournamentDetail.getParticipantForm() == ParticipantForm.TEAM) {
			return participateAsTeam(user);
		}
		return false;
	}
	/**
	 * Private method to check if the user participate as single.
	 * 
	 * @param user The given user.
	 * @return Return true if user participate as single.
	 */
	private boolean participateAsSingle(User user) {
		String userId = user.getUserId();
		return participantList.contains(userId);
	}
	/**
	 * Private method to check if the user participate as team.
	 * 
	 * @param user The given user.
	 * @return Return true if user participate as team.
	 */
	private boolean participateAsTeam(User user) {
		for(String teamId: user.getTeamList()) {
			if(participantList.contains(teamId)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Check if there is still place in tournament.
	 * 
	 * @return Return true if still has places.
	 */
	private boolean checkSize(){
		if(participantList.size() < this.maxParticipantNumber) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Check if user can join in.
	 * 
	 * @return Return true if user can join in.
	 */
	public boolean allowUserToJoin(){
		if(tournamentDetail.getParticipantForm() == ParticipantForm.SINGLE) {
			if (checkSize() && canRegister()){
				return true;
			}
	    }
		return false;
	}
	/**
	 * Check if team can join in.
	 * 
	 * @return Return true if team can join in.
	 */
	public boolean allowTeamToJoin() {
		if(tournamentDetail.getParticipantForm() == ParticipantForm.TEAM) {
			if(checkSize() && canRegister()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Check if the deadline doesnt passed.
	 * 
	 * @return Return true if deadline not passed.
	 */
	private boolean canRegister() {
		Date date = new Date();
		if(date.before(getTournamentDetail().getRegistrationDeadline())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Check if the tournament is ready to be held.
	 * 
	 * @return Return true if is ready.
	 */
	public boolean readyToBeHeld() {
		if(!checkSize() && getTournamentDetail().getRegistrationDeadline().before(new Date())) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Check if the tournament is open.
	 * 
	 * @return Return true if tournament is open.
	 */
	public boolean isOpen() {
		if(tournamentDetail.getTournamentTypes() == TournamentTypes.OPEN) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * To check if the given user is the admin of tournament.
	 * 
	 * @param userId Id of user to be checked.
	 * @return true if the given user is admin, else false.
	 */
	public boolean isAdmin(String userId) {
		if(userId.equals(tournamentDetail.getAdminId())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Implement the method in searchable.
	 * 
	 * @return Return the title.
	 */
	public String Title() {
		return "Tournament:" + getTournamentName();
	}
	/**
	 * Implement the method in searchable.
	 * 
	 * @return Return the information as string.
	 */
	public String Information() {
		return  "Tournament:" + getTournamentName() 
		+ "\n" + "ParticipantForm:" + tournamentDetail.getParticipantForm() 
		+ "\n" + "Location:" + tournamentDetail.getLocation()
		+ "\n" + "Game:" + tournamentDetail.getTypeOfGame()
		+ "\n" + "Time: from" + tournamentDetail.getStartTime() + "to" + tournamentDetail.getEndTime()
		+ "\n" + "Register: before" + tournamentDetail.getRegistrationDeadline().toString();
	}
	/**
	 * Calculate the date of a game, based on the index.
	 * 
	 * @param index The index of game in game list.
	 * @return Return the date of the game.
	 */
	public Date calculateDate(int index) {
		Date date = this.tournamentDetail.getStartTime();
		int temp = index / calculateCapacity();
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(Calendar.DATE,temp); 
		date = calendar.getTime(); 
		return date;
	}
	/**
	 * Private method to calculate how many games can be held on one day.
	 * 
	 * @return Return the result.
	 */
	private int calculateCapacity() {
		int daysBetween = daysBetween(this.tournamentDetail.getStartTime(), this.tournamentDetail.getEndTime());
		if(this.maxParticipantNumber % daysBetween == 0) {
			return (maxParticipantNumber / daysBetween);
		} else {
			return (maxParticipantNumber / daysBetween) + 1;
		}
	}
	/**
	 * Private method to calculate how many days there are between two date data.
	 * 
	 * @param smdate The small date.
	 * @param bdate The big date.
	 * @return Return the result.
	 */
	private int daysBetween(Date smdate,Date bdate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);
		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}
	/**
	 * Getter of max participant number.
	 * 
	 * @return Return the number.
	 */
	public int getMaxParticipantNumber() {
		return this.maxParticipantNumber;
	}
	/**
	 * Getter of tournament id.
	 * 
	 * @return Return the id of tournament.
	 */
	public String getTournamentId() {
		return this.tournamentid;
	}
	/**
	 * Getter of tournament name.
	 * 
	 * @return Return the name.
	 */
    public String getTournamentName(){
        return this.tournamentName;
    }
	/**
	 * Getter of tournament detail.
	 * 
	 * @return Return the detail.
	 */
	public TournamentDetail getTournamentDetail() {
		return this.tournamentDetail;
	}
	/**
	 * Getter of game list.
	 * 
	 * @return Return the game list.
	 */
	public List<String> getGameList(){
		return this.gameList;
	}
	/**
	 * Getter of participant list.
	 * 
	 * @return Return the participant list.
	 */
	public List<String> getParticipantList() {
		return this.participantList;
	}
	/**
	 * Setter of tournament detail.
	 * 
	 * @param detail The tournament detail.
	 */
	public void setTournamentDetail(TournamentDetail detail) {
		this.tournamentDetail = detail;
	}
	/**
	 * Add the game to the list.
	 * 
	 * @param gameId The id of game.
	 */
	public void addGame(String gameId){
		gameList.add(gameId);
	}
	/**
	 * Remove the game from the list.
	 * 
	 * @param gameId The id of game.
	 */
	public void removeGame(String gameId){
		gameList.remove(gameId);
	}
	/**
	 * Add the participant to the list.
	 * 
	 * @param participantId The id of participant.
	 */
	public void addParticipant(String participantId){
		participantList.add(participantId);
	}
	/**
	 * Remove the participant from the list.
	 * 
	 * @param participantId The id of participant.
	 */
	public void removeParticipant(String participantId){
		participantList.remove(participantId);
	}
	/**
	 * Setter of type.
	 * 
	 * @param type The type of tournament.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter of current state.
	 * 
	 * @return Return the current state.
	 */
	public TournamentState getCurrentState() {
		return currentState;
	}
	/**
	 * Setter of current state.
	 * 
	 * @param currentState The current state of tournament.
	 */
	public void setCurrentState(TournamentState currentState) {
		this.currentState = currentState;
	}
	/**
	 * Setter of mac participant number.
	 * 
	 * @param number The max participant number.
	 */
	public void setMaxParticipantNumber(int number) {
		this.maxParticipantNumber = number;
	}
	/**
	 * Setter of game list.
	 * 
	 * @param games The game list.
	 */
	public void setGameList(List<String> games) {
		this.gameList = games;
	}
	/**
	 * Setter of tournament id.
	 * 
	 * @param id The id of tournament.
	 */
	public void setTournamentId(String id) {
		this.tournamentid = id;
	}
	/**
	 * Setter of tournament name.
	 * 
	 * @param name The name of tournament.
	 */
	public void setTournamentName(String name) {
		this.tournamentName = name;
	}
	/**
	 * Setter of participant list.
	 * 
	 * @param participants The participant list.
	 */
	public void setParticipantList(List<String> participants) {
		this.participantList = participants;
	}
}
