package sieger.repository.database;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.User;
import sieger.repository.UserRepository;

@Repository("userDB")
public class UserDatabase implements UserRepository {
	
	private String path = "users";

	@Override
	public boolean createUser(User user) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> userDoc = new HashMap<>();
		userDoc.put("username", user.getUsername());
		userDoc.put("surname", user.getSurname());
		userDoc.put("forename", user.getForename());
		userDoc.put("notificationList", user.getNotificationList());
		userDoc.put("teamList", user.getTeamList());
		userDoc.put("tournamentList", user.getTournamentList());
		userDoc.put("invitationList", user.getInvitationList());
		db.collection(path).document(user.getUserId()).set(userDoc);
		return true;
	}

	@Override
	public Optional<User> retrieveUserById(String userId) {
		User user = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> future = db.collection(path)
				.document(userId).get();
		
		try {
			if (future.get().exists()) {
				user = future.get().toObject(User.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> retrieveUserByUsername(String username) {
		User user = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = db.collection(path)
				.whereEqualTo("username", username).get();
		try {
			for (DocumentSnapshot ds : future.get().getDocuments()) {
				user = ds.toObject(User.class);
				break;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}

	@Override
	public boolean updateUserById(String userId, User user) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(userId).set(user);
		return true;
	}

	@Override
	public boolean deleteUser(String userId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(userId).delete();
		return true;
	}

	

}
