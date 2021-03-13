package sieger.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import sieger.model.KnockOut;
import sieger.model.KnockOutWithGroup;
import sieger.model.League;
import sieger.model.Tournament;
import sieger.payload.TournamentDTO;

public class TournamentConverter {
	private static ModelMapper modelMapper;
	
	public static TournamentDTO convertToTournamentDTO(Tournament tournament) {
		TournamentDTO tourneyDTO = modelMapper.map(tournament, TournamentDTO.class);
		if (tournament instanceof KnockOutWithGroup) {
			tourneyDTO.getSpecifiedAttributes().put("groupTable", 
					((KnockOutWithGroup) tournament).getTables());
			tourneyDTO.getSpecifiedAttributes().put("currentGames", 
					((KnockOutWithGroup) tournament).getCurrentGames());
			tourneyDTO.getSpecifiedAttributes().put("koMapping", 
					((KnockOutWithGroup) tournament).getKoMapping());
		} else if (tournament instanceof KnockOut) {
			tourneyDTO.getSpecifiedAttributes().put("currentGames", 
					((KnockOut) tournament).getCurrentGames());
			tourneyDTO.getSpecifiedAttributes().put("koMapping", 
					((KnockOut) tournament).getKoMapping());
		} else if (tournament instanceof League){
			tourneyDTO.getSpecifiedAttributes().put("leagueTable", ((League) tournament).getLeagueTable());
		}
		return tourneyDTO;	
	}
	
	public static List<TournamentDTO> convertToTournamentDTOList(List<Tournament> listOfTournaments) {
		List<TournamentDTO> tourneyDTOList = new ArrayList<TournamentDTO>();
		
		for (Tournament tourney : listOfTournaments) {
			tourneyDTOList.add(convertToTournamentDTO(tourney));
		}
		return tourneyDTOList;
	}
}
