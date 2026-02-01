package spaceraze.servlethelper.game.player;

import spaceraze.game.*;
import spaceraze.game.report.incomeExpensesReports.IncomeType;
import spaceraze.world.CorruptionPoint;
import spaceraze.game.report.old.TurnInfo;
import spaceraze.map.GalaxyMap;
import spaceraze.servlethelper.game.building.BuildingPureFunctions;
import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.game.diplomacy.DiplomacyState;

import java.util.List;

public class IncomePureFunctions {

    private IncomePureFunctions(){}

    public static int getPlayerIncomeAlien(Player aPlayer, boolean addToIncomeReport, Galaxy galaxy, GalaxyMap galaxyMap, GameWorld gameWorld) {
        int totIncome = 0;
        TurnInfo playerTurnInfo = null;
        if (addToIncomeReport) {
            playerTurnInfo = aPlayer.getTurnInfo();
        }
        for (Planet planet : galaxy.getPlanets()) {
            List<Spaceship> shipsAtPlanet = SpaceshipPureFunctions.getPlayersSpaceshipsOnPlanet(aPlayer, planet, galaxy.getSpaceships());
            if (planet.getPlayerInControl() == aPlayer) {
                int tmpInc = getIncomeAlien(planet, aPlayer.getOpenPlanetBonus(),
                        aPlayer.getClosedPlanetBonus(), playerTurnInfo, galaxyMap)
                        + getVIPIncomeBonus(aPlayer, planet, playerTurnInfo, galaxy.getAllVIPs(), gameWorld, galaxyMap);
                Logger.finer("alien base income: " + tmpInc);
                totIncome += tmpInc;
                // add income bonus for buildings.

                totIncome += BuildingPureFunctions.getPlanetBuildingsBonus(planet, playerTurnInfo, gameWorld, galaxyMap);
                Logger.finest("alien base income: " + tmpInc);
            }
            totIncome += getShipIncome(shipsAtPlanet, planet, playerTurnInfo, galaxy, galaxyMap);
        }
        return totIncome;
    }

    public static int getPlayerIncomeWithoutCorruption(Player aPlayer, boolean addToIncomeReport, Galaxy galaxy, GalaxyMap galaxyMap, GameWorld gameWorld) {
        int income;
        if (GameWorldHandler.getFactionByUuid(aPlayer.getFactionUuid(), gameWorld).isAlien()) {
            income = getPlayerIncomeAlien(aPlayer, addToIncomeReport, galaxy, galaxyMap, gameWorld);
        } else {
            income = getPlayerIncomeNonAlien(aPlayer, addToIncomeReport, galaxy, galaxyMap, gameWorld);
        }
        return income;
    }

    public static int getPlayerIncome(Player aPlayer, boolean addToIncomeReport, GalaxyMap galaxyMap, GameWorld gameWorld, Galaxy galaxy) {
        int income = getPlayerIncomeWithoutCorruption(aPlayer, addToIncomeReport, galaxy, galaxyMap, gameWorld);
        // System.out.println("getPlayerIncome, income1: " + income);
        income = getIncomeAfterCorruption(income, PlayerPureFunctions.getCorruptionPoint(gameWorld, aPlayer.getFactionUuid(), aPlayer.getCorruptionPointUuid()));
        // System.out.println("getPlayerIncome, income2: " + income);
        return income;
    }

    public static int getIncomeAfterCorruption(int anIncome, CorruptionPoint corruptionPoint){
        int tmpIncomeAfterCorr;
        if (corruptionPoint == null){
            tmpIncomeAfterCorr = anIncome;
        }else{
            int firstLimit = corruptionPoint.getIncomeLimit();
            if (anIncome > firstLimit){
                tmpIncomeAfterCorr = firstLimit;
                // call breakpoints
                tmpIncomeAfterCorr += getIncome(anIncome, corruptionPoint);
            }else{
                tmpIncomeAfterCorr = anIncome;
            }
        }
        return tmpIncomeAfterCorr;
    }

