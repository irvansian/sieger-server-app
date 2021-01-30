import java.util.ArrayList;

public abstract class Participant {
	//tournament list of participant
	ArrayList<String> tournamentList;
	//get participant name.
	abstract String getParticipantName();
	//join a tournament
	abstract boolean joinTournament();
	//quit tournament
	abstract boolean quitTournament();
	//add tournament
	public void addTournament(String tournamentId) {
		tournamentList.add(tournamentId);
	}
	//remove tournament
	public void removeTournament(String tournamentId) {
		tournamentList.remove(tournamentId);
	}
	//get tournamentids
	public ArrayList<String> getTournamentList(){
		return this.tournamentList;
	}
}
