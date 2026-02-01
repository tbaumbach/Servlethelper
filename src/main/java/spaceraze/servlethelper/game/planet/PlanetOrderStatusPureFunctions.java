package spaceraze.servlethelper.game.planet;

import spaceraze.game.PlanetOrderStatus;

import java.util.List;

public class PlanetOrderStatusPureFunctions {

    public static PlanetOrderStatus getPlanetOrderStatus(String planetUuid, List<PlanetOrderStatus> planetOrderStatuses){
        return planetOrderStatuses.stream().filter(status -> status.getMapPlanetUuid().equals(planetUuid)).findFirst().orElseThrow();
    }

    public static boolean isAttackIfNeutral(String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(planetUuid, planetOrderStatuses).isAttackIfNeutral();
    }

    public static boolean isDestroyOrbitalBuildings(String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(planetUuid, planetOrderStatuses).isDestroyOrbitalBuildings();
    }

    public static boolean isDoNotBesiege(String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(planetUuid, planetOrderStatuses).isDoNotBesiege();
    }

    public static int getMaxBombardment(String planetUuid, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(planetUuid, planetOrderStatuses).getMaxBombardment();
    }
}
