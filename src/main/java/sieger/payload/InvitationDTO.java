package sieger.payload;

import sieger.model.ParticipantForm;

public class InvitationDTO {
	private String invitationId;
	private String senderId;
	private String tournamentId;
	private String recipientId;
	private String senderUsername;
	private String tournamentName;
	private ParticipantForm participantForm;
	
	public ParticipantForm getParticipantForm() {
		return participantForm;
	}
	public void setParticipantForm(ParticipantForm participantForm) {
		this.participantForm = participantForm;
	}
	public String getInvitationId() {
		return invitationId;
	}
	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(String tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getSenderUsername() {
		return senderUsername;
	}
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	
	
	
	
}
