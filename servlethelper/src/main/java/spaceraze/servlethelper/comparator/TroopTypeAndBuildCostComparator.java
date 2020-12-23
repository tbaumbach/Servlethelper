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
		if ((TroopPureFunctions.getTroopTypeByKey(t1.getTypeKey(), gameWorld).getDefaultPosition() == BattleGroupPosition.SUPPORT) & (TroopPureFunctions.getTroopTypeByKey(t2.getTypeKey(), gameWorld).getDefaultPosition() != BattleGroupPosition.SUPPORT)){
			diff = 1;
		}else
		if ((TroopPureFunctions.getTroopTypeByKey(t1.getTypeKey(), gameWorld).getDefaultPosition() != BattleGroupPosition.SUPPORT) & (TroopPureFunctions.getTroopTypeByKey(t2.getTypeKey(), gameWorld).getDefaultPosition() == BattleGroupPosition.SUPPORT)){
			diff = -1;
		}else
		if (!TroopPureFunctions.getTroopTypeByKey(t1.getTypeKey(), gameWorld).isArmor() & TroopPureFunctions.getTroopTypeByKey(t2.getTypeKey(), gameWorld).isArmor()){
			diff = 1;
		}else
		if (TroopPureFunctions.getTroopTypeByKey(t1.getTypeKey(), gameWorld).isArmor() & !TroopPureFunctions.getTroopTypeByKey(t2.getTypeKey(), gameWorld).isArmor()){
			diff = -1;
		}else if (diff == 0){
			diff = TroopPureFunctions.getCostBuild(TroopPureFunctions.getTroopTypeByKey(t2.getTypeKey(), gameWorld),null) - TroopPureFunctions.getCostBuild(TroopPureFunctions.getTroopTypeByKey(t1.getTypeKey(), gameWorld),null);
		}else if (diff == 0){
			diff = t2.getUpkeep() - t1.getUpkeep();
		}else if (diff == 0){
			diff = TroopPureFunctions.getTroopTypeByKey(t2.getTypeKey(), gameWorld).getName().compareTo(TroopPureFunctions.getTroopTypeByKey(t1.getTypeKey(), gameWorld).getName());
		}
		return diff;
	}

}
