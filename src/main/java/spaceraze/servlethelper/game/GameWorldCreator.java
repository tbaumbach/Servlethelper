package spaceraze.servlethelper.game;

import spaceraze.world.Faction;
import spaceraze.world.GameWorld;
import spaceraze.world.diplomacy.DiplomacyRelation;
import spaceraze.world.diplomacy.GameWorldDiplomacyRelation;

public class GameWorldCreator {

    private GameWorldCreator(){}

    public static void addFaction(Faction faction, GameWorld gameWorld){
        faction.setGameWorld(gameWorld);
        gameWorld.getFactions().add(faction);
        createDiplomacyRelations(faction, gameWorld);
    }

    private static void createDiplomacyRelations(Faction aFaction, GameWorld gameWorld){
        for (Faction tmpFaction : gameWorld.getFactions()) {
            addDefaultRelation(aFaction, tmpFaction, gameWorld);
        }
    }

    private static void addDefaultRelation(Faction aFaction1, Faction aFaction2, GameWorld gameWorld){
        GameWorldDiplomacyRelation tmpDR = new GameWorldDiplomacyRelation(aFaction1.getUuid(),aFaction2.getUuid());
        tmpDR.setGameWorld(gameWorld);
        gameWorld.getGameWorldDiplomacyRelations().add(tmpDR);
    }

    public static DiplomacyRelation getRelation(Faction aFaction1, Faction aFaction2, GameWorld gameWorld){
        DiplomacyRelation foundDR = null;
        int counter = 0;
        while ((counter < gameWorld.getGameWorldDiplomacyRelations().size()) & (foundDR == null)) {
            DiplomacyRelation tmpDR = gameWorld.getGameWorldDiplomacyRelations().get(counter);
            if (tmpDR.isRelation(aFaction1,aFaction2)){
                foundDR = tmpDR;
            }else{
                counter++;
            }
        }
        return foundDR;
    }

}
