package sieger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreResult implements Result{
	//score of first participant
	private int firstParticipantScore;
	//score of second participant
	private int secondParticipantScore;
	private String type;
	public ScoreResult() {
		
	}
	//constructor
	@JsonCreator
	public ScoreResult(@JsonProperty("firstParticipantScore")int firstParticipantScore, @JsonProperty("secondParticipantScore")int secondParticipantScore) {
		this.firstParticipantScore = firstParticipantScore;
		this.secondParticipantScore = secondParticipantScore;
		this.setType("Score");
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

	public int getFirstParticipantResult() {
		return this.firstParticipantScore;
	}
	public int getSecondParticipantResult() {
		return this.secondParticipantScore;
	}
	public void setFirstParticipantResult(int score) {
		this.firstParticipantScore = score;
	}
	public void setSecondParticipantResult(int score) {
		this.secondParticipantScore = score;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
