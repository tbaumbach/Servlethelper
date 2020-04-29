package spaceraze.servlethelper.game.player;

import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;

import java.util.List;
import java.util.stream.Collectors;

//TODO 2020-04-18 Move to a clean serverside modul
public class PlayerPureFunctions {

    private PlayerPureFunctions(){}

    /*
    public static List<SpaceshipType> getAvailableSpaceshipTypes(Galaxy galaxy, Player player){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getSpaceshipTypes().stream()
                .filter(ship -> SpaceshipPureFunctions.isConstructible(galaxy, player, ship, null))
                .collect(Collectors.toList());
    }*/

    public static List<SpaceshipType> getAvailableSpaceshipTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailablePlayerSpaceshipTypes(galaxy, player).stream().map(ship -> galaxy.getShipType(ship.getTypeId())).collect(Collectors.toList());
    }

    public static  List<PlayerSpaceshipType> getAvailablePlayerSpaceshipTypes(Galaxy galaxy, Player player){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getPlayerSpaceshipTypes().stream()
                .filter(ship -> SpaceshipPureFunctions.isConstructible(galaxy, player, galaxy.getShipType(ship.getTypeId()), ship))
                .collect(Collectors.toList());
    }

    /**
     * Find a SpaceshipType from the players types.
     */
    public static SpaceshipType findOwnSpaceshipType(String findName, Player player, Galaxy galaxy){
        PlayerSpaceshipType playerSpaceshipType = player.getPlayerSpaceshipTypes().stream()
                .filter(ship -> ship.getTypeId().equalsIgnoreCase(findName)).findFirst().orElse(null);

        return playerSpaceshipType != null ? new SpaceshipType(galaxy.findSpaceshipType(findName), playerSpaceshipType) : null;
    }

    /**
     * If the player researched/upgraded a shipType that type will be returned instead of original one from the galaxy
     */
    public static SpaceshipType findSpaceshipType(String findName, Player player, Galaxy galaxy){
        SpaceshipType sst = null;
        sst = findOwnSpaceshipType(findName, player, galaxy);
        if (sst == null){
            sst = galaxy.findSpaceshipType(findName);
        }
        return sst;
    }

    /**
     * Only used by client.
     */
    public static int getTreasuryAfterCosts(Player player, Galaxy galaxy){
        Logger.finer("upkeepShips();" + player.upkeepShips());
        Logger.finer("upkeepTroops();" + player.upkeepTroops());
        Logger.finer("upkeepVIPs();" + player.upkeepVIPs());
        Logger.finer("income();" + player.income());
        Logger.finer("orders.getExpensesCost();" + ExpensePureFunction.getExpensesCost(galaxy, player));
        Logger.finer("treasury;" + player.getTreasury());
        int tmpIncome = player.getTreasury() - player.upkeepShips() - player.upkeepTroops() - player.upkeepVIPs() + player.income();
        tmpIncome -= ExpensePureFunction.getExpensesCost(galaxy, player);
        return tmpIncome;
    }
}
