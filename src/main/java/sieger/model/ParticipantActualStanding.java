package sieger.model;
/**
 * The participant actual standing class, which contains the score of participant and other infos.
 * 
 * @author Chen Zhang
 *
 */
public class ParticipantActualStanding {
	/**
	 * The id of participant.
	 */
	private String participantId;
	/**
	 * The number of games that the participant plays.
	 */
	private int plays;
	/**
	 * The number of games that the participant wins.
	 */
	private int win;
	/**
	 * The number of games that the participant loses.
	 */
	private int lose;
	/**
	 * The number of games that the participant draws.
	 */
	private int draw;
	/**
	 * The point that participant has.
	 */
	private int points;
	/**
	 * No-argument constructor.
	 */
	public ParticipantActualStanding() {
		
	}
	/**
	 * Constructor of standing.
	 * 
	 * @param participantId The id of participant.
	 */
	public ParticipantActualStanding(String participantId) {
		this.participantId = participantId;
		this.draw = 0;
		this.lose = 0;
		this.plays = 0;
		this.win = 0;
		this.points = 0;
	}
	/**
	 * Change the standing when participant wins.
	 */
	public void winGame() {
		setPlays(getPlays() + 1);
		setWin(getWin() + 1);
		setPoints(getPoints() + 2);
	}
	/**
	 * Change the standing when participant loses.
	 */
	public void loseGame() {
		setPlays(getPlays() + 1);
		setLose(getLose() + 1);
	}
	/**
	 * Change the standing when participant draws.
	 */
	public void drawGame() {
		setPlays(getPlays() + 1);
		setDraw(getDraw() + 1);
		setPoints(getPoints() + 1);
	}
	/**
	 * Getter of participant id.
	 * 
	 * @return Return the id of participant.
	 */
	public String getParticipantId() {
		return this.participantId;
	}
	/**
	 * Getter of play.
	 * 
	 * @return Return the number of play.
	 */
	public int getPlays() {
		return this.plays;
	}
	//get win
	public int getWin() {
		return this.win;
	}
	//get lose
	public int getLose() {
		return this.lose;
	}
	//get draw
	public int getDraw() {
		return this.draw;
	}
	//get point
	public int getPoints() {
		return this.points;
	}
	//set plays
	public void setPlays(int newPlays) {
		this.plays = newPlays;
	}
	//set win
	public void setWin(int newWin) {
		this.win = newWin;
	}
	//set lose
	public void setLose(int newLose) {
		this.lose = newLose;
	}
	//set point
	public void setPoints(int newPoints) {
		this.points = newPoints;
	}
	//set draw
	public void setDraw(int newDraw) {
		this.draw = newDraw;
	}

}
