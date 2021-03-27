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
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import sieger.model.Game;
import sieger.model.GameOutcome;
import sieger.model.KnockOut;
import sieger.model.KnockOutMapping;
import sieger.model.KnockOutWithGroup;
import sieger.model.League;
import sieger.model.LeagueTable;
import sieger.model.Participant;
import sieger.model.ParticipantForm;
import sieger.model.Result;
import sieger.model.ScoreResult;
import sieger.model.Tournament;
import sieger.model.TournamentDetail;
import sieger.model.TournamentState;
import sieger.model.User;
import sieger.model.WinLoseResult;
import sieger.model.Team;
import sieger.repository.TournamentRepository;
/**
 * The tournament database class. We use firebase as database.
 * The class implements the tournament repository.
 * 
 * @author Irvan Sian Syah Putra, Chen Zhang
 *
 */
@Repository("tournamentDB")
public class TournamentDatabase implements TournamentRepository {
	/**
	 * Path to the firebase document.
	 */
	private String path = "tournaments";
	/**
	 * Retrieve the tournament from firebase with id.
	 * 
	 * @param tournamentId The id of tournament.
	 * @return Return tournament optional after searching.
	 */
	@Override
	public Optional<Tournament> retrieveTournamentById(String tournamentId) {
		Tournament tournament = null;
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> future = db.collection(path)
				.document(tournamentId).get();
		try {
			
				if(future.get().get("type").equals("League")){
					tournament = future.get().toObject(League.class.asSubclass(Tournament.class));
					tournament.setType((String)future.get().get("type"));
				} else if(future.get().get("type").equals("KnockOut")) {
					tournament = convertToKnockOut(future.get());
				} else {
					tournament = convertToKnockOutWithGroup(future.get());
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


	/**
	 * Create a new tournament in firebase.
	 * 
	 * @param tournament The tournament object to be stored.
	 * @return Return true after put it in the firebase.
	 */
	@Override
	public boolean createTournament(Tournament tournament) {	
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> tournamentDoc = convertTournamentToMap(tournament);
		db.collection(path).document(tournament.getTournamentId()).set(tournamentDoc);
		return true;
	}
	/**
	 * Update the data of tournament in firebase.
	 * 
	 * @param tournamentId The id of tournament to be updated.
	 * @param tournament The tournament with data to be updated.
	 * @return Return true after updating.
	 */
	@Override
	public boolean updateTournament(String tournamentId, Tournament tournament) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> tournamentDoc = convertTournamentToMap(tournament);
		db.collection(path).document(tournamentId).update(tournamentDoc);
		return true;
	}
	/**
	 * Delete the tournament data in firebase.
	 * 
	 * @param tournamentId The id of touenament to be deleted.
	 * @return Return true after delete the data.
	 */
	@Override
	public boolean deleteTournament(String tournamentId) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection(path).document(tournamentId).delete();
		return true;
	}
	/**
	 * Retrieve the tournament with name.
	 * 
	 * @param tournamentName Name of tournament to be searched.
	 * @return Return the tournament optional after searching.
	 */
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
					tournament.setType((String)ds.get("type"));
				} else if(ds.get("type").equals("KnockOut")) {
					tournament = convertToKnockOut(ds);
				} else if(ds.get("type").equals("KnockOutWithGroup")) {
					tournament = convertToKnockOutWithGroup(ds);
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
	/**
	 * Retrieve the participant list of tournament.
	 * If participant form is single, return a list of user objects.
	 * If participant form is team, return a list of team objects.
	 * 
	 * @param tournamentId The id of tournament.
	 * @param pf The participant form of tournament.
	 * @return Return required participant list.
	 */
	@Override
	public List<Participant> retrieveTournamentParticipants(String tournamentId, ParticipantForm pf) {
		Firestore db = FirestoreClient.getFirestore();
		String childPath = null;
		if (pf.equals(ParticipantForm.SINGLE)) {
			childPath = "users";
		} else {
			childPath = "team";
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
	/**
	 * Private method to convert a tournament object to a firebase document.
	 * 
	 * @param tournament The tournament need to be stored in firebase.
	 * @return Return a document to set in firebase.
	 */
	private Map<String, Object> convertTournamentToMap(Tournament tournament) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Map<String, Object> tournamentDoc = new HashMap<>();
		try {
			boolean league = objectMapper.writeValueAsString(tournament).contains("League");
			
			boolean knockoutwithgroup= objectMapper.writeValueAsString(tournament).contains("WithGroup");
			boolean knockout = !knockoutwithgroup && !league;
			if(league) {
				tournamentDoc.put("type", "League");
				tournamentDoc.put("leagueTable", ((League)tournament).getLeagueTable());
			} else if(knockout) {
				tournamentDoc.put("type", "KnockOut");
				tournamentDoc.put("koMapping", (KnockOutMapping)((KnockOut)tournament).getKoMapping());
				tournamentDoc.put("currentGames", ((KnockOut)tournament).getCurrentGames());
				
			} else {
				tournamentDoc.put("type", "KnockOutWithGroup");
				tournamentDoc.put("tables", ((KnockOutWithGroup)tournament).getTables());
				tournamentDoc.put("koMapping", (KnockOutMapping)((KnockOutWithGroup)tournament).getKoMapping());
				tournamentDoc.put("currentGames", ((KnockOutWithGroup)tournament).getCurrentGames());
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tournamentDoc.put("currentState", tournament.getCurrentState());
		tournamentDoc.put("tournamentDetail", tournament.getTournamentDetail());
		tournamentDoc.put("gameList", tournament.getGameList());
		tournamentDoc.put("tournamentid", tournament.getTournamentId());
		tournamentDoc.put("participantList", tournament.getParticipantList());
		tournamentDoc.put("tournamentName", tournament.getTournamentName());
		tournamentDoc.put("maxParticipantNumber", tournament.getMaxParticipantNumber());
		return tournamentDoc;
	}
	/**
	 * Private method to convert a document to knock out tournament object.
	 * 
	 * @param ds The document snapshot of data in firebase.
	 * @return Return the knock out tournament object.
	 */
	@SuppressWarnings("unchecked")
	private KnockOut convertToKnockOut(DocumentSnapshot ds) {
		KnockOut tournament = new KnockOut();
		List<Game> currentgames = new ArrayList<>();
		if (ds.get("currentGames") != null) {
			for(Map<String,Object> map: (List<Map<String,Object>>)ds.get("currentGames")) {
				Game game = new Game();
				HashMap<String,Object> result = (HashMap<String, Object>) map.get("result");
				Result resultRes = null;
				if(result != null) {
					if(result.containsValue("Winlose")) {
						resultRes = concertToWinLoseResult(result);
					}
					if(result.containsValue("Score")) {
						resultRes = convertToScoreResult(result);

					}
					game.setResult(resultRes);
				}
				game.setTime(((Timestamp)map.get("time")).toDate());
				game.setFirstParticipantId((String)map.get("firstParticipantId"));
				game.setSecondParticipantId((String)map.get("secondParticipantId"));
				game.setGameId((String)map.get("gameId"));
			}
		}
		
		tournament.setCurrentGames(currentgames);
		tournament.setParticipantList((List<String>)ds.get("participantList"));
		tournament.setGameList((List<String>)ds.get("gameList"));
		tournament.setTournamentId(ds.getId());
		tournament.setTournamentName((String)ds.get("tournamentName"));
		tournament.setMaxParticipantNumber(Integer.parseInt(String.valueOf(ds.get("maxParticipantNumber"))));
		tournament.setKoMapping(ds.get("koMapping", KnockOutMapping.class));
		tournament.setCurrentState(ds.get("currentState", TournamentState.class));
		tournament.setTournamentDetail(ds.get("tournamentDetail", TournamentDetail.class));
		tournament.setType((String)ds.get("type"));
		return tournament;
	}
	/**
	 * Private method to convert a document to knock out with group tournament object.
	 * 
	 * @param ds The document snapshot of data in firebase.
	 * @return Return the knock out with group tournament object.
	 */
	@SuppressWarnings("unchecked")
	private KnockOutWithGroup convertToKnockOutWithGroup(DocumentSnapshot ds) {
		KnockOutWithGroup tournament = new KnockOutWithGroup();
		List<Game> currentgames = new ArrayList<>();
		if(ds.get("currentGames") != null) {
			for(Map<String,Object> map: (List<Map<String,Object>>)ds.get("currentGames")) {
				Game game = new Game();
				HashMap<String,Object> result = (HashMap<String, Object>) map.get("result");
				Result resultRes = null;
				if(result != null) {
					if(result.containsValue("Winlose")) {
						resultRes = concertToWinLoseResult(result);
					}
					if(result.containsValue("Score")) {
						resultRes = convertToScoreResult(result);
					}
					game.setResult(resultRes);
				}
				game.setTime(((Timestamp)map.get("time")).toDate());
				game.setFirstParticipantId((String)map.get("firstParticipantId"));
				game.setSecondParticipantId((String)map.get("secondParticipantId"));
				game.setGameId((String)map.get("gameId"));
			}
		}
		
		tournament.setCurrentGames(currentgames);
		tournament.setParticipantList((List<String>)ds.get("participantList"));
		tournament.setGameList((List<String>)ds.get("gameList"));
		tournament.setTournamentId(ds.getId());
		tournament.setTournamentName((String)ds.get("tournamentName"));
		tournament.setMaxParticipantNumber(Integer.parseInt(String.valueOf(ds.get("maxParticipantNumber"))));
		tournament.setKoMapping(ds.get("koMapping", KnockOutMapping.class));
		tournament.setCurrentState(ds.get("currentState", TournamentState.class));
		tournament.setTournamentDetail(ds.get("tournamentDetail", TournamentDetail.class));
		tournament.setType((String)ds.get("type"));
		tournament.setTables((Map<String, LeagueTable>)ds.get("tables"));
		return tournament;
	}
	private WinLoseResult concertToWinLoseResult(HashMap<String,Object> result) {
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
		return new WinLoseResult(first, second);
	}
	private ScoreResult convertToScoreResult(HashMap<String,Object> result) {
		int first = Integer.parseInt(String.valueOf(result.get("firstParticipantResult")));
		int second = Integer.parseInt(String.valueOf(result.get("secondParticipantResult")));
		return new ScoreResult(first,second);
	}
}
