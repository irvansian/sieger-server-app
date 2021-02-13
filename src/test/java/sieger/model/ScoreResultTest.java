package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreResultTest {

	@Test
	void testFirstWins() {
		ScoreResult result = new ScoreResult(10,1);
		assertTrue(result.firstWins());
		
	}
	@Test
	void testSecondWins() {
		ScoreResult result = new ScoreResult(1,10);
		assertTrue(result.secondWins());
	}
	@Test
	void testDrawWins() {
		ScoreResult result = new ScoreResult(10,10);
		assertTrue(result.draws());
	}


}
