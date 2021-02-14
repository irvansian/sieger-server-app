package sieger.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.Objects;

public class KnockOutMapping {
	//map
	@JsonIgnore
	private Map<Integer, String> koMapping;
	//constructor
	public KnockOutMapping(int branchSize) {
		this.koMapping = new HashMap<Integer, String>();
		for(int i = 1; i <= branchSize; i++) {
			koMapping.put(i, null);
		}
	}
	//getter
	public Map<Integer, String> getKoMapping(){
		return this.koMapping;
	}
	//put game in map
	public void mapGameToKOBracket(int branchPosition, String game){
		koMapping.put(branchPosition, game);
	}
	//get key from value
	public int getKeyByValue(String value) {
		for (Map.Entry<Integer, String> m :koMapping.entrySet())  {
			if(Objects.equal(value, m.getValue())) {
				return m.getKey();
			}
		}
		return -1;
	}

}
