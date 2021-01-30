package sieger.service;

import java.util.List;
import java.util.Optional;

import sieger.model.Team;
import sieger.model.Tournament;
import sieger.model.User;
import sieger.repository.TeamRepository;

public class TeamService {
	private TeamRepository teamRepository;
	
	private UserService userService;
	
	private TournamentService tournamentService;

	public TeamService(TeamRepository teamRepository, UserService userService, TournamentService tournamentService) {
		this.teamRepository = teamRepository;
		this.userService = userService;
		this.tournamentService = tournamentService;
	}
	
	public Optional<Team> getTeamByName(String teamName) {
		return null;
	}
	
	public Optional<Team> getTeamById(String teamId) {
		return null;
	}
	
	public boolean createNewTeam(Team team) {
		return false;
	}
	
	public boolean deleteTeam(String teamId) {
		return false;
	}
	
	public List<User> getTeamMembers(String teamId) {
		return null;
	}
	
	public List<Tournament> getTeamTournaments(String teamId) {
		return null;
	}
	
	public boolean kickTeamMembers(String userId, String teamId) {
		return false;
	}
	
	public boolean updateTeamById(String teamId, Team team) {
		return false;
	}
	
}
