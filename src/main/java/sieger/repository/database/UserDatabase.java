package sieger.repository.database;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
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
		// TODO Auto-generated method stub
		return null;
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
