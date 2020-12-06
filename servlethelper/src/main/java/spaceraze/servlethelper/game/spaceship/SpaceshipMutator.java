package spaceraze.servlethelper.game.spaceship;

import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.world.*;

public class SpaceshipMutator {

    private SpaceshipMutator(){}

    public static Spaceship createSpaceShip(Player player, SpaceshipType type, VIP vipWithBonus, int factionTechBonus, int buildingBonus){
        PlayerSpaceshipImprovement playerSpaceshipImprovement = PlayerPureFunctions.findSpaceshipImprovement(type.getName(), player);
        SpaceshipType spaceshipType = playerSpaceshipImprovement != null ? new SpaceshipType(type, playerSpaceshipImprovement) : type;
        int nrProduced = playerSpaceshipImprovement != null ? playerSpaceshipImprovement.updateNrProduced() : 0;

         return new Spaceship(spaceshipType, null, nrProduced, vipWithBonus,factionTechBonus,buildingBonus);
    }

    public static Spaceship createSpaceShip(SpaceshipType type){

        return new Spaceship(type, null, 0, null,0,0);
    }
}
