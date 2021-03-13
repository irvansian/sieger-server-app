package sieger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The user class which extends the participant class.
 * 
 * @author Chen Zhang
 *
 */
public class User extends Participant{
	/**
	 * The id of user, which is created in client.
	 */
	private String userId;
	/**
	 * The name of user.
	 */
	private String username;
	/**
	 * The surname of user.
	 */
	private String surname;
	/**
	 * The forename of user.
	 */
	private String forename;
	/**
	 * The team list.
	 */
	private List<String> teamList;
	/**
	 * The list of inviatation.
	 */
	private List<String> invitationList;
	/**
	 * No-Argument constructor.
	 */
	public User() {
	}
	/**
	 * Contruction of a user.
	 * 
	 * @param username The name of user.
	 * @param surname The surname of user.
	 * @param forename The forename of user.
	 * @param userId The user id passed from client side.
	 */
	@JsonCreator
	public User(@JsonProperty("username")String username, @JsonProperty("surname")String surname, @JsonProperty("forename")String forename, @JsonProperty("userId")String userId) {
		super.tournamentList = new ArrayList<>();
		this.username = username;
		this.forename = forename;
		this.surname = surname;
		this.userId = userId;
		this.teamList = new ArrayList<>();
		this.invitationList = new ArrayList<>();
	}

	/**
	 * Override the method in participant class.
	 * 
	 * @return Return the name of user.
	 */
	@Override
	public String findParticipantName() {
		return getUsername();
	}
	/**
	 * Override the method in participant class.
	 * 
	 * @return Return true if success.
	 */
	@Override
	public boolean joinTournament(Tournament tournament) {
		addTournament(tournament.getTournamentId());
		tournament.addParticipant(userId);
		return true;
	
	}
	/**
	 * Override the method in participant class.
	 * 
	 * @return Return true if success.
	 */
	@Override
	public boolean quitTournament(Tournament tournament) {
		removeTournament(tournament.getTournamentId());
		tournament.removeParticipant(userId);
		return true;
	}
	
	/**
	 * Get the next three tournaments
	 * 
	 * @param tournaments All the tournaments that user took part in.
	 * @return Return a tournament list.
	 */
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
		if (tempTournaments.size() == 1) {
			result.add(tempTournaments.get(0).getTournamentId());
		}
		if (tempTournaments.size() == 2) {
			result.add(tempTournaments.get(0).getTournamentId());
			result.add(tempTournaments.get(1).getTournamentId());
		}
		if (tempTournaments.size() >= 3) {
			result.add(tempTournaments.get(0).getTournamentId());
			result.add(tempTournaments.get(1).getTournamentId());
			result.add(tempTournaments.get(2).getTournamentId());
		}
		return result;
	}
	/**
	 * User join in team with the password
	 * 
	 * @param team The team he wants to join in.
	 * @param password The password.
	 * @return Return true if success.
	 */
	public boolean joinTeam(Team team, String password) {
		if(team.checkPassword(password)) {
			team.addMember(userId);
			addTeam(team.getTeamId());
			return true;
		} else {
			return false;
		}
	}
	/**
	 * User quit the team.
	 * 
	 * @param team The team he wants to quit from.
	 * @return Return true if success.
	 */
	public boolean quitTeam(Team team) {
		team.removeMember(userId);
		removeTeam(team.getTeamId());
		return true;
	}
	/**
	 * Add the invitation to the list.
	 * 
	 * @param invitationId The id of invitation.
	 */
	public void addInvitation(String invitationId) {
		invitationList.add(invitationId);
	}
	/**
	 * Remove the invitation from the list.
	 * 
	 * @param invitationId The id of invitation.
	 */
	public void removeInvitation(String invitationId) {
		invitationList.remove(invitationId);
	}
	/**
	 * Add a team to the list.
	 * 
	 * @param teamId The id of team.
	 */
	public void addTeam(String teamId) {
		teamList.add(teamId);
	}
	/**
	 * Remove the team from list.
	 * 
	 * @param teamId
	 */
	public void removeTeam(String teamId) {
		teamList.remove(teamId);
	}
	/**
	 * Getter of userid.
	 * 
	 * @return Return the user id.
	 */
	public String getUserId() {
		return this.userId;
	}
	/**
	 * Getter of username.
	 * 
	 * @return Return the user name.
	 */
	public String getUsername() {
		return this.username;
	}
	/**
	 * Getter of surname.
	 * 
	 * @return Return the surname.
	 */
	public String getSurname() {
		return this.surname;
	}
	/**
	 * Getter of forename.
	 * 
	 * @return Return the forename.
	 */
	public String getForename() {
		return this.forename;
	}
	/**
	 * Getter of the team list.
	 * 
	 * @return Return the teamlist.
	 */
	public List<String> getTeamList(){
		return this.teamList;
	}
	/**
	 * Getter of the invitation list.
	 * 
	 * @return Return the invitation list.
	 */
	public List<String> getInvitationList(){
		return this.invitationList;
	}
	/**
	 * Setter of surname.
	 * 
	 * @param surname The surname of user.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * Setter of username.
	 * 
	 * @param username The username of user.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Setter of forename.
	 * 
	 * @param forename The forename of user.
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}
	/**
	 * Setter of the userid
	 * 
	 * @param userId The user id.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
