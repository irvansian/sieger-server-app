package sieger.model;

import java.util.List;
import java.util.UUID;

public class Team extends Participant {
	private UUID id;
	private String name;
	private List<User> members;
	private List<Tournament> tournaments;
	@Override
	String getParticipantName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	boolean joinTournament(Tournament tournament) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	boolean quitTournament(Tournament tournament) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
