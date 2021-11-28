package spaceraze.servlethelper.game;

import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Functions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.TypeOfTroop;
import spaceraze.world.incomeExpensesReports.IncomeType;
import spaceraze.world.orders.Expense;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuildingPureFunctions {

    private BuildingPureFunctions(){}

    public static BuildingType getBuildingTypeByName(String name, GameWorld gameWorld){
        return gameWorld.getFactions().stream().flatMap(faction1 -> faction1.getBuildings().stream()).filter(buildingType1 -> buildingType1.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static BuildingType getBuildingType(String key, GameWorld gameWorld){
        return gameWorld.getFactions().stream().flatMap(faction1 -> faction1.getBuildings().stream()).filter(buildingType1 -> buildingType1.getKey().equalsIgnoreCase(key)).findFirst().orElse(null);
    }

    public static List<BuildingType> getNextBuildingSteps(BuildingType aBuildingType, List<BuildingType> buildings){
        List<BuildingType> tempBuildingTypes = new ArrayList<>();
        for(BuildingType buildingType :buildings){
            if(buildingType.getParentBuildingName() != null && buildingType.getParentBuildingName().equalsIgnoreCase(aBuildingType.getName())){
                tempBuildingTypes.add(buildingType);
            }
        }
        return tempBuildingTypes;
    }

    public static boolean isConstructable(Galaxy galaxy, Player player, Planet aPlanet, BuildingType buildingType, String buildingKey, PlayerBuildingImprovement improvement){
        Logger.finer("isConstructable, aPlanet: " + aPlanet.getName());
        Logger.finer("isConstructable, BuildingType: " + buildingType.getName());
        boolean isConstructable =  true;
        if((improvement != null && !improvement.isDeveloped()) || (improvement == null && !buildingType.isDeveloped())){
            isConstructable = false;
        }else if((buildingType.isWorldUnique() && isWorldUniqueBuild(galaxy,buildingType)) || (buildingType.isFactionUnique() && isFactionUniqueBuild(player, galaxy, buildingType)) || (buildingType.isPlayerUnique() && isPlayerUniqueBuild(player, galaxy, buildingType))){
            isConstructable = false;
        }else if(buildingType.isPlanetUnique() && BuildingPureFunctions.hasBuilding(aPlanet, buildingType.getKey())){
            isConstructable = false;
        }else if(buildingType.isWorldUnique() || buildingType.isFactionUnique()|| buildingType.isPlayerUnique() || buildingType.isPlanetUnique()){ // kollar om en unik byggnad redan har en child byggnad byggd. Om s� �r fallet s� �r den ocks� unik och d� skall det inte g� att bygga denna byggnad.
            if(buildingType.isWorldUnique() || buildingType.isFactionUnique() || buildingType.isPlayerUnique()){
                // check if a build order already exists
                if(player.getOrders().haveBuildingTypeBuildOrder(buildingType, buildingKey)){
                    isConstructable = false;
                }
            }
            if(isConstructable){
                for(int i=0; i < aPlanet.getBuildings().size();i++){
                    BuildingType aBuildingType = BuildingPureFunctions.getBuildingType(aPlanet.getBuildings().get(i).getTypeKey(), galaxy.getGameWorld());
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
        return buildingTypeExist(buildingType, null, null, galaxy);
    }

    public static boolean isFactionUniqueBuild(Player aPlayer, Galaxy galaxy, BuildingType buildingType) {
        return buildingTypeExist(buildingType, GameWorldHandler.getFactionByKey(aPlayer.getFactionKey(), galaxy.getGameWorld()), null, galaxy);
    }

    public static boolean isPlayerUniqueBuild(Player aPlayer, Galaxy galaxy, BuildingType buildingType) {
        return buildingTypeExist(buildingType, null, aPlayer, galaxy);
    }

    private static boolean buildingTypeExist(BuildingType aBuildingType, Faction aFaction, Player aPlayer, Galaxy galaxy) {
        boolean exist = false;
        List<Planet> planetsToCheck;
        if (aPlayer != null) {// playerUnique
            planetsToCheck = PlanetPureFunctions.getPlayersPlanets(aPlayer, galaxy);
        } else if (aFaction != null) {// factionUnique
            planetsToCheck = new ArrayList<Planet>();
            for (Player tempPlayer : galaxy.getPlayers()) {
                if (GameWorldHandler.getFactionByKey(tempPlayer.getFactionKey(), galaxy.getGameWorld()).getName().equals(aFaction.getName())) {
                    planetsToCheck.addAll(PlanetPureFunctions.getPlayersPlanets(tempPlayer, galaxy));
                }
            }
        } else {// worldUnique
            planetsToCheck = galaxy.getPlanets();
        }
        for (Planet tempPlanet : planetsToCheck) {
            for (Building tempBuilding : tempPlanet.getBuildings()) {
                if (tempBuilding.getTypeKey().equals(aBuildingType.getKey())) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    public static Building findBuilding(String buildingKey, Player aPlayer, Galaxy galaxy) {
        List<Planet> planetList = PlanetPureFunctions.getPlayersPlanets(aPlayer, galaxy);

        for (Planet aPlanet : planetList) {
            Building aBuilding = getBuilding(aPlanet, buildingKey);
            if (aBuilding != null) {
                return aBuilding;
            }
        }
        return null;
    }

    private static Building getBuilding(Planet planet, String key){
        Building tempBuilding = null;
        for(int i=0; i < planet.getBuildings().size();i++){
            if(planet.getBuildings().get(i).getKey().equalsIgnoreCase(key)){
                tempBuilding = planet.getBuildings().get(i);
            }
        }
        return tempBuilding;
    }


    public static boolean hasBuilding(Planet planet, String buildingTypeKey){
        for(int i = 0; i < planet.getBuildings().size();i++){
            if(planet.getBuildings().get(i).getTypeKey().equalsIgnoreCase(buildingTypeKey)){
                return true;
            }
        }
        return false;
    }

    public static List<BuildingType> getUpgradableBuildingTypes(Galaxy galaxy, Player player, BuildingType buildingType, Building aBuilding, Planet planet, PlayerBuildingImprovement improvement){
        List<BuildingType> upgradableBuildingTypes = getNextBuildingSteps(buildingType, PlayerPureFunctions.getAvailableBuildingTypes(galaxy, player, planet, null));
        List<BuildingType> playerUpgradableBuildingTypes = new ArrayList<>();

        for(BuildingType type : upgradableBuildingTypes){
            if(isConstructable(galaxy, player, planet, type, aBuilding.getKey(), improvement)){
                playerUpgradableBuildingTypes.add(type);
            }
        }
        return playerUpgradableBuildingTypes;
    }

    //Gives a planets buildingTypes possible to construct, excludes upgrades of existing buildings.
    public static List<BuildingType> getAvailableBuildingsToConstruct(Galaxy galaxy, Player player, Planet planet){

        return PlayerPureFunctions.getAvailableBuildingTypes(galaxy, player, planet, null).stream()
                .filter(buildingType ->
                        buildingType.getParentBuildingName() == null  && isConstructable(galaxy, player, planet, buildingType, null, null)).collect(Collectors.toList());

    }

    public static BuildingType getUpgradeBuilding(Building currentBuilding, Player player, List<Expense> expenses){
        Optional<Expense> expense1 = expenses.stream().filter(expense -> ExpensePureFunction.isBuilding(expense, currentBuilding)).findFirst();
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

    public static int getPlanetBuildingsBonus(Planet tempPlanet, TurnInfo playerTurnInfo, GameWorld gameWorld) {
        int tempIncom = 0;
        for (Building building : tempPlanet.getBuildings()) {
            BuildingType buildingType = BuildingPureFunctions.getBuildingType(building.getTypeKey(), gameWorld);
            if (tempPlanet.isOpen()) {
                if (!buildingType.isInOrbit() || !tempPlanet.isBesieged()) {
                    int openInc = building.getOpenPlanetBonus();
                    tempIncom += openInc;
                    if (openInc > 0) {
                        if (playerTurnInfo != null) {
                            playerTurnInfo.addToLatestIncomeReport(IncomeType.BUILDING,
                                    buildingType.getName() + " open planet bonus", tempPlanet.getName(), openInc);
                        }
                    }
                }
            } else {
                if (!buildingType.isInOrbit() || !tempPlanet.isBesieged()) {
                    int closedInc = building.getClosedPlanetBonus();
                    tempIncom += closedInc;
                    if (closedInc > 0) {
                        if (playerTurnInfo != null) {
                            playerTurnInfo.addToLatestIncomeReport(IncomeType.BUILDING,
                                    buildingType.getName() + " closed planet bonus", tempPlanet.getName(), closedInc);
                        }
                    }
                }
            }
        }
        return tempIncom;
    }

    public static List<String> getAbilitiesStrings(BuildingType buildingType){
        List<String> allStrings = new LinkedList<>();


        if (buildingType.isWorldUnique()){
            allStrings.add("Is World Unique");
        }
        if (buildingType.isFactionUnique()){
            allStrings.add("Is Faction Unique");
        }
        if (buildingType.isPlayerUnique()){
            allStrings.add("Is Player Unique");
        }
        if (buildingType.isPlanetUnique()){
            allStrings.add("Is Planet Unique");
        }
        if (buildingType.isSpaceport()){
            allStrings.add("Spaceport");
        }
        if (buildingType.getOpenPlanetBonus() > 0){
            allStrings.add("Open Planet Bonus: " + buildingType.getOpenPlanetBonus());
        }
        if (buildingType.getClosedPlanetBonus() > 0){
            allStrings.add("Closed Planet Bonus: " + buildingType.getClosedPlanetBonus());
        }
        if (buildingType.getTechBonus() > 0){
            allStrings.add("Tech Bonus: " + buildingType.getTechBonus() + "%");
        }
        if (buildingType.getWharfSize() > 0){
            allStrings.add("Wharf Size: " + buildingType.getWharfSize());
        }
        if (buildingType.getTroopSize() > 0){
            allStrings.add("Build Troop Capacity: " + buildingType.getTroopSize());
        }
        if (buildingType.isAlienKiller()){
            allStrings.add("Alien Killer: prevent infestator to infestate the planet");
        }
        if (buildingType.getCounterEspionage() > 0){
            allStrings.add("Counter-espionage: " + buildingType.getCounterEspionage() + "%");
        }
        if (buildingType.getExterminator() > 0){
            allStrings.add("Exterminator: " + buildingType.getExterminator() + "%");
        }
        if (buildingType.getResistanceBonus() > 0){
            allStrings.add("Resistance bonus: " + buildingType.getResistanceBonus());
        }
        if (buildingType.getShieldCapacity() > 0){
            allStrings.add("Shield Capacity : " + buildingType.getShieldCapacity());
        }
        if (buildingType.getCannonDamage() > 0){
            allStrings.add("Cannon Damage: " + buildingType.getCannonDamage());
            allStrings.add("Cannon Rate Of Fire: " + buildingType.getCannonRateOfFire());
            allStrings.add("Cannon hit chance: " + buildingType.getCannonHitChance());
        }

        if (buildingType.getTypeOfTroop().size() > 0){
            String tmp = "Troop building:";
            boolean addComma = false;
            for (TypeOfTroop type : buildingType.getTypeOfTroop()) {
                if (addComma){
                    tmp += ",";
                }
                tmp += " " + type;
                addComma = true;
            }
            allStrings.add(tmp);
        }

        if (buildingType.getBuildVIPTypes().size() > 0){
            String tmp = "VIP building:";
            boolean addComma = false;
            for (VIPType vipType : buildingType.getBuildVIPTypes()) {
                if (addComma){
                    tmp += ",";
                }
                tmp += " " + vipType.getName();
                addComma = true;
            }
            allStrings.add(tmp);
        }

        if(!buildingType.isVisibleOnMap()){
            allStrings.add("Visible On Map: " + Functions.getYesNo(buildingType.isVisibleOnMap()));
        }

        if (buildingType.isAutoDestructWhenConquered()){
            allStrings.add("Will Auto Destruct When Conquered");
        }

        if (buildingType.isInOrbit()){
            allStrings.add("In orbit: can be destroyed by enemy ships in orbit if undefended");
        }else{
            allStrings.add("Placed on planets surface");
        }

        return allStrings;
    }

    public static int getBuildCost(BuildingType buildingType, int vipBuildBonus){
        int tempBuildCost = buildingType.getBuildCost();
        if (vipBuildBonus > 0){
            int vipBuildbonus = 100 - vipBuildBonus;
            double tempBuildBonus = vipBuildbonus / 100.0;
            tempBuildCost = (int) Math.round(tempBuildCost * tempBuildBonus);
            if (tempBuildCost < 1){
                tempBuildCost = 1;
            }
        }
        return tempBuildCost;
    }

}
