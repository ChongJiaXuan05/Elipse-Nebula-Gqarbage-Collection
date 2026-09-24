package src.game.items;

import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.actors.ActorStatistics;
import src.edu.monash.fit2099.engine.items.Item;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.statistics.BaseStatistic;
import src.edu.monash.fit2099.engine.statistics.StatisticOperations;
import src.game.actions.ConsumeAction;

/**
 * A multi-use consumable item that can be eaten up to a fixed number of times.
 */
public class Cookies extends Item implements Consumable {
	private static final int COOKIE_COUNT = 5;
	private int cookiesRemaining = COOKIE_COUNT;

	/**
	 * Creates a portable cookie pack.
	 */
	public Cookies() {
		super("Cookies", '◍');
		this.addNewStatistic(ItemStatistics.WEIGHT, new BaseStatistic(2));
		this.makePortable();
	}

	/**
	 * Consumes one cookie and applies effect.
	 *
	 * @param actor actor eating the cookie
	 * @return outcome message
	 */
	@Override
	public String consumeBy(Actor actor) {
		if (actor.hasAbility(Ability.STERILISE)) {
			actor.heal(1);
		} else {
			actor.modifyStatisticMaximum(ActorStatistics.HEALTH, StatisticOperations.DECREASE, 1);
		}
		cookiesRemaining -= 1;

		return actor + " eats a cookie.";
	}

	/**
	 * Indicates whether all cookies have been consumed.
	 *
	 * @return true when none remain
	 */
	@Override
	public  boolean isConsumed() {
		return cookiesRemaining <= 0;
	}

	/**
	 * Returns consume action for the cookie pack.
	 *
	 * @param owner actor carrying the cookies
	 * @param map map containing the actor
	 * @return available actions for this item
	 */
	@Override
	public ActionList allowableActions(Actor owner, GameMap map) {
		ActionList actions = new ActionList();
		actions.add(new ConsumeAction(this));
		return actions;
	}
}
