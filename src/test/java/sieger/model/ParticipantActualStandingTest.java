package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ParticipantActualStandingTest {

	@Test
	void testWinGame() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding("participantId");
		participantActualStanding.winGame();
		assertEquals(participantActualStanding.getPlays(), 1);
		assertEquals(participantActualStanding.getWin(), 1);
		assertEquals(participantActualStanding.getPoints(), 2);		
	}
	@Test
	void testLoseGame() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding("participantId1");
		participantActualStanding.loseGame();
		assertEquals(participantActualStanding.getPlays(), 1);
		assertEquals(participantActualStanding.getLose(), 1);
		assertEquals(participantActualStanding.getPoints(), 0);	
		
	}
	@Test
	void testDrawGame() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding("participantId2");
		participantActualStanding.drawGame();
		assertEquals(participantActualStanding.getPlays(), 1);
		assertEquals(participantActualStanding.getDraw(), 1);
		assertEquals(participantActualStanding.getPoints(), 1);			
	}
	@Test
	void test_constructor() {
		ParticipantActualStanding participantActualStanding = new ParticipantActualStanding();
		assertNull(participantActualStanding.getParticipantId());

	}
}
