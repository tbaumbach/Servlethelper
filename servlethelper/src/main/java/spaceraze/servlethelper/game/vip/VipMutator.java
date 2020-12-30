package spaceraze.servlethelper.game.vip;

import spaceraze.util.general.Functions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.HighlightType;

import java.util.List;

public class VipMutator {

    private VipMutator(){}

    public static void checkVIPsInDestroyedShips(Spaceship aShip, Player aPlayer, Galaxy galaxy) {
        List<VIP> allVIPsOnShip = VipPureFunctions.findAllVIPsOnShip(aShip, galaxy.getAllVIPs());
        for (int i = 0; i < allVIPsOnShip.size(); i++) {
            VIP tempVIP = allVIPsOnShip.get(i);
            VIPType vipType = VipPureFunctions.getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld());
            // if VIP is hard to kill he moves to the nearby planet
            if (vipType.isHardToKill()) {
                setShipLocation(tempVIP, aShip.getLocation());
                aPlayer.addToVIPReport(
                        "Your " + vipType.getName() + " travelling in " + aShip.getName() + " have moved to the planet "
                                + aShip.getLocation().getName() + " when the ship was destroyed.");
            } else { // annars dör VIPen
                galaxy.getAllVIPs().remove(tempVIP);
                aPlayer.addToVIPReport("Your " + vipType.getName() + " has been killed when your ship "
                        + aShip.getName() + " was destroyed at " + aShip.getLocation().getName() + ".");
                aPlayer.addToHighlights(vipType.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
            }
        }
    }

