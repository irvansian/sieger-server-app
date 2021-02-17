package sieger.repository.database;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.KnockOut;
import sieger.model.KnockOutWithGroup;
import sieger.model.League;
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
				if(future.get().get("type").equals("League")){
					tournament = future.get().toObject(League.class.asSubclass(Tournament.class));
				}
				if(future.get().get("type").equals("KnockOut")) {
					tournament = future.get().toObject(KnockOut.class.asSubclass(Tournament.class));
				}
				if(future.get().get("type").equals("KnockOutWithGroup")) {
					tournament = future.get().toObject(KnockOutWithGroup.class.asSubclass(Tournament.class));
				}
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
		Map<String, Object> tournamentDoc = convertTournamentToMap(tournament);
		db.collection(path).document(tournament.getTournamentId()).set(tournamentDoc);
		return true;
	}

	@Override
	public boolean updateTournament(String tournamentId, Tournament tournament) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> tournamentDoc = convertTournamentToMap(tournament);
		db.collection(path).document(tournamentId).set(tournamentDoc);
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
				if(ds.get("type").equals("League")){
					tournament = ds.toObject(League.class.asSubclass(Tournament.class));
				}
				if(ds.get("type").equals("KnockOut")) {
					tournament = ds.toObject(KnockOut.class.asSubclass(Tournament.class));
				}
				if(ds.get("type").equals("KnockOutWithGroup")) {
					tournament = ds.toObject(KnockOutWithGroup.class.asSubclass(Tournament.class));
				}
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
	
	private Map<String, Object> convertTournamentToMap(Tournament tournament) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Map<String, Object> tournamentDoc = new HashMap<>();
		try {
			boolean league = objectMapper.writeValueAsString(tournament).contains("League");
			boolean knockout = objectMapper.writeValueAsString(tournament).contains("KnockOut");
			boolean knockoutwithgroup= objectMapper.writeValueAsString(tournament).contains("KnockOutWithGroup");
			if(league) {
				tournamentDoc.put("type", "League");
			} else if(knockout) {
				tournamentDoc.put("type", "KnockOut");
			} else if(knockoutwithgroup) {
				tournamentDoc.put("type", "KnockOutWithGroup");
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tournamentDoc.put("currentState", tournament.getCurrentState());
		tournamentDoc.put("tournamentDetail", tournament.getTournamentDetail());
		tournamentDoc.put("gameList", tournament.getGameList());
		tournamentDoc.put("tournamentid", tournament.getTournamentId());
		tournamentDoc.put("notificationList", tournament.getNotificationList());
		tournamentDoc.put("participantList", tournament.getParticipantList());
		tournamentDoc.put("tournamentName", tournament.getTournamentName());
		tournamentDoc.put("maxParticipantNumber", tournament.getMaxParticipantNumber());
		return tournamentDoc;
	}
	
	
}
