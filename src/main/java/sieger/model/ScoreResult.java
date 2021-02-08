package sieger.model;

public class ScoreResult implements Result{
	//score of first participant
	private int firstParticipantScore;
	//score of second participant
	private int secondParticipantScore;
	//constructor
	public ScoreResult(int firstParticipantScore, int secondParticipantScore) {
		this.firstParticipantScore = firstParticipantScore;
		this.secondParticipantScore = secondParticipantScore;
	}
	@Override
	public boolean firstWins() {
		if(firstParticipantScore > secondParticipantScore) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean secondWins() {
		if(firstParticipantScore < secondParticipantScore) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean draws() {
		if(firstParticipantScore == secondParticipantScore) {
			return true;
		} else {
			return false;
		}
	}

}
