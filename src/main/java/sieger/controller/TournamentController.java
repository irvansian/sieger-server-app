package sieger.controller;

import sieger.service.TournamentService;
import sieger.util.TournamentConverter;

import java.util.List;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.Game;
import sieger.model.Participant;
import sieger.model.Result;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.payload.ApiResponse;
import sieger.payload.TournamentDTO;
/**
 * The tournament controller class, which handles the request from client.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@RestController
@RequestMapping("tournaments")
public class TournamentController {
	/**
	 * The tournament service, which connects controller and repository.
	 */
	private TournamentService tournamentService;
	/**
	 * Constructor of tournament controller.
	 * 
	 * @param tournamentService The tournament service.
	 */
	@Autowired
	public TournamentController(TournamentService tournamentService) {
		this.tournamentService = tournamentService;
	}
	
	public List<Tournament> getTournamentsByKeyword(String keyword) {
		return null;
	}
	/**
	 * Get request from client.To get the tournament from database with id.
	 * 
	 * @param tournamentId The id of tournament.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200Ok response with tournament details.
	 */
	@GetMapping
	public ResponseEntity<TournamentDTO> getTournamentById(
			@RequestParam(name = "id") String tournamentId,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournament = tournamentService
				.getTournamentById(currentUserId, tournamentId);
		TournamentDTO tourneyDTO = TournamentConverter.convertToTournamentDTO(tournament);
		return ResponseEntity.ok(tourneyDTO);
	}
	/**
	 * Get request from client.To get tournament by its name.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200OK response with tournament detail.
	 */
	@GetMapping("/{tournamentName}")
	public ResponseEntity<Tournament> getTournamentByName(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournament = 
				tournamentService.getTournamentByName(currentUserId, tournamentName);
		return ResponseEntity.ok(tournament);
	}
	/**
	 * Get request from client.To get the participant list of a tournament.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200Ok response with participant list.
	 */
	@GetMapping("/{tournamentName}/participants")
	public ResponseEntity<List<Participant>> getTournamentParticipants(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Participant> participants = tournamentService
				.getTournamentParticipants(currentUserId, tournamentName);
		return ResponseEntity.ok(participants);
	}
	/**
	 * Post request from client. To store new tournament in database.
	 * 
	 * @param tournament The tournament as json object.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with tournament details.
	 */
	@PostMapping
	public ResponseEntity<Tournament> createNewTournament(
			@RequestBody Tournament tournament,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournamentReady = tournamentService.createNewTournament(currentUserId, 
				tournament);

		return ResponseEntity.ok(tournamentReady);
	}
	/**
	 * Put request from client.Update the tournamentdetail by id.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param tournamentDetail The detail of tournament with new data.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200OK response with new tournament detail.
	 */
	@PutMapping("/{tournamentName}")
	public ResponseEntity<Tournament> updateTournamentDetailById(
			@PathVariable("tournamentName") String tournamentName, 
			@RequestBody TournamentDetail tournamentDetail,
			@RequestAttribute("currentUserId") String currentUserId) {
		Tournament tournament = tournamentService.updateTournamentDetailById(currentUserId, 
				tournamentName, tournamentDetail);
		return ResponseEntity.ok(tournament);
	}
	/**
	 * Delete request from client.Delete the tournament with name.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200OK response with message.
	 */
	@DeleteMapping("/{tournamentName}")
	public ResponseEntity<ApiResponse> deleteTournament(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = tournamentService.deleteTournament(currentUserId, 
				tournamentName);
		return ResponseEntity.ok(res);
	}
	/**
	 * Post request from client.To join or quit the tournament.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param participation Activity of user,it should be whether join or quit.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200OK response with message.
	 */
	@PostMapping("/{tournamentName}")
	public ResponseEntity<ApiResponse> handleParticipation(
			@PathVariable("tournamentName") String tournamentName,
			@RequestBody Map<String, Boolean> participation,
			@RequestAttribute("currentUserId") String currentUserId) {
		boolean participationVal = participation.get("participation").booleanValue();
		ApiResponse res = null;
		if (participationVal == true) {
			 res = tournamentService.joinTournament(currentUserId, tournamentName);
		} else if (participationVal == false) {
			res = tournamentService.quitTournament(currentUserId, tournamentName);
		}
		return ResponseEntity.ok(res);
	}
	/**
	 * Get request from client.To get the game list of tournament.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200OK response with game list.
	 */
	@GetMapping("/{tournamentName}/games")
	public ResponseEntity<List<Game>> getTournamentGames(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Game> games = tournamentService.getAllGame(currentUserId, tournamentName);
		return ResponseEntity.ok(games);
	}
	/**
	 * Get request from client.To get game by id.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param gameId The id of game.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200Ok response with game.
	 */
	@GetMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<Game> getGameById(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId,
			@RequestAttribute("currentUserId") String currentUserId) {
		Game game = tournamentService.getGameById(currentUserId, 
				tournamentName, gameId);
		return ResponseEntity.ok(game);
	}
	/**
	 * Put request from client.Update the game result with id.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param gameId The id of game.
	 * @param result The result of this game.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return 200Ok response with the game and result.
	 */
	@PutMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<Game> updateGameById(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId, 
			@RequestBody Result result,
			@RequestAttribute("currentUserId") String currentUserId) {
		Game gameForRes = tournamentService.updateGameById(currentUserId, 
				tournamentName, gameId, result);
		return ResponseEntity.ok(gameForRes);
	}
	/**
	 * Post request from client.To create new games and stored in database.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 201 created response with all the new games.
	 */
	@PostMapping("/{tournamentName}/games")
	public ResponseEntity<List<Game>> createGames(
			@PathVariable("tournamentName") String tournamentName,
			@RequestAttribute("currentUserId") String currentUserId) {
		List<Game> createdGames = tournamentService.createGames(currentUserId, 
				tournamentName);
		return new ResponseEntity<List<Game>>
		(createdGames, null, HttpStatus.SC_CREATED);
	}
	/**
	 * Delete request from client.To delete the game by id.
	 * 
	 * @param tournamentName The name of tournament.
	 * @param gameId The id of game.
	 * @param currentUserId The id of current user.To check if current user has the permission.
	 * @return Return the 200OK response with message.
	 */
	@DeleteMapping("/{tournamentName}/games/{id}")
	public ResponseEntity<ApiResponse> deleteGame(
			@PathVariable("tournamentName") String tournamentName, 
			@PathVariable("id") String gameId,
			@RequestAttribute("currentUserId") String currentUserId) {
		ApiResponse res = tournamentService.deleteGame(currentUserId, 
				tournamentName, gameId);
		return ResponseEntity.ok(res);
	}
	
	
}
