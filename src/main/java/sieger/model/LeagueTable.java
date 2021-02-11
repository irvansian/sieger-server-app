package sieger.model;

import java.util.List;

public class LeagueTable {
	//list of standing
	private List<ParticipantActualStanding> tables;
	//constructor
	public LeagueTable(List<String >participants) {
		for(String participant: participants) {
			ParticipantActualStanding newStanding = new ParticipantActualStanding(participant);
			addParticipantActualStanding(newStanding);
		}
	}
	//sort
	public void sort() {
		
	}
	//add participant standing
	public void addParticipantActualStanding(ParticipantActualStanding standing) {
		tables.add(standing);
	}
	//remove participant standing
	public void removeParticipantActualStanding(ParticipantActualStanding standing) {
		tables.remove(standing);
	}
	//get participant standing
	public ParticipantActualStanding getParticipantStandingById(String participant) {
		for(ParticipantActualStanding participantStanding: tables) {
			if(participantStanding.getParticipant().equals(participant)) {
				return participantStanding;
			}
		}
		return null;
	}
	//participant win
	public void participantWin(String participant) {
		ParticipantActualStanding standing = getParticipantStandingById(participant);
		if (standing != null) {
			standing.winGame();
		}
	}
	//participant lose
	public void participantLose(String participant) {
		ParticipantActualStanding standing = getParticipantStandingById(participant);
		if (standing != null) {
			standing.loseGame();
		}
	}
	//participant draw
	public void participantDraw(String participant) {
		ParticipantActualStanding standing = getParticipantStandingById(participant);
		if (standing != null) {
			standing.drawGame();
		}
	}

}
