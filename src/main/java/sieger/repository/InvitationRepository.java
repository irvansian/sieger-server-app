package sieger.repository;

import java.util.Optional;

import sieger.model.Invitation;

public interface InvitationRepository {
	Invitation createInvitation(Invitation invitation);
	Optional<Invitation> retrieveInvitationById(String invitationId);
	boolean deleteInvitation(String invitationId);
}
