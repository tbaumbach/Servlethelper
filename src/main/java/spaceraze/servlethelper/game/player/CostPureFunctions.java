package spaceraze.servlethelper.game.player;

import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.world.*;

import java.util.List;

public class CostPureFunctions {

    public static int getPlayerUpkeepShips(Player aPlayer, List<Planet> planets, List<Spaceship> spaceships, GameWorld gameWorld) {
        int totUpkeepCost = 0;
        int totUpkeepIncome = 0;
        int totUpkeep;
        totUpkeepIncome = getPlayerFreeUpkeep(aPlayer, planets, gameWorld);
        totUpkeepCost = getPlayerUpkeepCost(aPlayer, spaceships);
        totUpkeep = totUpkeepCost - totUpkeepIncome;
        if (totUpkeep < 0) {
            totUpkeep = 0;
        }
        return totUpkeep;
    }

    private static int getPlayerFreeUpkeep(Player aPlayer, List<Planet> planets, GameWorld gameWorld) {
        int totUpkeepIncome = getPlayerFreeUpkeepWithoutCorruption(aPlayer, planets, gameWorld);
        totUpkeepIncome = IncomePureFunctions.getIncomeAfterCorruption(totUpkeepIncome, aPlayer.getCorruptionPoint());
        return totUpkeepIncome;
    }

    public static int getPlayerFreeUpkeepWithoutCorruption(Player aPlayer, List<Planet> planets, GameWorld gameWorld) {
        int totUpkeepIncome = 0;
        for (int i = 0; i < planets.size(); i++) {
            Planet tempPlanet = planets.get(i);
            if (tempPlanet.getPlayerInControl() == aPlayer) {
                totUpkeepIncome = totUpkeepIncome + IncomePureFunctions.getUpkeep(tempPlanet, gameWorld);
            }
        }
        return totUpkeepIncome;
    }

    public static int getPlayerUpkeepCost(Player aPlayer, List<Spaceship> spaceships) {
        int totUpkeepCost = 0;
        for (int j = 0; j < spaceships.size(); j++) {
            Spaceship tempss = spaceships.get(j);
            if (tempss.getOwner() == aPlayer) {
                totUpkeepCost = totUpkeepCost + tempss.getUpkeep();
            }
        }
        return totUpkeepCost;
    }

    public static int getPlayerUpkeepTroops(Player aPlayer, List<Planet> planets, List<Troop> troops) {
        int totUpkeepCost = 0;
        for (Planet aPlanet : planets) {
            int totTroopsCost = getTroopsUpKeepPlanet(aPlayer, aPlanet, troops);
            if (totTroopsCost > 0) {
                totUpkeepCost = totUpkeepCost + totTroopsCost;
            }
        }
        return totUpkeepCost;
    }

    public static int getTroopsUpKeepPlanet(Player aPlayer, Planet aPlanet, List<Troop> troops) {
        // get all players troops on this planet and compute support costs
        int upkeepOnPlanet = getTroopsCostPlanet(aPlayer, aPlanet, troops);
        // get free upkeep for this planet (if any)
        int freeTroopUpkeep = 0;
        if (aPlanet.getPlayerInControl() == aPlayer) {
            freeTroopUpkeep = aPlanet.getResistance();
        }
        // tot cost = cost - upkeep
        int totTroopsCost = upkeepOnPlanet - freeTroopUpkeep;
        return totTroopsCost;
    }

    public static int getTroopsCostPlanet(Player aPlayer, Planet aPlanet, List<Troop> troops) {
        List<Troop> troopsAtPlanet = TroopPureFunctions.getPlayersTroopsOnPlanet(aPlayer, aPlanet, troops);
        int upkeep = 0;
        for (Troop aTroop : troopsAtPlanet) {
            upkeep = upkeep + aTroop.getUpkeep();
        }
        return upkeep;
    }

    public static boolean isBroke(Player player, Galaxy galaxy){
        return (getPlayerUpkeepShips(player, galaxy.getPlanets(), galaxy.getSpaceships(), galaxy.getGameWorld())
                + getPlayerUpkeepTroops(player, galaxy.getPlanets(), galaxy.getTroops()))
                + getPlayerUpkeepVIPs(player, galaxy.getAllVIPs())> (player.getTreasury()
                + IncomePureFunctions.getPlayerIncome(player,false));
    }

    public static int getPlayerUpkeepVIPs(Player aPlayer, List<VIP> vips) {
        int totUpkeepCost = 0;

        for (VIP vip : vips) {
            if (vip.getBoss() == aPlayer) {
                totUpkeepCost = totUpkeepCost + vip.getUpkeep();
            }
        }

        return totUpkeepCost;
    }
}
