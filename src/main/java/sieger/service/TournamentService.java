package sieger.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sieger.exception.BadRequestException;
import sieger.exception.ForbiddenException;
import sieger.exception.ResourceNotFoundException;
import sieger.model.Game;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.GameRepository;
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
	
	@Autowired
	@Qualifier("gameDB")
	private GameRepository gameRepository;
	
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
		if (!tournament.isParticipant(user) && !tournament.isAdmin(user.getUserId())) {
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
		if (!tournament.isParticipant(user) && !tournament.isAdmin(user.getUserId())) {
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
	
	public Tournament createNewTournament(String currentUserId, Tournament tournament) {
		Optional<Tournament> tournamentOpt = 
				tournamentRepository.retrieveTournamentByName(tournament.getTournamentName());
		if (tournamentOpt.isPresent()) {
			ApiResponse response = new ApiResponse(false, "Tournament with the name " 
					+ tournamentOpt.get().getTournamentName() + " already exist.");
			throw new BadRequestException(response);
		}
		User user = userRepository.retrieveUserById(currentUserId).get();
		tournamentRepository.createTournament(tournament);
		userRepository.updateUserById(currentUserId, user);
		return tournament;
	}
	
	public Tournament updateTournamentDetailById(String currentUserId, 
			String tournamentName, TournamentDetail tournamentDetail) {
		
		return null;
	}
	
	public boolean updateTournamentById(String tournamentId, Tournament tournament) {
		return false;
	}
	
	public ApiResponse deleteTournament(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have permission "
					+ "to delete the tournament");
			throw new ForbiddenException(response);
		}
		removeTournamentIdFromParticipant(tournament.getParticipantList(), 
				tournament.getTournamentDetail().getParticipantForm(), tournament.getTournamentId());
		tournamentRepository.deleteTournament(tournament.getTournamentId());
		ApiResponse res = new ApiResponse(true, "Successfully deleted the tournament");
		return res;
	}
	
	public List<Game> getAllGame(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		List<Game> gameList = new ArrayList<Game>();
		for (String id : tournament.getGameList()) {
			gameList.add(gameRepository
					.retrieveGameById(tournament.getTournamentId(), id).get());
		}
		return gameList;
		
	}
	
	public Game getGameById(String currentUserId, 
			String tournamentName, String gameId) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		Optional<Game> gameOpt = gameRepository
				.retrieveGameById(tournament.getTournamentId(), gameId);
		if (gameOpt.isEmpty()) {
			throw new ResourceNotFoundException("Game", "id", gameId);
		}
		return gameOpt.get();
	}
	
	public Game updateGameById(String currentUserId, 
			String tournamentName, String gameId, Game game) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to update this game.");
			throw new ForbiddenException(response);
		}
		gameRepository.updateGame(tournament.getTournamentId(), gameId, game);
		return game;
	}
	
	public List<Game> createGames(String currentUserId, 
			String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to create games in <" + tournamentName + "> tournament.");
			throw new ForbiddenException(response);
		}
		List<Game> games = tournament.createGames();
		for (Game game : games) {
			gameRepository.createGame(tournament.getTournamentId(), game);
		}
		tournamentRepository.updateTournament(
				tournament.getTournamentId(), tournament);
		return games;
	}
	
	public ApiResponse deleteGame(String currentUserId, 
			String tournamentName, String gameId) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to delete a game in <" + tournamentName + "> tournament.");
			throw new ForbiddenException(response);
		}
		tournament.getGameList().remove(gameId);
		gameRepository.deleteGame(tournament.getTournamentId(), gameId);
		ApiResponse res = new ApiResponse(true, "Successfully delete the game");
		return res;
	}
	
	public ApiResponse joinTournament(String participantId, String tournamentName) {
		Tournament tournament = tournamentRepository
				.retrieveTournamentByName(tournamentName).orElseThrow(() -> 
				new ResourceNotFoundException("Tournament", "name", tournamentName));
		if(tournament.allowUserToJoin()) {
			User user = userRepository.retrieveUserById(participantId)
					.orElseThrow(() -> 
					new ResourceNotFoundException("User", "id", participantId));
			user.joinTournament(tournament);
			userRepository.updateUserById(participantId, user);
		} else if(tournament.allowTeamToJoin()) {
			Team team = teamRepository.retrieveTeamById(participantId)
					.orElseThrow(() -> 
					new ResourceNotFoundException("Team", "id", participantId));
			team.joinTournament(tournament);
			teamRepository.updateTeam(participantId, team);
		} else {
			ApiResponse res = new ApiResponse(false, "Failed to join tournament");
			throw new BadRequestException(res);
		}
		
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		ApiResponse res = new ApiResponse(true, "Successfully joined the tournament");
		return res;
		
	}
	
	public ApiResponse quitTournament(String participantId, String tournamentName) {
		Tournament tournament = getTournamentByName(participantId, tournamentName);
		
		if(tournament.allowUserToJoin()) {
			User user = userRepository.retrieveUserById(participantId)
					.orElseThrow(() -> 
					new ResourceNotFoundException("User", "id", participantId));
			user.quitTournament(tournament);
			userRepository.updateUserById(participantId, user);

		} else if(tournament.allowTeamToJoin()) {
			Team team = teamRepository.retrieveTeamById(participantId)
					.orElseThrow(() -> 
					new ResourceNotFoundException("Team", "id", participantId));
			team.quitTournament(tournament);
			teamRepository.updateTeam(participantId, team);
		} else {
			ApiResponse res = new ApiResponse(false, "Failed to quit tournament");
			throw new BadRequestException(res);
		}
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		ApiResponse res = new ApiResponse(true, "Successfully quit the tournament");
		return res;
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
