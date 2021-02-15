package sieger.model;

import java.util.ArrayList;
import java.util.List;

public class LeagueTable {
	//list of standing
	private List<ParticipantActualStanding> tables;
	//constructor
	public LeagueTable(List<String >participants) {
		this.tables = new ArrayList<>();
		for(String participant: participants) {
			ParticipantActualStanding newStanding = new ParticipantActualStanding(participant);
			addParticipantActualStanding(newStanding);
		}
	}
	//sort
	public void sort() {
		ParticipantActualStanding temp;
		for (int i = tables.size()- 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
            	if(tables.get(j+1).getPoints() > tables.get(j).getPoints()){           		
            		temp = tables.get(j);
            		tables.set(j, tables.get(j+1));
            		tables.set(j+1, temp);
            	}
            }
        }
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
			if(participantStanding.getParticipantId().equals(participant)) {
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
	//getter
	public List<ParticipantActualStanding> getTables(){
		return this.tables;
	}

}
