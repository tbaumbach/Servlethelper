package spaceraze.servlethelper.game.player;

import spaceraze.game.*;
import spaceraze.map.GalaxyMap;
import spaceraze.servlethelper.game.building.BuildingMutator;
import spaceraze.servlethelper.game.building.BuildingPureFunctions;
import spaceraze.servlethelper.game.DiplomacyPureFunctions;
import spaceraze.servlethelper.game.expenses.ExpensePureFunction;
import spaceraze.servlethelper.game.spaceship.SpaceshipMutator;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopMutator;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.game.diplomacy.DiplomacyState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//TODO 2020-04-18 Move to a clean serverside modul
public class PlayerPureFunctions {

    private PlayerPureFunctions(){}

    public static List<SpaceshipType> getSpaceshipTypes(Player player, GameWorld gameWorld){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getSpaceshipImprovements().stream().map(improvement -> findOwnSpaceshipType(improvement.getTypeUuid(), player, gameWorld)).collect(Collectors.toList());
    }

    public static List<SpaceshipType> getAvailableSpaceshipTypes(Galaxy galaxy, Player player, GameWorld gameWorld){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableSpaceshipImprovements(galaxy, player, gameWorld).stream().map(improvement -> findOwnSpaceshipType(improvement.getTypeUuid(), player, gameWorld)).collect(Collectors.toList());
    }

    public static List<PlayerSpaceshipImprovement> getAvailableSpaceshipImprovements(Galaxy galaxy, Player player, GameWorld gameWorld){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getSpaceshipImprovements().stream()
                .filter(improvement -> SpaceshipPureFunctions.isConstructable(galaxy, player, SpaceshipPureFunctions.getSpaceshipTypeByUuid(improvement.getTypeUuid(), gameWorld), improvement, gameWorld))
                .collect(Collectors.toList());
    }

    public static PlayerSpaceshipImprovement findSpaceshipImprovement(String uuid, Player player){
        return player.getSpaceshipImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }

    public static List<TroopType> getTroopTypes(GameWorld gameWorld, Player player){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getTroopImprovements().stream().map(improvement -> findOwnTroopType(improvement.getTypeUuid(), player, gameWorld)).collect(Collectors.toList());
    }

    public static List<TroopType> getAvailableTroopTypes(Galaxy galaxy, Player player, GameWorld gameWorld){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableTroopImprovements(galaxy, player, gameWorld).stream().map(improvement -> findOwnTroopType(improvement.getTypeUuid(), player, gameWorld)).collect(Collectors.toList());
    }

    public static  List<PlayerTroopImprovement> getAvailableTroopImprovements(Galaxy galaxy, Player player, GameWorld gameWorld){
        //TODO 2020-04-18 used by client, should loop through player.getPlayerSpaceshipTypes() to get the correct values on the ship
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getTroopImprovements().stream()
                .filter(improvement -> TroopPureFunctions.isConstructable(player, galaxy, TroopPureFunctions.getTroopTypeByUuid(improvement.getTypeUuid(), gameWorld), improvement, gameWorld))
                .collect(Collectors.toList());
    }

    public static PlayerTroopImprovement findTroopImprovement(String uuid, Player player){
        return player.getTroopImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }

    /**
     * Find a TroopType from the players types.
     */
    public static TroopType findOwnTroopType(String troopTypeUuid, Player player, GameWorld gameWorld){
        PlayerTroopImprovement playerTroopImprovement = findTroopImprovement(troopTypeUuid, player);

        return playerTroopImprovement != null ? TroopMutator.mutateTroopType(TroopPureFunctions.getTroopTypeByUuid(troopTypeUuid, gameWorld), playerTroopImprovement) : null;
    }

    /**
     * If the player researched/upgraded a TroopType that type will be returned instead of original one from the galaxy
     */
    public static TroopType findTroopType(String troopTypeUuid, Player player, GameWorld gameWorld){
        return findOwnTroopType(troopTypeUuid, player, gameWorld) != null ? findOwnTroopType(troopTypeUuid, player, gameWorld) : TroopPureFunctions.getTroopTypeByUuid(troopTypeUuid, gameWorld);
    }


    /**
     * Find a SpaceshipType from the players types.
     */
    public static SpaceshipType findOwnSpaceshipType(String spaceShipUuid, Player player, GameWorld gameWorld){
        PlayerSpaceshipImprovement playerSpaceshipImprovement = findSpaceshipImprovement(spaceShipUuid, player);

        return playerSpaceshipImprovement != null ? SpaceshipMutator.createSpaceshipTypeWithImprovements(SpaceshipPureFunctions.getSpaceshipTypeByUuid(spaceShipUuid, gameWorld), playerSpaceshipImprovement) : null;
    }

    /**
     * If the player researched/upgraded a BuildingType that type will be returned instead of original one from the galaxy
     */
    public static BuildingType findBuildingTypeByUuid(String uuid, Player player, GameWorld gameWorld){
        return findOwnBuildingTypeByUuid(uuid, player, gameWorld) != null ? findOwnBuildingTypeByUuid(uuid, player, gameWorld) : BuildingPureFunctions.getBuildingTypeByUuid(uuid, gameWorld);
    }

    public static BuildingType findBuildingTypeByName(String findName, Player player, GameWorld gameWorld) {
        return findOwnBuildingTypeByName(findName, player, gameWorld) != null ? findOwnBuildingTypeByName(findName, player, gameWorld) : BuildingPureFunctions.getBuildingTypeByName(findName, gameWorld);
    }

