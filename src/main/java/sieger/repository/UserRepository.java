package sieger.repository;

import java.util.Optional;
import sieger.model.User;

public interface UserRepository {
	boolean createUser(User user);
	Optional<User> retrieveUserById(String userId);
	Optional<User> retrieveUserByUsername(String username);
	boolean updateUserById(String userId, User user);
	boolean deleteUser(String userId);
}
