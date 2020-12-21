package spaceraze.servlethelper.game.troop;

import spaceraze.util.general.Logger;
import spaceraze.world.*;

import java.util.LinkedList;
import java.util.List;

public class TroopPureFunctions {

    private TroopPureFunctions(){}

    public static boolean isConstructable(Player aPlayer, Galaxy galaxy, TroopType troopType, PlayerTroopImprovement playerTroopImprovement){
        boolean constructible =  true;
        if((playerTroopImprovement != null && !playerTroopImprovement.isAvailableToBuild()) || (playerTroopImprovement == null && troopType.isCanBuild())){
            constructible = false;
        }else if((troopType.isWorldUnique() && galaxy.troopTypeExist(troopType, null, null)) || (troopType.isFactionUnique() && galaxy.troopTypeExist(troopType, aPlayer.getFaction(), null)) || (troopType.isPlayerUnique() && galaxy.troopTypeExist(troopType, null, aPlayer))){
            constructible = false;
        }else if(troopType.isWorldUnique() || troopType.isFactionUnique() || troopType.isPlayerUnique()){
            // check if a build order already exist
            if(aPlayer.getOrders().haveTroopTypeBuildOrder(troopType)){
                constructible = false;
            }
            for (BlackMarketOffer aBlackMarketOffer : aPlayer.getGalaxy().getCurrentOffers()) {
                if(aBlackMarketOffer.isTroop() && aBlackMarketOffer.getTroopType().getUniqueName().equals(troopType.getUniqueName())){
                    constructible = false;
                }
            }
        }
        return constructible;
    }

    public static List<Troop> getPlayersTroopsOnPlanet(Player aPlayer, Planet aPlanet, List<Troop> troops) {
        List<Troop> playerst = new LinkedList<Troop>();
        Logger.finest("galaxy getPlayersTroopsOnPlanet: aPlanet: " + aPlanet.getName());
        for (Troop aTroop : troops) {
            if (aTroop.getOwner() != null) {
                // Logger.finest("aTroop.getOwner() + aPlayer: " + aTroop.getOwner().getName() +
                // " " + aPlayer.getName());

                if (aTroop.getOwner() == aPlayer) {
                    // Logger.finest("aTroop.getOwner() == aPlayer: True" );

                    if (aTroop.getPlanetLocation() != null && aTroop.getPlanetLocation() == aPlanet) {
                        // Logger.finest("aTroop.getPlanetLocation() + aPlanet: true");
                        playerst.add(aTroop);
                    } else {
                        if (aTroop.getShipLocation() != null) {
                            if (aTroop.getShipLocation().getLocation() != null) {
                                Logger.finest("aTroop.getShipLocation().getLocation() + aPlanet: "
                                        + aTroop.getShipLocation().getLocation().getName() + " " + aPlanet.getName());
                                if (aTroop.getShipLocation().getLocation() == aPlanet) {
                                    Logger.finest("aTroop.getShipLocation().getLocation() + aPlanet: true");
                                    playerst.add(aTroop); // this should also cover troops in retreating ships
                                }
                                // }else{
                                // Logger.finest("aTroop.getShipLocation().getLocation() = null. + aPlanet: " +
                                // aPlanet.getName());
                            }
                        }
                    }
                }
            }
        }
        return playerst;
    }

    public static int getNoTroopsAssignedToCarrier(Spaceship aCarrier, Player player, List<Troop> troops) {
        int count = 0;
        Player aPlayer = aCarrier.getOwner();
        List<Troop> troopsAtPlanet = getPlayersTroopsOnPlanet(player, aCarrier.getLocation(), troops);
        for (Troop aTroop : troopsAtPlanet) {
            if (aTroop.getShipLocation() == aCarrier) {
                // check if sstemp has a move order
                if (aPlayer != null) {
                    boolean moveOrder = aPlayer.checkTroopMove(aTroop);
                    // if not, inc counter
                    if (!moveOrder) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
