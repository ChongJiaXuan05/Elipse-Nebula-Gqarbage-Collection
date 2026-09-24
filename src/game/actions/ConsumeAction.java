package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Consumable;

/**
 * Action that applies a consumable's effect to an actor.
 */
public class ConsumeAction extends Action {
    private Consumable consumable;

    /**
     * Creates a consume action for a specific consumable target.
     *
     * @param consumable consumable entity to be used
     */
    public ConsumeAction(Consumable consumable) {
        this.consumable = consumable;
    }

    /**
     * Applies the consumable effect to the actor and removes consumed items.
     *
     * @param actor actor consuming the target
     * @param map map where consumption occurs
     * @return outcome text from the consumable
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = consumable.consumeBy(actor);
        if (consumable.isConsumed() && consumable instanceof Item item) {
            actor.getInventory().remove(item);
            map.locationOf(actor).removeItem(item);
        }
        return result;
    }

    /**
     * Returns menu text for consuming the target.
     *
     * @param actor actor viewing the menu
     * @return menu description string
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consume " + consumable;
    }
}
