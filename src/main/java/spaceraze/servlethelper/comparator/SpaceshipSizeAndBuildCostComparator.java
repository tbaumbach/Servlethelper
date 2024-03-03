/*
 * Created on 2005-aug-26
 */
package spaceraze.servlethelper.comparator;

import java.util.Comparator;

import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.world.GameWorld;
import spaceraze.world.Spaceship;

/**
 * @author WMPABOD
 *
 *         Compares two spaceships: 1. show all capital ships 2. show squadrons
 *         3. show defenders 4. show civilians
 * 
 *         If several of the same typ -> biggest/most expensive first
 */
public class SpaceshipSizeAndBuildCostComparator implements Comparator<Spaceship> {
	static final long serialVersionUID = 1L;
	private GameWorld gameWorld;

	public SpaceshipSizeAndBuildCostComparator(GameWorld gameWorld){
		this.gameWorld = gameWorld;
	}

	public int compare(Spaceship ss1, Spaceship ss2) {
		SpaceshipTypeSizeAndBuildCostComparator stsabcc = new SpaceshipTypeSizeAndBuildCostComparator();
		int diff = stsabcc.compare(SpaceshipPureFunctions.getSpaceshipTypeByUuid(ss1.getTypeUuid(), gameWorld), SpaceshipPureFunctions.getSpaceshipTypeByUuid(ss2.getTypeUuid(), gameWorld));

		/*
		 * if (diff == 0){ diff = ss2.getSpaceshipType().getBuildCost(null) -
		 * ss1.getSpaceshipType().getBuildCost(null); } if (diff == 0){ diff =
		 * ss1.getSpaceshipType().getName().compareTo(ss2.getSpaceshipType().getName());
		 * }
		 */
		return diff;
	}

}
