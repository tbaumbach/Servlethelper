package spaceraze.servlethelper.game.spaceship;

import spaceraze.world.*;

public class SpaceshipMutator {

    private SpaceshipMutator(){}

    public static Spaceship createSpaceShip(Player player, SpaceshipType type,  VIP vipWithBonus, Galaxy galaxy, int factionTechBonus, int buildingBonus, int uniqueId){
        PlayerSpaceshipType playerSpaceshipType = player.findOwnPlayerSpaceshipType(type.getName());
        SpaceshipType spaceshipType = playerSpaceshipType != null ? new SpaceshipType(type, playerSpaceshipType) : type;
        int nrProduced = playerSpaceshipType != null ? playerSpaceshipType.updateNrProduced() : uniqueId;

         return new Spaceship(spaceshipType, null, nrProduced, galaxy.getUniqueIdCounter("Ship").getUniqueId(),vipWithBonus,factionTechBonus,buildingBonus);
    }

    public static Spaceship createSpaceShip(SpaceshipType type, Galaxy galaxy){

        return new Spaceship(type, null, 0, galaxy.getUniqueIdCounter("Ship").getUniqueId(),null,0,0);
    }

    //.getShip(null,p.getFaction().getTechBonus(),0);
    /*
    @JsonIgnore
    public Spaceship getShip(VIP vipWithBonus, int factionTechBonus, int buildingBonus){
      nrProduced++;
      return new Spaceship(Functions.deepClone(this),null,nrProduced,uic.getUniqueId(),vipWithBonus,factionTechBonus,buildingBonus);
    }
     */
}
