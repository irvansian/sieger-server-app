package sieger.repository;

import java.util.Optional;
import sieger.model.User;
/**
 * The user repository interface.The user database implements it.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
public interface UserRepository {
	/**
	 * Create a new user in firebase.
	 * 
	 * @param user The user object to be stored.
	 * @return Return the user object after put it in the firebase.
	 */
	boolean createUser(User user);
	/**
	 * Retrieve the user from firebase with id.
	 * 
	 * @param userId The id of user.
	 * @return Return user optional after searching.
	 */
	Optional<User> retrieveUserById(String userId);
	/**
	 * Retrieve the user with name.
	 * 
	 * @param username Name of user to be searched.
	 * @return Return the user optional after searching.
	 */
	Optional<User> retrieveUserByUsername(String username);
	/**
	 * Update the data of user in firebase.
	 * 
	 * @param userId The id of user to be updated.
	 * @param user The user with data to be updated.
	 * @return Return true after updating.
	 */
	boolean updateUserById(String userId, User user);
}
