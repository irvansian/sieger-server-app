package sieger.repository.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import sieger.model.User;
import sieger.repository.UserRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserDatabaseTest {
	@Autowired
	private UserRepository userRepository;
	
	private User user = new User("username","surname", "forename", "userID");
	
	@Test
	@Order(1)
	void test_create() {
		assertTrue(userRepository.createUser(user));
	}
	
	@Test
	@Order(2)
	void test_getById() {
		User result = userRepository.retrieveUserById("userID").get();
		assertEquals(result.getUsername(), user.getUsername());
		assertEquals(result.getUserId(), user.getUserId());
	}
	
	@Test
	@Order(3)
	void test_getByName() {
		User result = userRepository.retrieveUserByUsername("username").get();
		assertEquals(result.getUsername(), user.getUsername());
		assertEquals(result.getUserId(), user.getUserId());
	}
	@Test
	@Order(4)
	void test_update() {
		User updated = new User("username","newsurname", "newforename", "userID");
		assertTrue(userRepository.updateUserById("userID", updated));
	}
	
	@Test
	@Order(5)
	void test_getById_Null() {
		assertEquals(userRepository.retrieveUserById("null"), Optional.empty());
	}
}
