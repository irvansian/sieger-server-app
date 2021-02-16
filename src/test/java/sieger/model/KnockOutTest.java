package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class KnockOutTest {

	@Test
	void testCreateGames_checkSize() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
		assertTrue(tournament.createGames().size() == 2);
	}
	@Test
	void testCreateGames_checkParticipant() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		List<Game> games = tournament.createGames();
		assertTrue(games.get(0).getFirstParticipantId().equals("a"));
	}
	@Test
	void testCreateGames_checkState() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
		tournament.createGames();
		assertTrue(tournament.getCurrentState().toString().equals("KOROUND"));
	}
	@Test
	void testNextRound_checkSize() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertTrue(tournament.getCurrentGames().size() == 1);
	}
	@Test
	void testNextRound_checkState() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertTrue(tournament.getCurrentState().toString().equals("FINISH"));
	}
	@Test
	void testNextRound_checkParticipant() {
		Calendar c1 = Calendar.getInstance();
		c1.set(2021, 12, 12);
		Date startdate = c1.getTime();
		c1.set(2021, 12, 13);
		Date enddate = c1.getTime();
		c1.set(2020, 12, 12);
		Date date = c1.getTime();
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location",date,startdate,enddate,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		tournament.addParticipant("a");
		tournament.addParticipant("b");
		tournament.addParticipant("c");
		tournament.addParticipant("d");
		
		List<Game> games = tournament.createGames();
		games.get(0).setResult(new ScoreResult(10,11));
		games.get(1).setResult(new ScoreResult(10,11));
		
		tournament.createGames();
		assertTrue(tournament.getCurrentGames().get(0).getFirstParticipantId().equals("b") && tournament.getCurrentGames().get(0).getSecondParticipantId().equals("d"));
	}
	
}
