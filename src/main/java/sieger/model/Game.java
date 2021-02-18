package sieger.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The class of game.
 * 
 * @author Chen Zhanf
 *
 */
public class Game {
	/**
	 * The id of game, which is unique.
	 */
	private String gameId;
	/**
	 * The time of the game.
	 */
	private Date time;
	/**
	 * The resul of the game.
	 */
	private Result result;
	/**
	 * The id of one participant, it can be a user or a team.
	 */
	private String firstParticipantId;
	/**
	 * The id of another participant, it can be a user or a team.
	 */
	private String secondParticipantId;
	/**
	 * No-argument constructor of class.
	 */
	public Game() {
		
	}
	/**
	 * Constructor of a game.
	 * 
	 * @param time The time of the game.
	 * @param firstParticipantId The id of one participant.
	 * @param secondParticipantId The id of another participant.
	 */
	@JsonCreator
	public Game(@JsonProperty("time")Date time, 
			@JsonProperty("firstParticipantId")String firstParticipantId, 
			@JsonProperty("secondParticipantId")String secondParticipantId) {
		this.time = time;
		this.firstParticipantId = firstParticipantId;
		this.secondParticipantId = secondParticipantId;
		this.setGameId(randomId());
		
	}
	/**
	 * Private method to get random id of game.
	 * 
	 * @return The id created by UUID.
	 */
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	/**
	 * To get the winner of the game. It will call the method in result.
	 * 
	 * @return The id of winner.
	 */
	public String returnWinnerId() {
		if(result.firstWins()) {
			return this.firstParticipantId;
		}
		if(result.secondWins()) {
			return this.secondParticipantId;
		}
		if(result.draws()) {
			return null;
		}
		return null;
	}
	/**
	 * Getter of time.
	 * 
	 * @return Return the time of game.
	 */
	public Date getTime() {
		return this.time;
	}
	/**
	 * Getter for id of one participant.
	 * 
	 * @return Return the id of participant.
	 */
	public String getFirstParticipantId() {
		return this.firstParticipantId;
	}
	/**
	 * Getter for id of another participant.
	 * 
	 * @return Return the id of another participant.
	 */
	public String getSecondParticipantId() {
		return this.secondParticipantId;
	}
	/**
	 * Setter for id of one participant.
	 * 
	 * @param id The id of this participant.
	 */
	public void setFirstParticipantId(String id) {
		this.firstParticipantId = id;
	}
	/**
	 * Setter for id of another participant.
	 * 
	 * @param id The id of this participant.
	 */
	public void setSecondParticipantId(String id) {
		this.secondParticipantId = id;
	}
	/**
	 * Getter of the result.
	 * 
	 * @return Return the current result.
	 */
	public Result getResult() {
		return this.result;
	}
	/**
	 * Setter of the result.
	 * 
	 * @param result Result of the game.
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	/**
	 * Setter of the time.
	 * 
	 * @param date Time of the game.
	 */
	public void setTime(Date date) {
		this.time = date;
	}
	/**
	 * Getter of id the game.
	 * 
	 * @return Return the id of game.
	 */
	public String getGameId() {
		return gameId;
	}
	/**
	 * Setter of game.
	 * 
	 * @param gameId The id of game.
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
