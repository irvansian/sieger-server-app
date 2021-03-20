package sieger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The score result class which implement the result.
 * The result should be score.
 * 
 * @author 张宸
 *
 */
public class ScoreResult implements Result{
	/**
	 * The score of first participant.
	 */
	private int firstParticipantResult;
	/**
	 * The score of second participant.
	 */
	private int secondParticipantResult;
	/**
	 * The type of Result.Here is always score.
	 */
	private String type;
	/**
	 * No-argument constructor.
	 */
	public ScoreResult() {
		
	}
	/**
	 * Constructor of a score result.
	 * 
	 * @param firstParticipantResult The score of first participant.
	 * @param secondParticipantResult The score of second participant.
	 */
	@JsonCreator
	public ScoreResult(@JsonProperty("firstParticipantResult")int firstParticipantResult, @JsonProperty("secondParticipantResult")int secondParticipantResult) {
		this.setFirstParticipantResult(firstParticipantResult);
		this.setSecondParticipantResult(secondParticipantResult);
		this.setType("Score");
	}
	/**
	 * Override the method in result interface.
	 * 
	 * @return Return true if first participant wins.
	 */
	@Override
	public boolean firstWins() {
		if(firstParticipantResult > secondParticipantResult) {
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
		if(firstParticipantResult < secondParticipantResult) {
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
		if(firstParticipantResult == secondParticipantResult) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Getter of score of first participant.
	 * 
	 * @return The score of first participant.
	 */
	public int getFirstParticipantResult() {
		return this.firstParticipantResult;
	}
	/**
	 * Getter of score of second participant.
	 * 
	 * @return The score of second participant.
	 */
	public int getSecondParticipantResult() {
		return this.secondParticipantResult;
	}
	/**
	 * Setter of score of first participant.
	 * 
	 * @param score The score of participant.
	 */
	public void setFirstParticipantResult(int score) {
		this.firstParticipantResult = score;
	}
	/**
	 * Setter of score of second participant
	 * 
	 * @param score The score of participant.
	 */
	public void setSecondParticipantResult(int score) {
		this.secondParticipantResult = score;
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
}
