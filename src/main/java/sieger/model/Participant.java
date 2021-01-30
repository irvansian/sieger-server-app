package sieger.model;

import java.util.List;

public abstract class Participant {
	//tournament list of participant
	private List<String> tournamentList;
	//get participant name.
	abstract String getParticipantName();
	//join a tournament
	abstract boolean joinTournament(Tournament tournament);
	//quit tournament
	abstract boolean quitTournament(Tournament tournament);
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
