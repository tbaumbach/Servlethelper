/*
 * Created on 2005-aug-26
 */
package spaceraze.servlethelper.comparator;

import java.util.Comparator;

import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.world.SpaceshipType;

/**
 * @author WMPABOD
 *
 * Compares two spaceships, biggest/most expensive first
 */
public class SpaceshipTypeComparator implements Comparator<SpaceshipType> {
    static final long serialVersionUID = 1L;

	public int compare(SpaceshipType sst1, SpaceshipType sst2) {
		int diff = 0;
		// defence stations first
		if (diff == 0){
			if (SpaceshipPureFunctions.isDefenceShip(sst1) & !SpaceshipPureFunctions.isDefenceShip(sst2)){
				diff = -1;
			}else
			if (!SpaceshipPureFunctions.isDefenceShip(sst1) & SpaceshipPureFunctions.isDefenceShip(sst2)){
				diff = 1;
			}
		}
		// else civilian ships first
		if (diff == 0){
			if (sst1.isCivilian() & !sst2.isCivilian()){
				diff = -1;
			}else
			if (!sst1.isCivilian() & sst2.isCivilian()){
				diff = 1;
			}
		}
		// else smallest first
		if (diff == 0){
			diff = sst1.getSize().getCompareSize() - sst2.getSize().getCompareSize();
		}
		// else lowest build cost first
		if (diff == 0){
			diff = SpaceshipPureFunctions.getBuildCost(sst1, 0) - SpaceshipPureFunctions.getBuildCost(sst2, 0);
		}
		// else lowest support cost first
		if (diff == 0){
			diff = sst1.getUpkeep() - sst2.getUpkeep();
		}
		// else sort on name
		if (diff == 0){
			diff = sst1.getName().compareTo(sst2.getName());
		}
		return diff;
	}

}
