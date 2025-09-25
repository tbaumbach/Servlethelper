package spaceraze.servlethelper.game.orders;

import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.util.general.Logger;
import spaceraze.world.Building;
import spaceraze.world.Planet;
import spaceraze.world.orders.Expense;
import spaceraze.world.orders.Orders;
import spaceraze.world.orders.PlanetNotesChange;

public class OrderMutator {

    private OrderMutator(){}

    public static void removeAllBuildShip(Building aBuilding, Orders orders) {
        int nrFoundIndexes = 0;
        int[] removeIndexes = new int[orders.getExpenses().size()];
        for (int i = 0; i < orders.getExpenses().size(); i++) {
            Expense tempExpense = orders.getExpenses().get(i);
            if (ExpensePureFunction.isBuildingBuildingShip(tempExpense, aBuilding)) {
                Logger.finest("Shall remove: " + nrFoundIndexes);
                removeIndexes[nrFoundIndexes] = i;
                nrFoundIndexes++;
            }
        }
        for (int j = nrFoundIndexes - 1; j >= 0; j--) {
            Logger.finest("Removing: " + j);
            Logger.finest("Removing: " + orders.getExpenses().get(j).getSpaceshipTypeUuid());
            orders.getExpenses().remove(removeIndexes[j]);
        }
    }

    public static void removeAllBuildTroop(Building aBuilding, Orders orders) {
        int nrFoundIndexes = 0;
        int[] removeIndexes = new int[orders.getExpenses().size()];
        for (int i = 0; i < orders.getExpenses().size(); i++) {
            Expense tempExpense = orders.getExpenses().get(i);
            if (ExpensePureFunction.isBuildingBuildingTroop(tempExpense, aBuilding)) {
                Logger.finest("Shall remove: " + nrFoundIndexes);
                removeIndexes[nrFoundIndexes] = i;
                nrFoundIndexes++;
            }
        }
        for (int j = nrFoundIndexes - 1; j >= 0; j--) {
            Logger.finest("Removing: " + j);
            orders.getExpenses().remove(removeIndexes[j]);
        }
    }

    public static void removeBuildVIP(Building aBuilding, Orders orders) {
        int foundIndexes = -1;
        for (int i = 0; i < orders.getExpenses().size(); i++) {
            Expense tempExpense = orders.getExpenses().get(i);
            if (ExpensePureFunction.isBuildingBuildingVIP(tempExpense, aBuilding)) {
                foundIndexes = i;
            }
        }
        if (foundIndexes >= 0) {
            orders.getExpenses().remove(foundIndexes);
        }
    }

    public static void removeUpgradeBuilding(Building aBuilding, Orders orders) {
        int findIndex = -1;
        for (int i = 0; i < orders.getExpenses().size(); i++) {
            Expense tempExpense = orders.getExpenses().get(i);
            if (ExpensePureFunction.isUpgradeBuilding(tempExpense, aBuilding)) {
                findIndex = i;
            }
        }
        if (findIndex > -1) {
            orders.getExpenses().remove(findIndex);
        }
    }

    public static void addPlanetNotesChange(Orders orders, Planet aPlanet, String planetName, String notesText) {
        PlanetNotesChange aPlanetNotesChange = OrderPureFunctions.getPlanetNotesChange(orders, aPlanet);
        if (aPlanetNotesChange != null) {
            aPlanetNotesChange.setNotesText(notesText);
        } else {
            orders.getPlanetNotesChanges().add(new PlanetNotesChange(aPlanet.getMapPlanetUuid(), planetName, notesText));
        }
    }

    public static  void removePlanetNotesChange(Orders orders, Planet aPlanet) {
        PlanetNotesChange aPlanetNotesChange = OrderPureFunctions.getPlanetNotesChange(orders, aPlanet);
        if (aPlanetNotesChange != null) {
            orders.getPlanetNotesChanges().remove(aPlanetNotesChange);
        }
    }

    public static void addOrRemovePlanetVisibility(Orders orders, Planet planet) {
        // remove if order exists
        if (!orders.getPlanetVisibilities().remove(planet.getMapPlanetUuid())) {
            // No order was found, add a new one
            orders.getPlanetVisibilities().add(planet.getMapPlanetUuid());
        }
    }

    public static void addOrRemoveAbandonPlanet(Orders orders, Planet planet) {
        // remove if order exists
        if (!orders.getAbandonPlanets().remove(planet.getMapPlanetUuid())) {
            // No order was found, add a new one
            orders.getAbandonPlanets().add(planet.getMapPlanetUuid());
        }
    }

}
