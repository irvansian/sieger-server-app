package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeamTest {

	@Test
	void testFindParticipantName() {
		Team team = new Team("admin", "name", "password");
		assertTrue(team.findParticipantName().equals("name"));
	}
	@Test
	void testCheckPassword_true() {
		Team team = new Team("admin", "name", "password");
		assertTrue(team.checkPassword("password"));
	}
	@Test
	void testCheckPassword_false() {
		Team team = new Team("admin", "name", "password");
		assertFalse(team.checkPassword("falsepassword"));
	}
	@Test
	void testCheckAdmin_true() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username", "surname", "forename", "admin");
		assertTrue(team.checkAdmin(user));
	}
	@Test
	void testCheckAdmin_false() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username", "surname", "forename", "notadmin");
		assertFalse(team.checkAdmin(user));
	}
	@Test
	void testkickMemer() {
		Team team = new Team("admin", "name", "password");
		User user = new User("username", "surname", "forename", "firstMember");
		user.addTeam(team.getTeamId());
		team.addMember(user.getUserId());
		team.kickMember(user);
		assertTrue(!team.getMemberList().contains(user.getUserId()) && !user.getTeamList().contains(team.getTeamId()));
	}
	@Test
	void testJoinTournament() {
		TournamentDetail detail = new TournamentDetail("organisatorId", TournamentTypes.OPEN, "typeOfGame", "location", null, null, null, ParticipantForm.TEAM);
		Tournament tournament = new League(8,"name",detail);
		Team team = new Team("admin", "name", "password");
		assertTrue(team.joinTournament(tournament));
	}
	@Test
	void testQuitTournament() {
		TournamentDetail detail = new TournamentDetail("organisatorId", TournamentTypes.OPEN, "typeOfGame", "location", null, null, null, ParticipantForm.TEAM);
		Tournament tournament = new League(8,"name",detail);
		Team team = new Team("admin", "name", "password");
		team.joinTournament(tournament);
		assertTrue(team.quitTournament(tournament));
	}
	
}
