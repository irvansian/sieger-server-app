package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest {

	@Test
	void test_findParticipantName() {
		User user = new User("username","surname", "forename", "userID");
		assertTrue(user.findParticipantName().equals(user.getUsername()));
	}
	
	@Test
	void test_quitTournament() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		User user = new User("username","surname", "forename", "userID");
		user.joinTournament(tournament);
		user.quitTournament(tournament);
		assertFalse(tournament.isParticipant(user));
	}
	
	@Test
	void test_joinTeam_fail() {
		User user = new User("username","surname", "forename", "userID");
		Team team = new Team("adminId", "name", "password");
		assertFalse(user.joinTeam(team, "falsePassword"));
		assertFalse(user.quitTeam(team));
	}
	
	@Test
	void test_showNextTournament_ThreeTournamentsInlist() {
		User user = new User("username","surname", "forename", "userID");
		List<Tournament> tournaments = new ArrayList<>();
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 14);
		Date enddate_1 = c1.getTime();
		TournamentDetail detail_1 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_1,ParticipantForm.SINGLE);
		League tournament_1 = new League(4, "name1", detail_1);
		tournaments.add(tournament_1);
		user.joinTournament(tournament_1);
		c1.set(2021, 12, 13);
		Date enddate_2 = c1.getTime();
		TournamentDetail detail_2 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_2,ParticipantForm.SINGLE);
		League tournament_2 = new League(4, "name2", detail_2);
		user.joinTournament(tournament_2);
		tournaments.add(tournament_2);
		c1.set(2021, 12, 12);
		Date enddate_3 = c1.getTime();
		TournamentDetail detail_3 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_3,ParticipantForm.SINGLE);
		League tournament_3 = new League(4, "name3", detail_3);
		user.joinTournament(tournament_3);
		tournaments.add(tournament_3);
		
		List<String> target = Arrays.asList(tournament_3.getTournamentId(), tournament_2.getTournamentId(), tournament_1.getTournamentId());
		assertTrue(user.showNextTournaments(tournaments).equals(target));
	}
	
	@Test
	void test_showNextTournament_TwoTournamentsInlist() {
		User user = new User("username","surname", "forename", "userID");
		List<Tournament> tournaments = new ArrayList<>();
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date enddate_1 = c1.getTime();
		TournamentDetail detail_1 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_1,ParticipantForm.SINGLE);
		League tournament_1 = new League(4, "name1", detail_1);
		tournaments.add(tournament_1);
		user.joinTournament(tournament_1);
		c1.set(2021, 12, 13);
		Date enddate_2 = c1.getTime();
		TournamentDetail detail_2 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_2,ParticipantForm.SINGLE);
		League tournament_2 = new League(4, "name2", detail_2);
		user.joinTournament(tournament_2);
		tournaments.add(tournament_2);
		
		List<String> target = Arrays.asList(tournament_1.getTournamentId(), tournament_2.getTournamentId());
		assertTrue(user.showNextTournaments(tournaments).equals(target));
	}
	@Test
	void test_showNextTournament_OneTournamentsInlist() {
		User user = new User("username","surname", "forename", "userID");
		List<Tournament> tournaments = new ArrayList<>();
		Calendar c1 = Calendar.getInstance();
		c1.set(2019, 12, 12);
		Date enddate_1 = c1.getTime();
		TournamentDetail detail_1 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_1,ParticipantForm.SINGLE);
		League tournament_1 = new League(4, "name1", detail_1);
		tournaments.add(tournament_1);
		user.joinTournament(tournament_1);
		c1.set(2021, 12, 13);
		Date enddate_2 = c1.getTime();
		TournamentDetail detail_2 = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,enddate_2,ParticipantForm.SINGLE);
		League tournament_2 = new League(4, "name2", detail_2);
		user.joinTournament(tournament_2);
		tournaments.add(tournament_2);
		
		List<String> target = Arrays.asList(tournament_2.getTournamentId());
		assertTrue(user.showNextTournaments(tournaments).equals(target));
	}
}
