package sieger.repository.database;

import java.util.ArrayList;
import java.util.List;


import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.model.Team;
import sieger.repository.TournamentRepository;

@Repository("tournamentDB")
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
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(tournamentId).delete();
		return true;
	}

	@Override
	public Optional<Tournament> retrieveTournamentByName(String tournamentName) {
		Tournament tournament = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = db.collection(path)
				.whereEqualTo("tournamentName", tournamentName).get();
		try {
			for (DocumentSnapshot ds : future.get().getDocuments()) {
				tournament = ds.toObject(Tournament.class);
				break;
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
	public List<Participant> retrieveTournamentParticipants(String tournamentId, ParticipantForm pf) {
		Firestore db = FirestoreClient.getFirestore();
		String childPath = null;
		if (pf.equals(ParticipantForm.SINGLE)) {
			childPath = "users";
		} else {
			childPath = "teams";
		}
		
		Query query = db.collection(childPath)
				.whereArrayContains("tournamentList", tournamentId);
		List<Participant> participants = new ArrayList<Participant>();
		try {
			for (DocumentSnapshot ds : query.get().get().getDocuments()) {
				if (pf.equals(ParticipantForm.SINGLE)) {
					participants.add(ds.toObject(User.class));
				} else {
					participants.add(ds.toObject(Team.class));
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return participants;
	}
	
	
}
