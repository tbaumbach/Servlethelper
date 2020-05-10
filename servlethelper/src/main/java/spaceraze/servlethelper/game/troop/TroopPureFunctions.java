package spaceraze.servlethelper.game.troop;

import spaceraze.world.*;

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
            for (BlackMarketOffer aBlackMarketOffer : aPlayer.getGalaxy().getBlackMarket().getCurrentOffers()) {
                if(aBlackMarketOffer.isTroop() && aBlackMarketOffer.getOfferedTroopType().getUniqueName().equals(troopType.getUniqueName())){
                    constructible = false;
                }
            }
        }
        return constructible;
    }
}
