package sieger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WinLoseResult implements Result {
	//result of firdt participant
	private GameOutcome firstParticipantResult;
	//result of second participant
	private GameOutcome secondParticipantResult;
	private String type;
	//constructor
	public WinLoseResult() {
		
	}
	@JsonCreator
	public WinLoseResult(@JsonProperty("firstParticipantResult")GameOutcome firstParticipantResult, @JsonProperty("secondParticipantResult")GameOutcome secondParticipantResult) {
		this.firstParticipantResult = firstParticipantResult;
		this.secondParticipantResult = secondParticipantResult;
		this.setType("Winlose");
	}
	@Override
	public boolean firstWins() {
		if(this.firstParticipantResult == GameOutcome.WIN && this.secondParticipantResult == GameOutcome.LOSE){
			return true;
		} else {
		    return false;
		}
	}
	@Override
	public boolean secondWins() {
		if(this.firstParticipantResult == GameOutcome.LOSE && this.secondParticipantResult == GameOutcome.WIN){
			return true;
		} else {
		    return false;
		}
	}
	@Override
	public boolean draws() {
		if(this.firstParticipantResult == GameOutcome.DRAW && this.secondParticipantResult == GameOutcome.DRAW){
			return true;
		} else {
		    return false;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GameOutcome getFirstParticipantResult() {
		return this.firstParticipantResult;
	}
	public GameOutcome getSecondParticipantResult() {
		return this.secondParticipantResult;
	}
	public void setFirstParticipantResult(GameOutcome gameOutcome) {
		this.firstParticipantResult = gameOutcome;
	}
	public void setSecondParticipantResult(GameOutcome gameOutcome) {
		this.secondParticipantResult = gameOutcome;
	}

}
