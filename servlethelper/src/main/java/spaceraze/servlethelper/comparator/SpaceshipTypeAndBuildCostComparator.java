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
 * Compares two spaceships, biggest/most expensive first
 */
public class SpaceshipTypeAndBuildCostComparator implements Comparator<Spaceship> {
    static final long serialVersionUID = 1L;
    private GameWorld gameWorld;

    public SpaceshipTypeAndBuildCostComparator(GameWorld gameWorld){
    	this.gameWorld = gameWorld;
	}

	public int compare(Spaceship ss1, Spaceship ss2) {
		int diff = 0;
		SpaceshipSizeAndBuildCostComparator sbcc = new SpaceshipSizeAndBuildCostComparator(gameWorld);
		if (SpaceshipPureFunctions.getSpaceshipTypeByKey(ss1.getTypeKey(), gameWorld).isCapitalShip() & !SpaceshipPureFunctions.getSpaceshipTypeByKey(ss2.getTypeKey(), gameWorld).isCapitalShip()){
			diff = -1;
		}else
		if (!SpaceshipPureFunctions.getSpaceshipTypeByKey(ss1.getTypeKey(), gameWorld).isCapitalShip() & SpaceshipPureFunctions.getSpaceshipTypeByKey(ss2.getTypeKey(), gameWorld).isCapitalShip()){
			diff = 1;
		}else
		if (SpaceshipPureFunctions.getSpaceshipTypeByKey(ss1.getTypeKey(), gameWorld).isCapitalShip() & SpaceshipPureFunctions.getSpaceshipTypeByKey(ss2.getTypeKey(), gameWorld).isCapitalShip()){
			diff = sbcc.compare(ss1,ss2);
		}else{
			// none of the ships are capital ships
			if (ss1.isSquadron() & !ss2.isSquadron()){
				diff = -1;
			}else
			if (!ss1.isSquadron() & ss2.isSquadron()){
				diff = 1;
			}else
			if (ss1.isSquadron() & ss2.isSquadron()){
				diff = sbcc.compare(ss1,ss2);
			}else{
				// none of the ships are squadrons
				if (SpaceshipPureFunctions.getSpaceshipTypeByKey(ss1.getTypeKey(), gameWorld).isCivilian() & !SpaceshipPureFunctions.getSpaceshipTypeByKey(ss2.getTypeKey(), gameWorld).isCivilian()){
					diff = -1;
				}else
				if (!SpaceshipPureFunctions.getSpaceshipTypeByKey(ss1.getTypeKey(), gameWorld).isCivilian() & SpaceshipPureFunctions.getSpaceshipTypeByKey(ss2.getTypeKey(), gameWorld).isCivilian()){
					diff = 1;
				}else
				if (SpaceshipPureFunctions.getSpaceshipTypeByKey(ss1.getTypeKey(), gameWorld).isCivilian() & SpaceshipPureFunctions.getSpaceshipTypeByKey(ss2.getTypeKey(), gameWorld).isCivilian()){
					diff = sbcc.compare(ss1,ss2);
				}else{
					// both of the ships must be defenders
					diff = sbcc.compare(ss1,ss2);
				}
			}
		}
		return diff;
	}

}
