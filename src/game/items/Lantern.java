package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.statistics.BaseStatistic;
import edu.monash.fit2099.engine.positions.Ground;
import game.structures.Fire;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Portable lantern that may leak oil and ignite the ground beneath its carrier.
 */
public class Lantern extends Item {
	private static final int STARTING_OIL_FUEL = 10;
	private static final int LEAK_CHANCE_PERCENT = 5;
	private int oilFuel = STARTING_OIL_FUEL;

	/**
	 * Creates a portable lantern with starting fuel.
	 */
	public Lantern() {
		super("Lantern", '&');
		this.addNewStatistic(ItemStatistics.WEIGHT, new BaseStatistic(7));
		this.makePortable();
	}

	/**
	 * Reduces fuel on leak chance and ignites the carrier's current ground.
	 *
	 * @param currentLocation location of the actor carrying the lantern
	 * @param actor actor carrying the lantern
	 */
	@Override
	public void tick(Location currentLocation, Actor actor) {
		if (oilFuel <= 0) {
			return;
		}

		if (ThreadLocalRandom.current().nextInt(100) < LEAK_CHANCE_PERCENT) {
			oilFuel -= 1;
			ignite(currentLocation);
		}
	}

	/**
	 * Replaces current ground with a fire wrapper.
	 *
	 * @param currentLocation location to ignite
	 */
	private void ignite(Location currentLocation) {
		Ground currentGround = currentLocation.getGround();
		currentLocation.setGround(new Fire(currentGround));
	}
}
