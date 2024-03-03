package spaceraze.servlethelper.game.expenses;

import spaceraze.servlethelper.game.BlackMarketPureFunctions;
import spaceraze.servlethelper.game.BuildingPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.orders.Expense;
import spaceraze.world.orders.Orders;

public class ExpensePureFunction {

    private ExpensePureFunction(){}

    public static int getExpensesCost(Galaxy aGalaxy, Player player){
        int totalCost = 0;
        for (Expense expense : player.getOrders().getExpenses()){
            totalCost = totalCost + ExpensePureFunction.getCost(expense, aGalaxy, player);
        }
        return totalCost;
    }

    public static int getCost(Expense expense, Galaxy aGalaxy, Player player){
        Orders o = player.getOrders();
        int cost = 0;
        Planet planet = null;
        String type = expense.getType();
        Logger.finer("Expense.getCost(Orders o, Galaxy aGalaxy) type: " +  expense.getType());
        if(expense.getPlanetName() != null && !expense.getPlanetName().equalsIgnoreCase("")){
            planet = aGalaxy.getPlanet(expense.getPlanetName());
        }

        if (type.equalsIgnoreCase("pop")){
            cost = planet.getPopulation();
        }else
        if (type.equalsIgnoreCase("res")){
            cost = planet.getResistance();
        }else
        if (type.equalsIgnoreCase("building")){
            Logger.finer("planetName: " + expense.getPlanetName());
            Logger.finer("planet: " + planet.getName());
            Logger.finer("planet.getPlayerInControl(): " + player);
            VIP tempVIP = VipPureFunctions.findVIPBuildingBuildBonus(planet, player, o, aGalaxy);
            int vipBuildBonus = tempVIP == null ? 0 : VipPureFunctions.getVipTypeByUuid(tempVIP.getTypeUuid(), player.getGalaxy().getGameWorld()).getBuildingBuildBonus();
            BuildingType aBuildingType = PlayerPureFunctions.findOwnBuildingTypeByUuid(expense.getBuildingTypeUuid(), player);
            cost =  BuildingPureFunctions.getBuildCost(aBuildingType, vipBuildBonus);
        }else
        if (type.equalsIgnoreCase("buildship")){
            // kollar f�rst om det finns en engineer vid planeten
            VIP tempEngineer = VipPureFunctions.findVIPShipBuildBonus(planet, player, o, aGalaxy);
            int vipBuildBonus = tempEngineer == null ? 0 : VipPureFunctions.getVipTypeByUuid(tempEngineer.getTypeUuid(), aGalaxy.getGameWorld()).getShipBuildBonus();
            cost = SpaceshipPureFunctions.getBuildCost(PlayerPureFunctions.findOwnSpaceshipType(expense.getSpaceshipTypeUuid(),  player, aGalaxy), vipBuildBonus);
        }
        else
        if (type.equalsIgnoreCase("buildtroop")){
            // first check if there is an engineer at the planet
            VIP tempVIP = VipPureFunctions.findVIPTroopBuildBonus(planet, player , o, aGalaxy);
            TroopType troopType = PlayerPureFunctions.findOwnTroopType(expense.getTroopTypeUuid(), player, aGalaxy);
            int vipBuildBonus = tempVIP == null ? 0 : VipPureFunctions.getVipTypeByUuid(tempVIP.getTypeUuid(), player.getGalaxy().getGameWorld()).getTroopBuildBonus();
            cost = TroopPureFunctions.getCostBuild(troopType, vipBuildBonus);

        }else
        if (type.equalsIgnoreCase("buildVIP")){
            VIPType tempVIPType = VipPureFunctions.getVipTypeByUuid(expense.getTypeVIPUuid(), aGalaxy.getGameWorld());
            cost = tempVIPType.getBuildCost();
        }else
        if (type.equalsIgnoreCase("transaction")){
            cost = expense.getSum();
        }else
        if (type.equalsIgnoreCase("blackmarketbid")){
            cost = expense.getBlackMarketBid().getCost();
        }else
        if (type.equalsIgnoreCase("reconstruct")){
            cost = GameWorldHandler.getFactionByUuid(player.getFactionUuid(), aGalaxy.getGameWorld()).getReconstructCost(planet);
        }else{
            if (type.equalsIgnoreCase("research")){
                cost = expense.getResearchOrder().getCost();
            }
        }
        return cost;
    }

