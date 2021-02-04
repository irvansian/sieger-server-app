package sieger.repository.database;

import java.util.List;
import java.util.Optional;

import sieger.model.Team;
import sieger.repository.TeamRepository;

public class TeamDatabase implements TeamRepository {

	@Override
	public Optional<Team> retrieveTeamById(String teamId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Team> retrieveTeamByName(String teamName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> retrieveMultipleTeamByIds(String[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createTeam(Team team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTeam(String teamId, Team team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTeam(String teamId) {
		// TODO Auto-generated method stub
		return false;
	}

}
