package spaceraze.servlethelper.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import spaceraze.util.general.Logger;
import spaceraze.world.Faction;
import spaceraze.world.Player;
import spaceraze.world.ResearchAdvantage;

public class ResearchPureFunctions implements Serializable {
	static final long serialVersionUID = 1L;
	
	//private List<ResearchAdvantage> advantages,onGoingResearchedAdvantages;
	private List<ResearchAdvantage> advantages;
	private HashMap<String,ResearchAdvantage> allAdvantage;
	private int numberOfSimultaneouslyResearchAdvantages = 1;
	
	private ResearchPureFunctions() {
	}

	public static List<ResearchAdvantage> getAllAdvantagesThatIsReadyToBeResearchOn(Player player, Faction faction) {
		
		List<ResearchAdvantage> tempAdvantages = new ArrayList<>();
		List<ResearchAdvantage> tempAllAdvantage = faction.getResearchAdvantages();
		Iterator<ResearchAdvantage> it = tempAllAdvantage.iterator();
		Logger.finer("tempAllAdvantage.size(): " + tempAllAdvantage.size());
		
		while(it.hasNext()){
			ResearchAdvantage tempResearchAdvantage = it.next();
			if(tempResearchAdvantage.isReadyToBeResearchedOn(player)){
				tempAdvantages.add(tempResearchAdvantage);
			}
		}

		List<ResearchAdvantage> sortedAdvantages = new ArrayList<>();
		sortedAdvantages.addAll(tempAdvantages.stream().filter(researchAdvantage -> !player.getResearchProgress(researchAdvantage.getName()).isDeveloped()).collect(Collectors.toList()));
		sortedAdvantages.addAll(tempAdvantages.stream().filter(researchAdvantage -> player.getResearchProgress(researchAdvantage.getName()).isDeveloped()).collect(Collectors.toList()));

		return sortedAdvantages;
	}

	public static ResearchAdvantage getAdvantage(Faction faction, String name) {
		return faction.getResearchAdvantages().stream().filter(researchAdvantage -> researchAdvantage.getName().equalsIgnoreCase(name)).findFirst().orElseThrow();
	}
}
