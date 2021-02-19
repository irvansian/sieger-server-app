package sieger.repository.database;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.User;
import sieger.repository.UserRepository;
/**
 * The user database class. We use firebase as database.
 * The class implements the user repository.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@Repository("userDB")
public class UserDatabase implements UserRepository {
	/**
	 * Path to the firebase document.
	 */
	private String path = "users";
	/**
	 * Create a new user in firebase.
	 * 
	 * @param user The user object to be stored.
	 * @return Return the user object after put it in the firebase.
	 */
	@Override
	public boolean createUser(User user) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> userDoc = new HashMap<>();
		userDoc.put("userId", user.getUserId());
		userDoc.put("username", user.getUsername());
		userDoc.put("surname", user.getSurname());
		userDoc.put("forename", user.getForename());
		userDoc.put("teamList", user.getTeamList());
		userDoc.put("tournamentList", user.getTournamentList());
		userDoc.put("invitationList", user.getInvitationList());
		db.collection(path).document(user.getUserId()).set(userDoc);
		return true;
	}
	/**
	 * Retrieve the user from firebase with id.
	 * 
	 * @param userId The id of user.
	 * @return Return user optional after searching.
	 */
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
	/**
	 * Retrieve the user with name.
	 * 
	 * @param username Name of user to be searched.
	 * @return Return the user optional after searching.
	 */
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
	/**
	 * Update the data of user in firebase.
	 * 
	 * @param userId The id of user to be updated.
	 * @param user The user with data to be updated.
	 * @return Return true after updating.
	 */
	@Override
	public boolean updateUserById(String userId, User user) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(userId).set(user);
		return true;
	}
	/**
	 * Delete the user data in firebase.
	 * 
	 * @param userId The id of user to be deleted.
	 * @return Return true after delete the data.
	 */
	@Override
	public boolean deleteUser(String userId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(userId).delete();
		return true;
	}

	

}
