package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreResultTest {

	@Test
	void testFirstWins() {
		ScoreResult result = new ScoreResult(10,1);
		assertTrue(result.firstWins());
		assertEquals(result.getFirstParticipantResult(), 10);
		assertEquals(result.getSecondParticipantResult(), 1);
		assertEquals(result.getType(), "Score");
		
	}
	@Test
	void testSecondWins() {
		ScoreResult result = new ScoreResult(1,10);
		assertTrue(result.secondWins());
		assertFalse(result.draws());
	}
	@Test
	void testDraws() {
		ScoreResult result = new ScoreResult(10,10);
		assertTrue(result.draws());
	}


}
