package spaceraze.servlethelper.game.troop;

import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.world.*;

public class TroopMutator {

    private TroopMutator(){}

    public static Troop createTroop(Player player, TroopType type, VIP vipWithTechBonus, int factionTechBonus, int buildingTechBonus, int uniqueId){
        PlayerTroopImprovement troopImprovement = player != null ?  PlayerPureFunctions.findTroopImprovement(type.getUniqueName(), player) : null;
        TroopType troopType = troopImprovement != null ? new TroopType(type, troopImprovement) : type;
        int nrProduced = troopImprovement != null ? troopImprovement.updateNrProduced() : uniqueId;
        nrProduced++;
        int totalTechBonus = 0;
        if (vipWithTechBonus != null){
            totalTechBonus = vipWithTechBonus.getTechBonus();
        }
        totalTechBonus += factionTechBonus;
        totalTechBonus += buildingTechBonus;
        Troop tmpTroop = new Troop(troopType, nrProduced, totalTechBonus, uniqueId);
        return tmpTroop;
    }

    public static Troop createTroop(TroopType type, Galaxy galaxy){

        return createTroop(null, type, null, 0, 0, galaxy.getUniqueIdCounter("Troop").getUniqueId());
    }
}
