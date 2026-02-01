package spaceraze.servlethelper.game.troop;

import spaceraze.game.*;
import spaceraze.game.report.old.CanBeLostInSpace;
import spaceraze.game.report.old.Report;
import spaceraze.game.report.old.TurnInfo;
import spaceraze.map.GalaxyMap;
import spaceraze.servlethelper.game.UniqueIdHandler;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.game.vip.VipMutator;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.HighlightType;

import java.util.LinkedList;
import java.util.List;

public class TroopMutator {

    private TroopMutator(){}

    public static Troop createTroop(Player player, TroopType type, int vipTechBonus, int factionTechBonus, int buildingTechBonus, int worldProductionNumber, GameWorld gameWorld){
        PlayerTroopImprovement troopImprovement = player != null ?  PlayerPureFunctions.findTroopImprovement(type.getUuid(), player) : null;
        TroopType troopType = troopImprovement != null ? mutateTroopType(type, troopImprovement) : type;
        int productionNumber = troopImprovement != null ? troopImprovement.updateNrProduced() + 1 : worldProductionNumber;
        int totalTechBonus = 0;
        totalTechBonus += factionTechBonus;
        totalTechBonus += buildingTechBonus;
        totalTechBonus += vipTechBonus;
        Troop tmpTroop = new Troop(troopType, productionNumber, totalTechBonus);
        return tmpTroop;
    }

    public static TroopType mutateTroopType(TroopType originalType, PlayerTroopImprovement improvement){
        TroopType mutatedType = originalType.clone();
        mutatedType.setDamageCapacity(originalType.getDamageCapacity() + improvement.getDamageCapacity());
        mutatedType.setUpkeep(originalType.getUpkeep() + improvement.getCostSupport());
        mutatedType.setCostBuild(originalType.getCostBuild() + improvement.getCostBuild());
        mutatedType.setAttackInfantry(originalType.getAttackInfantry() + improvement.getAttackInfantry());
        mutatedType.setAttackArmored(originalType.getAttackArmored() + improvement.getAttackArmored());
        mutatedType.setAttackArtillery(originalType.getAttackArtillery() + improvement.getAttackArtillery());
        mutatedType.setSpaceshipTravel(improvement.isChangeSpaceshipTravel() ? improvement.isSpaceshipTravel() : originalType.isSpaceshipTravel());
        mutatedType.setCanBuild(improvement.isAvailableToBuild());
        mutatedType.setVisible(improvement.isChangeVisible() ? improvement.isVisible() : originalType.isVisible());
        return mutatedType;
    }

    public static Troop createTroopForSimulation(TroopType  troopType, int vipTechBonus, int factionTechBonus, int buildingTechBonus, int productionNumber){
        int totalTechBonus = 0;
        totalTechBonus += factionTechBonus;
        totalTechBonus += buildingTechBonus;
        totalTechBonus += vipTechBonus;
        Troop tmpTroop = new Troop(troopType, productionNumber, totalTechBonus);
        return tmpTroop;
    }


    public static Troop createTroop(TroopType type, Galaxy galaxy, GameWorld gameWorld){

        return createTroop(null, type, 0, 0, 0, UniqueIdHandler.getUniqueIdCounter(galaxy, CounterType.TROOP).getUniqueId(), gameWorld);
    }

    public static void addToLatestTroopsLostInSpace(Troop aTroop, TurnInfo turnInfo, GameWorld gameWorld) {
        Report report = turnInfo.getGeneralReports().get(turnInfo.getGeneralReports().size() - 1);
        report.getTroopsLostInSpace().add(CanBeLostInSpace.builder().lostInSpaceString(TroopPureFunctions.getTroopTypeByUuid(aTroop.getTypeUuid(), gameWorld).getName()).owner(aTroop.getOwner() != null ? aTroop.getOwner().getGovernorName() : null).build()); // TODO 2020-11-28 This should be replaced by EvenReport logic. So add the lost ships to the new specific created Report (for the typ of event) extending EvenReport. Try to reuse the EnemySpaceship and OwnSpaceship
    }

