package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeagueTest {
	
	League tournament;
	@BeforeEach
	void setUp() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 14);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",null,null,null,ParticipantForm.SINGLE);
		detail.setEndTime(enddate);
		detail.setStartTime(startdate);
		detail.setRegistrationDeadline(date);
		tournament = new League(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
	}

	@Test
	void testCreateGames_checkSize() {

		assertEquals(tournament.createGames().size(), 6);
	}
	@Test
	void testCreateGames_checkParticipant() {
		List<Game> games = tournament.createGames();
		assertTrue(games.get(5).getFirstParticipantId().equals("c"));
	}
	@Test
	void testCreateGames_checkState() {
		tournament.createGames();
		assertTrue(tournament.getCurrentState().toString().equals("FINISH"));
	}
	@Test
	void testCreateGames_Fail() {
		tournament.setParticipantList(new ArrayList<String>());
		assertEquals(tournament.createGames(), null);
	}
	@Test
	void testCreateGames_Finish() {
		tournament.createGames();
		assertEquals(tournament.createGames(), null);
	}
	@Test
	void test_updateGame_Group_secondWins() {
		List<Game> games = tournament.createGames();
		ScoreResult result = new ScoreResult(10,11);
		games.get(1).setResult(result);
		tournament.updateGame(games.get(1));
		assertEquals(games.get(1).getResult(), result);
	}
	@Test
	void test_updateGame_Group_firstWins() {
		List<Game> games = tournament.createGames();
		ScoreResult result = new ScoreResult(12,11);
		games.get(0).setResult(result);
		tournament.updateGame(games.get(0));
		assertEquals(games.get(0).getResult(), result);
	}
	@Test
	void test_updateGame_Group_draws() {
		List<Game> games = tournament.createGames();
		ScoreResult result = new ScoreResult(11,11);
		games.get(1).setResult(result);
		tournament.updateGame(games.get(1));
		assertEquals(games.get(1).getResult(), result);
	}
}
