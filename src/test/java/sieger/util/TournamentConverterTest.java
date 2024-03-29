package sieger.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import sieger.model.KnockOut;
import sieger.model.KnockOutWithGroup;
import sieger.model.ParticipantForm;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.payload.TournamentDTO;

public class TournamentConverterTest {
	@Test
	public void testConvertToTournamentDTO_KnockOut() {
		int participantSize = 8;
		String tournamentName = "TestTournament";
		String organizerId = "idoforganizer";
		TournamentDetail td = new TournamentDetail(organizerId, TournamentTypes.PRIVATE, "chess"
				, "karlsruhe", new Date(), new Date(), new Date(), ParticipantForm.SINGLE);
		Tournament testTournament = new KnockOut(participantSize, tournamentName, td);
		testTournament.createGames();
		TournamentDTO testTournamentDTO = TournamentConverter.convertToTournamentDTO(testTournament);
		
		assertEquals(testTournamentDTO.getTournamentId(), testTournament.getTournamentId());
		assertEquals(testTournamentDTO.getTournamentDetail(), testTournament.getTournamentDetail());
		assertEquals(testTournamentDTO.getGameList(), testTournament.getGameList());
		assertEquals(testTournamentDTO.getParticipantList(), testTournament.getParticipantList());
		assertEquals(testTournamentDTO.getTournamentName(), testTournament.getTournamentName());
		assertEquals(testTournamentDTO.getGameList(), testTournament.getGameList());
		assertEquals(testTournamentDTO.getMaxParticipantNumber(), testTournament.getMaxParticipantNumber());
		assertEquals(testTournamentDTO.getGameList(), testTournament.getGameList());
		assertEquals(testTournamentDTO.getType(), "KnockOut");
		assertEquals(testTournamentDTO.getCurrentState(), testTournament.getCurrentState());
		assertEquals(testTournamentDTO.isOpen(), testTournament.isOpen());
		assertEquals(testTournamentDTO.getSpecifiedAttributes().get("currentGames"), ((KnockOut) testTournament).getCurrentGames());
		assertEquals(testTournamentDTO.getSpecifiedAttributes().get("koMapping"), ((KnockOut) testTournament).getKoMapping());
		
	}
	
	@Test
	public void testConvertToTournamentDTO_KnockOutWithGroup() {
		int participantSize = 8;
		String tournamentName = "TestTournament";
		String organizerId = "idoforganizer";
		TournamentDetail td = new TournamentDetail(organizerId, TournamentTypes.PRIVATE, "chess"
				, "karlsruhe", new Date(), new Date(), new Date(), ParticipantForm.SINGLE);
		Tournament testTournament = new KnockOutWithGroup(participantSize, tournamentName, td);
		testTournament.createGames();
		TournamentDTO testTournamentDTO = TournamentConverter.convertToTournamentDTO(testTournament);
		
		assertEquals(testTournamentDTO.getTournamentId(), testTournament.getTournamentId());
		assertEquals(testTournamentDTO.getTournamentDetail(), testTournament.getTournamentDetail());
		assertEquals(testTournamentDTO.getGameList(), testTournament.getGameList());
		assertEquals(testTournamentDTO.getParticipantList(), testTournament.getParticipantList());
		assertEquals(testTournamentDTO.getTournamentName(), testTournament.getTournamentName());
		assertEquals(testTournamentDTO.getGameList(), testTournament.getGameList());
		assertEquals(testTournamentDTO.getMaxParticipantNumber(), testTournament.getMaxParticipantNumber());
		assertEquals(testTournamentDTO.getGameList(), testTournament.getGameList());
		assertEquals(testTournamentDTO.getType(), "KnockOutWithGroup");
		assertEquals(testTournamentDTO.getCurrentState(), testTournament.getCurrentState());
		assertEquals(testTournamentDTO.isOpen(), testTournament.isOpen());
		assertEquals(testTournamentDTO.getSpecifiedAttributes().get("currentGames"), ((KnockOutWithGroup) testTournament).getCurrentGames());
		assertEquals(testTournamentDTO.getSpecifiedAttributes().get("koMapping"), ((KnockOutWithGroup) testTournament).getKoMapping());
		assertEquals(testTournamentDTO.getSpecifiedAttributes().get("groupTable"), ((KnockOutWithGroup) testTournament).getTables());
	}
	
	@Test
	public void test_convertTournamentList() {
		int participantSize = 8;
		String tournamentName = "TestTournament";
		String organizerId = "idoforganizer";
		TournamentDetail td = new TournamentDetail(organizerId, TournamentTypes.PRIVATE, "chess"
				, "karlsruhe", new Date(), new Date(), new Date(), ParticipantForm.SINGLE);
		Tournament testTournament = new KnockOutWithGroup(participantSize, tournamentName, td);
		testTournament.createGames();
		TournamentDTO testTournamentDTO = TournamentConverter.convertToTournamentDTO(testTournament);
		
		List<TournamentDTO> tourneyDTOList = new ArrayList<TournamentDTO>();
		List<Tournament> tournament = new ArrayList<Tournament>();
		tournament.add(testTournament);
		tourneyDTOList.add(testTournamentDTO);
		assertEquals(TournamentConverter.convertToTournamentDTOList(tournament).get(0).getTournamentId(), testTournament.getTournamentId());
	}
}
