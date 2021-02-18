package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class TournamentTest {

	@Test
	void testIsParticipant_Single() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		User user = new User("username","surname", "forename", "userID");
		user.joinTournament(tournament);
		assertTrue(tournament.isParticipant(user));
	}
	@Test
	void testIsParticipant_Team() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		League tournament = new League(4, "name", detail);
		User user = new User("username","surname", "forename", "userID");
		Team team = new Team("adminId", "name", "password");
		user.joinTeam(team, "password");
		team.joinTournament(tournament);
		assertTrue(tournament.isParticipant(user));
	}
	@Test
	void testAllowUserToJoinIn() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", date,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		assertTrue(tournament.allowUserToJoin());
	}
	@Test
	void testAllowTeamToJoinIn() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", date,null,null,ParticipantForm.TEAM);
		League tournament = new League(4, "name", detail);
		assertTrue(tournament.allowTeamToJoin());
	}
	@Test
	void testReadyToBeHeld() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", date,null,null,ParticipantForm.TEAM);
		League tournament = new League(2, "name", detail);
		User userA = new User("userAname","surname", "forename", "userAID");
		userA.joinTournament(tournament);
		User userB = new User("userBname","surname", "forename", "userBID");
		userB.joinTournament(tournament);
		assertTrue(tournament.readyToBeHeld());
	}
	@Test
	void testIsOpen() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		assertTrue(tournament.isOpen());
	}
	@Test
	void testIsAdmin() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		assertTrue(tournament.isAdmin("organisator"));
	}
	@Test
	void testCalculateDate_SameDay() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2020, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2020, 12, 12);
		Date enddate = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,startdate,enddate,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		assertTrue(tournament.calculateDate(0).equals(startdate));
	}
	@Test
	void testCalculateDate_NotSameDay() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2020, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2020, 12, 13);
		Date enddate = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,startdate,enddate,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		assertTrue(tournament.calculateDate(1).equals(startdate) && tournament.calculateDate(3).equals(enddate));
	}
}
