package spaceraze.servlethelper.game.troop;

import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.orders.Orders;
import spaceraze.world.orders.TroopToCarrierMovement;
import spaceraze.world.orders.TroopToPlanetMovement;

import java.util.*;

public class TroopPureFunctions {

    private TroopPureFunctions(){}

    public static boolean isConstructable(Player aPlayer, Galaxy galaxy, TroopType troopType, PlayerTroopImprovement playerTroopImprovement){
        boolean constructible =  true;
        if((playerTroopImprovement != null && !playerTroopImprovement.isAvailableToBuild()) || (playerTroopImprovement == null && troopType.isCanBuild())){
            constructible = false;
        }else if((troopType.isWorldUnique() && troopTypeExist(troopType, null, null, galaxy)) || (troopType.isFactionUnique() && troopTypeExist(troopType, aPlayer.getFaction(), null, galaxy)) || (troopType.isPlayerUnique() && troopTypeExist(troopType, null, aPlayer, galaxy))){
            constructible = false;
        }else if(troopType.isWorldUnique() || troopType.isFactionUnique() || troopType.isPlayerUnique()){
            // check if a build order already exist
            if(aPlayer.getOrders().haveTroopTypeBuildOrder(troopType)){
                constructible = false;
            }
            for (BlackMarketOffer aBlackMarketOffer : aPlayer.getGalaxy().getCurrentOffers()) {
                if(aBlackMarketOffer.isTroop() && aBlackMarketOffer.getTroopType().getName().equals(troopType.getName())){
                    constructible = false;
                }
            }
        }
        return constructible;
    }

    public static List<Troop> getPlayersTroopsOnPlanet(Player aPlayer, Planet aPlanet, List<Troop> troops) {
        List<Troop> playerst = new LinkedList<Troop>();
        Logger.finest("galaxy getPlayersTroopsOnPlanet: aPlanet: " + aPlanet.getName());
        for (Troop aTroop : troops) {
            if (aTroop.getOwner() != null) {
                // Logger.finest("aTroop.getOwner() + aPlayer: " + aTroop.getOwner().getName() +
                // " " + aPlayer.getName());

                if (aTroop.getOwner() == aPlayer) {
                    // Logger.finest("aTroop.getOwner() == aPlayer: True" );

                    if (aTroop.getPlanetLocation() != null && aTroop.getPlanetLocation() == aPlanet) {
                        // Logger.finest("aTroop.getPlanetLocation() + aPlanet: true");
                        playerst.add(aTroop);
                    } else {
                        if (aTroop.getShipLocation() != null) {
                            if (aTroop.getShipLocation().getLocation() != null) {
                                Logger.finest("aTroop.getShipLocation().getLocation() + aPlanet: "
                                        + aTroop.getShipLocation().getLocation().getName() + " " + aPlanet.getName());
                                if (aTroop.getShipLocation().getLocation() == aPlanet) {
                                    Logger.finest("aTroop.getShipLocation().getLocation() + aPlanet: true");
                                    playerst.add(aTroop); // this should also cover troops in retreating ships
                                }
                                // }else{
                                // Logger.finest("aTroop.getShipLocation().getLocation() = null. + aPlanet: " +
                                // aPlanet.getName());
                            }
                        }
                    }
                }
            }
        }
        return playerst;
    }