    public static int getLostToCorruption(int anIncome, CorruptionPoint corruptionPoint){
        return anIncome - getIncomeAfterCorruption(anIncome, corruptionPoint);
    }

    /**
     * Compute income after corruption between this and the next breakpoint.
     * If there is another breakpoint, call it and add it's income to the returned result.
     */
    private static int getIncome(int income, CorruptionPoint corruptionPoint){
        int incomeAfterCorruption;
        if (corruptionPoint.getNextBreakpoint() != null){
            int nextLimit = corruptionPoint.getNextBreakpoint().getIncomeLimit();
            if (income < nextLimit){
                double tmpInc = (income - corruptionPoint.getIncomeLimit()) * corruptionPoint.getIncomePercentage();
                incomeAfterCorruption = (int)Math.round(tmpInc);
            }else{
                double tmpInc = (nextLimit - corruptionPoint.getIncomeLimit()) * corruptionPoint.getIncomePercentage();
                incomeAfterCorruption = (int)Math.round(tmpInc);
                incomeAfterCorruption += getIncome(income, corruptionPoint.getNextBreakpoint());
            }
        }else{
            double tmpInc = (income - corruptionPoint.getIncomeLimit()) * corruptionPoint.getIncomePercentage();
            incomeAfterCorruption = (int)Math.round(tmpInc);
        }
        return incomeAfterCorruption;
    }

    private static int getPlayerIncomeNonAlien(Player aPlayer, boolean addToIncomeReport, Galaxy galaxy, GalaxyMap galaxyMap, GameWorld gameWorld) {
        int totIncome = 0;
        TurnInfo playerTurnInfo = null;
        if (addToIncomeReport) {
            playerTurnInfo = aPlayer.getTurnInfo();
        }
        for (Planet planet : galaxy.getPlanets()) {
            List<Spaceship> shipsAtPlanet = SpaceshipPureFunctions.getPlayersSpaceshipsOnPlanet(aPlayer, planet, galaxy.getSpaceships());
            // Logger.finest("shipsAtPlanet " + shipsAtPlanet.size());
            if (planet.getPlayerInControl() == aPlayer) {
                Logger.finest("tempPlanet " + planet.getMapPlanetUuid());
                int tmpInc = getIncome(planet, aPlayer.getOpenPlanetBonus(),
                        aPlayer.getClosedPlanetBonus(), playerTurnInfo, galaxyMap)
                        + getVIPIncomeBonus(aPlayer, planet, playerTurnInfo, galaxy.getAllVIPs(), gameWorld, galaxyMap);
                Logger.finest("tmpInc " + tmpInc);
                totIncome = totIncome + tmpInc;
                Logger.finest("totIncome1 " + totIncome);
                // add income bonus for Buildings
                Logger.finest("getPlanetBuildingsBonus(tempPlanet) f�re");
                totIncome = totIncome + BuildingPureFunctions.getPlanetBuildingsBonus(planet, playerTurnInfo, gameWorld, galaxyMap);
                Logger.finest("totIncome2 " + totIncome);
                Logger.finest("getPlanetBuildingsBonus(tempPlanet) efter");
                Logger.finest("totIncome3 " + totIncome);
            }
            totIncome += getShipIncome(shipsAtPlanet, planet, playerTurnInfo, galaxy, galaxyMap);
        }
        Logger.finest("getPlayerIncomeNonAlien totIncome return " + totIncome);
        return totIncome;
    }

    private static int getVIPIncomeBonus(Player aPlayer, Planet aPlanet, TurnInfo playerTurnInfo, List<VIP> vips, GameWorld gameWorld, GalaxyMap galaxyMap) {
        int incomeBonus = 0;
        if (!aPlanet.isBesieged()) {
            VIP tempVIP = findVIPEconomicBonus(aPlanet, aPlayer, vips, gameWorld);
            if (tempVIP != null) {
                VIPType vipType = VipPureFunctions.getVipTypeByUuid(tempVIP.getTypeUuid(), gameWorld);
                if (aPlanet.isOpen()) {
                    incomeBonus = vipType.getOpenIncBonus();
                    if (playerTurnInfo != null)
                        playerTurnInfo.addToLatestIncomeReport(IncomeType.VIP, vipType.getName() + " open planet bonus",
                                PlanetPureFunctions.getPlanetName(galaxyMap, aPlanet.getMapPlanetUuid()), incomeBonus);
                } else {
                    incomeBonus = vipType.getClosedIncBonus();
                    if (playerTurnInfo != null)
                        playerTurnInfo.addToLatestIncomeReport(IncomeType.VIP,
                                vipType.getName() + " closed planet bonus", PlanetPureFunctions.getPlanetName(galaxyMap, aPlanet.getMapPlanetUuid()), incomeBonus);
                }
            }
        }
        return incomeBonus;
    }

