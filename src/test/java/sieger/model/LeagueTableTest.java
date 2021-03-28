package sieger.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LeagueTableTest {

	@Test
	void testSort() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  participants.add("second");
	  participants.add("third");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantWin("third");
	  table.participantLose("first");
	  table.participantDraw("second");
	  table.sort();
	  assertEquals(table.getTables().get(0).getParticipantId(), "third");
	  assertEquals(table.getTables().get(1).getParticipantId(), "second");
	  assertEquals(table.getTables().get(2).getParticipantId(), "first");
	}
	@Test
	void testGetStandingById() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  participants.add("second");
	  participants.add("third");
	  LeagueTable table = new LeagueTable(participants);
	  ParticipantActualStanding result = table.getParticipantStandingById("first");
	  assertEquals(result, table.getTables().get(0));
	}
	@Test
	void testWin() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantWin("first");
	  assertEquals(table.getTables().get(0).getPlays(), 1);
	  assertEquals(table.getTables().get(0).getWin(), 1);
	  assertEquals(table.getTables().get(0).getPoints(), 2);
	}
	@Test
	void testLose() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantLose("first");
	  assertEquals(table.getTables().get(0).getPlays(), 1);
	  assertEquals(table.getTables().get(0).getLose(), 1);
	  assertEquals(table.getTables().get(0).getPoints(), 0);
	}
	@Test
	void testDraw() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantDraw("first");
	  assertEquals(table.getTables().get(0).getPlays(), 1);
	  assertEquals(table.getTables().get(0).getDraw(), 1);
	  assertEquals(table.getTables().get(0).getPoints(), 1);
	}
}
