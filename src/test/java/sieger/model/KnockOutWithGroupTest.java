package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnockOutWithGroupTest {
	
	KnockOutWithGroup tournament;
	
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
		tournament = new KnockOutWithGroup(8, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		tournament.addParticipant("e");
		tournament.addParticipant("f");
		tournament.addParticipant("g");
		tournament.addParticipant("h");
		
	}

	@Test
	void testCreateGroupGames_checkState() {
		
		tournament.createGames();
		
		assertTrue(tournament.getCurrentState().toString().equals("GROUP"));
	}
	@Test
	void testCreateKOGames_checkState() {
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(5).setResult(new ScoreResult(10,11));
		games.get(7).setResult(new ScoreResult(10,11));
		games.get(9).setResult(new ScoreResult(10,11));
		tournament.createGames();
		assertTrue(tournament.getCurrentState().toString().equals("KOROUND"));
	}
	
	@Test
	void test_updateGame_Group_secondWins() {
		List<Game> games = tournament.createGames();
		ScoreResult result = new ScoreResult(10,11);
		games.get(7).setResult(result);
		tournament.updateGame(games.get(7));
		assertEquals(games.get(7).getResult(), result);
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
		games.get(7).setResult(result);
		tournament.updateGame(games.get(7));
		assertEquals(games.get(7).getResult(), result);
	}
	
	@Test
	void test_updateGame_KORound() {
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(5).setResult(new ScoreResult(10,11));
		games.get(7).setResult(new ScoreResult(10,11));
		games.get(9).setResult(new ScoreResult(10,11));
		List<Game> Kogames = tournament.createGames();
		ScoreResult result = new ScoreResult(10,11);
		Kogames.get(1).setResult(result);
		tournament.updateGame(Kogames.get(1));
		assertEquals(Kogames.get(1).getResult(), result);
	}
	@Test
	void test_createGame_NextKo() {
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(5).setResult(new ScoreResult(10,11));
		games.get(7).setResult(new ScoreResult(10,11));
		games.get(9).setResult(new ScoreResult(10,11));
		List<Game> Kogames = tournament.createGames();
		Kogames.get(0).setResult(new ScoreResult(10,11));
		Kogames.get(1).setResult(new ScoreResult(10,11));
		tournament.createGames();
		assertTrue(tournament.getCurrentState().toString().equals("FINISH"));
	}
}
