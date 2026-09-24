package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Behavior contract for anything that can be consumed by an actor.
 * Implementations can be inventory items or environment entities.
 */
public interface Consumable {
    /**
     * Applies this consumable's effect to the provided actor.
     *
     * @param actor actor consuming the effect
     * @return outcome message
     */
    String consumeBy(Actor actor);

    /**
     * Indicates whether this consumable should be removed after use.
     *
     * @return true when consumed and removable
     */
    default boolean isConsumed() {
        return false;
    }
}
