package spaceraze.servlethelper.game.spaceship;

import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.SpaceShipSize;
import spaceraze.world.enums.SpaceshipRange;
import spaceraze.world.orders.Orders;
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
        }else if((spaceshipType.isWorldUnique() && spaceshipTypeExist(spaceshipType, null, null, galaxy))
                || (spaceshipType.isFactionUnique() && spaceshipTypeExist(spaceshipType, GameWorldHandler.getFactionByKey(player.getFactionKey(), galaxy.getGameWorld()), null, galaxy))
                || (spaceshipType.isPlayerUnique() && spaceshipTypeExist(spaceshipType, null, player, galaxy))){
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

    public static List<Spaceship> getPlayersSpaceships(Player aPlayer, Galaxy galaxy) {
        List<Spaceship> playersss = new ArrayList<Spaceship>();
        for (Spaceship spaceship : galaxy.getSpaceships()) {
            if (spaceship.getOwner() == aPlayer) {
                playersss.add(spaceship);
            }
        }
        return playersss;
    }

    private static boolean spaceshipTypeExist(SpaceshipType aSpaceshipType, Faction aFaction, Player aPlayer, Galaxy galaxy) {
        List<Spaceship> spaceshipsToCheck;
        if (aPlayer != null) {// playerUnique
            Logger.fine("spaceshipTypeExist: Player check");
            spaceshipsToCheck = getPlayersSpaceships(aPlayer, galaxy);
        } else if (aFaction != null) {// factionUnique
            spaceshipsToCheck = new ArrayList<Spaceship>();
            for (Player tempPlayer : galaxy.getPlayers()) {
                if (GameWorldHandler.getFactionByKey(tempPlayer.getFactionKey(), galaxy.getGameWorld()).getName().equals(aFaction.getName())) {
                    spaceshipsToCheck.addAll(getPlayersSpaceships(tempPlayer, galaxy));
                }
            }
        } else {// worldUnique
            spaceshipsToCheck = galaxy.getSpaceships();
        }

        for (Spaceship spaceship : spaceshipsToCheck) {
            if (spaceship.getTypeKey().equals(aSpaceshipType.getKey())) {
                Logger.fine("Shio exist: " + aSpaceshipType.getName());
                return true;
            }
        }

        return false;
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
                Spaceship tempShip = aPlayer.getGalaxy().findSpaceshipByUniqueId(shipMovement.getSpaceshipKey());
                if (!tempShipList.contains(tempShip)) {
                    tempShipList.add(tempShip);
                }

                if (isCarrier(tempShip)) {
                    List<ShipToCarrierMovement> SqdToCarrierMovementList = aPlayer.getOrders().getShipToCarrierMoves();
                    for (ShipToCarrierMovement shipToCarrierMovement : SqdToCarrierMovementList) {
                        if (tempShip.getKey().equalsIgnoreCase(shipToCarrierMovement.getDestinationCarrierKey())) {
                            tempShipList.add(aPlayer.getGalaxy().findSpaceshipByUniqueId(shipToCarrierMovement.getSpaceShipKey()));
                        }
                    }
                    List<Spaceship> shipListAtCarrierPlanet = getPlayersSpaceshipsOnPlanet(aPlayer, tempShip.getLocation(), aPlayer.getGalaxy().getSpaceships());
                    for (Spaceship spaceship : shipListAtCarrierPlanet) {
                        if (spaceship.getCarrierLocation() == tempShip) {
                            boolean add = true;
                            for (ShipMovement shipMove : shipMovemants) {
                                if (shipMove.getSpaceshipKey().equals(spaceship.getKey())) {
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
                Spaceship tempSpaceship = aPlayer.getGalaxy().findSpaceshipByUniqueId(shipMovement.getSpaceshipKey());
                if (tempSpaceship.getLocation() != null && tempSpaceship.getLocation() == aPlanet) {// removing ships on the planet with move orders
                    tempShipList.remove(tempSpaceship);
                    if (isCarrier(tempSpaceship)) {
                        List<Spaceship> removeShips = new ArrayList<>();
                        for (Spaceship tempShip : tempShipList) {
                            if (tempShip.getSize() == SpaceShipSize.SQUADRON) {
                                List<ShipToCarrierMovement> SqdToCarrierMovementList = aPlayer.getOrders().getShipToCarrierMoves();
                                for (ShipToCarrierMovement shipToCarrierMovement : SqdToCarrierMovementList) {
                                    if (tempSpaceship.getKey().equalsIgnoreCase(shipToCarrierMovement.getDestinationCarrierKey())) {
                                        removeShips.add(tempShip);
                                    }
                                }
                                if (tempShip.getCarrierLocation() == tempSpaceship) {
                                    boolean add = true;
                                    for (ShipMovement shipMove : shipMovemants) {
                                        if (shipMove.getSpaceshipKey().equals(tempShip.getKey())) {
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
                    if (!GameWorldHandler.getFactionByKey(galaxy.getPlayerByGovenorName(aLis.getOwner()).getFactionKey(), galaxy.getGameWorld()).getName().equalsIgnoreCase(aFactionName)) {
                        lisList.add(aLis);
                    }
                } else { // neutralt = lägg till
                    lisList.add(aLis);
                }
            } else { // ta endast skepp från aFaction
                if (aLis.getOwner() != null) {
                    if (GameWorldHandler.getFactionByKey(galaxy.getPlayerByGovenorName(aLis.getOwner()).getFactionKey(), galaxy.getGameWorld()).getName().equalsIgnoreCase(aFactionName)) {
                        lisList.add(aLis);
                    }
                }
            }
        }
        return lisList;
    }

    public static int getShields(Spaceship spaceship, GameWorld gameWorld) {
        return killsFactor(spaceship.getShields(), spaceship.getKills(), spaceship, gameWorld);
    }

    public static int getWeaponsStrengthSquadron(Spaceship spaceship, GameWorld gameWorld) {
        return killsFactor(spaceship.getWeaponsStrengthSquadron(), spaceship.getKills(), spaceship, gameWorld);
    }

    public static int getWeaponsStrengthSmall(Spaceship spaceship, GameWorld gameWorld) {
        return killsFactor(spaceship.getWeaponsStrengthSmall(), spaceship.getKills(), spaceship, gameWorld);
    }

    public static int getWeaponsStrengthMedium(Spaceship spaceship, GameWorld gameWorld) {
        return killsFactor(spaceship.getWeaponsStrengthMedium(), spaceship.getKills(), spaceship, gameWorld);
    }

    public static int getWeaponsStrengthLarge(Spaceship spaceship, GameWorld gameWorld) {
        return killsFactor(spaceship.getWeaponsStrengthLarge(), spaceship.getKills(), spaceship, gameWorld);
    }

    public static int getWeaponsStrengthHuge(Spaceship spaceship, GameWorld gameWorld) {
        return killsFactor(spaceship.getWeaponsStrengthHuge(), spaceship.getKills(), spaceship, gameWorld);
    }

    /**
     * Used to add experience (kills) to certain values (weapons, shields...)
     */
    private static int killsFactor(int value, int kills, Spaceship spaceship, GameWorld gameWorld){
        double temp = value * (((kills/getSpaceshipTypeByKey(spaceship.getTypeKey(), gameWorld).getSize().getSlots())+10.0) / 10.0);
        return (int)Math.round(temp);
    }

    public static SpaceshipType getSpaceshipTypeByName(String name, GameWorld gameWorld){
        SpaceshipType foundsst = gameWorld.getShipTypes().stream().filter(spaceshipType -> spaceshipType.getName().equalsIgnoreCase(name)).findAny().orElse(null);

        if (foundsst != null){
            Logger.finest("GameWorld.getSpaceshipTypeByName, name:" + name + " -> " + foundsst);
        }else{ // om detta inträffar så finns det antagligen en felstavning av en skeppstyp i gameworlden
            Logger.severe("GameWorld(" + gameWorld.getFullName() + ").getSpaceshipTypeByName, name:" + name + " -> " + foundsst);
            Thread.dumpStack();
        }
        return foundsst;
    }

    public static SpaceshipType getSpaceshipTypeByKey(String key, GameWorld gameWorld){
        return gameWorld.getShipTypes().stream().filter(spaceshipType -> spaceshipType.getKey().equalsIgnoreCase(key)).findAny().orElse(null);
    }

    /**
     * Return a sum of all current weapon strengths, counting with
     * the following factors:
     * -no # salvoes left
     * -kills
     * -damage to the ship
     * @return the computed strength of the ship
     */
    public static int getActualDamage(Spaceship spaceship, GameWorld gameWorld) {
        int totalDamage = getWeaponsStrengthSmall(spaceship, gameWorld);
        totalDamage = totalDamage + SpaceshipMutator.getMediumSalvo(spaceship, gameWorld, false);
        totalDamage = totalDamage + SpaceshipMutator.getLargeSalvo(spaceship, gameWorld, false);
        totalDamage = totalDamage + SpaceshipMutator.getHugeSalvo(spaceship, gameWorld, false);
        totalDamage = totalDamage + getWeaponsStrengthSquadron(spaceship, gameWorld);
        totalDamage = (int) Math.round(totalDamage * ((spaceship.getCurrentDc() * 1.0) / spaceship.getDamageCapacity()));
        if (totalDamage < 1){
            totalDamage = 1;
        }
        return totalDamage;
    }

    public static int getDamageNoArmor(Spaceship spaceship, GameWorld gameWorld, Spaceship target, int multiplier) {
        double tmpDamage = 0;
        if (target.getSize() == SpaceShipSize.SQUADRON) {
            tmpDamage = getWeaponsStrengthSquadron(spaceship, gameWorld);
        } else {
            tmpDamage = getWeaponsStrengthSmall(spaceship, gameWorld);
            if (getSpaceshipTypeByKey(target.getTypeKey(), gameWorld).getSize() == SpaceShipSize.MEDIUM) {
                tmpDamage = tmpDamage + SpaceshipMutator.getMediumSalvo(spaceship, gameWorld, false);
            }
            if (getSpaceshipTypeByKey(target.getTypeKey(), gameWorld).getSize() == SpaceShipSize.LARGE) {
                tmpDamage = tmpDamage + SpaceshipMutator.getLargeSalvo(spaceship, gameWorld, false);
            }
            if (getSpaceshipTypeByKey(target.getTypeKey(), gameWorld).getSize() == SpaceShipSize.HUGE) {
                tmpDamage = tmpDamage + SpaceshipMutator.getHugeSalvo(spaceship, gameWorld, false);
            }
        }
        double baseDamage = tmpDamage * ((spaceship.getCurrentDc() * 1.0) / spaceship.getDamageCapacity());
        // randomize damage
        int actualDamage = (int) Math.round(baseDamage * (multiplier / 10.0));
        if (actualDamage < 1) {
            actualDamage = 1;
        }
        Logger.finest("actualDamage=" + actualDamage + " baseDamage=" + baseDamage + " ship hit: " + target.getName() + " firing ship: " + spaceship.getName());
        return actualDamage;
    }

    public static boolean getNeedResupply(Spaceship spaceship) {
        boolean needSupplies = false;
        if (spaceship.getWeaponsSalvoesMedium() < spaceship.getWeaponsMaxSalvosMedium()) {
            needSupplies = true;
        }
        if (spaceship.getWeaponsSalvoesLarge() < spaceship.getWeaponsMaxSalvosLarge()) {
            needSupplies = true;
        }
        if (spaceship.getWeaponsSalvoesHuge() < spaceship.getWeaponsMaxSalvosHuge()) {
            needSupplies = true;
        }
        return needSupplies;
    }

    public static String getAllBattleSimVipsOnShip(Spaceship aShip, List<VIP> vips, GameWorld gameWorld) {
        StringBuffer sb = new StringBuffer();
        List<VIP> vipsOnShip = VipPureFunctions.findAllVIPsOnShip(aShip, vips);
        List<VIP> battleVips = new LinkedList<VIP>();
        for (VIP aVIP : vipsOnShip) {
            if (VipPureFunctions.isSpaceBattleVip(VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld))) {
                battleVips.add(aVIP);
            }
        }
        for (VIP aVIP : battleVips) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld).getShortName());
        }
        return sb.toString();
    }

    public static Spaceship findShipWithoutVipAndOrders(Player aPlayer, Planet aPlanet, String shipTypeName, Galaxy galaxy) {
        Spaceship foundShip = null;
        List<Spaceship> shipsAtPlanet = getPlayersSpaceshipsOnPlanet(aPlayer, aPlanet, galaxy.getSpaceships());
        for (Spaceship aShip : shipsAtPlanet) {
            if (aShip.getName().equals(shipTypeName)) {
                if (!checkShipMove(aShip, aPlayer.getOrders())) { // if there are no order already for this ship
                    if (VipPureFunctions.findAllVIPsOnShip(aShip, galaxy.getAllVIPs()).size() == 0) { // check that ship is empty (no carried VIPs)
                        foundShip = aShip;
                    }
                }
            }
        }
        return foundShip;
    }

    /**
     * return total percentage of dc and armor
     * Example 1: if a ship have 100% dc and shields, it returns 200
     * Example 2: if a ship have 50% left ofshields and 75% left of dc it returns 125
     * @return added percentage of dc and shields
     */
    public static int getDamageLevel(Spaceship spaceship, GameWorld gameWorld){
        double curDcLevel = spaceship.getCurrentDc()*1.0/ spaceship.getDamageCapacity();
        double curShieldsLevel = spaceship.getCurrentShields() *1.0/getShields(spaceship, gameWorld);
        int curDcPercentage = (int)Math.round(curDcLevel*100);
        int curShieldsPercentage = (int)Math.round(curShieldsLevel*100);
        int damageLevel = curDcPercentage + curShieldsPercentage;
        Logger.finest( "getDamageLevel: " + damageLevel);
        return damageLevel;
    }

    public static boolean isDamaged(Spaceship spaceship, GameWorld gameWorld){
        boolean damaged = getDamageLevel(spaceship, gameWorld) < 200;
        Logger.finest( "isDamaged: " + damaged);
        return damaged;
    }

    public static Spaceship findSurveyShip(Planet aPlanet, Player aPlayer, List<Spaceship> spaceships, GameWorld gameWorld) {
        Spaceship foundShip = null;
        int i = 0;
        while ((foundShip == null) & (i < spaceships.size())) {
            Spaceship tempShip = spaceships.get(i);
            if ((SpaceshipPureFunctions.getSpaceshipTypeByKey(tempShip.getTypeKey(), gameWorld).isPlanetarySurvey()) & (tempShip.getOwner() == aPlayer)
                    & (tempShip.getLocation() == aPlanet)) {
                foundShip = tempShip;
            } else {
                i++;
            }
        }
        return foundShip;
    }

    public static int getNoSquadronsAssignedToCarrier(Spaceship aCarrier, List<Spaceship> spaceships) {
        int count = 0;
        Player aPlayer = aCarrier.getOwner();
        List<Spaceship> shipsAtPlanet = getPlayersSpaceshipsOnPlanet(aPlayer, aCarrier.getLocation(), spaceships);
        for (Spaceship aSpaceship : shipsAtPlanet) {
            if (aSpaceship.getCarrierLocation() == aCarrier) {
                // check if sstemp has a move order
                if (aPlayer != null) {
                    boolean moveToPlanetOrder = checkShipMove(aSpaceship, aPlayer.getOrders());
                    boolean moveToCarrierOrder = checkShipToCarrierMove(aSpaceship, aPlayer.getOrders());
                    // if not, inc counter
                    if (!(moveToCarrierOrder | moveToPlanetOrder)) {
                        count++;
                    }
                } else {
                    count++;
                }
            }
        }
        return count;
    }

    // kolla om det finns en gammal order för detta skepp
    public static boolean checkShipToCarrierMove(Spaceship spaceship, Orders orders) {
        return orders.getShipToCarrierMoves().stream().anyMatch(move -> spaceship.getKey().equalsIgnoreCase(move.getSpaceShipKey()));
    }

    // kolla om det finns en gammal order för detta skepp
    public static boolean checkShipMove(Spaceship spaceship, Orders orders) {
        return orders.getShipMoves().stream().anyMatch(move -> spaceship.getKey().equalsIgnoreCase(move.getSpaceshipKey()));
    }



    public static boolean isCarrier(Spaceship spaceship) {
        return spaceship.getSquadronCapacity() > 0;
    }

    public static SpaceshipRange getRange(Spaceship spaceship, Galaxy galaxy) {
        SpaceshipRange range = getSpaceshipTypeByKey(spaceship.getTypeKey(), galaxy.getGameWorld()).getRange();
        if (spaceship.getOwner() != null){ // only need to check presense of vip if not neutral
            if (range == SpaceshipRange.SHORT && galaxy != null) {
                boolean ftlMasterOnShip = VipPureFunctions.isFTLMasterOnShip(spaceship, galaxy);
                if (ftlMasterOnShip) {
                    range = SpaceshipRange.LONG;
                }
            }
        }
        return range;
    }

    public static int getIncreaseInitiative(Spaceship spaceship) {
        int tmpInitBouns = 0;
        if (spaceship.isInitSupport()) {
            tmpInitBouns = getInitSupportBonus(spaceship);
        } else {
            tmpInitBouns = getInitiativeBonus(spaceship);
        }
        return tmpInitBouns;
    }

    public static int getInitiativeBonus(Spaceship spaceship){
        int tmpInitBonus = 0;
        if (!spaceship.isInitSupport()){
            tmpInitBonus = spaceship.getIncreaseInitiative();
        }
        return tmpInitBonus;
    }

    public static int getInitSupportBonus(Spaceship spaceship){
        int tmpInitSupportBonus = 0;
        if (spaceship.isInitSupport()){
            tmpInitSupportBonus = spaceship.getIncreaseInitiative();
        }
        return tmpInitSupportBonus;
    }

    public static int getInitiativeBonus(SpaceshipType spaceshipType){
        int tmpInitBonus = 0;
        if (!spaceshipType.isInitSupport()){
            tmpInitBonus = spaceshipType.getIncreaseInitiative();
        }
        return tmpInitBonus;
    }

    public static int getInitSupportBonus(SpaceshipType spaceshipType){
        int tmpInitSupportBonus = 0;
        if (spaceshipType.isInitSupport()){
            tmpInitSupportBonus = spaceshipType.getIncreaseInitiative();
        }
        return tmpInitSupportBonus;
    }

    /**
     * Return a list with all spaceships at aPlanet. If civilian is false, only
     * military ships are returned.
     *
     * @param planet
     *            a planet
     * @param civilian
     *            if only civilian ships should be returned
     * @return a list of civilian or military ships at the aPlanet
     */
    public static List<Spaceship> getShips(Planet planet, boolean civilian, Galaxy galaxy) {
        List<Spaceship> ships = new LinkedList<Spaceship>();
        for (Spaceship aShip : galaxy.getSpaceships()) {
            if ((aShip.getLocation() != null) && (aShip.getLocation() == planet)) {
                if (getSpaceshipTypeByKey(aShip.getTypeKey(), galaxy.getGameWorld()).isCivilian() == civilian) {
                    ships.add(aShip);
                }
            }
        }
        return ships;
    }

    /**
     * A capital ship is not civilian, not a squadron and can move
     * @return
     */
    public static boolean isCapitalShip(Spaceship spaceship, GameWorld gameWorld){
        return !getSpaceshipTypeByKey(spaceship.getTypeKey(), gameWorld).isCivilian() & spaceship.getSize() != SpaceShipSize.SQUADRON & (spaceship.getRange() != SpaceshipRange.NONE);
    }

    public static SpaceShipSize getMaxResupplyFromShip(Planet aPlanet, Player aPlayer, Galaxy galaxy) {
        SpaceShipSize maxSize = SpaceShipSize.NONE;
        for (Iterator<Spaceship> ss = galaxy.getSpaceships().iterator(); ss.hasNext();) {
            Spaceship aShip = ss.next();
            if (aShip.getLocation() == aPlanet) {
                if (aShip.getOwner() == aPlayer) {
                    if (aShip.getSupply().getCompareSize() > maxSize.getCompareSize()) {
                        maxSize = aShip.getSupply();
                    }
                }
            }
        }
        return maxSize;
    }

    public static boolean isDefenceShip(SpaceshipType spaceshipType){
        return !spaceshipType.isCivilian() & spaceshipType.getSize() != SpaceShipSize.SQUADRON & (spaceshipType.getRange() == SpaceshipRange.NONE);
    }

    /**
     * @return a percentage of remaning DC
     */
    public static int getHullStrength(Spaceship spaceship){
        int hullStrength = (int) Math.round((100.0 * spaceship.getCurrentDc())	/ spaceship.getDamageCapacity());
        return hullStrength;
    }

    public static boolean isPlayerUniqueBuild(Player aPlayer, SpaceshipType spaceshipType, Galaxy galaxy) {
        return spaceshipTypeExist(spaceshipType, null, aPlayer, galaxy);
    }

    public static boolean isWorldUniqueBuild(Galaxy aGalaxy, SpaceshipType spaceshipType) {
        return spaceshipTypeExist(spaceshipType, null, null, aGalaxy);
    }

    public static boolean isFactionUniqueBuild(Player aPlayer, SpaceshipType spaceshipType, Galaxy galaxy) {
        return spaceshipTypeExist(spaceshipType, GameWorldHandler.getFactionByKey(aPlayer.getFactionKey(), aPlayer.getGalaxy().getGameWorld()), null, galaxy);
    }

    public static int getBuildCost(SpaceshipType spaceshipType, int vipBuildBonus){
        int tempBuildCost = spaceshipType.getBuildCost();
        if (vipBuildBonus > 0){
            int vipBuildbonus = 100 - vipBuildBonus;
            double tempBuildBonus = vipBuildbonus / 100.0;
            tempBuildCost = (int) Math.round(tempBuildCost * tempBuildBonus);
            if (tempBuildCost < 1){
                tempBuildCost = 1;
            }
        }
        return tempBuildCost;
    }

    public static Spaceship findSpaceship(String key, Galaxy galaxy) {
        return galaxy.getSpaceships().stream().filter(ship -> ship.getKey().equalsIgnoreCase(key)).findAny().orElseThrow();
    }

    public static Spaceship findSpaceship(String name, Player owner, Galaxy galaxy) {
        return galaxy.getSpaceships().stream().filter(ship -> ship.getName().equalsIgnoreCase(name)).filter(ship -> ship.getOwner() == owner).findAny().orElseThrow();
    }

}
