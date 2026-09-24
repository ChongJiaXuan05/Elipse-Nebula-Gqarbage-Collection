package src.game.items;

import src.edu.monash.fit2099.engine.items.Item;
import src.edu.monash.fit2099.engine.statistics.BaseStatistic;

/**
 * Floppy Disk: A piece of ancient technology.
 * Extremely lightweight (weight of 1).
 */
public class FloppyDisk extends Item {
	/**
	 * Creates a portable floppy disk item.
	 */
	public FloppyDisk() {
		super("Floppy Disk", '⊟');
		this.addNewStatistic(ItemStatistics.WEIGHT, new BaseStatistic(1));
		this.makePortable();
	}
}
