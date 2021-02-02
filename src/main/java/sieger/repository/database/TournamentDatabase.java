package sieger.repository.database;

import java.util.List;
import java.util.Optional;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Tournament;
import sieger.repository.TournamentRepository;

public class TournamentDatabase implements TournamentRepository {
	private String path = "tournaments";

	@Override
	public Optional<Tournament> retrieveTournamentById(String tournamentId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTournament(String tournamentId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
