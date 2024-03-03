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

        return player.getSpaceshipImprovements().stream().map(improvement -> findOwnSpaceshipType(improvement.getTypeUuid(), player, galaxy)).collect(Collectors.toList());
    }

    public static List<SpaceshipType> getAvailableSpaceshipTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableSpaceshipImprovements(galaxy, player).stream().map(improvement -> findOwnSpaceshipType(improvement.getTypeUuid(), player, galaxy)).collect(Collectors.toList());
    }

    public static List<PlayerSpaceshipImprovement> getAvailableSpaceshipImprovements(Galaxy galaxy, Player player){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getSpaceshipImprovements().stream()
                .filter(improvement -> SpaceshipPureFunctions.isConstructable(galaxy, player, SpaceshipPureFunctions.getSpaceshipTypeByUuid(improvement.getTypeUuid(), galaxy.getGameWorld()), improvement))
                .collect(Collectors.toList());
    }

    public static PlayerSpaceshipImprovement findSpaceshipImprovement(String uuid, Player player){
        return player.getSpaceshipImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }

    public static List<TroopType> getTroopTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getTroopImprovements().stream().map(improvement -> findOwnTroopType(improvement.getTypeUuid(), player, galaxy)).collect(Collectors.toList());
    }

    public static List<TroopType> getAvailableTroopTypes(Galaxy galaxy, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableTroopImprovements(galaxy, player).stream().map(improvement -> findOwnTroopType(improvement.getTypeUuid(), player, galaxy)).collect(Collectors.toList());
    }

    public static  List<PlayerTroopImprovement> getAvailableTroopImprovements(Galaxy galaxy, Player player){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getTroopImprovements().stream()
                .filter(improvement -> TroopPureFunctions.isConstructable(player, galaxy, TroopPureFunctions.getTroopTypeByUuid(improvement.getTypeUuid(), galaxy.getGameWorld()), improvement))
                .collect(Collectors.toList());
    }

    public static PlayerTroopImprovement findTroopImprovement(String uuid, Player player){
        return player.getTroopImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }

    /**
     * Find a TroopType from the players types.
     */
    public static TroopType findOwnTroopType(String troopTypeUuid, Player player, Galaxy galaxy){
        PlayerTroopImprovement playerTroopImprovement = findTroopImprovement(troopTypeUuid, player);

        return playerTroopImprovement != null ? new TroopType(TroopPureFunctions.getTroopTypeByUuid(troopTypeUuid, galaxy.getGameWorld()), playerTroopImprovement) : null;
    }

    /**
     * If the player researched/upgraded a TroopType that type will be returned instead of original one from the galaxy
     */
    public static TroopType findTroopType(String troopTypeUuid, Player player, Galaxy galaxy){
        return findOwnTroopType(troopTypeUuid, player, galaxy) != null ? findOwnTroopType(troopTypeUuid, player, galaxy) : TroopPureFunctions.getTroopTypeByUuid(troopTypeUuid, galaxy.getGameWorld());
    }


    /**
     * Find a SpaceshipType from the players types.
     */
    public static SpaceshipType findOwnSpaceshipType(String spaceShipUuid, Player player, Galaxy galaxy){
        PlayerSpaceshipImprovement playerSpaceshipImprovement = findSpaceshipImprovement(spaceShipUuid, player);

        return playerSpaceshipImprovement != null ? new SpaceshipType(SpaceshipPureFunctions.getSpaceshipTypeByUuid(spaceShipUuid, galaxy.getGameWorld()), playerSpaceshipImprovement) : null;
    }

    /**
     * If the player researched/upgraded a BuildingType that type will be returned instead of original one from the galaxy
     */
    public static BuildingType findBuildingTypeByUuid(String uuid, Player player){
        return findOwnBuildingTypeByUuid(uuid, player) != null ? findOwnBuildingTypeByUuid(uuid, player) : BuildingPureFunctions.getBuildingTypeByUuid(uuid, player.getGalaxy().getGameWorld());
    }

    public static BuildingType findBuildingTypeByName(String findName, Player player) {
        return findOwnBuildingTypeByName(findName, player) != null ? findOwnBuildingTypeByName(findName, player) : BuildingPureFunctions.getBuildingTypeByName(findName, player.getGalaxy().getGameWorld());
    }

    public static List<BuildingType> getBuildingTypes(Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getBuildingImprovements().stream().map(improvement -> findOwnBuildingTypeByUuid(improvement.getTypeUuid(), player)).collect(Collectors.toList());
    }

    public static List<BuildingType> getAvailableBuildingTypes(Galaxy galaxy, Player player, Planet planet, String buildingKey){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableBuildingImprovements(galaxy, player, planet, buildingKey).stream().map(improvement -> findOwnBuildingTypeByUuid(improvement.getTypeUuid(), player)).collect(Collectors.toList());
    }

    public static List<PlayerBuildingImprovement> getAvailableBuildingImprovements(Galaxy galaxy, Player player, Planet planet, String buildingKey){

        return player.getBuildingImprovements().stream()
                .filter(improvement -> BuildingPureFunctions.isConstructable(galaxy, player, planet, BuildingPureFunctions.getBuildingTypeByUuid(improvement.getTypeUuid(), galaxy.getGameWorld()), buildingKey, improvement))
                .collect(Collectors.toList());
    }

    /**
     * Find a BuildingType from the players types.
     */
    public static BuildingType findOwnBuildingTypeByUuid(String buildingTypeUuid, Player player){
        PlayerBuildingImprovement improvement = findBuildingImprovementByUuid(buildingTypeUuid, player);

        return improvement != null ? new BuildingType(BuildingPureFunctions.getBuildingTypeByUuid(buildingTypeUuid, player.getGalaxy().getGameWorld()), improvement) : null;
    }

    public static BuildingType findOwnBuildingTypeByName(String buildingName, Player player){
        PlayerBuildingImprovement improvement = findBuildingImprovementByName(buildingName, player);

        return improvement != null ? new BuildingType(BuildingPureFunctions.getBuildingTypeByUuid(improvement.getTypeUuid(), player.getGalaxy().getGameWorld()), improvement) : null;
    }

    public static PlayerBuildingImprovement findBuildingImprovementByUuid(String uuid, Player player){
        return player.getBuildingImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }

    public static PlayerBuildingImprovement findBuildingImprovementByName(String name, Player player){
        BuildingType buildingType = GameWorldHandler.getFactionByUuid(player.getFactionUuid(), player.getGalaxy().getGameWorld()).getBuildingTypeByName(name);

        return buildingType != null ? player.getBuildingImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(buildingType.getUuid())).findFirst().orElse(null) : null;
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
