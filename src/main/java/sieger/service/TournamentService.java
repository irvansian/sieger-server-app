package sieger.service;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.TournamentRepository;

@Service
public class TournamentService {
	private TournamentRepository tournamentRepository;
	private UserService userService;
	
	public TournamentService(TournamentRepository tournamentRepository, UserService userService) {
		this.tournamentRepository = tournamentRepository;
		this.userService = userService;
	}
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	
	public Optional<Tournament> getTournamentById(String currentUserId, 
			String tournamentId) {
		Optional<Tournament> tournamentOpt = tournamentRepository
				.retrieveTournamentById(tournamentId);
		if (tournamentOpt.isEmpty()) {
			throw new ResourceNotFoundException("Tournament", "id", tournamentId);
		}
		Optional<User> user = userService.getUserById(currentUserId);
		if (!tournamentOpt.get().isParticipant(user.get())) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to view the tournament.");
			throw new ForbiddenException(response);
		}
		return tournamentOpt;
	}
	
	
	public Optional<Tournament> getTournamentByName(String currentUserId, 
			String tournamentName) {
		Optional<Tournament> tournamentOpt = tournamentRepository
				.retrieveTournamentByName(tournamentName);
		if (tournamentOpt.isEmpty()) {
			throw new ResourceNotFoundException("Tournament", "name", tournamentName);
		}
		Optional<User> user = userService.getUserById(currentUserId);
		if (!tournamentOpt.get().isParticipant(user.get())) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to view the tournament");
			throw new ForbiddenException(response);
		}
		return tournamentOpt;
	}
	
	public List<Participant> getTournamentParticipants(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName).get();
		String tournamentId = tournament.getTournamentId();
		ParticipantForm pf = tournament.getTournamentDetail()
				.getParticipantForm();
		return tournamentRepository.retrieveTournamentParticipants(tournamentId, pf);
	}
	
	public boolean createNewTournament(Tournament tournament) {
		Optional<Tournament> tournamentOpt = 
				tournamentRepository.retrieveTournamentByName(tournament.getTournamentName());
		if (tournamentOpt.isPresent()) {
			ApiResponse response = new ApiResponse(false, "Tournament with the name " 
					+ tournamentOpt.get().getTournamentName() + " already exist.");
			throw new BadRequestException(response);
		}
		tournamentRepository.createTournament(tournament);
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
		Tournament tournament = getTournamentByName(currentUserId, tournamentName)
				.get();
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to delete the tournament");
			throw new ForbiddenException(response);
		}
		tournamentRepository.deleteTournament(tournament.getTournamentId());
		return false;
	}	
	
}
