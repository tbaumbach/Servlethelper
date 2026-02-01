package spaceraze.servlethelper.game.expenses;

import spaceraze.game.*;
import spaceraze.map.GalaxyMap;
import spaceraze.map.MapPlanet;
import spaceraze.servlethelper.game.BlackMarketPureFunctions;
import spaceraze.servlethelper.game.building.BuildingPureFunctions;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.game.orders.Expense;
import spaceraze.game.orders.Orders;

public class ExpensePureFunction {

    private ExpensePureFunction(){}

    public static int getExpensesCost(Galaxy aGalaxy, Player player, GalaxyMap galaxyMap, GameWorld gameWorld){
        int totalCost = 0;
        for (Expense expense : player.getOrders().getExpenses()){
            totalCost = totalCost + ExpensePureFunction.getCost(expense, aGalaxy, player, galaxyMap, gameWorld);
        }
        return totalCost;
    }

    public static int getCost(Expense expense, Galaxy galaxy, Player player, GalaxyMap galaxyMap, GameWorld gameWorld){
        Orders o = player.getOrders();
        int cost = 0;
        String type = expense.getType();
        Logger.finer("Expense.getCost(Orders o, Galaxy aGalaxy) type: " +  expense.getType());
        MapPlanet mapPlanet = expense.getMapPlanetUuid() != null && !"".equalsIgnoreCase(expense.getMapPlanetUuid()) ? PlanetPureFunctions.getMapPlanet(galaxyMap, expense.getMapPlanetUuid()) : null;
        Planet planet = mapPlanet != null ? PlanetPureFunctions.getPlanet(mapPlanet.getUuid(), galaxy) : null;


        if (type.equalsIgnoreCase("pop")){
            cost = planet.getPopulation();
        }else
        if (type.equalsIgnoreCase("res")){
            cost = planet.getResistance();
        }else
        if (type.equalsIgnoreCase("building")){
            Logger.finer("planet: " + expense.getMapPlanetUuid());
            Logger.finer("planet.getPlayerInControl(): " + player);
            VIP tempVIP = VipPureFunctions.findVIPBuildingBuildBonus(planet, player, o, galaxy, gameWorld);
            int vipBuildBonus = tempVIP == null ? 0 : VipPureFunctions.getVipTypeByUuid(tempVIP.getTypeUuid(), gameWorld).getBuildingBuildBonus();
            BuildingType aBuildingType = PlayerPureFunctions.findOwnBuildingTypeByUuid(expense.getBuildingTypeUuid(), player, gameWorld);
            cost =  BuildingPureFunctions.getBuildCost(aBuildingType, vipBuildBonus);
        }else
        if (type.equalsIgnoreCase("buildship")){
            // kollar f�rst om det finns en engineer vid planeten
            VIP tempEngineer = VipPureFunctions.findVIPShipBuildBonus(planet, player, o, galaxy, gameWorld);
            int vipBuildBonus = tempEngineer == null ? 0 : VipPureFunctions.getVipTypeByUuid(tempEngineer.getTypeUuid(), gameWorld).getShipBuildBonus();
            cost = SpaceshipPureFunctions.getBuildCost(PlayerPureFunctions.findOwnSpaceshipType(expense.getSpaceshipTypeUuid(),  player, gameWorld), vipBuildBonus);
        }
        else
        if (type.equalsIgnoreCase("buildtroop")){
            // first check if there is an engineer at the planet
            VIP tempVIP = VipPureFunctions.findVIPTroopBuildBonus(planet, player , o, galaxy, gameWorld);
            TroopType troopType = PlayerPureFunctions.findOwnTroopType(expense.getTroopTypeUuid(), player, gameWorld);
            int vipBuildBonus = tempVIP == null ? 0 : VipPureFunctions.getVipTypeByUuid(tempVIP.getTypeUuid(), gameWorld).getTroopBuildBonus();
            cost = TroopPureFunctions.getCostBuild(troopType, vipBuildBonus);

        }else
        if (type.equalsIgnoreCase("buildVIP")){
            VIPType tempVIPType = VipPureFunctions.getVipTypeByUuid(expense.getTypeVIPUuid(), gameWorld);
            cost = tempVIPType.getBuildCost();
        }else
        if (type.equalsIgnoreCase("transaction")){
            cost = expense.getSum();
        }else
        if (type.equalsIgnoreCase("blackmarketbid")){
            cost = expense.getBlackMarketBid().getCost();
        }else
        if (type.equalsIgnoreCase("reconstruct")){
            cost = GameWorldHandler.getFactionByUuid(player.getFactionUuid(), gameWorld).getReconstructCostBase() + planet.getBasePopulation();
        }else{
            if (type.equalsIgnoreCase("research")){
                cost = expense.getResearchOrder().getCost();
            }
        }
        return cost;
    }