    public static int getNoTroopsAssignedToCarrier(Spaceship aCarrier, Player player, List<Troop> troops) {
        int count = 0;
        Player aPlayer = aCarrier.getOwner();
        List<Troop> troopsAtPlanet = getPlayersTroopsOnPlanet(player, aCarrier.getLocation(), troops);
        for (Troop aTroop : troopsAtPlanet) {
            if (aTroop.getShipLocation() == aCarrier) {
                // check if sstemp has a move order
                if (aPlayer != null) {
                    boolean moveOrder = TroopPureFunctions.checkTroopMove(aTroop, aPlayer.getOrders());
                    // if not, inc counter
                    if (!moveOrder) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static TroopType getTroopTypeByName(String name, GameWorld gameWorld){
        return gameWorld.getTroopTypes().stream().filter(troopType -> troopType.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public static TroopType getTroopTypeByKey(String key, GameWorld gameWorld){
        return gameWorld.getTroopTypes().stream().filter(troopType -> troopType.getKey().equalsIgnoreCase(key)).findAny().orElse(null);
    }

    public static int getCostBuild(TroopType troopType, int vipBuildBonus) {
        int tempBuildCost = troopType.getCostBuild();
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

    public static List<Troop> findAllTroopsOnPlanet(List<Troop> troops, Planet aPlanet) {
        List<Troop> troopsOnPlanet = new LinkedList<Troop>();
        for (Troop troop : troops) {
            if (troop.getPlanetLocation() == aPlanet) {
                troopsOnPlanet.add(troop);
            }
        }
        return troopsOnPlanet;
    }

    /**
     * Find all other players that have troops on planet aPlanet
     *
     * @param aPlayer
     *            Find any other player than aPlayer
     * @param aPlanet
     *            the planet
     * @return a set containing all other players
     */
    public static Set<Player> findOtherTroopsPlayersOnRazedPlanet(Player aPlayer, Planet aPlanet, List<Troop> troops) {
        Set<Player> otherPlayers = new HashSet<Player>();
        // find all troops on aPlanet
        List<Troop> troopsOnPlanet = findAllTroopsOnPlanet(troops, aPlanet);
        for (Troop aTroop : troopsOnPlanet) {
            // if not aPlayer or null
            Player owner = aTroop.getOwner();
            if ((owner != aPlayer) & (owner != null)) {
                // add the troops owner to set
                otherPlayers.add(owner);
            }
        }
        return otherPlayers;
    }

    /**
     * Returns a percentage of remaning DC
     * @return a percentage of remaning DC
     */
    public static int getTroopStrength(Troop troop){
        return (int) Math.round((100.0 * troop.getCurrentDamageCapacity()) / troop.getDamageCapacity());
    }

    public static List<Troop> getTroopsOnPlanet(Planet aPlanet, Player aPlayer, List<Troop> troops) {
        return getTroopsOnPlanet(aPlanet, aPlayer, true, troops);
    }

    public static List<Troop> getTroopsOnPlanet(Planet aPlanet, Player aPlayer, boolean showUnVisible, List<Troop> troops) {
        List<Troop> troopsAtPlanet = new ArrayList<>();
        for (Troop aTroop : troops) {
            if (aTroop.getPlanetLocation() == aPlanet) {
                if (aTroop.getOwner() == aPlayer) {
                    if (showUnVisible || getTroopTypeByKey(aTroop.getTypeKey(), aPlayer.getGalaxy().getGameWorld()).isVisible()) {
                        troopsAtPlanet.add(aTroop);
                    }
                }
            }
        }
        return troopsAtPlanet;
    }

    public static boolean isPlayerUniqueBuild(Player aPlayer, TroopType troopType) {
        return troopTypeExist(troopType, null, aPlayer, aPlayer.getGalaxy());
    }

    public static boolean isWorldUniqueBuild(Galaxy aGalaxy, TroopType troopType) {
        return troopTypeExist(troopType, null, null, aGalaxy);
    }

    public static boolean isFactionUniqueBuild(Player aPlayer, TroopType troopType) {
        return troopTypeExist(troopType, aPlayer.getFaction(), null, aPlayer.getGalaxy());
    }

    public static boolean troopTypeExist(TroopType aTroopType, Faction aFaction, Player aPlayer, Galaxy galaxy) {
        boolean exist = false;
        List<Troop> troopsToCheck;
        if (aPlayer != null) {// playerUnique
            troopsToCheck = getPlayersTroops(aPlayer, galaxy);
        } else if (aFaction != null) {// factionUnique
            troopsToCheck = new ArrayList<Troop>();
            for (Player tempPlayer : galaxy.getPlayers()) {
                if (tempPlayer.getFaction().getName().equals(aFaction.getName())) {
                    troopsToCheck.addAll(getPlayersTroops(tempPlayer, galaxy));
                }
            }
        } else {// worldUnique
            troopsToCheck = galaxy.getTroops();
        }

        for (Troop tempTroop : troopsToCheck) {
            if (getTroopTypeByKey(tempTroop.getTypeKey(), galaxy.getGameWorld()).getName().equals(aTroopType.getName())) {
                exist = true;
            }
        }

        return exist;
    }

    public static List<Troop> getPlayersTroops(Player aPlayer, Galaxy galaxy) {
        List<Troop> playersTroops = new LinkedList<>();
        for (Troop troop : galaxy.getTroops()) {
            if (troop.getOwner() == aPlayer) {
                playersTroops.add(troop);
            }
        }
        return playersTroops;
    }

    public static int getModifiedActualDamage(Troop troop, int baseDamage, int multiplier, boolean defender, int resistance, int vipBonus){
        double tmpDamage = baseDamage * ((100.0 + vipBonus) / 100.0);
        Logger.finer("vipBonus: " + tmpDamage);
        // add resistance effect
        Logger.finer("resistance: " + resistance);
        if (defender){
            tmpDamage = tmpDamage * ((resistance + 20.0) / 20.0);
        }//else{
        //tmpDamage = tmpDamage * ((20.0 - resistance) / 20.0);
        //}
        Logger.finer("defender=" + defender + ":" + tmpDamage);
        // add damage mod
        tmpDamage = tmpDamage * ((troop.getCurrentDamageCapacity() * 1.0) / troop.getDamageCapacity());
        Logger.finer("damaged: " + tmpDamage);
        // randomize damage
        int actualDamage = (int) Math.round(tmpDamage * (multiplier / 10.0));
        if (actualDamage < 0) {
            actualDamage = 1; // en attack m�ste alltid g�ra minst 1 i skada.
        }
        Logger.finer("multiplied: " + actualDamage);
        return actualDamage;
    }

    public static int getAttackArtillery(Troop troop) {
        double temp = troop.getAttackArtillery() * ((troop.getKills() + 10.0) / 10.0);
        return (int)Math.round(temp);
    }

    public static int getAttackArmored(Troop troop) {
        double temp = troop.getAttackArmored() * ((troop.getKills() + 10.0) / 10.0);
        return (int)Math.round(temp);
    }

    public static int getAttackInfantry(Troop troop) {
        double temp = troop.getAttackInfantry() * ((troop.getKills() + 10.0) / 10.0);
        return (int)Math.round(temp);
    }

    public static String getLocationString(Troop troop){
        String retStr = "";
        if (troop.getPlanetLocation() != null){
            retStr = troop.getPlanetLocation().getName();
        }else{
            retStr = troop.getShipLocation().getName();
        }
        return retStr;
    }

    /**
     * Check if a Troop is at a planet, either on the planet itself or on a ship at the planet
     * @param aPlanet
     * @return true if troop is on planet or on ship at planet
     */
    public static boolean isAtPlanet(Troop troop, Planet aPlanet){
        boolean atPlanet = false;
        if (troop.getPlanetLocation() != null){
            if (troop.getPlanetLocation() == aPlanet){
                atPlanet = true;
            }
        }else{
            if (troop.getShipLocation().getLocation() == aPlanet){
                atPlanet = true;
            }
        }
        return atPlanet;
    }

    public static boolean isDestroyed(Troop troop){
        return troop.getCurrentDamageCapacity() <= 0;
    }

    public static Troop findTroop(String key, Galaxy galaxy) {
        return galaxy.getTroops().stream().filter(troop -> troop.getKey().equalsIgnoreCase(key)).findAny().orElseThrow();
    }


    // check if there exist a carrier move order for this troop
    public static boolean checkTroopToCarrierMove(Troop aTroop, Orders orders) {
        boolean found = false;
        int i = 0;
        while ((found == false) & (i < orders.getTroopToCarrierMoves().size())) {
            TroopToCarrierMovement tempMove = orders.getTroopToCarrierMoves().get(i);
            if (aTroop.getKey().equalsIgnoreCase(tempMove.getTroopKey())) {
                found = true;
            } else {
                i++;
            }
        }
        return found;
    }

    // check if there exist a carrier move order for this troop to aCarrier
    public static boolean checkTroopToCarrierMove(Troop aTroop, Spaceship aCarrier, Orders orders) {
        boolean found = false;
        boolean moveToCarrier = false;
        int i = 0;
        while ((found == false) & (i < orders.getTroopToCarrierMoves().size())) {
            TroopToCarrierMovement tempMove = orders.getTroopToCarrierMoves().get(i);
            if (aTroop.getKey().equalsIgnoreCase(tempMove.getTroopKey())) {
                found = true;
                if (tempMove.getDestinationCarrierKey().equals(aCarrier.getKey())) {
                    moveToCarrier = true;
                }
            } else {
                i++;
            }
        }
        return moveToCarrier;
    }

    // check if there exist a planet move order for this troop
    public static boolean checkTroopToPlanetMove(Troop aTroop, Orders orders) {
        boolean found = false;
        int i = 0;
        while ((found == false) & (i < orders.getTroopToPlanetMoves().size())) {
            TroopToPlanetMovement tempMove = orders.getTroopToPlanetMoves().get(i);
            if (aTroop.getKey().equalsIgnoreCase(tempMove.getTroopKey())) {
                found = true;
            } else {
                i++;
            }
        }
        return found;
    }

    public static boolean checkTroopMove(Troop aTroop, Orders orders){
        boolean moving = false;
        if (checkTroopToCarrierMove(aTroop, orders)){
            moving = true;
        }else
        if (checkTroopToPlanetMove(aTroop, orders)){
            moving = true;
        }
        return moving;
    }

}
