package spaceraze.servlethelper.game.spaceship;

import spaceraze.world.*;
import spaceraze.world.orders.ShipMovement;
import spaceraze.world.orders.ShipToCarrierMovement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//TODO 2020-04-18 Move to a clean serverside modul
public class SpaceshipPureFunctions {

    private SpaceshipPureFunctions(){}

    public static boolean isConstructable(Galaxy galaxy, Player player, SpaceshipType spaceshipType, PlayerSpaceshipImprovement playerSpaceshipImprovement){
        //	LoggingHandler.fine("isConstructible aPlayer: " + aPlayer.getName() + " SpaceType namn: " + name);
        //	LoggingHandler.fine("isWorldUnique isFactionUnique isPlayerUnique : " + isWorldUnique() + " " + isFactionUnique() + " " +isPlayerUnique());

        boolean isConstructable =  true;
        if((playerSpaceshipImprovement != null && !playerSpaceshipImprovement.isAvailableToBuild()) || (playerSpaceshipImprovement == null && !spaceshipType.isAvailableToBuild())){
            isConstructable = false;
        }else if((spaceshipType.isWorldUnique() && galaxy.spaceshipTypeExist(spaceshipType, null, null))
                || (spaceshipType.isFactionUnique() && galaxy.spaceshipTypeExist(spaceshipType, player.getFaction(), null))
                || (spaceshipType.isPlayerUnique() && galaxy.spaceshipTypeExist(spaceshipType, null, player))){
            isConstructable = false;
        }else if(spaceshipType.isWorldUnique() || spaceshipType.isFactionUnique() || spaceshipType.isPlayerUnique()){
            //TODO 2020-04-18 This is more client side, but need something to check if build orders is valid trying to save them from client to server.
            //	LoggingHandler.fine("isWorldUnique/isFactionUnique/isPlayerUnique check orders ");
            // check if a build order already exist
            if(player.getOrders().haveSpaceshipTypeBuildOrder(spaceshipType)){//On server side this should check if more then one order exist, id so the order is invalid.
                isConstructable = false;
            }
            for (BlackMarketOffer aBlackMarketOffer : player.getGalaxy().getCurrentOffers()) {
                if(aBlackMarketOffer.isShip() && aBlackMarketOffer.getSpaceshipType().getName().equals(spaceshipType.getName())){
                    isConstructable = false;
                }
            }
        }
        return isConstructable;
    }

    public static List<Spaceship> getPlayersSpaceshipsOnPlanet(Player aPlayer, Planet aPlanet, List<Spaceship> spaceships) {
        List<Spaceship> playersss = new ArrayList<Spaceship>();
        for (int i = 0; i < spaceships.size(); i++) {
            Spaceship tempss = spaceships.get(i);
            if ((tempss.getOwner() == aPlayer) & (tempss.getLocation() == aPlanet)) {
                playersss.add(tempss);
            }
        }
        return playersss;
    }

