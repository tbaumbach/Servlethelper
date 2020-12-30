package spaceraze.servlethelper.game.planet;

import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
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

public class PlanetPureFunctions {

    private PlanetPureFunctions(){}

    public static PlanetInformation findPlanetInfo(String planetName, List<PlanetInformation> planetInformations){
        return planetInformations.stream().filter(planetInformation -> planetInformation.getName().equals(planetName)).findFirst().orElse(null);
    }

    public static String getLastKnownBuildingsOnSurface(String planetName, List<PlanetInformation> planetInformations){
        return PlanetPureFunctions.findPlanetInfo(planetName, planetInformations).getLastKnownBuildingsOnSurface();
    }

    public static String getLastKnownBuildings(String planetName, List<PlanetInformation> planetInformations){
        String allBuildingsString = "";
        String tmp = PlanetPureFunctions.findPlanetInfo(planetName, planetInformations).getLastKnownBuildingsOnSurface();
        if (tmp != null && tmp.length() > 0){
            allBuildingsString = tmp;
        }
        tmp = PlanetPureFunctions.findPlanetInfo(planetName, planetInformations).getLastKnownBuildingsInOrbit();
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
        Logger.finer("findClosestOwnPlanetFromShip: " + aLocation.getName());
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
                Logger.finest("temp edgeplanet: " + tempPlanet.getName());
                // Hämta alla grannar till tempPlanet
                allNeighbours = galaxy.getAllDestinations(tempPlanet, aSpaceshipRange == SpaceshipRange.LONG);
                // Gå igenom alla allNeighbours (lägg i newEdgePlanets)
                for (int j = 0; j < allNeighbours.size(); j++) {
                    Logger.finest("loop neighbours");
                    Planet tempNeighbourPlanet = allNeighbours.get(j);
                    Logger.finest("temp neighbours: " + tempNeighbourPlanet.getName());
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
                Logger.finest("temp new edgeplanet: " + tempPlanet.getName());
                boolean alreadyVisited = false;
                if ((visitedPlanets != null) && (visitedPlanets.contains(tempPlanet.getName()))) {
                    alreadyVisited = true;
                }
                if (!alreadyVisited) {
                    if (aCriterium == FindPlanetCriterion.OWN_PLANET_NOT_BESIEGED) {
                        // kolla om planeten tillhör eftersökt spelare
                        if (tempPlanet.getPlayerInControl() == aPlayer) {// om planeter tillhör eftersökt spelare
                            // om den dessutom ej är belägrad, sätt in den i foundPlanets
                            if (!tempPlanet.isBesieged()) {
                                foundPlanets.add(tempPlanet);
                                Logger.finest("adding to found: " + tempPlanet.getName());
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
        Logger.finer("findClosestOwnPlanetFromShip: " + aLocation.getName());
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
            Logger.finer("foundPlanet: " + foundPlanet.getName());
            firstDestination = findFirstJumpTowardsPlanet(spaceship.getLocation(), foundPlanet, SpaceshipPureFunctions.getRange(spaceship, galaxy), galaxy);
            Logger.finer("firstDestination: " + firstDestination.getName());
        }else{
            Logger.finer("no planet found");
        }
        return firstDestination;

    }

    private static Planet findFirstJumpTowardsPlanet(Planet aLocation, Planet aDestination, SpaceshipRange aSpaceshipRange, Galaxy galaxy){
        Logger.finer("findFirstJumpTowardsPlanet aDestination: " + aDestination.getName());
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
                Logger.finest("tempPlanet: " + tempPlanet.getName());
                // Hämta alla grannar till tempPlanet
                allNeighbours = galaxy.getAllDestinations(tempPlanet,aSpaceshipRange == SpaceshipRange.LONG);
                // Gå igenom alla allNeighbours  (lägg i newEdgePlanets)
                for (int j = 0; j < allNeighbours.size(); j++){
                    Planet tempNeighbourPlanet = allNeighbours.get(j);
                    Logger.finest("tempNeighbourPlanet: " + tempNeighbourPlanet.getName());
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
        Logger.finest("before while, lastStop: " + lastStop.getName());
        while (lastStop.getReachFrom().getReachFrom() != null){
            Logger.finest("");
            Logger.finest("inside if: " + lastStop.getReachFrom().getName());
            Logger.finest("inside if: " + lastStop.getReachFrom().getReachFrom().getName());
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
            if (galaxy.playerHasShipsInSystem(allies.get(i), planet)) {
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

    public static Planet getPlanet(String planetName, Galaxy galaxy) {
        Logger.finer("getPlanet(String planetName) planetName : " + planetName);
        Planet aPlanet = null;
        for (Planet tempPlanet : galaxy.getPlanets()) {
            if (tempPlanet.getName().equalsIgnoreCase(planetName)) {
                aPlanet = tempPlanet;
                break;
            }
        }
        return aPlanet;
    }

    public static boolean checkSurrender(Planet planet, Galaxy galaxy){
        return (planet.getResistance() + VipPureFunctions.findHighestVIPResistanceBonus(planet, planet.getPlayerInControl(), galaxy)) < 1;
    }
}
