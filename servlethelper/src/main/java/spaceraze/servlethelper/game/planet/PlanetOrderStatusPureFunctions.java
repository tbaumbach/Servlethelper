package spaceraze.servlethelper.game.planet;

import spaceraze.world.PlanetOrderStatus;

import java.util.List;

public class PlanetOrderStatusPureFunctions {

    public static PlanetOrderStatus getPlanetOrderStatus(String planetName, List<PlanetOrderStatus> planetOrderStatuses){
        return planetOrderStatuses.stream().filter(status -> status.getPlanetName().equals(planetName)).findFirst().orElseThrow();
    }

    public static boolean isAttackIfNeutral(String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(aPlanetName, planetOrderStatuses).isAttackIfNeutral();
    }

    public static boolean isDestroyOrbitalBuildings(String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(aPlanetName, planetOrderStatuses).isDestroyOrbitalBuildings();
    }

    public static boolean isDoNotBesiege(String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(aPlanetName, planetOrderStatuses).isDoNotBesiege();
    }

    public static int getMaxBombardment(String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        return getPlanetOrderStatus(aPlanetName, planetOrderStatuses).getMaxBombardment();
    }
}
