package spaceraze.servlethelper.game.planet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import spaceraze.map.GalaxyMap;
import spaceraze.map.MapPlanet;
import spaceraze.servlethelper.comparator.PlanetNameComparator;
import spaceraze.servlethelper.comparator.PlayerNameComparator;
import spaceraze.servlethelper.game.BuildingPureFunctions;
import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.util.move.FindPlanetCriterion;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.world.diplomacy.DiplomacyState;
import spaceraze.world.enums.SpaceshipRange;
import spaceraze.world.mapinfo.MapPlanetInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PlanetPureFunctions {

    private PlanetPureFunctions(){}

    public static PlanetInformation findPlanetInfo(String mapPlanetUuid, List<PlanetInformation> planetInformations){
        return planetInformations.stream().filter(planetInformation -> planetInformation.getMapPlanetUuid().equals(mapPlanetUuid)).findFirst().orElse(null);
    }

    public static String getLastKnownBuildingsOnSurface(String planetUuid, List<PlanetInformation> planetInformations){
        return PlanetPureFunctions.findPlanetInfo(planetUuid, planetInformations).getLastKnownBuildingsOnSurface();
    }

    public static String getLastKnownBuildings(String mapPlanetUuid, List<PlanetInformation> planetInformations){
        String allBuildingsString = "";
        String tmp = PlanetPureFunctions.findPlanetInfo(mapPlanetUuid, planetInformations).getLastKnownBuildingsOnSurface();
        if (tmp != null && tmp.length() > 0){
            allBuildingsString = tmp;
        }
        tmp = PlanetPureFunctions.findPlanetInfo(mapPlanetUuid, planetInformations).getLastKnownBuildingsInOrbit();
        if (allBuildingsString.length() > 0 & (tmp != null && tmp.length() > 0)){
            allBuildingsString += ", ";
        }
        if (tmp != null){
            allBuildingsString += tmp;
        }
        return allBuildingsString;
    }

    public static Planet findClosestOwnPlanetFromShip(Planet aLocation, Player aPlayer, Spaceship aSpaceship, Galaxy galaxy) {
        return findClosestPlanet(aLocation, aPlayer, SpaceshipPureFunctions.getRange(aSpaceship, galaxy), FindPlanetCriterion.OWN_PLANET_NOT_BESIEGED,
                null, galaxy);
    }

    public static List<Planet> findClosestPlanets(Planet aLocation, Player aPlayer, SpaceshipRange aSpaceshipRange,
                                                   FindPlanetCriterion aCriterium, List<String> visitedPlanets, Galaxy galaxy) {
        Logger.finer("findClosestOwnPlanetFromShip: " + aLocation.getMapPlanetUuid());
        List<Planet> foundPlanets = new ArrayList<Planet>();
        List<Planet> edgePlanets = new ArrayList<Planet>(); // de planeter som var på gränsen till det genomsökta
        // området
        edgePlanets.add(aLocation);
        List<Planet> newEdgePlanets = new ArrayList<Planet>(); // de planeter som är på gränsen till det genomsökta
        // området
        List<Planet> searchedPlanets = new ArrayList<Planet>(); // lägg in alla som genomsökts + startplaneten
        searchedPlanets.add(aLocation);
        /*
         * // a spaceship cannot retreat back to the planet it retreated from if
         * (aLocation != aSpaceship.getRetreatingFrom()){
         * searchedPlanets.addElement(aSpaceship.getRetreatingFrom());
         * LoggingHandler.fine( this, this, "","adding: " +
         * aSpaceship.getRetreatingFrom()); }
         */
        List<Planet> allNeighbours;
        // loopa tills alla planeter har letats igenom eller minst 1 lämplig planet har
        // hittats
        while ((searchedPlanets.size() < galaxy.getPlanets().size()) & (foundPlanets.size() == 0) & (edgePlanets.size() > 0)) {
            Logger.finer("in while");
            // Gå igenom alla edgePlanets
            for (int i = 0; i < edgePlanets.size(); i++) {
                Logger.finest("loop edgeplanets");
                Planet tempPlanet = edgePlanets.get(i);
                Logger.finest("temp edgeplanet: " + tempPlanet.getMapPlanetUuid());
                // Hämta alla grannar till tempPlanet
                allNeighbours = getAllDestinations(galaxy, tempPlanet, aSpaceshipRange == SpaceshipRange.LONG);
                // Gå igenom alla allNeighbours (lägg i newEdgePlanets)
                for (int j = 0; j < allNeighbours.size(); j++) {
                    Logger.finest("loop neighbours");
                    Planet tempNeighbourPlanet = allNeighbours.get(j);
                    Logger.finest("temp neighbours: " + tempNeighbourPlanet.getMapPlanetUuid());
                    // kolla att tempNeighbourPlanet inte redan finns i searchedPlanets
                    if ((!searchedPlanets.contains(tempNeighbourPlanet))
                            & (!newEdgePlanets.contains(tempNeighbourPlanet))) {
                        // lägg i newEdgePlanets
                        newEdgePlanets.add(tempNeighbourPlanet);
                        Logger.finest("adding to searched");
                    }
                }
            }
            Logger.finer("loop edge finished");
            // Gå igenom newEdgePlanets och (och ej belägrade??? kan bara gälla egna
            // planeter)
            for (int k = 0; k < newEdgePlanets.size(); k++) {
                Logger.finest("loop new edge");
                Planet tempPlanet = newEdgePlanets.get(k);
                Logger.finest("temp new edgeplanet: " + tempPlanet.getMapPlanetUuid());
                boolean alreadyVisited = false;
                if ((visitedPlanets != null) && (visitedPlanets.contains(tempPlanet.getMapPlanetUuid()))) {
                    alreadyVisited = true;
                }
                if (!alreadyVisited) {
                    if (aCriterium == FindPlanetCriterion.OWN_PLANET_NOT_BESIEGED) {
                        // kolla om planeten tillhör eftersökt spelare
                        if (tempPlanet.getPlayerInControl() == aPlayer) {// om planeter tillhör eftersökt spelare
                            // om den dessutom ej är belägrad, sätt in den i foundPlanets
                            if (!tempPlanet.isBesieged()) {
                                foundPlanets.add(tempPlanet);
                                Logger.finest("adding to found: " + tempPlanet.getMapPlanetUuid());
                            }
                        }
                    } else if (aCriterium == FindPlanetCriterion.CLOSED) { // only planets not belonging to the player
                        if ((tempPlanet.getPlayerInControl() != aPlayer) & (!tempPlanet.isOpen())) {
                            foundPlanets.add(tempPlanet);
                        }
                    } else if (aCriterium == FindPlanetCriterion.HOSTILE_ASSASSIN_OPEN) {
                        if (tempPlanet.isOpen() & tempPlanet.getPlayerInControl() != null) {
                            if (DiplomacyPureFunctions.hostileAssassin(tempPlanet.getPlayerInControl(), aPlayer, galaxy)) {
                                foundPlanets.add(tempPlanet);
                            }
                        }
                    } else if (aCriterium == FindPlanetCriterion.NEUTRAL_UNTOUCHED) {
                        if (tempPlanet.isOpen() & tempPlanet.getPlayerInControl() == null) { // open neutral
                            foundPlanets.add(tempPlanet);
                        } else { // if closed since the beginning, and assumed neutral
                            MapPlanetInfo mapPlanetInfo = aPlayer.getMapInfos().getLastKnownOwnerInfo(tempPlanet); // should
                            // return
                            // null
                            // if
                            // no
                            // info
                            // about
                            // owner
                            if ((tempPlanet.getPlayerInControl() != aPlayer) & !tempPlanet.isOpen()
                                    & (mapPlanetInfo == null)) {
                                foundPlanets.add(tempPlanet);
                            }
                        }
                    }
                }
            }
            Logger.finest("loop new edge finished");
            // töm edgePlanets
            edgePlanets.clear();
            // kopiera över newEdgePlanets till edgePlanets
            for (int l = 0; l < newEdgePlanets.size(); l++) {
                edgePlanets.add(newEdgePlanets.get(l));
            }
            // kopiera över newEdgePlanets till searchedPlanets
            for (int m = 0; m < newEdgePlanets.size(); m++) {
                searchedPlanets.add(newEdgePlanets.get(m));
            }
            // töm newEdgePlanets
            newEdgePlanets.clear();
            // log if no more planets can be searched
            if (edgePlanets.size() == 0) {
                Logger.finest("egdePlanets is empty, while loop exited");
            }
        }
        return foundPlanets;
    }

    // aPlayer kan vara null för att leta efter neutrala planeter
    private static Planet findClosestPlanet(Planet aLocation, Player aPlayer, SpaceshipRange aSpaceshipRange,
                                     FindPlanetCriterion aCriterium, List<String> visitedPlanets, Galaxy galaxy) {
        Logger.finer("findClosestOwnPlanetFromShip: " + aLocation.getMapPlanetUuid());
        Planet foundPlanet = null;
        List<Planet> foundPlanets = findClosestPlanets(aLocation, aPlayer, aSpaceshipRange,
                aCriterium, visitedPlanets, galaxy);

        Logger.finest("while finished");
        // om vektorn.size() > 0, dvs minst 1st lämplig planet har hittats
        if (foundPlanets.size() > 0) {
            Logger.finest("foundPlanets.size() > 0");
            // välj slumpartat en av de planeterna
            if (foundPlanets.size() > 1) {
                // Functions.randomize(foundPlanets);
                Collections.shuffle(foundPlanets);
            }
            // sätt foundPlanet till den utslumpade planeten
            foundPlanet = foundPlanets.get(0);
        } else {
            Logger.finest("foundPlanets.size() == 0");
        }
        return foundPlanet;
    }

    public static Planet getEscapePlanet(Spaceship spaceship, Galaxy galaxy) {
        Logger.finer("getRunToPlanet aSpaceship: " + spaceship.getName());
        Planet foundPlanet = null;
        Planet firstDestination = null;
        // kolla efter egna planeter
        foundPlanet = findClosestOwnPlanetFromShip(spaceship.getLocation(), spaceship.getOwner(), spaceship, galaxy);
        // om en destinationsplanet har hittats skall den 1:a planeten på väg dit hämtas
        if (foundPlanet != null){
            Logger.finer("foundPlanet: " + foundPlanet.getMapPlanetUuid());
            firstDestination = findFirstJumpTowardsPlanet(spaceship.getLocation(), foundPlanet, SpaceshipPureFunctions.getRange(spaceship, galaxy), galaxy);
            Logger.finer("firstDestination: " + firstDestination.getMapPlanetUuid());
        }else{
            Logger.finer("no planet found");
        }
        return firstDestination;

    }

    private static Planet findFirstJumpTowardsPlanet(Planet aLocation, Planet aDestination, SpaceshipRange aSpaceshipRange, Galaxy galaxy){
        Logger.finer("findFirstJumpTowardsPlanet aDestination: " + aDestination.getMapPlanetUuid());
        Planet firstStopPlanet = null;
        boolean found = false;
        // sätt reachFrom på startplaneten så den blir rotnod
        aLocation.setReachFrom(null);
        List<Planet> edgePlanets = new LinkedList<Planet>(); // de planeter som är på gränsen till det genomsökta området
        edgePlanets.add(aLocation);
        List<Planet> newEdgePlanets = new LinkedList<Planet>(); // de planeter som är på gränsen till det genomsökta området
        List<Planet> searchedPlanets = new LinkedList<Planet>();  // lägg in alla som genomsökts + startplaneten
        searchedPlanets.add(aLocation);
        List<Planet> allNeighbours;
        // loopa tills alla planeter har letats igenom eller minst 1 lämplig planet har hittats
        while (!found){
            Logger.finest("while (!found) found: " + found);
            // Gå igenom alla edgePlanets
            for (int i = 0; i < edgePlanets.size(); i++){
                Planet tempPlanet = (Planet)edgePlanets.get(i);
                Logger.finest("tempPlanet: " + tempPlanet.getMapPlanetUuid());
                // Hämta alla grannar till tempPlanet
                allNeighbours = getAllDestinations(galaxy, tempPlanet,aSpaceshipRange == SpaceshipRange.LONG);
                // Gå igenom alla allNeighbours  (lägg i newEdgePlanets)
                for (int j = 0; j < allNeighbours.size(); j++){
                    Planet tempNeighbourPlanet = allNeighbours.get(j);
                    Logger.finest("tempNeighbourPlanet: " + tempNeighbourPlanet.getMapPlanetUuid());
                    // kolla att tempNeighbourPlanet inte redan finns i searchedPlanets
                    if ((!containsPlanet(searchedPlanets,tempNeighbourPlanet)) & (!containsPlanet(newEdgePlanets,tempNeighbourPlanet))){
                        Logger.finest("containsPlanet: " + !containsPlanet(searchedPlanets,tempNeighbourPlanet));
                        Logger.finest("containsPlanet: " + !containsPlanet(newEdgePlanets,tempNeighbourPlanet));
                        Logger.finest("inside if: ");
                        // sätt reachFrom så det går att hitta pathen senare
                        tempNeighbourPlanet.setReachFrom(tempPlanet);
                        // lägg i newEdgePlanets
                        newEdgePlanets.add(tempNeighbourPlanet);
                        // kolla om det är den eftersökta planeten
                        if(tempNeighbourPlanet == aDestination){
                            Logger.finest("found = true ");
                            found = true;
                        }
                    }
                }
            }
            // töm edgePlanets
            edgePlanets.clear();
            for (int l = 0; l < newEdgePlanets.size(); l++){
                // kopiera över newEdgePlanets till edgePlanets
                edgePlanets.add((Planet)newEdgePlanets.get(l));
                // kopiera över newEdgePlanets till searchedPlanets
                searchedPlanets.add((Planet)newEdgePlanets.get(l));
            }
            // töm newEdgePlanets
            newEdgePlanets.clear();
        }
        Planet lastStop = aDestination;
        // loopa tills reachFrom �r null
        Logger.finest("before while, lastStop: " + lastStop.getMapPlanetUuid());
        while (lastStop.getReachFrom().getReachFrom() != null){
            Logger.finest("");
            Logger.finest("inside if: " + lastStop.getReachFrom().getMapPlanetUuid());
            Logger.finest("inside if: " + lastStop.getReachFrom().getReachFrom().getMapPlanetUuid());
            lastStop = lastStop.getReachFrom();
        }
        firstStopPlanet = lastStop;
        return firstStopPlanet;
    }

    public static boolean isEnemyOrNeutralPlanet(Player player, Planet planet, Galaxy galaxy){

        boolean enemyOrNeutralPlanet = true;

        if(planet.getPlayerInControl() == null){
            enemyOrNeutralPlanet = true;
        }else if(player == planet.getPlayerInControl()){
            enemyOrNeutralPlanet = false;
        }else{
            DiplomacyState diplomacyState = DiplomacyPureFunctions.getDiplomacyState(player, planet.getPlayerInControl(), galaxy.getDiplomacyStates());
            if(diplomacyState.getCurrentLevel().isHigher(DiplomacyLevel.PEACE)){
                enemyOrNeutralPlanet = false;
            }
        }
        return enemyOrNeutralPlanet;
    }

    public static boolean isItAlliesSurveyShipsOnPlanet(Player player, Planet planet, Galaxy galaxy) {
        List<Player> allies = PlayerPureFunctions.getAllies(player, galaxy.getPlayers(), galaxy);
        boolean foundShip = false;
        int i = 0;
        while (!foundShip && allies.size() > i) {
            if (SpaceshipPureFunctions.findSurveyShip(planet, allies.get(i), galaxy.getSpaceships(), galaxy.getGameWorld()) != null) {
                foundShip = true;
            }
            i++;
        }
        return foundShip;
    }

    /**
     * Checks if allies to the player have any ships on the planet. If so the player
     * should get information about the planet.
     *
     * @return
     */
    public static boolean isItAlliedShipsInSystem(Player player, Planet planet, Galaxy galaxy) {
        List<Player> allies = PlayerPureFunctions.getAllies(player, galaxy.getPlayers(), galaxy);
        boolean haveAllied = false;
        int i = 0;
        while (!haveAllied && allies.size() > i) {
            if (PlayerPureFunctions.playerHasShipsInSystem(allies.get(i), planet, galaxy)) {
                haveAllied = true;
            }
            i++;
        }
        return haveAllied;
    }

    public static boolean isItAlliesSurveyVipOnPlanet(Player player, Planet planet, Galaxy galaxy) {
        List<Player> allies = PlayerPureFunctions.getAllies(player, galaxy.getPlayers(), galaxy);
        boolean foundSpy = false;
        int i = 0;
        while (!foundSpy && allies.size() > i) {
            if (VipPureFunctions.findSurveyVIPonShip(planet, allies.get(i), galaxy) != null) {
                foundSpy = true;
            }
            i++;
        }
        return foundSpy;
    }

    public static boolean isItAlliedSpyOnPlanet(Player player, Planet planet, Galaxy galaxy) {
        List<Player> allies = PlayerPureFunctions.getAllies(player, galaxy.getPlayers(), galaxy);
        boolean foundSpy = false;
        int i = 0;
        while (!foundSpy && allies.size() < i) {
            if (VipPureFunctions.findVIPSpy(planet, allies.get(i), galaxy) != null) {
                foundSpy = true;
            }
            i++;
        }
        return foundSpy;
    }

    private static boolean containsPlanet(List<Planet> planets, Planet planet) {
        return planets.stream().anyMatch(planetInList -> planetInList == planet);
    }

    public static Planet getPlanet(String uuid, Galaxy galaxy) {
        return getPlanet(uuid, galaxy.getPlanets());
    }

    public static Planet getPlanet(String uuid, List<Planet> planets) {
        Logger.finer("getPlanet(String planetName) planetName : " + uuid);
        for (Planet planet : planets) {
            if (planet.getMapPlanetUuid().equalsIgnoreCase(uuid)) {
                return planet;
            }
        }
        throw new IllegalArgumentException("Planet not found");
    }

    public static boolean checkSurrender(Planet planet, Galaxy galaxy){
        return (planet.getResistance() + VipPureFunctions.findHighestVIPResistanceBonus(planet, planet.getPlayerInControl(), galaxy)) < 1;
    }

    public static List<Building> getBuildings(Planet planet, boolean orbitOnly, GameWorld gameWorld) {
        List<Building> buildingsInOrbit = new ArrayList<>();
        for (Building aBuilding : planet.getBuildings()) {
            if(orbitOnly == BuildingPureFunctions.getBuildingTypeByUuid(aBuilding.getTypeUuid(), gameWorld).isInOrbit()){
                buildingsInOrbit.add(aBuilding);
            }
        }
        return buildingsInOrbit;
    }

    public static List<Planet> getPlayersPlanets(Player aPlayer, Galaxy galaxy) {
        List<Planet> playersPlanets = new ArrayList<>();
        for (int i = 0; i < galaxy.getPlanets().size(); i++) {
            Planet tempPlanet = galaxy.getPlanets().get(i);
            if (tempPlanet.getPlayerInControl() == aPlayer) {
                playersPlanets.add(tempPlanet);
            }
        }
        return playersPlanets;
    }

    public static int getShield(Planet planet){
        int biggestShield=0;
        for(int i=0; i < planet.getBuildings().size(); i++){
            if(planet.getBuildings().get(i).getShieldCapacity() > biggestShield){
                biggestShield = planet.getBuildings().get(i).getShieldCapacity();
            }
        }
        return biggestShield;
    }

    public static boolean getInfectedByAlien(Planet planet, Galaxy galaxy){
        boolean infectedByAlien = false;
        if (planet.getPlayerInControl() != null){
            if (GameWorldHandler.getFactionByUuid(planet.getPlayerInControl().getFactionUuid(), galaxy.getGameWorld()).isAlien()){
                infectedByAlien = true;
            }
        }
        return infectedByAlien;
    }

    public static boolean isRazedAndUninfected(Planet planet){
        boolean empty = false;
        if (planet.getPopulation() == 0){
            if (planet.getPlayerInControl() == null){
                empty = true;
            }
        }
        return empty;
    }

    public static int getBuildingTechBonus(Planet planet, GameWorld gameWorld){
        int bonus=0;
        for(int i=0; i< planet.getBuildings().size();i++){
            if(planet.getBuildings().get(i).getTechBonus() > bonus){
                if(!BuildingPureFunctions.getBuildingTypeByUuid(planet.getBuildings().get(i).getTypeUuid(), gameWorld).isInOrbit() || !planet.isBesieged()){
                    bonus = planet.getBuildings().get(i).getTechBonus();
                }

            }
        }
        return bonus;
    }

    public static boolean hasSpacePort(Planet planet){
        for(int i=0; i< planet.getBuildings().size();i++){
            if(planet.getBuildings().get(i).isSpaceport()){
                return true;
            }
        }
        return false;
    }

    public static boolean isPlanetOwner(Planet planet, Player aPlayer){
        if(planet.getPlayerInControl() != null && planet.getPlayerInControl() == aPlayer){
            return true;
        }
        return false;
    }

    public static boolean isRazed(Planet planet){
        return planet.getPopulation() == 0;
    }

    public static List<MapPlanet> getMapPlanets(GalaxyMap galaxyMap, List<Planet> planets){
        ArrayList<MapPlanet> mapPlanets = galaxyMap.getPlanets().stream().filter(planet -> planetExists(planet, planets)).collect(Collectors.toCollection(ArrayList::new));
        mapPlanets.sort(new PlanetNameComparator<>());
        return mapPlanets;
    }

    private static boolean planetExists(MapPlanet planet, List<Planet> planets) {
        return planets.stream().anyMatch(planetInList -> planetInList.getMapPlanetUuid().equalsIgnoreCase(planet.getUuid()));
    }

    public static MapPlanet getMapPlanet(GalaxyMap galaxyMap, String planetUuid) {
        return galaxyMap.getPlanets().stream().filter(planet -> planet.getUuid().equals(planetUuid)).findFirst().orElse(null);
    }

    public static MapPlanet getMapPlanetByName(GalaxyMap galaxyMap, String planetName) {
        return galaxyMap.getPlanets().stream().filter(planet -> planet.getName().equals(planetName)).findFirst().orElse(null);
    }

    public static Planet getPlanetByName(Galaxy galaxy,GalaxyMap galaxyMap, String planetName) {
        MapPlanet mapPlanet = galaxyMap.getPlanets().stream().filter(planet -> planet.getName().equals(planetName)).findFirst().orElse(null);
        if (mapPlanet != null) {
            return getPlanet(mapPlanet.getUuid(), galaxy.getPlanets());
        }
        return null;
    }

    public static String getPlanetName(GalaxyMap galaxyMap, String planetUuid) {
        return getMapPlanet(galaxyMap, planetUuid) != null ? getMapPlanet(galaxyMap, planetUuid).getName() : null;
    }

    public static Planet getOtherEnd(PlanetConnection connection, Planet planet, boolean isLongRange) {
        if (!connection.isLongRange() || isLongRange) {
            if (planet == connection.getPlanetOne()) {
                return connection.getPlanetTwo();
            } else if (planet == connection.getPlanetTwo()) {
                return connection.getPlanetOne();
            }
        }
        return null;
    }

    public static boolean isConnection(PlanetConnection connection, Planet aPlanet1, Planet aPlanet2) {
        return (connection.getPlanetOne() == aPlanet1 && connection.getPlanetTwo() == aPlanet2) || (connection.getPlanetTwo() == aPlanet1 && connection.getPlanetOne() == aPlanet2);
    }

    /**
     * Returns 0 if there is no connection between the two planets
     */
    public static SpaceshipRange getDistance(Galaxy galaxy, Planet planet1, Planet planet2) {
        SpaceshipRange dist = SpaceshipRange.NONE;
        for (PlanetConnection connection : galaxy.getPlanetConnections()) {
            if (isConnection(connection, planet1, planet2)) { // there is a connection within range
                if (connection.isLongRange()) {
                    dist = SpaceshipRange.LONG;
                } else {
                    dist = SpaceshipRange.SHORT;
                }
            }
        }
        return dist;
    }

    public static List<Planet> getAllDestinations(Galaxy galaxy, Planet location, boolean longRange) {
        List<Planet> allDestinations = new ArrayList<>();
        for (PlanetConnection connection : galaxy.getPlanetConnections()) {
            Planet planet = getOtherEnd(connection, location, longRange);
            if (planet != null) { // there is a connection within range
                allDestinations.add(getPlanet(planet.getMapPlanetUuid(), galaxy.getPlanets()));
            }
        }
        return allDestinations;
    }
}
