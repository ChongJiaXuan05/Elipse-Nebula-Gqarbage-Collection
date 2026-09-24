package game.structures;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.items.Ability;
import game.items.Consumable;
import game.statuses.Poison;

/**
 * A small, stationary body of mysterious liquid on the ground.
 * In a standard video game, this would just be water. On a deprecated moon
 * in the Eclipse Nebula, it could be anything from spilled engine coolant to
 * highly corrosive alien saliva. Step in it at your own risk.
 */
public class Puddle extends Ground implements Consumable {
	private static final int POISON_DURATION = 3;
	private static final int HEAL_AMOUNT = 1;

    /**
     * Creates a puddle ground tile.
     */
    public Puddle() {
        super('~', "Puddle");
    }

	/**
	 * Adds a consume action so actors can drink from the puddle.
	 *
	 * @param actor actor interacting with this tile
	 * @param location location of this tile
	 * @param direction direction string from actor perspective
	 * @return action list including consume action
	 */
	@Override
	public ActionList allowableActions(Actor actor, Location location, String direction) {
		ActionList actions = super.allowableActions(actor, location, direction);
		actions.add(new ConsumeAction(this));

		return actions;
	}

	/**
	 * Applies sanitized healing or poison effect.
	 *
	 * @param actor actor consuming from the puddle
	 * @return outcome message
	 */
	@Override
	public String consumeBy(Actor actor) {
		if (actor.hasAbility(Ability.STERILISE)) {
			actor.heal(HEAL_AMOUNT);
			return actor + " drinks from the puddle and is healed for " + HEAL_AMOUNT + " health point.";
		}

		actor.addStatus(new Poison(POISON_DURATION));
		return actor + " drinks from the puddle and is poisoned, taking 1 damage per turn for "
				+ POISON_DURATION + " turns.";
	}
}
