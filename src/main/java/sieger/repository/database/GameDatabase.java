package sieger.repository.database;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Game;
import sieger.model.Invitation;
import sieger.repository.GameRepository;

@Repository("gameDB")
public class GameDatabase implements GameRepository {
	
	private String path = "games";

	@Override
	public Optional<Game> retrieveGameById(String tournamentId, String gameId) {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference dr = db.collection("tournaments")
				.document(tournamentId).collection(path).document(gameId);
		Game game = null;
		try {
			game = dr.get().get().toObject(Game.class);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(game);
	}

	@Override
	public boolean createGame(String tournamentId, Game game) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("tournaments").document(tournamentId)
			.collection(path).document(game.getGameId()).set(game);
		return true;
	}

	@Override
	public boolean updateGame(String tournamentId, String gameId, Game game) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("tournaments").document(tournamentId)
			.collection(path).document(gameId).set(game);
		return true;
	}

	@Override
	public boolean deleteGame(String tournamentId, String gameId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("tournaments").document(tournamentId)
			.collection(path).document(gameId).delete();
		return false;
	}
	
}
