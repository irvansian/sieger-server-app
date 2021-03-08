package sieger.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The tournament detail class.
 * 
 * @author Chen Zhang
 *
 */
public class TournamentDetail {
    /**
     * The form of participant.
     */
    private ParticipantForm participantForm;
    /**
     * The id of admin.
     */
    private String adminId;
    /**
     * The type of tournament.
     */
    private TournamentTypes tournamentTypes; 
    /**
     * The type of game.
     */
    private String typeOfGame;
    /**
     * The location of tournament.
     */
    private String location;
    /**
     * The deadline date of registration.
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+2")
    private Date registrationDeadline;
    /**
     * The time when tournament starts.
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+2")
    private Date startTime;
    /**
     * The time when tournament ends.
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+2")
    private Date endTime;
    /**
     * No-Argument construtor.
     */
    public TournamentDetail() {
    	
    }
    /**
     * Constructor of tournamentdetail.
     * 
     * @param organisatorId The id of admin.
     * @param tournamentTypes The type of tournament.
     * @param typeOfGame The type of game.
     * @param location The location of tournament.
     * @param registrationDeadline The deadline for registration.
     * @param startTime The time when tournament starts.
     * @param endTime The time when tournament ends.
     * @param form The form of participant.
     */
    @JsonCreator
    public TournamentDetail(@JsonProperty("organisatorId")String organisatorId, @JsonProperty("tournamentTypes")TournamentTypes tournamentTypes,
    		@JsonProperty("typeOfGame")String typeOfGame, @JsonProperty("location")String location, @JsonProperty("registrationDeadline")Date registrationDeadline, 
    		@JsonProperty("startTime")Date startTime, @JsonProperty("endTime")Date endTime, @JsonProperty("form")ParticipantForm form){
        this.adminId = organisatorId;   
        this.tournamentTypes = tournamentTypes;
        this.typeOfGame = typeOfGame;
        this.location = location;
        this.registrationDeadline = registrationDeadline;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participantForm = form;
    }
    /**
     * Getter of participant form.
     * 
     * @return Return the participant form.
     */
    public ParticipantForm getParticipantForm(){
        return this.participantForm;
    }
    /**
     * Getter of admin id.
     * 
     * @return Return the admin id.
     */
    public String getAdminId(){
        return this.adminId;
    }
    /**
     * Getter of tournament type.
     * 
     * @return Return the type of tournament.
     */
    public TournamentTypes getTournamentTypes(){
        return this.tournamentTypes;
    }
    /**
     * Getter of type of game.
     * 
     * @return Return the type of game.
     */
    public String getTypeOfGame(){
        return this.typeOfGame;
    }
    /**
     * Getter of location.
     * 
     * @return Return the location.
     */
    public String getLocation(){
        return this.location;
    }
    /**
     * Getter of registration deadline.
     * 
     * @return Return the deadline.
     */
    public Date getRegistrationDeadline(){
        return this.registrationDeadline;
    }
    /**
     * Getter of the start time.
     * 
     * @return Return the start time.
     */
    public Date getStartTime(){
        return this.startTime;
    }
    /**
     * Getter of the end time.
     * 
     * @return Return the end time.
     */
    public Date getEndTime(){
        return this.endTime;
    }
    /**
     * Setter of the participant form.
     * 
     * @param form The participant form.
     */
    public void setParticipantForm(ParticipantForm form) {
    	this.participantForm = form;
    }
    /**
     * Setter of type of game.
     *
     * @param type The type of game.
     */
    public void setTypeOfGame(String type){
        this.typeOfGame = type;
    }
    /**
     * Setter of location.
     * 
     * @param location The location of tournament.
     */
    public void setLocation(String location){
        this.location = location;
    }
    /**
     * Setter registration deadline
     * 
     * @param date The deadline of registration.
     */
    public void setRegistrationDeadline(Date date){
        this.registrationDeadline = date;
    }
    /**
     * Setter of start time.
     * 
     * @param date The start time.
     */
    public void setStartTime(Date date){
        this.startTime = date;
    }
    /**
     * Setter of end time.
     * 
     * @param date The end time.
     */
    public void setEndTime(Date date){
        this.endTime = date;
    }
}
