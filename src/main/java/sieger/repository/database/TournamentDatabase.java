package sieger.repository.database;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import sieger.model.Game;
import sieger.model.GameOutcome;
import sieger.model.KnockOut;
import sieger.model.KnockOutMapping;
import sieger.model.KnockOutWithGroup;
import sieger.model.League;
import sieger.model.LeagueTable;
import sieger.model.Notification;
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
			
				if(future.get().get("type").equals("League")){
					tournament = future.get().toObject(League.class.asSubclass(Tournament.class));
				}
				if(future.get().get("type").equals("KnockOut")) {
					tournament = convertToKnockOut(future.get());
				}
				if(future.get().get("type").equals("KnockOutWithGroup")) {
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
					tournament = convertToKnockOut(ds);
				}
				if(ds.get("type").equals("KnockOutWithGroup")) {
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
			
			boolean knockoutwithgroup= objectMapper.writeValueAsString(tournament).contains("WithGroup");
			boolean knockout = !knockoutwithgroup && !league;
			if(league) {
				tournamentDoc.put("type", "League");
				tournamentDoc.put("leagueTable", ((League)tournament).getLeagueTable());
			} else if(knockout) {
				tournamentDoc.put("type", "KnockOut");
				tournamentDoc.put("koMapping", ((KnockOut)tournament).getKoMapping());
				tournamentDoc.put("currentGames", ((KnockOut)tournament).getCurrentGames());
				
			} else if(knockoutwithgroup) {
				tournamentDoc.put("type", "KnockOutWithGroup");
				tournamentDoc.put("tables", ((KnockOutWithGroup)tournament).getTables());
				tournamentDoc.put("koMapping", ((KnockOutWithGroup)tournament).getKoMapping());
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
		tournamentDoc.put("notificationList", tournament.getNotificationList());
		tournamentDoc.put("participantList", tournament.getParticipantList());
		tournamentDoc.put("tournamentName", tournament.getTournamentName());
		tournamentDoc.put("maxParticipantNumber", tournament.getMaxParticipantNumber());
		return tournamentDoc;
	}
	@SuppressWarnings("unchecked")
	private KnockOut convertToKnockOut(DocumentSnapshot ds) {
		KnockOut tournament = new KnockOut();
		List<Game> currentgames = new ArrayList<>();
		for(Map<String,Object> map: (List<Map<String,Object>>)ds.get("currentGames")) {
			Game game = new Game();
			HashMap<String,Object> result = (HashMap<String, Object>) map.get("result");
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
			game.setTime(((Timestamp)map.get("time")).toDate());
			game.setFirstParticipantId((String)map.get("firstParticipantId"));
			game.setSecondParticipantId((String)map.get("secondParticipantId"));
			game.setGameId((String)map.get("gameId"));
		}
		tournament.setCurrentGames(currentgames);
		tournament.setParticipantList((List<String>)ds.get("participantList"));
		tournament.setGameList((List<String>)ds.get("gameList"));
		tournament.setNotificationList((List<Notification>)ds.get("notificationList"));
		tournament.setTournamentId(ds.getId());
		tournament.setTournamentName((String)ds.get("tournamentName"));
		tournament.setMaxParticipantNumber(Integer.parseInt(String.valueOf(ds.get("maxParticipantNumber"))));
		tournament.setKoMapping(ds.get("koMapping", KnockOutMapping.class));
		tournament.setCurrentState(ds.get("currentState", TournamentState.class));
		tournament.setTournamentDetail(ds.get("tournamentDetail", TournamentDetail.class));
		tournament.setType((String)ds.get("type"));
		return tournament;
	}
	
	@SuppressWarnings("unchecked")
	private KnockOutWithGroup convertToKnockOutWithGroup(DocumentSnapshot ds) {
		KnockOutWithGroup tournament = new KnockOutWithGroup();
		List<Game> currentgames = new ArrayList<>();
		for(Map<String,Object> map: (List<Map<String,Object>>)ds.get("currentGames")) {
			Game game = new Game();
			HashMap<String,Object> result = (HashMap<String, Object>) map.get("result");
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
			game.setTime(((Timestamp)map.get("time")).toDate());
			game.setFirstParticipantId((String)map.get("firstParticipantId"));
			game.setSecondParticipantId((String)map.get("secondParticipantId"));
			game.setGameId((String)map.get("gameId"));
		}
		tournament.setCurrentGames(currentgames);
		tournament.setParticipantList((List<String>)ds.get("participantList"));
		tournament.setGameList((List<String>)ds.get("gameList"));
		tournament.setNotificationList((List<Notification>)ds.get("notificationList"));
		tournament.setTournamentId(ds.getId());
		tournament.setTournamentName((String)ds.get("tournamentName"));
		tournament.setMaxParticipantNumber(Integer.parseInt(String.valueOf(ds.get("maxParticipantNumber"))));
		tournament.setKoMapping(ds.get("koMapping", KnockOutMapping.class));
		tournament.setCurrentState(ds.get("currentState", TournamentState.class));
		tournament.setTournamentDetail(ds.get("tournamentDetail", TournamentDetail.class));
		tournament.setType((String)ds.get("type"));
		tournament.setTables((List<LeagueTable>)ds.get("tables"));
		return tournament;
	}
}
