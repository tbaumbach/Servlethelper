package spaceraze.servlethelper.game;

import spaceraze.world.BlackMarketOffer;
import spaceraze.world.Galaxy;

public class BlackMarketPureFunctions {

    private BlackMarketPureFunctions(){}

    public static BlackMarketOffer findBlackMarketOffer(int aUniqueId, Galaxy galaxy){
        BlackMarketOffer found = null;
        int i = 0;
        while ((i < galaxy.getCurrentOffers().size()) & (found == null)){
            BlackMarketOffer tempOffer = galaxy.getCurrentOffers().get(i);
            if (tempOffer.getUniqueId() == aUniqueId){
                found = tempOffer;
            }else{
                i++;
            }
        }
        return found;
    }
}
