package sieger.repository;

import java.util.Optional;

import sieger.model.Invitation;
/**
 * The invitation repository interface.The invitation database implements it.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
public interface InvitationRepository {
	/**
	 * Create a new invitation in firebase.
	 * 
	 * @param invitation The invitation object to be stored.
	 * @return Return the invitation object after put it in the firebase.
	 */
	Invitation createInvitation(Invitation invitation);
	/**
	 * Retrieve the invitation from firebase with id.
	 * 
	 * @param invitationId The id of invitation.
	 * @return Return the invitation optional after searching.
	 */
	Optional<Invitation> retrieveInvitationById(String invitationId);
	/**
	 * Delete the invitation in the firebase with id.
	 * 
	 * @param invitationId The id of invitation to be deleted.
	 * @return Return true after it is successfully deleted.
	 */
	boolean deleteInvitation(String invitationId);
}
