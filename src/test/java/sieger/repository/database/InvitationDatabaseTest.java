package sieger.repository.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sieger.model.Invitation;
import sieger.model.ParticipantForm;
import sieger.repository.InvitationRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class InvitationDatabaseTest {
	@Autowired
	private InvitationRepository invitationRepository;
	
	
	@Test
	void test_getById() {
		Invitation result = invitationRepository.retrieveInvitationById("f6b73b14-6215-4cd2-b9e1-a658c81b4785").get();
		assertEquals(result.getParticipantForm(), ParticipantForm.SINGLE);
		assertEquals(result.getRecipientId(), "recipientId");
		assertEquals(result.getSenderId(), "senderId");
		assertEquals(result.getTournamentId(), "tournamentId");
	}
	
	@Test
	void test_getById_Null() {
		assertEquals(invitationRepository.retrieveInvitationById("null"), Optional.empty());
	}
	
	@Test
	void test_CreateAnddelete() {
		Invitation anotherInvitation = new Invitation("toBedeleted", "tebedeleted", "tebedeleted",ParticipantForm.TEAM);
		invitationRepository.createInvitation(anotherInvitation);
		assertTrue(invitationRepository.deleteInvitation(anotherInvitation.getInvitationId()));
	}
}
