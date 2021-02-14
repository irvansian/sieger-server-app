package sieger.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TournamentDetail {
    //form of Participant
    private ParticipantForm participantForm;
    //id of admin
    private String adminId;

    //type of tournament
    private TournamentTypes tournamentTypes; 
    //type of game
    private String typeOfGame;
    //location
    private String location;
    //registration deadline
    private Date registrationDeadline;
    //start time
    private Date startTime;
    //end time
    private Date endTime;
    //constructor
    public TournamentDetail() {
    	
    }
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
    //get participant form
    public ParticipantForm getParticipantForm(){
        return this.participantForm;
    }
    //get id of admin
    public String getAdminId(){
        return this.adminId;
    }
   
    //get tournament type
    public TournamentTypes getTournamentTypes(){
        return this.tournamentTypes;
    }
    //get type of game
    public String getTypeOfGame(){
        return this.typeOfGame;
    }
    //get location
    public String getLocation(){
        return this.location;
    }
    //get registration deadline
    public Date getRegistrationDeadline(){
        return this.registrationDeadline;
    }
    //get start time
    public Date getStartTime(){
        return this.startTime;
    }
    //get end time
    public Date getEndTime(){
        return this.endTime;
    }
}
