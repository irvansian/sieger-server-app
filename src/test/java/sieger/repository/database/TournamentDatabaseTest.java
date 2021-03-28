package sieger.repository.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import sieger.model.Game;
import sieger.model.GameOutcome;
import sieger.model.KnockOut;
import sieger.model.KnockOutWithGroup;
import sieger.model.League;
import sieger.model.LeagueTable;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Result;
import sieger.model.ScoreResult;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.model.WinLoseResult;
import sieger.repository.TournamentRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TournamentDatabaseTest {
	@Autowired
	private TournamentRepository tournamentRepository;
	@Test
	void test_getById_League() {
		Tournament result = tournamentRepository.retrieveTournamentById("71b2679a-401c-4b7a-9f71-558274f09fb6").get();
		assertEquals(result.getClass(), League.class);
	}
	@Test
	void test_getById_KnockOut() {
		Tournament result = tournamentRepository.retrieveTournamentById("614e7fcd-de06-4995-bd94-794a2cf28047").get();
		assertEquals(result.getClass(), KnockOut.class);
	}
	@Test
	void test_getById_KnockOutWithGroup() {
		Tournament result = tournamentRepository.retrieveTournamentById("74131d2a-ea0e-483f-b2e5-5803c41ff44a").get();
		assertEquals(result.getClass(), KnockOutWithGroup.class);
	}
	@Test
	void test_getByname_League() {
		Tournament result = tournamentRepository.retrieveTournamentByName("test2").get();
		assertEquals(result.getClass(), League.class);
	}
	@Test
	void test_getByname_KnockOut() {
		Tournament result = tournamentRepository.retrieveTournamentByName("test3").get();
		assertEquals(result.getClass(), KnockOut.class);
	}
	@Test
	void test_getByname_KnockOutWithGroup() {
		Tournament result = tournamentRepository.retrieveTournamentByName("test").get();
		assertEquals(result.getClass(), KnockOutWithGroup.class);
	}
	@Test
	void test_update_KnockOut() {
		Tournament result = tournamentRepository.retrieveTournamentById("614e7fcd-de06-4995-bd94-794a2cf28047").get();
		Game game = new Game(new Date(), "first", "second");
		Result scoreresult = new ScoreResult(1,1);
		game.setResult(scoreresult);
		((KnockOut)result).getCurrentGames().add(game);
		((KnockOut)result).getKoMapping().mapGameToKOBracket(4, game.getGameId());
		assertTrue(tournamentRepository.updateTournament("614e7fcd-de06-4995-bd94-794a2cf28047", result));
		Tournament tocheck = tournamentRepository.retrieveTournamentById("614e7fcd-de06-4995-bd94-794a2cf28047").get();
		assertEquals(tocheck.getClass(), KnockOut.class);
	}
	
	@Test
	void test_update_KnockOutWithGroup() {
		Tournament result = tournamentRepository.retrieveTournamentById("74131d2a-ea0e-483f-b2e5-5803c41ff44a").get();
		Game game = new Game(new Date(), "first", "second");
		Result winloseresult = new WinLoseResult(GameOutcome.WIN,GameOutcome.WIN);
		game.setResult(winloseresult);
		((KnockOutWithGroup)result).getCurrentGames().add(game);
		((KnockOutWithGroup)result).getTables().put("1", new LeagueTable());
		assertTrue(tournamentRepository.updateTournament("74131d2a-ea0e-483f-b2e5-5803c41ff44a", result));
		Tournament tocheck = tournamentRepository.retrieveTournamentById("74131d2a-ea0e-483f-b2e5-5803c41ff44a").get();
		assertEquals(tocheck.getClass(), KnockOutWithGroup.class);
	}
	
	@Test
	void test_createAndDelete() {
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		KnockOut tournament = new KnockOut(4, "name", detail);
		assertTrue(tournamentRepository.createTournament(tournament));
		assertTrue(tournamentRepository.deleteTournament(tournament.getTournamentId()));
	}
	@Test
	void test_getParticipant_single() {
		List<Participant> result = tournamentRepository.retrieveTournamentParticipants("74131d2a-ea0e-483f-b2e5-5803c41ff44a", ParticipantForm.SINGLE);
		assertEquals(result.get(0).getClass(), User.class);
	}
	@Test
	void test_getParticipant_Team() {
		List<Participant> result = tournamentRepository.retrieveTournamentParticipants("614e7fcd-de06-4995-bd94-794a2cf28047", ParticipantForm.TEAM);
		assertEquals(result.get(0).getClass(), Team.class);
	}
}