    public static VIP findVIPEconomicBonus(Planet aPlanet, Player aPlayer, List<VIP> vips, GameWorld gameWorld) {
        VIP foundVIP = null;
        int bonus = 0;
        for (VIP vip : vips) {
            VIPType vipType = VipPureFunctions.getVipTypeByUuid(vip.getTypeUuid(), gameWorld);
            if (vip.getBoss() == aPlayer && vip.getPlanetLocation() == aPlanet) {
                if (aPlanet.isOpen()) {
                    if (vipType.getOpenIncBonus() > bonus) {
                        foundVIP = vip;
                        bonus = vipType.getOpenIncBonus();
                    }
                } else {
                    if (vipType.getClosedIncBonus() > bonus) {
                        foundVIP = vip;
                        bonus = vipType.getClosedIncBonus();
                    }
                }
            }
        }
        return foundVIP;
    }

    private static int getShipIncome(List<Spaceship> shipsAtPlanet, Planet aPlanet, TurnInfo playerTurnInfo, Galaxy galaxy, GalaxyMap galaxyMap) {
        int tmpIncome = 0;
        Spaceship aShip = null;
        for (Spaceship spaceship : shipsAtPlanet) {
            int shipIncome = getShipIncome(spaceship, aPlanet, galaxy);
            if (shipIncome > tmpIncome) {
                tmpIncome = shipIncome;
                aShip = spaceship;
            }
        }
        if (tmpIncome > 0 && playerTurnInfo != null) {
            playerTurnInfo.addToLatestIncomeReport(IncomeType.SHIP, aShip.getName(), PlanetPureFunctions.getPlanetName(galaxyMap, aPlanet.getMapPlanetUuid()), tmpIncome);
        }
        return tmpIncome;
    }

    private static int getShipIncome(Spaceship spaceship, Planet aPlanet, Galaxy galaxy) {
        int income = 0;
        Player planetPlayer = aPlanet.getPlayerInControl();
        Player shipPlayer = spaceship.getOwner(); // should never be null
        if (aPlanet.isOpen()) {
            if (planetPlayer == null) { // neutral planet
                income = spaceship.getIncNeutralOpenBonus();
            } else { // planet not neutral
                if (planetPlayer == shipPlayer) {
                    spaceship.getIncOwnOpenBonus();
                } else {
                    DiplomacyState state = DiplomacyPureFunctions.getDiplomacyState(shipPlayer, planetPlayer, galaxy.getDiplomacyStates());
                    DiplomacyLevel level = state.getCurrentLevel();
                    if ((level == DiplomacyLevel.CONFEDERACY) || (level == DiplomacyLevel.ALLIANCE) || (level == DiplomacyLevel.LORD)) {
                        spaceship.getIncFriendlyOpenBonus();
                    } else if ((level == DiplomacyLevel.CEASE_FIRE) || (level == DiplomacyLevel.PEACE)) {
                        spaceship.getIncNeutralOpenBonus();
                    } else if ((level == DiplomacyLevel.WAR) || (level == DiplomacyLevel.ETERNAL_WAR)) {
                        spaceship.getIncEnemyOpenBonus();
                    }
                }
            }
        } else { // closed planet
            if (planetPlayer == null) { // neutral planet
                income = spaceship.getIncNeutralClosedBonus();
            } else { // planet not neutral
                if (planetPlayer == shipPlayer) {
                    spaceship.getIncOwnClosedBonus();
                } else {
                    DiplomacyState state = DiplomacyPureFunctions.getDiplomacyState(shipPlayer, planetPlayer, galaxy.getDiplomacyStates());
                    DiplomacyLevel level = state.getCurrentLevel();
                    if ((level == DiplomacyLevel.CONFEDERACY) || (level == DiplomacyLevel.ALLIANCE) || (level == DiplomacyLevel.LORD)) {
                        spaceship.getIncFriendlyClosedBonus();
                    } else if ((level == DiplomacyLevel.CEASE_FIRE) || (level == DiplomacyLevel.PEACE)) {
                        spaceship.getIncNeutralClosedBonus();
                    } else if ((level == DiplomacyLevel.WAR) || (level == DiplomacyLevel.ETERNAL_WAR)) {
                        spaceship.getIncEnemyClosedBonus();
                    }
                }
            }
        }
        return income;
    }

