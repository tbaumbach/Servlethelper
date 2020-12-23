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
 * Compares two spaceships:
 * 1. size
 * 2. build cost
 * 3. name
 * 
 */
public class SpaceshipTypeBuildCostComparator implements Comparator<SpaceshipType> {
    static final long serialVersionUID = 1L;

	public int compare(SpaceshipType sst1, SpaceshipType sst2) {
		int diff = SpaceshipPureFunctions.getBuildCost(sst2, null) - SpaceshipPureFunctions.getBuildCost(sst1, null);
		if (diff == 0){
			diff = sst1.getName().compareTo(sst2.getName());
		}
		return diff;
	}

}
