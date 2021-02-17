package sieger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreResult implements Result{
	//score of first participant
	private int firstParticipantResult;
	//score of second participant
	private int secondParticipantResult;
	private String type;
	public ScoreResult() {
		
	}
	//constructor
	@JsonCreator
	public ScoreResult(@JsonProperty("firstParticipantResult")int firstParticipantResult, @JsonProperty("secondParticipantResult")int secondParticipantResult) {
		this.firstParticipantResult = firstParticipantResult;
		this.secondParticipantResult = secondParticipantResult;
		this.setType("Score");
	}
	@Override
	public boolean firstWins() {
		if(firstParticipantResult > secondParticipantResult) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean secondWins() {
		if(firstParticipantResult < secondParticipantResult) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public boolean draws() {
		if(firstParticipantResult == secondParticipantResult) {
			return true;
		} else {
			return false;
		}
	}

	public int getFirstParticipantResult() {
		return this.firstParticipantResult;
	}
	public int getSecondParticipantResult() {
		return this.secondParticipantResult;
	}
	public void setFirstParticipantResult(int score) {
		this.firstParticipantResult = score;
	}
	public void setSecondParticipantResult(int score) {
		this.secondParticipantResult = score;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