    public static int getIncome(Planet planet, int openbonus, int closedbonus, TurnInfo playerTurnInfo, GalaxyMap galaxyMap){
//      LoggingHandler.finer("getIncome " + name + ": " + openbonus + " " + closedbonus);
        String planetName = PlanetPureFunctions.getPlanetName(galaxyMap, planet.getMapPlanetUuid());
        int tempIncome = 0;
        if (!planet.isBesieged()){
            if (planet.getPopulation() > 0){
                tempIncome = planet.getPopulation();
                if (playerTurnInfo != null) {
                    playerTurnInfo.addToLatestIncomeReport(IncomeType.PLANET, "Planet production", planetName, planet.getPopulation());
                }
                if (planet.isOpen()){
                    tempIncome = tempIncome + 2 + openbonus;
                    if ((openbonus + 2) > 0){
                        if (playerTurnInfo != null) playerTurnInfo.addToLatestIncomeReport(IncomeType.OPEN_BONUS, "Planet open bonus", planetName, 2 + openbonus);
                    }
                }else{
                    tempIncome = tempIncome + closedbonus;
                    if (closedbonus > 0){
                        if (playerTurnInfo != null) playerTurnInfo.addToLatestIncomeReport(IncomeType.CLOSED_BONUS, "Planet closed bonus", planetName, closedbonus);
                    }
                }
            }
        }
        return tempIncome;
    }

    public static int getIncomeAlien(Planet planet, int openbonus, int closedbonus, TurnInfo playerTurnInfo, GalaxyMap galaxyMap){
//    	LoggingHandler.finer("getIncomeAlien " + name + ": " + closedbonus);
        String planetName = PlanetPureFunctions.getPlanetName(galaxyMap, planet.getMapPlanetUuid());
        int tempIncome = 0;
        if (!planet.isBesieged()){
            if (planet.getResistance() > 0){
                tempIncome = planet.getResistance();
                if (playerTurnInfo != null){
                    playerTurnInfo.addToLatestIncomeReport(IncomeType.PLANET, "Planet resistance", planetName, planet.getPopulation());
                }
                if (planet.isOpen()){
                    tempIncome = tempIncome + 2 + openbonus;
                    if (playerTurnInfo != null) playerTurnInfo.addToLatestIncomeReport(IncomeType.OPEN_BONUS, "Planet open bonus", planetName, 2 + openbonus);
                }else{
                    tempIncome = tempIncome + closedbonus;
                    if (playerTurnInfo != null) playerTurnInfo.addToLatestIncomeReport(IncomeType.CLOSED_BONUS, "Planet closed bonus", planetName, closedbonus);
                }
            }
        }
        return tempIncome;
    }

    public static int getUpkeep(Planet planet, GameWorld gameWorld){
        int tempUpkeep = 0;
        if (planet.getPlayerInControl() != null && GameWorldHandler.getFactionByUuid(planet.getPlayerInControl().getFactionUuid(), gameWorld).isAlien()){
            tempUpkeep = planet.getResistance();
        }else{
            if (planet.getPopulation() > 0){
                tempUpkeep = planet.getPopulation();
            }
        }
        return tempUpkeep;
    }

}
