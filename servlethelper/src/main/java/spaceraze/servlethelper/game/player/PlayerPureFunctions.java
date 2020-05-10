package spaceraze.servlethelper.game.player;

import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;

import java.util.List;
import java.util.stream.Collectors;

//TODO 2020-04-18 Move to a clean serverside modul
public class PlayerPureFunctions {

    private PlayerPureFunctions(){}

    public static List<SpaceshipType> getSpaceshipTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getSpaceshipImprovements().stream().map(improvement -> findOwnSpaceshipType(improvement.getTypeId(), player, galaxy)).collect(Collectors.toList());
    }

    public static List<SpaceshipType> getAvailableSpaceshipTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableSpaceshipImprovements(galaxy, player).stream().map(improvement -> findOwnSpaceshipType(improvement.getTypeId(), player, galaxy)).collect(Collectors.toList());
    }

    public static List<PlayerSpaceshipImprovement> getAvailableSpaceshipImprovements(Galaxy galaxy, Player player){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getSpaceshipImprovements().stream()
                .filter(improvement -> SpaceshipPureFunctions.isConstructible(galaxy, player, galaxy.getShipType(improvement.getTypeId()), improvement))
                .collect(Collectors.toList());
    }

    public static PlayerSpaceshipImprovement findSpaceshipImprovement(String findName, Player player){
        return player.getSpaceshipImprovements().stream()
                .filter(improvement -> improvement.getTypeId().equalsIgnoreCase(findName)).findFirst().orElse(null);
    }

    public static List<TroopType> getTroopTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getTroopImprovements().stream().map(improvement -> findOwnTroopType(improvement.getTypeId(), player, galaxy)).collect(Collectors.toList());
    }

    public static List<TroopType> getAvailableTroopTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableTroopImprovements(galaxy, player).stream().map(improvement -> findOwnTroopType(improvement.getTypeId(), player, galaxy)).collect(Collectors.toList());
    }

    public static  List<PlayerTroopImprovement> getAvailableTroopImprovements(Galaxy galaxy, Player player){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getTroopImprovements().stream()
                .filter(improvement -> TroopPureFunctions.isConstructable(player, galaxy, galaxy.findTroopType(improvement.getTypeId()), improvement))
                .collect(Collectors.toList());
    }

    public static PlayerTroopImprovement findTroopImprovement(String findName, Player player){
        return player.getTroopImprovements().stream()
                .filter(improvement -> improvement.getTypeId().equalsIgnoreCase(findName)).findFirst().orElse(null);
    }

    /**
     * Find a TroopType from the players types.
     */
    public static TroopType findOwnTroopType(String findName, Player player, Galaxy galaxy){
        PlayerTroopImprovement playerTroopImprovement = findTroopImprovement(findName, player);

        return playerTroopImprovement != null ? new TroopType(galaxy.findTroopType(findName), playerTroopImprovement) : null;
    }

    /**
     * If the player researched/upgraded a TroopType that type will be returned instead of original one from the galaxy
     */
    public static TroopType findTroopType(String findName, Player player, Galaxy galaxy){
        return findOwnTroopType(findName, player, galaxy) != null ? findOwnTroopType(findName, player, galaxy) : galaxy.findTroopType(findName);
    }


    /**
     * Find a SpaceshipType from the players types.
     */
    public static SpaceshipType findOwnSpaceshipType(String findName, Player player, Galaxy galaxy){
        PlayerSpaceshipImprovement playerSpaceshipImprovement = findSpaceshipImprovement(findName, player);

        return playerSpaceshipImprovement != null ? new SpaceshipType(galaxy.findSpaceshipType(findName), playerSpaceshipImprovement) : null;
    }

    /**
     * If the player researched/upgraded a shipType that type will be returned instead of original one from the galaxy
     */
    public static SpaceshipType findSpaceshipType(String findName, Player player, Galaxy galaxy){
        return findOwnSpaceshipType(findName, player, galaxy) != null ? findOwnSpaceshipType(findName, player, galaxy) : galaxy.findSpaceshipType(findName);
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