    public static String getText(Galaxy galaxy, int cost, Expense expense){
        String returnString = "";
        if (expense.getType().equalsIgnoreCase("pop")){
            returnString = "Increase production on " + expense.getPlanetName() + " with +1.";
        }else
        if (expense.getType().equalsIgnoreCase("res")){
            returnString = "Increase resistance on " + expense.getPlanetName() + " with +1.";
        }else
        if (expense.getType().equalsIgnoreCase("building")){
            BuildingType buildingType = BuildingPureFunctions.getBuildingTypeByUuid(expense.getBuildingTypeUuid(), galaxy.getGameWorld());
            if(buildingType.getParentBuildingName() == null){
                returnString = "Build new " + buildingType.getName() + " at " + expense.getPlanetName() + ".";
            }else{
                returnString = "Upgrade " + buildingType.getParentBuildingName() + " to " + buildingType.getName() + " at " + expense.getPlanetName() + ".";
            }
        }else
        if (expense.getType().equalsIgnoreCase("buildship")){
            SpaceshipType sst = SpaceshipPureFunctions.getSpaceshipTypeByUuid(expense.getSpaceshipTypeUuid(), galaxy.getGameWorld());
            returnString = "Build new " + sst.getName() + " at " + expense.getPlanetName() + ".";
        }else
        if (expense.getType().equalsIgnoreCase("buildtroop")){
            TroopType troopType = TroopPureFunctions.getTroopTypeByUuid(expense.getTroopTypeUuid(), galaxy.getGameWorld());
            returnString = "Build new " + troopType.getName() + " at " + expense.getPlanetName() + ".";
        }else
        if (expense.getType().equalsIgnoreCase("buildVIP")){
            returnString = "Build new " + VipPureFunctions.getVipTypeByUuid(expense.getTypeVIPUuid(), galaxy.getGameWorld()).getName() + " at " + expense.getPlanetName() + ".";
        }else
        if (expense.getType().equalsIgnoreCase("transaction")){
            returnString = "Transfer " + expense.getSum() + " money to Govenor " + galaxy.getPlayerByUserName(expense.getPlayerName()).getGovernorName();
        }else
        if(expense.getType().equalsIgnoreCase("blackmarketbid")){
            returnString = BlackMarketBid.getBiddingText(BlackMarketPureFunctions.findBlackMarketOffer(expense.getBlackMarketBid().getOfferUniqueId(), galaxy), expense.getBlackMarketBid());
        }else
        if(expense.getType().equalsIgnoreCase("reconstruct")){
            returnString = "Reconstruct the planet " + expense.getPlanetName();
        }else
        if(expense.getType().equalsIgnoreCase("research")){
            returnString = "Research on " + expense.getResearchOrder().getAdvantageName();
        }
        returnString += " (cost: " + cost + ")";
        return returnString;
    }

    public static boolean isBuildingBuildingShip(Expense expense, Building aBuilding){
        Logger.finer("type currentBuildingId aBuilding.getUniqueId()" + expense.getType() + " " + expense.getBuildingUuid() + " " + aBuilding.getUuid());
        boolean isBilding = false;
        if (expense.getType().equalsIgnoreCase("buildship")){
            if (aBuilding.getUuid().equalsIgnoreCase(expense.getBuildingUuid())){
                isBilding = true;
            }
        }
        return isBilding;
    }

    public static boolean isBuildingBuildingTroop(Expense expense, Building aBuilding){
        boolean isBilding = false;
        if (expense.getType().equalsIgnoreCase("buildtroop")){
            if (aBuilding.getUuid().equalsIgnoreCase(expense.getBuildingUuid())){
                isBilding = true;
            }
        }
        return isBilding;
    }

    public static boolean isBuildingBuildingVIP(Expense expense,  Building aBuilding){
        boolean isBilding = false;
        if (expense.getType().equalsIgnoreCase("buildVIP")){
            if (aBuilding.getUuid().equalsIgnoreCase(expense.getBuildingUuid())){
                isBilding = true;
            }
        }
        return isBilding;
    }

    public static boolean isUpgradeBuilding(Expense expense, Building aBuilding){
        boolean returnValue = false;
        if ((expense.getType().equalsIgnoreCase("building")) && (expense.getBuildingUuid() != null) && (aBuilding.getUuid().equalsIgnoreCase(expense.getBuildingUuid())) && (expense.getBuildingTypeUuid() != null)){
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
        return ((expense.getType().equalsIgnoreCase("building")) && aBuilding.getUuid().equalsIgnoreCase(expense.getBuildingUuid()));
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
}
