package sieger.repository.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sieger.model.Game;
import sieger.model.GameOutcome;
import sieger.model.Result;
import sieger.model.ScoreResult;
import sieger.model.WinLoseResult;
import sieger.repository.GameRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GameDatabaseTest {
	@Autowired
	private GameRepository gameRepository;

	@Test
	void test_getGameById() {
		Game result = gameRepository.retrieveGameById("71b2679a-401c-4b7a-9f71-558274f09fb6","1a00c74f-9147-46c8-88c7-4c39c72ad5ab").get();
		assertEquals(result.getFirstParticipantId(), "PsWZZmzvggdETlTbxJdNUTnpG3t1");
	}
	@Test
	void test_CreateAndDelete() {
		Game game = new Game(new Date(), "first", "second");
		assertTrue(gameRepository.createGame("71b2679a-401c-4b7a-9f71-558274f09fb6", game));
		assertFalse(gameRepository.deleteGame("71b2679a-401c-4b7a-9f71-558274f09fb6", game.getGameId()));
	}
	@Test
	void test_update_score() {
		Game game = gameRepository.retrieveGameById("71b2679a-401c-4b7a-9f71-558274f09fb6","59f7f7cb-bc01-4e0e-867b-45415d7f5010").get();
		Result result = new ScoreResult(1,1);
		game.setResult(result);
		gameRepository.updateGame("71b2679a-401c-4b7a-9f71-558274f09fb6","59f7f7cb-bc01-4e0e-867b-45415d7f5010", game);
		assertTrue(gameRepository.retrieveGameById("71b2679a-401c-4b7a-9f71-558274f09fb6","59f7f7cb-bc01-4e0e-867b-45415d7f5010").get().getResult().draws());
	}
	@Test
	void test_update_Win() {
		Game game = gameRepository.retrieveGameById("74131d2a-ea0e-483f-b2e5-5803c41ff44a","01a528d3-efdf-4b95-a415-757ae91c68b3").get();
		Result result = new WinLoseResult(GameOutcome.WIN,GameOutcome.WIN);
		game.setResult(result);
		gameRepository.updateGame("74131d2a-ea0e-483f-b2e5-5803c41ff44a","01a528d3-efdf-4b95-a415-757ae91c68b3", game);
		assertNull(gameRepository.retrieveGameById("74131d2a-ea0e-483f-b2e5-5803c41ff44a","01a528d3-efdf-4b95-a415-757ae91c68b3").get().returnWinnerId());
	}
	@Test
	void test_update_Lose() {
		Game game = gameRepository.retrieveGameById("74131d2a-ea0e-483f-b2e5-5803c41ff44a","373c2a57-eaa7-412c-a420-6fc0690ef94b").get();
		Result result = new WinLoseResult(GameOutcome.LOSE,GameOutcome.LOSE);
		game.setResult(result);
		gameRepository.updateGame("74131d2a-ea0e-483f-b2e5-5803c41ff44a","373c2a57-eaa7-412c-a420-6fc0690ef94b", game);
		assertNull(gameRepository.retrieveGameById("74131d2a-ea0e-483f-b2e5-5803c41ff44a","373c2a57-eaa7-412c-a420-6fc0690ef94b").get().returnWinnerId());
	}
	@Test
	void test_update_Draw() {
		Game game = gameRepository.retrieveGameById("74131d2a-ea0e-483f-b2e5-5803c41ff44a","13bdc1c1-dd1b-4179-a80a-d60a9efb52d6").get();
		Result result = new WinLoseResult(GameOutcome.DRAW,GameOutcome.DRAW);
		game.setResult(result);
		gameRepository.updateGame("74131d2a-ea0e-483f-b2e5-5803c41ff44a","13bdc1c1-dd1b-4179-a80a-d60a9efb52d6", game);
		assertTrue(gameRepository.retrieveGameById("74131d2a-ea0e-483f-b2e5-5803c41ff44a","13bdc1c1-dd1b-4179-a80a-d60a9efb52d6").get().getResult().draws());
	}
}
