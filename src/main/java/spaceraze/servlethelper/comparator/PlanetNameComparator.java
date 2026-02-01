/*
 * Created on 2005-aug-26
 */
package spaceraze.servlethelper.comparator;

import java.util.Comparator;

import spaceraze.map.MapPlanet;
import spaceraze.game.Planet;

/**
 * @author WMPABOD
 *
 * Compares two planets alfanumerically
 */
public class PlanetNameComparator<T extends MapPlanet> implements Comparator<T> {

	public int compare(T p1, T p2) {
		return p1.getName().compareTo(p2.getName());
	}

}
