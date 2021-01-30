package sieger.repository;

import sieger.model.Invitation;

public interface InvitationRepository {
	boolean createInvitation(Invitation invitation);
	boolean retrieveInvitationById(String invitationId);
	boolean deleteInvitation(String invitationId);
}
