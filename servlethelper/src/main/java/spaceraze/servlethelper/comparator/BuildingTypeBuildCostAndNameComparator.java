package spaceraze.servlethelper.comparator;

import java.util.Comparator;

import spaceraze.servlethelper.game.BuildingPureFunctions;
import spaceraze.world.BuildingType;

public class BuildingTypeBuildCostAndNameComparator implements Comparator<BuildingType>{
	
	static final long serialVersionUID = 1L;

	public int compare(BuildingType arg0, BuildingType arg1) {
		int diff = BuildingPureFunctions.getBuildCost(arg1, 0) - BuildingPureFunctions.getBuildCost(arg0, 0);
		if (diff == 0){
			diff = arg0.getName().compareTo(arg1.getName());
		}
		return diff;
	}

}
