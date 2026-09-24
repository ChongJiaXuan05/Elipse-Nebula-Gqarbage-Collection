package src.game.items;

import src.edu.monash.fit2099.engine.actions.ActionList;
import src.edu.monash.fit2099.engine.actors.Actor;
import src.edu.monash.fit2099.engine.items.Item;
import src.edu.monash.fit2099.engine.positions.GameMap;
import src.edu.monash.fit2099.engine.statistics.BaseStatistic;
import src.game.actions.ConsumeAction;
import src.game.statuses.Poison;

/**
 * A consumable apple with beneficial or harmful effects based on sterilisation ability.
 */
public class Apple extends Item implements Consumable {
    /**
     * Creates a portable apple item.
     */
    public Apple() {
        super("Apple", 'ó');
        this.addNewStatistic(ItemStatistics.WEIGHT, new BaseStatistic(1));
        this.makePortable();
    }

    /**
     * Applies the apple effect based on sterilisation ability.
     *
     * @param actor actor consuming the apple
     * @return outcome description
     */
    @Override
    public String consumeBy(Actor actor) {
        String message;
        if (actor.hasAbility(Ability.STERILISE)){
            actor.heal(3);
            message = actor + " eats the apple and recovers 3 hit points.";
        } else {
            actor.addStatus(new Poison(5));
            message = actor + " eats the apple and is poisoned.";
        }
        //actor.getInventory().remove(this);
        return message;
    }

    /**
     * Indicates whether the apple should be removed after being consumed.
     *
     * @return true once consumed
     */
    @Override
    public  boolean isConsumed() {
        return true;
    }

    /**
     * Returns the consume action for this apple.
     *
     * @param owner actor carrying the apple
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
