package sieger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team extends Participant{
	//team id
	private String teamId;
	//admin id
	private String adminId;
	//team name
	private String teamName;
	//password of team
	private String teamPassword;
	//member list
	private List<String> memberList;
	//constructor
	public Team(String adminId, String name, String password) {
		this.adminId = adminId;
		this.teamName = name;
		this.teamPassword = password;
		this.teamId = randomId();
		this.memberList = new ArrayList<>();
	}
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//implement getparticipantname()
	@Override
	public String getParticipantName() {
		return this.teamName;
	}
	//implement joinTournament()
	@Override
	public boolean joinTournament(Tournament tournament) {
		if(tournament.allowTeamToJoin()) {
			addTournament(tournament.getTournamentId());
			tournament.addParticipant(teamId);
			return true;
		} else {
			return false;
		}
	}
	//implement quitTournament()
	@Override
	public boolean quitTournament(Tournament tournament) {
		removeTournament(tournament.getTournamentId());
		tournament.removeParticipant(teamId);
		return true;
	}
	//check if password is true
	public boolean checkPassword(String password) {
		if(password.equals(teamPassword)) {
			return true;
		} else {
			return false;
		}
	}
	//check if user is Admin
	public boolean checkAdmin(User admin) {
		if(admin.getUserId().equals(adminId)) {
			return true;
		} else {
			return false;
		}
	}
	//kick member
	public void kickMember(User user) {
		
	}
	//add member
	public void addMember(String userId) {
		memberList.add(userId);
	}
	//remove member
	public void removeMember(String userId) {
		memberList.remove(userId);
	}
	//get teamid
	public String getTeamId() {
		return this.teamId;
	}
	//get adminId
	public String getAdminId() {
		return this.adminId;
	}
	//get team name
	public String getTeamName() {
		return this.teamName;
	}
	//get team password
	public String getTeamPassword() {
		return this.teamPassword;
	}
	//get memberlist
	public List<String> getMemberList(){
		return this.memberList;
	}
}
