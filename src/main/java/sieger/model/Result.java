package sieger.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
	abstract boolean firstWins();
	abstract boolean secondWins();
	abstract boolean draws();
}
