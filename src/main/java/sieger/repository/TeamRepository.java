package sieger.repository;

import java.util.List;
import java.util.Optional;

import sieger.model.Team;
/**
 * The team repository interface.The team database implements it.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
public interface TeamRepository {
	/**
	 * Retrieve the team from firebase with id.
	 * 
	 * @param teamId The id of team.
	 * @return Return team optional after searching.
	 */
	Optional<Team> retrieveTeamById(String teamId);
	/**
	 * Retrieve the team with name.
	 * 
	 * @param teamName Name of team to be searched.
	 * @return Return the team optional after searching.
	 */
	Optional<Team> retrieveTeamByName(String teamName);
	List<Team> retrieveMultipleTeamByIds(String[] ids);
	/**
	 * Create a new team in firebase.
	 * 
	 * @param team The team object to be stored.
	 * @return Return true after put it in the firebase.
	 */
	boolean createTeam(Team team);
	/**
	 * Update the data of team in firebase.
	 * 
	 * @param teamId The id of team to be updated.
	 * @param team The team with data to be updated.
	 * @return Return true after updating.
	 */
	boolean updateTeam(String teamId, Team team);
	/**
	 * Delete the team data in firebase.
	 * 
	 * @param teamId The id of team to be deleted.
	 * @return Return true after delete the data.
	 */
	boolean deleteTeam(String teamId);
	
	
}
