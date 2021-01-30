package sieger.model;

import java.util.List;
import java.util.UUID;

public class Tournament {
	private UUID id;
	private String name;
	private List<Participant> participants;
	
	public Tournament(String name) {
		this.name = name;
		this.id = UUID.randomUUID();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
