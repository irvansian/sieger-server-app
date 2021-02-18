package sieger.model;

import java.util.ArrayList;
import java.util.List;
/**
 * The league table class which records the score and other info of participants.
 * It will be used in league tournament and Knock out with group tournament.
 * 
 * @author Chen Zhang
 *
 */
public class LeagueTable {
	/**
	 * The list of all participant actual standing.
	 */
	private List<ParticipantActualStanding> tables;
	/**
	 * No-argument constructor.
	 */
	public LeagueTable() {
	}
	/**
	 * Constructor of the league table.
	 * 
	 * @param participants The list of participant id.
	 */
	public LeagueTable(List<String >participants) {
		this.tables = new ArrayList<>();
		for(String participant: participants) {
			ParticipantActualStanding newStanding = new ParticipantActualStanding(participant);
			addParticipantActualStanding(newStanding);
		}
	}
	/**
	 * Sort the table with the results.
	 */
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
	/**
	 * Add a new participant standing.
	 * 
	 * @param standing New participant standing.
	 */
	public void addParticipantActualStanding(ParticipantActualStanding standing) {
		tables.add(standing);
	}
	/**
	 * Remove a participant standing.
	 * 
	 * @param standing The participant standing to be removed.
	 */
	public void removeParticipantActualStanding(ParticipantActualStanding standing) {
		tables.remove(standing);
	}
	/**
	 * Get the standing by participant id.
	 * 
	 * @param participant The id of participant.
	 * @return Return the participant standing.
	 */
	public ParticipantActualStanding getParticipantStandingById(String participant) {
		for(ParticipantActualStanding participantStanding: tables) {
			if(participantStanding.getParticipantId().equals(participant)) {
				return participantStanding;
			}
		}
		return null;
	}
	/**
	 * Change the info in standing when participant wins.
	 * 
	 * @param participant The id of participant.
	 */
	public void participantWin(String participant) {
		ParticipantActualStanding standing = getParticipantStandingById(participant);
		if (standing != null) {
			standing.winGame();
		}
	}
	/**
	 * Change the info in standing when participant loses.
	 * 
	 * @param participant The id of participant.
	 */
	public void participantLose(String participant) {
		ParticipantActualStanding standing = getParticipantStandingById(participant);
		if (standing != null) {
			standing.loseGame();
		}
	}
	/**
	 * Change the info in standing when participant draws.
	 * 
	 * @param participant The id of participant.
	 */
	public void participantDraw(String participant) {
		ParticipantActualStanding standing = getParticipantStandingById(participant);
		if (standing != null) {
			standing.drawGame();
		}
	}
	/**
	 * Getter of the list of standing.
	 * 
	 * @return Return the table.
	 */
	public List<ParticipantActualStanding> getTables(){
		return this.tables;
	}
	/**
	 * Setter of the table.
	 * 
	 * @param tables The table to be setted.
	 */
	public void setTables(List<ParticipantActualStanding> tables) {
		this.tables = tables;
	}
}