    public static void checkVIPsInSelfDestroyedShips(Spaceship aShip, Player aPlayer, Galaxy galaxy) {
        List<VIP> allVIPsOnShip = VipPureFunctions.findAllVIPsOnShip(aShip, galaxy.getAllVIPs());
        for (VIP tempVIP : allVIPsOnShip) {
            VIPType vipType = VipPureFunctions.getVipTypeByKey(tempVIP.getTypeKey(), galaxy.getGameWorld());
            if (aShip.getLocation() == null) {
                // ship is retreating, vip is killed
                galaxy.removeVIP(tempVIP);
                aPlayer.addToVIPReport("Your " + vipType.getName() + " has been killed when your retreating ship "
                        + aShip.getName() + " was selfdestructed.");
                aPlayer.addToHighlights(vipType.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
            } else if (aShip.getLocation().getPlayerInControl() == aPlayer) {
                // om skeppet är vid en egen planet så flyttar VIPen dit
                setShipLocation(tempVIP, aShip.getLocation());
                aPlayer.addToVIPReport(
                        "Your " + vipType.getName() + " travelling in " + aShip.getName() + " have moved to the planet "
                                + aShip.getLocation().getName() + " when the ship was selfdestructed.");
            } else {
                // annars om VIPen kan vara på fientliga planeter så flyttar den dit
                if (vipType.isCanVisitEnemyPlanets()) {
                    setShipLocation(tempVIP, aShip.getLocation());
                    aPlayer.addToVIPReport("Your " + vipType.getName() + " travelling in " + aShip.getName()
                            + " have moved to the planet " + aShip.getLocation().getName()
                            + " when the ship was selfdestructed.");
                } else // annars om det är en neutral planet och VIP en är en guvenör flyttar han dit
                    if ((vipType.isCanVisitNeutralPlanets()) & (aShip.getLocation().getPlayerInControl() == null)) {
                        setShipLocation(tempVIP, aShip.getLocation());
                        aPlayer.addToVIPReport("Your " + vipType.getName() + " travelling in " + aShip.getName()
                                + " have moved to the planet " + aShip.getLocation().getName()
                                + " when the ship was selfdestructed.");
                    } else { // annars dör VIPen
                        galaxy.removeVIP(tempVIP);
                        aPlayer.addToVIPReport("Your " + vipType.getName() + " has been killed when your ship "
                                + aShip.getName() + " was selfdestructed at " + aShip.getLocation().getName() + ".");
                        aPlayer.addToHighlights(vipType.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
                    }
            }
        }
    }

    public static void checkVIPsInDestroyedTroop(Troop aTroop, Galaxy galaxy) {
        List<VIP> allVIPsOnTroop = VipPureFunctions.findAllVIPsOnTroop(aTroop, galaxy.getAllVIPs());
        Player aPlayer = aTroop.getOwner();
        for (VIP vip : allVIPsOnTroop) {
            VIPType vipType = VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), galaxy.getGameWorld());
            // if VIP is hard to kill he moves to the nearby planet
            if (vipType.isHardToKill()) {
                if (aTroop.getPlanetLocation() != null) {
                    setShipLocation(vip, aTroop.getPlanetLocation());
                    aPlayer.addToVIPReport("Your " + vipType.getName() + " travelling in " + aTroop.getName()
                            + " have moved to the planet " + aTroop.getPlanetLocation().getName()
                            + " when the ship was destroyed.");
                } else { // VIP is on troop on a ship
                    setShipLocation(vip, aTroop.getShipLocation().getLocation());
                    aPlayer.addToVIPReport("Your " + vipType.getName() + " travelling in " + aTroop.getName()
                            + " have moved to the planet " + aTroop.getShipLocation().getLocation().getName()
                            + " when the ship carrying the troop was destroyed.");
                }
            } else { // annars d�r VIPen
                galaxy.getAllVIPs().remove(vip);
                if (aTroop.getPlanetLocation() != null) {
                    aPlayer.addToVIPReport(
                            "Your " + vipType.getName() + " has been killed when your troop " + aTroop.getName()
                                    + " was destroyed at " + aTroop.getPlanetLocation().getName() + ".");
                } else {
                    aPlayer.addToVIPReport(
                            "Your " + vipType.getName() + " has been killed when your troop " + aTroop.getName()
                                    + " was destroyed at " + aTroop.getShipLocation().getLocation().getName()
                                    + " when the ship carrying the troop was destroyed.");
                }
                aPlayer.addToHighlights(vipType.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
            }
        }
    }

    public static VIP maybeAddVIP(Player aPlayer, Galaxy galaxy) {
        VIP aVIP = null;
        int aRandom = Functions.getRandomInt(1, 2);
        if (aRandom == 1) {
            aVIP = createPlayerVIP(aPlayer, galaxy);
        }
        return aVIP;
    }

    /**
     * Create a VIP for a certain player
     *
     * @param aPlayer
     *            the player for whom this VIP will belong
     * @return a VIP compatible with the players faction
     */
    public static VIP createPlayerVIP(Player aPlayer, Galaxy galaxy) {
        Logger.finer("createPlayerVIP: " + aPlayer.getName() + ", alignment=" + aPlayer.getFaction().getAlignment());
        VIP aVIP = null;
        boolean ok = false;
        while (!ok) { // loopa tills det blir en vip som spelaren kan ha
            ok = true;
            aVIP = createRandomVIP(galaxy);
            VIPType vipType = VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), galaxy.getGameWorld());
            Logger.finer(vipType.getName() + ", alignment=" + vipType.getAlignment() + ", canHaveVip="
                    + aPlayer.getFaction().getAlignment().canHaveVip(vipType.getAlignment().getName()));
            if (!aPlayer.getFaction().getAlignment().canHaveVip(vipType.getAlignment().getName())) {
                ok = false;
            }
        }
        aVIP.setBoss(aPlayer);
        galaxy.getAllVIPs().add(aVIP);
        return aVIP;
    }

    public static VIP createRandomVIP(Galaxy galaxy) {
        VIPType tempviptype = VipPureFunctions.getRandomVIPType(galaxy);
        return createNewVIP(tempviptype, true);
    }

    public static VIP createNewVIP(VIPType vipType, boolean isFanatic) {
        return new VIP(vipType, isFanatic);
    }

    public static VIP createNewVIP(VIPType vipType, Player aBoss, Planet planetLocation, boolean isFanatic) {
        VIP tempVIP = createNewVIP(vipType, isFanatic);
        tempVIP.setBoss(aBoss);
        setShipLocation(tempVIP, planetLocation);
        return tempVIP;
    }

    public static VIP getNewVIPShortName(String vipTypeShortName, GameWorld gameWorld) {
        VIPType vipType = gameWorld.getVIPTypeByShortName(vipTypeShortName);
        return createNewVIP(vipType, true);
    }

    public static VIP getNewVIP(String vipTypeName, GameWorld gameWorld) {
        VIPType vipType = gameWorld.getVIPTypeByName(vipTypeName);
        return createNewVIP(vipType, true);
    }

    public static void moveVIP(VIP vip, Planet moveToPlanet, TurnInfo ti, GameWorld gameWorld) {
        String oldLocationString = VipPureFunctions.getLocationString(vip);
        vip.setPlanetLocation(moveToPlanet);
        vip.setShipLocation(null);
        vip.setTroopLocation(null);
        ti.addToLatestVIPReport(VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), gameWorld).getName() + " has moved from " + oldLocationString + " to " + vip.getPlanetLocation().getName() + ".");
    }

    public static void moveVIP(VIP vip, Spaceship moveToShip, TurnInfo ti, GameWorld gameWorld) {
        String oldLocationString = VipPureFunctions.getLocationString(vip);
        vip.setPlanetLocation(null);
        vip.setShipLocation(moveToShip);
        vip.setTroopLocation(null);
        ti.addToLatestVIPReport(VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), gameWorld).getName() + " has moved from " + oldLocationString + " to " + vip.getShipLocation().getName() + ".");
    }

    public static void moveVIP(VIP vip, Troop moveToTroop, TurnInfo ti, GameWorld gameWorld) {
        String oldLocationString = VipPureFunctions.getLocationString(vip);
        vip.setPlanetLocation(null);
        vip.setShipLocation(null);
        vip.setTroopLocation(moveToTroop);
        ti.addToLatestVIPReport(VipPureFunctions.getVipTypeByKey(vip.getTypeKey(), gameWorld).getName() + " has moved from " + oldLocationString + " to " + vip.getTroopLocation().getName() + ".");
    }

    public static void setShipLocation(VIP vip, Planet aPlanet) {
        vip.setPlanetLocation(aPlanet);
        vip.setShipLocation(null);
        vip.setTroopLocation(null);
    }

}
