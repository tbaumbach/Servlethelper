package spaceraze.servlethelper.map;

import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.VipPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.comparator.SpaceshipSizeAndBuildCostComparator;
import spaceraze.world.mapinfo.MapConnectionInfo;
import spaceraze.world.mapinfo.MapInfoTurn;
import spaceraze.world.mapinfo.MapInfos;
import spaceraze.world.mapinfo.MapPlanetInfo;

import java.util.*;

public class MapPureFunctions {

    public static List<String> getShipsList(Planet planet, Player player, Galaxy g){
        // list to return
        List<String> shipStrings = new LinkedList<>();
        // get spaceships on this planet, both civ and military
        List<Spaceship> shipsOnPlanet = g.getShips(planet,false);
        shipsOnPlanet.addAll(g.getShips(planet,true));
        List<Spaceship> shipsPlayer = new LinkedList<>();
        for (Spaceship spaceship : shipsOnPlanet) {
            if (spaceship.getOwner() == player){
                shipsPlayer.add(spaceship);
            }
        }
        // sort the ships
        Collections.sort(shipsPlayer,new SpaceshipSizeAndBuildCostComparator());
        int shipCount = 0;
        int i = 0;
        boolean addShip = false; // anv. för att avgöra när alla skepp av en typ har hittats (så att räknaren blir rätt)
        for (Spaceship spaceship : shipsPlayer) {
            shipCount++;
            List<VIP> tmpVips = VipPureFunctions.findAllVIPsOnShip(spaceship, g.getAllVIPs());
            if (i == (shipsPlayer.size() - 1)){
                addShip = true;
            }else{
                Spaceship spaceshipNext = shipsPlayer.get(i+1);
                if (!spaceshipNext.getSpaceshipType().getName().equals(spaceship.getSpaceshipType().getName())){
                    addShip = true;
                }
            }
            if (addShip){
                String tmpShipStr = spaceship.getSpaceshipType().getShortName();
                if (shipCount > 1){
                    tmpShipStr = shipCount + " " + tmpShipStr;
                }
                if (tmpVips.size() > 0){
                    tmpShipStr = tmpShipStr + " (";
                }
                if (tmpVips.size() > 0){
                    for (Iterator<VIP> iter = tmpVips.iterator(); iter.hasNext();) {
                        VIP aVIP = iter.next();
                        tmpShipStr = tmpShipStr + aVIP.getShortName();
                        if (iter.hasNext()){
                            tmpShipStr = tmpShipStr + ",";
                        }
                    }
                }
                if (tmpVips.size() > 0){
                    tmpShipStr = tmpShipStr + ")";
                }
                shipStrings.add(tmpShipStr);
                shipCount = 0;
            }
            i++;
        }
        return shipStrings;
    }

    public static MapInfoTurn createMapInfoTurn(Player player, MapInfos mapPlanetInfos, int turn){
        List<MapPlanetInfo> allPlanetInfos = new ArrayList<>();
        List<MapConnectionInfo> starPortConnections = new ArrayList<>();

        for (Planet planet : player.getGalaxy().getPlanets()) {
            MapPlanetInfo mapPlanetInfo = new MapPlanetInfo(planet, player, mapPlanetInfos, turn, getShipsList(planet, player, player.getGalaxy()));
            allPlanetInfos.add(mapPlanetInfo);
        }
        for (PlanetConnection aPlanetConnection : player.getGalaxy().getPlanetConnections()) {
            if (MapPureFunctions.isStarPortConnections(aPlanetConnection,player)){
                MapConnectionInfo mapConnectionInfo = new MapConnectionInfo(aPlanetConnection);
                starPortConnections.add(mapConnectionInfo);
            }
        }

        return MapInfoTurn.builder().allPlanetInfos(allPlanetInfos).starPortConnections(starPortConnections).build();
    }

