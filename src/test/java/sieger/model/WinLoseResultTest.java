package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WinLoseResultTest {

	@Test
	void testFirstWins() {
		WinLoseResult result = new WinLoseResult();
		result.setFirstParticipantResult(GameOutcome.WIN);
		result.setSecondParticipantResult(GameOutcome.LOSE);
		result.setType("Winlose");
		assertTrue(result.firstWins());
		assertEquals(result.getType(), "Winlose");
	}
	@Test
	void testFirstWins_FalseInput() {
		WinLoseResult result = new WinLoseResult(GameOutcome.WIN,GameOutcome.WIN);
		assertFalse(result.firstWins());
	}
	@Test
	void testFirstWins_False() {
		WinLoseResult result = new WinLoseResult(GameOutcome.LOSE,GameOutcome.WIN);
		assertFalse(result.firstWins());
	}
	@Test
	void testSecondWins() {
		WinLoseResult result = new WinLoseResult(GameOutcome.LOSE,GameOutcome.WIN);
		assertTrue(result.secondWins());
	}
	@Test
	void testSecondWins_FalseInput() {
		WinLoseResult result = new WinLoseResult(GameOutcome.LOSE,GameOutcome.LOSE);
		assertFalse(result.secondWins());
	}
	@Test
	void testSecondWins_False() {
		WinLoseResult result = new WinLoseResult(GameOutcome.WIN,GameOutcome.LOSE);
		assertFalse(result.secondWins());
	}
	@Test
	void testDraws() {
		WinLoseResult result = new WinLoseResult(GameOutcome.DRAW,GameOutcome.DRAW);
		assertTrue(result.draws());
	}
	@Test
	void testDraws_FalseInput() {
		WinLoseResult result = new WinLoseResult(GameOutcome.WIN,GameOutcome.LOSE);
		assertFalse(result.draws());
	}
	@Test
	void testDraw_False() {
		WinLoseResult result = new WinLoseResult(GameOutcome.DRAW,GameOutcome.LOSE);
		assertFalse(result.draws());
	}

}
