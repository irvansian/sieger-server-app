package sieger.model;

import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnockOutTest {
	
	KnockOut tournament;
	
	@BeforeEach
	void setUp() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
	    tournament = new KnockOut(4, "name", detail);

		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
	}

	@Test
	void testCreateGames_checkSize() {
		assertEquals(tournament.createGames().size(),2);
	}
	@Test
	void testCreateGames_checkParticipant() {
		List<Game> games = tournament.createGames();
		assertEquals(games.get(0).getFirstParticipantId(), "a");
	}
	@Test
	void testCreateGames_checkState() {
		tournament.createGames();
		assertEquals(tournament.getCurrentState().toString(), "KOROUND");
	}
	@Test
	void testNextRound_checkSize() {
		
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertEquals(tournament.getCurrentGames().size(), 1);
	}
	@Test
	void testNextRound_checkState() {

		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertEquals(tournament.getCurrentState().toString(), "FINISH");
	}
	
	@Test
	void testCreateGames_NUll() {
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertNull(tournament.createGames());
	}

	@Test
	void testNextRound_checkParticipant() {

		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertEquals(tournament.getCurrentGames().get(0).getFirstParticipantId(), "b");
		assertEquals(tournament.getCurrentGames().get(0).getSecondParticipantId(), "d");
	}
	@Test
	void test_updateGame() {
		List<Game> games = tournament.createGames();
		ScoreResult result = new ScoreResult(10,11);
		games.get(1).setResult(result);
		tournament.updateGame(games.get(1));
		assertEquals(games.get(1).getResult(), result);
	}
	
}
