package sieger.model;

import java.util.HashMap;
import java.util.Map;

import com.google.api.client.util.Objects;
/**
 * The knock out mapping class.
 * 
 * @author Chen Zhang
 *
 */
public class KnockOutMapping {
	/**
	 * The knock out map which records the game id in ko round.
	 */
	private Map<String, String> koMapping;
	/**
	 * No-argument constructor.
	 */
	public KnockOutMapping() {
		
	}
	/**
	 * Constructor of map. The branch size is always the number of games in first round. 
	 * @param branchSize
	 */
	public KnockOutMapping(int branchSize) {
		this.koMapping = new HashMap<String, String>();
		for(int i = 1; i <= branchSize; i++) {
			koMapping.put(String.valueOf(i), null);
		}
	}
	/**
	 * Getter of the map.
	 * 
	 * @return Return the map.
	 */
	public Map<String, String> getKoMapping(){
		return this.koMapping;
	}
	/**
	 * Setter of the map.
	 * @param mapping New knock out map.
	 */
	public void setKoMapping(Map<String, String> mapping) {
		this.koMapping = mapping;
	}
	/**
	 * Put the game id in corrent place with the position.
	 * 
	 * @param branchPosition The position of the game id.
	 * @param game The id of game.
	 */
	public void mapGameToKOBracket(int branchPosition, String game){
		koMapping.put(String.valueOf(branchPosition), game);
	}
	/**
	 * Get the key with the given id.
	 * 
	 * @param value The id of the game.
	 * @return Return the key of given value.
	 */
	public String getKeyByValue(String value) {
		for (Map.Entry<String, String> m :koMapping.entrySet())  {
			if(Objects.equal(value, m.getValue())) {
				return m.getKey();
			}
		}
		return null;
	}

}
