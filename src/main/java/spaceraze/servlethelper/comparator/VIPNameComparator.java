/*
 * Created on 2005-aug-26
 */
package spaceraze.servlethelper.comparator;

import java.util.Comparator;

import spaceraze.servlethelper.game.vip.VipPureFunctions;
import spaceraze.world.GameWorld;
import spaceraze.world.VIP;

/**
 * @author WMPABOD
 *
 * Compares two viptypes, alfanumerically on name field
 */
public class VIPNameComparator<T extends VIP> implements Comparator<T> {
    static final long serialVersionUID = 1L;

    private GameWorld gameWorld;

    public VIPNameComparator(GameWorld gameWorld){
    	this.gameWorld = gameWorld;
	}

	public int compare(T vip1, T vip2) {
		return VipPureFunctions.getVipTypeByUuid(vip1.getTypeUuid(), gameWorld).getName().compareToIgnoreCase(VipPureFunctions.getVipTypeByUuid(vip2.getTypeUuid(), gameWorld).getName());
	}

}
