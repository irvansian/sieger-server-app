package sieger.service;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Invitation;
import sieger.model.League;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.TournamentDetail;
import sieger.model.TournamentTypes;
import sieger.model.User;
import sieger.payload.InvitationDTO;
import sieger.repository.InvitationRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

class InvitationServiceTest {

    @InjectMocks
    private InvitationService invitationService;
 
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private TournamentRepository tournamentRepository;
    
    @Mock
    private TeamRepository teamRepository;
    
    @Mock
    private InvitationRepository invitationRepository;
    
 
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	void test_getInvitation_Empty() {
		when(invitationRepository.retrieveInvitationById("invitationID")).thenReturn(Optional.ofNullable(null));
		Executable exception =() ->invitationService.getInvitation("userID", "invitationID");
		Assertions.assertThrows(ResourceNotFoundException.class, exception);
		
	}
	
	@Test
	void test_getInvitation_Success_Single() {
		User user = new User("username","surname", "forename", "userID");
		Invitation invitation = new Invitation("senderId", "userID", "tournamentId", ParticipantForm.SINGLE);
		user.addInvitation(invitation.getInvitationId());
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		assertEquals(invitation, invitationService.getInvitation("userID", invitation.getInvitationId()).get());
	}
	
	@Test
	void test_getInvitation_NoPermission_Single() {
		User user = new User("username","surname", "forename", "userID");
		Invitation invitation = new Invitation("senderId", "recipientId", "tournamentId", ParticipantForm.SINGLE);
		user.addInvitation(invitation.getInvitationId());
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		Executable exception =() ->invitationService.getInvitation("userID", invitation.getInvitationId());
		Assertions.assertThrows(ForbiddenException.class, exception);
		
	}
	
	@Test
	void test_getInvitation_Success_Team() {
		Team team = new Team("userID", "name", "password");
		User user = new User("username","surname", "forename", "userID");
		user.addTeam(team.getTeamId());
		Invitation invitation = new Invitation("senderId", team.getTeamId(), "tournamentId", ParticipantForm.TEAM);
		team.addInvitation(invitation.getInvitationId());
		
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		
		assertEquals(invitation, invitationService.getInvitation("userID", invitation.getInvitationId()).get());
	}

	@Test
	void test_getInvitation_NoPermission_Team() {
		Team team = new Team("adminID", "name", "password");
		User user = new User("username","surname", "forename", "userID");
		user.addTeam(team.getTeamId());
		Invitation invitation = new Invitation("senderId", team.getTeamId(), "tournamentId", ParticipantForm.TEAM);
		team.addInvitation(invitation.getInvitationId());
		
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		Executable exception =() ->invitationService.getInvitation("userID", invitation.getInvitationId());
		Assertions.assertThrows(ForbiddenException.class, exception);
		
	}
	
