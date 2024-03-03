package spaceraze.servlethelper.game.planet;

import spaceraze.servlethelper.game.BuildingPureFunctions;
import spaceraze.servlethelper.game.vip.VipMutator;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.world.*;
import spaceraze.world.enums.HighlightType;

import java.util.ArrayList;
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
        planet.setResistance(1 + GameWorldHandler.getFactionByUuid(attacker.getFactionUuid(), gameWorld).getResistanceBonus());
        planet.setPlayerInControl(attacker);
        if (planet.isHasNeverSurrendered()){
            planet.setHasNeverSurrendered(false);
            // lägg till en slumpvis VIP till infestator spelaren
            VIP aVIP = VipMutator.maybeAddVIP(attacker, attacker.getGalaxy());
            if (aVIP != null){
                VipMutator.setShipLocation(aVIP, planet);
                VIPType vipType = VipPureFunctions.getVipTypeByUuid(aVIP.getTypeUuid(), gameWorld);
                attacker.addToVIPReport("When you conquered " + planet.getName() + " you have found a " + vipType.getName() + " who has joined your service.");
                attacker.addToHighlights(vipType.getName(), HighlightType.TYPE_VIP_JOINS);
            }
        }
    }

    public static  void joinsVisitingInfector(Planet planet, VIP tempInf, GameWorld gameWorld){
        planet.setPopulation(0);
        planet.setResistance(planet.getResistance() + GameWorldHandler.getFactionByUuid(tempInf.getBoss().getFactionUuid(), gameWorld).getResistanceBonus());
        // destroy all buildings, when an alien conquers a planet it is always razed in the process
        planet.getBuildings().clear();
        // spaceStation = null;
        tempInf.getBoss().addToGeneral("The planet " + planet.getName() + " has been infected by your " + VipPureFunctions.getVipTypeByUuid(tempInf.getTypeUuid(), gameWorld).getName() + " to join your forces!");
        tempInf.getBoss().addToHighlights(planet.getName(),HighlightType.TYPE_PLANET_JOINS);
        if (planet.isHasNeverSurrendered()){
            planet.setHasNeverSurrendered(false);
            // lägg till en slumpvis VIP till denna spelare
            VIP aVIP = VipMutator.maybeAddVIP(tempInf.getBoss(), tempInf.getBoss().getGalaxy());
            if (aVIP != null){
                VipMutator.setShipLocation(aVIP, planet);
                tempInf.getBoss().addToVIPReport("When you conquered " + planet.getName() + " you have found a " + VipPureFunctions.getVipTypeByUuid(aVIP.getTypeUuid(), gameWorld).getName() + " who has joined your service.");
                tempInf.getBoss().addToHighlights(VipPureFunctions.getVipTypeByUuid(aVIP.getTypeUuid(), gameWorld).getName(),HighlightType.TYPE_VIP_JOINS);
            }
        }
        if (planet.getPlayerInControl() != null){
            planet.getPlayerInControl().addToGeneral("The planet " + planet.getName() + " has been infected by Governor " + tempInf.getBoss().getGovernorName() + " and is lost!");
            planet.getPlayerInControl().addToHighlights(planet.getName(),HighlightType.TYPE_OWN_PLANET_INFESTATED);
        }
        planet.setPlayerInControl(tempInf.getBoss());
    }

    public static void joinsVisitingDiplomat(Planet planet, VIP tempVIP, boolean addInfoToPlayer, GameWorld gameWorld){
        planet.setResistance(planet.getResistance() + tempVIP.getBoss().getResistanceBonus());  // olika typer av spelare får olika res på ny erövrade planeter?
        if(addInfoToPlayer){
            tempVIP.getBoss().addToGeneral("The neutral planet " + planet.getName() + " has been convinced by your " + VipPureFunctions.getVipTypeByUuid(tempVIP.getTypeUuid(), gameWorld).getName() + " to join your forces!");
            tempVIP.getBoss().addToHighlights(planet.getName(),HighlightType.TYPE_PLANET_JOINS);
        }
        if (planet.isHasNeverSurrendered()){
            planet.setHasNeverSurrendered(false);
            // l�gg till en slumpvis VIP till denna spelare
            VIP aVIP = VipMutator.maybeAddVIP(tempVIP.getBoss(), tempVIP.getBoss().getGalaxy());
            if (aVIP != null){
                VipMutator.setShipLocation(aVIP, planet);
                if(addInfoToPlayer){
                    tempVIP.getBoss().addToVIPReport("When you conquered " + planet.getName() + " you have found a " + VipPureFunctions.getVipTypeByUuid(aVIP.getTypeUuid(), gameWorld).getName() + " who has joined your service.");
                    tempVIP.getBoss().addToHighlights(VipPureFunctions.getVipTypeByUuid(aVIP.getTypeUuid(), gameWorld).getName(),HighlightType.TYPE_VIP_JOINS);
                }
            }
        }
        planet.setPlayerInControl(tempVIP.getBoss());
    }

    public static void reverseVisibility(Planet planet){
        planet.setOpen(!planet.isOpen());
        if (planet.isOpen()){
            planet.setLastKnownPlayerInControl(planet.getPlayerInControl());
        }
    }
    public static void setRazed(Planet planet){
        planet.setProd(0);
        planet.setResistance(0);
        if (planet.isOpen()){
            reverseVisibility(planet);
        }
        planet.setBuildings(new ArrayList<Building>());
        planet.setPlayerInControl(null);
    }

    public static void removeBuilding(Planet planet, String  key) {
        planet.getBuildings().removeIf(building -> building.getUuid().equalsIgnoreCase(key));
    }

    /**
     *
     * @param conqueringPlayer if no conquering player (null) do not create any messages
     */
    public static void destroyBuildingsThatCanNotBeOverTaken(Planet planet, Player conqueringPlayer, GameWorld gameWorld){
        int i=0;
        while(i< planet.getBuildings().size()){
            BuildingType buildingType = BuildingPureFunctions.getBuildingTypeByUuid(planet.getBuildings().get(i).getTypeUuid(), gameWorld);
            if(buildingType.isAutoDestructWhenConquered()){
                if (conqueringPlayer != null){
                    conqueringPlayer.addToGeneral("The " + buildingType.getName() + " on the planet " + planet.getName() + " has been destroyed.");
                    planet.getPlayerInControl().addToGeneral("Your " + buildingType.getName() + " on the planet " + planet.getName() + " has been destroyed.");
                }
                planet.getBuildings().remove(i);
            }else{
                i++;
            }
        }
    }



}
