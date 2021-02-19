package sieger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The winlose result class which implement the result interface.
 * 
 * @author Chen Zhang
 *
 */
public class WinLoseResult implements Result {
	/**
	 * The result of first participant.
	 */
	private GameOutcome firstParticipantResult;
	/**
	 * The result of second participant.
	 */
	private GameOutcome secondParticipantResult;
	/**
	 * The type of result.Here is always "Winlose"
	 */
	private String type;
	/**
	 * No-Argument constructor.
	 */
	public WinLoseResult() {
		
	}
	/**
	 * Constructor.
	 * 
	 * @param firstParticipantResult The result of first participant.
	 * @param secondParticipantResult The result of second participant.
	 */
	@JsonCreator
	public WinLoseResult(@JsonProperty("firstParticipantResult")GameOutcome firstParticipantResult, @JsonProperty("secondParticipantResult")GameOutcome secondParticipantResult) {
		this.firstParticipantResult = firstParticipantResult;
		this.secondParticipantResult = secondParticipantResult;
		this.setType("Winlose");
	}
	/**
	 * Override the method in result interface.
	 * 
	 * @return Return true if first participant wins.
	 */
	@Override
	public boolean firstWins() {
		if(this.firstParticipantResult == GameOutcome.WIN && this.secondParticipantResult == GameOutcome.LOSE){
			return true;
		} else {
		    return false;
		}
	}
	/**
	 * Override the method in result interface.
	 * 
	 * @return Return true if second participant wins.
	 */
	@Override
	public boolean secondWins() {
		if(this.firstParticipantResult == GameOutcome.LOSE && this.secondParticipantResult == GameOutcome.WIN){
			return true;
		} else {
		    return false;
		}
	}
	/**
	 * Override the method in result interface.
	 * 
	 * @return Return true if the game draws.
	 */
	@Override
	public boolean draws() {
		if(this.firstParticipantResult == GameOutcome.DRAW && this.secondParticipantResult == GameOutcome.DRAW){
			return true;
		} else {
		    return false;
		}
	}
	/**
	 * Getter of type.
	 * 
	 * @return Return the type of result.
	 */
	public String getType() {
		return type;
	}
	/**
	 * Setter of type.
	 * 
	 * @param type The type of result.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter of result of first participant.
	 * 
	 * @return The result of first participant.
	 */
	public GameOutcome getFirstParticipantResult() {
		return this.firstParticipantResult;
	}
	/**
	 * Getter of result of second participant.
	 * 
	 * @return The result of second participant.
	 */
	public GameOutcome getSecondParticipantResult() {
		return this.secondParticipantResult;
	}
	/**
	 * Setter of result of first participant.
	 * 
	 * @param score The result of participant.
	 */
	public void setFirstParticipantResult(GameOutcome gameOutcome) {
		this.firstParticipantResult = gameOutcome;
	}
	/**
	 * Setter of result of second participant
	 * 
	 * @param score The result of participant.
	 */
	public void setSecondParticipantResult(GameOutcome gameOutcome) {
		this.secondParticipantResult = gameOutcome;
	}

}
