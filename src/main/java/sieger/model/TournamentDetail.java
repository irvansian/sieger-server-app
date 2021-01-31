package sieger.model;

import java.sql.Date;

public class TournamentDetail {
    //form of Participant
    private ParticipantForm participantForm;
    //id of admin
    private String adminId;
    //name of tournament
    private String tournamentName;
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
    public TournamentDetail(String organisatorId, String name, TournamentTypes tournamentTypes, String typeOfGame, String location, Date registrationDeadline, Date startTime, Date endTime, ParticipantForm form){
        this.adminId = organisatorId;
        this.tournamentName = name;
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
    //get name of tournament
    public String getTournamentName(){
        return this.tournamentName;
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
