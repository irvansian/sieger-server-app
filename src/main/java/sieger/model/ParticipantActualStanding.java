package sieger.model;

public class ParticipantActualStanding {
	//name of participant
	private String participant;
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
	public ParticipantActualStanding(String participant) {
		this.participant = participant;
		this.draw = 0;
		this.lose = 0;
		this.plays = 0;
		this.win = 0;
		this.points = 0;
	}
	//get name
	public String getParticipant() {
		return this.participant;
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

}