    public static boolean isStarPortConnections(PlanetConnection planetConnection, Player player){
        boolean haveStarPort = false;
        Galaxy g = player.getGalaxy();
        Planet p1 = g.getPlanet(planetConnection.getPlanetOne().getName());
        Planet p2 = g.getPlanet(planetConnection.getPlanetTwo().getName());
        // check if starport make this connection short range
        if ((p1.getPlayerInControl() != null) & (p2.getPlayerInControl() != null)){ // none of the planets are neutral
            if ((p1.hasSpacePort()) & (p2.hasSpacePort())){ // both have a spacestation
                if (DiplomacyPureFunctions.friendlySpaceports(p1.getPlayerInControl(),p2.getPlayerInControl(), g.getDiplomacyStates())){
                    if (DiplomacyPureFunctions.friendlySpaceports(player,p1.getPlayerInControl(), g.getDiplomacyStates())){
                        if (DiplomacyPureFunctions.friendlySpaceports(player,p2.getPlayerInControl(), g.getDiplomacyStates())){
                            haveStarPort = true;
                        }
                    }
                }
            }
        }
        return haveStarPort;
    }

    public static List<String> getAllDestinationsStrings(Planet location, boolean longRange, Player aPlayer,
                                                  boolean ownPlanetsOnly, Galaxy galaxy) {
        Logger.finer("getAllDestinationsStrings, location: " + location + " longRange: " + longRange);
        // first find if there is a spaceport on the planet
        boolean hasSpacePort = false;
        hasSpacePort = location.hasSpacePort();
        List<String> alldest = new LinkedList<String>();
        for (PlanetConnection planetConnection : galaxy.getPlanetConnections()) {
            Logger.finer("PC: " + planetConnection);
            // first check if this connection includes location at all
            BasePlanet otherEnd = planetConnection.getOtherEnd(location, true);

            if (otherEnd != null) {
                Planet planetOtherEnd = galaxy.getPlanet(otherEnd.getName());
                Logger.finer("PlanetOtherEnd: " + planetOtherEnd.getName());
                boolean bothHaveValidSpacePorts = false;
                if (hasSpacePort) {
                    Logger.finer(" hasSpacePort");
                    // check if the other planet belongs to the same faction
                    boolean friendlyPlayer = false;
                    Player tmpPlayer = planetOtherEnd.getPlayerInControl();
                    if (tmpPlayer != null) { // planet is not neutral
                        Logger.finer("  planet is not neutral");
                        if (DiplomacyPureFunctions.friendlySpaceports(aPlayer, tmpPlayer, galaxy.getDiplomacyStates())) {
                            Logger.finer("  friendly player");
                            friendlyPlayer = true;
                        }
                        // Faction tmpFaction = tmpPlayer.getFaction();
                        // if (tmpFaction.equals(aPlayer.getFaction())){
                        // Logger.finer(" same faction");
                        // sameFaction = true;
                        // }
                    }
                    // then check if there is a spaceport on the other planet
                    if (friendlyPlayer) {
                        if (planetOtherEnd.hasSpacePort()) {
                            Logger.finer("ss2 is spaceport");
                            bothHaveValidSpacePorts = true;
                        }
                    }
                }
                boolean add = false;
                if (bothHaveValidSpacePorts) {
                    Logger.finer("bothHaveValidSpacePorts!");
                    add = true;
                } else {
                    if (longRange) { // ship is long range
                        Logger.finer("ship is long range!");
                        add = true;
                    } else {
                        if (!planetConnection.isLongRange()) { // ship is short range but so is connection range
                            Logger.finer("ship is short range but so is connection range");
                            add = true;
                        }
                    }
                }
                if (add & ownPlanetsOnly) {
                    if (planetOtherEnd.getPlayerInControl() != aPlayer) {
                        add = false;
                    }
                }
                if (add) {
                    alldest.add(planetOtherEnd.getName());
                }
            }
        }
        return alldest;
    }
}
