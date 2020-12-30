package spaceraze.servlethelper.game.troop;

import spaceraze.servlethelper.game.UniqueIdHandler;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.game.vip.VipMutator;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.HighlightType;

import java.util.LinkedList;
import java.util.List;

public class TroopMutator {

    private TroopMutator(){}

    public static Troop createTroop(Player player, TroopType type, int vipTechBonus, int factionTechBonus, int buildingTechBonus, int uniqueId, GameWorld gameWorld){
        PlayerTroopImprovement troopImprovement = player != null ?  PlayerPureFunctions.findTroopImprovement(type.getName(), player) : null;
        TroopType troopType = troopImprovement != null ? new TroopType(type, troopImprovement) : type;
        int nrProduced = troopImprovement != null ? troopImprovement.updateNrProduced() : uniqueId;
        nrProduced++;
        int totalTechBonus = 0;
        totalTechBonus += factionTechBonus;
        totalTechBonus += buildingTechBonus;
        totalTechBonus += vipTechBonus;
        Troop tmpTroop = new Troop(troopType, nrProduced, totalTechBonus, uniqueId);
        return tmpTroop;
    }

    public static Troop createTroop(TroopType type, Galaxy galaxy){

        return createTroop(null, type, 0, 0, 0, UniqueIdHandler.getUniqueIdCounter(galaxy, CounterType.TROOP).getUniqueId(), galaxy.getGameWorld());
    }

    public static void addToLatestTroopsLostInSpace(Troop aTroop, TurnInfo turnInfo, GameWorld gameWorld) {
        Report report = turnInfo.getGeneralReports().get(turnInfo.getGeneralReports().size() - 1);
        report.getTroopsLostInSpace().add(CanBeLostInSpace.builder().lostInSpaceString(TroopPureFunctions.getTroopTypeByKey(aTroop.getTypeKey(), gameWorld).getName()).owner(aTroop.getOwner() != null ? aTroop.getOwner().getGovernorName() : null).build()); // TODO 2020-11-28 This should be replaced by EvenReport logic. So add the lost ships to the new specific created Report (for the typ of event) extending EvenReport. Try to reuse the EnemySpaceship and OwnSpaceship
    }

    public static void checkTroopsInDestroyedShips(Spaceship aShip, Player aPlayer, Galaxy galaxy) {
        List<Troop> troopList = getTroopsOnShip(aShip, galaxy);
        for (Troop aTroop : troopList) {
            addToLatestTroopsLostInSpace(aTroop, aPlayer.getTurnInfo(), galaxy.getGameWorld());
            aTroop.getOwner().addToGeneral("Your troop " + aTroop.getName() + " has been killed when your ship "
                    + aShip.getName() + " was destroyed at " + aShip.getLocation().getName() + ".");
            aTroop.getOwner().addToHighlights(aTroop.getName(), HighlightType.TYPE_OWN_TROOP_DESTROYED);
            TroopMutator.removeTroop(aTroop, galaxy);
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

    public static void removeTroop(Troop aTroop, Galaxy galaxy) {
        boolean ok;
        aTroop.setDestroyed();
        ok = galaxy.getTroops().remove(aTroop);
        if (aTroop.getOwner() != null) { // only players can have vips on troops
            VipMutator.checkVIPsInDestroyedTroop(aTroop, galaxy);
        }
        if (!ok) {
            Logger.finer("Couldn't find troop to delete!!!");
        } // sp�rutskrift
    }

    public static String hit(Troop troop, int damage, boolean artillery, boolean defending, int resistance){
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
                VipMutator.checkVIPsInDestroyedTroop(troop, troop.getOwner().getGalaxy());
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

    public static void performRepairs(Troop troop, double amountOfRepair) {
        troop.setCurrentDamageCapacity(Math.round(troop.getCurrentDamageCapacity() + (int)Math.round(amountOfRepair* troop.getDamageCapacity())));
        if (troop.getCurrentDamageCapacity() > troop.getDamageCapacity()){
            troop.setCurrentDamageCapacity(troop.getDamageCapacity());
        }
        if (troop.getOwner() != null) {
            if (troop.getCurrentDamageCapacity() == troop.getDamageCapacity()){
                troop.getOwner().addToGeneral("Your troop " + troop.getName() + " at "	+ TroopPureFunctions.getLocationString(troop) + " has been repaired " + (int)Math.round(amountOfRepair*100) + "% to full damage capacity.");
            }else{
                troop.getOwner().addToGeneral("Your troop " + troop.getName() + " at "	+ TroopPureFunctions.getLocationString(troop) + " has been repaired " + (int)Math.round(amountOfRepair*100) + "% to " + (int)Math.round((troop.getCurrentDamageCapacity() * 100)/ troop.getDamageCapacity())  + "% of full damage capacity.");
            }
        }
    }


}
