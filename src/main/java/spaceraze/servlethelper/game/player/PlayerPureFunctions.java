package spaceraze.servlethelper.game.player;

import spaceraze.servlethelper.game.BuildingPureFunctions;
import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.world.diplomacy.DiplomacyState;

import java.util.ArrayList;
import java.util.LinkedList;
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
                .filter(improvement -> SpaceshipPureFunctions.isConstructable(galaxy, player, galaxy.getGameWorld().getSpaceshipTypeByName(improvement.getTypeId()), improvement))
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
     * If the player researched/upgraded a BuildingType that type will be returned instead of original one from the galaxy
     */
    public static BuildingType findBuildingType(String findName, Player player){
        return findOwnBuildingType(findName, player) != null ? findOwnBuildingType(findName, player) : GameWorldHandler.getFactionByKey(player.getFactionKey(), player.getGalaxy().getGameWorld()).getBuildingTypeByName(findName);
    }

    public static List<BuildingType> getBuildingTypes(Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getBuildingImprovements().stream().map(improvement -> findOwnBuildingType(improvement.getName(), player)).collect(Collectors.toList());
    }

    public static List<BuildingType> getAvailableBuildingTypes(Galaxy galaxy, Player player, Planet planet, String buildingKey){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableBuildingImprovements(galaxy, player, planet, buildingKey).stream().map(improvement -> findOwnBuildingType(improvement.getName(), player)).collect(Collectors.toList());
    }

    public static List<PlayerBuildingImprovement> getAvailableBuildingImprovements(Galaxy galaxy, Player player, Planet planet, String buildingKey){
        return player.getBuildingImprovements().stream()
                .filter(improvement -> BuildingPureFunctions.isConstructable(galaxy, player, planet, galaxy.getGameWorld().getBuildingTypeByName(improvement.getName()), buildingKey, improvement))
                .collect(Collectors.toList());
    }

    /**
     * Find a BuildingType from the players types.
     */
    public static BuildingType findOwnBuildingType(String findName, Player player){
        PlayerBuildingImprovement improvement = findBuildingImprovement(findName, player);

        return improvement != null ? new BuildingType(GameWorldHandler.getFactionByKey(player.getFactionKey(), player.getGalaxy().getGameWorld()).getBuildingTypeByName(findName), improvement) : null;
    }

    public static PlayerBuildingImprovement findBuildingImprovement(String findName, Player player){
        return player.getBuildingImprovements().stream()
                .filter(improvement -> improvement.getName().equalsIgnoreCase(findName)).findFirst().orElse(null);
    }

    /**
     * Only used by client.
     */
    public static int getTreasuryAfterCosts(Player player, Galaxy galaxy){
        Logger.finer("upkeepShips();" + CostPureFunctions.getPlayerUpkeepShips(player, galaxy.getPlanets(), galaxy.getSpaceships(), galaxy.getGameWorld()));
        Logger.finer("upkeepTroops();" + CostPureFunctions.getPlayerUpkeepTroops(player, galaxy.getPlanets(), galaxy.getTroops()));
        Logger.finer("upkeepVIPs();" + CostPureFunctions.getPlayerUpkeepVIPs(player, galaxy.getAllVIPs()));
        Logger.finer("income();" + IncomePureFunctions.getPlayerIncome(player, false));
        Logger.finer("orders.getExpensesCost();" + ExpensePureFunction.getExpensesCost(galaxy, player));
        Logger.finer("treasury;" + player.getTreasury());
        int tmpIncome = player.getTreasury() - CostPureFunctions.getPlayerUpkeepShips(player, galaxy.getPlanets(), galaxy.getSpaceships(), galaxy.getGameWorld()) - CostPureFunctions.getPlayerUpkeepTroops(player, galaxy.getPlanets(), galaxy.getTroops()) -  CostPureFunctions.getPlayerUpkeepVIPs(player, galaxy.getAllVIPs()) + IncomePureFunctions.getPlayerIncome(player, false);
        tmpIncome -= ExpensePureFunction.getExpensesCost(galaxy, player);
        return tmpIncome;
    }

    public static List<Player> getAllies(Player player, List<Player> players, Galaxy galaxy) {

        List<Player> allies = new ArrayList<>();

        for (Player aPlayer : players) {
            if (player != aPlayer) {
                DiplomacyState diplomacyState = DiplomacyPureFunctions.getDiplomacyState(player, aPlayer, galaxy.getDiplomacyStates());
                if (diplomacyState.getCurrentLevel().isHigher(DiplomacyLevel.PEACE)) {
                    allies.add(aPlayer);
                }
            }
        }
        return allies;
    }

    public static boolean playerHasShipsInSystem(Player aPlayer, Planet aPlanet, Galaxy galaxy) {
        boolean hasShipsInSystem = false;
        int i = 0;
        List<Spaceship> playerShips = SpaceshipPureFunctions.getPlayersSpaceships(aPlayer, galaxy);
        while ((i < playerShips.size()) & !hasShipsInSystem) {
            Spaceship tempss = playerShips.get(i);
            if (tempss.getLocation() == aPlanet) {
                hasShipsInSystem = true;
            } else {
                i++;
            }
        }
        return hasShipsInSystem;
    }

    public static List<Player> getActivePlayers(Galaxy galaxy) {
        List<Player> tmpPlayers = new LinkedList<>();
        for (Player aPlayer : galaxy.getPlayers()) {
            if (!aPlayer.isDefeated()) {
                tmpPlayers.add(aPlayer);
            }
        }
        return tmpPlayers;
    }
}