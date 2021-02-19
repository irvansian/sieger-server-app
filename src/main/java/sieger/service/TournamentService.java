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
import sieger.model.Result;
import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.User;
import sieger.payload.ApiResponse;
import sieger.repository.GameRepository;
import sieger.repository.TeamRepository;
import sieger.repository.TournamentRepository;
import sieger.repository.UserRepository;
/**
 * The Tournament servie class. 
 * The class will be called in controller and then called repository.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@Service
public class TournamentService {
	/**
	 * The tournament repository that connect to the database.
	 */
	@Autowired
	@Qualifier("tournamentDB")
	private TournamentRepository tournamentRepository;
	/**
	 * The user repository that connect to the database.
	 */
	@Autowired
	@Qualifier("userDB")
	private UserRepository userRepository;
	/**
	 * The team repository that connect to the database.
	 */
	@Autowired
	@Qualifier("teamDB")
	private TeamRepository teamRepository;
	/**
	 * The game repository that connect to the database.
	 */
	@Autowired
	@Qualifier("gameDB")
	private GameRepository gameRepository;
	/**
	 * Get tournament by keyword.
	 * 
	 * @param keyword The given keyword.
	 * @return Return the list of tournament.
	 */
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	/**
	 * Get tournament from database by id.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentId The id of tournament.
	 * @return Return the result as tournament object.
	 * @throws ResourceNotFoundException When resource not exists in database.
	 * @throws ForbiddenException When user has no permission.
	 */
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
	/**
	 * Get tournament from database by name.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName The name of tournament.
	 * @return Return the result as tournament object.
	 * @throws ResourceNotFoundException When tournament not exists in database.
	 * @throws ForbiddenException When user has no permission.
	 */
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
	/**
	 * Get the participant list of tournament.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName The name of tournament.
	 * @return Return the participant in list.
	 */
	public List<Participant> getTournamentParticipants(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		String tournamentId = tournament.getTournamentId();
		ParticipantForm pf = tournament.getTournamentDetail()
				.getParticipantForm();
		return tournamentRepository.retrieveTournamentParticipants(tournamentId, pf);
	}
	/**
	 * Create tournament and stored in database.
	 * 
	 * @param currentUserId The id of current user who wants to create new tournament.
	 * @param tournament The tournament passed from controller.
	 * @return Return the tournament object.
	 * @throws BadRequestException When tournament exists with the name.
	 */
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
		user.addTournament(tournament.getTournamentId());
		userRepository.updateUserById(currentUserId, user);
		Tournament res = tournamentRepository.retrieveTournamentByName(tournament.getTournamentName()).get();
		return res;
	}
	/**
	 * Update tournament detail by tournament id.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @param tournamentDetail The detail of tournament.
	 * @return Return the tournament object.
	 */
	public Tournament updateTournamentDetailById(String currentUserId, 
			String tournamentName, TournamentDetail tournamentDetail) {
		
		return null;
	}
	/**
	 * Delete the tournament by its name.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @return Return Api reponse when no permission or successfully deleted the tournament.
	 * @throws ForbiddenException When user has no permission.
	 */
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
	/**
	 * Get the game of tournament
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @return Return the list of game object.
	 */
	public List<Game> getAllGame(String currentUserId, String tournamentName) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		List<Game> gameList = new ArrayList<Game>();
		for (String id : tournament.getGameList()) {
			gameList.add(gameRepository
					.retrieveGameById(tournament.getTournamentId(), id).get());
		}
		return gameList;
		
	}
	/**
	 * Get the game by game id.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @param gameId The id of game.
	 * @return Return the result as game object.
	 * @throws ResourceNotFoundException When resource not exists in database.
	 */
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
	/**
	 * Update the game with its id.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @param gameId The id of game.
	 * @param result The result of the game.
	 * @return Return the game object after updating.
	 * @throws ForbiddenException When user has no permission.
	 */
	public Game updateGameById(String currentUserId, 
			String tournamentName, String gameId, Result result) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
	
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to update this game.");
			throw new ForbiddenException(response);
		}
		Game game = gameRepository.retrieveGameById(tournament.getTournamentId(), gameId).get();
		game.setResult(result);
	
		tournament.updateGame(game);
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		gameRepository.updateGame(tournament.getTournamentId(), gameId, game);
		return game;
	}
	/**
	 * Create games for the tournament.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @return Return the new created game in list.
	 * @throws ForbiddenException When user has no permission.
	 */
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
	/**
	 * Delete the game with its id.
	 * 
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @param tournamentName Name of tournament.
	 * @param gameId The id of game.
	 * @return Return the api response when successfully delete or no permission.
	 * @throws ForbiddenException When user has no permission.
	 */
	public ApiResponse deleteGame(String currentUserId, 
			String tournamentName, String gameId) {
		Tournament tournament = getTournamentByName(currentUserId, tournamentName);
		if (!tournament.isAdmin(currentUserId)) {
			ApiResponse response = new ApiResponse(false, "You don't have "
					+ "permission to delete a game in <" + tournamentName + "> tournament.");
			throw new ForbiddenException(response);
		}
		tournament.getGameList().remove(gameId);
		tournamentRepository.updateTournament(tournament.getTournamentId(), tournament);
		gameRepository.deleteGame(tournament.getTournamentId(), gameId);
		ApiResponse res = new ApiResponse(true, "Successfully delete the game");
		return res;
	}
	/**
	 * Join the tournament.
	 * 
	 * @param participantId The id of participant who wants to join the tournament.
	 * @param tournamentName The name of tournament.
	 * @return Return Apiresponse in different situation.
	 * @throws ResourceNotFoundException When resource not exists in database.
	 * @throws BadRequestException When failed to join.
	 */
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
	/**
	 * Quit the tournament.
	 * 
	 * @param participantId The id of participant who wants to quit the tournament.
	 * @param tournamentName The name of tournament.
	 * @return Return the api responce in different situation.
	 * @throws ResourceNotFoundException When resource not exists in database.
	 * @throws BadRequestException When failed to quit.
	 */
	public ApiResponse quitTournament(String participantId, String tournamentName) {
		Tournament tournament = getTournamentByName(participantId, tournamentName);
		
		if(tournament.getTournamentDetail().getParticipantForm() == ParticipantForm.SINGLE) {
			User user = userRepository.retrieveUserById(participantId)
					.orElseThrow(() -> 
					new ResourceNotFoundException("User", "id", participantId));
			user.quitTournament(tournament);
			userRepository.updateUserById(participantId, user);

		} else if(tournament.getTournamentDetail().getParticipantForm() == ParticipantForm.TEAM) {
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
	/**
	 * Private method to remove the tournament for every participant when it is deleted by admin.
	 * 
	 * @param participantList The list of participant.
	 * @param pc The participant form of tournament.
	 * @param tournamentId The id of tournament.
	 */
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
