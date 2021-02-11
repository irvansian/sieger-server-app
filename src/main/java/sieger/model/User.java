package sieger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User extends Participant{
	//user id
	private String userId;
	//user name
	private String userName;
	//surname
	private String surname;
	//forname
	private String forename;
	//notificationlist
	private List<Notification> notificationList;
	//list of team
	private List<String> teamList;
	//list of invitation
	private List<String> invitationList;

	//constructor
	public User(String userName, String surname, String forename) {
		super.tournamentList = new ArrayList<>();
		this.userName = userName;
		this.forename = forename;
		this.surname = surname;
		this.userId = randomId();
		this.notificationList = new ArrayList<>();
		this.teamList = new ArrayList<>();
		this.invitationList = new ArrayList<>();
	}
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//implement getparticipantname()
	@Override
	public String getParticipantName() {
		return this.userName;
	}
	//implement joinTournament
	@Override
	public boolean joinTournament(Tournament tournament) {
		if(tournament.allowUserToJoin()) {
			addTournament(tournament.getTournamentId());
			tournament.addParticipant(userId);
			return true;
		} else {
			return false;
		}
	}
	//implement quittournament
	@Override
	public boolean quitTournament(Tournament tournament) {
		removeTournament(tournament.getTournamentId());
		tournament.removeParticipant(userId);
		return true;
	}
	
	//show next Tournament(not finished)
	public List<String> showNextTournaments(List<Tournament> tournaments){
		Date current = new Date();
		List<Tournament> tempTournaments = new ArrayList<>();
		for(Tournament tournament: tournaments) {
			if(tournament.getTournamentDetail().getEndTime().after(current)) {
				tempTournaments.add(tournament);
			}
		}
		Tournament tempTournament;
		//bubble sort
		for (int i = tempTournaments.size()- 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
            	if(tempTournaments.get(j+1).getTournamentDetail().getEndTime().before(tempTournaments.get(j).getTournamentDetail().getEndTime())){           		
            		tempTournament = tempTournaments.get(j);
            		tempTournaments.set(j, tempTournaments.get(j+1));
            		tempTournaments.set(j+1, tempTournament);
            	}
            }
        }
		List<String> result = new ArrayList<>();
		result.add(tempTournaments.get(0).getTournamentId());
		result.add(tempTournaments.get(1).getTournamentId());
		result.add(tempTournaments.get(2).getTournamentId());
		return result;
	}
	//join team
	public boolean joinTeam(Team team, String password) {
		if(team.checkPassword(password)) {
			team.addMember(userId);
			addTeam(team.getTeamId());
			return true;
		} else {
			return false;
		}
	}
	//quit team
	public boolean quitTeam(Team team) {
		team.removeMember(userId);
		removeTeam(team.getTeamId());
		return true;
	}
	//cancel Tournament
	public boolean cancelTournament(Tournament tournament, List<Participant> participants) {
		for(Participant participant: participants) {
			participant.removeTournament(tournament.getTournamentId());
		}
		return true;
	}
	//add notification
	public void addNotification(Notification notification) {
		notificationList.add(notification);
	}
	//remove notification
	public void removeNotification(Notification notification) {
		notificationList.remove(notification);
	}
	//add invitation
	public void addInvitation(String invitationId) {
		invitationList.add(invitationId);
	}
	//remove invitation
	public void removeInvitation(String invitationId) {
		invitationList.remove(invitationId);
	}
	//add team
	public void addTeam(String teamId) {
		teamList.add(teamId);
	}
	//remove team
	public void removeTeam(String teamId) {
		teamList.remove(teamId);
	}
	//get userid
	public String getUserId() {
		return this.userId;
	}
	//get username
	public String getUserName() {
		return this.userName;
	}
	//get surname
	public String getSurname() {
		return this.surname;
	}
	//get forename
	public String getForename() {
		return this.forename;
	}
	//get notificationlist
	public List<Notification> getNotificationList(){
		return this.notificationList;
	}
	//get teamlist
	public List<String> getTeamList(){
		return this.teamList;
	}
	//get invitation list
	public List<String> getInvitationList(){
		return this.invitationList;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setUserName(String username) {
		this.userName = username;
	}
	
	public void setForename(String forename) {
		this.forename = forename;
	}
	
	
	
	
}
