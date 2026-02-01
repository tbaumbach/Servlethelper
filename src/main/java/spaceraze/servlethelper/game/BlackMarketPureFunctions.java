package spaceraze.servlethelper.game;

import spaceraze.game.BlackMarketBid;
import spaceraze.game.BlackMarketOffer;
import spaceraze.game.Galaxy;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.world.GameWorld;

public class BlackMarketPureFunctions {

    private BlackMarketPureFunctions(){}

    public static BlackMarketOffer findBlackMarketOffer(String uuid, Galaxy galaxy){
        BlackMarketOffer found = null;
        int i = 0;
        while ((i < galaxy.getCurrentOffers().size()) & (found == null)){
            BlackMarketOffer tempOffer = galaxy.getCurrentOffers().get(i);
            if (tempOffer.getUuid().equalsIgnoreCase(uuid)){
                found = tempOffer;
            }else{
                i++;
            }
        }
        return found;
    }

    public static String getDescription(BlackMarketOffer blackMarketOffer, GameWorld gameWorld){
        String returnString = "Hot stuff";
        if (blackMarketOffer.getVipTypeUuid() != null){
            returnString = VipPureFunctions.getVipTypeByUuid(blackMarketOffer.getVipTypeUuid(), gameWorld).getName();
        }else
        if (blackMarketOffer.getSpaceshipTypeUuid() != null){
            returnString = SpaceshipPureFunctions.getSpaceshipTypeByUuid(blackMarketOffer.getSpaceshipTypeUuid(), gameWorld).getName();
        }else
        if (blackMarketOffer.getTroopTypeUuid() != null){
            returnString = TroopPureFunctions.getTroopTypeByUuid(blackMarketOffer.getTroopTypeUuid(), gameWorld).getName();
        }else
        if (blackMarketOffer.getBlueprint() != null){
            returnString = "Blueprint: " + SpaceshipPureFunctions.getSpaceshipTypeByUuid(blackMarketOffer.getBlueprint(), gameWorld).getName();
        }
        return returnString;
    }

    public static String getBiddingText(BlackMarketOffer aOffer, BlackMarketBid bid, GameWorld gameWorld){
        return "You have made a bid for a " + BlackMarketPureFunctions.getDescription(aOffer, gameWorld) + " at the cost " + bid.getCost() + ".";
    }
}
