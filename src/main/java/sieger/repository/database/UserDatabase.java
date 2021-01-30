package sieger.repository.database;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.google.firebase.auth.FirebaseAuth;

import sieger.model.User;
import sieger.repository.UserRepository;

@Repository("firebase")
public class UserDatabase implements UserRepository {

	@Override
	public boolean createUser(User user) {
		FirebaseAuth auth = FirebaseAuth.getInstance();
		
		return false;
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
