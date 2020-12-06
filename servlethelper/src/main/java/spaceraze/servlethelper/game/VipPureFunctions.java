package spaceraze.servlethelper.game;

import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.world.diplomacy.DiplomacyState;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

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
            for (BlackMarketOffer aBlackMarketOffer : aPlayer.getGalaxy().getCurrentOffers()) {
                if(aBlackMarketOffer.isVIP() && aBlackMarketOffer.getVipType().getName().equals(vipType.getName())){
                    constructible = false;
                }
            }
        }

        return constructible;
    }

    public static List<VIP> findAllVIPsOnShip(Spaceship aShip, List<VIP> vips) {
        List<VIP> tempAllVIPs = new LinkedList<>();
        for (VIP tempVIP : vips) {
            if (tempVIP.getShipLocation() != null) {
                Spaceship tempss = tempVIP.getShipLocation();
                if (tempss == aShip) {
                    tempAllVIPs.add(tempVIP);
                }
            }
        }
        return tempAllVIPs;
    }

    public static List<VIP> findAllVIPsOnPlanet(Planet aPlanet, Galaxy galaxy) {
        Vector<VIP> tempAllVIPs = new Vector<VIP>();
        for (VIP vip : galaxy.getAllVIPs()) {
            boolean atPlanet = vip.getPlanetLocation() == aPlanet; // finns vipen vid planeten?
            if (atPlanet) {
                tempAllVIPs.addElement(vip);
            }
        }
        return tempAllVIPs;
    }

    public List<VIP> getAllGovsFromFactionOnPlanet(Planet aPlanet, Faction aFaction, Galaxy galaxy) {
        Logger.finer("called for planet: " + aPlanet.getName() + " and faction: " + aFaction.getName());
        List<VIP> allGovs = new LinkedList<VIP>();
        List<VIP> allVIPsonPlanet = findAllVIPsOnPlanet(aPlanet, galaxy);
        Logger.finest("VIPs found on planet: " + allVIPsonPlanet.size());
        for (int i = 0; i < allVIPsonPlanet.size(); i++) {
            VIP tempVIP = (VIP) allVIPsonPlanet.get(i);
            Logger.finest("VIP found: " + tempVIP.getName());
            if (tempVIP.isGovernor()) {
                Logger.finest("VIP is governor!");
                Logger.finest(tempVIP.getBoss().getFaction().getName() + " equals " + aFaction.getName() + " ?");
                if (tempVIP.getBoss().getFaction().equals(aFaction)) {
                    Logger.finest("Remove VIP!");
                    allGovs.add(tempVIP);
                }
            }
        }
        return allGovs;
    }

    public static boolean isDuellistConflict(Planet aPlanet, VIP VIP1, VIP VIP2, Galaxy galaxy) {
        boolean fight = false;
        if ((VIP1.getLocation() == aPlanet) & (VIP2.getLocation() == aPlanet)) {
            if (VIP1.isDuellist() & VIP2.isDuellist()) {
                if (VIP1.hatesDuellist(VIP2)) {
                    fight = true;
                } else {
                    // only fight if on opposing sides
                    // if (VIP1.getBoss().getFaction() != VIP2.getBoss().getFaction()){
                    if (DiplomacyPureFunctions.hostileDuelists(VIP1.getBoss(), VIP2.getBoss(), aPlanet, galaxy.getDiplomacyStates())) { // use diplomacy to
                        // determine if they
                        // fight
                        if (VIP1.getAlignment().equals(VIP2.getAlignment())) {
                            if (VIP1.getAlignment().isDuelOwnAlignment()) {
                                fight = true;
                            }
                        } else {
                            fight = true;
                        }
                    }
                }
            }
        }
        return fight;
    }

    public static List<VIP> findAllVIPsOnPlanetOrShipsOrTroops(Planet aPlanet, Galaxy galaxy) {
        List<VIP> tempAllVIPs = new LinkedList<>();
        for (VIP vip : galaxy.getAllVIPs()) {
            boolean atPlanet = vip.getPlanetLocation() == aPlanet; // finns vipen vid planeten?
            boolean inShipAtPlanet = false;
            boolean inTroopAtPlanet = false;
            if (!atPlanet && (vip.getPlanetLocation() == null)) { // om inte på planeten...
                Spaceship tempss = vip.getShipLocation();
                if (tempss != null) {
                    if (tempss.getLocation() == aPlanet) { // finns vipen i ett skepp vid planeten?
                        inShipAtPlanet = true;
                    }
                } else { // vip is on troop
                    if (vip.getTroopLocation().isAtPlanet(aPlanet)) {
                        inTroopAtPlanet = true;
                    }
                }
            }
            if (atPlanet || inShipAtPlanet || inTroopAtPlanet) {
                tempAllVIPs.add(vip);
            }
        }
        return tempAllVIPs;
    }

    public static List<VIP> findPlayersVIPsOnPlanetOrShipsOrTroops(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        Logger.info("aPlanet: " + aPlanet);
        Logger.info("aPlayer: " + aPlayer);
        Logger.info("aPlayer: " + aPlayer.getName());
        Logger.finer("findPlayersVIPsOnPlanetOrShipsOrTroops called: " + aPlanet.getName() + " " + aPlayer.getName());
        List<VIP> tempAllVIPs = findAllVIPsOnPlanetOrShipsOrTroops(aPlanet, galaxy);
        List<VIP> vipsAtPlanet = new LinkedList<VIP>();
        Logger.finer("tempAllVIPs: " + tempAllVIPs);
        for (VIP vip : tempAllVIPs) {
            if (vip.getBoss() == aPlayer) {
                vipsAtPlanet.add(vip);
            }
        }
        return vipsAtPlanet;
    }

    public static List<VIP> findPlayersVIPsOnPlanet(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        Logger.finer("findPlayersVIPsOnPlanet called: " + aPlanet.getName() + " " + aPlayer.getName());
        List<VIP> tempAllVIPs = findAllVIPsOnPlanetOrShipsOrTroops(aPlanet, galaxy);
        List<VIP> vipsAtPlanet = new LinkedList<VIP>();
        Logger.finer("tempAllVIPs: " + tempAllVIPs);
        for (VIP vip : tempAllVIPs) {
            if ((vip.getBoss() == aPlayer) & (vip.getPlanetLocation() == aPlanet)) {
                vipsAtPlanet.add(vip);
            }
        }
        return vipsAtPlanet;
    }

    public static boolean isSpiesConflict(Planet aPlanet, VIP VIP1, VIP VIP2, Galaxy galaxy) {
        boolean isOnOwnPlanet = false;
        if ((VIP1.getPlanetLocation() == aPlanet) & (VIP2.getPlanetLocation() == aPlanet)
                & DiplomacyPureFunctions.hostileCounterSpies(VIP1.getBoss(), VIP2.getBoss(), galaxy)) {
            if (VIP1.isCounterSpy()) {
                Planet planetLocation = VIP1.getPlanetLocation();
                if (planetLocation != null) {
                    if (planetLocation.getPlayerInControl() == VIP1.getBoss()) {
                        if (!VIP2.isImmuneToCounterEspionage()) {
                            isOnOwnPlanet = true;
                        }
                    }
                }
            }
            if (VIP2.isCounterSpy()) {
                Planet planetLocation = VIP2.getPlanetLocation();
                if (planetLocation != null) {
                    if (planetLocation.getPlayerInControl() == VIP2.getBoss()) {
                        if (!VIP1.isImmuneToCounterEspionage()) {
                            isOnOwnPlanet = true;
                        }
                    }
                }
            }
        }
        return isOnOwnPlanet;
    }

    public static List<VIP> getExterminators(Planet aPlanet, List<VIP> vips) {
        List<VIP> exterminators = new LinkedList<>();
        for (VIP aVIP : vips) {
            if (aVIP.isExterminator() && (aVIP.getLocation() == aPlanet)) {
                if (aPlanet.getPlayerInControl() == aVIP.getBoss()) { // exterminators can only work on own planets
                    exterminators.add(aVIP);
                }
            }
        }
        return exterminators;
    }

    public static List<VIP> getInfestators(Planet aPlanet, List<VIP> vips) {
        List<VIP> infestators = new LinkedList<>();
        for (VIP aVIP : vips) {
            if (aVIP.isInfestator() && (aVIP.getLocation() == aPlanet)) {
                infestators.add(aVIP);
            }
        }
        return infestators;
    }

    public static boolean hostileAssassin(Player player1, Player player2, Galaxy galaxy){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = DiplomacyPureFunctions.getDiplomacyState(player1, player2, galaxy.getDiplomacyStates());
            if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.CEASE_FIRE)){ // ewar, war, cease fire
                hostile = true;
            }
        }
        return hostile;
    }

    public static boolean isPossibleAssassinationConflict(Planet aPlanet, VIP VIP1, VIP VIP2, Galaxy galaxy) {
        boolean possibleAssassination = false;

        if (hostileAssassin(VIP1.getBoss(), VIP2.getBoss(), galaxy)) {
            if ((VIP1.isAssassin()) & (!VIP1.getHasKilled()) & (VIP1.getLocation() == aPlanet)
                    & (VIP2.getLocation() == aPlanet) & (!VIP2.isWellGuarded())) {
                if (hostileAssassin(VIP1.getBoss(), VIP2.getBoss(), galaxy)) {
                    possibleAssassination = true;
                }
            }
            if ((VIP2.isAssassin()) & (!VIP2.getHasKilled()) & (VIP2.getLocation() == aPlanet)
                    & (VIP1.getLocation() == aPlanet) & (!VIP1.isWellGuarded())) {
                if (hostileAssassin(VIP1.getBoss(), VIP2.getBoss(), galaxy)) {
                    possibleAssassination = true;
                }
            }
        }
        return possibleAssassination;
    }
}
