package spaceraze.servlethelper.game.orders;

import spaceraze.game.*;
import spaceraze.game.orders.*;
import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.world.BuildingType;
import spaceraze.world.SpaceshipType;
import spaceraze.world.TroopType;
import spaceraze.world.VIPType;

import java.util.LinkedList;
import java.util.List;

public class OrderPureFunctions {

    private OrderPureFunctions(){}

    public static List<TroopToPlanetMovement> getTroopToPlanetMoves(String uuid, Orders orders) {
        List<TroopToPlanetMovement> troopMoves = new LinkedList<>();
        for (TroopToPlanetMovement aTroopToPlanetMove : orders.getTroopToPlanetMoves()) {
            if (uuid.equalsIgnoreCase(aTroopToPlanetMove.getMapPlanetUuid())) {
                troopMoves.add(aTroopToPlanetMove);
            }
        }
        return troopMoves;
    }

    public static PlanetNotesChange getPlanetNotesChange(Orders orders, Planet aPlanet) {
        PlanetNotesChange planetNotesChange = null;
        for (PlanetNotesChange aPlanetNotesChange : orders.getPlanetNotesChanges()) {
            if (aPlanet.getMapPlanetUuid().equals(aPlanetNotesChange.getPlanetUuid())) {
                planetNotesChange = aPlanetNotesChange;
            }
        }
        return planetNotesChange;
    }

    public static boolean isPlanetVisibility(Orders orders, Planet planet) {
        return orders.getPlanetVisibilities().contains(planet.getMapPlanetUuid());
    }

    public static boolean isAbandonPlanet(Orders orders, Planet planet) {
        return orders.getAbandonPlanets().contains(planet.getMapPlanetUuid());
    }

    public static boolean haveSpaceshipTypeBuildOrder(Orders orders, SpaceshipType aSpaceshipType) {
        return orders.getExpenses().stream().anyMatch(expense -> expense.getSpaceshipTypeUuid().equals(aSpaceshipType.getUuid()));
    }

    public static  boolean haveBuildingTypeBuildOrder(Orders orders, BuildingType aBuildingType, String buildingKey) {
        return orders.getExpenses().stream()
                .anyMatch(expense -> 
                        expense.getBuildingTypeUuid().equals(aBuildingType.getUuid()) 
                                && (buildingKey == null || buildingKey.equalsIgnoreCase(expense.getConstructionBuildingUuid())));
    }

    public static boolean haveVIPTypeBuildOrder(Orders orders, VIPType aVIPType) {
        return orders.getExpenses().stream().anyMatch(expense -> expense.getTypeVIPUuid().equals(aVIPType.getUuid()));
    }

    public static boolean haveTroopTypeBuildOrder(Orders orders, TroopType aTroopType) {
        return orders.getExpenses().stream().anyMatch(expense -> expense.getTroopTypeUuid().equals(aTroopType.getUuid()));
    }

    public static Expense getExpense(Orders orders, BlackMarketOffer blackMarketOffer) {
        return orders.getExpenses().stream()
                .filter(expense -> expense.getBlackMarketBid() != null)
                .filter(expense -> expense.getBlackMarketBid().getOfferUuid().equalsIgnoreCase(blackMarketOffer.getUuid())).findAny().orElse(null);
        
    }

    public static BlackMarketBid getBidToOffer(Orders orders, BlackMarketOffer blackMarketOffer){
        Expense expense = getExpense(orders, blackMarketOffer);
        return expense != null ? expense.getBlackMarketBid() : null;
    }

    /**
     * Returns the amount to give to player. If none is to be given (no gift exists),
     * 0 is returned.
     *
     * @param player The player to find a gift to
     * @return gift sum to aPlayer
     */
    public static int findGift(Orders orders, Player player) {
        return orders.getExpenses().stream()
                .filter(Expense::isTransaction)
                .filter(expense -> expense.getPlayerUuid().equalsIgnoreCase(player.getUuid()))
                .mapToInt(Expense::getSum).sum();
    }

    public static boolean isShipSelfDestruct(Orders orders, Spaceship spaceship) {
        return orders.getShipSelfDestructs().stream().anyMatch(shipKey -> shipKey.equalsIgnoreCase(spaceship.getUuid()));
    }

    public static boolean isVIPSelfDestruct(Orders orders, VIP vip) {
        return orders.getVIPSelfDestructs().stream().anyMatch(uuid -> uuid.equalsIgnoreCase(vip.getTypeUuid()));
    }

    public static boolean checkResearchOrder(Orders orders, String advantageName) {
        return orders.getResearchOrders().stream().anyMatch(researchOrder -> researchOrder.getAdvantageName().equals(advantageName));
    }

    public static boolean incResExpenseExist(Orders orders, String mapPlanetUuid) {
        return orders.getExpenses().stream().anyMatch(expense -> ExpensePureFunction.isIncResAt(expense, mapPlanetUuid));
    }

    public static boolean incPopExpenseExist(Orders orders, String mapPlanetUuid) {
        return orders.getExpenses().stream().anyMatch(expense -> ExpensePureFunction.isIncPopAt(expense, mapPlanetUuid));
    }

    public static boolean checkScreenedShip(Orders orders, String shipUuid) {
        return orders.getScreenedShips().stream().anyMatch(screenedShip -> screenedShip.equalsIgnoreCase(shipUuid));
    }

    public static boolean isScreenedShip(Orders orders, Spaceship ship) {
        return orders.getScreenedShips().stream().anyMatch(screenedShip -> screenedShip.equalsIgnoreCase(ship.getUuid()));
    }

    public static boolean isBuildingSelfDestruct(Orders orders, Building building) {
        return orders.getBuildingSelfDestructs().stream().anyMatch(buildingId -> buildingId.equalsIgnoreCase(building.getTypeUuid()));
    }

}
