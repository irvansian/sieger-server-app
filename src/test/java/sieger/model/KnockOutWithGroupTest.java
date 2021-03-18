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
		assertTrue(tournament.getCurrentState().toString().equals("FINISH"));
	}
}
