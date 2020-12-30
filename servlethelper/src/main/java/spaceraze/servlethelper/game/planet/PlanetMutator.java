package spaceraze.servlethelper.game.planet;

import spaceraze.servlethelper.game.vip.VipMutator;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.world.*;
import spaceraze.world.enums.HighlightType;

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

    public static void infectedByAttacker(Planet planet, Player attacker, GameWorld gameWorld){
        attacker.addToHighlights(planet.getName(), HighlightType.TYPE_PLANET_INFESTATED);
        attacker.addToGeneral("You have infected the planet " + planet.getName());
        planet.setProd(0);
        planet.setResistance(1 + attacker.getFaction().getResistanceBonus());
        planet.setPlayerInControl(attacker);
        if (planet.isHasNeverSurrendered()){
            planet.setHasNeverSurrendered(false);
            // lägg till en slumpvis VIP till infestator spelaren
            VIP aVIP = VipMutator.maybeAddVIP(attacker, attacker.getGalaxy());
            if (aVIP != null){
                VipMutator.setShipLocation(aVIP, planet);
                VIPType vipType = VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld);
                attacker.addToVIPReport("When you conquered " + planet.getName() + " you have found a " + vipType.getName() + " who has joined your service.");
                attacker.addToHighlights(vipType.getName(), HighlightType.TYPE_VIP_JOINS);
            }
        }
    }

    public static  void joinsVisitingInfector(Planet planet, VIP tempInf, GameWorld gameWorld){
        planet.setPopulation(0);
        planet.setResistance(planet.getResistance() + tempInf.getBoss().getFaction().getResistanceBonus());
        // destroy all buildings, when an alien conquers a planet it is always razed in the process
        planet.getBuildings().clear();
        // spaceStation = null;
        tempInf.getBoss().addToGeneral("The planet " + planet.getName() + " has been infected by your " + VipPureFunctions.getVipTypeByKey(tempInf.getTypeKey(), gameWorld).getName() + " to join your forces!");
        tempInf.getBoss().addToHighlights(planet.getName(),HighlightType.TYPE_PLANET_JOINS);
        if (planet.isHasNeverSurrendered()){
            planet.setHasNeverSurrendered(false);
            // lägg till en slumpvis VIP till denna spelare
            VIP aVIP = VipMutator.maybeAddVIP(tempInf.getBoss(), tempInf.getBoss().getGalaxy());
            if (aVIP != null){
                VipMutator.setShipLocation(aVIP, planet);
                tempInf.getBoss().addToVIPReport("When you conquered " + planet.getName() + " you have found a " + VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld).getName() + " who has joined your service.");
                tempInf.getBoss().addToHighlights(VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld).getName(),HighlightType.TYPE_VIP_JOINS);
            }
        }
        if (planet.getPlayerInControl() != null){
            planet.getPlayerInControl().addToGeneral("The planet " + planet.getName() + " has been infected by Governor " + tempInf.getBoss().getGovernorName() + " and is lost!");
            planet.getPlayerInControl().addToHighlights(planet.getName(),HighlightType.TYPE_OWN_PLANET_INFESTATED);
        }
        planet.setPlayerInControl(tempInf.getBoss());
    }

    public static void joinsVisitingDiplomat(Planet planet, VIP tempVIP, boolean addInfoToPlayer, GameWorld gameWorld){
        planet.setResistance(planet.getResistance() + tempVIP.getBoss().getFaction().getResistanceBonus());  // olika typer av spelare får olika res på ny erövrade planeter?
        if(addInfoToPlayer){
            tempVIP.getBoss().addToGeneral("The neutral planet " + planet.getName() + " has been convinced by your " + VipPureFunctions.getVipTypeByKey(tempVIP.getTypeKey(), gameWorld).getName() + " to join your forces!");
            tempVIP.getBoss().addToHighlights(planet.getName(),HighlightType.TYPE_PLANET_JOINS);
        }
        if (planet.isHasNeverSurrendered()){
            planet.setHasNeverSurrendered(false);
            // l�gg till en slumpvis VIP till denna spelare
            VIP aVIP = VipMutator.maybeAddVIP(tempVIP.getBoss(), tempVIP.getBoss().getGalaxy());
            if (aVIP != null){
                VipMutator.setShipLocation(aVIP, planet);
                if(addInfoToPlayer){
                    tempVIP.getBoss().addToVIPReport("When you conquered " + planet.getName() + " you have found a " + VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld).getName() + " who has joined your service.");
                    tempVIP.getBoss().addToHighlights(VipPureFunctions.getVipTypeByKey(aVIP.getTypeKey(), gameWorld).getName(),HighlightType.TYPE_VIP_JOINS);
                }
            }
        }
        planet.setPlayerInControl(tempVIP.getBoss());
    }

}
