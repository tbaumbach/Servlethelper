package spaceraze.servlethelper.game.orders;

import spaceraze.world.Planet;
import spaceraze.world.orders.Orders;
import spaceraze.world.orders.PlanetNotesChange;
import spaceraze.world.orders.TroopToPlanetMovement;

import java.util.LinkedList;
import java.util.List;

public class OrderPureFunctions {

    private OrderPureFunctions(){}

    public static List<TroopToPlanetMovement> getTroopToPlanetMoves(String uuid, Orders orders) {
        List<TroopToPlanetMovement> troopMoves = new LinkedList<>();
        for (TroopToPlanetMovement aTroopToPlanetMove : orders.getTroopToPlanetMoves()) {
            if (uuid.equalsIgnoreCase(aTroopToPlanetMove.getMapPlanetUuid())) {
                troopMoves.add(aTroopToPlanetMove);
            }
        }
        return troopMoves;
    }

    public static PlanetNotesChange getPlanetNotesChange(Orders orders, Planet aPlanet) {
        PlanetNotesChange planetNotesChange = null;
        for (PlanetNotesChange aPlanetNotesChange : orders.getPlanetNotesChanges()) {
            if (aPlanet.getMapPlanetUuid().equals(aPlanetNotesChange.getPlanetUuid())) {
                planetNotesChange = aPlanetNotesChange;
            }
        }
        return planetNotesChange;
    }
}
