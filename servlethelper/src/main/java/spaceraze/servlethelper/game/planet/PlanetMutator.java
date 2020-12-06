package spaceraze.servlethelper.game.planet;

import spaceraze.world.Planet;
import spaceraze.world.PlanetInformation;
import spaceraze.world.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetMutator {

    private PlanetMutator(){}

    public static List<PlanetInformation> createPlayerStartPlanetInformations(List<Planet> allPlanets, Player aPlayer) {
        return allPlanets.stream().map(planet -> new PlanetInformation(planet, aPlayer)).collect(Collectors.toList());
    }

    public static void setLastKnownOwner(String planetName, String newValue, int lastInfoTurn, List<PlanetInformation> planetInformations){
        PlanetInformation planetInformation = PlanetPureFunctions.findPlanetInfo(planetName, planetInformations);
        planetInformation.setLastKnownOwner(newValue);
        planetInformation.setLastInfoTurn(lastInfoTurn);
    }

    public static  void setLastKnownProductionAndResistance(String planetName, int newProd, int newRes, List<PlanetInformation> planetInformations){
        PlanetInformation planetInformation = PlanetPureFunctions.findPlanetInfo(planetName, planetInformations);
        planetInformation.setRes(newRes);
        planetInformation.setProd(newProd);
    }
}
