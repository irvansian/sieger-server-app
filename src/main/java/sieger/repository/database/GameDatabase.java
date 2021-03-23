package sieger.repository.database;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Game;
import sieger.model.GameOutcome;
import sieger.model.Result;
import sieger.model.ScoreResult;
import sieger.model.WinLoseResult;
import sieger.repository.GameRepository;
/**
 * The game database class. We use firebase as database.
 * The class implements the game repository.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@Repository("gameDB")
public class GameDatabase implements GameRepository {
	/**
	 * Path to the firebase document.
	 */
	private String path = "games";
	/**
	 * Retrieve the game from firebase with id.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param gameId The id of game.
	 * @return Return game optional after searching.
	 */
	@SuppressWarnings({"unchecked" })
	@Override
	public Optional<Game> retrieveGameById(String tournamentId, String gameId) {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> future = db.collection("tournaments")
				.document(tournamentId).collection(path).document(gameId).get();
		Game game = new Game();
		try {
			HashMap<String,Object> result = (HashMap<String, Object>) future.get().get("result");
			Result resultRes = null;
			if(result != null) {
				if(result.containsValue("Winlose")) {
					GameOutcome first = null;
					GameOutcome second = null;
					if(result.get("firstParticipantResult").equals(GameOutcome.WIN.toString())) {
						 first = GameOutcome.WIN;
					} else if (result.get("firstParticipantResult").equals(GameOutcome.LOSE.toString())) {
						 first = GameOutcome.LOSE;
					} else if (result.get("firstParticipantResult").equals(GameOutcome.DRAW.toString())) {
						 first = GameOutcome.DRAW;
					}
					if(result.get("secondParticipantResult").equals(GameOutcome.WIN.toString())) {
						 second = GameOutcome.WIN;
					} else if (result.get("secondParticipantResult").equals(GameOutcome.LOSE.toString())) {
						 second = GameOutcome.LOSE;
					} else if (result.get("secondParticipantResult").equals(GameOutcome.DRAW.toString())) {
						 second = GameOutcome.DRAW;
					}
					resultRes = new WinLoseResult(first, second);
				}
				if(result.containsValue("Score")) {
					int first = Integer.parseInt(String.valueOf(result.get("firstParticipantResult")));
					int second = Integer.parseInt(String.valueOf(result.get("secondParticipantResult")));
					resultRes = new ScoreResult(first,second);
				}
				game.setResult(resultRes);
			}
			game.setTime(((Timestamp)future.get().get("time")).toDate());
			game.setFirstParticipantId((String)future.get().get("firstParticipantId"));
			game.setSecondParticipantId((String)future.get().get("secondParticipantId"));
			game.setGameId((String)future.get().get("gameId"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(game);
	}
	/**
	 * Create a new game in firebase.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param game The team object to be stored.
	 * @return Return true after put it in the firebase.
	 */
	@Override
	public boolean createGame(String tournamentId, Game game) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> gameDoc = new HashMap<>();
		gameDoc.put("firstParticipantId", game.getFirstParticipantId());
		gameDoc.put("secondParticipantId", game.getSecondParticipantId());
		gameDoc.put("gameId", game.getGameId());
		gameDoc.put("time", game.getTime());
		
		db.collection("tournaments").document(tournamentId)
			.collection(path).document(game.getGameId()).set(gameDoc);
		return true;
	}
	/**
	 * Update the data of game in firebase.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param gameId The id of game to be updated.
	 * @param game The game with data to be updated.
	 * @return Return true after updating.
	 */
	@Override
	public boolean updateGame(String tournamentId, String gameId, Game game) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("tournaments").document(tournamentId)
			.collection(path).document(gameId).set(game);
		return true;
	}
	/**
	 * Delete the game data in firebase.
	 * 
	 * @param tournamentId The id of tournament which contains this game.
	 * @param gameId The id of game to be deleted.
	 * @return Return false after delete the data.
	 */
	@Override
	public boolean deleteGame(String tournamentId, String gameId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("tournaments").document(tournamentId)
			.collection(path).document(gameId).delete();
		return false;
	}
	
}
