package sieger.repository.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Invitation;
import sieger.repository.InvitationRepository;
/**
 * The invitation database class. We use firebase as database.
 * The class implements the invitation repository.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@Repository("invitationDB")
public class InvitationDatabase implements InvitationRepository {
	/**
	 * Path to the firebase document.
	 */
	private String path = "invitations";
	/**
	 * Create a new invitation in firebase.
	 * 
	 * @param invitation The invitation object to be stored.
	 * @return Return the invitation object after put it in the firebase.
	 */
	@Override
	public Invitation createInvitation(Invitation invitation) {
		
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> invitationDoc = new HashMap<>();
		invitationDoc.put("senderId", invitation.getSenderId());
		invitationDoc.put("recipientId", invitation.getRecipientId());
		invitationDoc.put("tournamentId", invitation.getTournamentId());
		invitationDoc.put("invitationId", invitation.getInvitationId());
		invitationDoc.put("participantForm", invitation.getParticipantForm());
		db.collection(path).document(invitation.getInvitationId()).set(invitationDoc);
		return invitation;
	}
	/**
	 * Retrieve the invitation from firebase with id.
	 * 
	 * @param invitationId The id of invitation.
	 * @return Return the invitation optional after searching.
	 */
	@Override
	public Optional<Invitation> retrieveInvitationById(String invitationId) {
		Invitation invitation = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> future = db.collection(path)
				.document(invitationId).get();
		
		try {
			if (future.get().exists()) {
				invitation = future.get().toObject(Invitation.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(invitation);
	}
	/**
	 * Delete the invitation in the firebase with id.
	 * 
	 * @param invitationId The id of invitation to be deleted.
	 * @return Return true after it is successfully deleted.
	 */
	@Override
	public boolean deleteInvitation(String invitationId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(invitationId).delete();
		return true;
	}

}
