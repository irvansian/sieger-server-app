package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTest {

	@Test
	void testGetWinnerId_first() {
		Game game = new Game(null, "first", "second");
		Result result = new ScoreResult(10,1);
		game.setResult(result);
		assertTrue(game.returnWinnerId().equals("first"));
	}
	@Test
	void testGetWinnerId_second() {
		Game game = new Game(null, "first", "second");
		Result result = new ScoreResult(1,10);
		game.setResult(result);
		assertTrue(game.returnWinnerId().equals("second"));
	}
	@Test
	void testGetWinnerId_draw() {
		Game game = new Game(null, "first", "second");
		Result result = new ScoreResult(1,1);
		game.setResult(result);
		assertTrue(game.returnWinnerId().equals("firstsecond"));
	}
}
