package sieger.repository.database;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Tournament;
import sieger.repository.TournamentRepository;

public class TournamentDatabase implements TournamentRepository {
	private String path = "tournaments";

	@Override
	public Optional<Tournament> retrieveTournamentById(String tournamentId) {
		Tournament tournament = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> future = db.collection(path)
				.document(tournamentId).get();
		
		try {
			if (future.get().exists()) {
				tournament = future.get().toObject(Tournament.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(tournament);
	}

	@Override
	public List<Tournament> retrieveMultipleTournamentsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createTournament(Tournament tournament) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(tournament.getTournamentId()).set(tournament);
		return true;
	}

	@Override
	public boolean updateTournament(String tournamentId, Tournament tournament) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(tournamentId).set(tournament);
		return true;
	}

	@Override
	public boolean deleteTournament(String tournamentId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
