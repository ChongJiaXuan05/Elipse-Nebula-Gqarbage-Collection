package game.structures;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.ActorRole;
import game.statuses.Burn;

/**
 * A temporary hazard tile that burns actors and reverts to its previous ground.
 */
public class Fire extends Ground {
	private static final int FIRE_DURATION = 5;
	private static final int BURN_DURATION = 5;

	private final Ground previousGround;
	private int turnsRemaining;

	/**
	 * Creates a fire tile that will later revert to the provided ground.
	 *
	 * @param previousGround ground to restore after fire expires
	 */
	public Fire(Ground previousGround) {
		super('^', "Fire");
		this.previousGround = previousGround;
		this.turnsRemaining = FIRE_DURATION;
	}

	/**
	 * Applies burn effect to actors on this tile and expires after duration.
	 *
	 * @param location location of this fire tile
	 */
	@Override
	public void tick(Location location) {
		Actor actor = location.getActor();
		if (actor != null) {
			if (actor.hasAbility(ActorRole.WORKER)) {
				new Display().println("Warning: " + actor + " is standing on fire and is burning!");
			}
			actor.addStatus(new Burn(1, BURN_DURATION));
		}

		turnsRemaining -= 1;
		if (turnsRemaining <= 0) {
			location.setGround(previousGround);
		}
	}
}
