package sieger.repository.database;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.User;
import sieger.repository.UserRepository;

@Repository("firebase")
public class UserDatabase implements UserRepository {
	
	private String path = "users";

	@Override
	public boolean createUser(User user) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(user.getUserId()).set(user);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUserById(String userId, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
