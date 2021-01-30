package sieger.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends Participant {
	private UUID id;
	private String username;
	private String firstName;
	private String lastName;
	private List<Tournament> tournaments;
	private List<Team> teams;
	
	public User(@JsonProperty("username") String username, 
				@JsonProperty("firstName")String firstName,
				@JsonProperty("lastName")String lastName) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = UUID.randomUUID();
	}
	
 	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Tournament> getTournaments() {
		return tournaments;
	}
	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	@Override
	public boolean joinTournament(Tournament tournament) {
		return tournaments.add(tournament);
		
	}

	@Override
	public String getParticipantName() {
		return getUsername();
	}


	@Override
	boolean quitTournament(Tournament tournament) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
