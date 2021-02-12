package sieger.repository.database;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Invitation;
import sieger.repository.InvitationRepository;

@Repository("invitationDB")
public class InvitationDatabase implements InvitationRepository {
	
	private String path = "invitations";

	@Override
	public boolean createInvitation(Invitation invitation) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(invitation.getInvitationId()).set(invitation);
		return true;
	}

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

	@Override
	public boolean deleteInvitation(String invitationId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(invitationId).delete();
		return true;
	}

}
