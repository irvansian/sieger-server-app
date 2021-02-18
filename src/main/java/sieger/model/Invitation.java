package sieger.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The class of an invitation.
 * 
 * @author Chen Zhang
 *
 */
public class Invitation {
	/**
	 * The id of invitation.
	 */
	private String invitationId;
	/**
	 * The id of sender. Sender is always the admin of a tournament.
	 */
	private String senderId;
	/**
	 * The id of participant who receives the invitation. It can be a user id or a team id.
	 */
	private String recipientId;
	/**
	 * The id of the tournament, to which participant will be invitated.
	 */
	private String tournamentId;
	/**
	 * The form of participant.
	 */
	private ParticipantForm participantForm;
	/**
	 * No-argu constructor of the invitation class.
	 */
	public Invitation() {
	}
	/**
	 * Constructor of the invitation class. The id of invitation will be created automatically.
	 * 
	 * @param senderId The id of sender. Always the admin id of tournament.
	 * @param recipientId The id of participant who receives the invitation.
	 * @param tournamentId The id of tournament.
	 * @param participantForm The form of participant.
	 */
	@JsonCreator
	public Invitation(@JsonProperty("senderId")String senderId, 
			@JsonProperty("recipientId")String recipientId, 
			@JsonProperty("tournamentId")String tournamentId,
			@JsonProperty("participantForm") ParticipantForm participantForm) {
		this.recipientId = recipientId;
		this.senderId = senderId;
		this.tournamentId = tournamentId;
		this.invitationId = randomId();
		this.participantForm = participantForm;
	}
	/**
	 * Private method which creates the id of invitation.
	 * 
	 * @return Return the random id of invitation.
	 */
	private String randomId() {
		return UUID.randomUUID().toString();
	}
	/**
	 * Getter of invitation id.
	 * 
	 * @return Return the id of invitation.
	 */
	public String getInvitationId() {
		return this.invitationId;
	}
	/**
	 * Getter of sender id.
	 * 
	 * @return Return the id of sender.
	 */
	public String getSenderId() {
		return this.senderId;
	}
	/**
	 * Getter the id of recipient.
	 * 
	 * @return Return the id of recipient.
	 */
	public String getRecipientId() {
		return this.recipientId;
	}
	/**
	 * Getter of tournament id.
	 * 
	 * @return Return the id of tournament.
	 */
	public String getTournamentId() {
		return this.tournamentId;
	}
	/**
	 * Getter of participant form.
	 * 
	 * @return Return the participant form.
	 */
	public ParticipantForm getParticipantForm() {
		return this.participantForm;
	}
}
