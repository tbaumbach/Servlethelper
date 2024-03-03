/*
 * Created on 2005-aug-26
 */
package spaceraze.servlethelper.comparator;

import java.util.Comparator;

import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.world.GameWorld;
import spaceraze.world.Troop;
import spaceraze.world.enums.BattleGroupPosition;

/**
 * Compares two spaceships, biggest/most expensive first
 */
public class TroopTypeAndBuildCostComparator implements Comparator<Troop> {
    static final long serialVersionUID = 1L;
    private GameWorld gameWorld;

	public TroopTypeAndBuildCostComparator(GameWorld gameWorld){
		this.gameWorld = gameWorld;
	}

	public int compare(Troop t1, Troop t2) {
		int diff = 0;
		if (t1.isSpaceshipTravel() & !t2.isSpaceshipTravel()){
			diff = -1;
		}else
		if (!t1.isSpaceshipTravel() & t2.isSpaceshipTravel()){
			diff = 1;
		}else
		if ((TroopPureFunctions.getTroopTypeByUuid(t1.getTypeUuid(), gameWorld).getDefaultPosition() == BattleGroupPosition.SUPPORT) & (TroopPureFunctions.getTroopTypeByUuid(t2.getTypeUuid(), gameWorld).getDefaultPosition() != BattleGroupPosition.SUPPORT)){
			diff = 1;
		}else
		if ((TroopPureFunctions.getTroopTypeByUuid(t1.getTypeUuid(), gameWorld).getDefaultPosition() != BattleGroupPosition.SUPPORT) & (TroopPureFunctions.getTroopTypeByUuid(t2.getTypeUuid(), gameWorld).getDefaultPosition() == BattleGroupPosition.SUPPORT)){
			diff = -1;
		}else
		if (!TroopPureFunctions.getTroopTypeByUuid(t1.getTypeUuid(), gameWorld).isArmor() & TroopPureFunctions.getTroopTypeByUuid(t2.getTypeUuid(), gameWorld).isArmor()){
			diff = 1;
		}else
		if (TroopPureFunctions.getTroopTypeByUuid(t1.getTypeUuid(), gameWorld).isArmor() & !TroopPureFunctions.getTroopTypeByUuid(t2.getTypeUuid(), gameWorld).isArmor()){
			diff = -1;
		}else if (diff == 0){
			diff = TroopPureFunctions.getCostBuild(TroopPureFunctions.getTroopTypeByUuid(t2.getTypeUuid(), gameWorld),0) - TroopPureFunctions.getCostBuild(TroopPureFunctions.getTroopTypeByUuid(t1.getTypeUuid(), gameWorld),0);
		}else if (diff == 0){
			diff = t2.getUpkeep() - t1.getUpkeep();
		}else if (diff == 0){
			diff = TroopPureFunctions.getTroopTypeByUuid(t2.getTypeUuid(), gameWorld).getName().compareTo(TroopPureFunctions.getTroopTypeByUuid(t1.getTypeUuid(), gameWorld).getName());
		}
		return diff;
	}

}
