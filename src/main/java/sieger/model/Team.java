package sieger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team extends Participant {
	private UUID id;
	private String name;
	private List<User> members;
	private List<Tournament> tournaments;
	
	public Team(String name) {
		this.name = name;
		this.id = UUID.randomUUID();
		this.members = new ArrayList<User>();
		this.tournaments = new ArrayList<Tournament>();
	}
	
	
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<User> getMembers() {
		return members;
	}


	public void setMembers(List<User> members) {
		this.members = members;
	}


	public List<Tournament> getTournaments() {
		return tournaments;
	}


	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}


	@Override
	public void joinTournament(Tournament tournament) {
		tournaments.add(tournament);
	}
	@Override
	public String getParcipantName() {
		return getName();
	}


	@Override
	String getParticipantName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	boolean joinTournament() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	boolean quitTournament() {
		// TODO Auto-generated method stub
		return false;
	}
}
