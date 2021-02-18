package sieger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The team class which extends participant class.
 * 
 * @author Chen Zhang
 *
 */
public class Team extends Participant{
	/**
	 * The id of a team.
	 */
	private String teamId;
	/**
	 * The id of the team admin.
	 */
	private String adminId;
	/**
	 * Name of the team.
	 */
	private String teamName;
	/**
	 * Password of the team. It is used to join a team.
	 */
	private String teamPassword;
	/**
	 * List that contains id of members.
	 */
	private List<String> memberList;
	/**
	 * List that contains the invitation. Only admin can handle invitation.
	 */
	private List<String> invitationList;
	/**
	 * No-argument constructor.
	 */
	public Team() {
		
	}
	/**
	 * Constructor of team class. The id will bew created automatically by UUID.
	 * 
	 * @param adminId The id of team admin.
	 * @param name The name of team.
	 * @param password The password of the team.
	 */
	@JsonCreator
	public Team(@JsonProperty("adminId")String adminId, @JsonProperty("name")String name, @JsonProperty("password")String password) {
		super.tournamentList = new ArrayList<>();
		this.adminId = adminId;
		this.teamName = name;
		this.teamPassword = password;
		this.teamId = randomId();
		this.memberList = new ArrayList<>();
		this.invitationList = new ArrayList<>();
		memberList.add(adminId);
	}
	/**
	 * Private method to get team id from UUID.
	 * 
	 * @return Return the id as String.
	 */
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	/**
	 * Override the method in participant class.
	 * 
	 * @return Return the name of team.
	 */
	@Override
	public String findParticipantName() {
		return getTeamName();
	}
	/**
	 * Override the method in participant class.
	 * 
	 * @return Return true if success.
	 */
	@Override
	public boolean joinTournament(Tournament tournament) {
		addTournament(tournament.getTournamentId());
		tournament.addParticipant(teamId);
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
		tournament.removeParticipant(teamId);
		return true;
	}
	/**
	 * To check if the given password is correct.
	 * 
	 * @param password The given password.
	 * @return Return true if matches.
	 */
	public boolean checkPassword(String password) {
		if(password.equals(teamPassword)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * To check if the user is the admin.
	 * 
	 * @param admin The given user.
	 * @return Return true if user id admin.
	 */
	public boolean checkAdmin(User admin) {
		if(admin.getUserId().equals(adminId)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Kick the user from the team.
	 * 
	 * @param user The member will be removed.
	 */
	public void kickMember(User user) {
		user.quitTeam(this);
	}
	/**
	 * Add member to the member list.
	 * 
	 * @param userId The id of new member.
	 */
	public void addMember(String userId) {
		memberList.add(userId);
	}
	/**
	 * Remove the member from the list.
	 * 
	 * @param userId The id of member.
	 */
	public void removeMember(String userId) {
		memberList.remove(userId);
	}
	/**
	 * Add invitation to the list.
	 * 
	 * @param invitationId The id of invitation.
	 */
	public void addInvitation(String invitationId) {
		invitationList.add(invitationId);
	}
	/**
	 * Remove invitation from the list.
	 * 
	 * @param invitationId The id of invitation to be moved.
	 */
	public void removeInvitation(String invitationId) {
		invitationList.remove(invitationId);
	}
	/**
	 * Getter of team id.
	 * 
	 * @return Return the team id.
	 */
	public String getTeamId() {
		return this.teamId;
	}
	/**
	 * Getter of admin id.
	 * 
	 * @return Return the admin id.
	 */
	public String getAdminId() {
		return this.adminId;
	}
	/**
	 * Getter of team name.
	 * 
	 * @return Return the name of team.
	 */
	public String getTeamName() {
		return this.teamName;
	}
	/**
	 * Getter password of team.
	 * 
	 * @return Return the password of team.
	 */
	public String getTeamPassword() {
		return this.teamPassword;
	}
	/**
	 * Getter of member list.
	 * 
	 * @return Return the memberlist.
	 */
	public List<String> getMemberList(){
		return this.memberList;
	}
	/**
	 * Getter of invitation list.
	 * 
	 * @return Return the invitation list.
	 */
	public List<String> getInvitationList(){
		return this.invitationList;
	}

}