    public static String getText(Galaxy galaxy, int cost, Expense expense, GalaxyMap galaxyMap, GameWorld gameWorld){
        String planetName = PlanetPureFunctions.getPlanetName(galaxyMap, expense.getMapPlanetUuid());
        String returnString = "";
        if (expense.getType().equalsIgnoreCase("pop")){
            returnString = "Increase production on " + planetName + " with +1.";
        }else
        if (expense.getType().equalsIgnoreCase("res")){
            returnString = "Increase resistance on " + planetName + " with +1.";
        }else
        if (expense.getType().equalsIgnoreCase("building")){
            BuildingType buildingType = BuildingPureFunctions.getBuildingTypeByUuid(expense.getBuildingTypeUuid(), gameWorld);
            if(buildingType.getParentBuildingType() == null){
                returnString = "Build new " + buildingType.getName() + " at " + planetName + ".";
            }else{
                returnString = "Upgrade " + BuildingPureFunctions.getBuildingTypeByUuid(buildingType.getParentBuildingType(), gameWorld).getName() + " to " + buildingType.getName() + " at " + planetName + ".";
            }
        }else
        if (expense.getType().equalsIgnoreCase("buildship")){
            SpaceshipType sst = SpaceshipPureFunctions.getSpaceshipTypeByUuid(expense.getSpaceshipTypeUuid(), gameWorld);
            returnString = "Build new " + sst.getName() + " at " + planetName + ".";
        }else
        if (expense.getType().equalsIgnoreCase("buildtroop")){
            TroopType troopType = TroopPureFunctions.getTroopTypeByUuid(expense.getTroopTypeUuid(), gameWorld);
            returnString = "Build new " + troopType.getName() + " at " + planetName + ".";
        }else
        if (expense.getType().equalsIgnoreCase("buildVIP")){
            returnString = "Build new " + VipPureFunctions.getVipTypeByUuid(expense.getTypeVIPUuid(), gameWorld).getName() + " at " + planetName + ".";
        }else
        if (expense.getType().equalsIgnoreCase("transaction")){
            returnString = "Transfer " + expense.getSum() + " money to Govenor " + galaxy.getPlayerByUserName(planetName).getGovernorName();
        }else
        if(expense.getType().equalsIgnoreCase("blackmarketbid")){
            returnString = BlackMarketPureFunctions.getBiddingText(BlackMarketPureFunctions.findBlackMarketOffer(expense.getBlackMarketBid().getOfferUuid(), galaxy), expense.getBlackMarketBid(), gameWorld);
        }else
        if(expense.getType().equalsIgnoreCase("reconstruct")){
            returnString = "Reconstruct the planet " + planetName;
        }else
        if(expense.getType().equalsIgnoreCase("research")){
            returnString = "Research on " + expense.getResearchOrder().getAdvantageName();
        }
        returnString += " (cost: " + cost + ")";
        return returnString;
    }

    public static boolean isBuildingBuildingShip(Expense expense, Building aBuilding){
        Logger.finer("type currentBuildingId aBuilding.getUniqueId()" + expense.getType() + " " + expense.getConstructionBuildingUuid() + " " + aBuilding.getUuid());
        boolean isBilding = false;
        if (expense.getType().equalsIgnoreCase("buildship")){
            if (aBuilding.getUuid().equalsIgnoreCase(expense.getConstructionBuildingUuid())){
                isBilding = true;
            }
        }
        return isBilding;
    }