	@Test
	void test_getInvitation_UserNotFound() {
		Invitation invitation = new Invitation("senderId", "userID", "tournamentId", ParticipantForm.SINGLE);
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(null));
		Executable exception =() ->invitationService.getInvitation("userID", invitation.getInvitationId());
		Assertions.assertThrows(ResourceNotFoundException.class, exception);
		
	}
	
	@Test
	void test_createInvitation_Single() {
		User user = new User("username","surname", "forename", "userID");
		User sender = new User("sendername","surname", "forename", "senderId");
		Invitation invitation = new Invitation("senderId", "userID", "tournamentId", ParticipantForm.SINGLE);
		ModelMapper mapper = new ModelMapper();
		InvitationDTO invDTO = mapper.map(invitation, InvitationDTO.class);
		invDTO.setSenderUsername("sendername");
		invDTO.setTournamentName("name");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(userRepository.updateUserById("userID", user)).thenReturn(true);
		when(userRepository.retrieveUserById("senderId")).thenReturn(Optional.ofNullable(sender));
		when(invitationRepository.createInvitation(invitation)).thenReturn(invitation);
		when(tournamentRepository.retrieveTournamentById("tournamentId")).thenReturn(Optional.ofNullable(tournament));
		assertEquals(invDTO.getInvitationId(), invitationService.createInvitation("userID", invitation).getInvitationId());
	}
	
	@Test
	void test_createInvitation_Team() {
		Team team = new Team("adminID", "name", "password");
		User sender = new User("sendername","surname", "forename", "senderId");
		Invitation invitation = new Invitation("senderId", team.getTeamId(), "tournamentId", ParticipantForm.TEAM);
		ModelMapper mapper = new ModelMapper();
		InvitationDTO invDTO = mapper.map(invitation, InvitationDTO.class);
		invDTO.setSenderUsername("sendername");
		invDTO.setTournamentName("name");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		when(userRepository.retrieveUserById("senderId")).thenReturn(Optional.ofNullable(sender));
		when(tournamentRepository.retrieveTournamentById("tournamentId")).thenReturn(Optional.ofNullable(tournament));
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(invitationRepository.createInvitation(invitation)).thenReturn(invitation);
		assertEquals(invDTO.getInvitationId(), invitationService.createInvitation("userID", invitation).getInvitationId());
	}
	
	@Test
	void test_acceptInvitation_Single() {
		User user = new User("username","surname", "forename", "userID");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		Invitation invitation = new Invitation("senderId", "userID", tournament.getTournamentId(), ParticipantForm.SINGLE);
		
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(userRepository.updateUserById("userID", user)).thenReturn(true);
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		when(invitationRepository.deleteInvitation(invitation.getInvitationId())).thenReturn(true);
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);

		assertEquals(invitationService.acceptInvitation("userID", invitation.getInvitationId()).getMessage(), "Successfully accepted the invitation");
		assertEquals(invitationService.acceptInvitation("userID", invitation.getInvitationId()).getSuccess(), true);
	}
	
	@Test
	void test_acceptInvitation_Team() {
		User user = new User("username","surname", "forename", "userID");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		League tournament = new League(4, "name", detail);
		Team team = new Team("userID", "name", "password");
		Invitation invitation = new Invitation("senderId", team.getTeamId(), tournament.getTournamentId(), ParticipantForm.TEAM);
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		when(invitationRepository.deleteInvitation(invitation.getInvitationId())).thenReturn(true);
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		when(tournamentRepository.updateTournament(tournament.getTournamentId(), tournament)).thenReturn(true);
		assertEquals(invitationService.acceptInvitation("userID", invitation.getInvitationId()).getMessage(), "Successfully accepted the invitation");
		assertEquals(invitationService.acceptInvitation("userID", invitation.getInvitationId()).getSuccess(), true);
	}
	
	@Test
	void test_declineInvitation_Single() {
		User user = new User("username","surname", "forename", "userID");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		Invitation invitation = new Invitation("senderId", "userID", tournament.getTournamentId(), ParticipantForm.SINGLE);
		
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(userRepository.updateUserById("userID", user)).thenReturn(true);
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		when(invitationRepository.deleteInvitation(invitation.getInvitationId())).thenReturn(true);
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		
		assertEquals(invitationService.declineInvitation("userID", invitation.getInvitationId()).getMessage(), "Successfully declined the invitation");
		assertEquals(invitationService.declineInvitation("userID", invitation.getInvitationId()).getSuccess(), true);
	}
	
	@Test
	void test_declineInvitation_Team() {
		User user = new User("username","surname", "forename", "userID");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.TEAM);
		League tournament = new League(4, "name", detail);
		Team team = new Team("userID", "name", "password");
		Invitation invitation = new Invitation("senderId", team.getTeamId(), tournament.getTournamentId(), ParticipantForm.TEAM);
		when(userRepository.retrieveUserById("userID")).thenReturn(Optional.ofNullable(user));
		when(teamRepository.retrieveTeamById(team.getTeamId())).thenReturn(Optional.ofNullable(team));
		when(teamRepository.updateTeam(team.getTeamId(), team)).thenReturn(true);
		when(invitationRepository.retrieveInvitationById(invitation.getInvitationId())).thenReturn(Optional.ofNullable(invitation));
		when(invitationRepository.deleteInvitation(invitation.getInvitationId())).thenReturn(true);
		when(tournamentRepository.retrieveTournamentById(tournament.getTournamentId())).thenReturn(Optional.ofNullable(tournament));
		
		assertEquals(invitationService.declineInvitation("userID", invitation.getInvitationId()).getMessage(), "Successfully declined the invitation");
		assertEquals(invitationService.declineInvitation("userID", invitation.getInvitationId()).getSuccess(), true);
	}
	
	@Test
	void test_convertToInvitationDTOList() {
		User sender = new User("sendername","surname", "forename", "senderId");
		TournamentDetail detail = new TournamentDetail("organisator", TournamentTypes.OPEN, "typeOfGame", "location", null,null,null,ParticipantForm.SINGLE);
		League tournament = new League(4, "name", detail);
		Invitation invitation = new Invitation("senderId", "recipientId", "tournamentId", ParticipantForm.SINGLE);
		List<Invitation> invitations = new ArrayList<>();
		invitations.add(invitation);
		ModelMapper mapper = new ModelMapper();
		InvitationDTO invDTO = mapper.map(invitation, InvitationDTO.class);
		List<InvitationDTO> invitationDTOs = new ArrayList<InvitationDTO>();
		invitationDTOs.add(invDTO);
		
		when(userRepository.retrieveUserById("senderId")).thenReturn(Optional.ofNullable(sender));
		when(tournamentRepository.retrieveTournamentById("tournamentId")).thenReturn(Optional.ofNullable(tournament));
		assertEquals(invitationService.convertToInvitationDTOList(invitations).get(0).getInvitationId(), invitationDTOs.get(0).getInvitationId());

	}
	
	@Test
	void test_getRecipientId_success() {
		User user = new User("username","surname", "forename", "userID");
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(user));
		assertEquals(invitationService.getRecipientId("username"), "userID");
	}
	@Test
	void test_getRecipientId_empty() {
		when(userRepository.retrieveUserByUsername("username")).thenReturn(Optional.ofNullable(null));
		Executable exception =() ->invitationService.getRecipientId("username");
		Assertions.assertThrows(ResourceNotFoundException.class, exception);
	}
	@Test
	void test_getRecipientTeamId_success() {
		Team team = new Team("userID", "name", "password");
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(team));
		assertEquals(invitationService.getRecipientTeamId("name"), team.getTeamId());
	}
	@Test
	void test_getRecipientTeamId_empty() {
		when(teamRepository.retrieveTeamByName("name")).thenReturn(Optional.ofNullable(null));
		Executable exception =() ->invitationService.getRecipientTeamId("name");
		Assertions.assertThrows(ResourceNotFoundException.class, exception);
	}
}
