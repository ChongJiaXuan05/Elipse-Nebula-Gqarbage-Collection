package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.behaviours.Behaviour;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.items.Consumable;

/**
 * Behaviour that consumes the first consumable item available on the actor's tile.
 */
public class ConsumeBehaviour implements Behaviour<Actor, Action> {
    /**
     * Selects a consume action for the first consumable item on the current tile.
     *
     * @param actor actor executing the behaviour
     * @param location actor's current location
     * @return consume action when possible, otherwise null
     */
    @Override
    public Action operate(Actor actor, Location location) {
        for (Item item : location.getItems()) {
            Consumable consumable = item.asCapability(Consumable.class).orElse(null);
            if (consumable != null) {
                return new ConsumeAction(consumable);
            }
        }

        return null;
    }
}
