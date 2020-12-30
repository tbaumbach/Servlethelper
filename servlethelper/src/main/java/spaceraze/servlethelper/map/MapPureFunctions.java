package spaceraze.servlethelper.map;

import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.servlethelper.comparator.SpaceshipSizeAndBuildCostComparator;
import spaceraze.world.enums.SpaceShipSize;
import spaceraze.world.mapinfo.*;

import java.util.*;

public class MapPureFunctions {

    public static List<String> getShipsList(Planet planet, Player player, Galaxy g) {
        // list to return
        List<String> shipStrings = new LinkedList<>();
        // get spaceships on this planet, both civ and military
        List<Spaceship> shipsOnPlanet = SpaceshipPureFunctions.getShips(planet, false, g);
        shipsOnPlanet.addAll(SpaceshipPureFunctions.getShips(planet, true, g));
        List<Spaceship> shipsPlayer = new LinkedList<>();
        for (Spaceship spaceship : shipsOnPlanet) {
            if (spaceship.getOwner() == player) {
                shipsPlayer.add(spaceship);
            }
        }
        // sort the ships
        Collections.sort(shipsPlayer, new SpaceshipSizeAndBuildCostComparator(g.getGameWorld()));
        int shipCount = 0;
        int i = 0;
        boolean addShip = false; // anv. för att avgöra när alla skepp av en typ har hittats (så att räknaren blir rätt)
        for (Spaceship spaceship : shipsPlayer) {
            shipCount++;
            List<VIP> tmpVips = VipPureFunctions.findAllVIPsOnShip(spaceship, g.getAllVIPs());
            if (i == (shipsPlayer.size() - 1)) {
                addShip = true;
            } else {
                Spaceship spaceshipNext = shipsPlayer.get(i + 1);
                if (!SpaceshipPureFunctions.getSpaceshipTypeByKey(spaceshipNext.getTypeKey(), g.getGameWorld()).getName().equals(SpaceshipPureFunctions.getSpaceshipTypeByKey(spaceship.getTypeKey(), g.getGameWorld()).getName())) {
                    addShip = true;
                }
            }
            if (addShip) {
                String tmpShipStr = SpaceshipPureFunctions.getSpaceshipTypeByKey(spaceship.getTypeKey(), g.getGameWorld()).getShortName();
                if (shipCount > 1) {
                    tmpShipStr = shipCount + " " + tmpShipStr;
                }
                if (tmpVips.size() > 0) {
                    tmpShipStr = tmpShipStr + " (";
                }
                if (tmpVips.size() > 0) {
                    for (Iterator<VIP> iter = tmpVips.iterator(); iter.hasNext(); ) {
                        VIP aVIP = iter.next();
                        tmpShipStr = tmpShipStr + VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), g.getGameWorld()).getShortName();
                        if (iter.hasNext()) {
                            tmpShipStr = tmpShipStr + ",";
                        }
                    }
                }
                if (tmpVips.size() > 0) {
                    tmpShipStr = tmpShipStr + ")";
                }
                shipStrings.add(tmpShipStr);
                shipCount = 0;
            }
            i++;
        }
        return shipStrings;
    }

    public static MapInfoTurn createMapInfoTurn(Player player, MapInfos mapPlanetInfos, int turn) {
        List<MapPlanetInfo> allPlanetInfos = new ArrayList<>();
        List<MapConnectionInfo> starPortConnections = new ArrayList<>();

        for (Planet planet : player.getGalaxy().getPlanets()) {
            MapPlanetInfo mapPlanetInfo = MapPureFunctions.createMapPlanetInfo(planet, player, mapPlanetInfos, turn, getShipsList(planet, player, player.getGalaxy()), player.getGalaxy());
            allPlanetInfos.add(mapPlanetInfo);
        }
        for (PlanetConnection aPlanetConnection : player.getGalaxy().getPlanetConnections()) {
            if (MapPureFunctions.isStarPortConnections(aPlanetConnection, player)) {
                MapConnectionInfo mapConnectionInfo = new MapConnectionInfo(aPlanetConnection);
                starPortConnections.add(mapConnectionInfo);
            }
        }

        return MapInfoTurn.builder().allPlanetInfos(allPlanetInfos).starPortConnections(starPortConnections).build();
    }

    public static boolean isStarPortConnections(PlanetConnection planetConnection, Player player) {
        boolean haveStarPort = false;
        Galaxy g = player.getGalaxy();
        Planet p1 = g.getPlanet(planetConnection.getPlanetOne().getName());
        Planet p2 = g.getPlanet(planetConnection.getPlanetTwo().getName());
        // check if starport make this connection short range
        if ((p1.getPlayerInControl() != null) & (p2.getPlayerInControl() != null)) { // none of the planets are neutral
            if ((p1.hasSpacePort()) & (p2.hasSpacePort())) { // both have a spacestation
                if (DiplomacyPureFunctions.friendlySpaceports(p1.getPlayerInControl(), p2.getPlayerInControl(), g.getDiplomacyStates())) {
                    if (DiplomacyPureFunctions.friendlySpaceports(player, p1.getPlayerInControl(), g.getDiplomacyStates())) {
                        if (DiplomacyPureFunctions.friendlySpaceports(player, p2.getPlayerInControl(), g.getDiplomacyStates())) {
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

    public static MapPlanetInfo createMapPlanetInfo(Planet planet, Player player, MapInfos mapPlanetInfos, int turn, List<String> spaceships, Galaxy galaxy) {
        MapPlanetInfo mapPlanetInfo = new MapPlanetInfo();
        Logger.finer("MapPlanetInfo creator, planet: " + planet.getName() + ", player: " + player.getGovernorName() + ", turn: " + turn);
        mapPlanetInfo.setPlanetName(planet.getName());
        boolean spy = VipPureFunctions.findVIPSpy(planet, player, galaxy) != null;
        boolean shipInSystem = (galaxy.playerHasShipsInSystem(player, planet));
        boolean surveyShip = (SpaceshipPureFunctions.findSurveyShip(planet, player, galaxy.getSpaceships(), galaxy.getGameWorld()) != null);
        boolean surveyVIP = VipPureFunctions.findSurveyVIPonShip(planet, player, galaxy) != null;
        boolean survey = surveyShip | surveyVIP;
        boolean openPlanet = planet.isOpen();
        boolean neutralPlanet = (planet.getPlayerInControl() == null);
        boolean ownsPlanet = planet.getPlayerInControl() == player;
        MapPlanetInfo lastKnownOwnerMapPlanetInfo = mapPlanetInfos.getLastKnownOwnerInfo(planet);
        MapPlanetInfo lastKnownProdResMapPlanetInfo = mapPlanetInfos.getLastKnownProdResInfo(planet);
        MapPlanetInfo lastKnownRazedMapPlanetInfo = mapPlanetInfos.getLastKnownRazedInfo(planet);
        // owner, lastKnownOwner, lastInfoTurn, razed, lastKnownMaxShipSize
        if (ownsPlanet) {
            mapPlanetInfo.setOwner(planet.getPlayerInControl().getName());
            mapPlanetInfo.setRazed(false);
            mapPlanetInfo.setBesieged(planet.isBesieged());
            mapPlanetInfo.setLastInfoTurn(turn);
            mapPlanetInfo.setLastKnownOwner(null);
            mapPlanetInfo.setLastKnownMaxShipSize(null);
            mapPlanetInfo.setLastKnownRazed(false);
        } else if (spy | shipInSystem | openPlanet) {
//			Logger.info("shipInSystem: " + shipInSystem);
            if (neutralPlanet) {
//				Logger.info("neutralPlanet: " + neutralPlanet);
                if (planet.isRazed()) {
//					Logger.info("planet.isRazed(): " + planet.isRazed());
                    mapPlanetInfo.setOwner(null);
                    mapPlanetInfo.setRazed(true);
//					lastKnownRazed = true;
                } else {
                    mapPlanetInfo.setOwner(MapPlanetInfo.NEUTRAL);
                    mapPlanetInfo.setRazed(false);
//					lastKnownRazed = false;
                }
            } else {
                mapPlanetInfo.setOwner(planet.getPlayerInControl().getName());
                mapPlanetInfo.setRazed(false);
//				lastKnownRazed = false;
            }
            mapPlanetInfo.setBesieged(planet.isBesieged());
            mapPlanetInfo.setLastInfoTurn(turn);
            mapPlanetInfo.setLastKnownOwner(null);
            mapPlanetInfo.setLastKnownMaxShipSize(null);
            mapPlanetInfo.setLastKnownRazed(false);
        } else { // ingen info detta drag
            Logger.finer("no current info about planet this turn");
            mapPlanetInfo.setOwner(null);
            mapPlanetInfo.setRazed(false);
            mapPlanetInfo.setBesieged(false);
            // there can both a lastKnownOwner and lastKnownRazed on the same planet, only use the one with the
            // latest turn nr.
            if ((lastKnownOwnerMapPlanetInfo != null) | (lastKnownRazedMapPlanetInfo != null)) {
                int lastOwnerTurn = -1;
                int lastRazedTurn = -1;
                if (lastKnownOwnerMapPlanetInfo != null) {
                    lastOwnerTurn = lastKnownOwnerMapPlanetInfo.getLastInfoTurn();
                }
                if (lastKnownRazedMapPlanetInfo != null) {
                    lastRazedTurn = lastKnownRazedMapPlanetInfo.getLastInfoTurn();
                }
//				Logger.fine("lastOwnerTurn: " + lastOwnerTurn);
//				Logger.fine("lastRazedTurn: " + lastRazedTurn);
                if (lastOwnerTurn > lastRazedTurn) {
                    Logger.finer("lastKnownOwnerMapPlanetInfo = senaste infon");
                    mapPlanetInfo.setLastKnownOwner(lastKnownOwnerMapPlanetInfo.getOwner());
                    mapPlanetInfo.setLastInfoTurn(lastKnownOwnerMapPlanetInfo.getLastInfoTurn());
                    mapPlanetInfo.setLastKnownRazed(lastKnownOwnerMapPlanetInfo.isRazed());
                    mapPlanetInfo.setLastKnownMaxShipSize(getLastKnownLargetsSizeShipOnPlanet(lastKnownOwnerMapPlanetInfo));
                } else {
                    Logger.finer("info om att den varit razead tidigare!");
                    mapPlanetInfo.setLastKnownOwner("neutral");
                    mapPlanetInfo.setLastInfoTurn(lastKnownRazedMapPlanetInfo.getLastInfoTurn());
                    mapPlanetInfo.setLastKnownRazed(true);
                    mapPlanetInfo.setLastKnownMaxShipSize(null);
//					Logger.fine("lastKnownRazed: " + lastKnownRazed);
//					Logger.fine("lastInfoTurn: " + lastInfoTurn);
                }
            } else {
                Logger.finer("ingen info alls!!!");
                mapPlanetInfo.setLastKnownOwner("neutral");
                mapPlanetInfo.setLastInfoTurn(1);
                mapPlanetInfo.setLastKnownRazed(false);
                mapPlanetInfo.setLastKnownMaxShipSize(null);
            }
        }
        // open
        mapPlanetInfo.setOpen(openPlanet);
        // shipsOwn
        mapPlanetInfo.setShipsOwn(spaceships);
        // fleetsOther
        if (spy | shipInSystem | openPlanet | ownsPlanet) {
            mapPlanetInfo.setFleetsOther(getOtherFleets(planet, player, galaxy));
        }
        // VIPs
        mapPlanetInfo.setOwnVIPs(getOwnVIPsData(player, planet, galaxy));
        if (openPlanet | spy) {
            mapPlanetInfo.setOtherVIPs(getOtherVIPsData(player, planet, galaxy));
        }
        // buildingsOrbit, buildingsSurface, lastKnownBuildingsInOrbit, lastKnownBuildingsOnSurface
        if (openPlanet | shipInSystem | spy | ownsPlanet | survey) {
//			buildingsOrbit = getBuildingsList(planet.getBuildingsInOrbit());
            mapPlanetInfo.setBuildingsVisible(getBuildingsList(planet.getBuildingsByVisibility(true)));
            mapPlanetInfo.setLastKnownBuildingsInOrbit(null);
//			if (openPlanet | spy | survey | ownsPlanet){
            if (spy | survey | ownsPlanet) {
//				buildingsSurface = getBuildingsList(planet.getBuildingsOnSurface());
                mapPlanetInfo.setBuildingsHidden(getBuildingsList(planet.getBuildingsByVisibility(false)));
                mapPlanetInfo.setLastKnownBuildingsOnSurface(null);
            }
        } else {
            mapPlanetInfo.setBuildingsVisible(null);
            mapPlanetInfo.setBuildingsHidden(null);
            if (lastKnownOwnerMapPlanetInfo != null) {
                mapPlanetInfo.setLastKnownBuildingsInOrbit(lastKnownOwnerMapPlanetInfo.getBuildingsVisible());
                mapPlanetInfo.setLastKnownBuildingsOnSurface(lastKnownOwnerMapPlanetInfo.getBuildingsHidden());
            } else {
                mapPlanetInfo.setLastKnownBuildingsInOrbit(null);
                mapPlanetInfo.setLastKnownBuildingsOnSurface(null);
            }
        }
        if (openPlanet | spy | survey | ownsPlanet) {
            if (planet.isRazed()) {
                mapPlanetInfo.setProd("-");
                if (planet.isRazedAndUninfected()) {
                    mapPlanetInfo.setRes("-");
                } else {
                    // alien player
                    mapPlanetInfo.setRes(String.valueOf(planet.getResistance()));
                }
            } else {
                mapPlanetInfo.setProd(String.valueOf(planet.getPopulation()));
                mapPlanetInfo.setRes(String.valueOf(planet.getResistance()));
            }
            mapPlanetInfo.setLastKnownProd(null);
            mapPlanetInfo.setLastKnownRes(null);
        } else {
            mapPlanetInfo.setProd(null);
            mapPlanetInfo.setRes(null);
            if (lastKnownProdResMapPlanetInfo != null) {
                mapPlanetInfo.setLastKnownProd(String.valueOf(lastKnownProdResMapPlanetInfo.getProd()));
                mapPlanetInfo.setLastKnownRes(String.valueOf(lastKnownProdResMapPlanetInfo.getRes()));
            } else {
                mapPlanetInfo.setLastKnownProd(null);
                mapPlanetInfo.setLastKnownRes(null);
            }
        }
        mapPlanetInfo.setNotes(PlanetPureFunctions.findPlanetInfo(planet.getName(), player.getPlanetInformations()).getNotes());


        return mapPlanetInfo;
    }

    private static String getLastKnownLargetsSizeShipOnPlanet(MapPlanetInfo lastKnownMapPlanetInfo){
        String maxSizeIncCiv = "";
        if (lastKnownMapPlanetInfo.getFleetsOther() != null){
            int maxSize = 0;
            boolean civ = false;
            for (FleetData fleetData : lastKnownMapPlanetInfo.getFleetsOther()) {
                if (fleetData.getMaxSize() > maxSize){
                    maxSize = fleetData.getMaxSize();
                }
                if (fleetData.isCiv()){
                    civ = true;
                }
            }
            if (maxSize > 0){
                maxSizeIncCiv = getSizeString(maxSize);
            }
            if (civ){
                if (maxSize > 0){
                    maxSizeIncCiv += "+";
                }
                maxSizeIncCiv += "civ";
            }
        }else{
            maxSizeIncCiv = null;
        }
        return maxSizeIncCiv;
    }

    private static String getSizeString(int size){
        String sizeString = "small";
        if (size == 2){
            sizeString = "medium";
        }else
        if (size == 3){
            sizeString = "large";
        }else
        if (size == 5){
            sizeString = "huge";
        }
        return sizeString;
    }

    private static List<FleetData> getOtherFleets(Planet planet, Player player, Galaxy g){
        List<FleetData> fleets = new LinkedList<FleetData>();
        // loopa igenom alla spelare och kolla efter flottor
        for (Player tempPlayer : g.getPlayers()) {
            if (tempPlayer != player){
                int shipSize = getLargestLookAsMilitaryShipSizeOnPlanet(planet,tempPlayer, g);
                boolean civilianExists = !getLargestShipSizeOnPlanet(planet,tempPlayer,true, g).equals("");
                if ((shipSize > -1) | civilianExists){
                    FleetData fleetData = new FleetData(tempPlayer.getGovernorName(),shipSize,civilianExists);
                    fleets.add(fleetData);
                }
            }
        }
        // kolla efter neutrala skepp
        int shipSize = getLargestLookAsMilitaryShipSizeOnPlanet(planet,null, g);
        if (shipSize > -1){
            FleetData fleetData = new FleetData(null,shipSize,false);
            fleets.add(fleetData);
        }
        return fleets;
    }

    public static String getLargestShipSizeOnPlanet(Planet aPlanet, Player aPlayer, boolean civilian, Galaxy galaxy) {
        SpaceShipSize maxSize = null;
        for (Spaceship aShip : galaxy.getSpaceships()) {
            if ((aShip.getOwner() == aPlayer) & (aShip.getLocation() == aPlanet)) {
                if (aShip.isLookAsCivilian() == civilian) {
                    VIP stealthVIP = VipPureFunctions.findStealthVIPonShip(aPlanet, aShip, galaxy);
                    if (aShip.isVisibleOnMap() & (stealthVIP == null)) {
                        if (maxSize == null || SpaceshipPureFunctions.getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).getSize().getCompareSize() > maxSize.getCompareSize()) {
                            maxSize = SpaceshipPureFunctions.getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).getSize();
                        }
                    }
                }
            }
        }
        return maxSize != null ? maxSize.getDescription() : "";
    }

    /**
     * Find the max ship size string to be shown on the map, ex: "small+civ"
     *
     * @param aPlanet
     * @param aPlayer
     *            find largest size of ships belonging to other players/neutral
     * @return
     */
    public static String getLargestShipSizeOnPlanet(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        String shipSize = "";
        int maxTonnage = 0;
        SpaceShipSize maxSize = null;
        boolean civ = false;
        for (Spaceship aShip : galaxy.getSpaceships()) {
            if ((aShip.getOwner() != aPlayer) & (aShip.getLocation() == aPlanet)) {
                if (aShip.isLookAsCivilian()) {
                    civ = true;
                } else if (aShip.isVisibleOnMap()) {
                    if (maxSize == null || SpaceshipPureFunctions.getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).getSize().getCompareSize() > maxSize.getCompareSize()) {
                        maxSize = SpaceshipPureFunctions.getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).getSize();
                    }
                }
            }
        }
        shipSize = maxSize != null ? maxSize.getDescription() : "";
        if (civ) {
            shipSize += "+civ";
        }
        return shipSize;
    }

    public static int getLargestLookAsMilitaryShipSizeOnPlanet(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        SpaceShipSize maxSize = null;
        for (Spaceship aShip : galaxy.getSpaceships()) {
            if ((aShip.getOwner() == aPlayer) & (aShip.getLocation() == aPlanet)) {
                if (!aShip.isLookAsCivilian()) {
                    VIP stealthVIP = VipPureFunctions.findStealthVIPonShip(aPlanet, aShip, galaxy);
                    if (aShip.isVisibleOnMap() & (stealthVIP == null)) {
                        if (maxSize == null || SpaceshipPureFunctions.getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).getSize().getCompareSize() > maxSize.getCompareSize()) {
                            // Logger.info("aShip name" + aShip.getName());
                            // Logger.info("aShip location" + aShip.getLocation());
                            maxSize = SpaceshipPureFunctions.getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).getSize();
                        }
                    }
                }
            }
        }
        return maxSize != null ? maxSize.getSlots() : -1;
    }

    /**
     *
     * @param player om null leta efter vippar från andra spelare
     * @return
     */
    private static VIPData getOwnVIPsData(Player player, Planet planet, Galaxy g){
        VIPData vipsData = new VIPData();
        vipsData.setPlayerName(player.getName());
        for (VIP aVIP : g.getAllVIPs()) {
            if (aVIP.getBoss() == player){ // leta efter vippar som tillhör spelaren
                if (aVIP.getPlanetLocation() == planet){
                    vipsData.addVipShortName(VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), g.getGameWorld()).getShortName());
                }
            }
        }
        if (vipsData.getVipShortNames().size() == 0){
            vipsData = null;
        }
        return vipsData;
    }

    private static VIPData getOtherVIPsData(Player player, Planet planet, Galaxy g){
        VIPData vipsData = new VIPData();
        for (VIP aVIP : g.getAllVIPs()) {
            if (aVIP.getBoss() != player){ // leta efter vippar som inte tillhör spelaren
                VIPType vipType =VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), g.getGameWorld());
                if (aVIP.getPlanetLocation() == planet){
                    if (vipType.getShowOnOpenPlanet()){
                        vipsData.addVipShortName(vipType.getShortName());
                        vipsData.setPlayerName(aVIP.getBoss().getName()); // förutsätter att alla andra vippar tillhör samma spelare
                    }
                }
            }
        }
        if (vipsData.getVipShortNames().size() == 0){
            vipsData = null;
        }
        return vipsData;
    }

    private static List<String> getBuildingsList(List<Building> buildings){
        List<String> buildingsList = null;
        if (buildings.size() > 0){
            buildingsList = new LinkedList<String>();
            for (Building building : buildings) {
                buildingsList.add(building.getBuildingType().getShortName());
            }
        }
        return buildingsList;
    }

}