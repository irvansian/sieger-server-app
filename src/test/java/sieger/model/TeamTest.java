package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeamTest {

	@Test
	void testGetParticipantName() {
		Team team = new Team("admin", "name", "password");
		assertTrue(team.findParticipantName().equals("name"));
	}

}
