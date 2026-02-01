package spaceraze.servlethelper.game.planet;

import spaceraze.game.Planet;
import spaceraze.game.PlanetOrderStatus;

import java.util.ArrayList;
import java.util.List;

public class PlanetOrderStatusMutator {

    public static List<PlanetOrderStatus> createPlanetOrderStatuses(List<Planet> planets){
        List<PlanetOrderStatus> planetOrderStatuses = new ArrayList<>();
        for (Planet planet : planets) {
            planetOrderStatuses.add(PlanetOrderStatus.builder().mapPlanetUuid(planet.getMapPlanetUuid()).build());
        }
        return planetOrderStatuses;
    }



    // easy to use setters
    public static void setAttackIfNeutral(boolean attackIfNeutral,String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(planetUuid, planetOrderStatuses).setAttackIfNeutral(attackIfNeutral);
    }

    public static void setDestroyOrbitalBuildings(boolean destroyOrbitalBuildings, String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(planetUuid, planetOrderStatuses).setDestroyOrbitalBuildings(destroyOrbitalBuildings);
    }

    public static void setDoNotBesiege(boolean doNotBesiege, String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(planetUuid, planetOrderStatuses).setDoNotBesiege(doNotBesiege);
    }

    public static void setMaxBombardment(int maxBombardment, String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(planetUuid, planetOrderStatuses).setMaxBombardment(maxBombardment);
    }

}
