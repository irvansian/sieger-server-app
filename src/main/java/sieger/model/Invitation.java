package sieger.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Invitation {
	//id of invitation
	private String invitationId;
	//sender name
	private String senderId;
	//recipient user name
	private String recipientId;
	//tournament id
	private String tournamentId;
	
	private ParticipantForm participantForm;
	
	public Invitation() {
	}
	//constructor
	@JsonCreator
	public Invitation(@JsonProperty("senderId")String senderId, 
			@JsonProperty("recipientId")String recipientId, 
			@JsonProperty("tournamentId")String tournamentId,
			@JsonProperty("participantForm") ParticipantForm participantForm) {
		this.recipientId = recipientId;
		this.senderId = senderId;
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
	public String getSenderId() {
		return this.senderId;
	}
	//get recipient username
	public String getRecipientId() {
		return this.recipientId;
	}
	//get tournament id
	public String getTournamentId() {
		return this.tournamentId;
	}
	
	public ParticipantForm getParticipantForm() {
		return this.participantForm;
	}
}