    public static List<BuildingType> getBuildingTypes(Player player, GameWorld gameWorld){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return player.getBuildingImprovements().stream().map(improvement -> findOwnBuildingTypeByUuid(improvement.getTypeUuid(), player, gameWorld)).collect(Collectors.toList());
    }

    public static List<BuildingType> getAvailableBuildingTypes(Galaxy galaxy, GameWorld gameWorld, Player player, Planet planet, String buildingKey){
        //The client will soon get this from servlets, turnInfo object = this method will be used for add the ships to turnInfo

        return getAvailableBuildingImprovements(galaxy, gameWorld, player, planet, buildingKey).stream().map(improvement -> findOwnBuildingTypeByUuid(improvement.getTypeUuid(), player, gameWorld)).collect(Collectors.toList());
    }

    public static List<PlayerBuildingImprovement> getAvailableBuildingImprovements(Galaxy galaxy, GameWorld gameWorld, Player player, Planet planet, String buildingKey){

        return player.getBuildingImprovements().stream()
                .filter(improvement -> BuildingPureFunctions.isConstructable(galaxy, gameWorld, player, planet, BuildingPureFunctions.getBuildingTypeByUuid(improvement.getTypeUuid(), gameWorld), buildingKey, improvement))
                .collect(Collectors.toList());
    }

    /**
     * Find a BuildingType from the players types.
     */
    public static BuildingType findOwnBuildingTypeByUuid(String buildingTypeUuid, Player player, GameWorld gameWorld){
        PlayerBuildingImprovement improvement = findBuildingImprovementByUuid(buildingTypeUuid, player);

        return improvement != null ? BuildingMutator.createImprovedBuildingType(BuildingPureFunctions.getBuildingTypeByUuid(buildingTypeUuid, gameWorld), improvement) : null;
    }

    public static BuildingType findOwnBuildingTypeByName(String buildingName, Player player, GameWorld gameWorld){
        PlayerBuildingImprovement improvement = findBuildingImprovementByName(buildingName, player, gameWorld);

        return improvement != null ? BuildingMutator.createImprovedBuildingType(BuildingPureFunctions.getBuildingTypeByUuid(improvement.getTypeUuid(), gameWorld), improvement) : null;
    }

    public static PlayerBuildingImprovement findBuildingImprovementByUuid(String uuid, Player player){
        return player.getBuildingImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(uuid)).findFirst().orElse(null);
    }

    public static PlayerBuildingImprovement findBuildingImprovementByName(String name, Player player, GameWorld gameWorld){
        BuildingType buildingType = GameWorldHandler.getFactionByUuid(player.getFactionUuid(), gameWorld).getBuildingTypeByName(name);

        return buildingType != null ? player.getBuildingImprovements().stream()
                .filter(improvement -> improvement.getTypeUuid().equalsIgnoreCase(buildingType.getUuid())).findFirst().orElse(null) : null;
    }

    /**
     * Only used by client.
     */
    public static int getTreasuryAfterCosts(Player player, Galaxy galaxy, GalaxyMap galaxyMap, GameWorld gameWorld){
        Logger.finer("upkeepShips();" + CostPureFunctions.getPlayerUpkeepShips(player, galaxy.getPlanets(), galaxy.getSpaceships(), gameWorld));
        Logger.finer("upkeepTroops();" + CostPureFunctions.getPlayerUpkeepTroops(player, galaxy.getPlanets(), galaxy.getTroops()));
        Logger.finer("upkeepVIPs();" + CostPureFunctions.getPlayerUpkeepVIPs(player, galaxy.getAllVIPs()));
        Logger.finer("income();" + IncomePureFunctions.getPlayerIncome(player, false, galaxyMap, gameWorld, galaxy));
        Logger.finer("orders.getExpensesCost();" + ExpensePureFunction.getExpensesCost(galaxy, player, galaxyMap, gameWorld));
        Logger.finer("treasury;" + player.getTreasury());
        int tmpIncome = player.getTreasury() - CostPureFunctions.getPlayerUpkeepShips(player, galaxy.getPlanets(), galaxy.getSpaceships(), gameWorld) - CostPureFunctions.getPlayerUpkeepTroops(player, galaxy.getPlanets(), galaxy.getTroops()) -  CostPureFunctions.getPlayerUpkeepVIPs(player, galaxy.getAllVIPs()) + IncomePureFunctions.getPlayerIncome(player, false, galaxyMap, gameWorld, galaxy);
        tmpIncome -= ExpensePureFunction.getExpensesCost(galaxy, player, galaxyMap, gameWorld);
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

    public static Player getPlayer(Galaxy galaxy, String playerUuid) {
        for (Player player : galaxy.getPlayers()) {
            if (player.getUuid().equalsIgnoreCase(playerUuid)) {
                return player;
            }
        }
        return null;
    }

    public static CorruptionPoint getCorruptionPoint(GameWorld gameWorld, String factionUuid, String corruptionPointUuid) {
        Faction faction = GameWorldHandler.getFactionByUuid(factionUuid, gameWorld);
        if (faction.getCorruptionPoint().getUuid().equalsIgnoreCase(corruptionPointUuid)){
            return faction.getCorruptionPoint();
        }
        return faction.getResearchAdvantages().stream().filter(researchAdvantage -> researchAdvantage.getCorruptionPoint() != null && researchAdvantage.getCorruptionPoint().getUuid().equalsIgnoreCase(corruptionPointUuid)).findAny().orElseThrow().getCorruptionPoint();
    }
}
