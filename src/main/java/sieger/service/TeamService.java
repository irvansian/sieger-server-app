package sieger.service;

import java.util.ArrayList;
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
		return teamRepository.retrieveTeamByName(teamName);
	}
	
	public Optional<Team> getTeamById(String teamId) {
		return teamRepository.retrieveTeamById(teamId);
	}
	
	public boolean createNewTeam(Team team) {
		return teamRepository.createTeam(team);
	}
	
	public boolean deleteTeam(String teamId) {
		return teamRepository.deleteTeam(teamId);
	}
	
	public List<User> getTeamMembers(String teamId) {
		List<User> teamMember = new ArrayList<>();
		Optional<Team> team = teamRepository.retrieveTeamById(teamId);
		if(!team.isEmpty()) {
			for(String memberId: team.get().getMemberList()) {
				teamMember.add(userService.getUserById(memberId).get());
			}
		}
		return teamMember;
	}
	
	public List<Tournament> getTeamTournaments(String teamId) {
		List<Tournament> teamTournament = new ArrayList<>();
		Optional<Team> team = teamRepository.retrieveTeamById(teamId);
		if(!team.isEmpty()) {
			for(String tournamentId: team.get().getTournamentList()) {
				teamTournament.add(tournamentService.getTournamentById(tournamentId).get());
			}
		}
		return teamTournament;
	}
	
	public boolean kickTeamMembers(String userId, String teamId) {
		Optional<Team> team = teamRepository.retrieveTeamById(teamId);
		User user = userService.getUserById(userId).get();
		if(!team.isEmpty()) {
			team.get().kickMember(user);
		}
		return userService.updateUserById(userId, user) && updateTeamById(teamId, team.get());
	}
	
	public boolean updateTeamById(String teamId, Team team) {
		Optional<Team> retrievedTeam = getTeamById(teamId);
		if (retrievedTeam.isEmpty()) return false;
		teamRepository.updateTeam(teamId, team);
		return true;
	}
	
	public boolean joinTeam(String userId, String teamName, String teamPassword) {
		return false;
	}
	
	public boolean quitTeam(String userId, String teamId) {
		return false;
	}
	
}
