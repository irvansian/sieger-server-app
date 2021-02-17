package sieger.model;

public class ParticipantActualStanding {
	//name of participant
	private String participantId;
	//number of game
	private int plays;
	//number of win
	private int win;
	//number of lose
	private int lose;
	//number of draw
	private int draw;
	//number of points
	private int points;
	//constructor
	public ParticipantActualStanding() {
		
	}
	public ParticipantActualStanding(String participantId) {
		this.participantId = participantId;
		this.draw = 0;
		this.lose = 0;
		this.plays = 0;
		this.win = 0;
		this.points = 0;
	}
	//participant win
	public void winGame() {
		setPlays(getPlays() + 1);
		setWin(getWin() + 1);
		setPoints(getPoints() + 2);
	}
	//participant lose
	public void loseGame() {
		setPlays(getPlays() + 1);
		setLose(getLose() + 1);
	}
	//participant draw
	public void drawGame() {
		setPlays(getPlays() + 1);
		setDraw(getDraw() + 1);
		setPoints(getPoints() + 1);
	}
	//get name
	public String getParticipantId() {
		return this.participantId;
	}
	//get plays
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
