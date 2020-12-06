package spaceraze.servlethelper.game.expenses;

import spaceraze.servlethelper.game.BlackMarketPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
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
            VIP tempVIP = aGalaxy.findVIPBuildingBuildBonus(planet, player, o);
            BuildingType aBuildingType = PlayerPureFunctions.findOwnBuildingType(expense.getBuildingTypeName(), player);
            cost =  aBuildingType.getBuildCost(tempVIP);
        }else
        if (type.equalsIgnoreCase("buildship")){
            // kollar f�rst om det finns en engineer vid planeten
            VIP tempEngineer = aGalaxy.findVIPShipBuildBonus(planet, player, o);
            cost = PlayerPureFunctions.findOwnSpaceshipType(expense.getSpaceshipTypeName(),  player, aGalaxy).getBuildCost(tempEngineer);
        }
        else
        if (type.equalsIgnoreCase("buildtroop")){
            // first check if there is an engineer at the planet
            VIP tempVIP = aGalaxy.findVIPTroopBuildBonus(planet, player , o);
            TroopType troopType = PlayerPureFunctions.findOwnTroopType(expense.getTroopTypeName(), player, aGalaxy);
            cost = troopType.getCostBuild(tempVIP);

        }else
        if (type.equalsIgnoreCase("buildVIP")){
            VIPType tempVIPType = aGalaxy.getGameWorld().getVIPTypeByName(expense.getVipTypeName());
            if(tempVIPType == null){
                tempVIPType = aGalaxy.getGameWorld().getVIPTypeByName(expense.getVipTypeName());
            }
            cost = tempVIPType.getBuildCost();
        }else
        if (type.equalsIgnoreCase("transaction")){
            cost = expense.getSum();
        }else
        if (type.equalsIgnoreCase("blackmarketbid")){
            cost = expense.getBlackMarketBid().getCost();
        }else
        if (type.equalsIgnoreCase("reconstruct")){
            cost = player.getFaction().getReconstructCost(planet);
        }else{
            if (type.equalsIgnoreCase("research")){
                cost = expense.getResearchOrder().getCost();
            }
        }
        return cost;
    }

    public static String getText(Galaxy aGalaxy, int cost, Expense expense){
        String returnString = "";
        if (expense.getType().equalsIgnoreCase("pop")){
            returnString = "Increase production on " + expense.getPlanetName() + " with +1.";
        }else
        if (expense.getType().equalsIgnoreCase("res")){
            returnString = "Increase resistance on " + expense.getPlanetName() + " with +1.";
        }else
        if (expense.getType().equalsIgnoreCase("building")){
            BuildingType buildingType = aGalaxy.getGameWorld().getBuildingTypeByName(expense.getBuildingTypeName());
            if(buildingType.getParentBuildingName() == null){
                returnString = "Build new " + buildingType.getName() + " at " + expense.getPlanetName() + ".";
            }else{
                returnString = "Upgrade " + buildingType.getParentBuildingName() + " to " + buildingType.getName() + " at " + expense.getPlanetName() + ".";
            }
        }else
        if (expense.getType().equalsIgnoreCase("buildship")){
            SpaceshipType sst = aGalaxy.findSpaceshipType(expense.getSpaceshipTypeName());
            returnString = "Build new " + sst.getName() + " at " + expense.getPlanetName() + ".";
        }else
        if (expense.getType().equalsIgnoreCase("buildtroop")){
            TroopType troopType = aGalaxy.findTroopType(expense.getTroopTypeName());
            returnString = "Build new " + troopType.getUniqueName() + " at " + expense.getPlanetName() + ".";
        }else
        if (expense.getType().equalsIgnoreCase("buildVIP")){
            returnString = "Build new " + expense.getVIPType() + " at " + expense.getPlanetName() + ".";
        }else
        if (expense.getType().equalsIgnoreCase("transaction")){
            returnString = "Transfer " + expense.getSum() + " money to Govenor " + aGalaxy.getPlayer(expense.getPlayerName()).getGovernorName();
        }else
        if(expense.getType().equalsIgnoreCase("blackmarketbid")){
            returnString = BlackMarketBid.getBiddingText(BlackMarketPureFunctions.findBlackMarketOffer(expense.getBlackMarketBid().getOfferUniqueId(), aGalaxy), expense.getBlackMarketBid());
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
}
