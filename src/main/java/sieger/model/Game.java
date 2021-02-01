package sieger.model;

import java.util.Date;
import java.util.UUID;

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
	//constructor
	public Game(Date time, String firstParticipantId, String secondParticipantId) {
		this.time = time;
		this.firstParticipantId = firstParticipantId;
		this.secondParticipantId = secondParticipantId;
		this.gameId = randomId();
	}
	//get random Id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//get winner id
	public String getWinnerId() {
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
	//get first participant id
	public String getFirstParticipantId() {
		return this.firstParticipantId;
	}
	//get second participant id
	public String getSecondParticipantId() {
		return this.secondParticipantId;
	}
	//get game id
	public String getGameId() {
		return this.gameId;
	}
	//get result
	public Result getResult() {
		return this.result;
	}
	//set result
	public void setResult(Result result) {
		this.result = result;
	}
}
