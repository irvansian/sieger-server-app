package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WinLoseResultTest {

	@Test
	void testFirstWins() {
		WinLoseResult result = new WinLoseResult(GameOutcome.WIN,GameOutcome.LOSE);
		assertTrue(result.firstWins());
		
	}
	@Test
	void testSecondWins() {
		WinLoseResult result = new WinLoseResult(GameOutcome.LOSE,GameOutcome.WIN);
		assertTrue(result.secondWins());
	}
	@Test
	void testDrawWins() {
		WinLoseResult result = new WinLoseResult(GameOutcome.DRAW,GameOutcome.DRAW);
		assertTrue(result.draws());
	}

}
