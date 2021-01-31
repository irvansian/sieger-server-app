package sieger.model;

import java.util.UUID;

public class Invitation {
	//id of invitation
	private String invitationId;
	//sender name
	private String senderName;
	//recipient user name
	private String recipientUsername;
	//tournament id
	private String tournamentId;
	//constructor
	public Invitation(String senderName, String recipientUsername, String tournamentId) {
		this.recipientUsername = recipientUsername;
		this.senderName = senderName;
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
	public String getSenderName() {
		return this.senderName;
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
