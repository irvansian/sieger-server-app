package sieger.repository.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import sieger.model.Team;
import sieger.repository.TeamRepository;

@Repository("teamDB")
public class TeamDatabase implements TeamRepository {
	
	private String path = "team";

	@Override
	public Optional<Team> retrieveTeamById(String teamId) {
		Team team = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> future = db.collection(path)
				.document(teamId).get();
		
		try {
			if (future.get().exists()) {
				team = future.get().toObject(Team.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(team);
	}

	@Override
	public Optional<Team> retrieveTeamByName(String teamName) {
		Team team = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = db.collection(path)
				.whereEqualTo("teamName", teamName).get();
		try {
			for (DocumentSnapshot ds : future.get().getDocuments()) {
				team = ds.toObject(Team.class);
				break;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.ofNullable(team);
	}

	@Override
	public List<Team> retrieveMultipleTeamByIds(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createTeam(Team team) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> teamDoc = new HashMap<>();
		teamDoc.put("tournamentList", team.getTournamentList());
		teamDoc.put("adminId", team.getAdminId());
		teamDoc.put("teamName", team.getTeamName());
		teamDoc.put("teamPassword", team.getTeamPassword());
		teamDoc.put("teamId", team.getTeamId());
		teamDoc.put("memberList", team.getMemberList());
		teamDoc.put("invitationList", team.getInvitationList());
		db.collection(path).document(team.getTeamId()).set(teamDoc);
		return true;
	}

	@Override
	public boolean updateTeam(String teamId, Team team) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(teamId).set(team);
		return true;
	}

	@Override
	public boolean deleteTeam(String teamId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(teamId).delete();
		return true;
	}

}
