/*
 * Created on 2005-aug-26
 */
package spaceraze.servlethelper.comparator.trooptype;

import java.util.Comparator;

import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.world.TroopType;

/**
 * Compares two spaceships, biggest/most expensive first
 */
public class TroopTypeComparator implements Comparator<TroopType> {
    static final long serialVersionUID = 1L;

	public int compare(TroopType tt1, TroopType tt2) {
		int diff = 0;
		// lowest build cost first
		if (diff == 0){
			diff = TroopPureFunctions.getCostBuild(tt2,0) - TroopPureFunctions.getCostBuild(tt1,0);
		}
		// else lowest support cost first
		if (diff == 0){
			diff = tt2.getUpkeep() - tt1.getUpkeep();
		}
		// else sort on name
		if (diff == 0){
			diff = tt2.getName().compareTo(tt1.getName());
		}
		return diff;
	}

}
