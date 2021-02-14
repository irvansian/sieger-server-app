package sieger.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Invitation {
	//id of invitation
	private String invitationId;
	//sender name
	private String senderUsername;
	//recipient user name
	private String recipientUsername;
	//tournament id
	private String tournamentId;
	public Invitation() {
	}
	//constructor
	@JsonCreator
	public Invitation(@JsonProperty("senderUsername")String senderUsername, @JsonProperty("recipientUsername")String recipientUsername, @JsonProperty("tournamentId")String tournamentId) {
		this.recipientUsername = recipientUsername;
		this.senderUsername = senderUsername;
		this.tournamentId = tournamentId;
		this.invitationId = randomId();
	}
	//get random id
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	//get id
	public String getInvitationId() {
		return this.invitationId;
	}
	//get sender name
	public String getSenderUsername() {
		return this.senderUsername;
	}
	//get recipient username
	public String getRecipientUsername() {
		return this.recipientUsername;
	}
	//get tournament id
	public String getTournamentId() {
		return this.tournamentId;
	}
}
