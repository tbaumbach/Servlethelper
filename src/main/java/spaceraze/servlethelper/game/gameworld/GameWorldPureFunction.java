package spaceraze.servlethelper.game.gameworld;

import spaceraze.world.Faction;
import spaceraze.world.GameWorld;
import spaceraze.world.ResearchAdvantage;

import java.util.List;
import java.util.stream.Collectors;

public class GameWorldPureFunction {

    private GameWorldPureFunction(){}

    public static List<ResearchAdvantage> getParent(String uuid, Faction faction){
        return faction.getResearchAdvantages().stream().filter(researchAdvantage -> researchAdvantage.getChildren().contains(uuid)).collect(Collectors.toList());
    }

    public static List<ResearchAdvantage> getParent(String uuid, GameWorld gameWorld){
        return gameWorld.getFactions().stream().flatMap(faction -> faction.getResearchAdvantages().stream()).filter(researchAdvantage -> researchAdvantage.getChildren().contains(uuid)).collect(Collectors.toList());
    }

    public static ResearchAdvantage getResearchAdvantageByUuid(Faction faction, String uuid) {
        return faction.getResearchAdvantages().stream().filter(researchAdvantage -> researchAdvantage.getUuid().equalsIgnoreCase(uuid)).findFirst().orElseThrow();
    }
}
