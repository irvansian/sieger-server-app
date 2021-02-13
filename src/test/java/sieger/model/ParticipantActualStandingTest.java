package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ParticipantActualStandingTest {

	@Test
	void testWinGame() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding("participantId");
		participantActualStanding.winGame();
		assertTrue(participantActualStanding.getPlays() == 1 && participantActualStanding.getWin() == 1 && participantActualStanding.getPoints() == 2);
		
	}
	@Test
	void testLoseGame() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding("participantId1");
		participantActualStanding.loseGame();
		assertTrue(participantActualStanding.getPlays() == 1 && participantActualStanding.getLose() == 1 && participantActualStanding.getPoints() == 0);
		
	}
	@Test
	void testDrawGame() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding("participantId2");
		participantActualStanding.drawGame();
		assertTrue(participantActualStanding.getPlays() == 1 && participantActualStanding.getDraw() == 1 && participantActualStanding.getPoints() == 1);
		
	}
}
