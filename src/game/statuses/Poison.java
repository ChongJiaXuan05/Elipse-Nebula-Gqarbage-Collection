package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Damage-over-time status effect applied by contaminated consumables.
 */
public class Poison implements Status {
    private int turnsRemaining;

    /**
     * Creates a poison effect with a fixed duration.
     *
     * @param turns number of turns the effect should remain active
     */
    public Poison(int turns) {
        this.turnsRemaining = turns;
    }

    /**
     * Applies one turn of poison damage and decreases remaining duration.
     *
     * @param currEntity entity holding the status
     * @param location location of the entity
     */
    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        if (turnsRemaining > 0 && currEntity instanceof Actor actor) {
            actor.hurt(1);
            turnsRemaining -= 1;
        }
    }

    /**
     * Indicates whether poison is still active.
     *
     * @return true while turns remain
     */
    @Override
    public boolean isStatusActive() {
        return turnsRemaining > 0;
    }
}