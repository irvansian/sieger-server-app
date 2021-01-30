package sieger.repository;

import java.util.List;
import java.util.Optional;

import sieger.model.Team;

public interface TeamRepository {
	Optional<Team> retrieveTeamById(String teamId);
	Optional<Team> retrieveTeamByName(String teamName);
	List<Team> retrieveMultipleTeamByIds(String[] ids);
	boolean createTeam(Team team);
	boolean updateTeam(String teamId, Team team);
	boolean deleteTeam(String teamId);
	
	
}
