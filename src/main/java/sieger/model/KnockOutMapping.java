package sieger.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.Objects;

public class KnockOutMapping {
	//map
	
	private Map<String, String> koMapping;
	//constructor
	public KnockOutMapping() {
		
	}
	public KnockOutMapping(int branchSize) {
		this.koMapping = new HashMap<String, String>();
		for(int i = 1; i <= branchSize; i++) {
			koMapping.put(String.valueOf(i), null);
		}
	}
	//getter
	public Map<String, String> getKoMapping(){
		return this.koMapping;
	}
	public void setKoMapping(Map<String, String> mapping) {
		this.koMapping = mapping;
	}
	//put game in map
	public void mapGameToKOBracket(int branchPosition, String game){
		koMapping.put(String.valueOf(branchPosition), game);
	}
	//get key from value
	public String getKeyByValue(String value) {
		for (Map.Entry<String, String> m :koMapping.entrySet())  {
			if(Objects.equal(value, m.getValue())) {
				return m.getKey();
			}
		}
		return null;
	}

}
