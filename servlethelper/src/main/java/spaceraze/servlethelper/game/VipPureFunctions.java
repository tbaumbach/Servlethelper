package spaceraze.servlethelper.game;

import spaceraze.world.*;

public class VipPureFunctions {

    private VipPureFunctions(){}

    public static boolean isConstructable(Player aPlayer, Galaxy galaxy, VIPType vipType){
        boolean constructible =  true;

        if((vipType.isWorldUnique() && galaxy.vipTypeExist(vipType, null, null)) || (vipType.isFactionUnique() && galaxy.vipTypeExist(vipType, aPlayer.getFaction(), null)) || (vipType.isPlayerUnique() && galaxy.vipTypeExist(vipType, null, aPlayer))){
            constructible = false;
        }else if(!aPlayer.getFaction().getAlignment().canHaveVip(vipType.getAlignment().getName())){
            constructible = false;
        }else if(vipType.isWorldUnique() || vipType.isFactionUnique() || vipType.isPlayerUnique()){
            // check if a build order already exist
            if(aPlayer.getOrders().haveVIPTypeBuildOrder(vipType)){
                constructible = false;
            }
            for (BlackMarketOffer aBlackMarketOffer : aPlayer.getGalaxy().getBlackMarket().getCurrentOffers()) {
                if(aBlackMarketOffer.isVIP() && aBlackMarketOffer.getOfferedVIPType().getName().equals(vipType.getName())){
                    constructible = false;
                }
            }
        }

        return constructible;
    }
}
