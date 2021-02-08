package sieger.model;

public class WinLoseResult implements Result {
	//result of firdt participant
	private GameOutcome firstParticipantResult;
	//result of second participant
	private GameOutcome secondParticipantResult;
	//constructor
	public WinLoseResult(GameOutcome firstParticipantResult, GameOutcome secondParticipantResult) {
		this.firstParticipantResult = firstParticipantResult;
		this.secondParticipantResult = secondParticipantResult;
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
	
	

}
