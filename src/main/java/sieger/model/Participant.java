package sieger.model;

import java.util.List;

public abstract class Participant {
	//tournament list of participant
	public List<String> tournamentList;
	//get participant name.
	abstract public String findParticipantName();
	//join a tournament
	abstract public boolean joinTournament(Tournament tournament);
	//quit tournament
	abstract public boolean quitTournament(Tournament tournament);
	//add tournament
	public void addTournament(String tournamentId) {
		tournamentList.add(tournamentId);
	}
	//remove tournament
	public void removeTournament(String tournamentId) {
		tournamentList.remove(tournamentId);
	}
	//get tournamentids
	public List<String> getTournamentList(){
		return this.tournamentList;
	}
}