    public static boolean isBuildingBuildingTroop(Expense expense, Building aBuilding){
        boolean isBilding = false;
        if (expense.getType().equalsIgnoreCase("buildtroop")){
            if (aBuilding.getUuid().equalsIgnoreCase(expense.getConstructionBuildingUuid())){
                isBilding = true;
            }
        }
        return isBilding;
    }

    public static boolean isBuildingBuildingVIP(Expense expense,  Building aBuilding){
        boolean isBilding = false;
        if (expense.getType().equalsIgnoreCase("buildVIP")){
            if (aBuilding.getUuid().equalsIgnoreCase(expense.getConstructionBuildingUuid())){
                isBilding = true;
            }
        }
        return isBilding;
    }

    public static boolean isUpgradeBuilding(Expense expense, Building aBuilding){
        boolean returnValue = false;
        if ((expense.getType().equalsIgnoreCase("building")) && (expense.getConstructionBuildingUuid() != null) && (aBuilding.getUuid().equalsIgnoreCase(expense.getConstructionBuildingUuid())) && (expense.getBuildingTypeUuid() != null)){
            returnValue = true;
        }
        return returnValue;
    }

    public static String getVIPBuild(Orders orders, Building currentBuilding) {
        String orderdVIPUuid = null;
        for (int i = 0; i < orders.getExpenses().size(); i++) {
            Expense tempExpense = orders.getExpenses().get(i);
            if (isBuildingBuildingVIP(tempExpense, currentBuilding)) {
                orderdVIPUuid = tempExpense.getTypeVIPUuid();
            }
        }
        return orderdVIPUuid;
    }

    public static boolean isBuilding(Expense expense, Building aBuilding){
        return ((expense.getType().equalsIgnoreCase("building")) && aBuilding.getUuid().equalsIgnoreCase(expense.getConstructionBuildingUuid()));
    }

    public static boolean alreadyUpgrading(Orders orders, Building currentBuilding) {
        boolean found = false;
        int i = 0;
        while ((i < orders.getExpenses().size()) && (!found)) {
            Logger.finer("alreadyUpgrading lop index: " + i);
            Expense tempExpense = orders.getExpenses().get(i);
            if (tempExpense.isBuilding(currentBuilding)) {
                found = true;
            } else {
                i++;
            }
        }
        return found;
    }

    /**
     * Check if there already exist a reconstruct order for this planet
     *
     * @return
     */
    public static boolean alreadyReconstructing(Orders orders, Planet aPlanet) {
        boolean found = false;
        int i = 0;
        while ((i < orders.getExpenses().size()) & (!found)) {
            Expense tempExpense = orders.getExpenses().get(i);
            if (tempExpense.isReconstructAt(aPlanet)) {
                found = true;
            } else {
                i++;
            }
        }
        return found;
    }

    public static boolean isIncResAt(Expense expense, String mapPlanetUuid) {
        return "res".equalsIgnoreCase(expense.getType()) && mapPlanetUuid.equalsIgnoreCase(expense.getMapPlanetUuid());
    }

    public static boolean isIncPopAt(Expense expense, String mapPlanetUuid) {
        return "pop".equalsIgnoreCase(expense.getType()) && mapPlanetUuid.equalsIgnoreCase(expense.getMapPlanetUuid());
     }

    public static boolean isReconstructAt(Expense expense, String mapPlanetUuid){
        return "reconstruct".equalsIgnoreCase(expense.getType()) && mapPlanetUuid.equalsIgnoreCase(expense.getMapPlanetUuid());
    }

    public boolean isBuildBuildingAt(Expense expense, String mapPlanetUuid, String buildingTypeUuid) {
        return "building".equalsIgnoreCase(expense.getType()) && mapPlanetUuid.equalsIgnoreCase(expense.getMapPlanetUuid()) && buildingTypeUuid.equalsIgnoreCase(expense.getBuildingTypeUuid());
    }

    public static boolean isBuildBuildingAt(Expense expense, String mapPlanetUuid){
        return "building".equalsIgnoreCase(expense.getType()) && mapPlanetUuid.equalsIgnoreCase(expense.getMapPlanetUuid()) && expense.getConstructionBuildingUuid() == null;
    }
}
