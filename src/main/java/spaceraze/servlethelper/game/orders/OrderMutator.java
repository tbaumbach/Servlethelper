package spaceraze.servlethelper.game.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import spaceraze.game.*;
import spaceraze.game.orders.*;
import spaceraze.servlethelper.game.expenses.ExpenseMutator;
import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.util.general.Logger;
import spaceraze.world.TroopType;
import spaceraze.world.VIPType;

import java.util.ArrayList;
import java.util.List;

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

    public static void clearOrders(List<Player> players) {
        //TODO should we keep the old orders and create a new Orders for each turn? Can we use the old Orders? Debugging errors, replay the old turn and also rerun the old  turn if something went wrong in turn update (the the old serialized persistence hade old orders saved and the game could bee rerun)
        Logger.fine("clear orders");
        players.forEach(player -> {
            Orders orders = new Orders();
            // adding research Orders that should continue.
            player.getOrders().getResearchOrders().stream()
                    .filter(researchOrder -> !player.getResearchProgress(researchOrder.getAdvantageName()).isDeveloped())
                    .forEach(researchOrder -> addResearchOrder(orders, researchOrder, player));

            player.setOrders(orders);
        });
    }

    public static void addResearchOrder(Orders orders, ResearchOrder researchOrder, Player p) {
        orders.getResearchOrders().add(researchOrder);
        if (researchOrder.getCost() > 0) {
            orders.getExpenses().add(ExpenseMutator.addResearch(researchOrder, p.getUuid()));
        }
    }

    public static void addVIPSelfDestruct(Orders orders, VIP vip) {
        orders.getVIPMoves().removeIf(vipMove -> vipMove.getVipKey().equalsIgnoreCase(vip.getUuid()));
        orders.getVIPSelfDestructs().add(vip.getUuid());
    }

    public static void removeShipSelfDestruct(Orders orders, Spaceship spaceship) {
        orders.getShipSelfDestructs().removeIf(shipKey -> shipKey.equalsIgnoreCase(spaceship.getUuid()));
    }

    public static void removeTroopSelfDestruct(Orders orders, Troop troop) {
        orders.getTroopSelfDestructs().removeIf(shipKey -> shipKey.equalsIgnoreCase(troop.getUuid()));

    }

    public static void removeVIPSelfDestruct(Orders orders, VIP vip) {
        orders.getVIPSelfDestructs().removeIf(shipKey -> shipKey.equalsIgnoreCase(vip.getUuid()));
    }

    public static void removeBuildingSelfDestruct(Orders orders,  Building building) {
        orders.getBuildingSelfDestructs().removeIf(shipKey -> shipKey.equalsIgnoreCase(building.getUuid()));
    }

    public static void addNewBlackMarketBid(Orders orders, int aSum, BlackMarketOffer aOffer, String mapPlanetUuid, Player aPlayer) {
        // remove old any bid to this offer
        Expense oldExpenseBid = OrderPureFunctions.getExpense(orders, aOffer);
        if (oldExpenseBid != null) {
            orders.getExpenses().remove(oldExpenseBid);
        }
        // add new bid if sum > 0
        if (aSum > 0) {
            BlackMarketBid blackMarketBid = new BlackMarketBid(aSum, aOffer.getUuid(), null);
            blackMarketBid.setPlayerUuid(aPlayer.getUuid());
            orders.getExpenses().add(ExpenseMutator.addBlackMarketBid(blackMarketBid, aPlayer.getUuid()));
        }
    }

    public static void removeIncRes(Orders orders, String mapPlanetUuid) {
        orders.getExpenses().removeIf(expense -> ExpensePureFunction.isIncResAt(expense, mapPlanetUuid));
    }

    public static void addIncRes(Orders orders, Planet aPlanet, Player aPlayer) {
        orders.getExpenses().add(new Expense("res", aPlanet, aPlayer));
    }

    public static void removeIncPop(Orders orders, String mapPlanetUuid) {
        orders.getExpenses().removeIf(expense -> ExpensePureFunction.isIncPopAt(expense, mapPlanetUuid));
    }

    public static void addIncPop(Orders orders, Planet aPlanet, Player aPlayer) {
        orders.getExpenses().add(new Expense("pop", aPlanet, aPlayer));
    }

    public static void removeNewBuilding(Orders orders, String mapPlanetUuid) {
        orders.getExpenses().removeIf(expense -> ExpensePureFunction.isBuildBuildingAt(expense, mapPlanetUuid));
    }

    public static void removeReconstruct(Orders orders, String mapPlanetUuid) {
        orders.getExpenses().removeIf(expense -> ExpensePureFunction.isReconstructAt(expense, mapPlanetUuid));
    }

    public static void addReconstruct(Orders orders, Planet aPlanet, Player aPlayer) {
        orders.getExpenses().add(new Expense("reconstruct", aPlanet, aPlayer));
    }

    public static void addNewTransaction(Orders orders, int aSum, Player recipient) {
        orders.getExpenses().removeIf(expense -> "transaction".equalsIgnoreCase(expense.getType()) && expense.getPlayerUuid().equalsIgnoreCase(recipient.getUuid()));
        if (aSum > 0) {
            orders.getExpenses().add(new Expense("transaction", recipient, aSum));
        }
    }

    public static void removeAllGroundAttacksAgainstPlanet(Orders orders, Planet inPlanet) {
        orders.getTroopToPlanetMoves().removeIf(troopToPlanetMovement -> troopToPlanetMovement.getMapPlanetUuid().equalsIgnoreCase(inPlanet.getMapPlanetUuid()));
    }

    public static void addBuildTroop(Orders orders, Building aBuilding, TroopType tt, Player aPlayer) {
        orders.getExpenses().add(new Expense("buildtroop", aBuilding, tt, aPlayer.getName()));
    }

    public static void addBuildVIP(Orders orders, Building aBuilding, VIPType vt, Player aPlayer) {
        orders.getExpenses().add(new Expense("buildVIP", aBuilding, vt, aPlayer.getName()));
    }

    public static void addOrRemoveScreenedShip(Orders orders, Spaceship ship) {
        if (orders.getScreenedShips().removeIf(screenedShip -> screenedShip.equalsIgnoreCase(ship.getUuid()))) {
            //None existed
            orders.getScreenedShips().add(ship.getUuid());
        }
    }

    public static void removeResearchOrder(Orders orders, String advantageName) {
        orders.getResearchOrders().removeIf(researchOrder -> researchOrder.getAdvantageName().equals(advantageName));
        orders.getExpenses().removeIf(expense -> expense.isResearchOrder(advantageName));
    }
}
