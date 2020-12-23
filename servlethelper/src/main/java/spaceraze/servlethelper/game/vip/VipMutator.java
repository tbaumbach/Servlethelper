package spaceraze.servlethelper.game.vip;

import spaceraze.world.*;
import spaceraze.world.enums.HighlightType;

import java.util.List;

public class VipMutator {

    private VipMutator(){}

    public static void checkVIPsInDestroyedShips(Spaceship aShip, Player aPlayer, Galaxy galaxy) {
        List<VIP> allVIPsOnShip = VipPureFunctions.findAllVIPsOnShip(aShip, galaxy.getAllVIPs());
        for (int i = 0; i < allVIPsOnShip.size(); i++) {
            VIP tempVIP = allVIPsOnShip.get(i);
            // if VIP is hard to kill he moves to the nearby planet
            if (tempVIP.isHardToKill()) {
                tempVIP.setLocation(aShip.getLocation());
                aPlayer.addToVIPReport(
                        "Your " + tempVIP.getName() + " travelling in " + aShip.getName() + " have moved to the planet "
                                + aShip.getLocation().getName() + " when the ship was destroyed.");
            } else { // annars dör VIPen
                galaxy.getAllVIPs().remove(tempVIP);
                aPlayer.addToVIPReport("Your " + tempVIP.getName() + " has been killed when your ship "
                        + aShip.getName() + " was destroyed at " + aShip.getLocation().getName() + ".");
                aPlayer.addToHighlights(tempVIP.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
            }
        }
    }

    public static void checkVIPsInSelfDestroyedShips(Spaceship aShip, Player aPlayer, Galaxy galaxy) {
        List<VIP> allVIPsOnShip = VipPureFunctions.findAllVIPsOnShip(aShip, galaxy.getAllVIPs());
        for (VIP tempVIP : allVIPsOnShip) {
            if (aShip.getLocation() == null) {
                // ship is retreating, vip is killed
                galaxy.removeVIP(tempVIP);
                aPlayer.addToVIPReport("Your " + tempVIP.getName() + " has been killed when your retreating ship "
                        + aShip.getName() + " was selfdestructed.");
                aPlayer.addToHighlights(tempVIP.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
            } else if (aShip.getLocation().getPlayerInControl() == aPlayer) {
                // om skeppet är vid en egen planet så flyttar VIPen dit
                tempVIP.setLocation(aShip.getLocation());
                aPlayer.addToVIPReport(
                        "Your " + tempVIP.getName() + " travelling in " + aShip.getName() + " have moved to the planet "
                                + aShip.getLocation().getName() + " when the ship was selfdestructed.");
            } else {
                // annars om VIPen kan vara på fientliga planeter så flyttar den dit
                if (tempVIP.canVisitEnemyPlanets()) {
                    tempVIP.setLocation(aShip.getLocation());
                    aPlayer.addToVIPReport("Your " + tempVIP.getName() + " travelling in " + aShip.getName()
                            + " have moved to the planet " + aShip.getLocation().getName()
                            + " when the ship was selfdestructed.");
                } else // annars om det är en neutral planet och VIP en är en guvenör flyttar han dit
                    if ((tempVIP.canVisitNeutralPlanets()) & (aShip.getLocation().getPlayerInControl() == null)) {
                        tempVIP.setLocation(aShip.getLocation());
                        aPlayer.addToVIPReport("Your " + tempVIP.getName() + " travelling in " + aShip.getName()
                                + " have moved to the planet " + aShip.getLocation().getName()
                                + " when the ship was selfdestructed.");
                    } else { // annars dör VIPen
                        galaxy.removeVIP(tempVIP);
                        aPlayer.addToVIPReport("Your " + tempVIP.getName() + " has been killed when your ship "
                                + aShip.getName() + " was selfdestructed at " + aShip.getLocation().getName() + ".");
                        aPlayer.addToHighlights(tempVIP.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
                    }
            }
        }
    }

    public static void checkVIPsInDestroyedTroop(Troop aTroop, Galaxy galaxy) {
        List<VIP> allVIPsOnTroop = VipPureFunctions.findAllVIPsOnTroop(aTroop, galaxy.getAllVIPs());
        Player aPlayer = aTroop.getOwner();
        for (VIP vip : allVIPsOnTroop) {
            // if VIP is hard to kill he moves to the nearby planet
            if (vip.isHardToKill()) {
                if (aTroop.getPlanetLocation() != null) {
                    vip.setLocation(aTroop.getPlanetLocation());
                    aPlayer.addToVIPReport("Your " + vip.getName() + " travelling in " + aTroop.getName()
                            + " have moved to the planet " + aTroop.getPlanetLocation().getName()
                            + " when the ship was destroyed.");
                } else { // VIP is on troop on a ship
                    vip.setLocation(aTroop.getShipLocation().getLocation());
                    aPlayer.addToVIPReport("Your " + vip.getName() + " travelling in " + aTroop.getName()
                            + " have moved to the planet " + aTroop.getShipLocation().getLocation().getName()
                            + " when the ship carrying the troop was destroyed.");
                }
            } else { // annars d�r VIPen
                galaxy.getAllVIPs().remove(vip);
                if (aTroop.getPlanetLocation() != null) {
                    aPlayer.addToVIPReport(
                            "Your " + vip.getName() + " has been killed when your troop " + aTroop.getName()
                                    + " was destroyed at " + aTroop.getPlanetLocation().getName() + ".");
                } else {
                    aPlayer.addToVIPReport(
                            "Your " + vip.getName() + " has been killed when your troop " + aTroop.getName()
                                    + " was destroyed at " + aTroop.getShipLocation().getLocation().getName()
                                    + " when the ship carrying the troop was destroyed.");
                }
                aPlayer.addToHighlights(vip.getName(), HighlightType.TYPE_OWN_VIP_KILLED);
            }
        }
    }
}
