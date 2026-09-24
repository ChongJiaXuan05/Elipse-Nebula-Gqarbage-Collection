package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.statistics.BaseStatistic;

/**
 * CRT Monitor: A massive, incredibly heavy piece of archaic junk.
 * Takes up massive 30 units of weight in the worker's inventory.
 */
public class CRTMonitor extends Item {
	/**
	 * Creates a portable CRT monitor item.
	 */
	public CRTMonitor() {
		super("CRT Monitor", '◙');
		this.addNewStatistic(ItemStatistics.WEIGHT, new BaseStatistic(30));
		this.makePortable();
	}
}
