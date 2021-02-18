package sieger.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
 * The result interface. Implemented by scoreResult class and winloseResult.
 * 
 * @author Chen Zhang.
 *
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "Score", value = ScoreResult.class),
        @JsonSubTypes.Type(name = "Winlose", value = WinLoseResult.class),
})
public interface Result {
	/**
	 * Check if first participant wins.
	 * 
	 * @return Return true if first participant wins.
	 */
	abstract boolean firstWins();
	/**
	 * Check if second participant wins.
	 * 
	 * @return Return true if second participant wins.
	 */
	abstract boolean secondWins();
	/**
	 * Check if the game draws.
	 * 
	 * @return Return true if the game draws.
	 */
	abstract boolean draws();
}
