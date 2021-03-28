package sieger.repository.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sieger.model.Team;
import sieger.repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeamDatabaseTest {
	@Autowired
	private TeamRepository teamRepository;
	
	
	@Test
	void test_getById() {
		Team result = teamRepository.retrieveTeamById("5001d255-5790-4b99-a6c2-8c9957eaa7da").get();
		assertEquals(result.getAdminId(), "admin");
	}
	
	@Test
	void test_getByName() {
		Team result = teamRepository.retrieveTeamByName("name").get();
		assertEquals(result.getAdminId(), "admin");
		assertEquals(result.getTeamId(), "5001d255-5790-4b99-a6c2-8c9957eaa7da");
	}
	@Test
	void test_updates() {
		Team result = teamRepository.retrieveTeamByName("name").get();
		result.addInvitation("newInvitation");
		teamRepository.updateTeam("5001d255-5790-4b99-a6c2-8c9957eaa7da", result);
		assertTrue(teamRepository.retrieveTeamByName("name").get().getInvitationList().contains("newInvitation"));
	}
	@Test
	void test_getById_Null() {
		assertEquals(teamRepository.retrieveTeamById("null"), Optional.empty());
	}
	
	@Test
	void test_CreateAnddelete() {
		Team anotherteam = new Team("toBedeleted", "tebedeleted", "tebedeleted");
		teamRepository.createTeam(anotherteam);
		assertTrue(teamRepository.deleteTeam(anotherteam.getTeamId()));
	}
}