    /**
     * Returns all ships on planet with no move orders and all ships with orders against planet.
     *
     * @param aPlayer
     * @param aPlanet
     * @return
     */
    public static List<Spaceship> getShipAtPlanetNextTurn(Player aPlayer, Planet aPlanet) {
        List<Spaceship> tempShipList =SpaceshipPureFunctions.getPlayersSpaceshipsOnPlanet(aPlayer, aPlanet, aPlayer.getGalaxy().getSpaceships());
        List<ShipMovement> shipMovemants = aPlayer.getOrders().getShipMoves();
        for (ShipMovement shipMovement : shipMovemants) {
            if (shipMovement.getDestinationName().equalsIgnoreCase(aPlanet.getName())) {// adding ships with travel ordes against the planet.
                Spaceship tempShip = aPlayer.getGalaxy().findSpaceshipByUniqueId(shipMovement.getSpaceShipID());
                if (!tempShipList.contains(tempShip)) {
                    tempShipList.add(tempShip);
                }

                if (tempShip.isCarrier()) {
                    List<ShipToCarrierMovement> SqdToCarrierMovementList = aPlayer.getOrders().getShipToCarrierMoves();
                    for (ShipToCarrierMovement shipToCarrierMovement : SqdToCarrierMovementList) {
                        if (shipToCarrierMovement.getDestinationCarrierId() == tempShip.getUniqueId()) {
                            tempShipList.add(aPlayer.getGalaxy().findSpaceshipByUniqueId(shipToCarrierMovement.getSpaceshipId()));
                        }
                    }
                    List<Spaceship> shipListAtCarrierPlanet = getPlayersSpaceshipsOnPlanet(aPlayer, tempShip.getLocation(), aPlayer.getGalaxy().getSpaceships());
                    for (Spaceship spaceship : shipListAtCarrierPlanet) {
                        if (spaceship.getCarrierLocation() == tempShip) {
                            boolean add = true;
                            for (ShipMovement shipMove : shipMovemants) {
                                if (shipMove.getSpaceShipID().equals(spaceship.getUniqueId())) {
                                    add = false;
                                }
                            }
                            if (add) {
                                tempShipList.add(spaceship);
                            }
                        }
                    }
                }

            } else {
                Spaceship tempSpaceship = aPlayer.getGalaxy().findSpaceshipByUniqueId(shipMovement.getSpaceShipID());
                if (tempSpaceship.getLocation() != null && tempSpaceship.getLocation() == aPlanet) {// removing ships on the planet with move orders
                    tempShipList.remove(tempSpaceship);
                    if (tempSpaceship.isCarrier()) {
                        List<Spaceship> removeShips = new ArrayList<>();
                        for (Spaceship tempShip : tempShipList) {
                            if (tempShip.isSquadron()) {
                                List<ShipToCarrierMovement> SqdToCarrierMovementList = aPlayer.getOrders().getShipToCarrierMoves();
                                for (ShipToCarrierMovement shipToCarrierMovement : SqdToCarrierMovementList) {
                                    if (shipToCarrierMovement.getDestinationCarrierId() == tempSpaceship.getUniqueId()) {
                                        removeShips.add(tempShip);
                                    }
                                }
                                if (tempShip.getCarrierLocation() == tempSpaceship) {
                                    boolean add = true;
                                    for (ShipMovement shipMove : shipMovemants) {
                                        if (shipMove.getSpaceShipID().equals(tempShip.getUniqueId())) {
                                            add = false;
                                        }
                                    }
                                    if (add) {
                                        removeShips.add(tempShip);
                                    }


                                }
                            }
                        }
                        tempShipList.removeAll(removeShips);
                    }
                }
            }
        }
        return tempShipList;
    }

    /**
     *            if false, return ships destroyed
     */
    public static List<CanBeLostInSpace> getShipsLostInSpace(Galaxy galaxy, List<CanBeLostInSpace> allLostInSpace, String aFactionName, boolean lostShips) {
        List<CanBeLostInSpace> lisList = new LinkedList<>();
        for (Iterator<CanBeLostInSpace> iter = allLostInSpace.iterator(); iter.hasNext();) {
            CanBeLostInSpace aLis = iter.next();
            if (!lostShips) { // ta alla skepp som ej är från aFaction
                if (aLis.getOwner() != null) {
                    if (!galaxy.getPlayerByGovenorName(aLis.getOwner()).getFaction().getName().equalsIgnoreCase(aFactionName)) {
                        lisList.add(aLis);
                    }
                } else { // neutralt = lägg till
                    lisList.add(aLis);
                }
            } else { // ta endast skepp från aFaction
                if (aLis.getOwner() != null) {
                    if (galaxy.getPlayerByGovenorName(aLis.getOwner()).getFaction().getName().equalsIgnoreCase(aFactionName)) {
                        lisList.add(aLis);
                    }
                }
            }
        }
        return lisList;
    }
}
