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
	  assertTrue(table.getTables().get(0).getParticipantId().equals("third") && table.getTables().get(1).getParticipantId().equals("second") 
			  && table.getTables().get(2).getParticipantId().equals("first"));
	}
	@Test
	void testGetStandingById() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  participants.add("second");
	  participants.add("third");
	  LeagueTable table = new LeagueTable(participants);
	  ParticipantActualStanding result = table.getParticipantStandingById("first");
	  assertTrue(result.equals(table.getTables().get(0)));
	}
	@Test
	void testWin() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantWin("first");
	  assertTrue(table.getTables().get(0).getPlays() == 1 && table.getTables().get(0).getWin() == 1 && table.getTables().get(0).getPoints() == 2 );
	}
	@Test
	void testLose() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantLose("first");
	  assertTrue(table.getTables().get(0).getPlays() == 1 && table.getTables().get(0).getLose() == 1 && table.getTables().get(0).getPoints() == 0 );
	}
	@Test
	void testDraw() {
	  List<String> participants = new ArrayList<>();
	  participants.add("first");
	  LeagueTable table = new LeagueTable(participants);
	  table.participantDraw("first");
	  assertTrue(table.getTables().get(0).getPlays() == 1 && table.getTables().get(0).getDraw() == 1 && table.getTables().get(0).getPoints() == 1 );
	}
}
