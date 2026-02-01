package spaceraze.servlethelper.game.expenses;

import spaceraze.game.BlackMarketBid;
import spaceraze.game.Building;
import spaceraze.game.orders.Expense;
import spaceraze.game.orders.ResearchOrder;

public class ExpenseMutator {

    private ExpenseMutator(){}

    public static Expense createBuilding (String buildingTypeUuid, String playerUuid, String planetUuid, String constructionBuildingUuid){
        Expense expense = new Expense();
        expense.setType("building");
        expense.setBuildingTypeUuid(buildingTypeUuid);
        expense.setMapPlanetUuid(planetUuid);
        if(constructionBuildingUuid!= null){
            expense.setConstructionBuildingUuid(constructionBuildingUuid);
        }
        expense.setPlayerUuid(playerUuid);
        return expense;
    }

    public static Expense addResearch (ResearchOrder researchOrder, String playerUuid) {
        Expense expense = new Expense();
        expense.setType("research");
        expense.setResearchOrder(researchOrder);
        expense.setPlayerUuid(playerUuid);
        return expense;
    }

    public static Expense addBlackMarketBid (BlackMarketBid blackMarketBid, String playerUuid) {
        Expense expense = new Expense();
        expense.setType("blackmarketbid");
        expense.setBlackMarketBid(blackMarketBid);        blackMarketBid.setPlayerUuid(playerUuid);
        expense.setPlayerUuid(playerUuid);
        return expense;
    }

    public static Expense buildSpaceship (String spaceshipTypeUuid, String playerUuid, Building building) {
        Expense expense = new Expense();
        expense.setType("buildship");
        expense.setPlayerUuid(playerUuid);
        expense.setSpaceshipTypeUuid(spaceshipTypeUuid);
        expense.setMapPlanetUuid(building.getLocation().getMapPlanetUuid());
        expense.setConstructionBuildingUuid(building.getUuid());
        return expense;
    }
}