    public static void checkTroopsInDestroyedShips(Spaceship aShip, Player aPlayer, Galaxy galaxy, GalaxyMap galaxyMap, GameWorld gameWorld) {
        List<Troop> troopList = getTroopsOnShip(aShip, galaxy);
        for (Troop aTroop : troopList) {
            addToLatestTroopsLostInSpace(aTroop, aPlayer.getTurnInfo(), gameWorld);
            aTroop.getOwner().addToGeneral("Your troop " + aTroop.getName() + " has been killed when your ship "
                    + aShip.getName() + " was destroyed at " + PlanetPureFunctions.getPlanetName(galaxyMap, aShip.getLocation().getMapPlanetUuid()) + ".");
            aTroop.getOwner().addToHighlights(aTroop.getName(), HighlightType.TYPE_OWN_TROOP_DESTROYED);
            TroopMutator.removeTroop(aTroop, galaxy, galaxyMap, gameWorld);
        }
    }

    private static List<Troop> getTroopsOnShip(Spaceship sShip, Galaxy galaxy) {
        List<Troop> troopsAtPlanet = new LinkedList<Troop>();
        for (Troop aTroop : galaxy.getTroops()) {
            if (aTroop.getShipLocation() == sShip) {
                troopsAtPlanet.add(aTroop);
            }
        }
        return troopsAtPlanet;
    }

    public static void removeTroop(Troop aTroop, Galaxy galaxy, GalaxyMap galaxyMap, GameWorld gameWorld) {
        boolean ok;
        aTroop.setDestroyed();
        ok = galaxy.getTroops().remove(aTroop);
        if (aTroop.getOwner() != null) { // only players can have vips on troops
            VipMutator.checkVIPsInDestroyedTroop(aTroop, galaxy, galaxyMap, gameWorld);
        }
        if (!ok) {
            Logger.finer("Couldn't find troop to delete!!!");
        } // sp�rutskrift
    }

    public static String hit(Troop troop, int damage, boolean artillery, boolean defending, int resistance, GalaxyMap galaxyMap, GameWorld gameWorld, Galaxy galaxy){
        Logger.finer("hit: damage=" + damage + " art=" + artillery + " def=" + defending + " res=" + resistance);
        String returnString = "";
        double remainingDamage = damage;
        Logger.finer("art remainingDamage: " + remainingDamage);
        // add resistance effect
        if (defending){
            remainingDamage = remainingDamage * ((20.0 - resistance) / 20.0);
        }
        Logger.finer("res remainingDamage: " + remainingDamage);
        int actualDamage = (int)Math.round(remainingDamage);
        if (actualDamage < 1){
            actualDamage = 1;
        }
        Logger.finer("actualDamage: " + actualDamage);
        if (actualDamage >= troop.getCurrentDamageCapacity()){
            troop.setCurrentDamageCapacity(0);
            if (troop.getOwner() != null){
                VipMutator.checkVIPsInDestroyedTroop(troop, galaxy, galaxyMap, gameWorld);
            }
            returnString = "Troop destroyed";
        }else{
            Logger.finer("currentDC before: " + troop.getCurrentDamageCapacity());
            troop.setCurrentDamageCapacity(troop.getCurrentDamageCapacity() - actualDamage);
            Logger.finer("currentDC after: " + troop.getCurrentDamageCapacity());
            int troopStrength = TroopPureFunctions.getTroopStrength(troop);
            Logger.finer("troopStrength: " + troopStrength);
            returnString = "Troop damaged, strength: " + String.valueOf(troopStrength) + "%";
        }
        return returnString;
    }
    public static void addKill(Troop troop){
       troop.setKills(troop.getKills() + 1);
    }

    public static void performRepairs(Troop troop, double amountOfRepair, GalaxyMap galaxyMap) {
        troop.setCurrentDamageCapacity(Math.round(troop.getCurrentDamageCapacity() + (int)Math.round(amountOfRepair* troop.getDamageCapacity())));
        if (troop.getCurrentDamageCapacity() > troop.getDamageCapacity()){
            troop.setCurrentDamageCapacity(troop.getDamageCapacity());
        }
        if (troop.getOwner() != null) {
            if (troop.getCurrentDamageCapacity() == troop.getDamageCapacity()){
                troop.getOwner().addToGeneral("Your troop " + troop.getName() + " at "	+ TroopPureFunctions.getLocationString(troop, galaxyMap) + " has been repaired " + (int)Math.round(amountOfRepair*100) + "% to full damage capacity.");
            }else{
                troop.getOwner().addToGeneral("Your troop " + troop.getName() + " at "	+ TroopPureFunctions.getLocationString(troop, galaxyMap) + " has been repaired " + (int)Math.round(amountOfRepair*100) + "% to " + (int)Math.round((troop.getCurrentDamageCapacity() * 100)/ troop.getDamageCapacity())  + "% of full damage capacity.");
            }
        }
    }


}
