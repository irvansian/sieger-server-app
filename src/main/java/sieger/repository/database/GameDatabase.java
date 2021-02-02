package sieger.repository.database;

import java.util.List;
import java.util.Optional;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Game;
import sieger.repository.GameRepository;

public class GameDatabase implements GameRepository {
	
	private String path = "games";

	@Override
	public Optional<Game> retrieveGameById(String tournmanetId, String gameId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> retrieveMultipleGamesById(String[] ids) {
		// TODO Auto-generated method stub
		return null;
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
	public boolean deleteGmae(String tournamentId, String gameId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
