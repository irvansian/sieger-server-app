package sieger.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {
	//game Id
	private String gameId;
	//time of game
	private Date time;
	//result of game
	private Result result;
	//first participant id
	private String firstParticipantId;
	//second participant id
	private String secondParticipantId;
	public Game() {
		
	}
	//constructor
	@JsonCreator
	public Game(@JsonProperty("time")Date time, @JsonProperty("firstParticipantId")String firstParticipantId, @JsonProperty("secondParticipantId")String secondParticipantId) {
		this.time = time;
		this.firstParticipantId = firstParticipantId;
		this.secondParticipantId = secondParticipantId;
		this.setGameId(randomId());
		this.result = null;
	}
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//get winner id
	public String returnWinnerId() {
		if(result.firstWins()) {
			return this.firstParticipantId;
		}
		if(result.secondWins()) {
			return this.secondParticipantId;
		}
		if(result.draws()) {
			return this.firstParticipantId + this.secondParticipantId;
		}
		return null;
	}
	//get time
	public Date getTime() {
		return this.time;
	}
	public String getFirstParticipantId() {
		return this.firstParticipantId;
	}
	public String getSecondParticipantId() {
		return this.secondParticipantId;
	}
	public void setFirstParticipantId(String id) {
		this.firstParticipantId = id;
	}
	public void setSecondParticipantId(String id) {
		this.secondParticipantId = id;
	}
	//get result
	public Result getResult() {
		return this.result;
	}
	//set result
	public void setResult(Result result) {
		this.result = result;
	}
	//set time
	public void setTime(Date date) {
		this.time = date;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
