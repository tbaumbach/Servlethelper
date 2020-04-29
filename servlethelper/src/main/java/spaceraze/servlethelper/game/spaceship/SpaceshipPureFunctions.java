package spaceraze.servlethelper.game.spaceship;


import spaceraze.world.*;

//TODO 2020-04-18 Move to a clean serverside modul
public class SpaceshipPureFunctions {

    private SpaceshipPureFunctions(){}

    public static boolean isConstructible(Galaxy galaxy, Player player, SpaceshipType spaceshipType, PlayerSpaceshipType playerSpaceshipType){
        //	LoggingHandler.fine("isConstructible aPlayer: " + aPlayer.getName() + " SpaceType namn: " + name);
        //	LoggingHandler.fine("isWorldUnique isFactionUnique isPlayerUnique : " + isWorldUnique() + " " + isFactionUnique() + " " +isPlayerUnique());

        boolean constructible =  true;
        if((playerSpaceshipType != null && !playerSpaceshipType.isAvailableToBuild()) || (playerSpaceshipType == null && spaceshipType.isAvailableToBuild())){
            constructible = false;
        }else if((spaceshipType.isWorldUnique() && galaxy.spaceshipTypeExist(spaceshipType, null, null))
                || (spaceshipType.isFactionUnique() && galaxy.spaceshipTypeExist(spaceshipType, player.getFaction(), null))
                || (spaceshipType.isPlayerUnique() && galaxy.spaceshipTypeExist(spaceshipType, null, player))){
            constructible = false;
        }else if(spaceshipType.isWorldUnique() || spaceshipType.isFactionUnique() || spaceshipType.isPlayerUnique()){
            //TODO 2020-04-18 This is more client side, but need something to check if build orders is valid trying to save them from client to server.
            //	LoggingHandler.fine("isWorldUnique/isFactionUnique/isPlayerUnique check orders ");
            // check if a build order already exist
            if(player.getOrders().haveSpaceshipTypeBuildOrder(spaceshipType)){//On server side this should check if more then one order exist, id so the order is invalid.
                constructible = false;
            }
            for (BlackMarketOffer aBlackMarketOffer : player.getGalaxy().getBlackMarket().getCurrentOffers()) {
                if(aBlackMarketOffer.isShip() && aBlackMarketOffer.getOfferedShiptype().getName().equals(spaceshipType.getName())){
                    constructible = false;
                }
            }
        }
        return constructible;
    }
}
