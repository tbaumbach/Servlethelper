package spaceraze.servlethelper.game;

import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.orders.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuildingPureFunctions {

    private BuildingPureFunctions(){}

    public static List<BuildingType> getNextBuildingSteps(BuildingType aBuildingType, List<BuildingType> buildings){
        List<BuildingType> tempBuildingTypes = new ArrayList<>();
        for(BuildingType buildingType :buildings){
            if(buildingType.getParentBuildingName() != null && buildingType.getParentBuildingName().equalsIgnoreCase(aBuildingType.getName())){
                tempBuildingTypes.add(buildingType);
            }
        }
        return tempBuildingTypes;
    }

    public static boolean isConstructable(Galaxy galaxy, Player player, Planet aPlanet, BuildingType buildingType, int buildingId, PlayerBuildingImprovement improvement){
        Logger.finer("isConstructable, aPlanet: " + aPlanet.getName());
        Logger.finer("isConstructable, BuildingType: " + buildingType.getName());
        boolean isConstructable =  true;
        if((improvement != null && !improvement.isDeveloped()) || (improvement == null && !buildingType.isDeveloped())){
            isConstructable = false;
        }else if((buildingType.isWorldUnique() && isWorldUniqueBuild(galaxy,buildingType)) || (buildingType.isFactionUnique() && isFactionUniqueBuild(player, galaxy, buildingType)) || (buildingType.isPlayerUnique() && isPlayerUniqueBuild(player, galaxy, buildingType))){
            isConstructable = false;
        }else if(buildingType.isPlanetUnique() && aPlanet.hasBuilding(buildingType.getName())){
            isConstructable = false;
        }else if(buildingType.isWorldUnique() || buildingType.isFactionUnique()|| buildingType.isPlayerUnique() || buildingType.isPlanetUnique()){ // kollar om en unik byggnad redan har en child byggnad byggd. Om s� �r fallet s� �r den ocks� unik och d� skall det inte g� att bygga denna byggnad.
            if(buildingType.isWorldUnique() || buildingType.isFactionUnique() || buildingType.isPlayerUnique()){
                // check if a build order already exists
                if(player.getOrders().haveBuildingTypeBuildOrder(buildingType, buildingId)){
                    isConstructable = false;
                }
            }
            if(isConstructable){
                for(int i=0; i < aPlanet.getBuildings().size();i++){
                    BuildingType aBuildingType = aPlanet.getBuildings().get(i).getBuildingType();
                    Logger.finer("aBuildingType: " + aBuildingType.getName());
                    if(checkIfAUniqueChildBuildingIsAlreadyBuild(aBuildingType, aPlanet.getPlayerInControl(), buildingType.getName())){
                        isConstructable = false;
                    }
                }
            }
        }

        return isConstructable;
    }

    private static boolean checkIfAUniqueChildBuildingIsAlreadyBuild(BuildingType aBuildingType, Player aPlayer, String buildingName){
        boolean childAlreadyBuild = false;
        Logger.finer("aBuildingType.getName(): " + aBuildingType.getName());
        if (aBuildingType.getParentBuildingName() != null){
            Logger.finer("aBuildingType.getParentBuilding().getName(): " + aBuildingType.getParentBuildingName());
            if(aBuildingType.getParentBuildingName().equalsIgnoreCase(buildingName)){
                childAlreadyBuild = true;// det finns en child byggnad som är byggd och eftersom denna byggnad är unik så måste den också vara det och då stoppa bygge av denna byggnad.
            }else{
                //TODO 2020-05-24 Risk for eternal loop, PlayerPureFunctions.findBuildingType(aBuildingType.getParentBuilding().getName(), aPlayer) calling this method.
                BuildingType tempBuildingType = PlayerPureFunctions.findBuildingType(aBuildingType.getParentBuildingName(), aPlayer);
                if(tempBuildingType != null){
                    Logger.finer("tempBuildingType.getName(): " + tempBuildingType.getName());
                    childAlreadyBuild = checkIfAUniqueChildBuildingIsAlreadyBuild(tempBuildingType, aPlayer, buildingName);
                }
            }
        }

        return childAlreadyBuild;
    }

    public static boolean isWorldUniqueBuild(Galaxy galaxy, BuildingType buildingType) {
        return galaxy.buildingTypeExist(buildingType, null, null);
    }

    public static boolean isFactionUniqueBuild(Player aPlayer, Galaxy galaxy, BuildingType buildingType) {
        return galaxy.buildingTypeExist(buildingType, aPlayer.getFaction(), null);
    }

    public static boolean isPlayerUniqueBuild(Player aPlayer, Galaxy galaxy, BuildingType buildingType) {
        return galaxy.buildingTypeExist(buildingType, null, aPlayer);
    }

    public static List<BuildingType> getUpgradableBuildingTypes(Galaxy galaxy, Player player, BuildingType buildingType, Building aBuilding, Planet planet, PlayerBuildingImprovement improvement){
        List<BuildingType> upgradableBuildingTypes = getNextBuildingSteps(buildingType, PlayerPureFunctions.getAvailableBuildingTypes(galaxy, player, planet, -1));
        List<BuildingType> playerUpgradableBuildingTypes = new ArrayList<>();

        for(BuildingType type : upgradableBuildingTypes){
            if(isConstructable(galaxy, player, planet, type, aBuilding.getUniqueId(), improvement)){
                playerUpgradableBuildingTypes.add(type);
            }
        }
        return playerUpgradableBuildingTypes;
    }

    //Gives a planets buildingTypes possible to construct, excludes upgrades of existing buildings.
    public static List<BuildingType> getAvailableBuildingsToConstruct(Galaxy galaxy, Player player, Planet planet){

        return PlayerPureFunctions.getAvailableBuildingTypes(galaxy, player, planet, -1).stream()
                .filter(buildingType ->
                        buildingType.getParentBuildingName() == null  && isConstructable(galaxy, player, planet, buildingType, -1, null)).collect(Collectors.toList());

    }

    public static BuildingType getUpgradeBuilding(Building currentBuilding, Player player, List<Expense> expenses){
        Optional<Expense> expense1 = expenses.stream().filter(expense -> expense.isBuilding(currentBuilding)).findFirst();
        return expense1.isPresent() ? PlayerPureFunctions.findBuildingType(expense1.get().getBuildingTypeName(), player) : null;
    }

    public static BuildingType getNewBuilding(Planet currentPlanet, Player player, List<Expense> expenses){
        Optional<Expense> expense1 = expenses.stream().filter(expense -> expense.isBuilding(currentPlanet)).findFirst();
        return expense1.isPresent() ? PlayerPureFunctions.findBuildingType(expense1.get().getBuildingTypeName(), player) : null;
    }

    public static List<BuildingType> getRootBuildings(Player player){
        return getRootBuildings(PlayerPureFunctions.getBuildingTypes(player));
    }

    public static List<BuildingType> getRootBuildings(List<BuildingType> buildingTypes){
        return buildingTypes.stream().filter(buildingType -> buildingType.getParentBuildingName() == null).collect(Collectors.toList());
    }

}
