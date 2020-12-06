package spaceraze.servlethelper.game.planet;

import spaceraze.world.Planet;
import spaceraze.world.PlanetOrderStatus;

import java.util.ArrayList;
import java.util.List;

public class PlanetOrderStatusMutator {

    public static List<PlanetOrderStatus> createPlanetOrderStatuses(List<Planet> planets){
        List<PlanetOrderStatus> planetOrderStatuses = new ArrayList<>();
        for (Planet planet : planets) {
            planetOrderStatuses.add(PlanetOrderStatus.builder().planetName(planet.getName()).build());
        }
        return planetOrderStatuses;
    }



    // easy to use setters
    public static void setAttackIfNeutral(boolean attackIfNeutral,String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(aPlanetName, planetOrderStatuses).setAttackIfNeutral(attackIfNeutral);
    }

    public static void setDestroyOrbitalBuildings(boolean destroyOrbitalBuildings, String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(aPlanetName, planetOrderStatuses).setDestroyOrbitalBuildings(destroyOrbitalBuildings);
    }

    public static void setDoNotBesiege(boolean doNotBesiege, String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(aPlanetName, planetOrderStatuses).setDoNotBesiege(doNotBesiege);
    }

    public static void setMaxBombardment(int maxBombardment, String aPlanetName, List<PlanetOrderStatus> planetOrderStatuses) {
        PlanetOrderStatusPureFunctions.getPlanetOrderStatus(aPlanetName, planetOrderStatuses).setMaxBombardment(maxBombardment);
    }

}
