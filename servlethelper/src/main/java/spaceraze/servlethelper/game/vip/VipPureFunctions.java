package spaceraze.servlethelper.game.vip;

import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.util.general.Functions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.world.diplomacy.DiplomacyState;
import spaceraze.world.orders.Orders;
import spaceraze.world.orders.VIPMovement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class VipPureFunctions {

    private VipPureFunctions(){}

    public static boolean isConstructable(Player aPlayer, Galaxy galaxy, VIPType vipType){
        boolean constructible =  true;

        if((vipType.isWorldUnique() && vipTypeExist(vipType, null, null, galaxy)) || (vipType.isFactionUnique() && vipTypeExist(vipType, aPlayer.getFaction(), null, galaxy)) || (vipType.isPlayerUnique() && vipTypeExist(vipType, null, aPlayer, galaxy))){
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

    public static List<VIP> getAllGovsFromFactionOnPlanet(Planet aPlanet, Faction aFaction, Galaxy galaxy) {
        Logger.finer("called for planet: " + aPlanet.getName() + " and faction: " + aFaction.getName());
        List<VIP> allGovs = new LinkedList<VIP>();
        List<VIP> allVIPsonPlanet = findAllVIPsOnPlanet(aPlanet, galaxy);
        Logger.finest("VIPs found on planet: " + allVIPsonPlanet.size());
        for (int i = 0; i < allVIPsonPlanet.size(); i++) {
            VIP tempVIP = allVIPsonPlanet.get(i);
            VIPType vipType = VipPureFunctions.getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld());
            Logger.finest("VIP found: " + vipType.getName());
            if (vipType.isGovernor()) {
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
        if (getLocation(VIP1) == aPlanet && getLocation(VIP2) == aPlanet) {
            VIPType vipType1 = VipPureFunctions.getVipTypeByKey(VIP1.getTypeKey(), galaxy.getGameWorld());
            VIPType vipType2 = VipPureFunctions.getVipTypeByKey(VIP2.getTypeKey(), galaxy.getGameWorld());
            if (vipType1.getDuellistSkill() > 0 && vipType2.getDuellistSkill() > 0) {
                if (hatesDuellist(VIP1, galaxy.getGameWorld(), VIP2)) {
                    fight = true;
                } else {
                    // only fight if on opposing sides
                    // if (VIP1.getBoss().getFaction() != VIP2.getBoss().getFaction()){
                    if (DiplomacyPureFunctions.hostileDuelists(VIP1.getBoss(), VIP2.getBoss(), aPlanet, galaxy.getDiplomacyStates())) { // use diplomacy to
                        // determine if they
                        // fight
                        if (vipType1.getAlignment().equals(vipType2.getAlignment())) {
                            if (vipType1.getAlignment().isDuelOwnAlignment()) {
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
                    if (TroopPureFunctions.isAtPlanet(vip.getTroopLocation(), aPlanet)) {
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
            VIPType vipType1 = VipPureFunctions.getVipTypeByKey(VIP1.getTypeKey(), galaxy.getGameWorld());
            VIPType vipType2 = VipPureFunctions.getVipTypeByKey(VIP2.getTypeKey(), galaxy.getGameWorld());
            if (vipType1.isCounterSpy()) {
                Planet planetLocation = VIP1.getPlanetLocation();
                if (planetLocation != null) {
                    if (planetLocation.getPlayerInControl() == VIP1.getBoss()) {
                        if (!vipType2.isImmuneToCounterEspionage()) {
                            isOnOwnPlanet = true;
                        }
                    }
                }
            }
            if (vipType2.isCounterSpy()) {
                Planet planetLocation = VIP2.getPlanetLocation();
                if (planetLocation != null) {
                    if (planetLocation.getPlayerInControl() == VIP2.getBoss()) {
                        if (!vipType1.isImmuneToCounterEspionage()) {
                            isOnOwnPlanet = true;
                        }
                    }
                }
            }
        }
        return isOnOwnPlanet;
    }

    public static List<VIP> getExterminators(Planet aPlanet, List<VIP> vips, GameWorld gameWorld) {
        List<VIP> exterminators = new LinkedList<>();
        for (VIP aVIP : vips) {
            if (VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld).getExterminator() > 0 && getLocation(aVIP) == aPlanet) {
                if (aPlanet.getPlayerInControl() == aVIP.getBoss()) { // exterminators can only work on own planets
                    exterminators.add(aVIP);
                }
            }
        }
        return exterminators;
    }

    public static List<VIP> getInfestators(Planet aPlanet, List<VIP> vips, GameWorld gameWorld) {
        List<VIP> infestators = new LinkedList<>();
        for (VIP aVIP : vips) {
            if (getVipTypeByKey(aVIP.getTypeKey(), gameWorld).isInfestate() && getLocation(aVIP) == aPlanet) {
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
            if (getVipTypeByKey(VIP1.getTypeKey(), galaxy.getGameWorld()).getAssassination() > 0 && !VIP1.getHasKilled() && getLocation(VIP1) == aPlanet
                    && getLocation(VIP2) == aPlanet && !getVipTypeByKey(VIP2.getTypeKey(), galaxy.getGameWorld()).isWellGuarded()) {
                if (hostileAssassin(VIP1.getBoss(), VIP2.getBoss(), galaxy)) {
                    possibleAssassination = true;
                }
            }
            if (getVipTypeByKey(VIP2.getTypeKey(), galaxy.getGameWorld()).getAssassination() > 0 && !VIP2.getHasKilled() && getLocation(VIP2) == aPlanet
                    && getLocation(VIP1) == aPlanet && !getVipTypeByKey(VIP1.getTypeKey(), galaxy.getGameWorld()).isWellGuarded()) {
                if (hostileAssassin(VIP1.getBoss(), VIP2.getBoss(), galaxy)) {
                    possibleAssassination = true;
                }
            }
        }
        return possibleAssassination;
    }

    public static boolean isFTLMasterOnShip(Spaceship aShip, Galaxy galaxy) {
        boolean found = false;
        for (VIP vip : galaxy.getAllVIPs()) {
            if (vip.getShipLocation() == aShip) {
                if (VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), galaxy.getGameWorld()).isFTLbonus()) {
                    Orders orders = aShip.getOwner().getOrders();
                    if (VIPWillStay(vip, aShip.getOwner().getOrders())) {
                        found = true;
                    }
                }
            }
        }
        return found;
    }

    // leta igenom alla VIPMoves och kolla om någon av dem förflyttar iväg denna vip
    public static boolean VIPWillStay(VIP vip, Orders orders) {
        boolean vipStays = true;
        int i = 0;
        while ((i < orders.getVIPMoves().size()) & (vipStays)) {
            VIPMovement tempVIPMove = orders.getVIPMoves().get(i);
            if (vip.getKey().equalsIgnoreCase(tempVIPMove.getVipKey())) {
                vipStays = false;
            } else {
                i++;
            }
        }
        return vipStays;
    }

    public static VIP findVIPTechBonus(Planet aPlanet, Player aPlayer, Orders orders, Galaxy galaxy) {
        VIP foundVIP = null;
        int bonus = 0;
        for (VIP vip : galaxy.getAllVIPs()) {
            if ((vip.getBoss() == aPlayer) & (vip.getPlanetLocation() == aPlanet)) {
                VIPType vipType = VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), galaxy.getGameWorld());
                if (vipType.getTechBonus() > bonus) {
                    if (VIPWillStay(vip, orders)) {
                        foundVIP = vip;
                        bonus = vipType.getTechBonus();
                    }
                }
            }
        }
        return foundVIP;
    }

    public static VIP findVIPShipBuildBonus(Planet aPlanet, Player aPlayer, Orders orders, Galaxy galaxy) {
        VIP foundVIP = null;
        int bonus = 0;
        for (VIP vip : galaxy.getAllVIPs()) {
            if ((vip.getBoss() == aPlayer) & (vip.getPlanetLocation() == aPlanet)) {
                VIPType vipType = VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), galaxy.getGameWorld());
                if (vipType.getShipBuildBonus() > bonus) {
                    if (VIPWillStay(vip, orders)) {
                        foundVIP = vip;
                        bonus = vipType.getShipBuildBonus();
                    }
                }
            }
        }
        return foundVIP;
    }

    public static VIP findVIPTroopBuildBonus(Planet aPlanet, Player aPlayer, Orders orders, Galaxy galaxy) {
        VIP foundVIP = null;
        int bonus = 0;
        for (VIP vip : galaxy.getAllVIPs()) {
            if ((vip.getBoss() == aPlayer) & (vip.getPlanetLocation() == aPlanet)) {
                VIPType vipType = VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), galaxy.getGameWorld());
                if (vipType.getTroopBuildBonus() > bonus) {
                    if (VIPWillStay(vip, orders)) {
                        foundVIP = vip;
                        bonus = vipType.getTroopBuildBonus();
                    }
                }
            }
        }
        return foundVIP;
    }

    public static VIP findVIPBuildingBuildBonus(Planet aPlanet, Player aPlayer, Orders orders, Galaxy galaxy) {
        VIP foundVIP = null;
        int bonus = 0;
        for (VIP vip : galaxy.getAllVIPs()) {
            if ((vip.getBoss() == aPlayer) & (vip.getPlanetLocation() == aPlanet)) {
                VIPType vipType = VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), galaxy.getGameWorld());
                if (vipType.getBuildingBuildBonus() > bonus) {
                    if (VIPWillStay(vip, orders)) {
                        foundVIP = vip;
                        bonus = vipType.getBuildingBuildBonus();
                    }
                }
            }
        }
        return foundVIP;
    }

    public static VIP findStealthVIPonShip(Planet aPlanet, Spaceship aShip, Galaxy galaxy) {
        VIP foundVIP = null;
        int index = 0;
        while ((foundVIP == null) & (index < galaxy.getAllVIPs().size())) {
            VIP aVIP = galaxy.getAllVIPs().get(index);
            if (aVIP.getShipLocation() == aShip) {
                VIPType vipType = VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), galaxy.getGameWorld());
                if (vipType.isStealth()) {
                    foundVIP = aVIP;
                }
            }
            if (foundVIP == null) {
                index++;
            }
        }
        return foundVIP;
    }

    public static List<VIP> findAllVIPsOnTroop(Troop aTroop, List<VIP> vips) {
        List<VIP> tempAllVIPs = new ArrayList<>();
        for (VIP vip : vips) {
            if (vip.getTroopLocation() != null) {
                Troop tempTroop = vip.getTroopLocation();
                if (tempTroop == aTroop) {
                    tempAllVIPs.add(vip);
                }
            }
        }
        return tempAllVIPs;
    }

    public static VIPType getRandomVIPType(Galaxy galaxy) {
        VIPType returnType = null;
        Logger.finer("getRandomVIPType()");
        int totFrequencySum = getTotalVIPFrequencySum(galaxy);
        Logger.finer(String.valueOf(totFrequencySum));
        int freqValue = Functions.getRandomInt(0, totFrequencySum - 1);
        Logger.finer(String.valueOf(freqValue));
        returnType = getVipFromFrequency(freqValue, galaxy);
        Logger.finer(returnType.getName());
        return returnType;
    }

    private static int getTotalVIPFrequencySum(Galaxy galaxy) {
        int total = 0;
        for (VIPType aVipType : galaxy.getGameWorld().getVipTypes()) {
            if (isReadyToUseInBlackMarket(aVipType, galaxy)) {
                total = total + aVipType.getFrequency().getFrequency();
            }
        }
        return total;
    }

    private static VIPType getVipFromFrequency(int freqValue, Galaxy galaxy) {
        VIPType aVipType = null;
        int tmpFreqSum = 0;
        VIPType tmpVipType = null;
        int counter = 0;
        Logger.finer(String.valueOf(freqValue));
        while (aVipType == null) {
            tmpVipType = galaxy.getGameWorld().getVipTypes().get(counter);
            if (isReadyToUseInBlackMarket(tmpVipType, galaxy)) {
                tmpFreqSum = tmpFreqSum + tmpVipType.getFrequency().getFrequency();
                Logger.finest("tmpFreqSum: " + tmpFreqSum);
                if (tmpFreqSum > freqValue) {
                    aVipType = tmpVipType;
                }
            }
            counter++;
        }
        return aVipType;
    }

    public static boolean isReadyToUseInBlackMarket(VIPType vipType, Galaxy aGalaxy) {
        boolean constructible = false;
        if (!vipType.isGovernor()) {
            if (!vipType.isPlayerUnique() && !vipType.isFactionUnique()) {
                if (vipType.isWorldUnique() && !isWorldUniqueBuild(vipType, aGalaxy)) {
                    boolean isAlreadyAoffer = false;
                    for (BlackMarketOffer aBlackMarketOffer : aGalaxy.getCurrentOffers()) {
                        if (aBlackMarketOffer.isVIP() && aBlackMarketOffer.getVipType().getName().equals(vipType.getName())) {
                            isAlreadyAoffer = true;
                        }
                    }

                    if (!isAlreadyAoffer) {
                        boolean haveBuildingOrder = false;
                        for (Player tempPlayer : aGalaxy.getPlayers()) {
                            if (tempPlayer.getOrders().haveVIPTypeBuildOrder(vipType)) {
                                haveBuildingOrder = true;
                            }
                        }
                        if (!haveBuildingOrder) {
                            constructible = true;
                        }
                    }
                } else {
                    constructible = true;
                }
            }
        }
        return constructible;
    }

    public static VIPType getVipTypeByName(String name, GameWorld gameWorld){
        return gameWorld.getVipTypes().stream().filter(vipType1 -> vipType1.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public static VIPType getVipTypeByKey(String key, GameWorld gameWorld){
        return gameWorld.getVipTypes().stream().filter(vipType -> vipType.getKey().equalsIgnoreCase(key)).findAny().orElse(null);
    }

    public static boolean isFactionUniqueBuild(VIPType vipType, Player aPlayer, Galaxy galaxy) {
        return vipTypeExist(vipType, aPlayer.getFaction(), null, galaxy);
    }

    public static boolean isPlayerUniqueBuild(VIPType vipType,Player aPlayer, Galaxy galaxy) {
        return vipTypeExist(vipType, null, aPlayer, galaxy);
    }

    public static boolean isWorldUniqueBuild(VIPType vipType, Galaxy galaxy) {
        return vipTypeExist(vipType, null, null, galaxy);
    }

    public static boolean vipTypeExist(VIPType aVIPType, Faction aFaction, Player aPlayer, Galaxy galaxy) {
        boolean exist = false;
        List<VIP> vipsToCheck;
        if (aPlayer != null) {// playerUnique
            vipsToCheck = getPlayersVips(aPlayer, galaxy);
        } else if (aFaction != null) {// factionUnique
            vipsToCheck = new ArrayList<VIP>();
            for (Player tempPlayer : galaxy.getPlayers()) {
                if (tempPlayer.getFaction().getName().equals(aFaction.getName())) {
                    vipsToCheck.addAll(getPlayersVips(tempPlayer, galaxy));
                }
            }
        } else {// worldUnique
            vipsToCheck = galaxy.getAllVIPs();
        }

        for (VIP tempVIP : vipsToCheck) {
            if (tempVIP.getTypeKey().equalsIgnoreCase(aVIPType.getKey())) {
                exist = true;
            }
        }

        return exist;
    }

    public static List<VIP> getPlayersVips(Player aPlayer, Galaxy galaxy) {
        return galaxy.getAllVIPs().stream().filter(vip -> vip.getBoss() == aPlayer).collect(Collectors.toList());
    }

    public static VIP findVIP(String key, Galaxy galaxy) {
        return galaxy.getAllVIPs().stream().filter(vip -> vip.getKey().equalsIgnoreCase(key)).findAny().orElseThrow();
    }

    public static List<VIPMovement> getVIPMoves(Planet aPlanet, Orders orders) {
        List<VIPMovement> vipMoves = new LinkedList<VIPMovement>();
        for (VIPMovement aVIPMovement : orders.getVIPMoves()) {
            if (aPlanet.getName().equals(aVIPMovement.getPlanetDestination())) {
                vipMoves.add(aVIPMovement);
            }
        }
        return vipMoves;
    }

    public static boolean isLandBattleVip(VIPType vipType) {
        boolean battleVIP = false;
        if (vipType.getLandBattleGroupAttackBonus() > 0) {
            battleVIP = true;
        }
        return battleVIP;
    }

    public static List<VIPType> getLandBattleVIPtypes(GameWorld gameWorld){
        Logger.finer("getLandBattleVIPtypes()");
        List<VIPType> battleVips = new ArrayList<VIPType>();
        for (VIPType aVIPtype : gameWorld.getVipTypes()) {
            if (isLandBattleVip(aVIPtype)){
                battleVips.add(aVIPtype);
            }
        }
        return battleVips;
    }

    public static boolean isSpaceBattleVip(VIPType vipType) {
        boolean isBattleVIP = false;
        if (vipType.getInitBonus() > 0) {
            isBattleVIP = true;
        } else if (vipType.getInitSupportBonus() > 0) {
            isBattleVIP = true;
        } else if (vipType.getInitDefence() > 0) {
            isBattleVIP = true;
        } else if (vipType.getInitFighterSquadronBonus() > 0) {
            isBattleVIP = true;
        } else if (vipType.getAimBonus() > 0) {
            isBattleVIP = true;
        }
        return isBattleVIP;
    }

    public static List<VIPType> getSpaceBattleVipTypes(GameWorld gameWorld){
        return gameWorld.getVipTypes().stream().filter(vipType -> isSpaceBattleVip(vipType)).collect(Collectors.toList());
    }

    public static String getDuellistSkillString(VIPType vipType) {
        String skillStr = "None";
        if (vipType.getDuellistSkill() == VIPType.DUELLIST_APPRENTICE) {
            skillStr = "Apprentice";
        } else if (vipType.getDuellistSkill() == VIPType.DUELLIST_VETERAN) {
            skillStr = "Average";
        } else if (vipType.getDuellistSkill() == VIPType.DUELLIST_MASTER) {
            skillStr = "Master";
        }
        return skillStr;
    }

    public static List<String> getAbilitiesStrings(VIPType vipType) {  // add info about how easily/hard the VIP dies?   And info about where this VIP may travel?
        List<String> allStrings = new LinkedList<String>();
        if (vipType.getAssassination() > 0) {
            allStrings.add("Assassin: " + vipType.getAssassination() + "%");
        }
        if (vipType.getCounterEspionage() > 0) {
            allStrings.add("Counter-espionage: " + vipType.getCounterEspionage() + "%");
        }
        if (vipType.isSpying()) {
            allStrings.add("Spy");
        }
        if (vipType.getInitBonus() > 0) {
            allStrings.add("Initiative bonus: " + vipType.getInitBonus() + "%");
        }
        if (vipType.getInitSupportBonus() > 0) {
            allStrings.add("Initiative support bonus: " + vipType.getInitSupportBonus() + "%");
        }
        if (vipType.getInitDefence() > 0) {
            allStrings.add("Initiative defence: " + vipType.getInitDefence() + "%");
        }
        if (vipType.getPsychWarfareBonus() > 0) {
            allStrings.add("Psych warfare bonus: " + vipType.getPsychWarfareBonus());
        }

        if (vipType.getShipBuildBonus() > 0) {
            allStrings.add("Ships build bonus: " + vipType.getShipBuildBonus() + "%");
        }
        if (vipType.getTroopBuildBonus() > 0) {
            allStrings.add("Troops build bonus: " + vipType.getTroopBuildBonus() + "%");
        }
        if (vipType.getBuildingBuildBonus() > 0) {
            allStrings.add("Buidings build bonus: " + vipType.getBuildingBuildBonus() + "%");
        }
        if (vipType.getTechBonus() > 0) {
            allStrings.add("Tech bonus: " + vipType.getTechBonus() + "%");
        }
        if (vipType.getOpenIncBonus() > 0) {
            allStrings.add("Open planet income bonus: " + vipType.getOpenIncBonus());
        }
        if (vipType.getClosedIncBonus() > 0) {
            allStrings.add("Closed planet income bonus: " + vipType.getClosedIncBonus());
        }
        if (vipType.isCanVisitEnemyPlanets()) {
            allStrings.add("Can visit enemy planets");
        }
        if (vipType.isCanVisitNeutralPlanets()) {
            allStrings.add("Can visit neutral planets");
        }
        if (vipType.getDuellistSkill() > 0) {
            allStrings.add("Duellist, skill: " + getDuellistSkillString(vipType));
        }
        if (vipType.isHardToKill()) {
            allStrings.add("Hard to kill: not killed by destroyed ships or lost planets");
        }
        if (vipType.isWellGuarded()) {
            allStrings.add("Cannot be assassinated");
        }
        if (vipType.isGovernor()) {
            allStrings.add("Governor");
        }
        if (vipType.isFTLbonus()) {
            allStrings.add("Boosts range of ships");
        }
        if (vipType.isDiplomat()) {
            allStrings.add("Diplomat: can persuade neutral planets to join you (only non-alien factions)");
        }
        if (vipType.isInfestate()) {
            allStrings.add("Infestate: can infestate planets to join you");
        }
        if (vipType.getShowOnOpenPlanet()) {
            allStrings.add("Visible on open planets");
        }
        if (vipType.isImmuneToCounterEspionage()) {
            allStrings.add("Immune to enemy counter-espionage");
        }
        if (vipType.getInitFighterSquadronBonus() > 0) {
            allStrings.add("Squadron initiative bonus: " + vipType.getInitFighterSquadronBonus() + "%");
        }
        if (vipType.getResistanceBonus() > 0) {
            allStrings.add("Resistance bonus: " + vipType.getResistanceBonus());
        }
        if (vipType.getExterminator() > 0) {
            allStrings.add("Exterminator: " + vipType.getExterminator() + "%");
        }
//    if (troopAttacksBonus > 0){
//    	allStrings.add("Troop number attacks bonus: +" + troopAttacksBonus);
//    }
        if (vipType.getLandBattleGroupAttackBonus() > 0) {
            allStrings.add("Troops attack bonus: +" + vipType.getLandBattleGroupAttackBonus());
        }
        if (vipType.isStealth()) {
            allStrings.add("Makes a ship invisible on the map");
        }
        if (vipType.getBombardmentBonus() > 0) {
            allStrings.add("Increase bombardment with +" + vipType.getBombardmentBonus() + " for a fleet (with a bombardment of at least 1)");
        }
        if (vipType.isAttackScreenedSquadron()) {
            allStrings.add("Enables a squadron to attack screened enemy ships");
        }
        if (vipType.isAttackScreenedCapital()) {
            allStrings.add("Enables a capital ship to attack screened enemy ships");
        }
        if (vipType.isPlanetarySurvey()) {
            allStrings.add("On a ship, act as spy on closed planets");
        }
        if (vipType.isWorldUnique()) {
            allStrings.add("Is World Unique");
        }
        if (vipType.isFactionUnique()) {
            allStrings.add("Is Faction Unique");
        }
        if (vipType.isPlayerUnique()) {
            allStrings.add("Is Player Unique");
        }


        return allStrings;
    }

    public static String getLocationString(VIP vip) {
        String locationString = "";
        if (vip.getPlanetLocation() != null) {
            locationString = vip.getPlanetLocation().getName();
        } else if (vip.getTroopLocation() != null) {
            locationString = vip.getTroopLocation().getName();
        } else {
            locationString = vip.getShipLocation().getName();
        }
        return locationString;
    }

    public static Planet getLocation(VIP vip) {
        Planet location = null;
        if (vip.getPlanetLocation() != null) {
            location = vip.getPlanetLocation();
        } else if (vip.getTroopLocation() != null) {
            if (vip.getTroopLocation().getPlanetLocation() != null) {
                location = vip.getTroopLocation().getPlanetLocation();
            } else {
                location = vip.getTroopLocation().getShipLocation().getLocation();
            }
        } else {
            location = vip.getShipLocation().getLocation();
        }
        return location;
    }

    public static boolean isPlayerVIPAtPlanet(Player aPlayer, Planet aPlanet, Galaxy galaxy) {
        boolean VIPAtPlanet = false;
        for (VIP vip : galaxy.getAllVIPs()) {
            if ((vip.getBoss() == aPlayer) & (getLocation(vip) == aPlanet)) {
                VIPAtPlanet = true;
            }
        }
        return VIPAtPlanet;
    }

    public static int getAssassinationSkill(VIP vip, GameWorld gameWorld) {
        int skill = getVipTypeByKey(vip.getTypeKey(), gameWorld).getAssassination();
        skill = skill + (vip.getKills() * 5);
        return skill;
    }

    public static int getDuellistSkill(VIP vip, GameWorld gameWorld) {
        int skill = getVipTypeByKey(vip.getTypeKey(), gameWorld).getDuellistSkill();
        skill = skill + (vip.getKills() * 5);
        return skill;
    }

    public static int getCounterEspionage(VIP vip, GameWorld gameWorld) {
        int skill = getVipTypeByKey(vip.getTypeKey(), gameWorld).getCounterEspionage();
        skill = skill + (vip.getKills() * 5);
        if (skill > 95) {
            skill = 95;
        }
        return skill;
    }

    public static int getExterminatorSkill(VIP vip, GameWorld gameWorld) {
        int skill = getVipTypeByKey(vip.getTypeKey(), gameWorld).getExterminator();
        skill = skill + (vip.getKills() * 5);
        if (skill > 95) {
            skill = 95;
        }
        return skill;
    }

    public static boolean hatesDuellist(VIP vip, GameWorld gameWorld, VIP anotherVIP) {
        return getVipTypeByKey(vip.getTypeKey(), gameWorld).getAlignment().hateDuellist(getVipTypeByKey(anotherVIP.getTypeKey(), gameWorld).getAlignment().getName());
    }

    public static VIP findVIPGovernor(Player aPlayer, Galaxy galaxy) {
        VIP foundVIP = null;
        int i = 0;
        while ((foundVIP == null) & (i < galaxy.getAllVIPs().size())) {
            VIP tempVIP = galaxy.getAllVIPs().get(i);
            if ((getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld()).isGovernor()) & (tempVIP.getBoss() == aPlayer)) {
                foundVIP = tempVIP;
            } else {
                i++;
            }
        }
        return foundVIP;
    }

    public static VIP findVIPSpy(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        VIP foundVIP = null;
        int i = 0;
        while ((foundVIP == null) & (i < galaxy.getAllVIPs().size())) {
            VIP tempVIP = galaxy.getAllVIPs().get(i);
            if ((getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld()).isSpying()) & (tempVIP.getBoss() == aPlayer) & (tempVIP.getPlanetLocation() == aPlanet)) {
                foundVIP = tempVIP;
            } else {
                i++;
            }
        }
        return foundVIP;
    }

    public static int findHighestVIPResistanceBonus(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        int highestResistanceBonus = 0;
        for (VIP tempVIP :  galaxy.getAllVIPs()) {
            VIPType vipType = getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld());
            if (vipType.getResistanceBonus() > 0 && (tempVIP.getBoss() == aPlayer)
                    & (tempVIP.getPlanetLocation() == aPlanet)) {
                if (vipType.getResistanceBonus() > highestResistanceBonus) {
                    highestResistanceBonus = vipType.getResistanceBonus();
                }
            }
        }
        return highestResistanceBonus;
    }
    public static VIP findSurveyVIPonShip(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        VIP foundVIP = null;
        int i = 0;
        while ((foundVIP == null) & (i < galaxy.getAllVIPs().size())) {
            VIP tempVIP = galaxy.getAllVIPs().get(i);
            if (getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld()).isPlanetarySurvey() & (tempVIP.getBoss() == aPlayer)) {
                if (tempVIP.getShipLocation() != null) { // VIP is on a ship
                    if (tempVIP.getShipLocation().getLocation() == aPlanet) { // ship is in orbit around aPlanet
                        foundVIP = tempVIP;
                    }
                }
            }
            if (foundVIP == null) {
                i++;
            }
        }
        return foundVIP;
    }

}
