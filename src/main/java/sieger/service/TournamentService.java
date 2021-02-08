package sieger.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;

@Service
public class TournamentService {
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	
	public Tournament getTournamentById(String currentUserId, 
			String tournamentId) {
		Tournament tournament = tournamentRepository
				.retrieveTournamentById(tournamentId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Tournament", "id", tournamentId));
		User user = userRepository.retrieveUserById(currentUserId).get();
		if (!tournament.isParticipant(user)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to view the tournament");
			throw new ForbiddenException(response);
		}
		return tournament;
	}
	
	
	public Tournament getTournamentByName(String currentUserId, 
			String tournamentName) {
		Tournament tournament = tournamentRepository
				.retrieveTournamentByName(tournamentName)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Tournament", "name", tournamentName));
		User user = userRepository.retrieveUserById(currentUserId).get();
		if (!tournament.isParticipant(user)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to view the tournament");
			throw new ForbiddenException(response);
		}
		return tournament;
	}
	
	public List<Participant> getTournamentParticipants(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		String tournamentId = tournament.getTournamentId();
		ParticipantForm pf = tournament.getTournamentDetail()
				.getParticipantForm();
		return tournamentRepository.retrieveTournamentParticipants(tournamentId, pf);
	}
	
	public boolean createNewTournament(String currentUserId, Tournament tournament) {
		Optional<Tournament> tournamentOpt = 
				tournamentRepository.retrieveTournamentByName(tournament.getTournamentName());
		if (tournamentOpt.isPresent()) {
			ApiResponse response = new ApiResponse(false, "Tournament with the name " 
					+ tournamentOpt.get().getTournamentName() + " already exist.");
			throw new BadRequestException(response);
		}
		User user = userRepository.retrieveUserById(currentUserId).get();
		user.addTournament(tournamentOpt.get().getTournamentId());
		tournamentRepository.createTournament(tournament);
		userRepository.updateUserById(currentUserId, user);
		return true;
	}
	
	public boolean updateTournamentDetailById(String currentUserId, 
			String tournamentName, TournamentDetail tournamentDetail) {
		
		return false;
	}
	
	public boolean updateTournamentById(String tournamentId, Tournament tournament) {
		return false;
	}
	
	public boolean deleteTournament(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to delete the tournament");
			throw new ForbiddenException(response);
		}
		removeTournamentIdFromParticipant(tournament.getParticipantList(), 
				tournament.getTournamentDetail().getParticipantForm(), tournament.getTournamentId());
		tournamentRepository.deleteTournament(tournament.getTournamentId());
		return false;
	}
	
	private void removeTournamentIdFromParticipant(List<String> participantList, 
			ParticipantForm pc, String tournamentId) {
		if (pc.equals(ParticipantForm.SINGLE)) {
			for (String userId : participantList) {
				User user = userRepository.retrieveUserById(userId).get();
				user.getTournamentList().remove(tournamentId);
				userRepository.updateUserById(userId, user);
			}
		} else {
			for (String teamId : participantList) {
				Team team = teamRepository.retrieveTeamById(teamId).get();
				team.getTournamentList().remove(tournamentId);
				teamRepository.updateTeam(teamId, team);
			}
		}
	}
	
}
